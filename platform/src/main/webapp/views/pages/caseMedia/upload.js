(function() {
	var videoData;	
	// 视频上传
	var uploadModule = smart.SingleEditModule.extend({
		// 构造函数
		init : function(options) {
			smart.SingleEditModule.fn.init.call(this, options);
		},

		// 初始化组件
		initComponents : function() {
			// 创建tabstrip
			var self = this;
			self.tabstrip = self.$("#tabstrip").kendoTabStrip({
						animation : {
							open : {
								effects : "fadeIn"
							}
						}
			});
			//初始化视频列表
			var id=$("#caseRecordId").html();
			videoData = new kendo.data.HierarchicalDataSource({
		        transport : {
		            read : {
						url : basePath + "/caseMedias/getLocalMediaList.do?caseRecordId="+id,
		                dataType : "json"            
		            }
		        },
		        schema : {
		            model : {
		            	id: "mappingPath",
		                hasChildren : "hasChildren"	                
		            }
		        }
    		});
			$("#mediaTreeView").kendoTreeView({
		        dataSource : videoData,
		        dataTextField : "label",
		        select: function(e){
			        var data = $('#mediaTreeView').data('kendoTreeView').dataItem(e.node);
					if(!data.hasChildren){
						$("#upload_mediaName").html(data.label);
						$("#upload_startDate").html(data.startDate);
						$("#upload_endDate").html(data.endDate);
						$("#upload_roomName").html(data.roomCategory);
						$("#upload_mediaPreview").attr("src",data.serverPath);
					}	
		        }
    		});
			
			//初始化视频类别
			 var mediaCgList = new kendo.data.DataSource({
                transport: {
                    read: {
                        url: basePath+"/bdmVideoCategories/findAllCg.do",
                        dataType: "json"
                    }
                }
            });	            
            smart.kendoui.comboBox($("#upload_categories"), {
                dataTextField: "name",
                dataValueField: "id",
                placeholder:"---视频类型---",
                dataSource: mediaCgList,
                select:function (e){
                	if(e.item){
	                	$("#btnDoMediaUpload").removeClass("k-state-disabled");
	                	$("#btnDoMediaUpload").removeAttr("disabled");
                	}                	
                }
            });
             smart.kendoui.comboBox($("#edit_mediaCategory"), {
                dataTextField: "name",
                dataValueField: "id",
                placeholder:"---视频类型---",
                dataSource: mediaCgList,
                select:function (e){
                	if(e.item){
	                	$("#uploadButton").removeClass("k-state-disabled");
	                	$("#uploadButton input").removeAttr("disabled");
                	}                	
                }
            });
		},
		
		bindEvents : function() {
			var self = this;	
			smart.bind('#' + this.containerId + ' #btnDoMediaUpload', [this.doMediaUpload, this]); 			
			smart.bind('#' + this.containerId + ' #btnDoTreeViewFresh', [this.doTreeViewFresh, this]); 

		},
		//上传
		doMediaUpload:function(){
			var selectedNode = $('#mediaTreeView').data('kendoTreeView').select();
			var data = $('#mediaTreeView').data('kendoTreeView').dataItem(selectedNode);
			var datas=[];
			var mediaCategory=$("#upload_categories").data("kendoComboBox").text();
			data.mediaCategory=mediaCategory;			
			if(!mediaCategory){
				smart.alert("请上传前选择视频类型！");
				return false;
			}
			datas.push(data);			
			if(!data.hasChildren){
				$("#upload_mediaPreview").removeAttr("src");
				smart.ajax({
                url: basePath + "/caseMedias/copyFiles.do",
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify(datas),
                success: function (res) {
                },
                error: function (jqXHR) {
                	/*console.log("---");
                    var msg = smart.ajax.formatError(jqXHR);
                    if (jqXHR.status == 632) { //校验错误
                        self.showValidateErrors(msg["smart_validator"]);
                    } else {
                        smart.alert(msg);
                    }*/
                }
            });
			}
		},
		//刷新
		doTreeViewFresh:function(){		
			$("#mediaTreeView").data("kendoTreeView").dataSource.read();			
		}
		
	});

	new uploadModule({
				name : "SmartCaseMediaUpload", // 必需，模块名
				containerId : "ctnCaseMediaUpload", // 必需，模块的容器id
				restUrl : "/caseMedias/", // 必需，请求的rest地址
				modelName : "caseMediaDto", // 必需，model名
				model : { // 必需，model用于MVVM
					caseMediaDto : {						
					}
				}
			});

})();

