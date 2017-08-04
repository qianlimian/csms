(function () {
    var IndexModule = smart.SingleIndexModule.extend({

        //构造函数
        init: function (options) {
            smart.SingleIndexModule.fn.init.call(this, options);
        },

        //初始化组件
        initComponents: function () {
            //调用父类方法初始化查询窗口、编辑页面、导航条等
            smart.SingleIndexModule.fn.initComponents.call(this);
            smart.kendoui.grid(this.$("#mainGrid"),
                $.extend(true, this.gridOptions(), {
                    columns: [
                        {field: "id", width: 100, hidden: true},
                        {field: "title", type: "string", title: "标题", width: 100},
                        {field: "content", type: "string", title: "投诉内容", width: 100},
                        {field: "name", type: "string", title: "投诉人姓名", width: 100, mapping:'name'},
                        {field: "tel", type: "string", title: "电话", width: 100, mapping:'tel'},
                        {field: "email", type: "string", title: "邮箱", width: 150, mapping:'email'},
                        {field: "replyStatus", type: "enum", title: "回复状态", width: 100, values: smart.Enums["com.bycc.enumitem.ReplyStatus"].getData()},
                        {field: "replyType", type: "enum", title: "回复方式", width: 100, values: smart.Enums["com.bycc.enumitem.ReplyType"].getData()},
                        {field: "result", type: "string", title: "投诉处理结果", width: 150},
                        {field: "insertDate",type: "date", title: "插入时间", width: 100, format: '{0:yyyy-MM-dd HH:mm:ss}'},
                        {field: "updateDate", type: "date", title: "更新时间", width: 100, format: '{0:yyyy-MM-dd HH:mm:ss}'}
                    ],
                    editable: false,
                    command:false
                })
            );
        },

        //绑定事件
        bindEvents: function () {
            //调用父类方法绑定增删改查
            smart.SingleIndexModule.fn.bindEvents.call(this);

            // 回复按钮
            smart.bind('#' + this.containerId + ' #btnShowReply', [this.showReplyPage, this]);

            // 导出记录
            smart.bind('#' + this.containerId + ' #btnExport', [this.exportExcel, this]);

            // 回复保存后处理事件
            smart.Event.bind('REPLY_SAVED_EVENT', [this.replyHandler, this]);
        },

        // 显示回复页面
        showReplyPage: function(){
            var selectedItem = this.getSelectItem();
            if (selectedItem) {
                this.replyWindow = smart.kendoui.window('#ctnReplyWrap', {
                    title: '投诉回复',
                    content: basePath + "/complaints/showReply.do?id=" + selectedItem.id,
                    width: "60%",
                    height: "55%",
                    animation: true
                });
                this.replyWindow.center().open();
            }
        },

        getSelectItem: function () {
            var $row = this.mainGrid.select(),
                item = this.mainGrid.dataItem($row);
            if (!item) smart.alert("请选择一条记录");
            return item;
        },

        replyHandler: function(){
            this.replyWindow.close();
            this.doQuery();
            this.notification.hide().success({ message: "回复成功！" });
        },

        exportExcel: function () {
            var ds = this.mainGrid.dataSource;
            // 导出时不分页，将'pageSize'设置成总记录数
            window.location.href='complaints/export.do?queryBean='+JSON.stringify({'pageSize':ds._total,'skip':ds._skip,'page':ds._page,'filter':ds._filter});
        }
    });

    new IndexModule({
        name: "SmartComplaintIndex", //必需，Index模块名
        containerId: "ctnComplaintIndex", //必需，Index模块的容器id
        restUrl: "/complaints/", //必需，请求的rest地址
        editModule: {}
    });
})();
