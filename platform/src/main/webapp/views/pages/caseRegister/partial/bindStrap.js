(function () {
    var SelectModule = smart.SelectModule.extend({
        //构造函数
        init: function (options) {
            smart.SelectModule.fn.init.call(this, options);
        },

        //初始化组件
        initComponents: function () {
            var self = this;
            smart.kendoui.grid(this.$("#mainGrid"),
                $.extend(true, this.gridOptions(), {
                    dataSource: { //可省略，默认方法findSubsById
                        url: this.restUrl + "findUnusedStraps.do"
                    },
                    columns: [
                        {field: "id", width: 100, hidden: true},
                        {field: "name", type: "string", title: "名称", width: 100},
                        {field: "code", type: "string", title: "编号", width: 100},
                        {
                            field: "status", type: "string", title: "状态", width: 100,
                            values: smart.Enums["com.bycc.enumitem.UsageStatus"].getData()
                        },
                        {field: "note", type: "string", title: "备注", width: 100}
                    ],
                    selectable: "single" //单选
                })
            )
        },

        //@overwrite
        bindEvents: function () {
            var self = this;
            //绑定选择事件
            smart.bind("#" + this.containerId + " #btnDoSelect", function () {
                var $row = self.mainGrid.select(),
                    data = self.mainGrid.dataItem($row);
                smart.Event('STRAP_BIND_EVENT', data).fire();

            });
            //绑定选择回调事件
            self.mainGrid.bind('smartRowDblClick', function (e) {
                var data = e.data.rowData;
                smart.Event('STRAP_BIND_EVENT', data).fire();
            })
        }
    });

    new SelectModule({
        name: "BdmBindStrap", //必需，模块名
        containerId: "ctnStrapSelect", //必需，模块的容器id
        restUrl: "/bdmStrap/" //必需，请求的rest地址
    });
})();