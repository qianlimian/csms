/* 
 * @description 请求ajax错误处理
 * @author zhaochuanfeng
 */

(function () {

    if (!window.smart) {
        window.smart = {};
    }

    smart.ajax = function (options) {
        var config = $.extend(true, {
            type: "get",
            dataType: "json" //服务端返回的数据格式
        }, options);

        config.error = config.error ||  function (jqXHR, textStatus, errorThrown) {
            _errorHandler(jqXHR, textStatus, errorThrown);
        };

        $.ajax(config);
    };


    /**
     * 格式化后台返回的错误数据
     * @param jqXHR
     */
    smart.ajax.formatError = function (jqXHR) {
        var msgBody = jqXHR.responseText;
        var msg;
        try {
            msg = $.parseJSON(msgBody);
        } catch (e) {
            alert(" ajax请求异常返回值错误!错误信息： " + msgBody);
        }
        return msg;
    };


    function _errorHandler(jqXHR, textStatus, errorThrown) {
        switch (textStatus) {
            case "timeout":
                alert("超时");
                break;
            case "error":
                alert("error:"+jqXHR.responseText);
                break;
            case "parsererror":
                alert("json数据解析错误");
                break;
            case "notmodified":
                alert("notmodified");
                break;
        }
    }

})();
