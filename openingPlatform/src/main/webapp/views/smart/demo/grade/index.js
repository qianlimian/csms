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

            //Grid组件
            smart.kendoui.grid($("#mainGrid"),
                $.extend(true, this.gridOptions(), {
                    columns : [
                        { field: "id",  width: 100, hidden: true },
                        { field: "gradeName", type: "string", title: "班级", width: 100 },
                        { field: "teacherId", type: "number", title: "教师", width: 150,
                            mapping: "teacher.id", // mapping用于foreignkey column的映射，以转换过滤和排序参数
                            values: smart.Data.get('#teachers_select')
                        }
                    ]
                })
            );
        }
    });

    new IndexModule({
        name : "SmartGradeIndex", //必需，Index模块名
        containerId : "ctnGradeIndex", //必需，Index模块的容器id
        restUrl: "/smart/grades/", //必需，请求的rest地址
        editModule : {
            name : "SmartGradeEdit", //必需，Edit模块名
            containerId : "ctnGradeEditWrap" //必需，Edit模块的容器id
        }
    });
})();
