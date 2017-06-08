(function () {

    var EditModule = smart.SingleEditModule.extend({
        //构造函数
        init: function (options) {
            smart.SingleEditModule.fn.init.call(this, options);
        },

        //初始化组件
        initComponents: function () {
            smart.kendoui.numericTextBox(this.$("#edit_age"),{format:'n0'});// n表示数字，0表示小数位数
            smart.kendoui.datePicker(this.$("#edit_birthday"));
        }
    });
	
    new EditModule({
        name: "SmartStudentEdit", //必需，Edit模块名
        containerId: "ctnStudentEdit", //必需，Edit模块的容器id
        restUrl: "/students/", //必需，请求的rest地址
        modelName: "student", //必需，model名
        model: { //必需，model用于MVVM
            student: {
                id: "",
                name: "",
                age: "",
                birthday: "",
                address: ""
            }
        },
        ymlModule: "student" //配置yml按钮显隐的模块名
    });
})();
