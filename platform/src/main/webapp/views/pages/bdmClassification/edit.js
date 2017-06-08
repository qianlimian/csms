(function () {
    var EditModule = smart.SingleEditModule.extend({
        //构造函数
        init: function (options) {
            smart.SingleEditModule.fn.init.call(this, options);
        },

        //初始化组件
        initComponents: function () {
            smart.kendoui.comboBox(this.$("#edit_caseType"),{
                dataSource: smart.Enums["com.bycc.enumitem.CaseType"].getData()
            });
            smart.kendoui.comboBox(this.$("#edit_riskLevel"),{
                dataSource: smart.Enums["com.bycc.enumitem.RiskLevel"].getData()
            });
        }
    });
    new EditModule({
        name:"SmartBdmClassificationEdit",
        containerId:"ctnBdmClassificationEdit",
        restUrl: "/bdmClassifications/",
        modelName: "bdmClassification",
        model:{
            bdmClassification:{
                id:"",
                caseType:"",
                keyWord:"",
                riskLevel:"",
                note:""

            }
        },
        ymlModule: "bdmClassification"
    });
})();
