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
                    columns : [
                        {field: "id", width: 100, hidden: true},
                        {field: "code", type: "string", title: "编码", width: 100},
                        {field: "name", type: "string", title: "名称", width: 100},
                        {field: "note", type: "string", title: "备注", width: 200}
                    ]
                })
            );
        }
    });

    new IndexModule({
        name : "SmartBdmVideoCgIndex", //必需，Index模块名
        containerId : "ctnBdmVideoCgIndex", //必需，Index模块的容器id
        restUrl: "/bdmVideoCategories/", //必需，请求的rest地址
        editModule : {
            name : "SmartBdmVideoCgEdit", //必需，Edit模块名
            containerId : "ctnBdmVideoCgEditWrap" //必需，Edit模块的容器id
        },
        ymlModule: "bdmVideoCategory" //配置yml按钮显隐的模块名
    });
})();
