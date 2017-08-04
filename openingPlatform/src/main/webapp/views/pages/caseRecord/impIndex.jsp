<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>
<div id="ctnCaseRecordIndex">	
 	 <div class="box">              
       <button type="button" class="btn btn-success" id='importBtn'>导入</button>
     </div>
     
     <div style="border-top:1px solid #ccc">
          <div style="margin-left: 150px;margin-top:20px;"><input type="file" id="excelFile"></div>
     </div>   
            
	<div style="margin-top:20px;">
		<span>共导入数据：</span><span id="number" style="font-weight:700;"></span><span>条</span>
	</div>
	
	<div id="resultInfo"></div> 
	<span id="staticNotification"></span>
	
	<div style="border-top:1px solid #ccc;margin-top:30px;">	
		<label style="font-weight:700;"> 格式要求：CSV for MS Excel</label>
	</div> 
</div>
<script src="${ctx}/views/pages/caseRecord/impIndex.js"></script>

</body>