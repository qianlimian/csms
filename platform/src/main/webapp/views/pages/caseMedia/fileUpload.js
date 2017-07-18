(function() {	
	//上传本地视频
	var jqXHR; 
	var id=$("#caseRecordId").html();
	var index=1;
	var url=basePath+"/caseMedias/doUpload.do?id="+id+"&category=";
   $("#MediaFile").fileupload({  
	   url: null, 
	   //acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
	   limitConcurrentUploads: 1, 
	   sequentialUploads: true, 
	   progressInterval: 100, 
	   maxChunkSize: 1024000, 
	   dataType: "json",
	   autoUpload: false,
   	   add: function (e, data) {    	   			 
	   		var upload="#"+data.index+" #operate";
	   		data.context = $('<button/>').attr("id",data.index+"upload").addClass("fa fa-upload")
                .appendTo(upload)
                .click(function () {
                    data.context = $('<p/>').text('Uploading...').replaceAll($(this));  
                    //删除取消按钮
                    var cancelId="#"+$(this).attr("id")+"Cancel";                    
                    $(cancelId).remove();
                    //获取视屏类别
                    var uploadId="#"+$(this).attr("id"); 
                    var categoryId=uploadId.split("upload")[0]+" #selectedCategory";
                    var category=$(categoryId).html();
                    //重置URL      
                    data.url=url+category;   
                    data.submit();
                });   
            data.context = $('<button/>').attr("id",data.index+"uploadCancel").addClass("fa fa-times")
                .appendTo(upload)
                .click(function () {
                    var uploadCancelId="#"+$(this).attr("id");      
                    var root=uploadCancelId.split("uploadCancel")[0];
                    $(root).remove();                    
                }); 
			}})
		    .bind('fileuploadprogress', function (e, data) { 
		    			var progress = parseInt(data.loaded / data.total * 100, 10);		    			
		    			if(data.index){
		    				progressBar="#"+data.index+" #progressbar #progress";
		    				$(progressBar).css('width',progress + '%'); 
			    			$(progressBar).html(progress + '%');
		    			}		    			
		    			})
		    .bind('fileuploadchange', function (e, data) {	
		    	initContainer(data);
		    	if(data.index){
		    		var fileName="#"+data.index+" #fileName";
		    		var fileSizeId="#"+data.index+" #fileSize";
		    		var fileSize=formatFileSize(data.files[0].size);
		    		$(fileName).append("<span>"+data.files[0].name+"</span>");
		    		$(fileSizeId).append("<span>"+fileSize+"</span>");
		    		fileProgress="#"+data.index+" #progressbar";		    		
			    	$(fileProgress).append("<div id='progress' class='progress-bar progress-bar-success' style='width: 0%;'></div>");					
		    	}		    	
		    })
		    .bind('fileuploadfail', function (e, data){
		    	operate="#"+data.index+" #operate";
		    	$(operate).empty();
		    	$(operate).append("<span style='color:red'>ERROR</span>");
		    	$('<a/>').attr("id",data.index+"uploadCancel").addClass("fa fa-times")
                .appendTo(operate)
                .click(function () {
                    var uploadCancelId="#"+$(this).attr("id");      
                    var root=uploadCancelId.split("uploadCancel")[0];
                    $(root).remove();                    
                }); 
		    })
		    .bind('fileuploaddone', function (e, data) {
    			operate="#"+data.index+" #operate";
    			$(operate).css('display','none');
    			if(data.index){
    				progressDone="#"+data.index+" #progressbar #progress";
    				$(progressDone).css('width',100 + '%'); 
					$(progressDone).html(100 + '%'); 
    			}		    			
				})
			.bind('fileuploadpaste', function (e, data) {
				//暂停
			});
	//生成ID
	function getFileId(data){
		if(data){
    		var datas=data.split(".");    		
    		return datas[0];    		
    	}
		return null;
	}	
	
	function initContainer(data){	
		var fileType=data.files[0].type;
   		if(fileType.search(/^video\/.*$/)==-1){
			smart.alert("只能上传视频文件！");
			return false;
   		}   
		data.index=index;

        var category = $("input[name='edit_mediaCategory']:checked").val();
		if (category) {
			var content="<div id='"+index+"' class='s-row-fluid'>";
			content=content+"<div class='s-pct5'><input type='checkbox' name='uploadCheckbox' value='"+index+"'/></div>";
			content=content+"<div id='selectedCategory' class='s-pct5'>"+category+"</div>";
			content=content+"<div id='fileName' class='s-pct20' ></div>";
			content=content+"<div id='fileSize' class='s-pct10'></div>";
			content=content+"<div id='progressbar' class='s-pct45 progress progress-striped active' role='progressbar'></div>";
			content=content+"<div id='operate' class='s-pct10' style='margin-left:15px'></div>";
			content=content+"</div>";		
			$("#fileContainer").append(content);
			index++;
		} else {
			smart.alert("请选择视频类别！");
		}		
	}
	//格式化文件大小
	function formatFileSize(bytes) {
            if (typeof bytes !== 'number') {
                return '';
            }
            if (bytes >= 1024000000) {
                return (bytes / 1024/ 1024 / 1024).toFixed(2) + ' GB';
            }
            if (bytes >= 1024000) {
                return (bytes / 1024 / 1024).toFixed(2) + ' MB';
            }
            return (bytes / 1024).toFixed(2) + ' KB';
    }       
})();

//全选
function allChecked(){
	var checkboxs=document.getElementsByName("uploadCheckbox");
	if(document.getElementsByName("all_checkbox")[0].checked==true){
		for(var i=0;i<checkboxs.length;i++){
			checkboxs[i].checked=true;
		}
	}else{
		for(var i=0;i<checkboxs.length;i++){
			checkboxs[i].checked=false;
		}
	}
}
//批量上传
function batchUpload(){
	var checkboxs=document.getElementsByName("uploadCheckbox");
	for(var i=0;i<checkboxs.length;i++){
		if(checkboxs[i].checked){
			var fileUploadId="#"+checkboxs[i].value+"upload";
			var a=$(fileUploadId).click();
			console.log("------");
			console.log(a);
		}		
	}
}
