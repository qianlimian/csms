(function () {
	
    new smart.SingleEditModule({
        name: "SmartRoleEdit", //必需，Edit模块名
        containerId: "ctnRoleEdit", //必需，Edit模块的容器id
        restUrl: "/smart/roles/", //必需，请求的rest地址
        modelName: "role", //必需，model名
        model: { //必需，model用于MVVM
            role: {
                id: "",
                name: "",
                desc: ""
            }
        }
    });

})();
