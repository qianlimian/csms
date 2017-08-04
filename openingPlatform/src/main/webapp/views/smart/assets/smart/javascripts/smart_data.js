(function () {
    if (!window.smart) {
        window.smart = {};
    }

    if (!smart.Data) {
        smart.Data = {};
    }

    var data = smart.Data;

    data.get = function (selector) {
        var array = [];
        $(selector).children('option').each(function (i, obj) {
            array.push({text: $(obj).text(), value: $(obj).val()});
        });
        return array;
    };

})();

