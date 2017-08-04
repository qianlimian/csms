(function () {

    var EditModule = smart.SingleEditModule.extend({
        //构造函数
        init: function (options) {
            smart.SingleEditModule.fn.init.call(this, options);
        },

        //载入当前item
        loadItem: function (item) {
            // 新增时清除富文本编辑器内容
            if (!item) {
                window.editor.html('');
            }
            smart.EditModule.fn.loadItem.call(this, item);

            var modelName = this.modelName,
                viewModel = this.viewModel;

            if (item && item.id) {
                smart.ajax({
                    type: 'get',
                    url: this.restUrl + "findById.do?id=" + item.id,
                    success: function (res) {
                        viewModel.set(modelName, res);
                        // 设置富文本编辑器内容
                        window.editor.html(res.content);
                    }
                });
            }
        },

        //保存
        getModelData: function () {
            var data = smart.EditModule.fn.getModelData.call(this);
            // 取富文本内容
            data.content = window.editor.html();
            return data;
        }
    });

    new EditModule({
        name: "SmartLawEdit", //必需，Edit模块名
        containerId: "ctnLawEdit", //必需，Edit模块的容器id
        restUrl: "/laws/", //必需，请求的rest地址
        modelName: "law", //必需，model名
        editor: '',
        width: '80%',
        height: '60%',
        model: { //必需，model用于MVVM
            law: {
                id: "",
                title: "",
                content: ""
            }
        }
    });
})();
