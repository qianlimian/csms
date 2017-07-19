(function () {
    var IndexModule = smart.MultiIndexModule.extend({

		//构造函数
        init: function (options) {
            smart.MultiIndexModule.fn.init.call(this, options);
        },
        
		//初始化组件
        initComponents: function () {
            //调用父类方法初始化查询窗口、编辑页面、导航条等
            smart.MultiIndexModule.fn.initComponents.call(this);

            smart.kendoui.grid($("#mainGrid"),
                $.extend(true, this.mainGridOptions(), {
                    // dataSource : { //可省略，默认方法query
                    //     url: this.restUrl + "query.do"
                    // },
                    columns : [
                        { field: "id",  width: 100, hidden: true},
                        { field: "courseName", type: "string", title: "课程名", width: 100 },
                        { field: "gradeName", type: "string", title: "年级", width: 150, mapping: "grade.gradeName" }, // mapping用于foreignkey column的映射，以转换过滤和排序参数
                        { field: "teacherName", type: "string", title: "教师", width: 150, mapping: "teacher.name" }
                    ]
                })
            );

            smart.kendoui.grid($("#subGrid"),
                $.extend(true, this.subGridOptions(), {
                    // dataSource : { //可省略，默认方法findSubsById
                    //     url: this.restUrl + "findSubsById.do"
                    // },
                    columns : [
                        { field: "id",  width: 100, hidden: true},
                        { field: "studentName", title: "学生", width: 100 },
                        { field: "mark", title: "成绩", width: 150 }
                    ]
                })
            );
        }
    });

    new IndexModule({
        name : "SmartCourseIndex", //必需，Index模块名
        containerId : "ctnCourseIndex", //必需，Index模块的容器id
        restUrl: "/smart/courses/", //必需，请求的rest地址
        // grid: ['mainGrid', 'subGrid'], //可省略，默认['mainGrid', 'subGrid']，如果要配置自定义的id时需要加
        // query : { //默认已经取消了查询窗口，有特殊需要的加
        //     containerId : "ctnQueryWin",
        //     options: {
        //         width : '600px',
        //         height : '200px'
        //     }
        // },
        editModule : {
            name : "SmartCourseEdit", //必需，Edit模块名
            containerId : "ctnCourseEditWrap", //必需，Edit模块的容器id
            // options: { //可省略，默认编辑窗口配置
            //     width : '800px',
            //     height : '450px',
            //     content: "/smart/courses/edit.htm" //可省略，从后台load页面
            // }
        },
        ymlModule: "course" //配置yml按钮显隐的模块名
    });
})();
