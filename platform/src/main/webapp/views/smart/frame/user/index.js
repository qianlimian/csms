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
            );
        },

        //绑定事件
        bindEvents: function () {
            //调用父类方法绑定增删改查
            smart.SingleIndexModule.fn.bindEvents.call(this);

            smart.bind('#' + this.containerId + ' #btnDoResetPwd', [this.doResetPwd, this]);
        },

        //--------------------自定义函数START-------------------------
        doResetPwd: function () {
            var data = this.mainGrid.selectData(),
                ids = data.ids;
            if (ids.length < 1) {
                smart.alert("请选择要重置的行");
                return false;
            }

            $.post(this.restUrl + "resetPsw.do", {ids: ids.join(",")}, function (res) {
                //
            }, "json");
        }
        //--------------------自定义函数END-------------------------
    });

    new IndexModule({
        name : "SmartUserIndex", //必需，Index模块名
        containerId : "ctnUserIndex", //必需，Index模块的容器id
        restUrl: "/users/", //必需，请求的rest地址
        editModule : {
            name : "SmartUserEdit", //必需，Edit模块名
            containerId : "ctnUserEditWrap" //必需，Edit模块的容器id
        }
    });

})();
