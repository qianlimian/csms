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

            //办案区索引grid
            smart.kendoui.grid("#indexGrid", {
                dataSource : {
                    url: basePath + "/bdmHandlingAreas/query.do"
                },
                columns : [
                    { field: "id",  width: 100, hidden: true},
                    { field: "code", type: "string", title: "编码", width: 100 },
                    { field: "name", type: "string", title: "名称", width: 100 }
                ],
                change: function () {
                    self.mainGrid.dataSource.read();
                },
                selectable: "single",
                pageable: {
                    pageSizes: [10 , 30 , 50 , 100 , 200],
                    refresh: false,
                    numeric: true,
                    input: false,
                    buttonCount: 10,
                    messages: {
                        previous: "上一页",
                        next: "下一页",
                        first: "第一页",
                        last: "最后一页",
                        display: " {0} - {1} /{2}条",
                        empty: "无数据",
                        page: "第",
                        of: "页/ {0} 页",
                        itemsPerPage: "条每页"
                    }
                }
            });

            //房间主表grid
            smart.kendoui.grid("#mainGrid",
                $.extend(true, this.mainGridOptions(), {
                    dataSource : {
                        url: this.restUrl + "query.do",
                        parameterMap: function () {
                            return { handlingAreaId : self.getHandlingAreaId() }
                        }
                    },
                    autoBind: false, //默认不查询
                    columns : [
                        { field: "id",  width: 100, hidden: true},
                        { field: "code", type: "string", title: "编码", width: 100 },
                        { field: "name", type: "string", title: "名称", width: 100 },
                        { field: "roomType", type: "enum", title: "房间类型", width: 100,
                            values: smart.Enums["com.bycc.enumitem.RoomType"].getData()
                        },
                        { field: "status", type: "enum", title: "房间状态", width: 100,
                            values: smart.Enums["com.bycc.enumitem.UsageStatus"].getData()
                        }
                    ],
                    change: function () {
                        var dataItem = this.dataItem(this.select());
                        if (dataItem) {
                            //read subGrid dataSource
                            self.stationSubGrid.dataSource.read({id: dataItem.id});
                            self.cameraSubGrid.dataSource.read({id: dataItem.id});
                        }
                    }
                })
            );

            //基站子表grid
            smart.kendoui.grid("#stationSubGrid",
                $.extend(true, this.subGridOptions(), {
                    dataSource : {
                        url: this.restUrl + "findSubStationsById.do"
                    },
                    columns : [
                        { field: "id",  width: 100, hidden: true},
                        { field: "ip", type: "string", title: "基站ip地址", width: 100 },
                        { field: "name", type: "string", title: "基站名称", width: 100 },
                        { field: "status", type: "string", title: "基站状态", width: 100,
                            values: smart.Enums["com.bycc.enumitem.DeviceStatus"].getData()
                        }
                    ]
                })
            );

            //摄像头子表grid
            smart.kendoui.grid("#cameraSubGrid",
                $.extend(true, this.subGridOptions(), {
                    dataSource : {
                        url: this.restUrl + "findSubCamerasById.do"
                    },
                    columns : [
                        { field: "id",  width: 100, hidden: true},
                        { field: "ip", type: "string", title: "摄像头ip地址", width: 100 },
                        { field: "name", type: "string", title: "摄像头名称", width: 100 },
                        { field: "username", type: "string", title: "用户名", width: 100 },
                        { field: "password", type: "string", title: "密码", width: 100 },
                        { field: "status", type: "string", title: "设备状态", width: 100,
                            values: smart.Enums["com.bycc.enumitem.DeviceStatus"].getData()
                        }
                    ]
                })
            );

        },

        //取选择的办案区ID
        getHandlingAreaId: function () {
            var $row = this.indexGrid.select(),
                dataItem = this.indexGrid.dataItem($row);

            return dataItem? dataItem.id : null;
        },


        //@overwrite
        doNew: function () {
            var handlingAreaId = this.getHandlingAreaId();
            if (!handlingAreaId) {
                smart.alert("请选择办案区");
                return;
            }
            //调用父类方法
            smart.MultiIndexModule.fn.doNew.call(this);
        },

        //@overwrite
        doEdit: function () {
            var handlingAreaId = this.getHandlingAreaId();
            if (!handlingAreaId) {
                smart.alert("请选择办案区");
                return;
            }
            //调用父类方法
            smart.MultiIndexModule.fn.doEdit.call(this);
        },

        //@overwrite
        initVars: function () {
            this.indexGrid = $("#indexGrid").data("kendoGrid");
            this.mainGrid = $("#mainGrid").data("kendoGrid");
            this.stationSubGrid = $("#stationSubGrid").data("kendoGrid");
            this.cameraSubGrid = $("#cameraSubGrid").data("kendoGrid");
        },

        //@overwrite
        resizeLayout:function () {
            var $indexGrid = this.indexGrid.wrapper,
                $mainGrid = this.mainGrid.wrapper,
                $stationSubGrid = this.stationSubGrid.wrapper,
                $cameraSubGrid = this.cameraSubGrid.wrapper,
                height = $(window).height() - $mainGrid.offset().top,
                gridHeight = height - 50;

            $indexGrid.height(height);
            $mainGrid.height(gridHeight*3/4);
            $stationSubGrid.height(gridHeight/4);
            $cameraSubGrid.height(gridHeight/4);

            smart.kendoui.fixGridHeight($indexGrid, $mainGrid, $stationSubGrid, $cameraSubGrid);
        }

    });
    new IndexModule({
        name : "SmartBdmRoomIndex",
        containerId : "ctnBdmRoomIndex",
        restUrl: "/bdmRooms/",
        grid: ['mainGrid', 'stationSubGrid', 'cameraSubGrid'],
        editModule : {
            name : "SmartBdmRoomEdit",
            containerId : "ctnBdmRoomEditWrap",
            options: {
                width : '800px',
                height : '500px'
            }
        },
        ymlModule: "bdmRoom"
    });
})();
