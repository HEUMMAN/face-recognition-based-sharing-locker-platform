<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Fabinet 내정보</title>
        <link rel="icon" type="image/x-icon" href="assets/img/favicon.ico" /><div class="ui middle aligned center aligned grid"></div>
        <!-- Font Awesome icons (free version)-->
        <script src="https://use.fontawesome.com/releases/v5.15.1/js/all.js" crossorigin="anonymous"></script>
        <!-- Google fonts-->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
        <link href="https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic" rel="stylesheet" type="text/css" />
        <link href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700" rel="stylesheet" type="text/css" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="css/styles.css" rel="stylesheet" />
        <%--        제이쿼리--%>
        <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    </head>
    <body id="page-top">
        <!-- Navigation-->
        <nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
            <div class="container">
                <%--<a class="navbar-brand js-scroll-trigger" href="#page-top"><img src="assets/img/navbar-logo.svg" alt="" /></a>--%>
                <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation"/>
            </div>
        </nav>



        <!-- 시작 -->
        <br><br><br>
        <div class="container">

            <div class="card shadow mb-4">
                <div class="card-header py-3">
                    <h6 class="m-0 font-weight-bold text-primary">내 정보</h6>
                </div>
                <div class="card-body">
                    <div class="card shadow mb-4">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-lg-4 col-sm-6 mb-2">
                                    <div class="text-center">
                                        <%--세션이름 확인하여 사진가져오기--%>
                                        <img alt="" src="/getImage" width="300" height="400"/>
                                    </div>
                                </div>

                                <div class="col-lg-4 col-sm-6 mb-2">
                                    <div>
                                        <div align="center">
                                            <h4>물품보관함 사용법</h4><br>
                                        </div>
                                        1. 아래 메뉴에서 본인의 <strong>정면 얼굴</strong>이 또렷하게 나온 사진을 등록한다<br><br>
                                        2. 카메라 앞에서 눈을 두번 "깜빡깜빡" 한다<br><br>
                                        3. 혹은 휴대전화를 통해 수동 개방을 한다<br><br>
                                        4. 사용 종료 후 결제를 하면 마무리!<br><br>
                                    </div>
                                </div>
                                <div class="col-lg-4 col-sm-6 mb-2">
                                    <div>
                                        <table class="ui celled table">
                                            <thead>
                                            <tr>
                                                <th>이름</th>
                                                <th>아이디</th>
                                                <th>전화번호</th>
                                            </tr>
                                            </thead>
                                            <br>
                                            <tbody id="info">
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card shadow mb-4">
                        <div class="card-body">
                            <h4>사진을 선택하고 등록하기 버튼을 누르세요</h4>
                            <form action="/imgUpload" method="post" enctype="multipart/form-data">
                                <div class="field" style="margin-bottom: 10px">
                                    <div>
                                        <input multiple="multiple" type="file" name="img"/><br>
                                    </div>
                                </div>

                                <div class="btn btn-primary btn-user btn-block">
                                    <button class="btn btn-primary btn-user btn-block" type="submit" name="save">
                                        사진등록하기
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>

                </div>
            </div>

            <div class="card o-hidden border-0 shadow-lg my-5">
                <div class="card-body p-0"  style=" display: inline-block; text-align: center;">
                    <!-- Nested Row within Card Body -->
                    <div class="text-center">
                        <br>
                        <h1 class="h4 text-gray-900 mb-4">사용내역</h1>
                    </div>

                    <div class="ui middle aligned center aligned grid">
                        <div class="column">
                            <!-- <h1 class="section-heading text-uppercase" align="center">게시판</h1> -->
                            <br>
                            <div class="ui middle aligned center aligned grid">
                                <div class="ui middle aligned center aligned grid">
                                </div>
                                <div class="ui middle aligned center aligned grid">
                                    <table class="ui celled table">
                                        <thead>
                                            <tr>
                                                <th>번호</th>
                                                <th>사물함</th>
                                                <th>시작시간</th>
                                                <th>종료시간</th>
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
                </div>
            </div>
        </div>
        <!-- 끝 -->

        <!-- Footer-->
        <footer class="footer py-4">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-lg-4 text-lg-left">Copyright © KPU Fabinet 2021</div>
                    <div class="col-lg-4 my-3 my-lg-0">
                        <a class="btn btn-dark btn-social mx-2" href="#!"><i class="fab fa-twitter"></i></a>
                        <a class="btn btn-dark btn-social mx-2" href="#!"><i class="fab fa-facebook-f"></i></a>
                        <a class="btn btn-dark btn-social mx-2" href="#!"><i class="fab fa-linkedin-in"></i></a>
                    </div>
                    <div class="col-lg-4 text-lg-right">
                        <a class="mr-3" href="#!">Privacy Policy</a>
                        <a href="#!">Terms of Use</a>
                    </div>
                </div>
            </div>
        </footer>

        <script>
            $(document).ready(function() {
                $.ajax({
                    type: "get",
                    url: "/mypage/info",
                    success: function(data) {
                        console.log(data);
                        var tr = $("<tr></tr>").attr("data-id", data['b_no']).appendTo("#info");
                        $("<td></td>").text(data['name']).appendTo(tr);
                        $("<td></td>").text(data['loginId']).appendTo(tr);
                        $("<td></td>").text(data['tel']).appendTo(tr);
                    },
                    /*error: function(error) {
                        alert("오류 발생" + error);
                    }*/
                });

                $.ajax({
                    type: "get",
                    url: "/mypage/list",
                    success: function(data) {
                        console.log(data);
                        for (var str in data) {
                            var tr = $("<tr></tr>").attr("data-id", data[str]['b_no']).appendTo("#list");
                            $("<td></td>").text(data[str]['id']).addClass("view_btn").appendTo(tr);
                            $("<td></td>").text(data[str]['name']).addClass("view_btn").appendTo(tr);
                            $("<td></td>").text(FormatToUnixtime(data[str]['startTime'])).addClass("view_btn").appendTo(tr);
                            $("<td></td>").text(FormatToUnixtime(data[str]['endTime'])).addClass("view_btn").appendTo(tr);
                        }
                    },
                    /*error: function(error) {
                        alert("오류 발생" + error);
                    }*/
                });
                function TimeDiff(unixtime1,unixtime2) {
                    var t1 = new Date(unixtime1);
                    var t2 = new Date(unixtime2);
                    return parseInt(t1-t2);
                };

                function FormatToUnixtime(unixtime) {
                    if(unixtime == null){
                        return "";
                    }
                    else {
                        var u = new Date(unixtime);
                        console.log("u: " + u);
                        return u.getUTCFullYear() +
                            '-' + ('0' + (u.getMonth() + 1)).slice(-2) +
                            '-' + ('0' + u.getDate()).slice(-2) +
                            ' ' + ('0' + u.getHours()).slice(-2) +
                            ':' + ('0' + u.getMinutes()).slice(-2)
                    }
                };
            });

        </script>

        <!-- Bootstrap core JS-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Third party plugin JS-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
        <!-- Contact form JS-->
        <script src="assets/mail/jqBootstrapValidation.js"></script>
        <script src="assets/mail/contact_me.js"></script>
        <!-- Core theme JS-->
        <script src="js/scripts.js"></script>
    </body>
</html>
