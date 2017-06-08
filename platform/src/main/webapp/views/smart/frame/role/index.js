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
                        { field: "id",  width: 100, hidden: true },
                        { field: "name", type:"string", title: "角色", width: 200 },
                        { field: "desc", type:"string", title: "描述", width: 200, mapping: "description" } //desc关键字错误
                    ]
                })
            );
        }

    });

    new IndexModule({
        name : "SmartRoleIndex", //必需，Index模块名
        containerId : "ctnRoleIndex", //必需，Index模块的容器id
        restUrl: "/roles/", //必需，请求的rest地址
        editModule : {
            name : "SmartRoleEdit", //必需，Edit模块名
            containerId : "ctnRoleEditWrap" //必需，Edit模块的容器id
        }
    });

})();
