<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Fabinet 회원가입</title>
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
                <a class="navbar-brand js-scroll-trigger" href="/"><img src="assets/img/FabinetHome2.png" alt="" /></a>
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
                        <h1 class="h4 text-gray-900 mb-4">사진을 등록해주세요</h1>
                        <p style="text-align: center">
                            <strong>
                            1. 자신의 얼굴이 명확하게 보이는 밝은 사진<br>
                            2. 마스크, 필터를 사용한 사진은 안돼요!   <br>
                            3. 내정보 페이지에서 수정할 수 있어요<br>
                            </strong>
                        </p>
                    </div>
                    <div class="row" style=" display: inline-block; text-align: center;">
                            <div class="card mb-4">
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
                        <br><br>
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
        <%--<script>
            $(document).ready(function() {
                $.ajax({
                    type: "get",
                    url: "/bill/cabinets",
                    dataType: "json",
                    cache: false,
                    success: function (data) {
                        console.log(data);
                        $('#selectCabinet').empty();
                        $.each(data, function (key, value) {
                            $('#selectCabinet').append('<option value="' + value + '">' + value + '</option>');
                        });
                    },
                });
            });
        </script>
        <script>
            $(document).ready(function(){
                $("#selectCabinetBtn").click(function(){
                    var json = {
                        selectOne : $("#selectCabinet option:selected").val()
                    };

                    $.ajax({
                        type : "POST",
                        url : "/bill/chooseCabinet",
                        data : JSON.stringify(json),
                        contentType: 'application/json',
                        success : function(data) {
                            console.log(data);
                            if (data == 'occupied') {
                                alert('이미 사용중인 자리입니다.');
                            }
                            else if(data == 'available'){
                                alert('사용 시작');
                                location.href = "/";
                            }
                        },
                        error: function (error) {
                            alert("오류 발생" + this.data);
                        }
                    });
                });
            });
        </script>--%>
    </body>
</html>
