(function () {
    var EditModule = smart.SingleEditModule.extend({

        //构造函数
        init: function (options) {
            smart.SingleEditModule.fn.init.call(this, options);
        },

        //初始化组件
        initComponents: function () {
            //调用父类方法初始化查询窗口、编辑页面、导航条等
            smart.SingleEditModule.fn.initComponents.call(this);
        },

        //绑定事件
        bindEvents: function () {
            //调用父类方法绑定增删改查
            smart.SingleEditModule.fn.bindEvents.call(this);

            var me = this;
            // 解绑手环按钮
            smart.bind('#' + this.containerId + ' #btnDoUnbindStrap', function () {
                var data = me.getModelData();
                data.allBelongsReturn = $(":radio[name='allBelongsReturn']:checked").val();

                smart.Event('STRAP_UNBIND_EVENT', data).fire();
            });
        }

    });

    new EditModule({
        name: "CaseRegisterUnbindStrap", //必需，Index模块名
        containerId: "ctnUnbindStrapWrap", //必需，Index模块的容器id
        restUrl: "/caseRegister/", //必需，请求的rest地址
        modelName: 'casePeople',
        model: {//必需，model用于MVVM
            casePeople: {
                id: "",
                leaveReason: "",
                allBelongsReturn: true,
                note: ""
            }
        }
    });
})();
