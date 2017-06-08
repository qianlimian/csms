(function () {

    if (!window.smart) {
        window.smart = {};
    }

    if (!smart.modules) {
        smart.modules = {};
    }

	/**
     *  继承kendoClass，init实为构造函数，其他为prototype方法
     */
    smart.Module = kendo.Class.extend({

        init: function (options) {
            var self = this;

            $.each(["containerId", "name", "restUrl"], function (index, item) {
                if (!options[item]) {
                    alert("smart.Module创建失败，缺少必要参数：" + item);
                }
            });

            for (var item in options) {
                self[item] = options[item];
            }
            //fixed
			self.restUrl = basePath + self.restUrl;
            self.options = options;
            smart.modules[self.name] = self;

            $(function () {
                self.ready.call(self);
            });
        },

        ready: function () {
            this.container = $("#" + this.containerId);
            this.notification = $("#notification").kendoNotification().data('kendoNotification');
        },

        $: function (selector) {
            return this.container.find(selector);
        }
    });

    smart.Module.getModule = function (name) {
        return smart.modules[name];
    };

})();
