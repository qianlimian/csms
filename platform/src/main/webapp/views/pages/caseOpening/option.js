/**
 * Created by wanghaidong on 2017/7/14.
 */
(function () {
    var IndexModule = smart.SingleIndexModule.extend({

        //构造函数
        init: function (options) {
            smart.SingleIndexModule.fn.init.call(this, options);
        },

        //设置编辑页面title
        _setTitle: function (title) {
            this.editWin.title("案件信息导出");
        },
        //初始化组件
        initComponents: function () {
            //调用父类方法初始化查询窗口、编辑页面、导航条等
            smart.SingleIndexModule.fn.initComponents.call(this);
            smart.kendoui.comboBox(this.$("#handle_status"), {
                dataSource: smart.Enums["com.bycc.enumitem.CaseHandle"].getData()
            });
            // smart.kendoui.comboBox(this.$("#case_status"), {
            //     dataSource: smart.Enums["com.bycc.enumitem.CaseStatus"].getData()
            // });
            smart.kendoui.datePicker(this.$("#accept_data_start"));
            smart.kendoui.datePicker(this.$("#accept_data_end"));
            smart.kendoui.datePicker(this.$("#close_data_start"));
            smart.kendoui.datePicker(this.$("#close_data_end"));
            smart.kendoui.comboBox(this.$("#master_unit"), {
                dataSource: smart.Data.get('#policeStation_select')
            });

        },

        //@overwrite
        bindEvents: function () {
            /*smart.IndexModule.fn.bindEvents.call(this);*/

            // smart.bind('#' + this.containerId + ' #btnDoFind', [this.doFind, this]);

            smart.bind('#' + this.containerId + ' #btnDoExport', [this.doExport, this]);

        },

        doExport: function () {
            //根据查询条件返回查询结果
            var handleStatus = $("#handle_status").val();
            var masterUnit = $("#master_unit").val();
            var acceptStart = $("#accept_data_start").val();
            var acceptEnd = $("#accept_data_end").val();
            var closeStart = $("#close_data_start").val();
            var closeEnd = $("#close_data_end").val();
            console.log(handleStatus);
            console.log(masterUnit);
            console.log(acceptStart);
            location.href=this.restUrl+"excel.do?handleStatus="+handleStatus+"&masterUnit="+masterUnit+"&acceptStart="+acceptStart+"&acceptEnd="+acceptEnd+"&closeStart="+closeStart+"&closeEnd="+closeEnd;
        }


    });

    new IndexModule({
        name : "SmartCaseOpen",
        containerId : "ctnCaseOpeningOption",
        restUrl: "/cases/",
        editModule : {
            // name : "SmartCaseEdit",
            // containerId : "ctnCaseEditWrap",
            // options: {
            //     width : '80%',
            //     height : '80%',
            //     content: "/cases/edit.htm"
            // }
        }
    });
})();
