(function () {

    var EditModule = smart.SingleEditModule.extend({
        //构造函数
        init: function (options) {
            smart.SingleEditModule.fn.init.call(this, options);
        },

        //初始化组件
        initComponents: function () {

            //1.localCombo写法，需要配置JS的smart.Enums枚举
           /* smart.kendoui.comboBox(this.$("#edit_evalType"), {
                dataSource: smart.Enums["com.bycc.enumitem.EvaluateType"].getData()
            });

            smart.kendoui.comboBox(this.$("#edit_scoreType"), {
                dataSource: smart.Enums["com.bycc.enumitem.ScoreType"].getData()
                        }); */                       
 			 //评价标准列表
            var standardList = new kendo.data.DataSource({
                transport: {
                    read: {
                        url: basePath+"/bdmEvaluations/findAllScoreStandards.do",
                        dataType: "json"
                    }
                }
            });
            smart.kendoui.comboBox($("#edit_standard"), {
                dataTextField: "standard",
                dataValueField: "standard",
                dataSource: standardList,
                select: function (e){
                    var dataItem = this.dataItem(e.item);
                    if (dataItem) {
                      $("#edit_scoreType").val(dataItem.scoreType);
                      $("#edit_evalType").val(dataItem.evalType);
                    }
                }
            });            
        }
    });

    new EditModule({
        name: "SmartBdmEvaluationEdit", //必需，Edit模块名
        containerId: "ctnBdmEvaluationEdit", //必需，Edit模块的容器id
        restUrl: "/bdmEvaluations/", //必需，请求的rest地址
        modelName: "bdmEvaluation", //必需，model名
        model: { //必需，model用于MVVM
            bdmEvaluation: {
                id: "",
                standard: "",
                scoreType: "",
                score: "",
                evalType: "",
                note: ""
            }
        },
        ymlModule: "bdmEvaluation" //配置yml按钮显隐的模块名
    });
})();
