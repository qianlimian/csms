/* 
 * @description 自定义kendoDialog
 * @author zhaochuanfeng
 */

(function ($) {
    var kendo = window.kendo,
        ui = kendo.ui;

    /*
     *
     * Dialog
     *
     */
    var DialogWin = ui.Window.extend({
        init: function (element, options) {
            var that = this;

            ui.Window.fn.init.call(that, element, options);

            that._template();

            that._bind();

            $(element).data("kendoWindow", that);
        },

        options: {
            name: "Dialog",
            width: 300,
            height: 100,
            modal: true,
            visible: false,
            resizable: false,
            animation: false,
            message: "",
            buttons: [
                {
                    name: "确认",
                    click: function () {
                    }
                }
            ]
        },

        _template: function () {
            var that = this,
                element = that.element,
                options = that.options,
                templ = kendo.template('<div class="dialog-content" style="width:#=parseInt(width)-14#px;height:#=parseInt(height)-55#px;">#=message#</div>' +
                    '<div class="dialog-buttons" style="width:#=parseInt(width)-14#px;">#$.each(buttons,function(_,button){#<button class="k-button">#=button.name#</button>#})#');

            element.html(templ(options));
        },

        _bind: function () {
            var that = this,
                element = that.element,
                options = that.options;

            $.each(options.buttons, function (i, button) {
                $(element.find(".dialog-buttons .k-button")[i]).on("click", { handler: button.click }, function (e) {
                    e.data.handler.call();
                    that.close();
                });
            });
        },

        show: function () {
            this.center().open();
        }
    });

    kendo.ui.plugin(DialogWin);

})(jQuery);