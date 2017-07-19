(function () {

    var EditModule = smart.SingleEditModule.extend({

        init: function (options) {
            smart.SingleEditModule.fn.init.call(this, options);
        },
		
        // 初始化组件
		initComponents : function() {
			// 调用父类方法初始化查询窗口、编辑页面、导航条等
			var self = this;
			smart.SingleEditModule.fn.initComponents.call(this);			
			$(".evalOperation").change(function(){			
				var selectedValue = $(this).val();
				if(isNaN(selectedValue)){
					$(this).parent().parent().find('.score').text("0 分");
					return false;
				}
				$(this).parent().parent().find('.score').text(selectedValue+" 分");
			});
		},
                
        bindEvents: function () {
            smart.bind('#' + this.containerId + ' #btnDoSave', [this.doSave, this]);
        },

        doSave: function () {
           var self = this;
            var caseScore = {};
            caseScore.id = $("#score_id").val();
            caseScore.caseId = $("#score_caseId").val();
            caseScore.itemDtos = self._getScoreItems();
			
           //保存
            smart.ajax({
                url: self.restUrl + "save.do",
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify(caseScore),
                success: function (res) {                   
		            self._setScoreItems(res);
                }
            });
        	
        },
        //打分项取值
        _getScoreItems: function () {
            var arr = [];
            $('table select').each(function (_, row) {    
            	var id=$(row).parent().find("input[type='hidden']").val();
            	var evalId=row.options[row.selectedIndex].id;
            	var score=row.options[row.selectedIndex].value;
            	var selected=true;
            	if(isNaN(score)){
            		score=0;
            		selected=false;
            	}
            	arr.push({id:id,evalId: evalId, score: score,selected:selected});
               
            });
            return arr;
        },
         //打分项赋值
        _setScoreItems: function (score) {
            $("#score_id").val(score.id);
            $("#score_caseId").val(score.caseId);
            $("#score_totalScore").text(score.totalScore);
            var arr = score.itemDtos;
            $('table select').each(function (_, row) {
                var evalId=row.options[row.selectedIndex].id;
                    for (var i in arr) {
	                    if (evalId == arr[i].evalId) {
	                        $(row).parent().find("input[type='hidden']").val(arr[i].id);                        
	                    }
               		}
            });
        }

    });

    new EditModule({
        name: "SmartCaseScore", //必需，模块名
        containerId: "ctnCaseScore", //必需，模块的容器id
        restUrl: "/caseScores/", //必需，请求的rest地址
        modelName: "caseScore", //必需，model名
        model: { //必需，model用于MVVM
            caseScore: {
                id: "",
                caseId: "",
                caseName: "",
                totalScore: "",
                note: ""
            }
        }
    });
})();
