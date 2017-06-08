/* 
 * @description 绑定事件、自定义事件
 * @kendo风格的navbar
 */

(function () {
    if (!window.smart) {
        window.smart = {};
    }

    /**
     * 构造方法
     * @constructor
     */
    smart.NavBar = function (selector, config) {

        if (!(this instanceof smart.NavBar)) {
            return new smart.NavBar(selector);
        }

        this.config = $.extend({
            onNext : function (item, title) {
                return true;
            },
            onPrev : function (item, title) {
                return true;
            }
        }, config);

        var self = this,
            $actions = $(selector).closest('.k-window').find('.k-window-actions'),
            $nextBtn = $('<a role="button" class="k-window-action k-link" title="下一页"><span role="presentation" class="k-icon k-i-seek-e"></span></a>'),
            $navPager = $('<span class="s-nav-pager"><span class="current-index"></span>/<span class="total-index"></span><span>'),
            $prevBtn = $('<a role="button" class="k-window-action k-link" title="上一页"><span role="presentation" class="k-icon k-i-seek-w"></span></a>');

        this.$currIndexDom = $navPager.find('.current-index');
        this.$totalIndexDom = $navPager.find('.total-index');

        $nextBtn.prependTo($actions);
        $navPager.prependTo($actions);
        $prevBtn.prependTo($actions);

        $nextBtn.click(function(){self._next()});
        $prevBtn.click(function(){self._prev()});
    };


    smart.NavBar.prototype = {
        setItems: function (items) {
            if (!$.isArray(items)) {
                items = [items];
            }
            this.items = items;
            this.currIndex = 0;
            this._setCurrItem(this.currIndex);
            this._setPager();
        },

        //增加item
        addItem: function (item) {
            this.items.splice(this.currIndex + 1, 0, item);
            this._next();
        },

        //更新当前item
        updateCurrItem: function (item) {
            this.items[this.currIndex] = item;
            this.currItem = item;
        },

        //移除当前item
        removeCurrItem: function () {
            this.items.splice(this.currIndex, 1);

            if (this.items.length == 0) {
                this.setItems([null]);
            } else {
                if (this.currIndex < this.items.length) {
                    this._setCurrItem(this.currIndex);
                } else {
                    this._setCurrItem(--this.currIndex);
                }
                this._setPager();
            }
        },

        //取当前item
        getCurrItem: function () {
            return this.currItem;
        },

        //设置当前item
        _setCurrItem: function (currIndex) {
           this.currItem = this.items[currIndex];
        },

        //设置页码
        _setPager: function () {
            this.$currIndexDom.text(this.currIndex + 1);
            this.$totalIndexDom.text(this.items.length);
        },

        //判断是否有下一条
        _hasNext: function () {
            return (this.items && this.currIndex < this.items.length - 1);
        },

        //下一条
        _next: function () {
            if (this._hasNext()) {
                this._setCurrItem(++this.currIndex);
                this._setPager();
                this.config.onNext(this.getCurrItem(), this.title());
            }
            return false;
        },

        //判断是否有上一条
        _hasPrev: function () {
            return (this.items && this.currIndex > 0);
        },

        //上一条
        _prev: function () {
            if (this._hasPrev()) {
                this._setCurrItem(--this.currIndex);
                this._setPager();
                this.config.onPrev(this.getCurrItem(), this.title());
            }
            return false;
        },

        title: function() {
            return this.getCurrItem()?'编辑':'新增'
        }
    };
})();
