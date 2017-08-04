<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<body>
<jsp:include page="alarm.jsp"></jsp:include>
<link rel="stylesheet" href="${ctx}/views/pages/bdmRoomLayout/index.css">
<link rel="stylesheet" href="${ctx}/views/pages/overview/index.css">
<script src="${ctx}/views/smart/assets/bootstrap/js/bootstrap.min.js"></script>
<div class="content">
    <div class="col-md-2 content-left">
        <div id="ctnBdmPoliceStationListWrap"></div>
    </div>
    <div class="col-md-10 content-right">
        <div id="ctnBdmRoomWrap"></div>
        <div class="video_up"><i class="fa fa-caret-up"></i></div>
        <div id="ctnCaseMediaWrap" class="video-bttom" style="display: none;">
            <div class="video_down"><i class="fa fa-caret-down"></i></div>
            <div style="width: 90%;margin:0 auto;overflow-y:auto;height:80%;">
                <div class="col-md-6"><div id="videoDiv1" style="height: 100%;"><div class="title-center">VIDEO</div></div></div>
                <div class="col-md-6"><div id="videoDiv2" style="height: 100%;"><div class="title-center">VIDEO</div></div></div>
                <div class="col-md-6"><div id="videoDiv3" style="height: 100%;"><div class="title-center">VIDEO</div></div></div>
                <div class="col-md-6"><div id="videoDiv4" style="height: 100%;"><div class="title-center">VIDEO</div></div></div>
            </div>
        </div>
    </div>

</div>
<script type="text/javascript">
//正在播放的视频实体数组
var playingVideos = [];
//播放视频的DIV数组
var divTypes = [];
//定时刷新定位
var locateTimer;

Array.prototype.contains = function ( needle ) {
	for (i in this) {
		if (this[i] == needle) return true;
	}
	return false;
}
$(function(){
    $(".content").height($(window).height() - $(".content").offset().top);

    //获取树数据
    function getTreeData() {
        var policeStationsStr = '${policeStations }';
        var policeStations = JSON.parse(policeStationsStr);
        var data = [];
        $.each(policeStations, function(i,callback){
            var caseNodeValues = [],
                stationNode = new Object(),    //派出所节点
                caseNodes = [];   //案件节点
            stationNode.text = policeStations[i].name;
            stationNode.id = policeStations[i].id;
            stationNode.type = 'station';
            caseNodeValues = policeStations[i].recordDtos;
            $.each(caseNodeValues, function(j,callback){
                var caseNode = new Object(),
                    peopleNodeValues = [],
                    peopleNodes = [];    //涉案人节点
                caseNode.text = caseNodeValues[j].name;
                caseNode.id = caseNodeValues[j].id;
                caseNode.type = 'case';
                peopleNodeValues = caseNodeValues[j].peopleDtos;
                $.each(peopleNodeValues, function(i,callback){
                    var peopleNode = new Object();
                    peopleNode.type = 'people';
                    peopleNode.text = peopleNodeValues[i].name;
                    peopleNode.id = peopleNodeValues[i].id;
                    peopleNode.strapId = peopleNodeValues[i].strapId;
                    peopleNodes.push(peopleNode);
                });
                caseNode.items = peopleNodes;
                caseNodes.push(caseNode);
            });

            stationNode.items = caseNodes;
            data.push(stationNode);
        });
        return data;
    }

    //生成树
    $("#ctnBdmPoliceStationListWrap").kendoTreeView({
        dataSource: {
            data: getTreeData()
        },
        select: onTreeNodeSelect,
        loadOnDemand: false
    }).data("kendoTreeView");

    //树点击事件
    function onTreeNodeSelect(e){
    	window.clearInterval(locateTimer);
        var treeView =  $("#ctnBdmPoliceStationListWrap").data("kendoTreeView");
        var selectedNode = treeView.dataItem(e.node);
        if (selectedNode.type == 'people') {
        	locateTimer = setInterval(function(){dragRoomLayout(selectedNode.parent().parent().parent().parent().id, selectedNode.id)}, 10*1000);
        	dragRoomLayout(selectedNode.parent().parent().parent().parent().id, selectedNode.id);
        }
        if (selectedNode.type == 'station') {
        	locateTimer = setInterval(function(){dragRoomLayout(selectedNode.id, null)}, 10*1000);
        	dragRoomLayout(selectedNode.id, null);
        }
        if (selectedNode.type == 'case') {
        	locateTimer = setInterval(function(){dragRoomLayout(selectedNode.parent().parent().id, null)}, 10*1000);
        	dragRoomLayout(selectedNode.parent().parent().id, null);
        }

    }

    //获取房间布局
    function dragRoomLayout(handingAreaId, peopleId) {
    	var url = basePath +'/overviews/findRoomLayout/'+ handingAreaId + '.do';
    	if(peopleId) {
    		url = basePath +'/overviews/findRoomLayout/'+ handingAreaId + '.do?peopleId=' + peopleId
    	}
    	
        $.ajax({
            type: 'GET',
            url: url,
            success:function(result){
            	
            	var roomDtos = result.roomDtos;
            	var locateInfo = result.locateInfo;
            	var locateState = result.locateState;
            	if(locateState) {
            		var roomIds = [];
                	var peoples = [];
                	for(var key in locateInfo) {
                	    roomIds.push(key);
                        peoples.push(locateInfo[key]);  
                	} 
                	//显示办案区布局
                    $('#ctnBdmRoomWrap').empty();
                    for (var i = 0; i < roomDtos.length; i++) {
                        load_room(roomDtos[i]);
                    }
                    //显示定位信息
                    $.each(roomIds, function(i){
                   		var div = '<div class="popover right person_content" id="personContent'+peoples[i].id+'">' +
                           '        <div class="arrow"></div>' +
                           '        <h3 class="popover-title">人员信息</h3>' +
                           '        <div class="popover-content">' +
                           '          <p>姓名：' + peoples[i].name + '</p><p>证件号码：' +peoples[i].certificateNum+ '</p>' +
                           '        </div>' +
                           '      </div>';
                        $('#room' + roomIds[i]).append("<img src='views/smart/assets/smart/images/timg.gif' class='room_warning' /><img class='person_position' id='peopleId"+peoples[i].id+"' src='views/smart/assets/smart/images/person.png' />"+div);
                        $('#peopleId' + peoples[i].id).hover(function () {
                            $("#personContent" + peoples[i].id).toggle();
                        }); 
                    });
            	} else {
            		if(roomDtos.length > 0) {
            			smart.alert("获取定位信息失败！");
            		}
            		window.clearInterval(locateTimer);
            		//显示办案区布局
                    $('#ctnBdmRoomWrap').empty();
                    for (var i = 0; i < roomDtos.length; i++) {
                        load_room(roomDtos[i]);
                    }
            	}
            }
        });
    }

    //显示房间布局
    function load_room(room){
        if(room.position) {
            if(room.position.length !== 0 ){
                var text=room.name,
                    id=room.id,
                    type=room.roomType,
                    room_position= $.parseJSON(room.position),
                    w=room_position.w,
                    h=room_position.h,
                    x=room_position.x,
                    y=room_position.y;
                $("#ctnBdmRoomWrap").append('<div class="ui-widget-content" id="room'+ id +'" style="position: absolute; left: '+ x +'px; top: '+ y +'px; width: '+ w +'px; height: '+ h +'px;" onclick="videoPlay('+id+', \'' + text + '\')"><p>'+ text+'</p><img src="views/smart/assets/smart/images/'+ type +'.png"></div>');
            }
        }
    }
    $(".video_down").click(function(){
        $("#ctnCaseMediaWrap").slideUp();
        $("#ctnBdmRoomWrap").show();
        $('.video_up').show();
    });
    $('.video_up').click(function(){
    	$("#ctnCaseMediaWrap").height($(".content-right").height());
        $("#ctnCaseMediaWrap").slideDown();
        $("#ctnBdmRoomWrap").hide();
        $('.video_up').hide();
    });
});
//点击房间播放视频
function videoPlay(roomId, text) {
    var existVideo = false;
    $.each(playingVideos, function(i,callback){
        if(playingVideos[i].roomId == roomId) {
            existVideo = true;
        }
    });
    if(existVideo) {
        $('.video_up').trigger("click");
        return;
    }

    //获取播放地址
    var urls = [];
    $.ajax({
        url: "${ctx}/overviews/findCamerasByRoom/" + roomId + ".do",
        type: "get",
        async: false,
        dataType: "json",
        timeout: 10000,
        success: function(result) {
            var resultMap = JSON.parse(JSON.stringify(result));
            for(var key in resultMap) {
                if(resultMap[key] != '' && resultMap[key] != 'Failure') {
                    urls.push(resultMap[key]);
                }
            }
        },
        error: function(){
        	smart.alert("请求超时！");
        }
    });

    if(urls.length > 0) {
        $('.video_up').trigger("click");
        //新建一个正在播放的视频实体
        var playingVideo = {};
        playingVideo.roomId = roomId;
        playingVideo.divs = [];
        playingVideo.timerIds = [];
        var deleteVideos = [];

        if(playingVideos[0]) {
            var divs = [];
            for(var i = 1; i <= 4; i++ ) {
                if (!divTypes.contains('#videoDiv' + i)) {
                    divTypes.push('#videoDiv' + i);
                    divs.push('#videoDiv' + i);
                }
            }
            if(divs.length >= urls.length) {
                var diff = divs.length - urls.length;
                for(var i = 0; i < diff; i++) {
                    divs.pop();
                    divTypes.pop();
                }
            } else {
                var existDivLength = divs.length;
                for(var i = 0; i < playingVideos.length; i++) {
                    existDivLength = existDivLength + playingVideos[i].divs.length;
                    if (existDivLength <= urls.length) {
                        divs = divs.concat(playingVideos[i].divs);
                        deleteVideos.push(playingVideos[i]);
                    } else {
                        var diff = playingVideos[i].divs.length - (existDivLength - urls.length);
                        for(var j = 0; j < diff; j++) {
                            var d = playingVideos[i].divs.splice(0, 1);
                            divs.push(d[0]);
                            //删除原有的心跳定时器
                            var t = playingVideos[i].timerIds.splice(0, 1);
                            window.clearInterval(t);
                        }
                        break;
                    }
                }
            }

            playingVideo.divs = divs;
            $.each(divs, function(i,callback){
                //清空原来播放器的播放地址并删除原有播放器
                var existPlayer = flowplayer(divs[i]);
                if(existPlayer) {
                    if(existPlayer.engine.hlsjs) {
                        existPlayer.engine.hlsjs.stopLoad();
                    }
                    existPlayer.shutdown();
                }
                $(divs[i]).empty();
                flowplayer(divs[i], {
                    autoplay: true,
                    live: true,
                    clip: {
                        sources: [{ type: "application/x-mpegurl", src: urls[i] }],
                        title: text
                    },
                    muted : true
                });
            });
            //删除原有的心跳定时器
            $.each(deleteVideos, function(i,callback){
                playingVideos.splice(0, 1);
                var timerIds = deleteVideos[i].timerIds;
                $.each(timerIds, function(j,callback){
                    window.clearInterval(timerIds[j]);
                })
            });

        } else {
            var divs = [];
            //新建播放器
            for(var i = 1; i <= urls.length; i++) {
                divs.push("#videoDiv" + i);
                divTypes.push("#videoDiv" + i);
                $("#videoDiv" + i).empty();
                flowplayer("#videoDiv" + i, {
                    autoplay: true,
                    live: true,
                    clip: {
                        sources: [{ type: "application/x-mpegurl", src: urls[i-1] }],
                        title: text
                    },
                    muted : true
                });
            }
            playingVideo.divs = divs;
        }

        //新建心跳定时器
        $.each(urls, function(i,callback){
            var urlArray = urls[i].split('/');
            //心跳url
            var heartBeatUrl = "http://" + urlArray[2] + ":8888/video/heartbeat";

            var timerId = setInterval(function() {
                $.ajax({
                    url: heartBeatUrl,
                    type: "get",
                    async: false,
                    dataType: "jsonp",
                    jsonp: "callbackparam", //服务端用于接收callback调用的function名的参数
                    jsonpCallback: "success_jsonpCallback", //callback的function名称,服务端会把名称和data一起传递回来
                    data:{"cIp": urlArray[3].replace(/_/g, ".")},
                    success: function(json) {
                    },
                    error: function(){
                    }
                })
            }, 30000);
            playingVideo.timerIds.push(timerId);
        });
        playingVideos.push(playingVideo);
        
        //去掉分享按钮
        $('#ctnCaseMediaWrap .fp-header .fp-share').each(function() {
            $(this).remove();
        });
        //去掉右键菜单
        $('#ctnCaseMediaWrap .fp-player .fp-context-menu').each(function() {
            $(this).empty();
        });
        //去掉水印
        $('#ctnCaseMediaWrap .flowplayer a').each(function() {
        	if($(this).attr("href") == "https://flowplayer.org/hello"){
        		$(this).remove();
        	}
        });
    } else {
    	smart.alert('未找到摄像头或摄像头连接失败！');
    }
}
</script>
</body>