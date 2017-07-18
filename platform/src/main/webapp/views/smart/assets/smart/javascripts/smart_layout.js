$(function () {
    //左侧Module菜单
    $(".s-menu.s-module-menu>li>a").click(function () {
        if (!$(document.body).hasClass("minified")) {
            if ($(this).next().is(':visible')) {
                $(this).children('.s-icon-right').removeClass('fa-chevron-down').addClass('fa-chevron-right');
                $(this).next().slideUp("fast");
            } else {
                $(".s-menu.s-module-menu>li>a").children('.s-icon-right').removeClass('fa-chevron-down').addClass('fa-chevron-right');
                $(".s-menu.s-module-menu>li>a").next().slideUp('fast');
                $(this).children('.s-icon-right').removeClass('fa-chevron-right').addClass('fa-chevron-down');
                $(this).next().slideDown("fast");
            }
        }
    });

    $(".s-menu.s-module-menu>li>a").hover(function () {
        if ($(document.body).hasClass("minified")) {
            $(".s-menu.s-module-menu>li>a").next().hide();
            $(this).next().show();
        }
    });

    //左侧Group菜单
    $(".s-group-menu>li>a").click(function () {
        if (!$(document.body).hasClass("minified")) {
            if ($(this).next().is(':visible')) {
                $(this).children('.s-icon-right').removeClass('fa-chevron-down').addClass('fa-chevron-right');
                $(this).next().slideUp("fast");
            } else {
                $(".s-group-menu>li>a .s-icon-right").removeClass('fa-chevron-down').addClass('fa-chevron-right');
                $(".s-group-menu>li>ul").slideUp('fast');
                $(this).children('.s-icon-right').removeClass('fa-chevron-right').addClass('fa-chevron-down');
                $(this).next().slideDown("fast");
            }
        }
    });

    $(".s-group-menu>li>a").hover(function () {
        if ($(document.body).hasClass("minified")) {
            $(".s-group-menu>li>a").next().hide();
            $(this).next().show();
        }
    });

    //收缩左侧边栏
    $("#minify").click(function() {
        $(document.body).toggleClass("minified");
        if ($(document.body).hasClass("minified")) {
            $(this).children('span').removeClass('fa-arrow-circle-left').addClass('fa-arrow-circle-right');
            $(".s-icon-right").removeClass('fa-chevron-down').addClass('fa-chevron-right');
        } else {
            $(this).children('span').removeClass('fa-arrow-circle-right').addClass('fa-arrow-circle-left');
        }
    });

    //设置（主题）选择器
    $("#theme").click(function(){
        $(this).toggleClass("activate");
        if ($(this).hasClass("activate")) {
            $(this).children("span").removeClass('fa-gear').addClass('fa-close');
            $(this).children("div").show();
        } else {
            $(this).children("span").removeClass('fa-close').addClass('fa-gear');
            $(this).children("div").hide();
        }
    });

    //初始化theme选择器
    $("#theme ul").smartTheme();

    //初始化switch选择器
    var $panel = $('#s-panel'),
        $widthSwitch = $("input[name='widthSwitch']"),
        $navBarSwitch = $("input[name='navbarSwitch']");

    //设置switch选择器的默认状态
    $widthSwitch.attr("checked", $panel.hasClass('full-width'));
    $navBarSwitch.attr("checked", $panel.hasClass('shrink-menu'));

    //switch选择器change事件
    $widthSwitch.bootstrapSwitch({
        onSwitchChange: function (event, state) {
            if (state) {
                $panel.removeClass('fixed-width').addClass('full-width');
            } else {
                $panel.removeClass('full-width').addClass('fixed-width');
            }
            saveSetting();
        }
    });
    $navBarSwitch.bootstrapSwitch({
        onSwitchChange: function (event, state) {
            if (state) {
                $panel.removeClass('expend-menu').addClass('shrink-menu');
            } else {
                $panel.removeClass('shrink-menu').addClass('expend-menu');
            }
            $(window).resize();
            saveSetting();
        }
    });

    //保存设置
    function saveSetting() {
        var widthState = $widthSwitch.bootstrapSwitch('state'),
            navBarState = $navBarSwitch.bootstrapSwitch('state');

        var data = {
            pageWidth: widthState? "full-width" : "fixed-width",
            menuPosition: navBarState? "shrink-menu" : "expend-menu"
        };

        $.ajax({
            type: 'POST',
            url: basePath + "/smart/users/saveSetting.do",
            data: data,
            success:function(result){
            }
        });
    }
});
