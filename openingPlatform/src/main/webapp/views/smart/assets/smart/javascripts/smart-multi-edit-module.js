/**
 * 主子表编辑页面模板，依赖 smart-edit-module.js
 * = require ./smart-edit-module.js
 */
(function () {

    if (!window.smart) {
        window.smart = {};
    }

    smart.MultiEditModule = smart.EditModule.extend({

		//构造函数
        init: function (options) {

            if (typeof(options.grid) == "undefined") {
                this.gridName = ["subGridEdit"];
            } else if (typeof(options.grid) == "string") {
                this.gridName = [options.grid];
            } else {
                this.gridName = options.grid;
            }

            smart.EditModule.fn.init.call(this, options);
        },

		//载入当前主表item，并刷新子表
        loadItem: function (item) {
            smart.EditModule.fn.loadItem.call(this, item);
            var modelName = this.modelName,
                viewModel = this.viewModel;

            this.subGridEdit.clearData();

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
            this.subGridEdit.dataSource.read({id: item.id});
        },

		//新增
        doNew: function () {
            //事件名前缀
            var eventPrefix = this.eventPrefix || "";
            //触发自定义事件
            smart.Event(eventPrefix + '_ITEM_ADD_EVENT').fire();

            this.$("#btnDoNew").hide();
        },

		//保存
        doSave: function () {
            var self = this,
                formData = this.getModelData(),
                gridData = this.getGridData(),
                data = $.extend({}, formData, gridData),
                eventPrefix = this.eventPrefix || "";

            smart.ajax({
                url: this.restUrl + "save.do",
                type: "post",
                //springmvc对于解析默认的application/x-www-form-urlencoded比较弱
                //存在对象（数组）嵌套时推荐用application/json提交，然后后台用@requestBody接收
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: function (res) {
                    //触发自定义事件
                    smart.Event(eventPrefix + '_ITEM_SAVE_EVENT', res).fire();
                },
                error: function (jqXHR) {
                    var msg = smart.ajax.formatError(jqXHR);

                    if (jqXHR.status == 632) { //校验错误
                        self.showValidateErrors(msg["smart_validator"]);
                    } else {
                        smart.alert(msg);
                    }

                    //提示
                    self.notification.hide();
                    self.notification.error({ message: '保存失败！' });
                }
            });
        },

        //获取子表增删改数据
        getGridData: function () {
            return this.subGridEdit.getCudData();
        },

		//删除
        doDelete: function () {
            var self = this,
                modelName = this.modelName,
                viewModel = this.viewModel,
                eventPrefix = this.eventPrefix || "";

            if (this.isNewItem()) {
                //触发自定义事件
                smart.Event(eventPrefix + "_ITEM_REMOVE_EVENT").fire();
                return;
            }

            smart.confirm({
                message: "您确定要删除这条记录?",
                buttons: [{
                    click: function () {
                        smart.ajax({
                            type: 'delete',
                            dataType: 'text', //@ResponseBody返回null
                            url: this.restUrl + "delete.do?ids=" + viewModel[modelName].id,
                            success: function (res) {
                                //触发自定义事件
                                smart.Event(eventPrefix + "_ITEM_REMOVE_EVENT").fire();
                            }
                        });
                    }
                }]
            });
        },

        //默认的subGrid参数
        subGridOptions: function () {
            var self = this;
            return {
                height: 300,
                dataSource : {
                    url: self.restUrl + "findSubsById.do"
                },
                toolbar: self.$("#template").length == 0? false : kendo.template(self.$("#template").html()),
                command: {
                    onDelClick: this.can("delete") && function () {
                        return function (event) {
                            self.doDeleteSub.call(self, event);
                        }
                    }(self)
                },
                autoBind: false,
                editable: true,
                filterable: false,
                groupable: false,
                pageable: false
            }
        },

		//子grid新增
        doNewSub: function () {
            this.subGridEdit.smartAddRow();
        },

		//子grid删除
        doDeleteSub: function (event) {
            this.subGridEdit.smartRemoveRow(event);
        },

		//初始化变量
        initVars: function () {
            this.subGridEdit = $("#" + this.gridName[0]).data("kendoGrid");
        },

		//绑定事件
        bindEvents: function () {
            smart.EditModule.fn.bindEvents.call(this);
            smart.bind('#' + this.containerId + ' #btnDoNewSub', [this.doNewSub, this]);
            smart.bind('#' + this.containerId + ' #btnDoDeleteSub', [this.doDeleteSub, this]);
        }

    });
})();
