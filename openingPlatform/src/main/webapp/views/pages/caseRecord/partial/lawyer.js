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
            smart.kendoui.grid(this.$("#mainLawyerGrid"),
                $.extend(true, this.gridOptions(), {
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
                    ]
                })
            );

        },

        //绑定事件
        bindEvents: function () {
            //调用父类方法绑定增删改查
           /* smart.SingleIndexModule.fn.bindEvents.call(this);
           */ 
            smart.bind('#' + this.containerId + ' #btnDoSaveLawyer', [this.doSaveLawyer, this]);
                      
        },

        //取选择的办案记录
        getSelectItem: function () {
        	var self=this;
        	var selectedIds=[];         
   		    var data = this.mainGrid.selectData();
            selectedIds = data.ids;
            if (selectedIds.length < 1) {
                smart.alert("请选择要删除的行");
                return false;
            }
            return selectedIds;
        },
        //保存关联
       doSaveLawyer:function(){       
       		var self=this;
        	var selectedIds=self.getSelectItem();    
        	console.log(selectedIds.join(','));
        	if(selectedIds){
        		var crId=$("#crId").val();
        		smart.ajax({
                    type: 'post',
                    dataType: 'text',
                    url: basePath + "/caseRecords/saveLawyer.do?caseRecordId="+crId+"&lawyerIds=" + selectedIds.join(','),
                    success: function (res) {
                    	//提示
		                self.notification.hide().success({message:'保存成功！'});
                    },
                    error: function (jqXHR) {
                    	
                    }
                });       
        	}
       }
        
    });

    new IndexModule({
        name : "SmartLawyerIndex", //必需，Index模块名
        containerId : "ctnLawyerIndex", //必需，Index模块的容器id
        restUrl: "/lawyers/", //必需，请求的rest地址
        grid: 'mainLawyerGrid',
        editModule : {
            name : "SmartLawyerEdit", //必需，Edit模块名
            containerId : "ctnLawyerEditWrap", //必需，Edit模块的容器id
            options: {
                width : '80%',
                height : '80%'
            }
        }
    });
})();
