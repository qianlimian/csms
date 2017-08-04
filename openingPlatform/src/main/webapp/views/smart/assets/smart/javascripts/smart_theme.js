/* 
 * @description 主题切换
 * @auther zhaochuanfeng
 */

(function($) {

    $.fn.smartTheme = function(options) {
        var themes = [
            { text: "Black", value: "black" },
            { text: "BlueOpal", value: "blueopal" },
            { text: "Bootstrap", value: "bootstrap" },
            { text: "Default", value: "default" },
            { text: "Fiori", value: "fiori" },
            { text: "HighContrast", value: "highcontrast" },
            { text: "Metro", value: "metro" },
            { text: "Moonlight", value: "moonlight" },
            { text: "Silver", value: "silver" }
        ],
        template = kendo.template("<li data-value='#=value#' class='skin-#=value#'><span>#= text #</span></li>");

        return this.each(function() {
            var theme = localStorage.getItem("kendoSkin");

            $(this).html(kendo.render(template, themes))
                .on("click", "li", function() {
                    var li = $(this).children("span").addClass("fa fa-check-square-o").end(),
                        theme = themes[li.index()];

                    li.siblings().children("span").removeClass("fa fa-check-square-o");

                    changeTheme(theme.value);

                })
                .children("li")
                .filter(function() {
                    return $(this).data("value") === theme;
                }).children("span").addClass("fa fa-check-square-o");
        });
    };

    function changeTheme(skinName) {
        var doc = document,
            kendoLinks = $("link[href*='kendo.']", doc.getElementsByTagName("head")[0]),
            commonLink = kendoLinks.filter("[href*='kendo.common']"),
            skinLink = kendoLinks.filter(":not([href*='kendo.common'])"),
            href = location.href,
            skinRegex = /kendo\.\w+(\.min)?\.css/i,
            extension = skinLink.attr("rel") === "stylesheet" ? ".css" : ".less",
            url = commonLink.attr("href").replace(skinRegex, "kendo." + skinName + "$1" + extension);

        function replaceTheme() {
            var oldSkinName = localStorage.getItem("kendoSkin"),
                newLink;

            if (navigator.userAgent.match(/MSIE ([6-9]+)\./)) {
                newLink = doc.createStyleSheet(url);
            } else {
                newLink = skinLink.eq(0).clone().attr("href", url);
            }

            //css href
            newLink.insertBefore(skinLink[0]);
            skinLink.remove();

            //html Class
            $(doc.documentElement).removeClass("k-" + oldSkinName).addClass("k-" + skinName);

            //localStorage
            try {
                localStorage.setItem("kendoSkin", skinName);
            } catch(err) {}
        }

        replaceTheme();
    }

    function applyCurrentTheme() {
        var storage = localStorage.getItem("kendoSkin"),
            theme = storage && storage != "null" ? storage : "silver";

        changeTheme(theme);
    }

    applyCurrentTheme();

})(jQuery);
