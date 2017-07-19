(function () {
	$("#btnDoSave").click(function(){
		
        evalItem = getItemDatas();
        //保存
        smart.ajax({
            url:  basePath + "/bdmEvaluations/save.do",
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(evalItem),
            success: function (res) {
               $("#evalDetails").data("kendoTreeView").dataSource.read();
               $("#bigItemTree").data("kendoTreeView").dataSource.read();
            }
         });
	});
	
	//打分项取值
    function getItemDatas() {
        var item= {};
        item.id=$("#editEval_id").val();
        item.standard=$("#editEval_standard").val();
        item.score=$("#editEval_score").val();
        item.displayOrder=$("#editEval_displayOrder").val();
        item.parent=$("#editEval_parent").val();
        return item;
    }
})();
