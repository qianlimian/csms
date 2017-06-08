(function () {
    var IndexModule = smart.MultiIndexModule.extend({

		//构造函数
        init: function (options) {
            smart.MultiIndexModule.fn.init.call(this, options);
        },
        
		//初始化组件
        initComponents: function () {
            //调用父类方法初始化查询窗口、编辑页面、导航条等
            smart.MultiIndexModule.fn.initComponents.call(this);

            smart.kendoui.grid("#mainGrid",
                $.extend(true, this.mainGridOptions(), {
                    dataSource : { //可省略，默认方法query
                        url: this.restUrl + "query.do"
                    },
                    columns : [
                        { field: "id",  width: 100, hidden: true},
                        { field: "code", type: "string", title: "编码", width: 100 },
                        { field: "name", type: "string", title: "名称", width: 100 },
                        { field: "areaType", type: "enum", title: "地区", width: 100,
                            values: smart.Enums["com.bycc.enumitem.AreaType"].getData()
                        },
                        { field: "policeStationType", type: "enum", title: "级别", width: 100,
                            values: smart.Enums["com.bycc.enumitem.PoliceStationType"].getData()
                        },
                        { field: "ip", type: "string", title: "MSIP", width: 100 }
                    ]
                })
            );

            smart.kendoui.grid("#subGrid",
                $.extend(true, this.subGridOptions(), {
                    dataSource : { //可省略，默认方法findSubsById
                        url: this.restUrl + "findSubsById.do"
                    },
                    columns : [
                        { field: "id",  width: 100, hidden: true},
                        { field: "userId", width: 100, hidden: true },
                        { field: "userName", title: "民警", width: 100 },
                        { field: "dutyType", title: "职务", width: 100,
                            values: smart.Enums["com.bycc.enumitem.DutyType"].getData()
                        }
                    ]
                })
            );
        }

    });

    new IndexModule({
        name : "SmartBdmPoliceStationIndex",
        containerId : "ctnBdmPoliceStationIndex",
        restUrl: "/bdmPoliceStations/",
        editModule : {
            name : "SmartBdmPoliceStationEdit",
            containerId : "ctnBdmPoliceStationEditWrap"
        },
        ymlModule: "bdmPoliceStation"
    });
})();
