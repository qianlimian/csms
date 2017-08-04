/* 
 * @description kendoui框架增强
 */
 
(function () {
    // kendo ui 配置国际化
    kendo.culture("zh-CN");

    if (!window.smart) {
        window.smart = {};
    }

    smart.kendoui = {};

	//增强的kendoWindow
    smart.kendoui.window = function (selector, options) {
        var config = $.extend(true, {
            width: "60%",
            height: "60%",
            actions: ["Maximize","Close"],
            visible: false,
            modal: true,
            animation: false,
            content: "",
            iframe: false
        }, options);


        return $(selector).kendoWindow(config).data('kendoWindow');
    };

    var _extendColumnFilterAndSort = function (columns) {
        var _columns = [];
        for (var i in columns) {
            var column = columns[i];
            //smart框架添加属性mapping, 用于foreignkey column的映射，以转换过滤和排序参数
            if (column.field && column.mapping) {
                if (column.filterable != false) {
                    column.filterable = $.extend(true, {
                        cell: {
                            dataTextField: column.mapping
                        }
                    }, column.filterable);
                }
                if (column.sortable != false) {
                    column.sortable = {
                        dataTextField: column.mapping
                    };
                }
            }
            _columns.push(column);
        }
        return _columns;
    };

    var _createDataSource = function (columns, options, pageable) {
        options = $.extend(true, {
            transport: {
                read: {
                    url: options.url,
                    dataType: "json"
                },
                parameterMap: function(data, operation) {
                    if (operation == "read") {

                        if (pageable !== false) {

                            var queryParam = {
                                queryBean : JSON.stringify(data)
                            };

                            if (options.parameterMap) {
                                queryParam.condition = JSON.stringify(options.parameterMap());
                            }

                            return queryParam;
                        } else {
                            return $.extend(data, {});
                        }
                    }
                }
            },
            schema: {
                model: {
                    fields: _createFields(columns)
                }
            }
        }, options);

        if (pageable == false) {
            return new kendo.data.DataSource($.extend(true, {
                serverPaging: false
            }, options));
        } else {
            return new kendo.data.DataSource($.extend(true, {
                pageSize: 30,
                serverPaging: true,
                serverSorting: true,
                serverFiltering: true,
                serverGrouping: false,
                schema: {
                    data: "items",
                    total: "total"
                }
            }, options));
        }
    };

    var _createFields = function (columns) {
        var fields = {};
        for (var i = 0; i < columns.length; i++) {
            var col = columns[i],
                field = col.field,
                type = col.type;
            if (field) {
                fields[field] = {};
                if (type) {
                    fields[field].type = type;
                }
            }
        }
        return fields;
    };

    var _createCommand = function (options) {
        var command = [];

        if (options.onEditClick) {
            command.push({
                name: "smart_edit",
                text: "<span class='k-icon k-edit' title='编辑'></span>",
                click: options.onEditClick
            });
        }

        if (options.onDelClick) {
            command.push({
                name: "smart_destroy",
                text: "<span class='k-icon k-delete' title='删除'></span>",
                click: options.onDelClick
            });
        }

        return { command: command, width: 75 }
    };

    smart.kendoui.filterMessages = {
        info: '请输入过滤条件：',
        selectValue: "请选择",
        and: "且",
        or: "或",
        filter: "过滤",
        clear: "清除过滤器"
    };

    smart.kendoui.filterOperators = {
        string: {
            contains: "包含",
            doesnotcontain: "不包含",
            eq: "等于",
            neq: "不等于"
            // startswith: "开始于",
            // endswith: "结束于"
        },
        number: {
            eq: "等于",
            neq: "不等于",
            gte: "大于等于",
            gt: "大于",
            lte: "小于等于",
            lt: "小于"
        },
        date: {
            eq: "等于",
            // neq: "不等于",
            gte: "起始于",
            // gt: "起始于",
            lte: "结束于"
            // lt: "结束于"
        },
        enums: {
            eq: "等于"
            // neq: "不等于"
        }
    };

    smart.kendoui.pageMessages = {
        previous: "上一页",
        next: "下一页",
        first: "第一页",
        last: "最后一页",
        display: " {0} - {1} /{2}条",
        empty: "无数据",
        page: "第",
        of: "页/ {0} 页",
        itemsPerPage: "条每页",
        refresh: '刷新'
    };

	/* 
	 * @description 增强的kendoGrid
	 * 1.扩展数据源
	 * 2.增加删改按钮
	 * 3.增加删改样式
	 * 4.默认配置
 	 */
    smart.kendoui.grid = function (selector, options) {

        if (options.columns && options.dataSource) {

            //扩展column的过滤和排序功能
            options.columns = _extendColumnFilterAndSort(options.columns);

            //根据columns创建datasource的schema
            options.dataSource = _createDataSource(options.columns, options.dataSource, options.pageable);

            //添加columns的行内按钮
            if (options.command) options.columns.unshift(_createCommand(options.command));
        }

        //grid可编辑时添加删改样式，用于主子表的保存
        if (options.editable) {
            options = $.extend(true, {
                edit: function (e) {
                    var $tr = e.container.parent("tr");
                    if (e.model.id && $tr.hasClass('s-grid-row-del')) {
                        this.closeCell();
                    }
                },
                save: function (e) {
                    if (e.model.id && e.model.dirty) {
                        e.container.parent('tr').addClass('s-grid-row-update');
                    }
                }
            }, options, {editable: {confirmation: false}} );
        }

        var config = $.extend(true, {
            editable: false,
            filterable: {
                extra: false,
                mode: "row", //行模式
                messages: smart.kendoui.filterMessages,
                operators: smart.kendoui.filterOperators
            },
            height: 600,
            pageable: {
                pageSizes: [10 , 30 , 50 , 100 , 200],
                refresh: true,
                numeric: true,
                input: true,
                buttonCount: 10,
                messages: smart.kendoui.pageMessages
            },
            reorderable: true,
            resizable: true,
            selectable: "multiple",
            sortable: {
                mode: "multiple"
            },
            navigatable: true
        }, options);

        var grid = $(selector).kendoGrid(config).data('kendoGrid');
        grid.bindSmartEvents();

        return grid;
    };

    smart.kendoui.treeList = function (selector, options) {
        return $(selector).kendoTreeList(options).data('kendoTreeList');
    };

	//增强的kendoCombobox
    //1.默认请求本地数据源，如果配置了url或者dicUrl，会请求后台数据源
    //2.配置了url或者dicUrl是简写的方法，也可以自己实现dataSource
    smart.kendoui.comboBox = function (selector, options) {
        var config = $.extend(true, {
            autoBind: true,
            placeholder: '--请选择--',
            filter: "contains",
            dataTextField: 'text',
            dataValueField: 'value',
            valuePrimitive: true,
            change : function (e) {
                var item = this.dataItem();
                if (!item) this.value(null);
            }
        }, options);

        //remoteCombo
        if (options.url) {
            config = $.extend(true, config, {
                autoBind: true,
                dataSource: {
                    transport: {
                        read: {
                            url: options.url,
                            dataType: "json"
                        }
                    }
                }
            });
        }
        if (options.dicUrl) {
            config = $.extend(true, config, {
                autoBind: true,
                dataValueField: 'key',
                dataTextField: 'value',
                dataSource: {
                    transport: {
                        read: {
                            url: basePath + "/dictionary/entry.do?dicId=" + options.dicUrl,
                            dataType: "json"
                        }
                    }
                }
            });
        }

        var comboBox = $(selector).kendoComboBox(config).data('kendoComboBox');

        comboBox.input
            .on("focusin.kendoComboBox", function () { comboBox.open() })
            .on("click.kendoComboBox", function () { comboBox.open() });

        smart.kendoui._removeSpanClass(selector);

        return comboBox;
    };

	//增强的kendoDropdownList
    smart.kendoui.dropDownList = function (selector, options) {
        var config = $.extend(true, {
            autoBind: false,
            optionLabel: '--请选择--',
            dataTextField: 'text',
            dataValueField: 'value',
            valuePrimitive: true
        }, options);

        var dropDownList = $(selector).kendoDropDownList(config).data('kendoDropDownList');

        dropDownList.wrapper
            .on("focusin.kendoDropdownList", function () { dropDownList.open() })

        smart.kendoui._removeSpanClass(selector);

        return dropDownList;
    };

    //增强的自定义datepicker
    smart.kendoui.datePicker = function (selector, options) {
        options = $.extend(true, {
            format: 'yyyy-MM-dd'
        }, options);

        var datePicker = $(selector).kendoDatePicker(options).data('kendoDatePicker');

        datePicker.element
            .on("focusin.kendoDatePicker", function () { datePicker.open() })
            .on("click.kendoDatePicker", function () { datePicker.open() });

        smart.kendoui._removeSpanClass(selector);

        return datePicker;
    };

    //增强的自定义numericTextBox
    smart.kendoui.numericTextBox = function (selector, options) {
        var numericTextBox = $(selector).kendoNumericTextBox(options).data('kendoNumericTextBox');

        smart.kendoui._removeSpanClass(selector);

        return numericTextBox;
    };

	//增强的自定义searchBox
    smart.kendoui.searchBox = function (selector, options) {
        options = $.extend(true, {
            autoBind: false,
            placeholder: '--请选择--',
            filter: 'contains',
            ignoreCase: false,
            grid: {
                dataSource: {
                    pageSize: 10,
                    serverPaging: true,
                    serverSorting: false,
                    serverFiltering: true,
                    serverGrouping: false,
                    transport: {
                        read: {
                            url: "",
                            dataType: 'json'
                        },
                        parameterMap : function(options, operation) {
                            if (operation == "read") {
                                //return $.extend(options, {});
                                return {
                                    queryBean: JSON.stringify(options)
                                };
                            }
                        }
                    }
                },
                columns: [],
                pageable: true,
                filterable: false,
                scrollable: false
            }
        }, options);

        var searchBox = $(selector).kendoSearchBox(options).data('kendoSearchBox');

        smart.kendoui._removeSpanClass(selector);

        return searchBox;
    };

    smart.kendoui._removeSpanClass = function (selector) {
        var classStr = $(selector).attr('class');
        if (classStr) {
            var classes = classStr.split(/\s+/);
            for (var i = 0; i < classes.length; i++) {
                if (/^s-span(\d){1,2}$/.test(classes[i])) {
                    $(selector).closest('.k-widget').find('input').removeClass(classes[i]);
                }
            }
        }
    };

    /**
     * 修复grid由于总高度和内容高度不匹配导致的变形问题
     * @param selector 选择器或jquery对象
     */
    smart.kendoui.fixGridHeight = function (selector) {
        $(selector).each(function (index, element) {
            var $grid = $(element);
            var height = $grid.height();
            $grid.children().each(function (i, obj) {
                var $obj = $(obj);
                if (!$obj.hasClass('k-grid-content')) {
                    height -= $obj.outerHeight();
                }
            });
            $grid.find('.k-grid-content').height(height);
        })
    };

    /**
     * 获取kendoWidget的值
     * @param selector 选择器或jquery对象
     */
    smart.kendoui.getWidgetData =  function (selector) {
        var $widget = $(selector);
        var dataRole = $widget.attr("data-role");
        if (!dataRole) {
            return $widget.val();
        }
        switch (dataRole) {
            case 'combobox':
                return $widget.data("kendoComboBox").value();
            case 'dropdownlist':
                return $widget.data("kendoDropDownList").value();
            case 'autocomplete':
                return $widget.data("kendoAutoComplete").value();
            case 'datepicker':
                return kendo.toString($widget.data("kendoDatePicker").value(), 'yyyy-MM-dd');
            case 'timepicker':
                return kendo.toString($widget.data("kendoTimePicker").value(), 'HH:mm:ss');
            case 'datetimepicker':
                return kendo.toString($widget.data("kendoDateTimePicker").value(), 'yyyy-MM-dd HH:mm:ss');
            case 'numerictextbox':
                return $widget.data("kendoNumericTextBox").value();
            case 'searchbox':
                return $widget.data("kendoSearchBox").value();
            default:
                console.log('unknown type of widget');
        }
    };

    /**
     * 获取kendoWidget的值
     */
    smart.kendoui.nonEditor = function (container, options) {
        container.text(options.model[options.field])
    };

    //----------------------------扩展kendoui方法--------------------------------------
    kendo.ui.Grid.prototype.getParameterMap = function () {
        var dataSource = this.dataSource;
        return {
            take: dataSource._take,
            skip : dataSource._skip,
            page : dataSource._page,
            pageSize : dataSource._pageSize,
            total : dataSource._total,
            filter : dataSource._filter,
            sort : dataSource._sort
        }
    };

    kendo.ui.Grid.prototype.selectById = function (rowId) {
        if (rowId && rowId != -1) {
            var rowData = this.dataSource.get(rowId);
            if (rowData && rowData.uid) {
                var tr = this.table.find('tr[data-uid="' + rowData.uid + '"]');
                this.select(tr);
            }
        }
    };

    kendo.ui.Grid.prototype.setColorById = function (rowId, color) {
        if (rowId && rowId != -1) {
            var rowData = this.dataSource.get(rowId);
            if (rowData && rowData.uid) {
                var tr = this.table.find('tr[data-uid="' + rowData.uid + '"]');
                tr.css('color', color);
            }
        }
    };

    kendo.ui.Grid.prototype.setDeleteIds = function (delIds) {
        this._deleteIds = delIds||[];
    };

    kendo.ui.Grid.prototype.smartAddRow = function (item) {
        var that = this, index, dataSource = that.dataSource, mode = that._editMode(), createAt = that.options.editable.createAt || '', pageSize = dataSource.pageSize(), view = dataSource.view() || [];
        if ((that.editable && that.editable.end()) || !that.editable) {
            if (mode != "incell") {
                that.cancelRow();
            }
            index = dataSource.indexOf(view[0]);
            if (createAt.toLowerCase() == 'bottom') {
                index += view.length;
                if (pageSize && !dataSource.options.serverPaging && pageSize <= view.length) {
                    index -= 1;
                }
            }
            if (index < 0) {
                if (dataSource.page() > dataSource.totalPages()) {
                    index = (dataSource.page() - 1) * pageSize;
                } else {
                    index = 0;
                }
            }
            //新增行赋值
            var model = dataSource.insert(index, item || {}),
                id = model.uid,
                row = that.table.find("tr[" + kendo.attr("uid") + "=" + id + "]"),
                cell = row.children("td:not(.k-group-cell,.k-hierarchy-cell)").eq(that._firstEditableColumnIndex(row));

            if ((mode === "inline" || mode === "popup") && row.length) {
                that.editRow(row);
            } else if (cell.length) {
                that.editCell(cell);
            }

            //刷新SmartCud样式
            that.refreshRow();
            return model;
        }
    };

    //删除行
    kendo.ui.Grid.prototype.smartRemoveRow = function (event) {
        // 点击行内删除按钮进行单条删除
        if (event) {
            var tr = $(event.target).closest("tr");
            var data = this.dataItem(tr);
            if (data.id) {
                this._deleteIds.push(data.id);
                tr.css("text-decoration", "line-through");
            } else {
                this.removeRow(tr);
            }
        } else { // 点击删除按钮，进行多选删除
            var $rows = this.select();
            for (var i = 0; i < $rows.length; i++) {
                var data = this.dataItem($rows[i]);
                if (data && data.id) {
                    this._deleteIds.push(data.id);
                    $rows[i].css("text-decoration", "line-through");
                } else {
                    this.removeRow($rows[i]);
                }
            }
        }

        this.refreshRow();
    };

    //刷新表格行样式，新增绿色，修改蓝色，删除贯穿线
    kendo.ui.Grid.prototype.refreshRow = function () {
        var trs = this.tbody.children("tr[role=row]");
        for (var i = 0; i < trs.length; i++) {
            var row = this.dataItem(trs[i]);
            if (!row.id) {
                $(trs[i]).addClass('s-grid-row-new');
            } else if (this._deleteIds.indexOf(row.id) >= 0) {
                $(trs[i]).removeClass('s-grid-row-update').addClass('s-grid-row-del');
            } else if (row.dirty) {
                $(trs[i]).addClass('s-grid-row-update');
            }
        }
    };

    //返回表格选中行的数据--kendoGrid
    kendo.ui.Grid.prototype.selectData = function () {
        var ids = [], items = [];
        var $rows = this.select();
        for (var i = 0; i < $rows.length; i++) {
                var data = this.dataItem($rows[i]);
                if (data) {
                    ids.push(data.id);
                    items.push(data);
                }
        }
        return {ids: ids, items: items};
    };

    //返回表格选中行的数据--kendoTreeList
    kendo.ui.TreeList.prototype.selectData = function () {
        var ids = [], items = [];
        var $rows = this.tbody.children("tr.k-state-selected[role=row]");
        for (var i = 0; i < $rows.length; i++) {
            var data = this.dataItem($rows[i]);
            if (data) {
                ids.push(data.id);
                items.push(data);
            }
        }
        return {ids: ids, items: items};
    };

    //清除表格数据
    kendo.ui.Grid.prototype.clearData = function () {
        var data = this.dataSource.data();
        for (var i = data.length; i > 0; i--) {
            this.dataSource.remove(this.dataSource.at(i - 1));
        }
        this.setDeleteIds([]);
    };

    //过滤fields属性
    kendo.ui.Grid.prototype.getFields = function () {
        var fields = [];
        for (var i = 0; i < this.columns.length; i++) {
            if (this.columns[i].field) {
                fields.push(this.columns[i].field);
            }
        }
        return fields;
    };

    //获取表格增删改数据
    kendo.ui.Grid.prototype.getCudData = function () {
        var deleteIds = this._deleteIds;
        var fields = this.getFields();
        var datas = {news: [], updates: [], deletes: deleteIds}
        for (var i = 0; i < this.dataSource.data().length; i++) {
            var item = this.dataSource.at(i);
            //此处用于记录行号_row_index
            var itemClone = {_row_id: i};
            // 拷贝fields中的数据，如果不过滤掉非field数据，提交数据会报错：field不存在
            for (var f in item) {
                if (fields.indexOf(f) >= 0) {
                    itemClone[f] = item[f];
                }
            }
            if (!item.id) {
                datas.news.push(itemClone);
            } else if (item.dirty && deleteIds.indexOf(item.id) < 0) {
                datas.updates.push(itemClone);
            }
        }
        return datas;
    };

    kendo.ui.Grid.prototype.getColumnIndex = function (field) {
        for (var i = 0; i < this.columns.length; i++) {
            if (this.columns[i].field) {
                if (field == this.columns[i].field) {
                    return i;
                }
            }
        }
        return -1;
    };

    //定位校验错误
    kendo.ui.Grid.prototype.showInvalidErrors = function (errors) {
        var $rows = this.tbody.find('> tr:not(.k-grouping-row,.k-detail-row,.k-group-footer)');
        for (var i = 0; i < errors.length; i++) {
            var error = errors[i],
                rowId = error["rowId"],
                field = error["columnId"],
                msg = error["errorMsg"];

            var errorRow = $rows[rowId];
            var colIndex = this.getColumnIndex(field);
            if (colIndex > -1) {
                var errorTd = $(errorRow).find("td[role=gridcell]:eq(" + colIndex + ")");
                errorTd.addClass("s-field-invalid");
                errorTd.attr("error_notice", msg.join(";"));
            } else {
                alert("查找错误td失败:" + field);
            }
        }
    };

    /**
     * 添加框架扩展的一些事件及处理函数
     */
    kendo.ui.Grid.prototype.bindSmartEvents = function () {
        var grid = this,
            options = grid.options;

        grid._addSmartClickEvent();
    };

    /**
     * 添加行点击事件、双击事件、选中行变化事件
     * @private
     */
    kendo.ui.Grid.prototype._addSmartClickEvent = function () {
        var self = this,
            $table = self.table;
        self.selectedRowUid = null;
        // 主表选择行事件
        $table.on("click", function (e) {
            var $target = $(e.target);
            var $tr = $target.closest("tr");
            if ($tr.size() > 0 && $tr.hasClass("k-state-selected")) {
                var rowData = self.dataItem($tr);
                var data = { data: { tr: $tr, rowData: rowData}};
                self.trigger("smartRowClick", data);

                // 选中行发生变化的事件
                if (rowData && self.selectedRowUid != rowData.uid) {
                    self.trigger("smartRowChange", data);
                    self.selectedRowUid = rowData.uid;
                }
            }
        });
        // 绑定行双击击事件
        $table.on("dblclick", function (e) {
            var $target = $(e.target);
            var $tr = $target.closest("tr");
            if ($tr.size() > 0 && $tr.hasClass("k-state-selected")) {
                self.trigger("smartRowDblClick", { data: { tr: $tr, rowData: self.dataItem($tr)}});
            }
        });
    };

})();