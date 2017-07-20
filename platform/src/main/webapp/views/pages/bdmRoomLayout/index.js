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

            //办案区索引grid
            smart.kendoui.grid("#indexGrid", {
                dataSource: {
                    url: basePath + "/bdmHandlingAreas/query.do"
                },
                columns: [
                    {field: "id", width: 100, hidden: true},
                    {field: "name", title: "办案区", width: 100}
                ],
                change: function () {
                    var dataItem = this.dataItem(this.select());
                    if (dataItem) {
                        $.ajax({
                            type: 'GET',
                            url: basePath + '/bdmRoomLayout/query.do?areaId=' + dataItem.id,
                            success: function (result) {
                                render(result);
                            }
                        });
                    }
                },
                selectable: "single",
                pageable: {
                    pageSizes: [10, 30, 50, 100, 200],
                    refresh: false,
                    numeric: false,
                    input: false,
                    buttonCount: 10
                }
            });
        },

        bindEvents: function () {
            smart.MultiIndexModule.fn.bindEvents.call(this);

            //事件绑定
            smart.bind('#' + this.containerId + ' #btnDoSave', [this.doSave, this]);
        },

        doSave: function () {
            var me = this;
            $.ajax({
                type: 'POST',
                url: basePath + '/bdmRoomLayout/save.do',
                contentType: 'application/json',
                data: JSON.stringify(room_position),
                success: function (result) {
                    me.notification.hide().success({ message: "操作成功！" });
                }
            });
        },

        //@overwrite
        initVars: function () {
            this.mainGrid = $("#mainGrid").data("kendoGrid");
            this.indexGrid = $("#indexGrid").data("kendoGrid");
        },

        //@overwrite
        resizeLayout: function () {
            var $indexGrid = this.indexGrid.wrapper,
                height = $(window).height(),
                gridHeight = height - 140;

            $indexGrid.height(gridHeight);
            $('.room-list').height(gridHeight);
            $('#snaptarget').height(gridHeight - 70);

            smart.kendoui.fixGridHeight($indexGrid);
        }
    });

    new IndexModule({
        name: "SmartBdmRoomIndex",
        containerId: "ctnBdmRoomIndex",
        restUrl: "/bdmRoomLayout/",
        grid: ['mainGrid'],
        editModule: {},
        ymlModule: "BdmRoom"
    });
})();

var room_position = [];
function render(result) {
    //清除房间列表
    $('#mainGrid').empty();
    $('#mainGrid').append('<div class="k-grid-header title-room"><a class="k-link" href="#">房间列表</a></div>');

    //清除画布
    $('#snaptarget').children('.draggable').remove();
    $("#snaptarget").ruler();

    room_position = [];
    for (var i = 0; i < result.length; i++) {
        var room = result[i],
            id = room.id,
            text = room.name,
            type = room.roomType;

        if (!room.position) {
            //显示房间列表
            $('#mainGrid').append('<div class="draggable ui-widget-content" id="room' + room.id + '" title="' + room.roomType + '"><p>' + room.name + '</p></div>');

            room_position.push({id: 'room' + id, h: '', w: '', x: '', y: ''});

        } else {
            var position = $.parseJSON(room.position),
                w = position.w,
                h = position.h,
                x = position.x,
                y = position.y;

            //画布显示房间
            $("#snaptarget").append('<div class="draggable resizable ui-widget-content" id="room' + id + '" style="position: absolute; left: ' + x + 'px; top: ' + y + 'px; width: ' + w + 'px; height: ' + h + 'px;"><p>' + text + '</p><img src="views/smart/assets/smart/images/' + type + '.png"><div class="close-room"><a onclick="remove_room(' + id + ',' + "'" + text + "'" + ',' + "'" + type + "'" + ');">×</a></div></div>');
            resizable();

            room_position.push({id: 'room' + id, h: h, w: w, x: x, y: y});
        }
        draggable();
    }
}
//删除room
function remove_room(roomid, roomname, roomtype) {
    $('#room' + roomid).remove();
    $('#mainGrid').append('<div class="draggable ui-widget-content" id="room' + roomid + '" title="' + roomtype + '"><p>' + roomname + '</p></div>');
    for (var i = 0; i < room_position.length; i++) {
        if (room_position[i].id === 'room' + roomid) {
            room_position[i].h = '';
            room_position[i].w = '';
            room_position[i].x = '';
            room_position[i].y = '';
        }
    }
    draggable();
}
//拖拽后更新位置
function update_room(ui) {
    var $element = ui.helper,
        context = ui.helper.context,
        id = context.id,
        position = ui.position,
        w = $element.width(),
        h = $element.height(),
        x = position.left,
        y = position.top;

    //fixed：判断房间是由列表还是画布拖拽的
    if ($element.closest("#snaptarget").length == 0) {
        x -= $("#mainGrid").outerWidth();
        y -= $("#toolbar").outerHeight();
    }

    for (var i = 0; i < room_position.length; i++) {
        if (room_position[i].id === id) {
            room_position[i].h = h;
            room_position[i].w = w;
            room_position[i].x = x;
            room_position[i].y = y;
        }
    }
}
//可拖拽初始化
function draggable() {
    $(".draggable").draggable({
        containment: '#snaptarget',
        scroll: true,
        start: function (event, ui) {
            var ui_id = ui.helper.context.id,
                ui_text = ui.helper.context.outerText,
                ui_type = ui.helper.context.title,
                id = ui_id.replace('room', '');

            if (!$("#" + ui_id).hasClass("resizable")) {
                $("#" + ui_id).addClass("resizable");
                $("#" + ui_id).css({'position': 'absolute', 'width': '110px', 'height': '150px', 'z-index': '90'});
                $("#" + ui_id).append('<img src="views/smart/assets/smart/images/' + ui_type + '.png"><div class="close-room"><a onclick="remove_room(' + id + ',' + "'" + ui_text + "'" + ',' + "'" + ui_type + "'" + ');">×</a></div>');
                resizable();
            }
        },
        stop: function (event, ui) {
            update_room(ui);
        }
    });
}
//可调整初始化
function resizable() {
    var wh = $("#snaptarget").width(),
        gh = $("#snaptarget").height();
    $(".resizable").resizable({
        autoHide: true,
        grid: [20, 20],
        minwidth: 100,
        minheight: 200,
        maxHeight: gh / 2,
        maxWidth: wh,
        stop: function (event, ui) {
            update_room(ui);
        }
    });
}
