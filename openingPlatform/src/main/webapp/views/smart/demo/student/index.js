(function () {
    var IndexModule = smart.SingleIndexModule.extend({

		//构造函数
        init: function (options) {
            smart.SingleIndexModule.fn.init.call(this, options);
        },
        
		//初始化组件
        initComponents: function () {
            //调用父类方法初始化查询窗口、编辑页面、导航条等
            smart.SingleIndexModule.fn.initComponents.call(this);

            smart.kendoui.grid($("#mainGrid"),
                $.extend(true, this.gridOptions(), {
                    // dataSource : { //可省略，默认方法query
                    //     url: this.restUrl + "query.do"
                    // },
                    columns : [
                        { field: "id",  width: 100, hidden: true},
                        { field: "name", type: "string", title: "姓名", width: 100 },
                        { field: "age", type: "number", title: "年龄", width: 150,
                            filterable: {
                                cell: {
                                    template: function (args) {
                                        args.element.kendoNumericTextBox({
                                            format: "n0",
                                            decimals: 0
                                        });
                                    }
                                }
                            }
                        },
                        { field: "birthday", type: "date", title: "生日", width: 150, format: '{0:yyyy-MM-dd}'},
                        { field: "address", type: "string", title: "住址", width: 150 }
                    ]
                })
            );
        }
    });

    new IndexModule({
        name : "SmartStudentIndex", //必需，Index模块名
        containerId : "ctnStudentIndex", //必需，Index模块的容器id
        restUrl: "/smart/students/", //必需，请求的rest地址
        // grid: 'mainGrid', //可省略，默认mainGrid，如果要配置自定义的id时需要加
        // query : { //默认已经取消了查询窗口，有特殊需要的加
        //     containerId : "ctnQueryWin",
        //     options: {
        //         width : '600px',
        //         height : '200px'
        //     }
        // },
        editModule : {
            name : "SmartStudentEdit", //必需，Edit模块名
            containerId : "ctnStudentEditWrap", //必需，Edit模块的容器id
            // options: { //可省略，默认编辑窗口配置
            //     width : '600px',
            //     height : '400px',
            //     content: "/smart/students/edit.htm" //可省略，从后台load页面
            // }
        }
    });
})();
