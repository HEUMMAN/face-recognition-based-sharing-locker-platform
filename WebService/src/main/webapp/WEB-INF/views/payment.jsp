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
    </div>

    <script>
        //console.log("시작");
        //location.href = "/bill/stopUsing/"+${userInfo.num};
        //console.log("종료");

        //지금 테스트중. 겟방식으로 stopUsing 페이지로 넘어간 후 거기서 ajax로 post의 delete 메소드 호출할 예정
        //근데 지금 Resolved [org.springframework.dao.InvalidDataAccessApiUsageException: Update/delete queries cannot be typed; nested exception is java.lang.IllegalArgumentException: Update/delete queries cannot be typed]
        //라는 에러가떠서 못하는중
        //만약 해결한다면 stopUsing페이지로 리다이렉션하는 location.href를 success 블록에 넣자

        var num = '${userInfo.num}';
        var money = '${userInfo.money}';
        var name = '${userInfo.name}';
        var email = '${userInfo.email}';
        var tel = '${userInfo.tel}';
        console.log("결제금액: "+money);
        console.log("사용자이름: "+name);
        console.log("사용자이메일: "+email);
        console.log("사용자번호: "+tel);
        //var money = 102;
        IMP.init('imp50322150');
        IMP.request_pay({
            pg : 'html5_inicis',
            pay_method : 'card',
            merchant_uid : 'merchant_' + new Date().getTime(),
            name : '주문명:결제테스트',
            amount : money,
            buyer_email : email,
            buyer_name : name,
            buyer_tel : tel,
            buyer_addr : '서울특별시 강남구 삼성동',
            buyer_postcode : '123-456'
        }, function(rsp) {
            if ( rsp.success ) {
                var msg = '결제가 완료되었습니다.\n';
                //msg += '고유ID : ' + rsp.imp_uid;
                //msg += '상점 거래ID : ' + rsp.merchant_uid;
                //msg += '결제 금액 : ' + rsp.paid_amount;
                msg += '카드 승인번호 : ' + rsp.apply_num;

                //사물함 사용종료 실행
                location.href = "/delete/"+${userInfo.num};
                //location.href = "/bill/stopUsing/"+${userInfo.num};
            } else {
                var msg = '결제에 실패하였습니다.';
                msg += '에러내용 : ' + rsp.error_msg;
            }
            alert(msg);
        });
    </script>
</body>
</html>
