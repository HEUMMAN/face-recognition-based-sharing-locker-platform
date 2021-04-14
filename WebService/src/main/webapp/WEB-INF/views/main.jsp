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
</head>

<body>
    <div class="ui middle aligned center aligned grid">
        <div class="column">
            <h1 class="ui teal image header">
                Fabinet
            </h1>
            <br>
            <h3 class="ui teal image header">
                가장 편리한 공유 사물함 서비스
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
                <%
                    if(userId != "guest"){
                        out.println("<a href='/logout'><button class='ui fluid large teal submit button'>로그아웃</button></a>");
                    }
                    else{
                        out.println("<a href='/login'><div class='ui fluid large teal submit button' id='logout'>로그인</div></a>");
                    }
                %>
            </div>
            <div class="ui large form">
                <div class="ui stacked segment">
                    <a href="/boards"><button class="ui fluid large teal submit button">페비넷 게시판</button></a>
                    <br>
                    <a href="/toChooseCabinet"><button class="ui fluid large teal submit button" id="go_cabinet">사물함 선택</button></a>
                    <br>
                    <a href="/adjustment"><div class="ui fluid large teal submit button" id="go_payment">정산하기</div>

                    <form action="/imgUpload" method="post" enctype="multipart/form-data">
                        <div class="field">
                            <div>
                                <input class="ui fluid large teal submit button" value="파일 선택" type="file" name="img"/>
                            </div>
                        </div>

                        <div>
                            <button class="ui fluid large teal submit button" type="submit" name="save">
                                사진등록하기<i class="ui fluid large teal submit button"></i>
                            </button>
                        </div>
                    </form>

                    <img alt="" src="/getImage" />

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

    <!-- js 가져오기 -->
    <script src="/jquery3.3.1.min.js"></script>
    <script src="/semantic.min.js"></script>

    <script>
        /*$(document).ready(function() {
            $("#go_payment").click(function() {
                $.ajax({
                    type: "get",
                    url: "isPaymentLogined",
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
            });*/

            /*$("#go_payment").click(function() {
                $.ajax({
                    type: "get",
                    url: "/adjustment",
                });
            });*/

    </script>
</body>

</html>
