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
                    filterable: false,
                    sortable: false,
                    height: 300,
                    columns: [
                        {field: "caseName", type: "string", title: "案件名称", width: 100},
                        {field: "masterPoliceName", title: "主办人", width: 100},
                        {field: "slavePoliceName", title: "协办人", width: 100},
                        {field: "totalScore", type: "number", title: "案件分数", width: 100}
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

        loadItem: function (item) {
            var dataSource = this.mainGrid.dataSource;
            dataSource.transport.parameterMap = function (model, type) {
                return {
                    policeId: item.policeId,
                    queryBean: JSON.stringify(model)
                }
            };
            dataSource.read();
            this.subGrid.clearData();
        },

        //@overwrite
        resizeLayout:function () {
            if (this.container.closest(".s-container").length > 0) {
                smart.MultiIndexModule.fn.resizeLayout.call(this);
            }
        }
    });

    new DetailModule({
        name: "SmartPoliceScoreDetail", //必需，Index模块名
        containerId: "ctnCaseScoreDetail", //必需，Index模块的容器id
        restUrl: "/caseScores/",//必需，请求的rest地址
        editModule: {}
    });
})();
