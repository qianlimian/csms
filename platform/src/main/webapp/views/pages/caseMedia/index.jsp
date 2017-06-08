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
	.s-media-cg-wrap, .s-query-wrap {
		width: 50%;
		float: left;
	}
	.s-nav-prev, .s-nav-next {
		width: 20px;
		line-height: 30px;
		margin-left: 10px;
		float: left;
	}
	.s-media-cg {
		padding: 0;
		margin: 0;
		list-style: none;
	}
	.s-media-cg > li {
		border: 1px solid #ccc;
		border-radius: 4px;
		line-height: 30px;
		float: left;
	}
	.s-media-cg > li > a {
		display: block;
		width: 90px;
		color: #333;
		text-align: center;
		text-decoration: none;
	}
	.s-input-group > .s-input {
		width: 182px;
		height: 30px;
		line-height: 30px;
	}
	.s-input-group > .s-button {
		margin-left: -20px;
		height: 30px;
		line-height: 30px;
	}
	.s-media {
		padding: 20px;
		margin: 0;
		list-style: none;
	}
	.s-media > li {
		display: block;
		width: 33%;
		float: left;
	}
	.thumbnail {
		height: 220px;
		padding: 5px;
		margin: 10px;
		border: 1px solid #ccc;
		border-radius: 4px;
	}
</style>

<div class="s-row">
	<%--视频类别--%>
	<div class="s-media-cg-wrap">
		<a class="s-nav-prev"><i class="fa fa-chevron-left"></i></a>
		<ul class="s-media-cg">
			<c:forEach items="${cgMenu}" var="cg">
				<li class="<c:if test='${cg.name == category}'>k-state-selected</c:if>">
					<a href="caseMedias.htm?category=${cg.name}&filter=${filter}">${cg.name}</a>
				</li>
			</c:forEach>
		</ul>
		<a class="s-nav-next"><i class="fa fa-chevron-right"></i></a>
	</div>
	<%--视频搜索--%>
	<div class="s-query-wrap">
		<div class="s-input-group">
			<input class="s-input" type="text" value="${filter}" placeholder="请输入关键词" onKeyPress="search()">
			<a class="s-button"><i class="fa fa-search"></i></a>
		</div>
	</div>
</div>
<%--视频列表--%>
<div class="s-row">
	<ul class="s-media">
		<c:forEach items="${medias}" var="media">
			<li>
				<div class="thumbnail" onclick="play(this)">
					<video src="${media.url}" style="width:100%;height:100%"></video>
					<div class="caption">${media.title}</div>
				</div>
			</li>
		</c:forEach>
	</ul>
</div>
<%--分页条--%>
<div class="s-row">
	<ul class="pagination" id="pagination"></ul>
</div>
<%--视频浏览--%>
<div id="playWindow"></div>

<script src="${ctx}/views/smart/assets/jqPaginator/jqPaginator.min.js"></script>
<script type="text/javascript">
    var currentPage = "${queryBean.page}";
    var pageSize = "${queryBean.pageSize}";
    var total = "${queryBean.total}";
    var filter = "${filter}";
    var category = "${category}";
    //分页
    $.jqPaginator('#pagination', {
        //totalPages: parseInt(total/pageSize),
        currentPage: parseInt(currentPage),
        totalCounts: parseInt(total),
        pageSize: 6,
        visiblePages: 10,
        onPageChange: function (num, type) {
            if (currentPage != num) {
                window.location.href = basePath + "/caseMedias.htm?currentPage=" + num + "&filter=" + filter + "&category=" + category;
            }
        }
    });
    function search() {
        window.location.href = basePath + "/caseMedias.htm?filter=" + $(".s-input").val() + "&category=" + category;
    }
    function play(e) {
        $("#playWindow").html($(e).html());
        $("#playWindow video").attr("controls", "controls");
        $("#playWindow").kendoWindow({
            width: "700px",
            actions: ["Maximize", "Close"],
            title: "视频浏览",
        }).data("kendoWindow").center().open();
    }
</script>
</body>
