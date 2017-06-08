/**
 * 编辑页面模块，依赖 smart-module.js
 * = require ./smart-module.js
 */
(function () {

    if (!window.smart) {
        window.smart = {};
    }
    /**
     * 执行顺序为： init -> ready { bindViewModel -> initComponents -> initVars -> bindEvents }
     */
    smart.EditModule = smart.Module.extend({

		//构造函数
        init: function (options) {
            var self = this;
            $.each(["modelName", "model"], function (index, item) {
                if (!options[item]) {
                    alert("smart.EditModule创建失败，缺少必要参数：" + item);
                }
            });

            smart.Module.fn.init.call(this, options);
        },

        //新增？编辑
        isNewItem: function () {
            var modelName = this.modelName,
                viewModel = this.viewModel;
            return !(viewModel[modelName].id);
        },

        doNew: function () {
            //在子类实现
        },

        doSave: function () {
            //在子类实现
        },

        doDelete: function () {
            //在子类实现
        },

        loadItem: function (item) {
            this.clearErrorNotices();
            // 新增
            if (!item) {
                this.resetViewModel();
                this.$('#btnDoNew').hide();
                return;
            }
            //编辑
            this.$("#btnDoNew").show();
        },

        //获取model数据
        getModelData: function () {
            var modelName = this.modelName,
                viewModel = this.viewModel,
                data = {};

            this.$('form input:not([name$=input])').each(function () {
                var $input = $(this),
                    databind = $input.attr("data-bind");
                if (databind) {
                    // var value = databind.match(/^value:(.*)/)[1];
                    // var name = value.split('.').last;
                    var name = databind.split('.')[1];
                    data[name] = smart.kendoui.getWidgetData($input);
                }
            });

            return $.extend(true, viewModel.get(modelName).toJSON(), data);
        },

        //显示校验错误信息
        showValidateErrors: function (validators) {
            this.clearErrorNotices();

            var modelName = this.modelName;
            for (var i = 0; i < validators.length; i++) {
                var validator = validators[i],
                    type = validator["type"],
                    errors = validator["errors"];

                //form校验错误提示
                if (type == "form") {
                    for (var j = 0; j < errors.length; j++) {
                        var error = errors[j],
                            field = error["propIndex"],
                            msg = error["errorMsg"];

                        var $field = this.$("input[data-bind='value:" + modelName + "." + field + "']");
                        if ($field.attr('data-role')) {
                            $field = $field.closest('.k-widget').find("input");
                        }
                        $field.addClass("s-field-invalid");
                        $field.attr("error_notice", msg.join(";"));
                    }
                }

                //grid校验错误提示
                if (type == "grid") {
                    this.subGridEdit.showInvalidErrors(errors);
                }
            }

            this.initErrorNotices();
        },

        //错误提示
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
        },

        //数据绑定
        bindViewModel: function () {
            this.viewModel = kendo.observable(this.model);
            kendo.bind(this.container, this.viewModel);
        },

        //重置初始值
        resetViewModel: function () {
            var modelName = this.modelName;
            this.viewModel.set(modelName, this.model[modelName]);
        },

        //初始化组件
        initComponents: function () {
            //在子类实现
        },

        //初始化变量
        initVars: function () {
            //在子类实现
        },

        //事件绑定
        bindEvents: function () {
            smart.bind('#' + this.containerId + ' #btnDoNew', [this.doNew, this]);
            smart.bind('#' + this.containerId + ' #btnDoSave', [this.doSave, this]);
            smart.bind('#' + this.containerId + ' #btnDoDelete', [this.doDelete, this]);
        },

        //判断显隐按钮
        can: function (op) {
            return smart.ability.can(this.options.ymlModule, op);
        },

        ready: function () {

            smart.Module.fn.ready.call(this);

            this.bindViewModel();

            this.initComponents();

            this.initVars();

            this.bindEvents();

            smart.ability.hide(this.options.ymlModule);
        }

    });
})();
