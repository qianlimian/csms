(function () {
    var IndexModule = smart.SingleIndexModule.extend({

		//构造函数
        init: function (options) {
            smart.SingleIndexModule.fn.init.call(this, options);
        },
        
        //设置编辑页面title
        _setTitle: function (title) {
            this.editWin.title("查看案件信息");
        },
        
        //编辑
        doEdit: function (event) {
            var editItems = [];

            //行内编辑
            if (event) {
                var tr = $(event.target).closest("tr");
                var data = this.mainGrid.dataItem(tr);
                editItems.push(data);
            //多条编辑
            } else {
                var data = this.mainGrid.selectData();
                editItems = data.items;
            }

            if (editItems.length < 1) {
                smart.alert("请选择要查看的案件");
                return false;
            }

            this._doShow(editItems);
        },
        
		//初始化组件
        initComponents: function () {
            //调用父类方法初始化查询窗口、编辑页面、导航条等
            smart.SingleIndexModule.fn.initComponents.call(this);

            smart.kendoui.grid($("#mainGrid"),
                $.extend(true, this.gridOptions(), {
                    dataSource : {
                        url: this.restUrl + "query.do?type=dispute"
                    },
                    command: false,
                    columns : [
                        {field: "id", width: 100, hidden: true},
                        {field: "caseCode", type: "string", title: "案件编号", width: 100},
                        {field: "caseName", type: "string", title: "案件名称", width: 100},
                        {field: "suspect", type: "string", title: "嫌疑人", width: 100},
                        {field: "masterUnit", type: "string", title: "主办单位", width: 100, mapping: "masterUnit.name"},
                        {field: "masterPoliceName", type: "string", title: "主办人", width: 100, mapping: "masterPolice.user.name"},
                        {field: "caseStatus", type: "enum", title: "案件状态", width: 100,
                            values: smart.Enums["com.bycc.enumitem.CaseStatus"].getData()
                        }
                    ]
                })
            );

            //办案区登记窗口
            smart.kendoui.window('#ctnCaseRegisterWrap', {
                content: basePath + "/caseRegister.do",
                title: "办案区登记",
                width: "80%",
                height: "80%"
            });
        },

        //@overwrite
        bindEvents: function () {
            smart.IndexModule.fn.bindEvents.call(this);

            smart.bind('#' + this.containerId + ' #btnDoView', [this.doEdit, this]);
            smart.bind('#' + this.containerId + ' #btnDoHandle', [this.doHandle, this]);
            smart.bind('#' + this.containerId + ' #btnDoFinish', [this.doFinish, this]);
        },

        //取选择的案件
        getSelectItem: function () {
            var $row = this.mainGrid.select(),
                item = this.mainGrid.dataItem($row);
            if (!item) smart.alert("请选择案件记录");
            return item;
        },

        //开始办案
        doHandle: function () {
            var me = this,
                selectedItem = this.getSelectItem();
            if (selectedItem) {
                smart.ajax({
                    type: 'get',
                    url: basePath + "/caseRecords/startCase.do?caseId=" + selectedItem.id,
                    success: function (res) {

                        smart.confirm({
                            message: "即将开始办案区登记流程，是否继续?",
                            buttons: [{
                                click: function () {
                                    var regWin = $("#ctnCaseRegisterWrap").data("kendoWindow"),
                                        regModule = smart.Module.getModule("SmartCaseRegisterIndex");

                                    regWin.maximize().open();
                                    regModule.loadItems(null, selectedItem.id);
                                }
                            }]
                        });
                    },
                    error: function (jqXHR) {
                        smart.alert(jqXHR.responseText);
                    }
                });
            }
        },

        //结束办案
        doFinish: function () {
            var me = this,
                selectedItem = this.getSelectItem();
            if (selectedItem) {
                smart.confirm({
                    message: "即将结束办案区登记流程，是否继续?",
                    buttons: [{
                        click: function () {
                            smart.ajax({
                                type: 'GET',
                                url: basePath + "/caseRecords/finishCase.do?caseId=" + selectedItem.id,
                                success: function (res) {
                                    me.notification.hide().success({ message: "操作成功！" });
                                },
                                error: function (jqXHR) {
                                    smart.alert(jqXHR.responseText);
                                }
                            });
                        }
                    }]
                });
            }
        }
    });

    new IndexModule({
        name : "SmartCaseIndex",
        containerId : "ctnCaseIndex",
        restUrl: "/cases/",
        editModule : {
            name : "SmartCaseEdit",
            containerId : "ctnCaseEditWrap",
            options: {
                width : '80%',
                height : '80%',
                content: "/cases/edit.htm?type=dispute"
            }
        },
        ymlModule: "case"
    });
})();
