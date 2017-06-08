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
                    columns : [
                        {field: "id", width: 100, hidden: true},
                        {field: "caseType", type: "enum", title: "案件类型", width: 100,
                           values: smart.Enums["com.bycc.enumitem.CaseType"].getData()
                         },
                        {field: "keyWord", type: "string", title: "关键词", width: 100},
                        {field: "riskLevel", type: "enum", title: "风险等级", width: 100,
                            values: smart.Enums["com.bycc.enumitem.RiskLevel"].getData()
                        },
                        {field: "note", type: "string", title: "备注", width: 100}

                    ]
                })
            );
        }
    });

    new IndexModule({
        name : "SmartBdmClassificationIndex", //必需，Index模块名
        containerId : "ctnBdmClassificationIndex", //必需，Index模块的容器id
        restUrl: "/bdmClassifications/", //必需，请求的rest地址
        editModule : {
            name : "SmartBdmClassificationEdit", //必需，Edit模块名
            containerId : "ctnBdmClassificationEditWrap" //必需，Edit模块的容器id
        },
        ymlModule: "bdmClassification"
    });
})();

