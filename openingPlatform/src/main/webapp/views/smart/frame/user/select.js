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
                    columns : [
                        { field: "id",  width: 100, hidden: true},
                        { field: "name", type:"string", title: "姓名", width: 150 },
                        { field: "loginName", type:"string", title: "登陆名", width: 150 },
                        { field: "lastLoginDate", type:"date", title: "登陆时间", width: 150, format: "{0:yyyy-MM-dd HH:mm:ss}", filterable: false },
                        { field: "insertDate", type:"date", title: "创建时间", width: 150, format: "{0:yyyy-MM-dd HH:mm:ss}", filterable: false },
                        { field: "updateDate", type:"date", title: "修改时间", width: 150, format: "{0:yyyy-MM-dd HH:mm:ss}", filterable: false }
                    ]
                })
            )
        }

    });

    new SelectModule({
        name : "SmartUserSelect", //必需，模块名
        containerId : "ctnUserSelect", //必需，模块的容器id
        restUrl: "/smart/users/" //必需，请求的rest地址
    });

})();