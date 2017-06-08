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

            var self = this;

            //创建tabstrip
            self.tabstrip = $("#tabstrip").kendoTabStrip({
                animation: { open: { effects: "fadeIn"} }
            });

            //主表
            smart.kendoui.grid("#mainGrid",
                $.extend(true, this.mainGridOptions(), {
                    dataSource : {
                        url: this.restUrl + "query.do"
                    },
                    columns : [
                        { field: "id",  width: 100, hidden: true},
                        { field: "code", type: "string", title: "编码", width: 100 },
                        { field: "name", type: "string", title: "名称", width: 100 },
                        { field: "policeStationName", type: "string", title: "名称", width: 100, mapping: "policeStation.name" }
                    ],
                    change: function () {
                        var dataItem = this.dataItem(this.select());
                        if (dataItem) {
                            //read多个subGrid
                            var grid = self.gridName;
                            for (var i = 1; i < grid.length; i++) {
                                self[grid[i]].dataSource.read({id: dataItem.id});
                            }
                        }
                    }
                })
            );

            //物品柜子表
            smart.kendoui.grid("#cabinSubGrid",
                $.extend(true, this.subGridOptions(), {
                    dataSource : {
                        url: this.restUrl + "findSubCabinetsById.do"
                    },
                    columns : [
                        { field: "id",  width: 100, hidden: true},
                        { field: "code", type: "string", title: "编号", width: 100 },
                        { field: "name", type: "string", title: "名称", width: 100 },
                        { field: "status", type: "enum", title: "使用状态", width: 100,
                            values: smart.Enums["com.bycc.enumitem.UsageStatus"].getData()
                        }
                    ]
                })
            );

            //手环子表
            smart.kendoui.grid("#strapSubGrid",
                $.extend(true, this.subGridOptions(), {
                    dataSource : {
                        url: this.restUrl + "findSubStrapsById.do"
                    },
                    columns : [
                        { field: "id",  width: 100, hidden: true},
                        { field: "code", type: "string", title: "编号", width: 100 },
                        { field: "name", type: "string", title: "名称", width: 100 },
                        { field: "status", type: "enum", title: "使用状态", width: 100,
                            values: smart.Enums["com.bycc.enumitem.UsageStatus"].getData()
                        }
                    ]
                })
            );
        },

        //@overwrite
        initVars: function () {
            smart.IndexModule.fn.initVars.call(this);

            //--init多个subGrid
            var grid = this.gridName;
            for (var i = 1; i < grid.length; i++) {
                this[grid[i]] = $("#" + grid[i]).data("kendoGrid");
            }
        },

        //@overwrite
        resizeLayout:function () {
            var $mainGrid = this.mainGrid.wrapper,
                height = $(window).height() - $mainGrid.offset().top - 50;

            $mainGrid.height(height*3/4);
            smart.kendoui.fixGridHeight($mainGrid);

            //--resize多个subGrid
            var grid = this.gridName;
            for (var i = 1; i < grid.length; i++) {
                var $subGrid = this[grid[i]].wrapper;
                $subGrid.height(height/4);
                smart.kendoui.fixGridHeight($subGrid);
            }
        }

    });

    new IndexModule({
        name : "SmartBdmHandlingAreaIndex",
        containerId : "ctnBdmHandlingAreaIndex",
        restUrl: "/bdmHandlingAreas/",
        grid: ['mainGrid', 'cabinSubGrid', 'strapSubGrid'],
        editModule : {
            name : "SmartBdmHandlingAreaEdit",
            containerId : "ctnBdmHandlingAreaEditWrap",
            options: {
                width : '800px',
                height : '500px'
            }
        },
        ymlModule: "bdmHandlingArea"
    });
})();
