<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Fabinet</title>
    <!-- css 가져오기 -->
    <link rel="stylesheet" type="text/css" href="/semantic.min.css">

    <style type="text/css">
        body {
            background-color: #DADADA;
        }

        body>.grid {
            height: 100%;
        }

        .image {
            margin-top: -100px;
        }

        .column {
            max-width: 1000px;
        }

        .view_btn {
            cursor: pointer;
        }

    </style>

    <%--아임포트 API--%>
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
    <script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
</head>

<body>
    <div class="ui middle aligned center aligned grid">
        <div class="column">
            <h1 class="ui teal image header">
                Fabinet
            </h1>
            <br>
            <h3 class="ui teal image header">
                정산하기
            </h3>
            <div class="ui stacked segment">
                <%
                    String getSession = "";
                    String userId = "";
                    getSession = (String)session.getAttribute("loginMemberId");            // request에서 id 파라미터를 가져온다
                    if(getSession==null||getSession.equals("")){                            // id가 Null 이거나 없을 경우
                        userId = "guest";
                    }
                    else{
                        userId = getSession;
                    }
                %>

                어서오세요 <%=userId %>님
            </div>
            <div class="ui large form">
                <div class="ui stacked segment">
                    <%--<form class="ui stacked segment" action="/" method="post">
                        <button type="submit" class="btn btn-primary btn-user btn-block">등록하기</button>
                    </form>--%>
                    DB에서 사물함 사용한 시간을 다 조회해와서 현재 순간까지 지난 시간을 계산하여 결제하기를 누르면 payment로 넘어가며
                    지불할 가격이 스트링으로 넘어간다
                    예시 가격: 10
                    서버에서 넘어온 가격: ${payMoney}
                    <br>
                        <form method="post", action="/doPayment">
                            <input type="submit" class="ui fluid large teal submit button" value="정산하기">
                        </form>
                </div>
                <div class="ui error message"></div>
            </div>
        </div>
    </div>

    <div class="ui modal" id='view_modal'>
        <i class="close">x</i>
        <div class="header" id="b_title">
            
        </div>
        <div class="content">
            <div class="description">
            	<p style = "text-align: right" id = "b_review"></p>
            	<div id = 'b_content'></div>
            </div>
        </div>
        <div class="actions">
            <div class="ui black deny button">
                닫기
            </div>
        </div>
    </div>
    <%--<script>
        $(document).ready(function() {
            $("#doPayment").click(function() {

                var json = {
                    money: $("#payMoney").val() //서버측에서 model로 결제할 금액 넘겨야할듯 그리고 여기서 $로 사용하자
                };

                $.ajax({
                    type: "POST",
                    url: "doPayment",
                    data : json,
                    success: function (data) {
                        switch (Number(data)) {
                            case -1:
                                alert("로그인 후 이용해주세요.");
                                window.location.href = "/";
                                break;
                            case 1:
                                window.location.href = "/toCheckBill";
                                break;

                            default:
                                alert("알수없는 오류가 발생 했습니다.[Error Code : " + Number(data) + "]");
                                break;
                        }
                    },
                    error: function (error) {
                        alert("오류 발생" + error);
                    }
                });
            });
        });

    </script>--%>

    <!-- js 가져오기 -->
    <script src="/jquery3.3.1.min.js"></script>
    <script src="/semantic.min.js"></script>

    <script>

    </script>
</body>

</html>
