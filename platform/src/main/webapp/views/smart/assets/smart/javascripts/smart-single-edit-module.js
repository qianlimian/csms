/**
 * 单表表编辑页面模板，依赖 smart-edit-module.js
 * = require ./smart-edit-module.js
 */
(function(){

    if (!window.smart) {
        window.smart = {};
    }

    smart.SingleEditModule = smart.EditModule.extend({
		
		//构造函数
        init: function (options) {
            smart.EditModule.fn.init.call(this, options);
        },

		//载入当前item
        loadItem: function (item) {
            smart.EditModule.fn.loadItem.call(this, item);
            
            var modelName = this.modelName,
                viewModel = this.viewModel;

            if (item && item.id) {
                smart.ajax({
                    type: 'get',
                    url: this.restUrl + "findById.do?id=" + item.id,
                    success: function (res) {
                        viewModel.set(modelName, res);
                    }
                });
            }
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
                data = this.getModelData(),
                eventPrefix = this.eventPrefix || "";

            smart.ajax({
                url: this.restUrl + "save.do",
                type: 'post',
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
                    self.notification.hide().error('保存失败！');
                }
            });
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
                            url: self.restUrl + "delete.do?ids=" + viewModel[modelName].id,
                            success: function (res) {
                                //触发自定义事件
                                smart.Event(eventPrefix + "_ITEM_REMOVE_EVENT").fire();
                            }
                        });
                    }
                }]
            });

        }

    });
})();
