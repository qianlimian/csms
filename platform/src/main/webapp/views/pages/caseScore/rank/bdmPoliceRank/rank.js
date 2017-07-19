(function () {
    var RankModule = smart.SingleIndexModule.extend({

        //构造函数
        init: function (options) {
            smart.SingleIndexModule.fn.init.call(this, options);
        },

        //初始化组件
        initComponents: function () {
            //调用父类方法初始化查询窗口、编辑页面、导航条等
            smart.SingleIndexModule.fn.initComponents.call(this);
            smart.kendoui.grid($("#rankMainGrid"),
                $.extend(true, this.gridOptions(), {
                    dataSource: {
                        url: this.restUrl + "queryRank.do"
                    },
                    columns: [
                        {field: "policeId", width: 100, hidden: true},
                        {field: "policeName", type: "string", title: "办案民警", width: 100, sortable: false, mapping: "police_name_"},
                        {field: "policeStation", type: "string", title: "办案单位", width: 100, sortable: false, mapping: "police_station_name_"},
                        {field: "totalScore", type: "string", title: "总得分", width: 100, filterable: false}
                    ],
                    command: false
                })
            );
        },

        //绑定事件
        bindEvents: function () {
            smart.bind('#' + this.containerId + ' #btnDoShow', [this.doShow, this]);
        },

        //显示查看页面
        doShow: function () {
            var data = this.mainGrid.selectData();
            var editItems = data.items;
            if (editItems.length < 1) {
                smart.alert("请选择行");
                return false;
            }

            this._doShow(editItems);
        },

        //@overwrite
        _setTitle: function () {
            this.editWin.title("详情");
        }
    });

    new RankModule({
        name: "SmartPoliceScoreRank", //必需，Index模块名
        containerId: "ctnCaseScoreRank", //必需，Index模块的容器id
        restUrl: "/caseScores/rank/bdmPoliceRank/", //必需，请求的rest地址
        grid: 'rankMainGrid',
        editModule: {
            name: "SmartPoliceScoreDetail", //必需，Edit模块名
            containerId: "ctnScoreDetailWrap", //必需，Edit模块的容器id
            options: {
                width: '1000px',
                height: '650px',
                content: "/caseScores/rank/bdmPoliceRank/detail.do" //可省略，从后台load页面
            }
        }
    });
})();
