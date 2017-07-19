<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<body>
<style>
	.text-center{
		text-align:center;
	}
	tbody,th,td,tr{
		vertical-align:middle !important;
	}
	tbody select{  
	  width: 95%;
	}
</style>
<div id="ctnCaseScore">
<div>
 	<h4 class="text-center">执法办案案件评价表</h4>
 </div>
 <div>
	 <div style="float:left">
	 	<h6>
	  	   	<input type="hidden" id="score_id" value="${score.id}"/>
	       	<input type="hidden" id="score_caseId" value="${score.caseId}"/>
			<span>案件名称：<span style="text-decoration:underline;font-weight:700">${score.caseName}</span></span>
			<span style="margin-left:20px">案件得分：<span style="text-decoration:underline;font-weight:700;" id="score_totalScore">${score.totalScore}分</span></span>
	 	</h6>
	 </div>
	 <div style="float:right">        
	 	<a class="k-button" id="btnDoSave" smart-ability="edit"><i class="fa fa-save"></i>保存</a>
	 </div>
 </div>
 <table class="table table-bordered">
     <thead class="text-center">
	     <tr>
	         <th colspan="2" class='col-md-2'>项目</th>
	         <th class='col-md-2'>评价内容</th>
	         <th class='col-md-1'>分值</th>
	         <th colspan="2" class='col-md-2'>评价标准</th>
	         <th class='col-md-2'>操作</th>        
	         <th class='col-md-1'>得分</th>
	     </tr>
     </thead>
     <tbody valign="center">
     <c:forEach items="${bigItems}" var="bigItem">
     	<tr>
     		<th rowspan="${bigItem.treeleafs.size()+1}" colspan="2" scope="row">${bigItem.seq }、${bigItem.standard }（${bigItem.score } 分）</th>
     		<c:forEach items="${bigItem.treeleafs }" var="leaf">
     		<tr>
	     		<td class='col-md-2'>${leaf.seq}、${leaf.standard }</td>
	     		<td class="text-center">${leaf.score }分</td>
	     		<td colspan="2">
	     			<c:forEach items="${leaf.treeleafs }" var="detail">
		     		<div>${detail.standard }</div>		     		
		     		</c:forEach>
	     		</td>
	     		<td>
	     			<select class="evalOperation">
			     		<option>--请选择--</option>	
			     		<c:set value="1" var="existed"></c:set>	
			     		 <c:set value="0" var="itemSelected_score"></c:set>
			     		  <c:set value="" var="itemSelected_id"></c:set>
			     		<c:forEach items="${leaf.treeleafs }" var="detail">				     					     			
			     			<c:forEach items="${score.itemDtos }" var="itemSelected">
				     			<c:choose>		     				     			
	                    			<c:when test="${itemSelected.evalId eq detail.id }">
	                    				 <option value="${detail.score }" id="${detail.id }" selected>${detail.standard }</option>	                    				 
	                    				 <c:set value="${itemSelected.score}" var="itemSelected_score"></c:set>
	                    				 <c:set value="${itemSelected.id}" var="itemSelected_id"></c:set>
										 <c:set value="-1" var="existed"></c:set>
	                    			</c:when>	                    				                    			 
	                			</c:choose>	                   			
				     		</c:forEach>	
				     		<c:if test="${existed == 1}">
				     			<option value="${detail.score }" id="${detail.id }">${detail.standard }</option>
				     		</c:if>	 	
				     		<c:set value="1" var="existed"></c:set>				     		
		     			</c:forEach>
		     		</select>
		     		<input type="hidden" value="${itemSelected_id}"> 		     		
	     		</td>
	     		<td class="score text-center">${itemSelected_score } 分</td>	
     		</tr>
     		</c:forEach>
     	</tr>
     	</c:forEach>
     </tbody>
</table>
</div>
<script src="${ctx}/views/pages/caseScore/mark/score.js"></script>
</body>
