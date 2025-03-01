<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>사물함 선택하기</title>
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
            <h2 class="ui teal image header">
                사물함 선택하기
            </h2>
            <div class="ui large form">
                <div class="ui stacked segment">
                    <form action="/chooseCabinet" method="post">
                        <table class="ui celled table">
                            <thead>
                                <tr>
                                    <select id="select1", name="select1" onchange="itemChange()">
                                        <option>A동</option>
                                        <option>B동</option>
                                        <option>D동</option>
                                        <option>E동</option>
                                        <option>산융</option>
                                    </select>

                                    <select id="select2", name="select2" onchange="itemChange2()">
                                    </select>

                                    <select id="select3", name="select3">
                                    </select>
                                </tr>
                            </thead>
                            <tbody id="list">
                            </tbody>
                        </table>
                        <%--<a href="/chooseCabinet"><button class="ui fluid large teal submit button">선택완료</button></a>--%>
                        <input type="submit" class="ui fluid large teal submit button" value="선택완료">
                        <!--선택 완료 누르면 선택정보 재확인 및 선불 후불 결제 페이지로 넘어가자-->
                    </form>
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

    <%--<script type="text/javascript">
        var target = document.getElementById("select1");
        alert('선택된 옵션 text 값=' + target.options[target.selectedIndex].text);     // 옵션 text 값
        alert('선택된 옵션 value 값=' + target.options[target.selectedIndex].value);     // 옵션 value 값
    </script>
    <script type="text/javascript">
        var target = document.getElementById("select2");
        alert('선택된 옵션 text 값=' + target.options[target.selectedIndex].text);     // 옵션 text 값
        alert('선택된 옵션 value 값=' + target.options[target.selectedIndex].value);     // 옵션 value 값
    </script>
    <script type="text/javascript">
        var target = document.getElementById("select3");
        alert('선택된 옵션 text 값=' + target.options[target.selectedIndex].text);     // 옵션 text 값
        alert('선택된 옵션 value 값=' + target.options[target.selectedIndex].value);     // 옵션 value 값
    </script>--%>



    <script>
        //아 어자피 DB 조회해서 사용가능한 사물함만 보이도록 해야한다.
        function itemChange(){
            var A_floor = ["1층","2층","3층"];
            var B_floor = ["1층","2층","3층"];
            var D_floor = ["1층","2층","3층"];
            var E_floor = ["1층","2층","3층"];
            var San_floor = ["1층","2층","3층"];

            selectItem = $("#select1").val();   //이거 전역변수다. 밑에 itemChange2에서도 사용해야하므로.
            var changeItem;

            if(selectItem == "A동"){
                changeItem = A_floor;
            }
            else if(selectItem == "B동"){
                changeItem = B_floor;
            }
            else if(selectItem == "D동"){
                changeItem =  D_floor;
            }
            else if(selectItem == "E동"){
                changeItem = E_floor;
            }
            else if(selectItem == "산융"){
                changeItem =  San_floor;
            }

            $('#select2').empty();

            for(var count = 0; count < changeItem.length; count++){
                var option = $("<option>"+changeItem[count]+"</option>");
                $('#select2').append(option);
            }
        }
    </script>

    <script>
        function itemChange2(){
            var A1_num = ["A동 1층","1-1","1-2","1-3","1-4","1-5"];
            var A2_num = ["A동 2층","2-1","2-2","2-3"];
            var A3_num = ["A동 3층","3-1","3-2","3-3","3-4","3-5","3-6"];

            var B1_num = ["B동 1층","1-1","1-2","1-3","1-4","1-5"];
            var B2_num = ["B동 2층","2-1","2-2","2-3"];
            var B3_num = ["B동 3층","3-1","3-2","3-3","3-4","3-5","3-6"];

            var D1_num = ["D동 1층","1-1","1-2","1-3","1-4","1-5"];
            var D2_num = ["D동 2층","2-1","2-2","2-3"];
            var D3_num = ["D동 3층","3-1","3-2","3-3","3-4","3-5","3-6"];

            var E1_num = ["E동 1층","1-1","1-2","1-3","1-4","1-5"];
            var E2_num = ["E동 2층","2-1","2-2","2-3"];
            var E3_num = ["E동 3층","3-1","3-2","3-3","3-4","3-5","3-6"];

            var San1_num = ["산융 1층","1-1","1-2","1-3","1-4","1-5"];
            var San2_num = ["산융 2층","2-1","2-2","2-3"];
            var San3_num = ["산융 3층","3-1","3-2","3-3","3-4","3-5","3-6"];

            //selectItem은 지금 어느 건물인지 담겨있다.
            //selectItem2는 몇층인지 담겨있다.
            var selectItem2 = $("#select2").val();

            if(selectItem == "A동"){
                if(selectItem2 == "1층"){
                    changeItem = A1_num;
                }
                else if(selectItem2 == "2층"){
                    changeItem = A2_num;
                }
                else if(selectItem2 == "3층"){
                    changeItem = A3_num;
                }
            }
            if(selectItem == "B동"){
                if(selectItem2 == "1층"){
                    changeItem = B1_num;
                }
                else if(selectItem2 == "2층"){
                    changeItem = B2_num;
                }
                else if(selectItem2 == "3층"){
                    changeItem = B3_num;
                }
            }
            if(selectItem == "D동"){
                if(selectItem2 == "1층"){
                    changeItem = D1_num;
                }
                else if(selectItem2 == "2층"){
                    changeItem = D2_num;
                }
                else if(selectItem2 == "3층"){
                    changeItem = D3_num;
                }
            }
            if(selectItem == "E동"){
                if(selectItem2 == "1층"){
                    changeItem = E1_num;
                }
                else if(selectItem2 == "2층"){
                    changeItem = E2_num;
                }
                else if(selectItem2 == "3층"){
                    changeItem = E3_num;
                }
            }
            if(selectItem == "산융"){
                if(selectItem2 == "1층"){
                    changeItem = San1_num;
                }
                else if(selectItem2 == "2층"){
                    changeItem = San2_num;
                }
                else if(selectItem2 == "3층"){
                    changeItem = San3_num;
                }
            }

            $('#select3').empty();

            for(var count = 0; count < changeItem.length; count++){
                var option = $("<option>"+changeItem[count]+"</option>");
                $('#select3').append(option);
            }
        }
    </script>
</body>

</html>
