<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>게시판 페이지</title>
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
                공지사항
            </h3>
            <div class="ui large form">
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
                    <%=userId %>님
                </div>
                <div class="ui stacked segment">
                    <a href="/write_bbs"><button class="ui fluid large teal submit button">게시글 작성하기</button></a>
                    <table class="ui celled table">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>제목</th>
                                <th>작성자</th>
                                <th>등록일</th>
                            </tr>
                        </thead>
                        <br>
                        <tbody id="list">
                        </tbody>
                    </table>
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
        $(document).ready(function() {
            $.ajax({
                type: "get",
                url: "bbs_all",
                success: function(data) {
                    console.log(data);
                        for (var str in data) {
                            var tr = $("<tr></tr>").attr("data-id", data[str]['b_no']).appendTo("#list");
                            $("<td></td>").text(data[str]['id']).addClass("view_btn").appendTo(tr);
                            $("<td></td>").text(data[str]['author']).addClass("view_btn").appendTo(tr);
                            $("<td></td>").text(data[str]['title']).addClass("view_btn").appendTo(tr);
                            $("<td></td>").text(FormatToUnixtime(data[str]['date'])).addClass("view_btn").appendTo(tr);
                        }
                },
                /*error: function(error) {
                    alert("오류 발생" + error);
                }*/
            });

            $(document).on("click", ".view_btn", function() {
                var b_no = $(this).parent().attr("data-id");

                $.ajax({
                    type: "get",
                    url: "get_bbs",
                    data: {
                        b_no: b_no
                    },
                    success: function(data) {
                    	console.log(data);
                    	$("#b_title").text(data['b_title']);
                    	$("#b_review").text(data['b_ownernick'] + " - " +  FormatToUnixtime(data['b_regdate']));
                    	$("#b_content").text(data['b_content']);
                    	$('#view_modal').modal('show');
                    },
                    error: function(error) {
                        alert("오류 발생" + error);
                    }
                });
            });

            function FormatToUnixtime(unixtime) {
                var u = new Date(unixtime);
                return u.getUTCFullYear() +
                    '-' + ('0' + u.getUTCMonth()).slice(-2) +
                    '-' + ('0' + u.getUTCDate()).slice(-2)
                    // ' ' + ('0' + u.getUTCHours()).slice(-2) +
                    // ':' + ('0' + u.getUTCMinutes()).slice(-2) +
                    // ':' + ('0' + u.getUTCSeconds()).slice(-2)
            };

            /*$("#write_bbs").click(function() {
                $.ajax({
                    type: "post",
                    url: "createBoard",
                    success: function(data) {
                        switch (Number(data)) {
                            case -1:
                                alert("로그인 후 이용해주세요.");
                                window.location.href = "/";
                                break;
                            default:
                                alert("알수없는 오류가 발생 했습니다.[Error Code : " + Number(data) + "]");
                                break;
                        }
                    },
                    /!*error: function(error) {
                        alert("오류 발생" + error);
                    }*!/
                });
            });*/
        });

    </script>
</body>

</html>
