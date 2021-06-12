<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Fabinet</title>
    <%--아임포트 API--%>
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
    <script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
</head>

<body>
    <div class="ui modal" id='view_modal' align="center">
        <h2>잠시만 기다려주세요..</h2>
        번호는 ${num}
    </div>

    <script>
        $(document).ready(function(){
            console.log("ajax 시작")
            var num = ${num};
            console.log(typeof num);
            $.ajax({
                type : "POST",
                //url: "/bill/pay?id="+b_no,
                //url : "/bill/stopUsing?id="+num,
                url : "/bill/stopUsing/"+num,
                contentType: 'application/json',
                success : function(data) {
                    console.log("실행완료");
                    //location.href="/";
                }
            });
        });
    </script>
</body>
</html>
