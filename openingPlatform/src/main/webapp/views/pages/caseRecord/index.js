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
            smart.kendoui.grid(this.$("#mainGrid"),
                $.extend(true, this.gridOptions(), {
                    columns : [
                        {field: "id", width: 100, hidden: true},
                        {field: "caseCode", type: "string", title: "案件编号", width: 100},
                        {field: "caseName", type: "string", title: "案件名称", width: 100},
                        /*{field: "suspect", type: "string", title: "嫌疑人", width: 100},*/
                        {field: "masterUnit", type: "string", title: "主办单位", width: 100, mapping:"masterUnit.name"},
                        {field: "masterPoliceName", type: "string", title: "主办人", width: 100, mapping:"masterPolice.user.name"},
                        {field: "caseHandle", type: "enum", title: "办理状态", width: 150,
                            values: smart.Enums["com.bycc.enumitem.CaseHandle"].getData()
                        },
                        {field: "open", type: "enum", title: "公开状态", width: 150,
                            values: smart.Enums["com.bycc.enumitem.OpenType"].getData()
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
            smart.bind('#' + this.containerId + ' #btnDoView', [this.doEdit, this]);
            smart.bind('#' + this.containerId + ' #btnDoOpen', [this.doOpen, this]);
            smart.bind('#' + this.containerId + ' #btnDoCancel', [this.doCancel, this]);
            smart.bind('#' + this.containerId + ' #btnDoLawyer', [this.doLawyer, this]);
            smart.bind('#' + this.containerId + ' #btnDoLaw', [this.doLaw, this]);            
            smart.bind('#' + this.containerId + ' #btnDoUpload', [this.doUpload, this]);
           
        },

        //取选择的办案记录
        getSelectItem: function () {
        	var self=this;
        	var selectedIds=[];         
   		    var data = this.mainGrid.selectData();
            selectedIds = data.ids;
            if (selectedIds.length < 1) {
                smart.alert("请选中编辑的行");
                return false;
            }
            return selectedIds;
        },
        
		//显示(编辑)案件信息
        doEdit: function (event) {
            var editItems = [];
            //行内编辑
            if (event) {
                var tr = $(event.target).closest("tr");
                var data = this.mainGrid.dataItem(tr);
                editItems.push(data);
            //多条编辑
            } else {
                var data = this.mainGrid.selectData();
                editItems = data.items;
            }

            if (editItems.length < 1) {
                smart.alert("请选择要查看的案件");
                return false;
            }

            this._doShow(editItems);
        },       
		//案件开放
        doOpen:function(){
        	self=this;        	
        	var selectedIds = this.getSelectItem();   
        	if(selectedIds){        		
        		smart.ajax({
                    type: 'post',
                    dataType: 'text',
                    url: this.restUrl + "doOpen.do?caseRecordId=" + selectedIds.join(','),
                    success: function (res) {
                    	self.notification.hide().success({ message: '操作成功！' });
						self.doFresh();
                    },
                    error: function (jqXHR) {
                        self.doFresh();
                    }
                });        		
        	}
        },
        //取消开放
        doCancel:function(){
        	self=this;
        	var selectedIds=this.getSelectItem();     	
        	if(selectedIds){        		
        		smart.ajax({
                    type: 'post',
                    dataType: 'text',
                    url: this.restUrl + "doCancel.do?caseRecordId=" + selectedIds.join(','),
                    success: function (res) {
                    	self.notification.hide().success({ message: '操作成功！' });
						self.doFresh();
                    },
                    error: function (jqXHR) {
                        self.doFresh();
                    }
                });        		
        	}
        },
        //关联律师
        doLawyer:function(){
    	  self=this;
    	  var selectedIds=this.getSelectItem(); 
    	  if(selectedIds){
	    	   if(selectedIds.length>1){
	    	  		return false;
	    	   }
	    	  var id=selectedIds[0];
	          smart.kendoui.window('#ctnLawWrap', {
	                content: basePath + "/caseRecords/lawyer.do?id="+id,
	                title: "律师",
	                width: "80%",
	                height: "80%"
	            }).center().open();
    	  }
    	 
        },
        //关联法律法规
        doLaw:function(){ 
        	 self=this;
	    	  var selectedIds=this.getSelectItem(); 
	    	  if(selectedIds){
	    	  
	    	  	if(selectedIds.length>1){
		    	  	return false;
		    	}	    	  
	    	    var id=selectedIds[0];
	            smart.kendoui.window('#ctnLawyerWrap', {
	                content: basePath + "/caseRecords/law.do?id="+id,
	                title: "法律法规",
	                width: "80%",
	                height: "80%"
	            }).center().open();
	    	  }	    	  
        },
        //刷新
		doFresh:function(){		
			var grid = $("#mainGrid").data("kendoGrid");
               grid.dataSource.page(1);
               grid.dataSource.read();
		},
		//导入数据
		doUpload:function(){
			smart.kendoui.window('#ctnUploadWrap', {
	                content: basePath + "/caseRecords/impIndex.do",
	                title: "导入案件",
	                width: 600,
	                height: 400
	            }).center().open();
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
                height : '80%',
                content: "/caseRecords/edit.htm"
            }
        }
    });
})();
