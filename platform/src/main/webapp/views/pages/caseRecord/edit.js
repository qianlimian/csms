(function () {

    //编辑案件记录
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

            //case组件
            smart.kendoui.comboBox(this.$("#edit_caseType"), {
                dataSource: smart.Enums["com.bycc.enumitem.CaseType"].getData()
            });
            smart.kendoui.comboBox(this.$("#edit_caseStatus"), {
                dataSource: smart.Enums["com.bycc.enumitem.CaseStatus"].getData()
            });
            smart.kendoui.comboBox(this.$("#edit_caseHandle"), {
                dataSource: smart.Enums["com.bycc.enumitem.CaseHandle"].getData()
            });
            smart.kendoui.datePicker(this.$("#edit_occurDate"));
            smart.kendoui.datePicker(this.$("#edit_acceptDate"));
            smart.kendoui.datePicker(this.$("#edit_registerDate"));
            smart.kendoui.datePicker(this.$("#edit_startDate"));
            smart.kendoui.datePicker(this.$("#edit_closeDate"));

            //cascade下拉组件
            this.masterUnit = smart.kendoui.comboBox($("#edit_masterUnitId"), {
                dataSource: self._getPoliceStationData(),
                dataTextField: 'name',
                dataValueField: 'id',
                select: function (e){
                    var dataItem = this.dataItem(e.item);
                    self._setPoliceData(self.masterPolice, dataItem);
                }
            });
            this.slaveUnit = smart.kendoui.comboBox($("#edit_slaveUnitId"), {
                dataSource: self._getPoliceStationData(),
                dataTextField: 'name',
                dataValueField: 'id',
                select: function (e){
                    var dataItem = this.dataItem(e.item);
                    self._setPoliceData(self.slavePolice, dataItem);
                }
            });
            this.masterPolice = smart.kendoui.comboBox($("#edit_masterPoliceId"), {
                dataSource: self._getPoliceData(),
                dataTextField: 'userName',
                dataValueField: 'id'
            });
            this.slavePolice = smart.kendoui.comboBox($("#edit_slavePoliceId"), {
                dataSource: self._getPoliceData(),
                dataTextField: 'userName',
                dataValueField: 'id'
            });
        },

        //派出所DataSource
        _getPoliceStationData: function () {
            var array = [];
            $("#police_stations_select").children('option').each(function (i, obj) {
                array.push({name: $(obj).text(), id: $(obj).val()});
            });
            return array;
        },
        //民警DataSource
        _getPoliceData: function () {
            var array = [];
            $("#polices_select").children('option').each(function (i, obj) {
                array.push({userName: $(obj).text(), id: $(obj).val()});
            });
            return array;
        },
        //设置民警DataSource
        _setPoliceData: function (combo, item) {
            var policeList = new kendo.data.DataSource({
                transport: {
                    read: {
                        url: basePath + "/bdmPoliceStations/findSubsById.do?id=" + item.id,
                        dataType: "json"
                    }
                }
            });
            combo.value(null);
            combo.readonly(false);
            combo.setDataSource(policeList);
        },
        //设置民警默认DataSource
        _setDefaultData: function (combo) {
            var policeList = this._getPoliceData();
            combo.readonly(true);
            combo.setDataSource(policeList);
        },

		//载入当前item
        loadItem: function (item) {
            smart.SingleEditModule.fn.loadItem.call(this, item);

            this._setDefaultData(this.masterPolice);
            this._setDefaultData(this.slavePolice);

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
            if (item && item.id) {
                var video_videoLists = new kendo.data.HierarchicalDataSource({
                    transport : {
                        read : {
                            url : basePath + "/caseMedias/getRemoteMediaList.do?caseRecordId="+item.id,
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
                            $("#video_date").html(data.startDate);
                            $("#video_mediaPreview").attr("src",data.serverPath);
                            
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
                        url: basePath + "/caseRegister/query.do?caseRecordId=" + item.id
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

            smart.bind('#' + this.containerId + ' #btnDoShow', [this.doShow, this]);
            smart.bind('#' + this.containerId + ' #btnDoTreeViewFresh', [this.doTreeViewFresh, this]);

            smart.Event.bind('ITEM_SELECTED_EVENT', [this.itemSelectedHandler, this]);
        },

        //选择
        doShow: function () {
            smart.kendoui.window('#ctnSelectWrap', {
                content: basePath + "/caseRecords/load.do"
            }).center().open();
        },

        //刷新
		doTreeViewFresh:function(){		
			$("#video_mediaTreeView").data("kendoTreeView").dataSource.read();			
		},

        //选择回调事件
        itemSelectedHandler: function (item) {
           // this.viewModel.set(this.modelName, item);
           /* this.viewModel.set("caseRecord.id", "");*/
            this.viewModel.set("caseRecord.caseId", item.id);      
            this.viewModel.set("caseRecord.alarmCode",item.alarmCode);
            this.viewModel.set("caseRecord.caseCode",item.caseCode);            
        }
    });

    new EditModule({
        name: "SmartCaseRecordEdit", //必需，Edit模块名
        containerId: "ctnCaseRecordEdit", //必需，Edit模块的容器id
        restUrl: "/caseRecords/", //必需，请求的rest地址
        modelName: "caseRecord", //必需，model名
        model: { //必需，model用于MVVM
            caseRecord: {
                id: "",
                caseId: "",
                alarmCode: "",
                caseCode: "",
                caseName: "",
                caseSummary: "",
                caseType: "",
                caseStatus: "",
                caseHandle:"",
                suspect: "",
                acceptUnitId: "",
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
                startDate: "",
                insertDate:"",
                updateDate:"",
                note: ""
            }
        },
        ymlModule: "caseRecord"
    });

})();
