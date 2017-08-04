(function () {
    var IndexModule = smart.SingleIndexModule.extend({

        //构造函数
        init: function (options) {
            smart.SingleIndexModule.fn.init.call(this, options);
        },

        //初始化组件
        initComponents: function () {
            //调用父类方法初始化查询窗口、编辑页面、导航条等
            smart.SingleIndexModule.fn.initComponents.call(this);

            smart.kendoui.grid($("#mainGrid"),
                $.extend(true, this.gridOptions(), {
                    dataSource: {
                        url: this.restUrl + "query.do"
                    },
                    columns: [
                        {field: "id", width: 50, hidden: true},
                        {field: "title", type: "string", title: "标题", width: 100},
                        {field: "content", type: "string", title: "内容", width: 100}
                    ]
                })
            );
        },
        //绑定事件
        bindEvents: function () {
            //调用父类方法绑定增删改查
            smart.SingleIndexModule.fn.bindEvents.call(this);
            smart.bind('#' + this.containerId + ' #btnDoUpload', [this.doUpload, this]);
        },

        //导入民法
        doUpload: function () {
            smart.kendoui.window('#ctnUploadWrap', {
                content: basePath + "/laws/impIndex.do",
                title: "导入民法",
                width: 600,
                height: 400
            }).center().open();
        }
    });

    new IndexModule({
        name: "SmartCivilIndex", //必需，Index模块名
        containerId: "ctnCivilIndex", //必需，Index模块的容器id
        restUrl: "/laws/", //必需，请求的rest地址
        editModule: {
            options: {
                width: '60%',
                height: '65%'
            },
            name: "SmartLawEdit", //必需，Edit模块名
            containerId: "ctnLawEditWrap" //必需，Edit模块的容器id
        }
    });
})();
