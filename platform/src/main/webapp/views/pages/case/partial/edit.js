(function () {
    var EditModule = smart.SingleEditModule.extend({
        //构造函数
        init: function (options) {
            smart.SingleEditModule.fn.init.call(this, options);
        },

        //初始化组件
        initComponents: function () {
            //创建tabstrip
            var self = this;
            self.tabstrip = $("#tabstrip").kendoTabStrip({
                animation: { open: { effects: "fadeIn"} }
            });   
        },
        
        //载入当前item
        loadItem: function (item) {
            smart.SingleEditModule.fn.loadItem.call(this, item);

            this._loadVideo(item);
            this._loadTable(item);
        },

        //载入视频
        _loadVideo: function (item) {
            if($('#video_mediaTreeView').data('kendoTreeView')){
                $('#video_mediaTreeView').data('kendoTreeView').destroy();
                $("#video_mediaTreeView").empty();
            }
            $("#video_mediaName").html("");
            $("#video_category").html("");
            $("#video_date").html("");
            $("#video_roomName").html("");
            if (item && item.id) {
                var video_videoLists = new kendo.data.HierarchicalDataSource({
                    transport : {
                        read : {
                            url : basePath + "/caseMedias/getRemoteMediaList.do?caseRecordId="+item.caseRecordId,
                            dataType : "json"
                        }
                    },
                    schema : {
                        model : {
                            id: "mappingPath",
                            hasChildren : "hasChildren"
                        }
                    }
                });
                $("#video_mediaTreeView").kendoTreeView({
                    dataSource : video_videoLists,
                    dataTextField : "label",
                    select: function(e){
                        var data = $('#video_mediaTreeView').data('kendoTreeView').dataItem(e.node);
                        if(!data.hasChildren){
                           $("#video_mediaName").html(data.label);
                            $("#video_category").html(data.mediaCategory);
                            $("#video_roomName").html(data.roomCategory);
                            $("#video_date").html(data.startDate);
                            $("#video_mediaPreview").attr("src",basePath+"/"+data.serverPath);
                        }
                    }
                });
            }
        },

        //载入登记表
        _loadTable: function (item) {
            $('#preview').attr('src', '');
            $('#indexGrid').empty();
            if(item && item.id) {
                var casePeopleGrid = smart.kendoui.grid("#indexGrid", {
                    dataSource : {
                        url: basePath + "/caseRegister/query.do?caseRecordId=" + item.caseRecordId
                    },
                    columns : [
                        {field: "id", width: 50, hidden: true},
                        {field: "name", type: "string", title: "涉案人姓名", width: 100}
                    ],
                    filterable: false,
                    selectable: "single",
                    height: 540,
                    pageable: {
                        refresh: false,
                        input: false
                    }
                });
                //绑定单击事件
                casePeopleGrid.bind('smartRowClick', function (e) {
                    var data = e.data.rowData;
                    $('#preview').attr("src", basePath + "/caseRegister/print.do?casePeopleId=" + data.id);
                })
            } else {
                smart.kendoui.grid("#indexGrid", {
                    columns : [
                        {field: "id", width: 50, hidden: true},
                        {field: "name", type: "string", title: "涉案人姓名", width: 100}
                    ],
                    filterable: false,
                    selectable: "single",
                    height: 540,
                    pageable: {
                        refresh: false,
                        input: false
                    }
                });
            }
        },

         //绑定事件
        bindEvents: function () {
            //调用父类方法绑定增删改查
            smart.SingleEditModule.fn.bindEvents.call(this);
			smart.bind('#' + this.containerId + ' #btnDoTreeViewFresh', [this.doTreeViewFresh, this]); 
        },

    	//刷新
		doTreeViewFresh:function(){		
			$("#video_mediaTreeView").data("kendoTreeView").dataSource.read();			
		}
    });

    new EditModule({
        name: "SmartCaseEdit",
        containerId: "ctnCaseEdit",
        restUrl: "/cases/",
        modelName: "case",
        model: {
            case: {
                id: "",
                caseRecordId:"",
                alarmCode: "",
                caseCode: "",
                caseName: "",
                caseSummary: "",
                caseType: "",
                caseStatus: "",
                suspect: "",
                acceptUnit: "",
                acceptPoliceId: "",
                masterUnitId: "",
                masterUnit: "",
                masterPoliceId: "",
                masterPoliceName: "",
                slaveUnitId: "",
                slaveUnit: "",
                slavePoliceId: "",
                slavePoliceName: "",
                occurDate: "",
                acceptDate: "",
                registerDate: "",
                closeDate: "",
                startDate:"",
                insertDate:"",
                updateDate:"",
                note:""
            }
        },
        ymlModule: "case"
    });
})();
