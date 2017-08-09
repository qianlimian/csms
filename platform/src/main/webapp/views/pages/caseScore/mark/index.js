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
                        url: this.restUrl + "query.do"
                    },
                    command: false,
                    columns : [
                        {field: "id", width: 100, hidden: true},
                        {field: "caseCode", type: "string", title: "案件编号", width: 100},
                        {field: "caseName", type: "string", title: "案件名称", width: 100},
                        {field: "caseType", type: "enum", title: "案件类型", width: 100,
                            values: smart.Enums["com.bycc.enumitem.CaseType"].getData()
                        },
                        {field: "suspect", type: "string", title: "嫌疑人", width: 100},
                        {field: "masterUnit", type: "string", title: "主办单位", width: 100, mapping: "masterUnit.name"},
                        {field: "masterPoliceName", type: "string", title: "主办人", width: 100, mapping: "masterPolice.user.name"},
                        {field: "slaveUnit", type: "string", title: "协办单位", width: 100,mapping:"slaveUnit.name"},
                        {field: "slavePoliceName", type: "string", title: "协办人", width: 100,mapping:"slavePolice.user.name"},
                        {field: "caseStatus", type: "enum", title: "案件状态", width: 100,
                            values: smart.Enums["com.bycc.enumitem.CaseStatus"].getData()
                        }
                    ]
                })
            );

        },

        //@overwrite
        bindEvents: function () {
            smart.IndexModule.fn.bindEvents.call(this);

            smart.bind('#' + this.containerId + ' #btnDoView', [this.doEdit, this]);
            smart.bind('#' + this.containerId + ' #btnDoMark', [this.doMark, this]);

            var self = this;
            this.mainGrid.bind("dataBound", function() {
                $.each(this.dataSource.data(), function(i, data) {
                    if (data["riskLevel"] === "高") {
                        self.mainGrid.setColorById(data["id"], "red");
                    }
                })
            });
        },

        //取选择的案件
        getSelectItem: function () {
            var $row = this.mainGrid.select(),
                item = this.mainGrid.dataItem($row);
            if (!item) smart.alert("请选择案件记录");
            return item;
        },

        //打分
        doMark:function(){
            var selectedItem = this.getSelectItem();
            if (selectedItem) {
                //打分窗口
                smart.kendoui.window('#ctnCaseScoreWrap', {
                    content: basePath + "/caseScores/score.do?caseId=" + selectedItem.id,
                    title: "评价",
                    width: "80%",
                    height: "80%"
                }).center().open();
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
                content: "/cases/edit.htm"
            }
        }
    });
})();
