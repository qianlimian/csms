/* 
 * @description kendoui主题的弹窗
 * @author zhaochuanfeng
 */

(function () {
    if (!window.smart) {
        window.smart = {};
    }

    //确认框
    smart.confirm = function (options) {

        if ($("#confirmDialog").length > 0) {
            $("#confirmDialog").parent().remove();
        }

        options = $.extend(true, {
            message: "",
            buttons: [
                {
                    name: "确认",
                    click: function () {
                    }
                },
                {
                    name: "取消",
                    click: function () {
                    }
                }
            ]
        }, options);

        var $confirmDialog = $("<div id='confirmDialog'></div>");

        $confirmDialog.kendoDialog(options).data("kendoDialog").show();

    };

    //警示框
    smart.alert = function (message) {

        if ($("#alertDialog").length > 0) {
            $("#alertDialog").parent().remove();
        }

        var $alertDialog = $("<div id='alertDialog'></div>");

        $alertDialog.kendoDialog({message: message}).data("kendoDialog").show();
    };

})();