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
                    // dataSource : { //可省略，默认方法query
                    //     url: this.restUrl + "query.do"
                    // },
                    columns : [
                        { field: "id",  width: 100, hidden: true},
                        { field: "caseCode", type: "string", title: "案件编号", width: 100, mapping: "caze.caseCode" },
                        { field: "caseName", type: "string", title: "案件名称", width: 100, mapping: "caze.caseName" },
                        { field: "note", type: "string", title: "告警信息", width: 100 },
                        { field: "caseType", type: "enum", title: "案件类型", width: 100 ,
                          values: smart.Enums["com.bycc.enumitem.CaseType"].getData(), mapping: "caze.caseType"},
                        { field: "policeStationName", type: "string", title: "主办单位", width: 100, mapping: "caze.masterUnit.name"},
                        { field: "policeName", type: "string", title: "主办人", width: 100, mapping: "caze.masterPolice.user.name"},
                        { field: "caseStatus", type: "enum", title: "案件状态", width: 100,
                          values: smart.Enums["com.bycc.enumitem.CaseStatus"].getData(), mapping: "caze.caseStatus"},
                        { field: "updateTime", type: "date", title: "更新时间", width: 100, format: '{0:yyyy-MM-dd}', mapping: "updateDate"}
                    ]
                })
            );
        },
        gridOptions: function () {
            var self = this;
            return {
                height: 940,
                dataSource : {
                    url: self.restUrl + "query.do"
                }
            }
        }
    });

    new IndexModule({
        name : "SmartCaseWarningIndex", //必需，Index模块名
        containerId : "ctnCaseWarningIndex", //必需，Index模块的容器id
        restUrl: "/caseWarnings/", //必需，请求的rest地址
        editModule: {},
        ymlModule: "caseWarning"
    });
})();
