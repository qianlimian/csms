(function () {

    var EditModule = smart.SingleEditModule.extend({

        init: function (options) {
            smart.SingleEditModule.fn.init.call(this, options);
        },

        bindEvents: function () {
            smart.bind('#' + this.containerId + ' #btnDoSave', [this.doSave, this]);
        },

        doSave: function () {
            var self = this;
            var caseScore = {};
            caseScore.id = $("#score_id").val();
            caseScore.caseRecordId = $("#score_caseRecordId").val();
            caseScore.itemDtos = this._getScoreItems();

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
            $('fieldset .s-row-fluid').each(function (_, row) {
                var id = $(row).find("input[name='item_id']").val(),
                    evalId = $(row).find("input[name='item_evalId']").val(),
                    score = $(row).find("input[name='item_score']").val();
                arr.push({id: id, evalId: evalId, score: score});
            });
            return arr;
        },

        //打分项赋值
        _setScoreItems: function (score) {
            $("#score_id").val(score.id);
            $("#score_caseRecordId").val(score.caseRecordId);
            $("#score_caseName").val(score.caseName);
            $("#score_totalScore").val(score.totalScore);

            var arr = score.itemDtos;
            $('fieldset .s-row-fluid').each(function (_, row) {
                var standard = $(row).find("label").text();
                    for (var i in arr) {
                    if (standard == arr[i].standard) {
                        $(row).find("input[name='item_id']").val(arr[i].id);
                        $(row).find("input[name='item_evalId']").val(arr[i].evalId);
                        $(row).find("input[name='item_score']").val(arr[i].score);
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
                caseRecordId: "",
                caseName: "",
                totalScore: "",
                note: ""
            }
        }
    });
})();
