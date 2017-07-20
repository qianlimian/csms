(function () {
    var IndexModule = smart.SingleIndexModule.extend({

        //构造函数
        init: function (options) {
            smart.SingleIndexModule.fn.init.call(this, options);
        },

        //供【案件】和【案件记录】模块调用的接口
        loadItems: function (caseRecordId, caseId) {
            var me = this,
                grid = me.mainGrid,
                url = me.restUrl + "query.do";

            if (caseRecordId) {
                url = url + "?caseRecordId=" + caseRecordId;
            }
            if (caseId) {
                url = url + "?caseId=" + caseId;
            }

            grid.dataSource.transport.options.read.url = url;
            grid.dataSource.read();
        },

        //初始化组件
        initComponents: function () {
            //调用父类方法初始化查询窗口、编辑页面、导航条等
            smart.SingleIndexModule.fn.initComponents.call(this);

            var me = this;

            // 涉案人员列表
            this.mainGrid = smart.kendoui.grid(this.$("#peopleGrid"),
                $.extend(true, this.gridOptions(), {
                        autoBind: false,
                        columns: [
                            {field: "id", width: 50, hidden: true},
                            {field: "name", type: "string", title: "姓名", width: 100},
                            {field: "address", type: "string", title: "家庭住址", width: 100},
                            {field: "strapId", type: "int", title: "手环id", width: 100, hidden: true},
                            {field: "strapName", type: "string", title: "手环", width: 100}
                        ],
                        filterable: false,
                        change: function () {
                            // 查询随身物品
                            me.queryPeopleBelongs();
                            // 查询人员详细信息(人身安全检查、信息采集)
                            me.queryPeopleInspect();
                        }
                    }
                )
            );

            // 随身物品列表
            this.subGrid = smart.kendoui.grid(this.$("#peopleBelongsGrid"), {
                    autoBind: false,
                    dataSource: {
                        url: this.restUrl + "findPeopleBelongs.do"
                    },
                    toolbar: kendo.template($("#subtemplate").html()),
                    columns: [
                        {field: "id", width: 100, hidden: true},
                        {field: "name", title: "物品名称", width: 100},
                        {field: "description", title: "物品特征", width: 150},
                        {field: "count", title: "数量", width: 150},
                        {field: "unit", title: "单位", width: 150},
                        {
                            field: "storeType", title: "保管措施", width: 150,
                            values: smart.Enums["com.bycc.enumitem.StoreType"].getData(),
                            editor: function (container, options) {
                                var $input = $('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '"/>');
                                $input.appendTo(container);
                                smart.kendoui.dropDownList($input, {
                                    dataSource: smart.Enums["com.bycc.enumitem.StoreType"].getData()
                                });
                            }
                        },
                        {
                            field: "backOrNot", title: "是否归还", width: 150,
                            values: smart.Enums["com.bycc.enumitem.Boolean"].getData(),
                            editor: function (container, options) {
                                var $input = $('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '"/>');
                                $input.appendTo(container);
                                smart.kendoui.dropDownList($input, {
                                    dataSource: smart.Enums["com.bycc.enumitem.Boolean"].getData()
                                });
                            }
                        },
                        {
                            field: "involvedOrNot", title: "是否涉案", width: 150,
                            values: smart.Enums["com.bycc.enumitem.Boolean"].getData(),
                            editor: function (container, options) {
                                var $input = $('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '"/>');
                                $input.appendTo(container);
                                smart.kendoui.dropDownList($input, {
                                    dataSource: smart.Enums["com.bycc.enumitem.Boolean"].getData()
                                });
                            }
                        },
                        {
                            field: "cabinetId", title: "保管柜号", width: 150,
                            mapping: "cabinet.id", // mapping用于foreignkey column的映射，以转换过滤和排序参数
                            values: smart.Data.get('#cabinet_select'),
                            editor: function (container, options) {
                                var $input = $('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '"/>');
                                $input.appendTo(container);
                                smart.kendoui.comboBox($input, {
                                    dataSource: smart.Data.get("#cabinet_select")
                                });
                            }
                        }
                    ],
                    command: {
                        onDelClick: function () {
                            return function (event) {
                                me.doDeleteBelong.call(me, event)
                            }
                        }(me)
                    },
                    editable: "inline",
                    filterable: false,
                    selectable: 'single'
                }
            );

            this.bindViewModel();
        },

        //数据绑定
        bindViewModel: function () {
            this.viewModel = kendo.observable(this.model);
            kendo.bind(this.container, this.viewModel);
        },

        //绑定事件
        bindEvents: function () {
            //调用父类方法绑定增删改查
            smart.SingleIndexModule.fn.bindEvents.call(this);

            // 绑定手环按钮
            smart.bind('#' + this.containerId + ' #btnBindStrap', [this.showBindStrapPage, this]);
            // 解绑手环按钮
            smart.bind('#' + this.containerId + ' #btnUnbindStrap', [this.showUnbindStrapPage, this]);
            // 增加随身物品
            smart.bind('#' + this.containerId + ' #btnDoAddBelong', [this.doAddBelong, this]);
            // 保存随身物品
            smart.bind('#' + this.containerId + ' #btnDoSaveBelong', [this.doSaveBelong, this]);
            // 返还随身物品
            smart.bind('#' + this.containerId + ' #btnDoReturnBelong', [this.doReturnBelong, this]);
            // 保存人身安全检查
            smart.bind('#' + this.containerId + ' #btnDoSavePeopleInspect', [this.doSavePeopleInspect, this]);
            // 查看办案区登记表
            smart.bind('#' + this.containerId + ' #btnDoPrint', [this.doPrint, this]);

            // 手环绑定事件处理
            smart.Event.bind('STRAP_BIND_EVENT', [this.strapBindHandler, this]);
            smart.Event.bind('STRAP_UNBIND_EVENT', [this.strapUnbindHandler, this]);
        },

        //取选择的涉案人员
        getSelectItem: function () {
            var $row = this.mainGrid.select(),
                item = this.mainGrid.dataItem($row);
            if (!item) smart.alert("请选择涉案人员");
            return item;
        },

        parse: function (json){
        console.log(json);
    },
        //********************************绑定、解绑手环*********************************
        // 显示绑定手环页面
        showBindStrapPage: function () {
            var selectedItem = this.getSelectItem();
            if (selectedItem) {
                this.bindStrapWindow = smart.kendoui.window('#ctnBindStrapWrap', {
                    title: '选取手环',
                    content: basePath + "/caseRegister/bindStrap/load.do",
                    width: "50%",
                    height: "50%"
                });
                this.bindStrapWindow.center().open();
            }
        },

        // 显示解绑手环页面
        showUnbindStrapPage: function () {
            var selectedItem = this.getSelectItem();
            if (selectedItem) {
                this.unbindStrapWindow = smart.kendoui.window('#ctnUnbindStrapWrap', {
                    title: '离开办案区',
                    content: basePath + "/caseRegister/unbindStrap/load.do",
                    width: "50%",
                    height: "50%"
                });
                this.unbindStrapWindow.center().open();
            }
        },

        // 选取手环
        strapBindHandler: function (item) {
            var me = this,
                selectedItem = this.getSelectItem();
            if (selectedItem) {
                smart.ajax({
                    type: "GET",
                    url: "caseRegister/bindStrap.do?casePeopleId=" + selectedItem.id + "&strapId=" + item.id,
                    success: function (res) {
                        if (res.success) {
                            me.notification.hide().success({ message: "绑定成功！" });
                        } else {
                            me.notification.hide().error({ message: res.errorInfo });
                        }
                        me.bindStrapWindow.close();
                        me.doQuery();
                    }
                });
            }
        },

        // 解绑手环
        strapUnbindHandler: function (data) {
            var me = this,
                selectedItem = this.getSelectItem();
            if (selectedItem) {
                data.id = selectedItem.id;
                smart.ajax({
                    type: "POST",
                    url: "caseRegister/unBindStrap.do",
                    contentType: 'application/json',
                    data: JSON.stringify(data),
                    success: function (res) {
                        me.notification.hide().success({ message: "保存成功！" });
                        me.unbindStrapWindow.close();
                        me.doQuery();
                    }
                });
            }
        },

		//查看办案区登记表
        doPrint:function(){
        	var selectedItem = this.getSelectItem();
            if (selectedItem) {
            	//查看窗口
                smart.kendoui.window('#ctnPreviewWrap', {
                	content: basePath + "/caseRegister/print.do?casePeopleId=" + selectedItem.id,
                    title: "打印办案区使用登记表",
                    width: "80%",
                    height: "80%",
                    iframe: true
                }).maximize().open();
            }
        },
        //********************************绑定、解绑手环*************************************

        //********************************人身安全检查和信息采集********************************
        // 查询人身安全检查
        queryPeopleInspect: function () {
            var me = this,
                selectedItem = this.getSelectItem();
            if (selectedItem) {
                smart.ajax({
                    type: "GET",
                    url: me.restUrl + "findPeopleInspect.do?casePeopleId=" + selectedItem.id,
                    success: function (res) {
                        // 清空原有数据
                        me.viewModel.set("inspect", me.model.inspect);
                        // 更新人身安全检查数据
                        me.viewModel.set("inspect", res);
                    }
                });
            }
        },

        // 保存人身安全检查
        doSavePeopleInspect: function () {
            var me = this,
                selectedItem = this.getSelectItem();
            if (selectedItem) {
                var data = this.viewModel.inspect.toJSON();
                data.casePeopleId = selectedItem.id;

                smart.ajax({
                    type: "POST",
                    url: me.restUrl + "savePeopleInspect.do",
                    data: JSON.stringify(data),
                    contentType: 'application/json',
                    success: function (res) {
                        if (res.success) {
                            me.notification.hide().success({ message: "保存成功！" });
                        } else {
                            me.notification.hide().error({ messsage: res.errorInfo });
                        }
                    }
                });
            }
        },
        //*****************************人身安全检查和信息采集**************************

        //********************************随身物品********************************
        // 查询随身物品
        queryPeopleBelongs: function () {
            var selectedItem = this.getSelectItem();
            if (selectedItem) {
                this.subGrid.dataSource.transport.parameterMap = function (model, type) {
                    return {
                        casePeopleId: selectedItem.id,
                        queryBean: JSON.stringify(model)
                    }
                };
                this.subGrid.clearData();
                this.subGrid.dataSource.read();
            }
        },

        // 添加随身物品
        doAddBelong: function () {
            var selectedItem = this.getSelectItem();
            if (selectedItem)
                this.subGrid.smartAddRow();
        },

        // 删除随身物品
        doDeleteBelong: function (event) {
            var selectedItem = this.getSelectItem();
            if (selectedItem)
                this.subGrid.smartRemoveRow(event);
        },

        // 保存随身物品
        doSaveBelong: function () {
            var me = this,
                selectedItem = this.getSelectItem();
            if (selectedItem) {
                var data = $.extend({casePeopleId: selectedItem.id}, this.subGrid.getCudData());
                smart.ajax({
                    type: "POST",
                    url: me.restUrl + "savePeopleBelongs.do",
                    contentType: 'application/json',
                    data: JSON.stringify(data),
                    success: function (res) {
                        // 刷新grid
                        me.queryPeopleBelongs();
                        me.notification.hide().success({ message: "保存成功！" });
                    },
                    error: function (jqXHR) {
                       var msg = smart.ajax.formatError(jqXHR);

                       if (jqXHR.status == 632) { //校验错误
                           me.showValidateErrors(msg["smart_validator"]);
                       } else {
                           smart.alert(msg);
                       }

                       //提示
                       me.notification.hide().error({ message: '保存失败！' });
                   }
                });
            }
        },

        // 返还随身物品
        doReturnBelong: function () {
            var me = this,
                selectedIds = this.subGrid.selectData().ids;
            if (!selectedIds.length) {
                smart.alert('请选择要返还的物品');
                return;
            }
            smart.confirm({
                message: "您确定要返还随身物品吗?",
                buttons: [{
                    click: function () {
                        smart.ajax({
                            type: 'PUT',
                            url: me.restUrl + "returnBelongs.do?belongIds=" + selectedIds,
                            success: function (res) {
                                //触发自定义事件
                                me.queryPeopleBelongs();
                                me.notification.hide().success({ message: "保存成功！" });
                            }
                        });
                    }
                }]
            });
        },
        //********************************随身物品********************************

        //grid自适应高度
        resizeLayout: function () {
            var $form = this.$("#inspectForm"),
                $grid = this.$("#peopleGrid"),
                $subGrid = this.$("#peopleBelongsGrid");

            $form.height(440);
            $grid.height(440);
            $subGrid.height(480);

            smart.kendoui.fixGridHeight($grid);
            smart.kendoui.fixGridHeight($subGrid);
        },

        //显示校验错误
        showValidateErrors: function (validators) {
            this.clearErrorNotices();

            for (var i = 0; i < validators.length; i++) {
                var validator = validators[i],
                    type = validator["type"],
                    errors = validator["errors"];

                //grid校验错误提示
                if (type == "grid") {
                    this.subGrid.showInvalidErrors(errors);
                }
            }

            this.initErrorNotices();
        },

        //初始化错误提示
        initErrorNotices: function () {
            this.container.kendoTooltip({
                filter: '.s-field-invalid',
                content: function (e) {
                    return e.target.attr('error_notice');
                },
                position: "bottom"
            });
        },

        //清除错误提示
        clearErrorNotices: function () {
            var errorNotice = this.container.data("kendoTooltip");
            if (errorNotice) errorNotice.destroy();

            $('.s-field-invalid').each(function () {
                $(this).removeClass("s-field-invalid").removeAttr("error_notice");
            })
        }
    });

    new IndexModule({
        name: "SmartCaseRegisterIndex", //必需，Index模块名
        containerId: "ctnCaseRegisterIndex", //必需，Index模块的容器id
        restUrl: "/caseRegister/", //必需，请求的rest地址
        grid: ["peopleGrid", "peopleBelongsGrid"],
        editModule: {
            name: "SmartCaseRegisterEdit", //必需，Edit模块名
            containerId: "ctnCaseRegisterEditWrap", //必需，Edit模块的容器id
            options: {
                width : '800px',
                height : '400px'
            }
        },
        model: {
            inspect: {
                id: "",
                statement: '',
                inspection: '',
                storeOrNot: '',
                verifyOrNot: '',
                collectOrNot: '',
                collectItems: []
            }
        },
        eventPrefix: "REGISTER"
    });
})();
