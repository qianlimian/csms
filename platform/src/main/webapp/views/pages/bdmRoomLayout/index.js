var room_position=[];
(function () {
    var IndexModule = smart.MultiIndexModule.extend({

        //构造函数
        init: function (options) {

            smart.MultiIndexModule.fn.init.call(this, options);
        },

        //初始化组件
        initComponents: function () {
            //调用父类方法初始化查询窗口、编辑页面、导航条等
            smart.MultiIndexModule.fn.initComponents.call(this);

            var self = this;

            //创建tabstrip
            self.tabstrip = $("#tabstrip").kendoTabStrip({
                animation: { open: { effects: "fadeIn"} }
            });

            //办案区索引grid
            smart.kendoui.grid("#indexGrid", {
                dataSource : {
                    url: basePath + "/bdmHandlingAreas/query.do"
                },
                columns : [
                    { field: "id",  width: 100, hidden: true},
                    { field: "name", type: "string", title: "办案区", width: 100 }
                ],
                change: function () {
                    room_position=[];
                    $('#snaptarget').children('.draggable').remove();
                    $("#snaptarget").ruler();
                    $.ajax({
                        type: 'GET',
                        url: basePath +'/bdmRoomLayout/query.do?areaId='+this.selectData().ids[0],
                        success:function(result){
                            $('#mainGrid').empty();
                            // result[0].id
                            var html='',arr=new Array(),max=0;
                            for(var i=0;i<result.length;i++){
                                switch (result[i].roomType)
                                {
                                    case "INSPECT":
                                        room_class="人身安全检查室";
                                        break;
                                    case "COLLECT":
                                        room_class="信息采集室";
                                        break;
                                    case "IDENTIFY":
                                        room_class="辨认室";
                                        break;
                                    case "AWAIT":
                                        room_class="候审室";
                                        break;
                                    case "INQUEST":
                                        room_class="讯问室";
                                        break;
                                    case "INQUIRY":
                                        room_class="询问室";
                                        break;
                                }
                                var room_load= load_room(result[i]);
                                if(result[i].id === room_load){
                                    html=html;
                                }else{
                                    html=html + '<div class="draggable ui-widget-content" style="width:100%;" id="room'+ result[i].id +'"><p>'+ result[i].name +'</p></div>';
                                }
                                // if ($.parseJSON(result[i].position).x > max) {
                                //     max = $.parseJSON(result[i].position).x;
                                // }
                            }
                            // var w = 0;
                            // $(".ui-resizable-autohide").each(function(){
                            //     w += parseInt($(this).width());//获取宽度。并累加
                            // });
                            // $('#snaptarget').css('min-width', w + max);
                            // $("#snaptarget").ruler();
                            var wh=$("#snaptarget").width(),gh=$("#snaptarget").height();

                            var title_div='<div class="k-grid-header title-room">\n' +
                                '                    <a class="k-link" href="#">房间列表</a>\n' +
                                '                </div>';
                            // $('#mainGrid').append(title_div +'<div id="draggable" class="ui-widget-content">走廊</div>'+ html);
                            $('#mainGrid').append(title_div+html);
                            $('#draggable').draggable({containment: '#snaptarget',stop: function(event,ui){
                                $('#draggable').resizable({
                                    minwidth:100,
                                    minheight:200,
                                    grid: [0, 0],
                                    maxHeight: gh,
                                    maxWidth: wh
                                });
                            }});
                            revert(result);
                        }
                    });
                },
                selectable: "single",
                pageable: {
                    pageSizes: [10 , 30 , 50 , 100 , 200],
                    refresh: false,
                    numeric: true,
                    input: false,
                    buttonCount: 10,
                    messages: {
                        previous: "上一页",
                        next: "下一页",
                        first: "第一页",
                        last: "最后一页",
                        display: "",
                        empty: "无数据",
                        page: "第",
                        of: "页/ {0} 页",
                        itemsPerPage: "条每页"
                    }
                }
            });
        },
        //取选择的办案区ID
        getHandlingAreaId: function () {
            var $row = this.indexGrid.select(),
                dataItem = this.indexGrid.dataItem($row);

            return dataItem? dataItem.id : null;
        },

        bindEvents: function () {
            smart.MultiIndexModule.fn.bindEvents.call(this);

            //事件绑定
            smart.bind('#' + this.containerId + ' #btnDoSave', [this.doSave, this]);
        },

        doSave: function () {
            for(var i=0;i<room_position.length;i++){
                $.ajax({
                    type: 'POST',
                    url: basePath +'/bdmRoomLayout/save.do',
                    data:JSON.stringify({
                        id: parseInt(room_position[i].id.replace('room','')),
                        position: JSON.stringify(room_position[i])
                    }),
                    contentType:'application/json;charset=utf-8',
                    success:function(result){
                        console.log("保存成功!");
                    }
                });
            }
        },

        //@overwrite
        initVars: function () {
            this.mainGrid = $("#mainGrid").data("kendoGrid");
            this.indexGrid = $("#indexGrid").data("kendoGrid");
        },
        //@overwrite
        resizeLayout:function () {
            var $indexGrid = this.indexGrid.wrapper,
                height = $(window).height(),
                gridHeight = height - 140;
            $indexGrid.height(gridHeight);
            $('.room-list').height(gridHeight);
            $('#snaptarget').height(gridHeight-70);

            smart.kendoui.fixGridHeight(indexGrid);
        }
    });

    new IndexModule({
        name : "SmartBdmRoomIndex",
        containerId : "ctnBdmRoomIndex",
        restUrl: "/bdmRoomLayout/",
        grid: ['mainGrid'],
        editModule : {
        },
        ymlModule: "BdmRoom"
    });
})();

//显示布局
function load_room(room){

    if(room.position) {
    	if(room.position.length !== 0 ){
            var text=room.name,
                position= $.parseJSON(room.position),
                id=room.id,
                type=room.roomType,
                w=position.w,
                h=position.h,
                x=position.x,
                y=position.y;
            $("#snaptarget").append('<div class="draggable ui-widget-content ui-draggable ui-resizable ui-resizable-autohide" id="room'+ id +'" style="position: absolute; left: '+ x +'px; top: '+ y +'px; width: '+ w +'px; height: '+ h +'px;"><p>'+ text+'</p><img src="views/smart/assets/smart/images/'+ type +'.png"><div class="close-room"><a onclick="revert_room('+id+','+"'"+text+"'"+');">×</a></div></div>');
            var found  =false;
            for(var i=0;i<room_position.length;i++){
                if (room_position[i] && room_position[i].id==='"room"'+ id){
                    found = true;
                    room_position[i]=position;
                    break;
                }
            }
            if(!found) {
                room_position.push(position);
            }

            return id;
        }
    }
     revert(room);
}
//删除room
function revert_room(roomid,text) {
     $("#room"+ roomid).remove();
    var html='<div class="draggable ui-widget-content ui-draggable" id="room'+ roomid +'" style="width:100%;"><p>'+ text +'</p></div>';
    $('.title-room').after(html);
    for (var k = 0; k < room_position.length; k++) {
        if (room_position[k] && room_position[k].id === 'room'+roomid) {
            room_position[k].h='';
            room_position[k].w='';
            room_position[k].x='';
            room_position[k].y='';
            break;
        }
    }
    revert(room_position);
}
//拖拽后更新room_position
function update_room(room_position,ui){
    var x_room = ui.position.left,
        y_room = ui.position.top,
        id_room = ui.helper.context.id,
        w_room = ui.helper.context.scrollWidth,
        h_room = ui.helper.context.scrollHeight;

    var position = {
        id:id_room,
        x:x_room,
        y:y_room,
        w:w_room,
        h:h_room
    };
    var found  =false;
    for(var i=0;i<room_position.length;i++){
        if (room_position[i] && room_position[i].id===id_room){
            found = true;
            room_position[i]=position;
            break;
        }
    }
    if(!found) {
        room_position.push(position);
    }
}
//初始化
function revert(room) {
    console.log(room);
    var wh=$("#snaptarget").width(),gh=$("#snaptarget").height();
    $( ".draggable" ).draggable({
        containment: '#snaptarget',
        scroll: false,
        start:function (event,ui) {
            var id_room = ui.helper.context.id,
                id=id_room.replace('room','');
            text_room = "'"+ui.helper.context.outerText+"'";
            $('#'+ui.helper.context.id).css('position','absolute');
            if($("#"+ id_room).find($('.close-room')).length<=0){
                $("#"+ id_room).css({'width':'120px','height':'150px'});
                $("#"+ id_room).append('<img src="views/smart/assets/smart/images/'+ room.roomType +'.png"><div class="close-room"><a onclick="revert_room('+id+','+text_room+');">×</a></div>');
            }
        },
        stop: function(event,ui){
        ui.helper.context.scrollWidth = 100;
        ui.helper.context.scrollHeight = 150;
        update_room(room_position,ui);
        $("#"+ ui.helper.context.id).resizable({
            autoHide: true,
            grid: [0, 0],
            minwidth:100,
            minheight:200,
            maxHeight: gh/2,
            maxWidth: wh/2,
            stop: function(eve,u){
                update_room(room_position,u);
            }
        });
    } });
}
