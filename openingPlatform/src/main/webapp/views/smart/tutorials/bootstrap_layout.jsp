<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <title>smart input</title>
    <link rel="stylesheet" href="${ctx}/views/smart/assets/bootstrap/css/bootstrap.min.css">
</head>
<body>
<div class="container">

    <div class="row">
        <div class="col-md-1 col-sm-1 col-xs-1">col-md-1</div>
        <div class="col-md-1 col-sm-1 col-xs-1">col-md-1</div>
        <div class="col-md-1 col-sm-1 col-xs-1">col-md-1</div>
        <div class="col-md-1 col-sm-1 col-xs-1">col-md-1</div>
        <div class="col-md-1 col-sm-1 col-xs-1">col-md-1</div>
        <div class="col-md-1 col-sm-1 col-xs-1">col-md-1</div>
        <div class="col-md-1 col-sm-1 col-xs-1">col-md-1</div>
        <div class="col-md-1 col-sm-1 col-xs-1">col-md-1</div>
        <div class="col-md-1 col-sm-1 col-xs-1">col-md-1</div>
        <div class="col-md-1 col-sm-1 col-xs-1">col-md-1</div>
        <div class="col-md-1 col-sm-1 col-xs-1">col-md-1</div>
        <div class="col-md-1 col-sm-1 col-xs-1">col-md-1</div>
    </div>
    <div class="row">
        <div class="col-md-2 col-sm-2 col-xs-2">col-md-2</div>
        <div class="col-md-2 col-sm-2 col-xs-2">col-md-2</div>
        <div class="col-md-2 col-sm-2 col-xs-2">col-md-2</div>
        <div class="col-md-2 col-sm-2 col-xs-2">col-md-2</div>
        <div class="col-md-2 col-sm-2 col-xs-2">col-md-2</div>
        <div class="col-md-2 col-sm-2 col-xs-2">col-md-2</div>
    </div>
    <div class="row">
        <div class="col-md-2 col-sm-2 col-xs-2">col-md-2</div>
        <div class="col-md-4 col-sm-4 col-xs-4">col-md-4</div>
        <div class="col-md-2 col-sm-2 col-xs-2">col-md-2</div>
        <div class="col-md-4 col-sm-4 col-xs-4">col-md-4</div>
    </div>
    <div class="row">
        <div class="col-md-3 col-sm-3 col-xs-3">col-md-3</div>
        <div class="col-md-3 col-sm-3 col-xs-3">col-md-3</div>
        <div class="col-md-3 col-sm-3 col-xs-3">col-md-3</div>
        <div class="col-md-3 col-sm-3 col-xs-3">col-md-3</div>
    </div>
    <div class="row">
        <div class="col-md-3 col-sm-3 col-xs-3">col-md-3</div>
        <div class="col-md-6 col-sm-6 col-xs-6">col-md-6</div>
        <div class="col-md-3 col-sm-3 col-xs-3">col-md-3</div>
    </div>
    <div class="row">
        <div class="col-md-4 col-sm-4 col-xs-4">col-md-4</div>
        <div class="col-md-4 col-sm-4 col-xs-4">col-md-4</div>
        <div class="col-md-4 col-sm-4 col-xs-4">col-md-4</div>
    </div>
    <div class="row">
        <div class="col-md-4 col-sm-4 col-xs-4">col-md-4</div>
        <div class="col-md-8 col-sm-8 col-xs-8">col-md-8</div>
    </div>
    <div class="row">
        <div class="col-md-6 col-sm-6 col-xs-6">col-md-6</div>
        <div class="col-md-6 col-sm-6 col-xs-6">col-md-6</div>
    </div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">col-md-12</div>
    </div>

    <div class="row">
        <div class="col-md-3 col-sm-3 col-xs-3">
            <div class="row">
                <div class="col-md-5 col-sm-5 col-xs-5">5</div>
                <div class="col-md-7 col-sm-7 col-xs-7">7</div>
            </div>
        </div>
        <div class="col-md-3 col-sm-3 col-xs-3">
            <div class="row">
                <div class="col-md-5 col-sm-5 col-xs-5">5</div>
                <div class="col-md-7 col-sm-7 col-xs-7">7</div>
            </div>
        </div>
        <div class="col-md-3 col-sm-3 col-xs-3">
            <div class="row">
                <div class="col-md-5 col-sm-5 col-xs-5">5</div>
                <div class="col-md-7 col-sm-7 col-xs-7">7</div>
            </div>
        </div>
        <div class="col-md-3 col-sm-3 col-xs-3">
            <div class="row">
                <div class="col-md-5 col-sm-5 col-xs-5">5</div>
                <div class="col-md-7 col-sm-7 col-xs-7">7</div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-3 col-sm-3 col-xs-3">
            <div class="row">
                <div class="col-md-4 col-sm-4 col-xs-4">4</div>
                <div class="col-md-8 col-sm-8 col-xs-8">8</div>
            </div>
        </div>
        <div class="col-md-3 col-sm-3 col-xs-3">
            <div class="row">
                <div class="col-md-4 col-sm-4 col-xs-4">4</div>
                <div class="col-md-8 col-sm-8 col-xs-8">8</div>
            </div>
        </div>
        <div class="col-md-3 col-sm-3 col-xs-3">
            <div class="row">
                <div class="col-md-4 col-sm-4 col-xs-4">4</div>
                <div class="col-md-8 col-sm-8 col-xs-8">8</div>
            </div>
        </div>
        <div class="col-md-3 col-sm-3 col-xs-3">
            <div class="row">
                <div class="col-md-4 col-sm-4 col-xs-4">4</div>
                <div class="col-md-8 col-sm-8 col-xs-8">8</div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-3 col-sm-3 col-xs-3">
            <div class="row">
                <div class="col-md-3 col-sm-3 col-xs-3">3</div>
                <div class="col-md-9 col-sm-9 col-xs-9">9</div>
            </div>
        </div>
        <div class="col-md-3 col-sm-3 col-xs-3">
            <div class="row">
                <div class="col-md-3 col-sm-3 col-xs-3">3</div>
                <div class="col-md-9 col-sm-9 col-xs-9">9</div>
            </div>
        </div>
        <div class="col-md-3 col-sm-3 col-xs-3">
            <div class="row">
                <div class="col-md-3 col-sm-3 col-xs-3">3</div>
                <div class="col-md-9 col-sm-9 col-xs-9">9</div>
            </div>
        </div>
        <div class="col-md-3 col-sm-3 col-xs-3">
            <div class="row">
                <div class="col-md-3 col-sm-3 col-xs-3">3</div>
                <div class="col-md-9 col-sm-9 col-xs-9">9</div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-3 col-sm-3 col-xs-3">
            <div class="row">
                <div class="col-md-2 col-sm-2 col-xs-2">2</div>
                <div class="col-md-10 col-sm-10 col-xs-10">10</div>
            </div>
        </div>
        <div class="col-md-3 col-sm-3 col-xs-3">
            <div class="row">
                <div class="col-md-2 col-sm-2 col-xs-2">2</div>
                <div class="col-md-10 col-sm-10 col-xs-10">10</div>
            </div>
        </div>
        <div class="col-md-3 col-sm-3 col-xs-3">
            <div class="row">
                <div class="col-md-2 col-sm-2 col-xs-2">2</div>
                <div class="col-md-10 col-sm-10 col-xs-10">10</div>
            </div>
        </div>
        <div class="col-md-3 col-sm-3 col-xs-3">
            <div class="row">
                <div class="col-md-2 col-sm-2 col-xs-2">2</div>
                <div class="col-md-10 col-sm-10 col-xs-10">10</div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6 col-sm-6 col-xs-6">
            <div class="row">
                <div class="col-md-2 col-sm-2 col-xs-2">2</div>
                <div class="col-md-10 col-sm-10 col-xs-10">10</div>
            </div>
        </div>
        <div class="col-md-6 col-sm-6 col-xs-6">
            <div class="row">
                <div class="col-md-2 col-sm-2 col-xs-2">2</div>
                <div class="col-md-10 col-sm-10 col-xs-10">10</div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6 col-sm-6 col-xs-6">
            <div class="row">
                <div class="col-md-3 col-sm-3 col-xs-3">3</div>
                <div class="col-md-9 col-sm-9 col-xs-9">9</div>
            </div>
        </div>
        <div class="col-md-6 col-sm-6 col-xs-6">
            <div class="row">
                <div class="col-md-3 col-sm-3 col-xs-3">3</div>
                <div class="col-md-9 col-sm-9 col-xs-9">9</div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6 col-sm-6 col-xs-6">
            <div class="row">
                <div class="col-md-4 col-sm-4 col-xs-4">4</div>
                <div class="col-md-8 col-sm-8 col-xs-8">8</div>
            </div>
        </div>
        <div class="col-md-6 col-sm-6 col-xs-6">
            <div class="row">
                <div class="col-md-4 col-sm-4 col-xs-4">4</div>
                <div class="col-md-8 col-sm-8 col-xs-8">8</div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6 col-sm-6 col-xs-6">
            <div class="row">
                <div class="col-md-5 col-sm-5 col-xs-5">5</div>
                <div class="col-md-7 col-sm-7 col-xs-7">7</div>
            </div>
        </div>
        <div class="col-md-6 col-sm-6 col-xs-6">
            <div class="row">
                <div class="col-md-5 col-sm-5 col-xs-5">5</div>
                <div class="col-md-7 col-sm-7 col-xs-7">7</div>
            </div>
        </div>
    </div>

</div>

<style>
    .row {
        margin-top: 10px;
        margin-bottom: 10px;
    }

    .row [class*="col-md-"] {
        background-color: #eee;
        text-align: center;
        -webkit-border-radius: 3px;
        -moz-border-radius: 3px;
        border-radius: 3px;
        min-height: 40px;
        line-height: 40px;
        border: 1px solid #000;
    }

    .row [class*="col-md-"]:hover {
        background-color: #ddd;
    }

    .row .row {
        margin-top: 0;
        margin-bottom: 0;
    }

    .row .row [class*="col-md-"] {
        margin-top: 5px;
    }

    .row [class*="col-md-"] [class*="col-md-"] {
        background-color: #ccc;
    }

    .row [class*="col-md-"] [class*="col-md-"] [class*="col-md-"] {
        background-color: #999;
    }
</style>

</body>
</html>