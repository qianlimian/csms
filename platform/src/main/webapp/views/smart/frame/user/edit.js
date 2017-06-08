(function () {
	
    new smart.SingleEditModule({
        name: "SmartUserEdit", //必需，Edit模块名
        containerId: "ctnUserEdit", //必需，Edit模块的容器id
        restUrl: "/users/", //必需，请求的rest地址
        modelName: "user", //必需，model名
        model: { //必需，model用于MVVM
            user: {
                id: "",
                name: "",
                loginName: "",
                password: ""
            }
        }
    });

})();
