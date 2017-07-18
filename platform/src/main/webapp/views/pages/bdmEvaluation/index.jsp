<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>
<div id="ctnSelectWrap">
</div>
    
  <div class="eval_items"> 
	  <div class="eval_header">
	  	<div></div><span>评分大项</span>
	  	<div class="operater">
	  		<a id="btnDoNewItem" smart-ability="edit" title="新增"><span class="fa fa-plus"></span></a>	  	
	  		<a id="btnDoEditItem" smart-ability="edit" title="编辑"><span class="fa fa-edit"></span</a>
	  		<a id="btnDoDeleteItem" smart-ability="delete" title="删除"><span class="fa fa-remove"></span></a>
	  	</div>
	  </div>
	  <div class="eval_body" id="bigItemTree">	  
	  </div> 
  </div>   
  <div class="eval_subItems">
   	  <div class="eval_header" >
	  	<div style="text-align:center"><span>评分细则</span></div>
	  	<div style="border:1px solid silver">
			<a id="btnSubEvalDoNew" class="k-button" smart-ability="edit"><span class="fa fa-plus"></span>新增</a>
			<a id="btnSubEvalDoEdit" class="k-button" smart-ability="edit"><span class="fa fa-edit"></span>编辑</a>
			<a id="btnSubEvalDoDelete" class="k-button" smart-ability="delete"><span class="fa fa-remove"></span>删除</a>
		</div>
	  </div>
  	  <div id="evalDetails">  	  	 
  	  </div>
  </div>
    
<div></div>
<div id="window">
	
</div>


<script src="${ctx}/views/pages/bdmEvaluation/index.js"></script>
<style>
	.eval_items{
		width: 15%;   		
   		border: 1px solid silver;
    	text-align: center;
    	float:left;
	}
	.eval_subItems{
		float: left;
	    width: 84%;
	    margin-left: 5px;
	    border: 1px solid silver;
	}
	.operater{
	 	border: 1px solid silver;
	}
	.operater a span{
		width:20px;
	}
</style>
</body>