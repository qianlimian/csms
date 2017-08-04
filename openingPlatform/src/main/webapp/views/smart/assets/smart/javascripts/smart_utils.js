(function () {
    if (!window.smart) {
        window.smart = {};
    }

    if (!smart.Utils) {
        smart.Utils = {};
    }

    var utils = smart.Utils;

    utils.formatDate = function (date, formatStr) {
        /*
         * 函数：格式化日期
         * 参数：formatStr-格式化字符串
         * d：将日显示为不带前导零的数字，如1
         * dd：将日显示为带前导零的数字，如01
         * ddd：将日显示为缩写形式，如Sun
         * dddd：将日显示为全名，如Sunday
         * M：将月份显示为不带前导零的数字，如一月显示为1
         * MM：将月份显示为带前导零的数字，如01
         * MMM：将月份显示为缩写形式，如Jan
         * MMMM：将月份显示为完整月份名，如January
         * yy：以两位数字格式显示年份
         * yyyy：以四位数字格式显示年份
         * h：使用12小时制将小时显示为不带前导零的数字，注意||的用法
         * hh：使用12小时制将小时显示为带前导零的数字
         * H：使用24小时制将小时显示为不带前导零的数字
         * HH：使用24小时制将小时显示为带前导零的数字
         * m：将分钟显示为不带前导零的数字
         * mm：将分钟显示为带前导零的数字
         * s：将秒显示为不带前导零的数字
         * ss：将秒显示为带前导零的数字
         * l：将毫秒显示为不带前导零的数字
         * ll：将毫秒显示为带前导零的数字
         * tt：显示am/pm
         * TT：显示AM/PM
         * 返回：格式化后的日期
         */

        /*
         函数：填充0字符
         参数：value-需要填充的字符串, length-总长度
         返回：填充后的字符串
         */
        var zeroize = function (value, length) {
            if (!length) {
                length = 2;
            }
            value = new String(value);
            for (var i = 0, zeros = ''; i < (length - value.length); i++) {
                zeros += '0';
            }
            return zeros + value;
        };
        return formatStr.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|M{1,4}|yy(?:yy)?|([hHmstT])\1?|[lLZ])\b/g, function ($0) {
            switch ($0) {
                case 'd':
                    return date.getDate();
                case 'dd':
                    return zeroize(date.getDate());
                case 'ddd':
                    return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][date.getDay()];
                case 'dddd':
                    return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][date.getDay()];
                case 'M':
                    return date.getMonth() + 1;
                case 'MM':
                    return zeroize(date.getMonth() + 1);
                case 'MMM':
                    return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][date.getMonth()];
                case 'MMMM':
                    return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][date.getMonth()];
                case 'yy':
                    return new String(date.getFullYear()).substr(2);
                case 'yyyy':
                    return date.getFullYear();
                case 'h':
                    return date.getHours() % 12 || 12;
                case 'hh':
                    return zeroize(date.getHours() % 12 || 12);
                case 'H':
                    return date.getHours();
                case 'HH':
                    return zeroize(date.getHours());
                case 'm':
                    return date.getMinutes();
                case 'mm':
                    return zeroize(date.getMinutes());
                case 's':
                    return date.getSeconds();
                case 'ss':
                    return zeroize(date.getSeconds());
                case 'l':
                    return date.getMilliseconds();
                case 'll':
                    return zeroize(date.getMilliseconds());
                case 'tt':
                    return date.getHours() < 12 ? 'am' : 'pm';
                case 'TT':
                    return date.getHours() < 12 ? 'AM' : 'PM';
            }
        });
    };
    /**
     * 将日期时间格式的字符串转换为Date对象
     * 注意：仅适用于类似“2010-12-21 08:08:08”格式，即按年月日时分秒顺序排列且有字符(不能是数字！)隔开
     * 例如"2012-12-21T08:08:08+08:00","2012/12/21 08:08:08"等等
     */
    utils.strToDate = function (str) {
        var arr = str.split(/\D/);
        return new Date(arr[0], arr[1] - 1, arr[2], arr[3], arr[4], arr[5]);
    };


    /**
     * 将日期时间格式的字符串转换为“2010-12-21 08:08:08”格式的日期时间字符串格式
     * 注意：仅适用于类似“2010-12-21 08:08:08”格式，即按年月日时分秒顺序排列且有字符(不能是数字！)隔开
     * 例如"2012-12-21T08:08:08+08:00","2012/12/21 08:08:08"等等
     */
    /**
     * 将日期格式化为标准字符串格式“2012-12-21 18:08:08”
     * 即"四位年-月-日 二十四小时:两位分:两位秒"
     */
    utils.formatDateStr = function (obj) {
        if (typeof obj == 'string') {
            var arr = obj.split(/\D/);
            var date = new Date(arr[0], arr[1] - 1, arr[2], arr[3], arr[4], arr[5]);
            return utils.formatDateStr(date);
        } else {
            return utils.formatDate(obj, "yyyy-MM-dd HH:mm:ss");
        }
    };


    /**
     * 获取时间长度字符串
     * @param beginTime 开始时间，Date类型
     * @param endTime 结束时间，Date类型
     *
     * return::timeLengthStr 字符串，如：1天1小时1分钟12秒
     */
    utils.getTimeLengthStr = function (beginTime, endTime) {
        var ms = endTime.getTime() - beginTime.getTime();  //时间差的毫秒数
        var str = "";
        //计算出相差天数
        var days = Math.floor(ms / (24 * 3600 * 1000));
        if (days > 0) {
            str += days + "天";
        }
        //计算出小时数
        var leave1 = ms % (24 * 3600 * 1000);    //计算天数后剩余的毫秒数
        var hours = Math.floor(leave1 / (3600 * 1000));
        if (days > 0 || hours > 0) {
            str += hours + "小时";
        }
        //计算相差分钟数
        var leave2 = leave1 % (3600 * 1000);        //计算小时数后剩余的毫秒数
        var minutes = Math.floor(leave2 / (60 * 1000));
        if (days > 0 || hours > 0 || minutes > 0) {
            str += minutes + "分钟";
        }
        //计算相差秒数
        var leave3 = leave2 % (60 * 1000);      //计算分钟数后剩余的毫秒数
        var seconds = Math.round(leave3 / 1000);
        if ((days > 0 || hours > 0 || minutes > 0) && seconds > 0) {
            str += seconds + "秒";
        }
        return str;
    };


    utils.endWith = function (str, end_str) {
        return str.substr(str.length - end_str.length, end_str.length) == end_str;
    };

    utils.beginWith = function (str, begin_str) {
        return str.substr(0, begin_str.length) == begin_str;
    };

    /**
     * 去掉字符串前后的空格
     */
    utils.trimStr = function (str) {
        return str.replace(/(^\s*)|(\s*$)/g, "");
    };
    /**
     * 去掉字符串前面的空格
     */
    utils.lTrim = function (str) {
        return str.replace(/(^\s*)/g, "");
    };
    /**
     * 去掉字符串后面的空格
     */
    utils.rTrim = function (str) {
        return str.replace(/(\s*$)/g, "");
    };

    utils.numEn = function (num) {
        var arr2 = ["ZERO","TEN","TWENTY","THIRTY","FORTY","FIFTY","SIXTY","SEVENTY","EIGHTY","NINETY"];
        var arr3 = ["ZERO","ONE","TWO","THREE","FOUR","FIVE","SIX","SEVEN","EIGHT","NINE"];
        var arr4 = ["TEN","ELEVEN","TWELVE","THIRTEEN","FOURTEEN","FIFTEEN","SIXTEEN","SEVENTEEN","EIGHTEEN","NINETEEN"];
        var strRet = "";
        if((num.length == 3) && (num.substr(0,3) != "000")){
            if((num.substr(0,1) != "0")){
                strRet += arr3[num.substr(0,1)] + " HUNDRED";
                if(num.substr(1,2) != "00"){
                    strRet+=" AND ";
                }
            }
            num = num.substring(1);
        }
        if((num.length == 2)){
            if((num.substr(0,1) == "0")){
                num = num.substring(1);
            }
            else if((num.substr(0,1) == "1")){
                strRet += arr4[num.substr(1,2)];
            }
            else{
                strRet += arr2[num.substr(0,1)];
                if(num.substr(1,1) != "0"){
                    strRet += "-";
                }
                num = num.substring(1);
            }
        }
        if((num.length == 1) && (num.substr(0,1) != "0")){
            strRet += arr3[num.substr(0,1)];
        }
        return strRet;
    };

    /**
     * 阿拉伯数字转英文数字
     */
    utils.translate = function (num) {
        var num = num.toString();
        var arr1 = [""," THOUSAND"," MILLION"," BILLION"];
        var i,j= 0,num3,strRet="";
        var len = num.length;
        var cols = Math.ceil(len/3);
        var first = len-cols*3;
        for(i=first; i<len; i+=3){
            ++j;
            if(i>=0){
                num3 = num.substring(i, i+3);
            }else{
                num3 = num.substring(0, first+3);
            }
            var strEng = utils.numEn(num3);
            if(strEng != ""){
                if(strRet != ""){
                    strRet += ",";
                }
                strRet += utils.numEn(num3) + arr1[cols-j];
            }
        }
        return strRet;
    }

    /**
     * 校验查询条件中输入的月份格式是否正确
     * 2012-01
     */
    utils.checkMonth = function (month) {
        return month.match(/^((?:19|20)\d\d)-(0[1-9]|1[012])$/);
    }

    /**
     * 校验查询条件中输入的日期格式是否正确
     * 2012-01-01
     */
    utils.checkDate = function (date) {
        return date.match(/^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/);
    }

    /**
     * 校验查询条件中的日期时间格式是否正确
     * 2012-01-01 11:30
     */
    utils.checkDateTime = function (time){
        return time.match(/^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])\s+([0-1][0-9]|[2][0-3]):([0-5][0-9])$/);
    }


    /**
     * 对查询条件中，输入的起止时间进行判定
     * @param d1
     * @param d2
     */
    utils.compareDate = function (d1, d2) {
        if (d1 && d2) {
            var v1 = d1.split('-');
            var v2 = d2.split('-');
            var day1 = v1[0] * 12 * 30 + v1[1] * 30 + v1[2];
            var day2 = v2[0] * 12 * 30 + v2[1] * 30 + v2[2];
            return (day2 -day1 >= 0);
        }
        return true;
    }


    ///js  浮点数减法
    utils.accSubtr = function(arg1,arg2){
        var r1,r2,m,n;
        try{r1=(arg1||0).toString().split(".")[1].length}catch(e){r1=0}
        try{r2=(arg2||0).toString().split(".")[1].length}catch(e){r2=0}
        m=Math.pow(10,Math.max(r1,r2));
        //动态控制精度长度
        n=(r1>=r2)?r1:r2;
        return ((arg1*m-arg2*m)/m).toFixed(n);
    }

    //js  浮点数的加法
    utils.accAdd = function(arg1,arg2){
        var r1,r2,m;
        try{r1=(arg1||0).toString().split(".")[1].length}catch(e){r1=0}
        try{r2=(arg2||0).toString().split(".")[1].length}catch(e){r2=0}
        m=Math.pow(10,Math.max(r1,r2))
        return (arg1*m+arg2*m)/m
    }

    //js 除法
    utils.accDiv = function (arg1,arg2){
        var t1=0,t2=0,r1,r2;
        try{t1=(arg1||0).toString().split(".")[1].length}catch(e){}
        try{t2=(arg2||0).toString().split(".")[1].length}catch(e){}
        with(Math){
            r1=Number(arg1.toString().replace(".",""))
            r2=Number(arg2.toString().replace(".",""))
            return (r1/r2)*pow(10,t2-t1);
        }
    }

    //js 乘法
    utils.accMul = function(arg1,arg2){
        var m=0,s1=(arg1||0).toString(),s2=(arg2||0).toString();
        try{m+=s1.split(".")[1].length}catch(e){}
        try{m+=s2.split(".")[1].length}catch(e){}
        return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
    }

    utils.getUrlParams = function(name) {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null) return  unescape(r[2]); return null;
    }

    /**
     * 添加cookie,expires单位为ms
     * @param {name: 'cookie_name', value: 'cookie_value', expires: 0}
     */
    smart.addCookie = function (obj) {
        var cookie = $.extend(true, {expires: 0}, obj);
        var expires = cookie.expires;
        var str = cookie.name + '=' + encodeURIComponent(cookie.value);
        //expires为0时不设定过期时间，浏览器关闭时cookie自动消失
        if (expires > 0) {
            var date = new Date();
            date.setTime(date.getTime() + expires);
            str += "; expires=" + date.toGMTString();
        }
        document.cookie = str;
    };

    /**
     * 获取指定名称的cookie的值
     */
    smart.getCookie = function (cookieName) {
        var arrStr = document.cookie.split("; "), value;
        for (var i = 0; i < arrStr.length; i++) {
            var temp = arrStr[i].split("=");
            if (temp[0] == cookieName) {
                value = decodeURIComponent(temp[1]);
                break;
            }
        }
        return value;
    };

    /**
     * 为了删除指定名称的cookie，可以将其过期时间设定为一个过去的时间
     */
    smart.delCookie = function (cookieName) {
        var date = new Date();
        date.setTime(date.getTime() - 10000);
        document.cookie = cookieName + "=a; expires=" + date.toGMTString();
    };


})();

