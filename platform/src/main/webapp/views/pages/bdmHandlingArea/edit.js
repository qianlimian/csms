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
            
            //物品柜子表
            smart.kendoui.grid("#cabinSubGridEdit",
                $.extend(true, this.subGridOptions(), {
                    dataSource : {
                        url: this.restUrl + "findSubCabinetsById.do"
                    },
                    toolbar: kendo.template(this.$("#cabin_template").html()),
                    command: {
                        onDelClick: this.can("delete") && function () {
                            return function (event) {
                                self.doDeleteSub.call(this, event);
                            }
                        }(self)
                    },
                    columns : [
                        { field: "id",  width: 100, hidden: true },
                        { field: "code", title: "编号", width: 100 },
                        { field: "name", title: "名称", width: 100 },
                        { field: "status", title: "使用状态", width: 100,
                            values: smart.Enums["com.bycc.enumitem.UsageStatus"].getData()
                        }
                    ]
                })
            );

            //手环子表
            smart.kendoui.grid("#strapSubGridEdit",
                $.extend(true, this.subGridOptions(), {
                    dataSource : {
                        url: this.restUrl + "findSubStrapsById.do"
                    },
                    toolbar: kendo.template(this.$("#strap_template").html()),
                    command: {
                        onDelClick: this.can("delete") && function () {
                            return function (event) {
                                self.doDeleteSub.call(this, event);
                            }
                        }(self)
                    },
                    columns : [
                        { field: "id",  width: 100, hidden: true },
                        { field: "code", title: "编号", width: 100 },
                        { field: "name", title: "名称", width: 100 },
                        { field: "status", title: "使用状态", width: 100,
                            values: smart.Enums["com.bycc.enumitem.UsageStatus"].getData()
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
            gridData["cabin"] = this["cabinSubGridEdit"].getCudData();
            gridData["strap"] = this["strapSubGridEdit"].getCudData();
            return gridData;
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

            //选择公安机关
            smart.bind('#btnDoPick', [this.doPickPs, this]);
            smart.Event.bind('ITEM_SELECTED_EVENT', [this.itemSelectedHandler, this]);

            smart.bind('#cabinBtnDoNewSub', [this.doNewSub, this["cabinSubGridEdit"]]);
            smart.bind('#cabinBtnDoDeleteSub', [this.doDeleteSub, this["cabinSubGridEdit"]]);
            smart.bind('#strapBtnDoNewSub', [this.doNewSub, this["strapSubGridEdit"]]);
            smart.bind('#strapBtnDoDeleteSub', [this.doDeleteSub, this["strapSubGridEdit"]]);
        },

        //@overwrite
        doNewSub: function () {
            //注意：传过来的this变量为grid！！！
            this.smartAddRow({
                status: 'UNUSED'
            });
        },

        //@overwrite
        doDeleteSub: function (event) {
            //注意：传过来的this变量为grid！！！
            this.smartRemoveRow(event);
        },

        //选择派出所
        doPickPs: function () {
            //初始化选择window
            if (!this.psSelectWin) {
                this.psSelectWin = smart.kendoui.window('#ctnPsSelectWrap', {
                    width: '800',
                    height: '500',
                    content: basePath + "/bdmPoliceStations/load.do"
                });
            }
            this.psSelectWin.center().open();
        },

        //选择派出所回调
        itemSelectedHandler: function (data) {
            this.viewModel.set("bdmHandlingArea.policeStationId", data.id);
            this.viewModel.set("bdmHandlingArea.policeStationName", data.name);
            this.psSelectWin.close();
        }

    });

    new EditModule({
        name: "SmartBdmHandlingAreaEdit", //必需，Edit模块名
        containerId: "ctnBdmHandlingAreaEdit", //必需，Edit模块的容器id
        restUrl: "/bdmHandlingAreas/", //必需，请求的rest地址
        grid: ['cabinSubGridEdit', 'strapSubGridEdit'],
        modelName: "bdmHandlingArea", //必需，model名
        model: {
            bdmHandlingArea: {
                id: "",
                code: "",
                name: "",
                policeStationId: "",
                policeStationName: "",
                note: ""
            }
        },
        ymlModule: "bdmHandlingArea" //配置yml按钮显隐的模块名
    });

})();