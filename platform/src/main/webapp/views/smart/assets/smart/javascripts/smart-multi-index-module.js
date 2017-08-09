/**
 * 主子表索引页面模板，依赖 smart-index-module.js
 * = require ./smart-index-module.js
 */
(function(){
    if (!window.smart) {
        window.smart = {};
    }

    smart.MultiIndexModule = smart.IndexModule.extend({

		//构造函数
        init: function (options) {
            if (!options["editModule"]) {
                alert("smart.MultiIndexModule创建失败，缺少必要参数： editModule");
            }
            smart.IndexModule.fn.init.call(this, options);
        },


        //新增
        doNew: function () {
            this._doShow(null);
        },

        //编辑
        doEdit: function (event) {
            var editItems = [];

            //行内编辑
            if (event) {
                var tr = $(event.target).closest("tr");
                var data = this.mainGrid.dataItem(tr);
                editItems.push(data);
                //多条编辑
            } else {
                var data = this.mainGrid.selectData();
                editItems = data.items;
            }

            if (editItems.length < 1) {
                smart.alert("请选择要编辑的行");
                return false;
            }

            this._doShow(editItems);
        },

        //删除
        doDelete: function (event) {
            var self = this,
                deleteIds = [];

            // 行内删除
            if (event) {
                var tr = $(event.target).closest("tr");
                var data = this.mainGrid.dataItem(tr);
                deleteIds.push(data.id);
                //多条删除
            } else {
                var data = this.mainGrid.selectData();
                deleteIds = data.ids;
            }

            if (deleteIds.length < 1) {
                smart.alert("请选择要删除的行");
                return false;
            }

            smart.confirm({
                message: "您确定要删除" + (deleteIds.length > 1 ? "所选" + deleteIds.length : "该") + "条记录?",
                buttons: [{
                    click: function () {
                        //批量删除
                        smart.ajax({
                            type: 'delete',
                            dataType: 'text', //@ResponseBody返回null
                            url: self.restUrl + 'delete.do?ids=' + deleteIds.join(','),
                            success: function (res) {
                                self.doQuery();

                                //提示
                                self.notification.hide()
                                self.notification.success({ message: '删除成功！' });
                            }
                        });
                    }
                }]
            });
        },

        //显示编辑页面
        _doShow: function (items) {
            this.navBar.setItems(items);
            this._setTitle(this.navBar.title());
            this._loadItem(this.navBar.getCurrItem());
            this.editWin.center().open();
        },

        //设置编辑页面title
        _setTitle: function (title) {
            this.editWin.title(title);
        },

        //编辑页面--载入当前item
        _loadItem: function (item) {
            var editModule = smart.Module.getModule(this.editModule.name);
            if (editModule) editModule.loadItem(item);
        },

        //编辑页面--新增回调函数
        _itemAddHandler: function () {
            this.navBar.addItem(null);
            this._setTitle(this.navBar.title());
        },

        //编辑页面--保存回调函数
        _itemSaveHandler: function (item) {
            this.navBar.updateCurrItem(item);
            this._setTitle(this.navBar.title());
            this._loadItem(this.navBar.getCurrItem());
            this.doQuery();

            //提示
            this.notification.hide();
            this.notification.success({ message: '保存成功！' });
        },

        //编辑页面--删除回调函数
        _itemRemoveHandler: function () {
            this.navBar.removeCurrItem();
            this._setTitle(this.navBar.title());
            this._loadItem(this.navBar.getCurrItem());
            this.doQuery();

            //提示
            this.notification.hide();
            this.notification.success({ message: '删除成功！' });
        },

		//初始化组件
        initComponents: function () {
            smart.IndexModule.fn.initComponents.call(this);

            var self = this,
                editModule = self.editModule,
                editContainerId = editModule.containerId,
                editOptions = editModule.options || {
                        width : '800px',
                        height : '450px'
                    };

            //编辑窗口
            if (editOptions.content) {
                editOptions.content = basePath + editOptions.content; //可以从其他模块加载
            } else {
                editOptions.content = self.restUrl + "edit.htm"; //默认从当前模块加载
            }
            this.editWin = smart.kendoui.window('#' + editContainerId, editOptions);

            //导航条
            this.navBar = new smart.NavBar('#' + editContainerId, {
                onNext: function (item, title) {
                    self._setTitle(title);
                    self._loadItem(item);
                    return true;
                },
                onPrev: function (item, title) {
                    self._setTitle(title);
                    self._loadItem(item);
                    return true;
                }
            });

        },

        //默认的mainGrid参数
        mainGridOptions: function () {
            var self = this;
            return {
                height: 600,
                dataSource : {
                    url: self.restUrl + "query.do"
                },
                toolbar: self.$("#template").length == 0? false : kendo.template(self.$("#template").html()),
                command: {
                    onEditClick: this.can("edit") && function () {
                        return function (event) {
                            self.doEdit.call(self, event)
                        }
                    }(self),
                    onDelClick: this.can("delete") && function () {
                        return function (event) {
                            self.doDelete.call(self, event)
                        }
                    }(self)
                },
                change: function () {
                    var dataItem = this.dataItem(this.select());
                    if (dataItem) {
                        self.subGrid.dataSource.read({id: dataItem.id});
                    }
                }
            }
        },

        //默认的subGrid参数
        subGridOptions: function () {
            var self = this;
            return {
                height: 300,
                dataSource : {
                    url: self.restUrl + "findSubsById.do"
                },
                autoBind: false,
                filterable: false,
                groupable: false,
                pageable: false
            }
        },

        //初始化变量
        initVars: function () {
            smart.IndexModule.fn.initVars.call(this);

            this.subGrid = $("#" + this.gridName[1]).data("kendoGrid");
        },

        //mainGrid && subGrid自适应高度
        resizeLayout:function () {
            var $mainGrid = this.mainGrid.wrapper,
                $subGrid = this.subGrid.wrapper,
                height = $(window).height() - $mainGrid.offset().top;

            $mainGrid.height(height*3/4);
            $subGrid.height(height/4);

            smart.kendoui.fixGridHeight($mainGrid);
            smart.kendoui.fixGridHeight($subGrid);
        },

        bindEvents: function () {
            smart.IndexModule.fn.bindEvents.call(this);

            //事件绑定
            smart.bind('#' + this.containerId + ' #btnDoNew', [this.doNew, this]);
            smart.bind('#' + this.containerId + ' #btnDoEdit', [this.doEdit, this]);
            smart.bind("#" + this.containerId + " #btnDoDelete", [this.doDelete, this]);

            //事件名前缀
            var eventPrefix = this.eventPrefix || "";
            //自定义事件绑定
            smart.Event.bind(eventPrefix + '_ITEM_ADD_EVENT', [this._itemAddHandler, this]);
            smart.Event.bind(eventPrefix + '_ITEM_SAVE_EVENT', [this._itemSaveHandler, this]);
            smart.Event.bind(eventPrefix + '_ITEM_REMOVE_EVENT', [this._itemRemoveHandler, this]);
        }
    });
})();
