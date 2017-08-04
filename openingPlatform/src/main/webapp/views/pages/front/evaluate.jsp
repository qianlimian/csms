<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<body>
<%@ include file="common/header.jsp" %>
<style>
.evaluate_list a:link {
    color: #000000;
    text-decoration: none
}
.evaluate_list a:visited {
    color: #000000;
    text-decoration: none
}
.evaluate_list a:hover {
    color: #ff7f24;
    text-decoration: none;
}
.evaluate_list a:active {
    color: #ff7f24;
    text-decoration: none;
}
</style>

<div class="container evaluate-content">
    <form id="evaluateForm">
        <div class="row">
            <div class="col-md-2">评&ensp;价&ensp;人<strong style="color: red;">*</strong>：</div>
            <input class="col-md-8" type="text" id="name" required/>
            <div class="col-md-2 error"></div>
        </div>
        <div class="row">
            <div class="col-md-2">联系方式<strong style="color: red;">*</strong>：</div>
            <input class="col-md-8" type="text" id="tel" required/>
            <div class="col-md-2 error"></div>
        </div>
        <div class="row">
            <div class="col-md-2">邮件地址<strong style="color: red;">*</strong>：</div>
            <input class="col-md-8" type="text" id="email" required/>
            <div class="col-md-2 error"></div>
        </div>
        <div class="row">
            <div class="col-md-2">评价标题<strong style="color: red;">*</strong>：</div>
            <input class="col-md-8" type="text" id="title" required/>
            <div class="col-md-2 error"></div>
        </div>
        <div class="row">
            <div class="col-md-2">评价内容<strong style="color: red;">*</strong>：</div>
            <textarea class="col-md-8" id="content" style="height:120px;" required></textarea>
            <div class="col-md-2 error"></div>
        </div>
        <div class="row">
            <div class="col-md-2">验&ensp;证&ensp;码：</div>
            <input class="col-md-4" type="text" id="code"/>&ensp;
            <div class="col-md-4">
                <img id="img" src="${ctx}/authImage" onclick="javascript:changeImg()"/>&ensp;
                <a href='#' style="font-size: 1em;" onclick="javascript:changeImg()">看不清？换一张</a>
            </div>
            <div class="col-md-2 error"></div>
        </div>
        <div class="row">
            <button type="button" id="submitBtn">提交</button>
            <button type="button" id="resetBtn">重置</button>
        </div>
    </form>
</div>

<script type="text/javascript">

//提交按钮点击方法
$('#submitBtn').click(function(){
	var canSubmit = true;
	$("#evaluateForm").find("input[type=text], textarea").each(function () {
		$(this).siblings(".error").children("span").remove();
		if($(this).val().trim() == '') {
			canSubmit = false;
			//创建元素
	        var $required = $("<span style='color:red;'>不能为空</span>");
	        //将它追加到文档中
	        $(this).siblings(".error").append($required);
		}
    });
	if(canSubmit) {
		$.ajax({
            type: "POST",
            url: "${ctx }/complaints/save.do?code=" + $('#code').val(),
            data: JSON.stringify({"name":$('#name').val(),"email":$('#email').val(),"tel":$('#tel').val(),"title":$('#title').val(),"content":$('#content').val()}),
            contentType: "application/json",
            dataType: "text",
            success: function(data) {
            	if(data == "success"){
            		alert("提交成功！");
            		$('#code').val("");
                    changeImg();
            	}
            	if(data == "error"){
            		//创建元素
        	        var $required = $("<span style='color:red;'>验证码输入错误！</span>");
                	$('#code').parent().append($required);
            	}
            },
            error: function (jqXHR) {
                alert(jqXHR.responseText);
                changeImg();
            }
            
        });
	}
})

//重置按钮点击方法
$('#resetBtn').click(function(){
	$('#evaluateForm')[0].reset();
	$("#evaluateForm").find("input[type=text], textarea").each(function () {
        $(this).siblings(".error").children("span").remove();
	});
})

//换验证码图片
function changeImg(){
    var img = document.getElementById("img"); 
    img.src = "${ctx}/authImage?date=" + new Date();
}
</script>
</body>