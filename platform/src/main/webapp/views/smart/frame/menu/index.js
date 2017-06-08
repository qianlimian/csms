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

            //创建TreeList
            var self = this;
            var dataSource = new kendo.data.TreeListDataSource({
                transport: {
                    read: {
                        url: self.restUrl + "findByCondition.do",
                        dataType: "json"
                    },
                    parameterMap: function(options, operation) {
                        if (operation == "read") {
                            //调用父类请求参数
                            var params = self.queryParams.call(self);
                            return {
                                condition: JSON.stringify(params)
                            };
                        }
                    }
                },
                schema: {
                    model: {
                        fields: {
                            id: { field: "id", type: "number", nullable: false },
                            parentId: {field: "parentId", nullable: true},
                            //code: { field: "code" },
                            name: { field: "name" },
                            plugin: { field: "plugin" },
                            type: { field: "type" },
                            url: { field: "url" },
                            desc: { field: "desc" },
                            displayOrder: { field: "displayOrder" }
                        },
                        expanded: true
                    }
                }
            });

            smart.kendoui.treeList("#mainGrid", {
                dataSource: dataSource,
                columns : [
                    { field: "id",  width: 100, hidden: true},
                    //{ field: "code", title: "编码", width: 100 },
                    { field: "name", title: "名称", width: 100 },
                    { field: "plugin", title: "插件", width: 100 },
                    { field: "type", title: "类型", width: 100 },
                    { field: "url", title: "URL", width: 200 },
                    { field: "desc", title: "描述", width: 100 },
                    { field: "displayOrder", title: "排序", width: 100 }
                ],
                toolbar: kendo.template($("#template").html()),
                filterable: false,
                resizable: true,
                editable: false,
                selectable: "multiple, row"
            });
        },

        //覆盖父类方法
        initVars:function () {
            this.mainGrid = $("#mainGrid").data("kendoTreeList");
        }

    });

    new IndexModule({
        name : "SmartMenuIndex", //必需，Index模块名
        containerId : "ctnMenuIndex", //必需，Index模块的容器id
        restUrl: "/menus/", //必需，请求的rest地址
        editModule : {
            name : "SmartMenuEdit", //必需，Edit模块名
            containerId : "ctnMenuEditWrap" //必需，Edit模块的容器id
        }
    });

})();
