<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Fabinet 사용하기</title>
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
                        <h1 class="h4 text-gray-900 mb-4">사용할 사물함을 선택하세요</h1>
                    </div>
                    <div class="row" style=" display: inline-block; text-align: center;">

                            <div class="card mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">사물함 선택</h6>
                                </div>
                                <div class="card-body">
                                    <select name="selectCabinet" class="btn btn-secondary dropdown-toggle" id="selectCabinet">
                                        <option id="opt1">========선택하기========</option>
                                    </select>
                                </div>
                            </div>
<%--                            <button type="submit"--%>
<%--                                    onClick="location.href='/'"--%>
<%--                                    class="btn btn-primary btn-user btn-block">사용하기</button>--%>

                        <div class="btn btn-primary btn-user btn-block" id = "selectCabinetBtn">사용시작</div>

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
        <script>
            $(document).ready(function() {
                $.ajax({
                    type: "get",
                    url: "/bill/cabinets",
                    dataType: "json",
                    cache: false,
                    success: function (data) {
                        console.log(data);
                        $('#selectCabinet').empty();
                        // $.each(data, function (key, value) {
                        //     $('#selectCabinet').append('<option value="' + value + '">' + value + '</option>');
                        // });
                        if(data.length == 0){
                            $.each(data, function (key, value) {
                                $('#selectCabinet').append('<option value="">사용 가능한 사물함이 없습니다.</option>');
                            });
                        }
                        else{
                            $.each(data, function (key, value) {
                                $('#selectCabinet').append('<option value="' + value + '">' + value + '</option>');
                            });
                        }
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
                            alert('사용 시작');
                            location.href = "/";
                        },
                        error: function (error) {
                            alert("오류 발생" + this.data);
                        }
                    });
                });
            });
        </script>
    </body>
</html>
