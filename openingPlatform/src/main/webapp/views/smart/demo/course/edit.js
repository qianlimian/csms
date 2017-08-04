(function () {

    var EditModule = smart.MultiEditModule.extend({

        //构造函数
        init: function (options) {
            smart.MultiEditModule.fn.init.call(this, options);
        },

        //初始化组件`
        initComponents: function () {

            //localCombo组件
            smart.kendoui.comboBox(this.$("#edit_teacherId"), {
                dataSource: smart.Data.get('#teachers_select')
            });

            //smartSearchBox组件
            smart.kendoui.searchBox(this.$("#edit_gradeId"), {
                dataFilterField: 'gradeName', //过滤字段
                dataTextField: 'gradeName', //显示字段
                dataValueField: 'id',
                grid: {
                    dataSource: {
                        pageSize: 10, //默认可省略
                        transport: {
                            read: {
                                url: basePath + "/smart/grades/find4SearchBox.do"
                            }
                        }
                    },
                    columns: [
                        {field: 'id', hidden: true},
                        {field: "gradeName", title: "班级", width: 80},
                        {field: "teacherName", title: "教师", width: 80}
                    ]
                }
            });

            //grid组件
            smart.kendoui.grid("#subGridEdit",
                $.extend(true, this.subGridOptions(), {
                    // dataSource : { //可省略，默认方法findSubsById
                    //     url: this.restUrl + "findSubsById.do"
                    // },
                    columns : [
                        { field: "id",  width: 100, hidden: true},
                        { field: "studentId", title: "学生", headerTemplate: "<span style='color: red'>*</span> 学生", width: 100, values: smart.Data.get("#students_select"),
                            editor: function (container, options) {
                                // $('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '"/>').appendTo(container).kendoComboBox ({
                                //     dataSource: smart.Data.get("#students_select")
                                // });
                                var $input = $('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '"/>');
                                $input.appendTo(container);
                                smart.kendoui.comboBox($input, {
                                    dataSource: smart.Data.get("#students_select")
                                });
                            }
                        },
                        { field: "mark", title: "成绩", width: 150,
                            editor: function (container, options) {
                                $('<input name="' + options.field + '"/>').appendTo(container).kendoNumericTextBox ();
                            }
                        }
                    ]
                })
            );
        }
    });

    new EditModule({
        name: "SmartCourseEdit", //必需，Edit模块名
        containerId: "ctnCourseEdit", //必需，Edit模块的容器id
        restUrl: "/smart/courses/", //必需，请求的rest地址
        // grid: "subGridEdit", //可省略，默认"subGridEdit"
        modelName: "course", //必需，model名
        model: {
            course: {
                id: "",
                courseName: "",
                gradeId: "",
                teacherId: ""
            }
        }
    });

})();