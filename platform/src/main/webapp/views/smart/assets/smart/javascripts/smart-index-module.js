/**
 * 索引页面模板，依赖 smart-module.js
 * = require ./smart-module.js
 */
(function () {
    if (!window.smart) {
        window.smart = {};
    }
    /**
     *  执行顺序为： init -> ready{ initComponents -> initVars -> bindEvents -->resizeLayout }
     */
    smart.IndexModule = smart.Module.extend({

		//构造函数
        init: function (options) {

            if (typeof(options.grid) == "undefined") {
                this.gridName = ["mainGrid", "subGrid"];
            } else if (typeof(options.grid) == "string") {
                this.gridName = [options.grid];
            } else {
                this.gridName = options.grid;
            }

            this.query = $.extend(true, {containerId: "ctnQueryWin"}, options["query"]);

            smart.Module.fn.init.call(this, options);
        },

        //初始化组件
        initComponents: function () {
            var self = this,
                query = self.query,
                queryWinId = query.containerId,
                queryOptions = query.options || {
                    width : '600px',
                    height : '200px'
                };

            //查询窗口
            if (queryOptions.content) {
                queryOptions.content = basePath + queryOptions.content; //可用load的方式
            }
            this.queryWin = smart.kendoui.window('#' + queryWinId, queryOptions);
        },

        //初始化变量
        initVars: function () {
            this.mainGrid = $("#" + this.gridName[0]).data("kendoGrid");
        },

        //绑定事件
        bindEvents: function () {
            var self = this;

            //高级查询窗口
            smart.bind('#' + this.containerId + ' #btnAdvancedQuery', [this.openQueryWin, this]);

            //确认查询、重置查询
            smart.bind('#' + this.query.containerId + ' #btnDoQuery', [this.returnQuery, this]);
            smart.bind('#' + this.query.containerId + ' #btnDoReset', [this.resetQuery, this]);

            //回车查询
            $("#" + this.query.containerId).find(".s-query :input").each(function(){
                $(this).keydown(function (e) {
                    if (e.which == 13) {
                        self.returnQuery();
                    }
                });
            });

            $(window).resize(function () {
                self.resizeLayout();
            });
        },

        //打开查询窗口
        openQueryWin: function () {
            this.queryWin.title("查询");
            this.queryWin.center().open();
        },

        //查询
        doQuery: function () {
            this.mainGrid.dataSource.read();
        },

        //确认查询
        returnQuery: function () {
            this.queryWin.close();
            this.doQuery();
        },

        //重置查询
        resetQuery: function () {
            $("#" + this.query.containerId).find(".s-query :input").val("");
        },

        //默认的查询参数
        queryParams: function () {
            var params = {},
                $inputs = $("#" + this.query.containerId).find(".s-query input:not([name$=input])");

            $inputs.each(function (index, item) {
                var $item = $(item);
                params[$item.prop("name")] = smart.kendoui.getWidgetData($item);
            });
            return params;
        },

        //grid自适应高度
        resizeLayout:function () {
            var $grid = this.mainGrid.wrapper,
                height = $(window).height() - $grid.offset().top - 5;

            $grid.height(height);

            smart.kendoui.fixGridHeight($grid);
        },

        //判断显隐按钮
        can: function (op) {
            return smart.ability.can(this.options.ymlModule, op);
        },

        //入口函数
        ready: function () {

            smart.Module.fn.ready.call(this);

            this.initComponents();

            this.initVars();

            this.bindEvents();

            this.resizeLayout();

            smart.ability.hide(this.options.ymlModule);
        }

    });
})();
