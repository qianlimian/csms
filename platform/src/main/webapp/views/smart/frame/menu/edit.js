(function () {
	
    new smart.SingleEditModule({
        name: "SmartMenuEdit", //必需，Edit模块名
        containerId: "ctnMenuEdit", //必需，Edit模块的容器id
        restUrl: "/menus/", //必需，请求的rest地址
        modelName: "menu", //必需，model名
        model: { //必需，model用于MVVM
            menu: {
                id: "",
                code: "",
                name: "",
                type: "",
                plugin: "",
                url: "",
                parentId: "",
                desc: "",
                displayOrder: ""
            }
        }
    });

})();
