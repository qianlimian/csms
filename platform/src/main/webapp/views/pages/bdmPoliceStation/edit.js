(function () {

    var EditModule = smart.MultiEditModule.extend({

        //构造函数
        init: function (options) {
            smart.MultiEditModule.fn.init.call(this, options);
        },

        //初始化组件`
        initComponents: function () {

            //区域信息的下拉列表框
            smart.kendoui.comboBox("#edit_areaType", {
                dataSource: smart.Enums["com.bycc.enumitem.AreaType"].getData()
            });
            
            //警局级别的下拉列表框
            smart.kendoui.dropDownList("#edit_policeStationType", {
                dataSource: smart.Enums["com.bycc.enumitem.PoliceStationType"].getData()
            });

            //grid组件
            smart.kendoui.grid("#subGridEdit",
                $.extend(true, this.subGridOptions(), {
                    dataSource : { //可省略，默认方法findSubsById
                        url: this.restUrl + "findSubsById.do"
                    },
                    columns : [
                        { field: "id",  width: 100, hidden: true },
                        { field: "userId", width: 100, hidden: true },
                        { field: "userName", title: "民警", width: 100,
                            editor: smart.kendoui.nonEditor },
                        { field: "dutyType", title: "职务", width: 100,
                            values: smart.Enums["com.bycc.enumitem.DutyType"].getData(),
                            editor: function (container, options) {
                                // $('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '"/>').appendTo(container).kendoDropDownList ({
                                //     dataSource: smart.Enums["com.bycc.enumitem.DutyType"].getData(),
                                //     valuePrimitive: true
                                // });
                                var $input = $('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '"/>');
                                $input.appendTo(container);
                                smart.kendoui.dropDownList($input, {
                                    dataSource: smart.Enums["com.bycc.enumitem.DutyType"].getData()
                                });
                            }
                        }
                    ]
                })
            );
        },

        //@overwrite
        doNewSub: function () {
            //初始化选择window
            if (!this.userSelectWin) {
                this.userSelectWin = smart.kendoui.window('#ctnUserSelectWrap', {
                    width : '800px',
                    height : '500px',
                    content: basePath + "/smart/users/load.do"
                });
            }
            this.userSelectWin.center().open();
        },

        //子表新增行
        addNewUser: function (data) {
            var self = this;
            $.each(data.items, function (i, item) {
                var row = {
                    userId: item.id,
                    userName: item.name,
                    dutyType: 'JY'
                };
                self.subGridEdit.smartAddRow(row);
            });

            this.userSelectWin.close();
        },

        //绑定事件
        bindEvents: function () {
            var self = this;
            smart.MultiEditModule.fn.bindEvents.call(self);

            // 绑定选择用户处理事件
            smart.Event.bind('ITEM_SELECTED_EVENT', [this.addNewUser, this]);
        }

    });

    new EditModule({
        name: "SmartBdmPoliceStationEdit",
        containerId: "ctnBdmPoliceStationEdit",
        restUrl: "/bdmPoliceStations/",
        modelName: "bdmPoliceStation",
        model: {
            bdmPoliceStation: {
                id: "",
                code: "",
                name: "",
                areaType: "",
                address: ""
            }
        },
        ymlModule: "bdmPoliceStation"
    });

})();