(function () {

    var EditModule = smart.SingleEditModule.extend({
        //构造函数
        init: function (options) {
            smart.SingleEditModule.fn.init.call(this, options);
        },

        //初始化组件
        initComponents: function () {

            smart.kendoui.comboBox(this.$("#edit_teacherId"), {
                dataSource: smart.Data.get('#teachers_select') //localComboBox数据源
            });
        }
    });
	
    new EditModule({
        name: "SmartGradeEdit", //必需，Edit模块名
        containerId: "ctnGradeEdit", //必需，Edit模块的容器id
        restUrl: "/smart/grades/", //必需，请求的rest地址
        modelName: "grade", //必需，model名
        model: { //必需，model用于MVVM
            grade: {
                id: "",
                gradeName: "",
                teacherId: ""
            }
        }
    });
})();
