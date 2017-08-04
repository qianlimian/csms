// 导入
var staticNotification = $("#staticNotification").kendoNotification({
                    appendTo: "#resultInfo"
                }).data("kendoNotification");
                
	$('#importBtn').click(function(){		
		var formData = new FormData();
		formData.append('file',$('#excelFile')[0].files[0]);
		var url = basePath+'/caseRecords/impExcel.do';
		$.ajax({
			url:url,
			type: "POST",
			processData: false,
			contentType: false,
			data:formData,
			success:function(data){
				if(data.success){
					$("#number").html(data.number);
					staticNotification.show("操作成功！","info");
				}else{
					staticNotification.show(data.errorMsg,"error");
				}
			},
			error:function(jqXHR, textStatus, errorThrown){
				$("importAlert").html("error");
			}
		});
	});