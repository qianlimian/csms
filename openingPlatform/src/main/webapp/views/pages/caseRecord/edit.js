(function () {
    var EditModule = smart.SingleEditModule.extend({
        //构造函数
        init: function (options) {
            smart.SingleEditModule.fn.init.call(this, options);
        },

        //初始化组件
        initComponents: function () {
            //创建tabstrip
            var self = this;
            self.tabstrip = $("#tabstrip").kendoTabStrip({
                animation: { open: { effects: "fadeIn"} }
            });   
            
            //case组件
            smart.kendoui.comboBox(this.$("#edit_caseType"), {
                dataSource: smart.Enums["com.bycc.enumitem.CaseType"].getData()
            });
            smart.kendoui.comboBox(this.$("#edit_caseStatus"), {
                dataSource: smart.Enums["com.bycc.enumitem.CaseStatus"].getData()
            });
            smart.kendoui.comboBox(this.$("#edit_caseHandle"), {
                dataSource: smart.Enums["com.bycc.enumitem.CaseHandle"].getData()
            });
             smart.kendoui.comboBox(this.$("#edit_open"), {
                dataSource: smart.Enums["com.bycc.enumitem.OpenType"].getData()
            });
            smart.kendoui.datePicker(this.$("#edit_acceptDate"));
            smart.kendoui.datePicker(this.$("#edit_registerDate"));
            smart.kendoui.datePicker(this.$("#edit_startDate"));
            smart.kendoui.datePicker(this.$("#edit_closeDate"));
        },
        
        //载入当前item
        loadItem: function (item) {
            smart.SingleEditModule.fn.loadItem.call(this, item);
            
            this._loadLaw(item);
            this._loadLawyer(item);
        },
        //载入法律法规
		_loadLaw:function(item){
			var self=this;			
			if(item && item.id) {
			 	 var lawGrid = smart.kendoui.grid("#lawGrid", {
                    dataSource : {
                        url: basePath + "/caseRecords/queryLaw.do?caseRecordId=" + item.id
                    },
                     columns : [
                        {field: "id", width: 100, hidden: true},
                        {field: "chapter", type: "string", title: "章", width: 100},
                        {field: "chapterName", type: "string", title: "章名", width: 100},
                        {field: "section", type: "string", title: "节", width: 100},
                        {field: "sectionName", type: "string", title: "节名", width: 100},
                        {field: "num", type: "string", title: "条", width: 100},
                        {field: "lawType", type: "enum", title: "法律类型", width: 150,
                            values: smart.Enums["com.bycc.enumitem.LawType"].getData()
                        }                        
                    ],
                    filterable: false,
                    selectable: "single",
                    height: 540,
                    pageable: {
                        refresh: false,
                        input: false
                    }
                });                
                $("#btnDoCancelLaw").click(function(){                	
                	var data=lawGrid.selectData();
                	 deleteIds = data.ids;
                	  if (deleteIds.length < 1) {
			                smart.alert("请选中删除的行");
			                return false;
			          }
			          smart.confirm({
		                message: "您确定要删除" + (deleteIds.length > 1 ? "所选" + deleteIds.length : "该") + "条记录?",
		                buttons: [{
		                    click: function () {
		                        //批量删除
		                        smart.ajax({
		                            type: 'delete',
		                            dataType: 'text', ////@ResponseBody返回null
		                            url: self.restUrl + 'cancelLaw.do?lawIds=' + deleteIds.join(',')+"&caseRecordId="+item.id,
		                            success: function (res) {
		                               lawGrid.dataSource.read();
		                                //提示
		                                self.notification.hide().success({message:'删除成功！'});
		                            }
		                        });
		                    }
		                }]
		            });
			          
                });                
			 }
		},
		
		//载入律师
		_loadLawyer:function(item){
			 if(item && item.id) {
			 	 var lawyerGrid = smart.kendoui.grid("#lawyerGrid", {
                    dataSource : {
                        url: basePath + "/caseRecords/queryLawyer.do?caseRecordId=" + item.id
                    },
                     columns : [
                        {field: "id", width: 100, hidden: true},
                        {field: "name", type: "string", title: "姓名", width: 100},
                        {field: "domain", type: "string", title: "领域", width: 100},
                        {field: "registrationNum", type: "string", title: "证件号", width: 100},
                        {field: "lawyerOffice", type: "string", title: "事务所", width: 100},
                        {field: "registerDate", type: "string", title: "注册时间", width: 150},
                        {field: "status", type: "enum", title: "状态", width: 150,
                            values: smart.Enums["com.bycc.enumitem.CertificateStatus"].getData()
                        }
                    ],
                    filterable: false,
                    selectable: "single",
                    height: 540,
                    pageable: {
                        refresh: false,
                        input: false
                    }
                });
                
                 $("#btnDoCancelLawyer").click(function(){                	
                	var data=lawyerGrid.selectData();
                	 deleteIds = data.ids;
                	  if (deleteIds.length < 1) {
			                smart.alert("请选中删除的行");
			                return false;
			          }
			          smart.confirm({
		                message: "您确定要删除" + (deleteIds.length > 1 ? "所选" + deleteIds.length : "该") + "条记录?",
		                buttons: [{
		                    click: function () {
		                        //批量删除
		                        smart.ajax({
		                            type: 'delete',
		                            dataType: 'text', ////@ResponseBody返回null
		                            url: self.restUrl + 'cancelLawyer.do?lawyerIds=' + deleteIds.join(',')+"&caseRecordId="+item.id,
		                            success: function (res) {
		                               lawyerGrid.dataSource.read();
		                                //提示
		                                self.notification.hide().success({message:'删除成功！'});
		                            }
		                        });
		                    }
		                }]
		            });
			          
                }); 
                
			 }
		},
		
         //绑定事件
        bindEvents: function () {
            //调用父类方法绑定增删改查
            smart.SingleEditModule.fn.bindEvents.call(this);
			smart.bind('#' + this.containerId + ' #btnDoTreeViewFresh', [this.doTreeViewFresh, this]); 
        }
    });

    new EditModule({
        name: "SmartCaseRecordEdit", //必需，Edit模块名
        containerId: "ctnCaseRecordEdit", //必需，Edit模块的容器id
        restUrl: "/caseRecords/", //必需，请求的rest地址
        modelName: "caseRecord", //必需，model名
        model: {
              caseRecord: {
                id: "",
                alarmCode: "",
                caseCode: "",
                caseName: "",
                caseSummary: "",
                caseType: "",
                caseStatus: "",
                caseHandle:"",
                suspect: "",            
                masterUnit: "",               
                masterPoliceName: "",                
                slaveUnit: "",
                slavePoliceName: "",                
                acceptDate: "",
                registerDate: "",
                closeDate: "",
                startDate: "",
                note: "",
                open:""
            }
        },
        ymlModule: "caseRecord"
    });
})();
