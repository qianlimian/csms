(function () {
    if (!window.smart) {
        window.smart = {};
    }

    smart.ability = {
        _moduleOperates: {},

        init: function () {
            var self = this;
            var json = $('#userOperateSetting').text();
            if (json) {
                self._moduleOperates = JSON.parse(json);
            }
        },

        can: function (module, operate) {
            if (!module) return true;
            var moduleOperates = this._moduleOperates,
                operates = moduleOperates[module];
            if (operates) {
                for (var i = 0; i < operates.length; i++) {
                    if (operates[i] == operate.toUpperCase()) {
                        return true;
                    }
                }
            }
            return false;
        },

        hide: function (module) {
            var self = this,
                attr = "smart-ability";

            $("[" + attr + "]").each(function (_, element) {
                var $element = $(element),
                    operate = $element.attr(attr);
                if (!self.can(module, operate)) {
                    $element.hide();
                }
            });
        }
    };

    $(function () {
        smart.ability.init();
    });
})();
