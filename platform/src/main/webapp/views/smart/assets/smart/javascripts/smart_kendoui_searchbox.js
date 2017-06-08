if (!window.smart) {
    window.smart = {};
}

(function ($, undefined) {
    var kendo = window.kendo,
        ui = kendo.ui,
        Widget = ui.Widget,
        keys = kendo.keys,
        support = kendo.support,
        htmlEncode = kendo.htmlEncode,
        activeElement = kendo._activeElement,
        ID = "id",
        TR = "tr[role=row]",
        CHANGE = "change",
        CHARACTER = "character",
        FOCUSED = "k-state-focused",
        HOVER = "k-state-hover",
        LOADING = "k-loading",
        OPEN = "open",
        CLOSE = "close",
        SELECT = "select",
        PROGRESS = "progress",
        REQUESTEND = "requestEnd",
        WIDTH = "width",
        extend = $.extend,
        proxy = $.proxy,
        browser = support.browser,
        isIE8 = browser.msie && browser.version < 9,
        quotRegExp = /"/g;

    var GridList = Widget.extend({
        init: function (element, options) {
            var that = this,
                ns = that.ns,
                id;
////A
            if (!options.grid) {
                options.grid = {};
            }

            Widget.fn.init.call(that, element, options);
            element = that.element;

            that._isSelect = element.is(SELECT);
            that._template();

////C
            that.listbox = $('<div unselectable="on" class="k-list k-reset"/>')
                .css({ overflow: support.kineticScrollNeeded ? "" : "auto" })
                .on("mouseenter" + ns, TR, function () {
                    $(this).addClass(HOVER);
                })
                .on("mouseleave" + ns, TR, function () {
                    $(this).removeClass(HOVER);
                })
                .on("click" + ns, TR, proxy(that._click, that))
                .attr({
                    tabIndex: -1,
                    role: "listbox",
                    "aria-hidden": true
                });

            that.list = $("<div class='k-list-container'/>")
                .append(that.listbox)
                .on("mousedown" + ns, function (e) {
                    e.preventDefault();
                });

            id = element.attr(ID);

            if (id) {
                that.list.attr(ID, id + "-list");
                that.listbox.attr(ID, id + "_listbox");
                that._optionID = id + "_option_selected";
            }

            that._header();
            that._accessors();
            that._initValue();
        },

        options: {
            valuePrimitive: false,
            headerTemplate: ""
        },

        setOptions: function (options) {
            Widget.fn.setOptions.call(this, options);

            if (options && options.enable !== undefined) {
                options.enabled = options.enable;
            }
        },

        focus: function () {
            this._focused.focus();
        },

        readonly: function (readonly) {
            this._editable({
                readonly: readonly === undefined ? true : readonly,
                disable: false
            });
        },

        enable: function (enable) {
            this._editable({
                readonly: false,
                disable: !(enable = enable === undefined ? true : enable)
            });
        },

        _filterSource: function (filter) {
            var that = this,
                options = that.options,
                dataSource = that.dataSource,
                expression = dataSource.filter() || {};
////C
            removeFiltersForField(expression, options.dataTextField);
            removeFiltersForField(expression, options.dataValueField);
            removeFiltersForField(expression, options.dataFilterField);

            if (filter) {
                expression = expression.filters || [];
                expression.push(filter);
            }
            dataSource.filter(expression);
        },

        _header: function() {
            var template = this.options.headerTemplate;
            var header;

            if ($.isFunction(template)) {
                template = template({});
            }

            if (template) {
                this.list.prepend(template);

                header = this.listbox.prev();

                this.header = header[0] ? header : null;
            }
        },

        _initValue: function () {
            var that = this,
                value = that.options.value;

            if (value) {
                that.element.val(value);
            } else {
                value = that.element.val();
            }

            that._old = value;
        },

        _ignoreCase: function () {
            var that = this,
                model = that.dataSource.reader.model,
                field;

            if (model && model.fields) {
////C
                field = model.fields[that.options.dataFilterField];

                if (field && field.type && field.type !== "string") {
                    that.options.ignoreCase = false;
                }
            }
        },

        items: function () {
            return this.listbox[0].children;
        },

        current: function (candidate) {
            var that = this,
                id = that._optionID;

            if (candidate !== undefined) {
                if (that._current) {
                    that._current
                        .removeClass(FOCUSED)
                        .removeAttr("aria-selected")
                        .removeAttr(ID);

                    that._focused
                        .removeAttr("aria-activedescendant");
                }

                if (candidate) {
                    candidate.addClass(FOCUSED);
                    that._scroll(candidate);

                    if (id) {
                        candidate.attr("id", id);
                        that._focused.attr("aria-activedescendant", id);
                    }
                }

                that._current = candidate;
            } else {
                return that._current;
            }
        },

////2014-A
        destroy: function () {
            var that = this,
                ns = that.ns;

            Widget.fn.destroy.call(that);

            that._unbindDataSource();

            that.listbox.off(ns);
            that.list.off(ns);

            that.popup.destroy();

            if (that._form) {
                that._form.off("reset", that._resetHandler);
            }
        },

        dataItem: function (index) {
            var that = this;

            if (index === undefined) {
                index = that.selectedIndex;
            }
            return that._data()[index];
        },

        _accessors: function () {
            var that = this,
                element = that.element,
                getter = kendo.getter,
                options = that.options,
                textField = element.attr(kendo.attr("text-field")),
                valueField = element.attr(kendo.attr("value-field"));

            if (textField) {
                options.dataTextField = textField;
            }

            if (valueField) {
                options.dataValueField = valueField;
            }

            that._text = getter(options.dataTextField);
            that._value = getter(options.dataValueField);
        },

        _aria: function (id) {
            var that = this,
                options = that.options,
                element = that._focused;

            if (options.suggest !== undefined) {
                element.attr("aria-autocomplete", options.suggest ? "both" : "list");
            }

            id = id ? id + " " + that.listbox[0].id : that.listbox[0].id;

            element.attr("aria-owns", id);

            that.listbox.attr("aria-live", !options.filter || options.filter === "none" ? "off" : "polite");
        },

        _blur: function () {
            var that = this;

            that._change();

////C       // 防止点击过滤器时关闭grid
            if (!that.popup.visible()) {
                that.close();
            }
        },

        _change: function () {
            var that = this,
                index = that.selectedIndex,
                optionValue = that.options.value,
                value = that.value(),
                trigger;

            if (that._isSelect && !that._bound && optionValue) {
                value = optionValue;
            }

            if (value !== that._old) {
                trigger = true;
            } else if (index !== undefined && index !== that._oldIndex) {
                trigger = true;
            }

            if (trigger) {
                that._old = value;
                that._oldIndex = index;

                that.trigger(CHANGE);

                // trigger the DOM change event so any subscriber gets notified
                that.element.trigger(CHANGE);
            }
        },

        _click: function (e) {
            if (!e.isDefaultPrevented()) {
                this._accept($(e.currentTarget));
            }
        },

        _data: function () {
            return this.dataSource.view();
        },

        _enable: function () {
            var that = this,
                options = that.options,
                disabled = that.element.is("[disabled]");

            if (options.enable !== undefined) {
                options.enabled = options.enable;
            }

            if (!options.enabled || disabled) {
                that.enable(false);
            } else {
                that.readonly(that.element.is("[readonly]"));
            }
        },

////2014-C
        _focus: function (tr) {
            var that = this;

            if (that.popup.visible() && tr && that.trigger(SELECT, {item: tr})) {
                that.close();
                return;
            }

            that._select(tr);
            that._triggerCascade();

            that._blur();
        },

        _index: function (value) {
            var that = this,
                idx,
                length,
                data = that._data();

            for (idx = 0, length = data.length; idx < length; idx++) {
                if (that._dataValue(data[idx]) == value) {
                    return idx;
                }
            }

            return -1;
        },

        _dataValue: function (dataItem) {
            var value = this._value(dataItem);

            if (value === undefined) {
                value = this._text(dataItem);
            }

            return value;
        },

////2014-C
        _height: function (length) {
            if (length) {
                var that = this,
                    list = that.list,
                    visible = that.popup.visible(),
                    height = that.options.height;

                list.add(list.parent(".k-animation-container")).show()
                    .height(that.listbox[0].scrollHeight > height ? height : "auto");

                if (!visible) {
                    list.hide();
                }
            }
        },

////2014-C
        _adjustListWidth: function () {
            var that = this,
                list = this.list,
////C
                width = that.options.width,
                wrapper = this.wrapper,
                computedStyle, computedWidth;

            if (!list.data(WIDTH) && width) {
                return;
            }

            computedStyle = window.getComputedStyle ? window.getComputedStyle(wrapper[0], null) : 0;
            computedWidth = computedStyle ? parseFloat(computedStyle.width) : wrapper.outerWidth();

            if (computedStyle && (browser.mozilla || browser.msie)) { // getComputedStyle returns different box in FF and IE.
                computedWidth += parseFloat(computedStyle.paddingLeft) + parseFloat(computedStyle.paddingRight) + parseFloat(computedStyle.borderLeftWidth) + parseFloat(computedStyle.borderRightWidth);
            }

            width = computedWidth - (list.outerWidth() - list.width());

            list.css({
                fontFamily: wrapper.css("font-family"),
                width: width
            }).data(WIDTH, width);

            return true;
        },

        _popup: function () {
            var that = this,
                list = that.list,
                focused = that._focused,
                options = that.options,
                wrapper = that.wrapper;

            that.popup = new ui.Popup(list, extend({}, options.popup, {
                anchor: wrapper.find('input'),
                open: function (e) {
                    that._adjustListWidth();

                    if (that.trigger(OPEN)) {
                        e.preventDefault();
                    } else {
                        focused.attr("aria-expanded", true);
                        that.listbox.attr("aria-hidden", false);
                    }
                },
                close: function (e) {
                    if (that.trigger(CLOSE)) {
                        e.preventDefault();
                    } else {
                        focused.attr("aria-expanded", false);
                        that.listbox.attr("aria-hidden", true);
                    }
                },
                animation: options.animation,
                isRtl: support.isRtl(wrapper)
            }));

            that.popup.one(OPEN, function () {
                that._height(that._data().length);
            });

            that._touchScroller = kendo.touchScroller(that.popup.element);
        },

        _makeUnselectable: function () {
            if (isIE8) {
                this.list.find("*").attr("unselectable", "on");
            }
        },

        _toggleHover: function (e) {
            $(e.currentTarget).toggleClass(HOVER, e.type === "mouseenter");
        },

        _toggle: function (open) {
            var that = this;
            open = open !== undefined ? open : !that.popup.visible();

            if (!support.touch && that._focused[0] !== activeElement()) {
                that._focused.focus();
            }

            that[open ? OPEN : CLOSE]();
        },

////2014-C
        _scroll: function (item) {

            if (!item) {
                return;
            }

            if (item[0]) {
                item = item[0];
            }

            var listbox = this.listbox[0],
                itemOffsetTop = item.offsetTop,
                itemOffsetHeight = item.offsetHeight,
                ulScrollTop = listbox.scrollTop,
                ulOffsetHeight = listbox.clientHeight,
                bottomDistance = itemOffsetTop + itemOffsetHeight;

            listbox.scrollTop = ulScrollTop > itemOffsetTop ?
                itemOffsetTop : bottomDistance > (ulScrollTop + ulOffsetHeight) ?
                bottomDistance - ulOffsetHeight : ulScrollTop;
        },

        _template: function () {
            var that = this,
                options = that.options,
                template = options.template,
////C
                hasDataSource = options.grid.dataSource;

            if (that._isSelect && that.element[0].length) {
                if (!hasDataSource) {
                    options.dataTextField = options.dataTextField || "text";
                    options.dataValueField = options.dataValueField || "value";
                }
            }

            if (!template) {
                that.template = kendo.template('<li tabindex="-1" role="option" unselectable="on" class="k-item">${' + kendo.expr(options.dataTextField, "data") + "}</li>", { useWithBlock: false });
            } else {
                template = kendo.template(template);
                that.template = function (data) {
                    return '<li tabindex="-1" role="option" unselectable="on" class="k-item">' + template(data) + "</li>";
                };
            }
        },

        _triggerCascade: function () {
            var that = this,
                value = that.value();
            if ((!that._bound && value) || that._old !== value) {
                that.trigger("cascade");
            }
        },

        _unbindDataSource: function () {
            var that = this;

            that.dataSource.unbind(CHANGE, that._refreshHandler)
                .unbind(PROGRESS, that._progressHandler)
                .unbind(REQUESTEND, that._requestEndHandler)
                .unbind("error", that._errorHandler);
        }
    });

    extend(GridList, {
////2014-R
        caret: function (element) {
            var caret,
                selection = element.ownerDocument.selection;

            if (selection) {
                caret = Math.abs(selection.createRange().moveStart(CHARACTER, -element.value.length));
            } else {
                caret = element.selectionStart;
            }

            return caret;
        },

        selectText: function (element, selectionStart, selectionEnd) {
            try {
                if (element.createTextRange) {
                    element.focus();
                    var textRange = element.createTextRange();
                    textRange.collapse(true);
                    textRange.moveStart(CHARACTER, selectionStart);
                    textRange.moveEnd(CHARACTER, selectionEnd - selectionStart);
                    textRange.select();
                } else {
                    element.setSelectionRange(selectionStart, selectionEnd);
                }
            } catch (e) { /* element is not focused or it is not in the DOM */
            }
        },
        inArray: function (node, parentNode) {
            var idx, children = $(parentNode).find(TR),
                length = children.length;

            for (idx = 0; idx < length; idx++) {
                if (node === children[idx]) {
                    return idx;
                }
            }

            return -1;
        }
    });

    kendo.ui.GridList = GridList;

    ui.GridSelect = GridList.extend({
        init: function (element, options) {
            GridList.fn.init.call(this, element, options);
            this._initial = this.element.val();
        },

        setDataSource: function (dataSource) {
////C
            this.options.grid.dataSource = dataSource;

            this._dataSource();

            if (this.options.autoBind) {
                this.dataSource.fetch();
            }
        },

        close: function () {
            this.popup.close();
        },

        select: function (tr) {
            var that = this;

            if (tr === undefined) {
                return that.selectedIndex;
            } else {
                that._select(tr);
                that._triggerCascade();
                that._old = that._accessor();
                that._oldIndex = that.selectedIndex;
            }
        },

////C
        _accessor: function (value, idx) {
            var element = this.element,
                isSelect = this._isSelect,
                option, selectedIndex;

            element = element[0];

            if (value === undefined) {
                if (isSelect) {
                    selectedIndex = element.selectedIndex;

                    if (selectedIndex > -1) {
                        option = element.options[selectedIndex];

                        if (option) {
                            value = option.value;
                        }
                    }
                } else {
                    value = element.value;
                }
                return value;
            } else {
                if (isSelect) {
                    element.selectedIndex = idx;
                } else {
                    element.value = value;
                }
            }
        },

        _hideBusy: function () {
            var that = this;
            clearTimeout(that._busy);
            that._arrow.removeClass(LOADING);
            that._focused.attr("aria-busy", false);
            that._busy = null;
        },

        _showBusy: function () {
            var that = this;

            that._request = true;

            if (that._busy) {
                return;
            }

            that._busy = setTimeout(function () {
                that._focused.attr("aria-busy", true);
                that._arrow.addClass(LOADING);
            }, 100);
        },

        _requestEnd: function () {
            this._request = false;
        },

////C
        _dataSource: function () {
            var that = this,
                element = that.element,
                options = that.options,
                gridOptions = options.grid,
                filterField = options.dataFilterField || options.dataValueField,
                valueField = options.dataValueField,
                dataSource = options.grid.dataSource || {},
                idx;

            dataSource = $.isArray(dataSource) ? {data: dataSource} : dataSource;

            // 当启用serverFiltering及serverPaging时，如果serverFiltering为false，则会出现过滤一次后表格中的数据仅能显示第一页的问题，故而这里强制启用serverFiltering
            if (gridOptions.filter != 'none' && dataSource.serverPaging) {
                dataSource.serverFiltering = true;
            }
            if (dataSource.serverPaging) {
                dataSource = extend(true, {
                    schema: {
                        data: "items",
                        total: "total"
                    }}, dataSource);
            }
            if (dataSource.serverGrouping) {
                dataSource = extend(true, {
                    schema: {
                        groups: "groups"
                    }}, dataSource);
            }

            if (gridOptions.filterable) {
                gridOptions.filterable = extend(true, {
                    messages: smart.kendoui.filterMessages,
                    operators: smart.kendoui.filterOperators
                }, gridOptions.filterable);
            }

            if (gridOptions.pageable) {
                gridOptions.pageable = extend(true, {
                    messages: smart.kendoui.pageMessages
                }, gridOptions.pageable);
            }

            for (var i = 0; i < gridOptions.columns.length; i++) {
                var column = gridOptions.columns[i];
                if (column.field == filterField && !column.type) {
                    column.type = 'string';
                } else if (column.field == valueField && !column.type) {
                    column.type = 'number';
                }
            }

            gridOptions.dataSource = dataSource;

            if (that._isSelect) {
                idx = element[0].selectedIndex;
                if (idx > -1) {
                    options.index = idx;
                }

                dataSource.select = element;
                dataSource.fields = [
                    { field: options.dataTextField },
                    { field: options.dataFilterField },
                    { field: options.dataValueField }
                ];
            }

            if (that.dataSource && that._refreshHandler) {
                that._unbindDataSource();
            } else {
                that._refreshHandler = proxy(that.refresh, that);
                that._progressHandler = proxy(that._showBusy, that);
                that._requestEndHandler = proxy(that._requestEnd, that);
                that._errorHandler = proxy(that._hideBusy, that);
            }

            that._createGridView();

            that.dataSource = that.grid.dataSource
                .bind(CHANGE, that._refreshHandler)
                .bind(PROGRESS, that._progressHandler)
                .bind(REQUESTEND, that._requestEndHandler)
                .bind("error", that._errorHandler);
        },

/////AA
        _createGridView: function () {
            var that = this,
                gridOptions = that.options.grid,
                tbody = that.tbody,
                listbox = that.listbox[0];

            var id = this.element.selector + "-searchbox-grid-" + new Date().getTime();
            listbox.innerHTML = '<div id="' + id + '"></div>';

            gridOptions = extend(true, gridOptions, {
                autoBind: that.options.autoBind
            });

            var grid = that.grid = $('#' + id).kendoGrid(gridOptions).data('kendoGrid');
            tbody = that.tbody = that.listbox.find('tbody');
            that.dataSource = grid.dataSource;
            grid.one("dataBound", function () {
                tbody = that.tbody = that.listbox.find('tbody');
                var tr = tbody.find(TR)[0];
                if (tr) {
                    that.current($(tr));
                }
            });
        },

        _get: function (tr) {
            var that = this,
                data = that._data(),
                idx;

            if (typeof tr === "function") {
                if (!that.tbody) {
                    that.refresh();
                }
                var length = that.tbody.find(TR).length;
                for (idx = 0; idx < length; idx++) {
                    var dataItem = that.grid.dataItem(that.tbody.children(TR)[idx]);
                    if (tr(dataItem)) {
                        tr = idx;
                        break;
                    }
                }
            }

            if (typeof tr === "number") {
                if (tr < 0) {
                    return $();
                }
///C
                tr = $(that.tbody.children(TR)[tr]);
            }

            if (tr && tr.nodeType) {
                tr = $(tr);
            }

            return tr;
        },

        _move: function (e) {
            var that = this,
                key = e.keyCode,
                tbody = that.tbody,
                methodName = that.popup.visible() ? "_select" : "_accept",
                current = that._current,
                down = key === keys.DOWN,
                firstChild,
                pressed;

            if (key === keys.UP || down) {
                if (e.altKey) {
                    that.toggle(down);
                } else {
                    var children = tbody.find(TR);
                    firstChild = children[0];
                    var lastChild = children[children.length - 1];

                    if (!firstChild && !that._accessor() && that._state !== "filter") {
                        that.dataSource.one(CHANGE, function () {
                            if (that.dataSource.view().length > 0) {
                                that._move(e);
                            }
                        });
                        that._filterSource();
                        e.preventDefault();
                        return true; //pressed
                    }

                    if (down) {
                        if (!current || (that.selectedIndex === -1 && !that.value() && current[0] === firstChild)) {
                            current = firstChild;
                        } else {
                            var nextSibling = current.nextAll(TR).first()[0];
                            current = current[0] ? nextSibling : firstChild;

                            if (!current && firstChild === lastChild) {
                                current = firstChild;
                            }
                        }

                        that[methodName](current);
                    } else {
                        current = current[0] ? current.prevAll(TR).first()[0] : lastChild;
                        if (!current && firstChild === lastChild) {
                            current = firstChild;
                        }

                        that[methodName](current);
                    }
                }

                e.preventDefault();
                pressed = true;
            } else if (key === keys.ENTER || key === keys.TAB) {
                if (that.popup.visible()) {
                    e.preventDefault();
                }

                that._accept(current);
                pressed = true;
            } else if (key === keys.ESC) {
                if (that.popup.visible()) {
                    e.preventDefault();
                }
                that.close();
                pressed = true;
            }

            return pressed;
        },

////2014C
        _selectItem: function (value) {
            var that = this,
                options = that.options,
                index = that.selectedIndex;

            value = that._selectedValue || options.value || that._accessor();

//            if (value) {
//                that.value(value);
//            } else
            if (!that._bound || index > -1) {
                if (!that._bound) {
                    index = options.index;
                }

                that.select(index);
            }
        },

        _fetchItems: function (value) {
            var that = this;
            var hasItems = that.tbody ? that.tbody.children(TR)[0] : null;

            if (value == '[object Object]') {
                return false;
            }

            //if request is started avoid datasource.fetch
            if (that._request) {
                return true;
            }

            if (!that._fetch && !hasItems) {
                if (that.options.cascadeFrom) {
                    return !hasItems;
                }

                that.dataSource.one(CHANGE, function () {
                    that.value(value);
                    that._fetch = false;
                });

                that._fetch = true;
                that.dataSource.fetch();

                return true;
            }
        },

        _options: function (data, optionLabel) {
            var that = this,
                element = that.element,
                selectedIndex = element[0].selectedIndex,
                length = data.length,
                options = "",
                option,
                dataItem,
                dataText,
                dataValue,
                idx = 0;

            if (optionLabel) {
                options = optionLabel;
                selectedIndex += 1;
                idx = 1;
            }

            for (; idx < length; idx++) {
                option = "<option";
                dataItem = data[idx];
                dataText = that._text(dataItem);
                dataValue = that._value(dataItem);

                if (dataValue !== undefined) {
                    dataValue += "";

                    if (dataValue.indexOf('"') !== -1) {
                        dataValue = dataValue.replace(quotRegExp, "&quot;");
                    }

                    option += ' value="' + dataValue + '"';
                }

                option += ">";

                if (dataText !== undefined) {
                    option += htmlEncode(dataText);
                }

                option += "</option>";
                options += option;
            }

            element.html(options);
////2014-R
            element[0].selectedIndex = selectedIndex === -1 ? 0 : selectedIndex;
        },

        _reset: function () {
            var that = this,
                element = that.element,
                form = element.closest("form");

            if (form[0]) {
                that._resetHandler = function () {
                    setTimeout(function () {
                        that.value(that._initial);
                    });
                };

                that._form = form.on("reset", that._resetHandler);
            }
        },

////2014-C
        _cascade: function () {
            var that = this,
                options = that.options,
                cascade = options.cascadeFrom,
                parent, parentElement,
                select, valueField,
                change;

            if (cascade) {
                that._selectedValue = options.value || that._accessor();

                parentElement = $("#" + cascade);
                parent = parentElement.data("kendo" + options.name);

                if (!parent) {
                    return;
                }

                options.autoBind = false;
                valueField = parent.options.dataValueField;

                change = function () {
                    var value = that._selectedValue || that.value();
                    if (value) {
                        that.value(value);
                        if (!that.dataSource.view()[0] || that.selectedIndex === -1) {
                            that._clearSelection(parent, true);
                        }
                    } else {
                        that.select(options.index);
                    }

                    that.enable();
                    that._triggerCascade();
                };
                select = function () {
                    var dataItem = parent.dataItem(),
                        filterValue = dataItem ? parent._value(dataItem) : null,
                        expressions, filters;

                    if (filterValue) {
                        expressions = that.dataSource.filter() || {};
                        removeFiltersForField(expressions, valueField);
                        filters = expressions.filters || [];

                        filters.push({
                            field: valueField,
                            operator: "eq",
                            value: filterValue
                        });

                        that.dataSource
                            .one(CHANGE, change)
                            .filter(filters);

                    } else {
                        that.enable(false);
                        that._clearSelection(parent);
                        that._triggerCascade();
                    }
                };

                parent.bind("cascade", function () {
                    select();
                });

                //refresh was called
                if (parent._bound) {
                    select();
                } else if (!parent.value()) {
                    that.enable(false);
                }
            }
        }
    });

    function removeFiltersForField(expression, field) {
        if (expression.filters) {
            expression.filters = $.grep(expression.filters, function (filter) {
                removeFiltersForField(filter, field);
                if (filter.filters) {
                    return filter.filters.length;
                } else {
                    return filter.field != field;
                }
            });
        }
    }
})(window.kendo.jQuery);

(function ($, undefined) {
    var kendo = window.kendo,
        ui = kendo.ui,
        Widget = ui.Widget,
        GridList = ui.GridList,
        GridSelect = ui.GridSelect,
        support = kendo.support,
        placeholderSupported = support.placeholder,
        activeElement = kendo._activeElement,
        keys = kendo.keys,
        extend = $.extend,
        TR = 'tr[role=row]',
        ns = ".kendoSearchBox",
        CLICK = "click" + ns,
        MOUSEDOWN = "mousedown" + ns,
        DISABLED = "disabled",
        READONLY = "readonly",
        CHANGE = "change",
        DEFAULT = "k-state-default",
        FOCUSED = "k-state-focused",
        STATEDISABLED = "k-state-disabled",
        ARIA_DISABLED = "aria-disabled",
        ARIA_READONLY = "aria-readonly",
        STATE_SELECTED = "k-state-selected",
        STATE_FILTER = "filter",
        STATE_ACCEPT = "accept",
        STATE_REBIND = "rebind",
        HOVEREVENTS = "mouseenter" + ns + " mouseleave" + ns,
        NULL = null,
        proxy = $.proxy;


    var SearchBox = GridSelect.extend({
        init: function (element, options) {
            var that = this, text;

            that.ns = ns;
////C
            if (!options.dataFilterField) {
                options.dataFilterField = options.dataTextField;
            }
            options = $.isArray(options) ? { dataSource: options } : options;

            GridSelect.fn.init.call(that, element, options);

            that._focusHandler = function () {
////C
                that.input.focus();
            };

            options = that.options;
            element = that.element.on("focus" + ns, that._focusHandler);
////2014-R
            options.placeholder = options.placeholder || element.attr("placeholder");

            that._reset();

            that._wrapper();

            that._input();

            that._tabindex(that.input);

            that._popup();

            that._dataSource();
            that._ignoreCase();

            that._enable();

            that._cascade();

            that._aria();

            that._oldIndex = that.selectedIndex = -1;

            if (options.autoBind) {
                that._filterSource();
            } else {
                text = options.text;

                if (!text && that._isSelect) {
                    text = element.children(":selected").text();
                }

                if (text) {
                    that.input.val(text);
                }
            }

            if (!text) {
                that._placeholder();
            }

            that.wrapper.on("click" + ns, '.s-delete-btn', proxy(that._clearData, that));

            kendo.notify(that);
        },

        options: {
            name: "SearchBox",
            enabled: true,
            index: -1,
            text: null,
            value: null,
            autoBind: true,
            delay: 200,
            dataFilterField: "",
            dataTextField: "",
            dataValueField: "",
            minLength: 0,
            width: 200,
            height: 'auto',
            highlightFirst: true,
            template: "",
            filter: "none",
            placeholder: "",
            suggest: false,
            ignoreCase: true,
            animation: {},
            grid: {
                autoBind: true,
                dataSource: {
                    data: [],
                    pageSize: 30,
                    autoSync: false,
                    batch: false,
                    serverFiltering: false,
                    serverGrouping: false,
                    serverSorting: false,
                    serverPaging: false,
                    serverAggregates: false
                },
                pageable: false,
                scrollable: false
            }
        },

        events: [
            "open",
            "close",
            CHANGE,
            "select",
            "dataBinding",
            "dataBound",
            "cascade"
        ],

        setOptions: function (options) {
            GridSelect.fn.setOptions.call(this, options);

            this._template();
            this._accessors();
            this._aria();
        },

        current: function (tr) {
            var that = this,
                current = that._current;

            if (tr === undefined) {
                return current;
            }

            if (current) {
                current.removeClass(STATE_SELECTED);
            }

            GridSelect.fn.current.call(that, tr);
        },

        destroy: function () {
            var that = this;

            that.input.off(ns);
            that.element.off(ns);
            that._inputWrapper.off(ns);

            GridSelect.fn.destroy.call(that);
        },

        _editable: function (options) {
            var that = this,
                disable = options.disable,
                readonly = options.readonly,
                wrapper = that._inputWrapper.off(ns),
                input = that.element.add(that.input.off(ns)),
                arrow = that._arrow.parent().off(CLICK + " " + MOUSEDOWN);

            if (!readonly && !disable) {
                wrapper
                    .addClass(DEFAULT)
                    .removeClass(STATEDISABLED)
                    .on(HOVEREVENTS, that._toggleHover);

                input.removeAttr(DISABLED)
                    .removeAttr(READONLY)
                    .attr(ARIA_DISABLED, false)
                    .attr(ARIA_READONLY, false);

                arrow.on(CLICK, function () {
                    that.toggle();
                })
                    .on(MOUSEDOWN, function (e) {
                        e.preventDefault();
                    });

                that.input
                    .on("keydown" + ns, proxy(that._keydown, that))
                    .on("keyup" + ns, proxy(that._keyup, that))
                    .on("focus" + ns, function () {
                        wrapper.addClass(FOCUSED);
                        that._placeholder(false);
////C
                        that.popup.open();
                    })
                    .on("blur" + ns, function () {
                        wrapper.removeClass(FOCUSED);
                        clearTimeout(that._typing);

                        if (that.options.text !== that.input.val()) {
                            that.text(that.text());
                        }

                        that._placeholder();
                        that._blur();
////C
                        that.element.blur();
                        that._placeDeleteBtn();
                    });

            } else {
                wrapper
                    .addClass(disable ? STATEDISABLED : DEFAULT)
                    .removeClass(disable ? DEFAULT : STATEDISABLED);

                input.attr(DISABLED, disable)
                    .attr(READONLY, readonly)
                    .attr(ARIA_DISABLED, disable)
                    .attr(ARIA_READONLY, readonly);
            }
        },

        open: function () {
            var that = this,
                serverFiltering = that.dataSource.options.serverFiltering;

            if (that.popup.visible()) {
                return;
            }

////C
            if (!that.tbody || !that.tbody[0] || that.tbody.find('tr').length == 0 || (that._state === STATE_ACCEPT && !serverFiltering)) {
                that._open = true;
                that._state = STATE_REBIND;
                if (that.dataSource.view().length == 0) {
                    that._filterSource();
                }
            } else {
                that.popup.open();
                that._scroll(that._current);
            }
        },

////A
        resetGrid: function () {
            var that = this;
            that.value(null);
            that.search('');
            that._placeDeleteBtn();
            that._open = false;
        },

        refresh: function () {
            var that = this,
                tbody = that.tbody,
                options = that.options,
                state = that._state,
                data = that._data(),
                length = data.length,
                value, open, custom,
                gridOptions = options.grid,
                dataSource = gridOptions.dataSource;

            that.trigger("dataBinding");

            that._height(length);

            if (that.popup.visible()) {
                that.popup._position();
            }

            if (that._isSelect) {
                if (state === STATE_REBIND) {
                    that._state = "";
                    value = that.value();
                }

                custom = that._option;
                that._option = undefined;
                that._options(data);
////2014C
                if (custom && custom[0].selected) {
                    that._custom(custom.val());
                }
            }

////C
            if (length) {
                var $trs = tbody.find(TR);
                if (options.highlightFirst) {
                    that.current($($trs[0]));
                }

                if (options.suggest && that.input.val() && that._request !== undefined /*first refresh ever*/) {
                    that.suggest($($trs[0]));
                }

                var grid = that.grid, value = that.value();
                for (var i = 0; i < $trs.length; i++) {
                    var $tr = $($trs[i]);
                    if (grid.dataItem($tr).id == value) {
                        that.current($tr);
                        break;
                    }
                }
            }

            if (state !== STATE_FILTER && !that._fetch) {
                that._selectItem();
            }

            if (that._open) {
                that._open = false;
                open = !!length;

                if (that._typing && that.input[0] !== activeElement()) {
                    open = false;
                }

                that.toggle(open);
                that._typing = undefined;
            }

            if (that._touchScroller) {
                that._touchScroller.reset();
            }

            that._makeUnselectable();

            that._hideBusy();
            that._bound = true;
            that.trigger("dataBound");
        },

        search: function (word) {
            word = typeof word === "string" ? word : this.text();
            var that = this,
                length = word.length,
                options = that.options,
                ignoreCase = options.ignoreCase,
                filter = options.filter,
////C
                field = options.dataFilterField;

            clearTimeout(that._typing);

            if (length >= options.minLength) {
                that._state = STATE_FILTER;
                if (filter === "none") {
                    that._filter(word);
                } else {
                    that._open = true;
                    that._filterSource({
                        value: ignoreCase ? word.toLowerCase() : word,
                        field: field,
                        operator: filter,
                        ignoreCase: ignoreCase
                    });
                }
            }
        },

        suggest: function (word) {
            var that = this,
                element = that.input[0],
                value = that.text(),
                caret = GridList.caret(element),
                key = that._last,
                idx;

            if (key == keys.BACKSPACE || key == keys.DELETE) {
                that._last = undefined;
                return;
            }

            word = word || "";

            if (typeof word !== "string") {
                idx = GridList.inArray(word[0], that.tbody[0]);

                if (idx > -1) {
                    word = that._text(that.dataSource.view()[idx]);
                } else {
                    word = "";
                }
            }

            if (caret <= 0) {
                caret = value.toLowerCase().indexOf(word.toLowerCase()) + 1;
            }

            if (word) {
                idx = word.toLowerCase().indexOf(value.toLowerCase());
                if (idx > -1) {
                    value += word.substring(idx + value.length);
                }
            } else {
                value = value.substring(0, caret);
            }

            if (value.length !== caret || !word) {
                element.value = value;
                GridList.selectText(element, caret, value.length);
            }
        },

////A
        _clearData: function (e) {
            var that = this;
            if (!e.isDefaultPrevented()) {
                that.text('');
                that._change();
                that.search('');
                that.wrapper.find('.s-delete-btn').hide();
            }
            e.preventDefault();
        },

        text: function (text) {
            text = text === null ? "" : text;

            var that = this,
                textAccessor = that._text,
                input = that.input[0],
                ignoreCase = that.options.ignoreCase,
                loweredText = text,
                dataItem;

            if (text !== undefined) {
                dataItem = that.dataItem();

                if (dataItem && textAccessor(dataItem) === text) {
////2014C
                    that._triggerCascade();
                    return;
                }

                if (ignoreCase) {
                    loweredText = loweredText.toLowerCase();
                }

                that._select(function (data) {
                    data = textAccessor(data);

                    if (ignoreCase) {
                        data = (data + "").toLowerCase();
                    }

                    return data === loweredText;
                });

                if (that.selectedIndex < 0) {
                    that._custom(text);
                    input.value = text;
                }

                that._triggerCascade();
            } else {
                return input.value;
            }
        },

        toggle: function (toggle) {
            var that = this;

            that._toggle(toggle);
        },

        value: function (value) {
            var that = this,
                obj = value,
                idx;

            if (value !== undefined) {
                if (value !== null) {
                    value = value.toString();
                }

                that._selectedValue = value;
                if (!that._open && value && that._fetchItems(value)) {
                    return;
                }

                idx = that._index(value);
                if (idx > -1) {
                    that.select(idx);
////C
                } else {
                    if (obj && typeof obj == 'object') {
                        value = (obj[that.options.dataValueField] || '').toString();

                        that._selectedValue = value;
                        if (!that._open && value && that._fetchItems(value)) {
                            return;
                        }

                        idx = that._index(value);
                        if (idx > -1) {
                            that.select(idx);
                        } else {
                            that.dataSource.insert(0, obj);
                            that.grid.refresh();
                            that.value(obj[that.options.dataValueField]);
                        }
                    } else {
                        that.current(NULL);
                        that._custom(value);

                        that.text(value);
                        that._placeholder();

                        // 过滤dataSource，尝试找出匹配项
                        var options = that.options,
                            ignoreCase = options.ignoreCase,
                            field = that.options.dataValueField,
                            word = that.value();
                        if (word) {
                            that._filterSource({
                                value: ignoreCase ? word.toLowerCase() : word,
                                field: field,
                                operator: 'eq',
                                ignoreCase: ignoreCase
                            });

                            var selectFirst = function () {
                                var $tr = that.tbody.find('tr:first');
                                if ($tr.length > 0) {
                                    that.select($tr);
                                } else {
                                    that.value('');
                                }
                            };

                            that.grid.one('dataBound', selectFirst);
                        } else {
                            that.dataSource._filter = undefined;
                            that.dataSource.read();
                        }
                    }
                }

                that._prev = that._old = that._accessor();
                that._oldIndex = that.selectedIndex;

                that._placeDeleteBtn();
            } else {
                return that._accessor();
            }
        },

        _accept: function (tr) {
            var that = this;

            if (tr) {
                that._focus(tr);
                that.close();
            } else {
                that.text(that.text());
                that._change();
            }
            that._placeDeleteBtn();
        },

////A
        _placeDeleteBtn: function () {
            var that = this,
                value = that.text();

            var $deleteBtn = that.wrapper.find('.s-delete-btn');
            if (value) {
                $deleteBtn.show();
            } else {
                $deleteBtn.hide();
            }
        },

        _custom: function (value) {
            var that = this,
                element = that.element,
                custom = that._option;

            if (that._state === STATE_FILTER) {
                that._state = STATE_ACCEPT;
            }

            if (that._isSelect) {
                if (!custom) {
                    custom = that._option = $("<option/>");
                    element.append(custom);
                }
                custom.text(value);
                custom[0].selected = true;
            } else {
                element.val(value);
            }

            that._selectedValue = value;
        },

        _filter: function (word) {
            var that = this,
                options = that.options,
                dataSource = that.dataSource,
                ignoreCase = options.ignoreCase,
                predicate = function (dataItem) {
                    var text = that._text(dataItem);
                    if (text !== undefined) {
                        text = text + "";
                        if (text !== "" && word === "") {
                            return false;
                        }

                        if (ignoreCase) {
                            text = text.toLowerCase();
                        }

                        return text.indexOf(word) === 0;
                    }
                };

            if (ignoreCase) {
                word = word.toLowerCase();
            }

            if (!that.tbody.find(TR)[0]) {
                dataSource.one(CHANGE, function () {
                    if (dataSource.data()[0]) {
                        that.search(word);
                    }
                }).fetch();
                return;
            }

            if (that._highlight(predicate) !== -1) {
                if (options.suggest && that._current) {
                    that.suggest(that._current);
                }
                that.open();
            }

            that._hideBusy();
        },

        _highlight: function (tr) {
            var that = this, idx;

            if (tr === undefined || tr === null) {
                return -1;
            }

            tr = that._get(tr);
            idx = GridList.inArray(tr[0], that.tbody[0]);
            if (idx == -1) {
                if (that.options.highlightFirst && !that.text()) {
                    tr = $(that.tbody.find(TR)[0]);
                } else {
                    tr = NULL;
                }
            }

            that.current(tr);

            return idx;
        },

        _input: function () {
            var that = this,
                element = that.element.removeClass("k-input")[0],
                accessKey = element.accessKey,
                wrapper = that.wrapper,
                SELECTOR = "input.k-input",
                name = element.name || "",
                input;

            if (name) {
                name = 'name="' + name + '_input" ';
            }

            input = wrapper.find(SELECTOR);

            if (!input[0]) {
                wrapper.append('<span tabindex="-1" unselectable="on" class="k-dropdown-wrap k-state-default">' +
                    '<input ' + name + 'class="k-input" type="text" autocomplete="off"/>' +
                    '<span class="s-select s-delete-btn" style="display: none;"><span class="s-icon s-i-delete"></span></span>' +
                    '<span tabindex="-1" unselectable="on" class="k-select"><span unselectable="on" class="k-icon k-i-arrow-s">select</span></span></span>')
                    .append(that.element);

                input = wrapper.find(SELECTOR);
            }

            input[0].style.cssText = element.style.cssText;

            if (element.maxLength > -1) {
                input[0].maxLength = element.maxLength;
            }

            input.addClass(element.className)
                .val(element.value)
                .css({
                    width: "100%",
                    height: element.style.height
                })
                .attr({
                    "role": "searchbox",
                    "aria-expanded": false
                })
                .show();

            if (placeholderSupported) {
                input.attr("placeholder", that.options.placeholder);
            }

            if (accessKey) {
                element.accessKey = "";
                input[0].accessKey = accessKey;
            }

            that._focused = that.input = input;
            that._inputWrapper = $(wrapper[0].firstChild);
            that._arrow = wrapper.find(".k-icon")
                .attr({
                    "role": "button",
                    "tabIndex": -1
                });

            if (element.id) {
                that._arrow.attr("aria-controls", that.listbox[0].id);
            }
        },

        _keydown: function (e) {
            var that = this,
                key = e.keyCode;

            that._last = key;

            clearTimeout(that._typing);

            if (key != keys.TAB && !that._move(e)) {
                that._search();
            }
        },

        _keyup: function (e) {
            var that = this;
            that._placeDeleteBtn();
        },

        _placeholder: function (show) {
            if (placeholderSupported) {
                return;
            }

            var that = this,
                input = that.input,
                placeholder = that.options.placeholder,
                value;

            if (placeholder) {
                value = that.value();

                if (show === undefined) {
                    show = !value;
                }

                input.toggleClass("k-readonly", show);

                if (!show) {
                    if (!value) {
                        placeholder = "";
                    } else {
                        return;
                    }
                }

                input.val(placeholder);
////2014C
            }
        },

        _search: function () {
            var that = this;

            that._typing = setTimeout(function () {
                var value = that.text();
                if (that._prev !== value) {
                    that._prev = value;
                    that.search(value);
                }
            }, that.options.delay);
        },

        _select: function (tr) {
            var that = this,
                text,
                value,
                data = that._data(),
                idx = that._highlight(tr);

            that.selectedIndex = idx;
            if (idx !== -1) {
                if (that._state === STATE_FILTER) {
                    that._state = STATE_ACCEPT;
                }

                that._current.addClass(STATE_SELECTED);

////C
                tr = that._get(tr);
                data = that.grid.dataItem(tr);
                that._filterValue = data[that.options.dataFilterField];    // 选中某行后，将dataFilterField的值保存下来
                text = that._text(data);
                value = that._value(data);

                that._prev = that.input[0].value = text;
                that._accessor(value !== undefined ? value : text, idx);
                that._selectedValue = that._accessor();
                that._placeholder();

                if (that._optionID) {
                    that._current.attr("aria-selected", true);
                }
            }
        },

        _wrapper: function () {
            var that = this,
                element = that.element,
                wrapper = element.parent();

            if (!wrapper.is("span.k-widget")) {
                wrapper = element.hide().wrap("<span />").parent();
                wrapper[0].style.cssText = element[0].style.cssText;
            }

            that.wrapper = wrapper.addClass("k-widget k-searchbox k-header")
                .addClass(element[0].className)
                .css("display", "");
        },

        _clearSelection: function (parent, isFiltered) {
            var that = this,
                hasValue = parent._selectedValue || parent.value(),
                custom = hasValue && parent.selectedIndex === -1;

            if (isFiltered || !hasValue || custom) {
                that.value("");
            }
        }
    });

    ui.plugin(SearchBox);

})(window.kendo.jQuery);
