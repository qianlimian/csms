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
                        {field: "name", type: "string", title: "律师姓名", width: 100},
                        {field: "domain", type: "string", title: "专业领域", width: 100},
                        {field: "registrationNum", type: "string", title: "执业证号", width: 100},
                        {field: "lawyerOffice", type: "string", title: "所属事务所", width: 100},
                        {field: "registerDate", type: "date", title: "从业时间", width: 100, format: '{0:yyyy-MM-dd}'},
                        {field: "status", type: "enum", title: "办理状态", width: 150,
                            values: smart.Enums["com.bycc.enumitem.CertificateStatus"].getData()}
                    ]
                })
            );

        },

        //绑定事件
        bindEvents: function () {
            //调用父类方法绑定增删改查
            smart.SingleIndexModule.fn.bindEvents.call(this);
            smart.bind('#' + this.containerId + ' #btnDoUpload', [this.doUpload, this]);
        },
        //导入律师信息
        doUpload:function(){
            smart.kendoui.window('#ctnUploadWrap', {
                content: basePath + "/lawyers/impIndex.do",
                title: "导入律师",
                width: 600,
                height: 400
            }).center().open();
        }
    });

    new IndexModule({
        name : "SmartLawyerIndex", //必需，Index模块名
        containerId : "ctnLawyerIndex", //必需，Index模块的容器id
        restUrl: "/lawyers/", //必需，请求的rest地址
        editModule : {
            name : "SmartLawyerEdit", //必需，Edit模块名
            containerId : "ctnLawyerEditWrap" //必需，Edit模块的容器id
        }
    });
})();
