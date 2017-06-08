(function () {
	
    new smart.SingleEditModule({
        name: "SmartGroupEdit", //必需，Edit模块名
        containerId: "ctnGroupEdit", //必需，Edit模块的容器id
        restUrl: "/groups/", //必需，请求的rest地址
        modelName: "group", //必需，model名
        model: { //必需，model用于MVVM
            group: {
                id: "",
                name: "",
                desc: ""
            }
        }
    });

})();
