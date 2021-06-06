<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Agency - Start Bootstrap Theme</title>
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
                <a class="navbar-brand js-scroll-trigger" href="#page-top"><img src="assets/img/navbar-logo.svg" alt="" /></a>
                <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation"/>
            </div>
        </nav>

        <!-- 시작 -->
        <br><br><br>
        <div class="container">
            <div class="card o-hidden border-0 shadow-lg my-5">
                <div class="card-body p-0"  style=" display: inline-block; text-align: center;">
                    <!-- Nested Row within Card Body -->
                    <div class="text-center">
                        <br>
                        <h1 class="h4 text-gray-900 mb-4">요금확인</h1>
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
                                                <th>요금</th>   <!--몇개 골라서 결제가능하도록-->
                                                <th>선택</th>
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

        <div class="container">
            <div class="card o-hidden border-0 shadow-lg my-5" align="center">
                <h5>합계요금</h5>
                <br><br>
                <div id="entiremoney">
                </div>
                <div class="ui middle aligned center aligned grid" style="text-align:center">
                    <a href="/bill/pay"><button class="btn btn-primary btn-xl text-uppercase js-scroll-trigger">결제하기</button></a>
                    <br>
                </div>
            </div>
        </div>

        <!-- Footer-->
        <footer class="footer py-4">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-lg-4 text-lg-left">Copyright © FABINET 2021</div>
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
                var entiremoneyVar;
                var now = new Date();
                $.ajax({
                    type: "get",
                    url: "/bill/list",
                    success: function(data) {
                        console.log(data);
                        for (var str in data) {
                            var tr = $("<tr></tr>").attr("data-id", data[str]['b_no']).appendTo("#list");
                            $("<td></td>").text(data[str]['id']).addClass("view_btn").appendTo(tr);
                            $("<td></td>").text(data[str]['name']).addClass("view_btn").appendTo(tr);
                            $("<td></td>").text(FormatToUnixtime(data[str]['startTime'])).addClass("view_btn").appendTo(tr);
                            $("<td></td>").text((Math.floor(TimeDiff(now,new Date(data[str]['startTime']))* 0.00001541666/10)*10+1000)+ " 원").addClass("view_btn").appendTo(tr);
                            $("<td><div class='btn btn-primary btn-user btn-block' id='pay_btn'>결제하기</div></td>").appendTo(tr);

                            //$("<td><input name='rowCheck' type='checkbox' id='${id}' checked='checked' /></td>").appendTo(tr);
                            //$("<td><button type='button' class='btn btn-primary' id='pay_btn'>결제하기</button></td>").appendTo(tr);
                            entiremoneyVar += Math.floor(TimeDiff(now,new Date(data[str]['startTime']))* 0.00001541666/10)*10+1000;
                        }
                    }
                    /*error: function(error) {
                        alert("오류 발생" + error);
                    }*/
                });

                $(document).on("click", ".view_btn", function() {
                    var b_no = $(this).parent().attr("name");    //이거 글번호 읽기가 안된다 어케하지
                    console.log(b_no);
                    console.log(typeof b_no)
                    $.ajax({
                        type: "get",
                        url: "/bill/pay?id="+b_no,
                        contentType : "application/json; charset=UTF-8",
                        dataType : "json",
                        data: {
                            b_no: b_no
                        },
                        success: function(data) {
                            console.log(data);
                            location = "/";
                        },
                        error: function(error) {
                            alert("게시글 읽기 페이지 아직 안만듬" + error);
                        }
                    });
                });


                function TimeDiff(unixtime1,unixtime2) {
                    var t1 = new Date(unixtime1);
                    var t2 = new Date(unixtime2);
                    return parseInt(t1-t2);
                };

                function FormatToUnixtime(unixtime) {
                    var u = new Date(unixtime);
                    console.log("u: " + u);
                    return u.getUTCFullYear() +
                        '-' + ('0' + u.getMonth()).slice(-2) +
                        '-' + ('0' + u.getDate()).slice(-2) +
                        ' ' + ('0' + u.getHours()).slice(-2) +
                        ':' + ('0' + u.getMinutes()).slice(-2)
                    // ':' + ('0' + u.getUTCSeconds()).slice(-2)
                };
            });
        </script>

        <%--<script type="text/javascript">
            $(document).ready(function(){
                $("#pay_btn").click(function(){
                    alert("눌림");
                    var b_no = $(this).parent().attr("name");    //이거 글번호 읽기가 안된다 어케하지
                    console.log(b_no);
                    $.ajax({
                        type: "get",
                        url: "/bill/pay?id="+b_no,
                        contentType : "application/json; charset=UTF-8",
                        dataType : "json",
                        data: {
                            b_no: b_no
                        },
                        success: function(data) {
                            console.log(data);
                            location.href = "/";
                            // $("#b_title").text(data['b_title']);
                            // $("#b_review").text(data['b_ownernick'] + " - " +  FormatToUnixtime(data['b_regdate']));
                            // $("#b_content").text(data['b_content']);
                            // $('#view_modal').modal('show');
                        },
                        error: function(error) {
                            alert("게시글 읽기 페이지 아직 안만듬" + error);
                        }
                    });
                });
            });
        </script>--%>

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
