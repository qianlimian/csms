(function () {

    var IndexModule = smart.SingleIndexModule.extend({

        init: function (options) {
            smart.SingleIndexModule.fn.init.call(this, options);
        },

        initComponents: function () {
            //调用父类方法初始化查询窗口、编辑页面、导航条等
            smart.SingleIndexModule.fn.initComponents.call(this);
            var self = this;

            //左侧权限组
            smart.kendoui.grid($("#mainGrid"),
                $.extend(true, this.gridOptions(), {
                    selectable: "single",
                    columns: [
                        {field: "id", width: 100, hidden: true},
                        {field: "name", title: "用户组", width: 100},
                        {field: "desc", title: "描述", width: 100, mapping: "description"} //desc关键字错误
                    ]
                })
            );

            self.initTabStrip();
        },

        //初始化tabs
        initTabStrip: function () {
            var self = this;

            self.tabstrip = $("#tabstrip").kendoTabStrip({
                animation: { open: { effects: "fadeIn"} }
            });

            self._initUserGrid();
            self._initMenuTree();
            self._initOperateTree();
            self._initRoleSelect();
        },

        //用户配置
        _initUserGrid: function () {
            var self = this;

            self.userGrid = smart.kendoui.grid($("#userGrid"), {
                height: 800,
                columns : [
                    { field: 'id', hidden: true },
                    { field: "name", title: "姓名", width: 85 },
                    { field: "loginName", title: "登录名", width: 85 }
                ],
                autoBind: false,
                dataSource : {
                    url: basePath + '/smart/users/queryByGroupId.do'
                },
                toolbar: kendo.template($("#tbUserGrid").html()),
                command: {
                    onDelClick: function () {
                        return function (event) {
                            self.removeUsersFromGroup.call(self, event)
                        }
                    }(self)
                },
                filterable:false,
                groupable:false
            });
        },

        //菜单配置
        _initMenuTree: function () {
            var self = this,
                $treeView = self.$('#menusTreeView');

            $treeView.kendoTreeView({
                dataTextField: 'label',
                checkboxes: {
                    checkChildren: true
                },
                dataSource: JSON.parse($("#smartMenus").text())
            });
            self.menuTreeView = $treeView.data('kendoTreeView');
        },

        //操作权限
        _initOperateTree: function () {
            var self = this,
                $treeView = self.$('#operatesTreeView');

            $treeView.kendoTreeView({
                dataTextField: 'label',
                checkboxes: {
                    checkChildren: true
                },
                dataSource: JSON.parse($("#smartOperates").text())
            });
            self.operateTreeView = $treeView.data('kendoTreeView');
        },

        //角色配置
        _initRoleSelect: function () {
            var self = this;

            self.$('#multiSelect').multiSelect();
        },

        //grid自适应高度
        resizeLayout:function () {
            var self = this;
            smart.SingleIndexModule.fn.resizeLayout.call(self);

            var $userGrid = $("#userGrid"),
                $menusTreeView = $("#menusTreeView"),
                $operatesTreeView = $("#operatesTreeView"),
                $roleSelect = $("#roleSelect"),
                height = $(window).height() - $userGrid.offset().top - 10;

            $userGrid.height(height);
            $menusTreeView.height(height);
            $operatesTreeView.height(height);
            $roleSelect.height(height);

            smart.kendoui.fixGridHeight($userGrid);
        },

        bindEvents: function () {
            var self = this;
            smart.SingleIndexModule.fn.bindEvents.call(self);

            smart.bind('#btnDoNewUser', [this.initUserSelectWin, this]);
            smart.bind('#btnDoDeleteUser', [this.removeUsersFromGroup, this]);

            // 绑定选择用户处理事件
            smart.Event.bind('ITEM_SELECTED_EVENT', [this.addUsersToGroup, this]);

            // 绑定表格刷新事件
            self.mainGrid.bind("dataBound", function () {
                self.userGrid.tbody.find('tr').remove();
                self.doResetTree(self.menuTreeView);
                self.doResetTree(self.operateTreeView);
            });

            // 绑定表格行单击事件
            self.mainGrid.bind("smartRowChange", (function (e) {
                var groupId = e.data.rowData.id;
                self.loadUsers(groupId, 1);
                self.loadMenus(groupId);
                self.loadRoles(groupId);
                self.loadOperates(groupId);
            }));

            // 绑定保存事件
            self.$('#btnDoSave').click(function () {
                self.doSaveMenus();
                self.doSaveRoles();
                self.doSaveOperates();
            });
        },

        doResetTree: function (treeView) {
            if (!treeView) return;
            var items = treeView.dataSource.view();
            for (var i = 0; i < items.length; i++) {
                var node = items[i];
                node.set('checked', true);
                node.set('checked', false);
            }
        },

        //*********************************************************************************************
        loadUsers: function (groupId, page) {
            var self = this;
            var dataSource = self.userGrid.dataSource;
            dataSource.transport.parameterMap = function (model, type) {
                return {
                    groupId: groupId,
                    queryBean: JSON.stringify(model)
                }
            };
            if (page) {
                dataSource.page(page);
            } else {
                dataSource.read();
            }
        },

        initUserSelectWin: function () {
            var self = this,
                $tr = self.mainGrid.select(),
                dataItem = self.mainGrid.dataItem($tr);

            if (!dataItem) {
                smart.alert("请选择用户组");
                return;
            }

            //初始化选择window
            if (!self.userSelectWin) {
                self.userSelectWin = smart.kendoui.window('#ctnUserSelectWrap', {
                    content: basePath + "/smart/users/load.do"
                });
            }
            self.userSelectWin.center().open();
        },

        addUsersToGroup: function (data) {
            var self = this,
                $tr = self.mainGrid.select(),
                dataItem = self.mainGrid.dataItem($tr);

            if (!dataItem) {
                smart.alert("请选择用户组");
                return;
            }

            self.userSelectWin.close();

            //添加用户到组
            if (data.ids) {
                smart.ajax({
                    type: 'post',
                    data: {userIds: data.ids.join(',')},
                    dataType: 'text', ////@ResponseBody返回null
                    url: self.restUrl + dataItem.id + '/addUsers.do',
                    success: function (res) {
                        self.loadUsers(dataItem.id);
                    }
                })
            }
        },

        removeUsersFromGroup: function (event) {
            var self = this,
                $mtr = self.mainGrid.select(),
                dataItem = self.mainGrid.dataItem($mtr),
                deleteIds = [];

            if (!dataItem) {
                smart.alert("请选择用户组");
                return;
            }

            if (event) { //行内删除
                var $utr = $(event.target).closest("tr");
                var data = self.userGrid.dataItem($utr);
                deleteIds.push(data.id);
            } else { //多条删除
                var data = self.userGrid.selectData();
                deleteIds = data.ids;
            }

            if (deleteIds.length < 1) {
                smart.alert("请选择要移除的用户");
                return;
            }

            smart.confirm({
                message: "您确定要将该用户从组中移除?",
                buttons: [{
                    click: function () {
                        smart.ajax({
                            type: 'delete',
                            dataType: 'text', ////@ResponseBody返回null
                            url: self.restUrl + dataItem.id + '/removeUsers.do?userIds=' + deleteIds.join(','),
                            success: function (res) {
                                self.loadUsers(dataItem.id);
                            }
                        });
                    }
                }]
            });
        },
        //****************************************************************************************
        //****************************************************************************************
        loadMenus: function (groupId) {
            var self = this;
            $.get(self.restUrl + groupId + '/getMenus.do', function (res) {
                self._checkMenuTree(res);
            }, 'json');
        },

        _checkMenuTree: function (ids) {
            var self = this,
                treeView = self.menuTreeView,
                $checkboxes = treeView.element.find('li input:checkbox'),
                idArr = ids || [];

            self.doResetTree(treeView);

            $checkboxes.filter(function () {
                var $li = $(this).parents('li:first'), dataItem = treeView.dataItem($li);
                return dataItem.type == 'LEAF' && idArr.indexOf(dataItem.id) > -1;
            }).prop('checked', true);
            treeView.updateIndeterminate();
        },

        doSaveMenus: function () {
            var self = this,
                tr = self.mainGrid.select(),
                dataItem = self.mainGrid.dataItem(tr),
                treeView = self.menuTreeView,
                menuIds = self._getMenuIds(treeView);

            if (!dataItem) {
                smart.alert("请选择用户组");
                return;
            }

            smart.ajax({
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify(menuIds),
                dataType: 'text', ////@ResponseBody返回null
                url: self.restUrl + dataItem.id + '/saveMenus.do',
                success: function (res) {
                    //提示
                    self.notification.hide().show('保存成功！','success');
                }
            });
        },

        /**
         * @param treeView
         * @returns {{checked: Array, unchecked: Array}}
         */
        _getMenuIds: function (treeView) {
            var checkedNodes = [],
                uncheckedNodes = [],
                $nodes = treeView.element.find('li.k-item');

            $nodes.each(function (i, obj) {
                var $node = $(obj),
                    dataItem = treeView.dataItem($node),
                    $checkbox = $node.find('input:checkbox:first');

                var $parent = treeView.parent($node),
                    parent = treeView.dataItem($parent);

                //MODULE,GROUP,LEAF
                if (dataItem) {
                    if ($checkbox.is(':checked')) {
                        checkedNodes.push(dataItem.id);

                        //fixed: push父node的id
                        if (parent) {
                            if (checkedNodes.indexOf(parent.id) == -1) {
                                checkedNodes.push(parent.id);
                            }
                        }

                    } else {
                        uncheckedNodes.push(dataItem.id);
                    }
                }
            });

            return { checked: checkedNodes, unchecked: uncheckedNodes};
        },
        //****************************************************************************************
        //****************************************************************************************
        loadOperates: function (groupId) {
            var self = this;
            $.get(self.restUrl + groupId + '/getOperates.do', function (res) {
                self._checkOperateTree(res);
            }, 'json');
        },

        _checkOperateTree: function (ids) {
            var self = this,
                treeView = self.operateTreeView,
                $checkboxes = treeView.element.find('li input:checkbox'),
                idArr = ids || [];

            self.doResetTree(treeView);

            $checkboxes.filter(function () {
                var $li = $(this).parents('li:first'), dataItem = treeView.dataItem($li);
                return dataItem.type == 'OPERATE' && idArr.indexOf(dataItem.id) > -1;
            }).prop('checked', true);
            treeView.updateIndeterminate();
        },

        doSaveOperates: function () {
            var self = this,
                tr = self.mainGrid.select(),
                dataItem = self.mainGrid.dataItem(tr),
                treeView = self.operateTreeView,
                operateIds = self._getOperateIds(treeView);

            if (!dataItem || !dataItem.id) {
                smart.alert("请选择用户组");
                return;
            }

            smart.ajax({
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify(operateIds),
                dataType: 'text', ////@ResponseBody返回null
                url: self.restUrl + dataItem.id + '/saveOperates.do',
                success: function (res) {
                    //提示
                    self.notification.hide().show('保存成功！','success');
                }
            });
        },

        /**
         * @param treeView
         * @returns {{checked: Array, unchecked: Array}}
         */
        _getOperateIds: function (treeView) {
            var checkedNodes = [],
                uncheckedNodes = [],
                $nodes = treeView.element.find('li.k-item');

            $nodes.each(function (i, obj) {
                var $node = $(obj),
                    $checkbox = $node.find('input:checkbox:first'),
                    dataItem = treeView.dataItem($node);

                if (dataItem && dataItem.type.toUpperCase() == 'OPERATE') {
                    if ($checkbox.is(':checked')) {
                        checkedNodes.push(dataItem.id);
                    } else {
                        uncheckedNodes.push(dataItem.id);
                    }
                }
            });

            return { checked: checkedNodes, unchecked: uncheckedNodes};
        },
        //*********************************************************************************************
        loadRoles: function (groupId) {
            var self = this;
            $.get(self.restUrl + groupId + '/getRoles.do', function (roles) {
                self.$('#multiSelect').multiSelect('deselect_all');
                self.$('#multiSelect').multiSelect('select', roles);
            }, 'json');
        },

        doSaveRoles: function () {
            var self = this,
                tr = self.mainGrid.select(),
                dataItem = self.mainGrid.dataItem(tr),
                roles = self.$('#multiSelect').val();

            if (!dataItem) {
                smart.alert("请选择用户组");
                return;
            }

            smart.ajax({
                type: 'post',
                data: {roles: roles.join(",")},
                dataType: 'text', ////@ResponseBody返回null
                url: self.restUrl + dataItem.id + '/saveRoles.do',
                success: function (res) {
                    //提示
                    self.notification.hide().show('保存成功！','success');
                }
            });
        }
        //****************************************************************************************
    });

    new IndexModule({
        name : "SmartGroupIndex", //必需，Index模块名
        containerId : "ctnGroupIndex", //必需，Index模块的容器id
        restUrl: "/smart/groups/", //必需，请求的rest地址
        editModule : {
            name : "SmartGroupEdit", //必需，Edit模块名
            containerId : "ctnGroupEditWrap" //必需，Edit模块的容器id
        }
    });

})();









