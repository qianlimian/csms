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
            var self = this;



            smart.kendoui.comboBox($("#handle_status"), {
                dataSource: smart.Enums["com.bycc.enumitem.CaseHandle"].getData()
            });
            smart.kendoui.datePicker($("#accept_data_start"));
            smart.kendoui.datePicker($("#accept_data_end"));
            smart.kendoui.datePicker($("#close_data_start"));
            smart.kendoui.datePicker($("#close_data_end"));
            smart.kendoui.comboBox($("#master_unit"), {
                dataSource: smart.Data.get('#police_stations_select')
            });
            smart.kendoui.comboBox($("#case_type"), {
                dataSource: smart.Enums["com.bycc.enumitem.CaseType"].getData()
            });
            smart.kendoui.comboBox($("#accept_unit"), {
                dataSource: smart.Data.get('#police_stations_select')
            });
            smart.kendoui.datePicker($("#occur_data_start"));
            smart.kendoui.datePicker($("#occur_data_end"));



            smart.kendoui.grid(this.$("#mainGrid"),
                $.extend(true, this.gridOptions(), {
                    dataSource : { //可省略，默认方法query
                        url: this.restUrl + "search.do",
                        parameterMap: function () {
                            return self.queryParams.call(self)
                        }
                    },
                    columns : [
                        {field: "id", width: 100, hidden: true},
                        {field: "caseCode", type: "string", title: "案件编号", width: 100},
                        {field: "caseName", type: "string", title: "案件名称", width: 100},
                        {field: "suspect", type: "string", title: "嫌疑人", width: 100},
                        {field: "masterUnit", type: "string", title: "主办单位", width: 100,mapping:"masterUnit.name"},
                        {field: "masterPoliceName", type: "string", title: "主办人", width: 100,mapping:"masterPolice.user.name"},
                        {field: "caseHandle", type: "enum", title: "办理状态", width: 150,
                            values: smart.Enums["com.bycc.enumitem.CaseHandle"].getData()
                        }
                    ]
                })
            );

            //办案区登记窗口
            smart.kendoui.window('#ctnCaseRegisterWrap', {
                content: basePath + "/caseRegister.do",
                title: "办案区登记",
                width: "80%",
                height: "80%"
            });
        },


        queryParams: function () {
            var param = {};
            param.masterUnit = $('#master_unit').val();
            param.handleStatus = $('#handle_status').val();
            param.acceptStart = $('#accept_data_start').val();
            param.acceptEnd = $('#accept_data_end').val();
            param.closeStart = $('#close_data_start').val();
            param.closeEnd = $('#close_data_end').val();
            param.alarmCode = $('#alarm_code').val();
            param.caseType = $('#case_type').val();
            param.acceptUnit = $('#accept_unit').val();
            param.acceptPolice = $('#accept_police').val();
            param.occurStart = $('#occur_data_start').val();
            param.occurEnd = $('#occur_data_end').val();
            return param;
        },

        //绑定事件
        bindEvents: function () {
            //调用父类方法绑定增删改查
            smart.SingleIndexModule.fn.bindEvents.call(this);
            smart.bind('#' + this.containerId + ' #btnDoHandle', [this.doHandle, this]);
            smart.bind('#' + this.containerId + ' #btnDoFinish', [this.doFinish, this]);
            smart.bind('#' + this.containerId + ' #btnDoUpload', [this.doMediaUpload, this]);
            smart.bind('#btnDoExport', [this.doExport, this]);
        },
        

        //取选择的办案记录
        getSelectItem: function () {
            var $row = this.mainGrid.select(),
                item = this.mainGrid.dataItem($row);
            if (!item) smart.alert("请选择案件记录");
            return item;
        },


        //开始办案
        doHandle: function () {
            var selectedItem = this.getSelectItem();
            if (selectedItem) {
                smart.ajax({
                    type: 'get',
                    url: this.restUrl + "startCase.do?caseRecordId=" + selectedItem.id,
                    success: function (res) {

                        smart.confirm({
                            message: "即将开始办案区登记流程，是否继续?",
                            buttons: [{
                                click: function () {
                                    var regWin = $("#ctnCaseRegisterWrap").data("kendoWindow"),
                                        regModule = smart.Module.getModule("SmartCaseRegisterIndex");

                                    regWin.maximize().open();
                                    regModule.loadItems(selectedItem.id);
                                }
                            }]
                        });
                    }
                });
            }
        },

        //结束办案
        doFinish: function () {
            var me = this,
                selectedItem = this.getSelectItem();
            if (selectedItem) {
                smart.confirm({
                    message: "即将结束办案区登记流程，是否继续?",
                    buttons: [{
                        click: function () {
                            $.ajax({
                                type: 'GET',
                                url: me.restUrl + "finishCase.do?caseRecordId=" + selectedItem.id,
                                success: function (res) {
                                    me.notification.hide().success({ message: "操作成功！" });
                                },
                                error: function (jqXHR) {
                                    smart.alert(jqXHR.responseText);
                                }
                            });
                        }
                    }]
                });
            }
        },

		//视频上传
        doMediaUpload:function(){
            var selectedItem = this.getSelectItem();
            if (selectedItem) {
                //上传窗口
                smart.kendoui.window('#ctnCaseMediaUploadWrap', {
                    content: basePath + "/caseMedias/load.do?caseRecordId=" + selectedItem.id,
                    title: "视频上传",
                    width: "80%",
                    height: "80%"
                }).center().open();
            }
        },

        doExport: function () {

            var param = this.queryParams();

            //获取mainGrid的queryBean
            var qb = this.mainGrid.getParameterMap();

            location.href=this.restUrl+"excel.do?param="+JSON.stringify(param)+"&qb="+JSON.stringify(qb);
        }


    });

    new IndexModule({
        name : "SmartCaseRecordIndex", //必需，Index模块名
        containerId : "ctnCaseRecordIndex", //必需，Index模块的容器id
        restUrl: "/caseRecords/", //必需，请求的rest地址
        editModule : {
            name : "SmartCaseRecordEdit", //必需，Edit模块名
            containerId : "ctnCaseRecordEditWrap", //必需，Edit模块的容器id
            options: {
                width : '80%',
                height : '80%'
            }
        },
        query:{
            containerId:"ctnQueryWin",
            options:{
                width : '600px',
                height : '300px'
            }
        },
        ymlModule: "caseRecord" 
    });
})();
