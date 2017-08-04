/* 
 * @description 枚举
 * @author zhaochuanfeng
 */

(function () {
    if (!window.smart) {
        window.smart = {};
    }

    smart.Enums = {};

    smart.Enum = function (className, data) {
        this.className = className;
        this.data = data;
        smart.Enums[className] = this;
    };

    smart.Enum.prototype = {
        getData: function () {
            var data = this.data;
            for (var i = 0; i < data.length; i++) {
                data[i].className = "enum[" + this.className + "]";
            }
            return [].concat(data);
        },
        getText: function (value) {
            var data = this.data;
            for (var i = 0; i < data.length; i++) {
                if (data[i].value === value || data[i].value + '' === value) {
                    return data[i].text;
                }
            }
            return '--请选择--';
        },
        getValue: function (text) {
            var data = this.data;
            for (var i = 0; i < data.length; i++) {
                if (data[i].text === text) {
                    return data[i].value;
                }
            }
            return '';
        }
    };

    new smart.Enum("com.bycc.enumitem.Gender", [
        {value: "MALE", text: "男"},
        {value: "FEMALE", text: "女"}
    ]);

    new smart.Enum("com.bycc.enumitem.AreaType", [
        {value: "HCQ", text: "环翠"},
        {value: "JQ", text: "经区"},
        {value: "GQ", text: "高区"},
        {value: "LGQ", text: "临港区"}
    ]);


    new smart.Enum("com.bycc.enumitem.CaseProcess", [
        {value: "FL", text: "分流"},
        {value: "SA", text: "受案"},
        {value: "LA", text: "立案"},
        {value: "JL", text: "拘留"},
        {value: "DB", text: "逮捕"},
        {value: "QS", text: "起诉"}
    ]);

    new smart.Enum("com.bycc.enumitem.CaseStatus", [
        {value: "ACCEPTED", text: "受理"},
        {value: "REGISTED", text: "立案"},
        {value: "DETAINED", text: "拘留"},
        {value: "ARRESTED", text: "逮捕"},
        {value: "PROSECUETD", text: "起诉"},
        {value: "CLOSED", text: "结案"}
    ]);

    new smart.Enum("com.bycc.enumitem.CaseHandle", [
        {value: "PENDING", text: "待办理"},
        {value: "HANDLING", text: "办理中"},
        {value: "HANDLED", text: "已办理"}
    ]);

    new smart.Enum("com.bycc.enumitem.CaseType", [
        {value: "CIVIL", text: "行政"},
        {value: "CRIMINAL", text: "刑事"},
        {value: "DISPUTE", text: "纠纷"}
    ]);

    new smart.Enum("com.bycc.enumitem.CertificateType", [
        {value: "ID", text: "身份证"},
        {value: "PP", text: "护照"}
    ]);

    new smart.Enum("com.bycc.enumitem.CollectItem", [
        {value: "ZW", text: "指纹"},
        {value: "XY", text: "血液"},
        {value: "NY", text: "尿液"},
        {value: "QT", text: "其他"}
    ]);

    new smart.Enum("com.bycc.enumitem.DeviceStatus", [
        {value: "NORMAL", text: "正常"},
        {value: "STOP", text: "停用"},
        {value: "DAMAGED", text: "损坏"}
    ]);

    new smart.Enum("com.bycc.enumitem.DutyType", [
        {value: "JY", text: "警员"},
        {value: "FZY", text: "法制员"},
        {value: "SZ", text: "所长"},
        {value: "FD", text: "分队"},
        {value: "ZD", text: "支队"}
    ]);

    new smart.Enum("com.bycc.enumitem.EvaluateType", [
        {value: "SUBJECTIVE", text: "主观"},
        {value: "OBJECTIVE", text: "客观"}
    ]);

    new smart.Enum("com.bycc.enumitem.RiskLevel", [
        {value: "HIGH", text: "高"},
        {value: "MEDIUM", text: "中"},
        {value: "LOW", text: "低"}
    ]);

    new smart.Enum("com.bycc.enumitem.RoomType", [
        {value: "INSPECT", text: "人身安全检查室"},
        {value: "COLLECT", text: "信息采集室"},
        {value: "IDENTIFY", text: "辨认室"},
        {value: "AWAIT", text: "候审室"},
        {value: "INQUEST", text: "讯问室"},
        {value: "INQUIRY", text: "询问室"},
        {value: "AISLE", text: "过道"}
    ]);

    new smart.Enum("com.bycc.enumitem.ScoreType", [
        {value: "ADD", text: "加分"},
        {value: "SUB", text: "减分"}
    ]);

    new smart.Enum("com.bycc.enumitem.UsageStatus", [
        {value: "UNUSED", text: "未使用"},
        {value: "USED", text: "已使用"}
    ]);

    new smart.Enum("com.bycc.enumitem.StoreType", [
        {value: "KY", text: "扣押"},
        {value: "ZC", text: "暂存"},
        {value: "BG", text: "保管"}
    ]);
    new smart.Enum("com.bycc.enumitem.PoliceStationType", [
        {value: "PCS", text: "派出所"},
        {value: "FJ", text: "分局"},
        {value: "SJ", text: "市局"}
    ]);

    new smart.Enum("com.bycc.enumitem.Boolean", [
        {value: true, text: "是"},
        {value: false, text: "否"}
    ]);

    new smart.Enum("com.bycc.enumitem.ReplyType", [
        {value: "EMAIL", text: "邮件回复"},
        {value: "TEL", text: "电话回复"},
        {value: "SMS", text: "短信回复"},
        {value: "TALK", text: "交谈回复"}
    ]);

    new smart.Enum("com.bycc.enumitem.ReplyStatus", [
        {value: "REPLYED", text: "已回复"},
        {value: "UNREPLY", text: "未回复"}
    ]);

    new smart.Enum("com.bycc.enumitem.OpenType",[
    	{value:"YES",text:"公开"},
    	{value:"NO",text:"非公开"}
    ]);

    new smart.Enum("com.bycc.enumitem.CertificateStatus",[
    	{value:"PRACTICE",text:"实习"},
    	{value:"FORMAL",text:"正式"},
    	{value:"CLOSE",text:"注销"}
    ]);
    
     new smart.Enum("com.bycc.enumitem.LawType",[
    	{value:"ADMINIST",text:"民法"},
    	{value:"CRIMINAL",text:"刑法"}
    ]);
})();


