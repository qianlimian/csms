(function () {

    var SelectModule = smart.SelectModule.extend({

        //构造函数
        init: function (options) {
            smart.SelectModule.fn.init.call(this, options);
        },

        //初始化组件
        initComponents: function () {
            var self = this;

            smart.kendoui.grid(this.$("#mainGrid"),
                $.extend(true, this.gridOptions(), {
                    columns : [
                        { field: "id",  width: 100, hidden: true},
                        { field: "code", type: "string", title: "编码", width: 100 },
                        { field: "name", type: "string", title: "名称", width: 150 },
                        { field: "areaType", type: "string", title: "地区", width: 100,
                            values: smart.Enums["com.bycc.enumitem.AreaType"].getData()
                        },
                        { field: "policeStationType", type: "enum", title: "级别", width: 100,
                            values: smart.Enums["com.bycc.enumitem.PoliceStationType"].getData()
                        }
                    ],
                    selectable: 'single'
                })
            )
        },

        //@overwrite
        bindEvents: function () {
            var self = this;
            //绑定选择事件
            smart.bind("#" + this.containerId + " #btnDoSelect", function () {
                var $row = self.mainGrid.select(),
                    data = self.mainGrid.dataItem($row);
                smart.Event('ITEM_SELECTED_EVENT', data).fire();
            });
            //绑定选择回调事件
            self.mainGrid.bind('smartRowDblClick', function (e) {
                var data = e.data.rowData;
                smart.Event('ITEM_SELECTED_EVENT', data).fire();
            })
        }

    });

    new SelectModule({
        name : "SmartPoliceStationSelect", //必需，模块名
        containerId : "ctnPoliceStationSelect", //必需，模块的容器id
        restUrl: "/bdmPoliceStations/" //必需，请求的rest地址
    });

})();