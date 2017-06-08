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
    smart.SelectModule = smart.Module.extend({

		//构造函数
        init: function (options) {

            if (typeof(options.grid) == "undefined") {
                this.gridName = "mainGrid";
            } else {
                this.gridName = options.grid;
            }

            smart.Module.fn.init.call(this, options);
        },

        //初始化组件
        initComponents: function () {
        },

        //默认参数
        gridOptions: function () {
            var self = this;
            return {
                height: '100%',
                dataSource : {
                    url: self.restUrl + "query.do",
                    parameterMap: function () {
                        return {}
                    }
                },
                toolbar: self.$("#template").length == 0? false : kendo.template(self.$("#template").html()),
                groupable: false
            }
        },

        //初始化变量
        initVars: function () {
            this.mainGrid = this.$("#" + this.gridName).data("kendoGrid");
        },

        //绑定事件
        bindEvents: function () {
            var self = this;

            //绑定选择事件
            smart.bind("#" + this.containerId + " #btnDoSelect", function () {
                var data = self.mainGrid.selectData();
                smart.Event('ITEM_SELECTED_EVENT', data).fire();

            });
            //绑定选择回调事件
            self.mainGrid.bind('smartRowDblClick', function (e) {
                var dataItem = e.data.rowData,
                    data = {ids: [dataItem.id], items: [dataItem]};
                smart.Event('ITEM_SELECTED_EVENT', data).fire();
            })
        },

        //查询
        doQuery: function () {
            this.mainGrid.dataSource.read();
        },

        //grid自适应高度
        resizeLayout:function () {
            smart.kendoui.fixGridHeight(this.mainGrid.wrapper);
        },

        //入口函数
        ready: function () {

            smart.Module.fn.ready.call(this);

            this.initComponents();

            this.initVars();

            this.bindEvents();

            this.resizeLayout();
        }

    });
})();
