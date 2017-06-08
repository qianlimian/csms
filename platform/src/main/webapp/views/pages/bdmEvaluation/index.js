(function () {
    var IndexModule = smart.SingleIndexModule.extend({

		//构造函数
        init: function (options) {
            smart.SingleIndexModule.fn.init.call(this, options);
        },

		//初始化组件
        initComponents: function () {
            //调用父类方法初始化查询窗口、编辑页面、导航条等
            smart.SingleIndexModule.fn.initComponents.call(this);

            smart.kendoui.grid($("#mainGrid"),
                $.extend(true, this.gridOptions(), {
                    // dataSource : { //可省略，默认方法query，有两个参数：condition{}用于封装窗口查询参数，queryBean{}用于封装parameterMap参数
                    //     url: this.restUrl + "query.do"
                    // },
                    columns : [
                        { field: "id",  width: 100, hidden: true},
                        { field: "standard", type: "string", title: "积分评价", width: 150 },
                        { field: "scoreType", type: "String", title: "加减分项", width: 150},
                        { field: "score", type: "number", title: "基础分值", width: 150,
                            filterable: {
                                cell: {
                                    template: function (args) {
                                        args.element.kendoNumericTextBox({
                                            format: "n0",
                                            decimals: 0
                                        });
                                    }
                                }
                            }
                        },
                        { field: "evalType", type: "String", title: "类别", width: 150},
                        { field: "note", type: "string", title: "备注", width: 150 }
                    ]
                })
            );
        }
    });

    new IndexModule({
        name : "SmartBdmEvaluationIndex", //必需，Index模块名
        containerId : "ctnBdmEvaluationIndex", //必需，Index模块的容器id
        restUrl: "/bdmEvaluations/", //必需，请求的rest地址
        editModule : {
            name : "SmartBdmEvaluationEdit", //必需，Edit模块名
            containerId : "ctnBdmEvaluationEditWrap" //必需，Edit模块的容器id
        },
        ymlModule: "bdmEvaluation" //配置yml按钮显隐的模块名
    });
})();
