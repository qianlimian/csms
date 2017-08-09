(function () {
    var IndexModule = smart.SingleIndexModule.extend({

        // 构造函数
        init: function (options) {
            smart.SingleIndexModule.fn.init.call(this, options);
        },

        // 初始化组件
        initComponents: function () {
            // 调用父类方法初始化查询窗口、编辑页面、导航条等
            var self = this;
            smart.SingleIndexModule.fn.initComponents.call(this);

            var dataSource = new kendo.data.HierarchicalDataSource({
                transport: {
                    read: {
                        url: basePath + "/bdmEvaluations/findBigItems.do?",
                        dataType: "json"
                    }
                }
            });
            $("#rootItem").kendoTreeView({
                dataSource: dataSource,
                dataTextField: "standard",
                select: function (e) {
                    var data = this.dataItem(e.node);
                    self.showEvalDetails(data.id);
                }
            });

            $("#detailItems").kendoTreeView({
                dataTextField: "standard"
            });

            //新增评价大项
            $("#btnDoNewItem").click(function () {
                self.editEval("新增评价项", null, null);
            });
            //编辑评价大项
            $("#btnDoEditItem").click(function () {
                var id = self.getSelectId($("#rootItem"));
                self.editEval("新增评价项", id, null);
            });
            //删除评价大项
            $("#btnDoDeleteItem").click(function () {
                var id = self.getSelectId($("#rootItem"));
                self.deleteEval(true, id);
            });
            //新增评分细则
            $("#btnDoNewSub").click(function () {
                var parent = self.getSelectId($("#detailItems"));
                self.editEval("新增评价项", null, parent);
            });
            //编辑评分细则
            $("#btnDoEditSub").click(function () {
                var id = self.getSelectId($("#detailItems"));
                self.editEval("编辑评价项", id, null);
            });
            //删除评分细则
            $("#btnDoDeleteSub").click(function () {
                var id = self.getSelectId($("#detailItems"));
                self.deleteEval(false, id);
            });
        },
        //获得选中项的ID
        getSelectId: function (target) {
            var selectSubItem = target.data("kendoTreeView").select();
            var data = target.data('kendoTreeView').dataItem(selectSubItem);
            return data.id;
        },
        //评分细则
        showEvalDetails: function (id) {
            var detailDataSource = new kendo.data.HierarchicalDataSource({
                transport: {
                    read: {
                        url: basePath + "/bdmEvaluations/findAllById.do?id=" + id,
                        dataType: "json"
                    }
                },
                schema: {
                    model: {
                        children: "treeleafs",
                        hasChildren: "hasChildren",
                        expanded: true
                    }
                }
            });
            $("#detailItems").data("kendoTreeView").setDataSource(detailDataSource);
        },
        //新增|编辑打分项
        editEval: function (title, id, parent) {
            var url = basePath + "/bdmEvaluations/edit.do";
            if (id) {
                url = basePath + "/bdmEvaluations/edit.do?id=" + id;
            } else if (parent) {
                url = basePath + "/bdmEvaluations/edit.do?parent=" + parent;
            }
            smart.kendoui.window('#ctnSelectWrap', {
                content: url,
                title: title,
                width: "50%",
                height: "50%"
            }).center().open();
        },
        //删除打分项
        deleteEval: function (target, id) {
            var self = this;
            smart.confirm({
                message: "您确定要删除该项嘛?",
                buttons: [{
                    click: function () {
                        $.ajax({
                            url: basePath + "/bdmEvaluations/delete.do?id=" + id,
                            type: 'DELETE',
                            success: function () {
                                self.doTreeViewFresh(target);
                            }
                        });
                    }
                }]
            });
        },
        //刷新打分项
        doTreeViewFresh: function (target) {
            if (target) {
                $("#rootItem").data("kendoTreeView").dataSource.read();
                $("#detailItems").data("kendoTreeView").dataSource.read();
            } else {
                $("#detailItems").data("kendoTreeView").dataSource.read();
            }
        },
        //重置
        resizeLayout: function () {}
    });

    new IndexModule({
        name: "SmartBdmEvaluationIndex", // 必需，Index模块名
        containerId: "ctnBdmEvaluationIndex", // 必需，Index模块的容器id
        restUrl: "/bdmEvaluations/", // 必需，请求的rest地址
        editModule: {
            name: "SmartBdmEvaluationEdit", // 必需，Edit模块名
            containerId: "ctnBdmEvaluationEditWrap" // 必需，Edit模块的容器id
        },
        ymlModule: "bdmEvaluation" // 配置yml按钮显隐的模块名
    });

})();
