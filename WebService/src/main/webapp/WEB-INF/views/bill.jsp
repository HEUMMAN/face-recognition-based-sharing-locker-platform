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
                                                <th>#</th>
                                                <th>사물함</th>
                                                <th>시작시간</th>
                                                <th>경과시간</th>
                                                <th>요금</th>
                                                <th>선택</th>   <!--몇개 골라서 결제가능하도록-->
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
                            $("<td></td>").text(data[str]['tt']).addClass("view_btn").appendTo(tr);
                            $("<td></td>").checkbox(data[null]).addClass("view_btn").appendTo(tr);  //체크박스 넣어서 선택한 사물함만 결제할수있도록 하자
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
                            alert("읽기 페이지 아직 안만듬" + error);
                        }
                    });
                });

                function FormatToUnixtime(unixtime) {
                    var u = new Date(unixtime);
                    return u.getUTCFullYear() +
                        '-' + ('0' + u.getUTCMonth()).slice(-2) +
                        '-' + ('0' + u.getUTCDate()).slice(-2)
                     ' ' + ('0' + u.getUTCHours()).slice(-2) +
                     ':' + ('0' + u.getUTCMinutes()).slice(-2)
                    // ':' + ('0' + u.getUTCSeconds()).slice(-2)
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
