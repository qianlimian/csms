(function () {

    new smart.SingleEditModule({
        name: "SmartBdmVideoCgEdit", //必需，Edit模块名
        containerId: "ctnBdmVideoCgEdit", //必需，Edit模块的容器id
        restUrl: "/bdmVideoCategories/", //必需，请求的rest地址
        modelName: "bdmVideoCategory", //必需，model名
        model: { //必需，model用于MVVM
            bdmVideoCategory: {
                id: "",
                code: "",
                name: "",
                note: ""
            }
        },
        ymlModule: "bdmVideoCategory" //配置yml按钮显隐的模块名
    });
})();
