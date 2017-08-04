/* 
 * @description 绑定事件、自定义事件
 */
 
(function () {
    if (!window.smart) {
        window.smart = {};
    }

    smart.bind = function (selector, fun, eventName) {
        var eventName = eventName || 'click';
        $(selector).bind(eventName, function () {
            if ($.isArray(fun)) fun[0].call(fun[1]);
            if ($.isFunction(fun)) fun.call();
        });
    };

    //----------------------------自定义事件-------------------------------------------------------------------
    smart.Event = function (eventName, param) {
        return new smart.Event.prototype.init(eventName, param);
    };

    smart.Event.events = {};

    /**
     * 绑定事件
     * @example:  smart.Event.bind('eventName',[function, context]);
     */
    smart.Event.bind = function (eventName, fun) {
        if (!smart.Event.events[eventName]) {
            smart.Event.events[eventName] = fun;
        }
    };

    /**
     * 触发事件
     * @example:  smart.Event('eventName', param).fire();
     */
    smart.Event.prototype = {

        init: function (eventName, param) {
            this.eventName = eventName;
            this.param = param || {};
            return this;
        },

        fire: function () {
            var handleFun = smart.Event.events[this.eventName];
            if (handleFun) {
                if ($.isArray(handleFun)) handleFun[0].call(handleFun[1], this.param);
                if ($.isFunction(handleFun))  handleFun.call(this.param);
            }

        }
    };

    // 无new创建smart.Event对象时实际创建的是smart.Event.init对象，
    // 需要重定向smart.Event.init对象的prototype属性到smart.Event.prototype
    smart.Event.prototype.init.prototype = smart.Event.prototype;

})();
