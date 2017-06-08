<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<body>
<style>
    form label {
        text-align: center;
    }
</style>

<div class="s-row-fluid s-toolbar">
    <a class="k-button" id="btnDoRead">身份证识读</a>
    <a class="k-button" id="btnDoSave"><i class="fa fa-save"></i>保存</a>
</div>

<form class="s-row-fluid">
    <div class="s-row-fluid">
        <label class="s-span6 s-required">姓名</label>
        <input class="s-span12" id="name" type="text" data-bind="value:casePeople.name"/>

        <label class="s-span6 s-required">性别</label>
        <input class="s-span12" id="gender" type="text" data-bind="value:casePeople.gender"/>

        <label class="s-span6 s-required">出生日期</label>
        <input class="s-span12" id="birthday" type="text" data-bind="value:casePeople.birthday"/>
    </div>

    <div class="s-row-fluid">
        <label class="s-span6 s-required">证件类型</label>
        <input class="s-span12" id="certificateType" type="text" data-bind="value:casePeople.certificateType"/>

        <label class="s-span6 s-required">证件号</label>
        <input class="s-span30" id="certificateNum" type="text" data-bind="value:casePeople.certificateNum"/>
    </div>

    <div class="s-row-fluid">
    	<label class="s-span6">联系方式</label>
        <input class="s-span12" id="telNum" type="text" data-bind="value:casePeople.telNum"/>
        
        <label class="s-span6">家庭住址</label>
        <input class="s-span30" id="address" type="text" data-bind="value:casePeople.address"/>
    </div>

    <div class="s-row-fluid">
        <label class="s-span6">事由</label>
        <div class="s-span48">

            <div class="s-row-fluid">
                <div class="s-span15">
                    <label class="s-span24">投案自首</label>
                    <input class="s-span12" type="radio" name="enterReason" value="TAZS" data-bind="checked: casePeople.enterReason">
                </div>
                <div class="s-span15">
                    <label class="s-span24">治安传唤</label>
                    <input class="s-span12" type="radio" name="enterReason" value="ZACH" data-bind="checked: casePeople.enterReason">
                </div>
                <div class="s-span15">
                    <label class="s-span24">继续盘问</label>
                    <input class="s-span12" type="radio" name="enterReason" value="JXPW" data-bind="checked: casePeople.enterReason">
                </div>
                <div class="s-span15">
                    <label class="s-span24">刑事传唤</label>
                    <input class="s-span12" type="radio" name="enterReason" value="XSCH" data-bind="checked: casePeople.enterReason">
                </div>
            </div>

            <div class="s-row-fluid">
                <div class="s-span15">
                    <label class="s-span24">拘传</label>
                    <input class="s-span12" type="radio" name="enterReason" value="JC" data-bind="checked: casePeople.enterReason">
                </div>
                <div class="s-span15">
                    <label class="s-span24">刑事拘留</label>
                    <input class="s-span12" type="radio" name="enterReason" value="XSJL" data-bind="checked: casePeople.enterReason">
                </div>
                <div class="s-span15">
                    <label class="s-span24">取保候审</label>
                    <input class="s-span12" type="radio" name="enterReason" value="QBHS" data-bind="checked: casePeople.enterReason">
                </div>
                <div class="s-span15">
                    <label class="s-span24">监视居住</label>
                    <input class="s-span12" type="radio" name="enterReason" value="JSJZ" data-bind="checked: casePeople.enterReason">
                </div>
            </div>

            <div class="s-row-fluid">
                <div class="s-span15">
                    <label class="s-span24">逮捕</label>
                    <input class="s-span12" type="radio" name="enterReason" value="DB" data-bind="checked: casePeople.enterReason">
                </div>
                <div class="s-span15">
                    <label class="s-span24">被害人</label>
                    <input class="s-span12" type="radio" name="enterReason" value="BHR" data-bind="checked: casePeople.enterReason">
                </div>
                <div class="s-span15">
                    <label class="s-span24">证人</label>
                    <input class="s-span12" type="radio" name="enterReason" value="ZR" data-bind="checked: casePeople.enterReason">
                </div>
                <div class="s-span15">
                    <label class="s-span24">其他</label>
                    <input class="s-span12" type="radio" name="enterReason" value="QT" data-bind="checked: casePeople.enterReason">
                </div>
            </div>
        </div>
    </div>
</form>
<script src="${ctx}/views/pages/caseRegister/edit.js"></script>
</body>
