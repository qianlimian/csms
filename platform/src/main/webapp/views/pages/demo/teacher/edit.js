(function () {

    var EditModule = smart.SingleEditModule.extend({
        //构造函数
        init: function (options) {
            smart.SingleEditModule.fn.init.call(this, options);
        },

        //初始化组件
        initComponents: function () {
            //需要配置JS的smart.Enums枚举
            smart.kendoui.comboBox(this.$("#edit_gender"), {
                dataSource: smart.Enums["com.bycc.enumitem.Gender"].getData()
            });
        }
    });
	
    new EditModule({
        name: "SmartTeacherEdit", //必需，Edit模块名
        containerId: "ctnTeacherEdit", //必需，Edit模块的容器id
        restUrl: "/teachers/", //必需，请求的rest地址
        modelName: "teacher", //必需，model名
        model: { //必需，model用于MVVM
            teacher: {
                id: "",
                name: "",
                gender: ""
            }
        },
        ymlModule: "teacher" //配置yml按钮显隐的模块名
    });
})();
