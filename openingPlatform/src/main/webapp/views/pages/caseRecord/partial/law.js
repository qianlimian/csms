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
            smart.kendoui.grid(this.$("#mainLawGrid"),
                $.extend(true, this.gridOptions(), {
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
                    ]
                })
            );

        },

        //绑定事件
        bindEvents: function () {
            //调用父类方法绑定增删改查
           /* smart.SingleIndexModule.fn.bindEvents.call(this);
           */ 
            smart.bind('#' + this.containerId + ' #btnDoLaw', [this.doSaveLaw, this]);
                      
            
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
        
        //选中法律法规保存
        doSaveLaw:function(){
        	var self=this;
        	var selectedIds=self.getSelectItem();    
        	if(selectedIds){
        		var crId=$("#crId").val();
        		
        		smart.ajax({
                    type: 'post',
                    dataType:"text",
                    url: basePath + "/caseRecords/saveLaw.do?caseRecordId="+crId+"&lawIds=" + selectedIds.join(','),
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
        name : "SmartLawIndex", //必需，Index模块名
        containerId : "ctnLawIndex", //必需，Index模块的容器id
        restUrl: "/laws/", //必需，请求的rest地址
        grid: 'mainLawGrid',
        editModule : {
            name : "SmartLawEdit", //必需，Edit模块名
            containerId : "ctnLawEditWrap", //必需，Edit模块的容器id
            options: {
                width : '80%',
                height : '80%'
            }
        }
    });
})();
