(function () {

    var EditModule = smart.MultiEditModule.extend({

        //构造函数
        init: function (options) {
            smart.MultiEditModule.fn.init.call(this, options);
        },

        //初始化组件
        initComponents: function () {

            var self = this;

            //创建tabstrip
            self.tabstrip = $("#tabstripEdit").kendoTabStrip({
                animation: { open: { effects: "fadeIn"} }
            });

            smart.kendoui.comboBox(this.$("#edit_roomType"), {
                dataSource: smart.Enums["com.bycc.enumitem.RoomType"].getData()
            });
            smart.kendoui.comboBox(this.$("#edit_status"), {
                dataSource: smart.Enums["com.bycc.enumitem.UsageStatus"].getData()
            });

            //基站子表
            smart.kendoui.grid("#stationSubGridEdit",
                $.extend(true, this.subGridOptions(), {
                    dataSource : {
                        url: this.restUrl + "findSubStationsById.do"
                    },
                    toolbar: kendo.template(this.$("#station_template").html()),
                    command: {
                        onDelClick: this.can("delete") && function () {
                            return function (event) {
                                self.doDeleteSub.call(this, event);
                            }
                        }(self)
                    },
                    columns : [
                        { field: "id",  width: 100, hidden: true },
                        { field: "ip", title: "基站ip地址", width: 100 },
                        { field: "name", title: "基站名称", width: 100 },
                        { field: "status", title: "设备状态", width: 100,
                            values: smart.Enums["com.bycc.enumitem.DeviceStatus"].getData()
                        }
                    ]
                })
            );

            //摄像头子表
            smart.kendoui.grid("#cameraSubGridEdit",
                $.extend(true, this.subGridOptions(), {
                    dataSource : {
                        url: this.restUrl + "findSubCamerasById.do"
                    },
                    toolbar: kendo.template(this.$("#camera_template").html()),
                    command: {
                        onDelClick: this.can("delete") && function () {
                            return function (event) {
                                self.doDeleteSub.call(this, event);
                            }
                        }(self)
                    },
                    columns : [
                        { field: "id",  width: 100, hidden: true },
                        { field: "ip", title: "摄像头ip地址", width: 100 },
                        { field: "name", title: "摄像头名称", width: 100 },
                        { field: "username", title: "用户名", width: 100 },
                        { field: "password", title: "密码", width: 100 },
                        { field: "status", title: "设备状态", width: 100,
                            values: smart.Enums["com.bycc.enumitem.DeviceStatus"].getData()
                        }
                    ]
                })
            );

        },

        //@overwrite
        loadItem: function (item) {
            smart.EditModule.fn.loadItem.call(this, item);

            var modelName = this.modelName,
                viewModel = this.viewModel,
                grid = this.gridName;

            //--clear多个subGrid
            for (var i = 0; i < grid.length; i++) {
                this[grid[i]].clearData();
            }

            //新增
            if (!item) {
                return;
            }
            //编辑
            smart.ajax({
                type: 'get',
                url: this.restUrl + "findById.do?id=" + item.id,
                success: function (res) {
                    viewModel.set(modelName, res);
                }
            });

            //--read多个subGrid
            for (var i = 0; i < grid.length; i++) {
                this[grid[i]].dataSource.read({id: item.id});
            }
        },

        //@overwrite
        getGridData: function () {
            //--取多个subGrid的CudData
            var gridData = {};
            gridData["station"] = this["stationSubGridEdit"].getCudData();
            gridData["camera"] = this["cameraSubGridEdit"].getCudData();
            return gridData;
        },

        //@overwrite
        resetViewModel: function () {
            smart.EditModule.fn.resetViewModel.call(this);

            var indexModule = smart.Module.getModule("SmartBdmRoomIndex");

            this.viewModel.set("bdmRoom.handlingAreaId", indexModule.getHandlingAreaId());
        },

        //@overwrite
        initVars: function () {
            //--init多个subGrid
            var grid = this.gridName;
            for (var i = 0; i < grid.length; i++) {
                this[grid[i]] = $("#" + grid[i]).data("kendoGrid");
            }
        },

        //@overwrite
        bindEvents: function () {
            smart.EditModule.fn.bindEvents.call(this);

            smart.bind('#stationBtnDoNewSub', [this.doNewSub, this["stationSubGridEdit"]]);
            smart.bind('#stationBtnDoDeleteSub', [this.doDeleteSub, this["stationSubGridEdit"]]);
            smart.bind('#cameraBtnDoNewSub', [this.doNewSub, this["cameraSubGridEdit"]]);
            smart.bind('#cameraBtnDoDeleteSub', [this.doDeleteSub, this["cameraSubGridEdit"]]);
        },

        //@overwrite
        doNewSub: function () {
            //注意：传过来的this变量为grid！！！
            this.smartAddRow({
                status: 'NORMAL'
            });
        },

        //@overwrite
        doDeleteSub: function (event) {
            //注意：传过来的this变量为grid！！！
            this.smartRemoveRow(event);
        }

    });

    new EditModule({
        name: "SmartBdmRoomEdit", //必需，Edit模块名
        containerId: "ctnBdmRoomEdit", //必需，Edit模块的容器id
        restUrl: "/bdmRooms/", //必需，请求的rest地址
        grid: ['stationSubGridEdit', 'cameraSubGridEdit'],
        modelName: "bdmRoom", //必需，model名
        model: {
            bdmRoom: {
                id: "",
                code: "",
                name: "",
                roomType: "",
                status: "",
                handlingAreaId:""
            }
        },
        ymlModule: "bdmRoom" //配置yml按钮显隐的模块名
    });

})();