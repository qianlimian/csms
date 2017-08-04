<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<body>
<%@ include file="common/header.jsp" %>

<div class="container">
    <div class="row">
        <h1 class="col-md-12 index-title">执法办案信息公开查询</h1>
    </div>
    <div class="row">
        <form class="col-md-12 index-form" action="${ctx}/display.htm" method="post">
            <div class="row" style="margin-bottom:15px;">
                <input class="col-md-7" id="tel" name="tel" placeholder="手机号" style="margin-right:15px;"/>
                <input class="col-md-3" id="code" name="code" placeholder="验证码"/>
                <button id="codeBtn" type="button" onclick="sendCode()">验证手机</button>
            </div>
            <div class="row">
                <input class="col-md-7" id="idNumber" name="idNumber" placeholder="身份证号" style="margin-right:15px;"/>
                <input class="col-md-3" id="name" name="name" placeholder="姓名"/>
                <button type="submit" onclick="return query()">查询</button>
            </div>
        </form>
    </div>
    <div class="row">
        <div class="col-md-12 index-prompt">
            <h3>查询须知</h3>
            <p>1、报案人、被害人应如实填写有关信息后方可查询案件办理的相关情况。</p>
            <p>2、为切实保障当事人的合法权益，查询者填写的信息必须真实，填写的姓名应与案件中的姓名一致，同时应填写身份证号码、手机号码，通过短信的形式获取验证码后方可查询。</p>
            <p>3、本系统支持2017年09月01日后受理案件的查询。刑事案件暂限盗窃、诈骗、抢劫、抢夺、敲诈勒索、故意伤害、故意毁坏财物等案件；行政案件暂限盗窃等案件、诈骗、哄抢、抢夺、敲诈勒索、故意伤害（殴打他人）、故意损毁公私财物等案件。</p>
            <p>4、网上查询结果仅供参考，以公安机关实际案卷材料为准。</p>
        </div>
    </div>
</div>


<script src="${ctx}/views/pages/front/assets/js/jquery.placeholder.min.js"></script>
<script>
    // 解决IE下placeholder不显示的问题
    $(function () {
        $('input').placeholder();
    });

    /**
     * 重新发送验证码的时间间隔(秒)
     * @constant
     * @default 60
     * @type {int}
     */
    var RESEND_INTERVAL = 60;

    /**
     * 重新获取验证码剩余时间(秒)
     * @type {int}
     */
    var REMAINING;

    /**
     * 定时器id
     */
    var intervalId;

    /**
     * 校验手机号码.
     *
     * @param {string} number - 手机号码.
     * @return {boolean} 如果手机号码符合11位数字且以1开头则返回true,否则返回false.
     */
    function isValidMobile(number) {
        var reg = /^1\d{10}$/;
        return reg.test(number);
    }

    /**
     * 验证字段是否为空.
     *
     * @param {string} selector - 要验证字段的选择符.
     * @return {boolean} 如果字段不为空则返回true,否则返回false.
     */
    function isEmpty(selector) {
        return !$(selector).val();
    }

    /**
     * 发送手机验证码.
     */
    function sendCode() {
        // 校验手机号
        if (isEmpty('#tel')) {
            alert("请填写手机号码");
            return;
        }
        var tel = $('#tel').val();
        if (!isValidMobile(tel)) {
            alert("手机号码格式不正确！");
            return;
        }

        REMAINING = RESEND_INTERVAL;

        // 禁用获取验证码按钮
        disableCodeBtn();
        $('#codeBtn').html('验证手机(' + REMAINING + 's)');

        intervalId = window.setInterval(updateBtnText, 1000);

        // 发送验证码
        $.ajax({
            url: "/sendCode.do?tel=" + tel,
            method: 'GET',
            success: function (result) {
                if (!result.success) {
                    alert("短信发送失败，原因: " + result.msg);
                    window.clearInterval(intervalId);
                    // 启用获取验证码按钮
                    enableCodeBtn();
                }
            },
            error: function (result) {
                alert("短信发送失败，原因: " + result.msg);
                // 启用获取验证码按钮
                enableCodeBtn();
                window.clearInterval(intervalId);
            }
        });
    }

    /**
     * 实时更新获取验证码按钮状态.
     */
    function updateBtnText() {
        if (REMAINING == 0) {
            window.clearInterval(intervalId);
            enableCodeBtn();
        } else {
            REMAINING--;
            $('#codeBtn').html('验证手机(' + REMAINING + 's)');
        }
    }

    /**
     * 案件信息查询.
     */
    function query() {
        if (isEmpty('#code')) {
            alert("请填写验证码！");
            return false;
        }
        if (isEmpty('#idNumber') || isEmpty('#name')) {
            alert("请填写身份证号码和姓名！");
            return false;
        }
        return true;
    }

    /**
     * 启用获取手机验证码按钮
     */
    function enableCodeBtn() {
        $('#codeBtn').prop('disabled', false).css('cursor', '').html('验证手机');
    }

    /**
     * 禁用获取手机验证码按钮
     */
    function disableCodeBtn() {
        $('#codeBtn').prop('disabled', true).css('cursor', 'not-allowed');
    }
</script>
</body>