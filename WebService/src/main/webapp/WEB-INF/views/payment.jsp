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
                    ${money}원 결제
                    <a href="/toBoardList"><button class="ui fluid large teal submit button">페비넷 게시판</button></a>
                    <br>
                    <a href="/toChooseCabinet"><button class="ui fluid large teal submit button" id="go_cabinet">사물함 선택</button></a>
                    <br>
                    <a href="/toPayment"><button class="ui fluid large teal submit button" id="go_payment">사용종료</button></a>
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
        var money = '${money}';
        IMP.init('imp50322150');
        IMP.request_pay({
            pg : 'html5_inicis',
            pay_method : 'card',
            merchant_uid : 'merchant_' + new Date().getTime(),
            name : '주문명:결제테스트',
            amount : money,
            buyer_email : 'iamport@siot.do',
            buyer_name : '구매자이름',
            buyer_tel : '010-1234-5678',
            buyer_addr : '서울특별시 강남구 삼성동',
            buyer_postcode : '123-456'
        }, function(rsp) {
            if ( rsp.success ) {
                var msg = '결제가 완료되었습니다.';
                msg += '고유ID : ' + rsp.imp_uid;
                msg += '상점 거래ID : ' + rsp.merchant_uid;
                msg += '결제 금액 : ' + rsp.paid_amount;
                msg += '카드 승인번호 : ' + rsp.apply_num;
            } else {
                var msg = '결제에 실패하였습니다.';
                msg += '에러내용 : ' + rsp.error_msg;
            }

            alert(msg);
        });
    </script>
</body>

</html>
