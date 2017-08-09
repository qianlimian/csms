(function () {

    smart.kendoui.numericTextBox($("#editEval_score"), {decimals: 0, format: 'n0'});
    smart.kendoui.numericTextBox($("#editEval_displayOrder"), {decimals: 0, format: 'n0'});

    $("#btnDoSave").click(function () {
        var evalItem = getItemDatas();
        smart.ajax({
            url: basePath + "/bdmEvaluations/save.do",
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(evalItem),
            success: function (res) {
                $("#rootItem").data("kendoTreeView").dataSource.read();
                $("#detailItems").data("kendoTreeView").dataSource.read();
            }
        });
    });

    //打分项取值
    function getItemDatas() {
        var item = {};
        item.id = $("#editEval_id").val();
        item.standard = $("#editEval_standard").val();
        item.score = $("#editEval_score").val();
        item.displayOrder = $("#editEval_displayOrder").val();
        item.parent = $("#editEval_parent").val();
        return item;
    }
})();
