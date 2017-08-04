<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<body>

<style>
	.s-row {
		width: 100%;
		display: block;
		float: left;
		text-align: center;
	}
	/* 
	.s-media-cg-wrap, .s-query-wrap {
		width: 50%;
		float: left;
	}
	.s-nav-prev, .s-nav-next {
		width: 20px;
		line-height: 30px;
		margin-left: 10px;
		float: left;
	} */
	.s-media-cg {
		padding: 0;
		margin: 0;
		list-style: none;
	}
	.s-media-cg > li {
		/* border: 1px solid #ccc; */
		border-radius: 4px;
		line-height: 40px;
		float: left;
	}
	#selected .s-media-cg > li {
		margin-top:-5px;
		border-radius: 4px;
		line-height: 40px;
		float: left;
	}
	.s-media-cg > li > a {
		display: block;
		width: 90px;
		color: #333;
		text-align: center;
		text-decoration: none;
	}
	
	.s-media-cg > li > span:first-child {
		
		width: 90px;
		color: #333;
		text-align: center;
		text-decoration: none;
		padding-left: 15px;
	}
	.s-input-group > .s-input {
		width: 95%;
		height: 30px;
		line-height: 30px;
	}
	.s-input-group > .s-button {
		margin-left: -20px;
		height: 30px;
		line-height: 30px;
	}
	.s-media {
		/* padding: 20px; */
		margin: 0;
		list-style: none;
	}
	.s-media > li {
		display: block;
		width: 33%;
		float: left;
	} 
	.thumbnail {
		height: 300px;
		padding: 5px;
		margin: 10px;
		border: 1px solid #ccc;
		border-radius: 4px;
	}
	.main-nav{
        border: 1px solid silver;
		border-radius: 4px;
		width:100%;
		min-height:14%;
		background-color: #fff;
		clear:both;
	}
	.main-nav .sub-group:first-child{
		border-top: none;
	}
	.sub-group{
		clear:both;
		padding:5px;
		border-top: 1px dashed #dedede;
		height: 40px;  
		margin-left: 22px;
   		margin-right: 22px  		
	}
	.sub-group .head{
		width:55px;
		float:left;		
		margin-top: 5px;
	}
	.sub-group .body{
		width:70%;
		float:left;
	}
	.sub-group .body .items{
		height: 36px;
   		overflow: hidden;
	} 
	.sub-group .expand-mode .items{
		width:100%;
		height: auto;
	    max-height: 102px;
	    overflow: auto;
	}
	.sub-group .foot{
		width:10%;
		float:left;
		margin-top: 5px;
	}
	a:hover, a:visited, a:link, a:active {
   		text-decoration: none;
		
	}
	.main-query .s-query-wrap{
		width:20%;
		float:right;
	}
	.pointer-events-none{
		pointer-events:none;
	}
	
	.main-sort{
        border: 1px solid silver;
		border-radius: 4px;
		width:100%;
		min-height:35px;
		/* background-color: #fff; */
		clear:both;
		margin-top:15px;
	}
	.sub_sort{
		float:right;
		width: 30%;
	}
	.sub-total{
		clear:both;
		padding:5px;
		border-top: 1px dashed #dedede;
		height: 40px;  
		margin-left: 22px;
   		margin-right: 22px;
   		width:15%; 
   		float: left; 
	}
	
    .subArea {
        border: 1px solid silver;
        min-height: 300px !important;
    }

    .subAreaTitle {
        border-bottom: 1px solid silver;
        width: 100%;
        padding: 5px 0;
        text-align: center;
    }

    .subAreaDetail {
        padding: 5px;
        word-wrap: break-word;
    }

    .subAreaDetail > label {
        width: 100%;
        text-align: left;
    }
    .s-row-fluid .s-pct5{
    	width:5%
    }
    .s-row-fluid .s-pct8{
    	width:8%
    }
</style>
<div class="main-query">
	<div class="s-query-wrap">
		<div class="s-input-group">
			<input class="s-input" type="text" value="${filter}" placeholder="请输入关键词" onKeyPress="search()">
			<a class="s-button"><i class="fa fa-search"></i></a>
		</div>
	</div>
</div> 
<div class="main-nav">
	<div class="sub-group" id="videoCg">
		<div class="head"><span>视频分类:</span></div>
		<div class="body">
			<div class="items">
				<ul class="s-media-cg">
					<c:forEach items="${cgMenu}" var="cg">
						<li class="<c:if test='${cg.name == category}'>k-state-selected</c:if>">
							<a href="caseMedias.htm?category=${cg.name}&filter=${filter}&unitSelected=${unitSelected}">${cg.name}</a>
						</li>
					</c:forEach>
				</ul>
			</div>			
		</div>
		<div class="foot">			
			<span class="show_more">更多<span class="fa fa-angle-down pointer-events-none"></span></span>
			<span class="show_less display-none">收起<span class="fa fa-angle-up pointer-events-none"></span></span>	
		</div>
	</div>
	<div class="sub-group" id="policeStation">
		<div class="head"><span>办案单位:</span></div>
		<div class="body">
			<div class="items">
				<ul class="s-media-cg">
					<c:forEach items="${unitMenu}" var="unit">
							<li class="<c:if test='${unit.name == unitSelected}'>k-state-selected</c:if>">
								<a href="caseMedias.htm?category=${category}&filter=${filter}&unitSelected=${unit.name}">${unit.name}</a>
							</li>
					</c:forEach>				
				</ul>
			</div>
		</div>
		<div class="foot">			
			<span class="show_more">更多<span class="fa fa-angle-down pointer-events-none"></span></span>
			<span class="show_less display-none">收起<span class="fa fa-angle-up pointer-events-none"></span></span>	
		</div>
	</div>	
	<div class="sub-group" id="selected">
		<div class="head"><span>已选条件:</span></div>
		<div class="body">
			<ul class="s-media-cg">				
				<li><span>${category}</span><c:if test='${not empty category}'><span class="fa fa-times" id="cancelCategory"></span></c:if></li>	
				<li><span>${unitSelected}</span><c:if test='${not empty unitSelected}'><span class="fa fa-times" id="cancelUnit"></span></c:if></li>							
			</ul>
		</div>
	</div>
</div> 
<div class="main-sort">
	<div class="sub-total">所有数据(<span>${medias.size() }</span>)</div>
	<div class="sub_sort">
		<ul class="s-media-cg">
			<li onclick="sort('DEFAULT')"><a href="#">默认</a></li>
			<li onclick="sort('DATE')"><a href="#">最新时间<label class="fa fa-sort"></label></a></li>
		</ul>
	</div>
</div>
<%--视频列表--%>
<div class="media-content">
	<div class="s-row">
		<ul class="s-media">
			<c:forEach items="${medias}" var="media">
				<li>
					<div class="thumbnail" onclick="play(this)">
						<div id="mediaId" class="display-none">${media.id }</div>
						<div id="mediaContent"><video src="${media.url}" style="width:95%;height:85%"></video></div>						
						<%-- <div class="caption">${media.title}</div> --%>
						<div id="mediaInfo">
							<div class="title">
								<span>${media.title}</span>							
							</div>
							<div id="details" style="padding-left:20px;padding-right:20px;border-top: 1px dashed #dedede;">
								<div style="float:left"><span>所属案件:</span><span>${media.caseName}</span></div>						
								<div style="float:right"><span>时间:</span><span>${media.updateDate}</span></div>								
							</div>
						</div>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>
<%--分页条--%>
<div class="s-row">
	<ul class="pagination" id="pagination"></ul>
</div>
<%--视频浏览--%>
<div id="playWindow">	
</div>

<script src="${ctx}/views/smart/assets/jqPaginator/jqPaginator.min.js"></script>

<script type="text/javascript">
	$("span.show_more").click(function(e){
		$(e.target).hide();
		var body="#"+$(e.target).parent().parent().attr("id")+" .body";
		$(body).addClass("expand-mode");		
		$(e.target).parent().children().eq(1).removeClass("display-none");
	});
	$("span.show_less").click(function(e){
		$(e.target).addClass("display-none");
		var body="#"+$(e.target).parent().parent().attr("id")+" .body";
		$(body).removeClass("expand-mode");
		$(e.target).parent().children().eq(0).show();
	});
	
	$("#cancelCategory").click(function(e){	
		//$(e.target).parent().empty();
		window.location.href = basePath + "/caseMedias.htm?filter=" + filter + "&unitSelected=" + unitSelected+ "&sort=" + sortValue;        
	});
	$("#cancelUnit").click(function(e){	
		//$(e.target).parent().empty();
		window.location.href = basePath + "/caseMedias.htm?filter=" + filter + "&category=" + category+ "&sort=" + sortValue;
	});
	
</script>

<script type="text/javascript">
    var currentPage = "${queryBean.page}";
    var pageSize = "${queryBean.pageSize}";
    var total = "${queryBean.total}";
    var filter = "${filter}";
    var category = "${category}";
    var unitSelected = "${unitSelected}";
    var sortValue="${sort}";
    $(".s-input").val(filter);
    
    //分页
    $.jqPaginator('#pagination', {
        //totalPages: parseInt(total/pageSize),
        currentPage: parseInt(currentPage),
        totalCounts: parseInt(total),
        pageSize: 6,
        visiblePages: 10,
        onPageChange: function (num, type) {
            if (currentPage != num) {
                window.location.href = basePath + "/caseMedias.htm?currentPage=" + num + "&filter=" + filter + "&category=" + category+ "&unitSelected=" + unitSelected+ "&sort=" + sortValue;
            }
        }
    });
    function search() {
        window.location.href = basePath + "/caseMedias.htm?filter=" + $(".s-input").val() + "&category=" + category+ "&unitSelected=" + unitSelected+ "&sort=" + sortValue;
    }
    function sort(e){
        window.location.href = basePath + "/caseMedias.htm?filter=" + filter + "&category=" + category+ "&unitSelected=" + unitSelected+ "&sort=" + e;
    }
    function play(e) {    	
    	var target=$(e);
    	var mediaId=target.children("#mediaId").html();
    			 
		smart.kendoui.window('#playWindow', {
			content:basePath + "/caseMedias/previewMedia.do?id="+mediaId,
            title: "视频点播",
            actions: ["Maximize", "Close"],
            width: "55%",
            height: "54%"
        }).center().open();
		
    }
</script>
</body>
