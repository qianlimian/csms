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
                        {field: "name", type: "string", title: "姓名", width: 100},
                        //smart框架扩展的enum写法，主要用于条件过滤（原生kendoui无type:"enum"）
                        {field: "gender", type: "enum", title: "性别", width: 150,
                            values: smart.Enums["com.bycc.enumitem.Gender"].getData()
                        }
                    ]
                })
            );
        }
    });

    new IndexModule({
        name : "SmartTeacherIndex", //必需，Index模块名
        containerId : "ctnTeacherIndex", //必需，Index模块的容器id
        restUrl: "/teachers/", //必需，请求的rest地址
        editModule : {
            name : "SmartTeacherEdit", //必需，Edit模块名
            containerId : "ctnTeacherEditWrap" //必需，Edit模块的容器id
        },
        ymlModule: "teacher" //配置yml按钮显隐的模块名
    });
})();
