(function () {

    var EditModule = smart.SingleEditModule.extend({
        //构造函数
        init: function (options) {
            smart.SingleEditModule.fn.init.call(this, options);
        },

        //初始化组件
        initComponents: function () {
            smart.kendoui.datePicker(this.$("#edit_registerDate"));

            smart.kendoui.comboBox(this.$("#edit_status"), {
                dataSource: smart.Enums["com.bycc.enumitem.CertificateStatus"].getData()
            });
        }
    });

    $('#btnDoSave').click(function () {
        var mail = $('#edit_email').val();
        if (mail != '') {//判断
            var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
            if (!reg.test(mail)) {
                smart.alert('邮箱格式不正确，请重新填写!');
                return false;
            }
        }
    })


	
    new EditModule({
        name: "SmartLawyerEdit", //必需，Edit模块名
        containerId: "ctnLawyerEdit", //必需，Edit模块的容器id
        restUrl: "/lawyers/", //必需，请求的rest地址
        modelName: "lawyer", //必需，model名
        model: { //必需，model用于MVVM
            lawyer: {
                id: "",
                name: "",
                domain: "",
                registrationNum: "",
                lawyerOffice: "",
                registerDate:"",
                status:"",
                info:"",
                phone:"",
                email:""
            }
        }
    });
})();
