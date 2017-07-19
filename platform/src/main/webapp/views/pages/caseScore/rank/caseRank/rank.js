(function () {
    var DetailModule = smart.MultiIndexModule.extend({

        //构造函数
        init: function (options) {
            smart.MultiIndexModule.fn.init.call(this, options);
        },

        //初始化组件
        initComponents: function () {

            smart.kendoui.grid($("#mainGrid"),
                $.extend(true, this.mainGridOptions(), {
                    command: false,
                    height: 300,
                    columns: [
                        {field: "caseId", width: 100, hidden: true, sortable: false,},
                        {field: "caseName", type: "string", title: "案件名称", width: 100, mapping: "caze.caseName", sortable: false,},
                        {field: "masterUnitName", type: "string", title: "主办单位", width: 100, mapping: "caze.masterUnit.name", sortable: false,},
                        {field: "masterPoliceName", type: "string", title: "主办人", width: 100, mapping: "caze.masterPolice.user.name", sortable: false,},
                        {field: "slaveUnitName", type: "string", title: "协办单位", width: 100, mapping: "caze.slaveUnit.name", sortable: false,},
                        {field: "slavePoliceName", type: "string", title: "协办人", width: 100, mapping: "caze.slavePolice.user.name", sortable: false,},
                        {field: "totalScore", type: "number", title: "案件分数", width: 100, filterable: false}
                    ]
                })
            );

            smart.kendoui.grid($("#subGrid"),
                $.extend(true, this.subGridOptions(), {
                    height: 300,
                    columns: [
                        {field: "id", width: 100, hidden: true},
                        {field: "evalId", width: 100, hidden: true},
                        {field: "standard", title: "标准", width: 100},
                        {field: "score", title: "分数", width: 100}
                    ]
                })
            );
        },

        //@overwrite
        resizeLayout:function () {
            if (this.container.closest(".s-container").length > 0) {
                smart.MultiIndexModule.fn.resizeLayout.call(this);
            }
        }
    });

    new DetailModule({
        name: "SmartCaseScoreRank", //必需，Index模块名
        containerId: "ctnCaseScoreRank", //必需，Index模块的容器id
        restUrl: "/caseScores/rank/caseRank/",//必需，请求的rest地址
        editModule: {}
    });
})();
