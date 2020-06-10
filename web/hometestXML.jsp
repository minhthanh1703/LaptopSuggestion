<%-- 
    Document   : hometestXML
    Created on : Mar 23, 2020, 4:48:19 PM
    Author     : minht
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <style>
            body {
                background-color: white;
            }

            .container {
                float: left;
                width: 100%;
                height: 100%;
                background-color: lightgrey;
                margin-left: auto;
                margin-right: auto;
            }

            .container_class {
                float: left;
                background-color: lightgrey;
                width: 60%;
                height: 100%;
                margin-left: 20%;
            }

            .title {

                float: left;
                position: static;
                background-color: white;
                width: 95%;
                height: inherit;
                padding-left: 5%;
                border-radius: 20px 20px;
                margin: 2%;
                box-shadow: 2px 2px 2px 0px;
                padding: 17px;

            }

            .button {
                padding: 13px 20px;
                font-size: 18px;
                text-align: center;
                cursor: pointer;
                outline: none;
                color: #fff;
                background-color: #365899;
                border: none;
                border-radius: 15px;
                box-shadow: 0 9px #999;
                margin-left: 2%;
            }

            .button:hover {
                background-color: #34495E
            }

            .button:active {
                background-color: #365899;
                box-shadow: 0 5px #666;
                transform: translateY(4px);
            }
        </style>
        <script>
            function formValidation() {
                x = 0;
                var question1 = document.answer.question1.value;
                var question2 = document.answer.question2.value;
                var question3 = document.answer.question3.value;
                var question4 = document.answer.question4.value;
                var question5 = document.answer.question5.value;
                var question6 = document.answer.question6.value;
                var question7 = document.answer.question7.value;

                if (question1 != "") {
                    x++;
                }
                if (question2 != "") {
                    x++;
                }
                if (question3 != "") {
                    x++;
                }
                if (question4 != "") {
                    x++;
                }
                if (question5 != "") {
                    x++;
                }
                if (question6 != "") {
                    x++;
                }
                if (question7 != "") {
                    x++;
                }


                console.log("so cau tra loi: " + x);

                if (x != 7) {
                    alert('Vui lòng trả lời hết câu hỏi');
                    return false;
                } else {
                    return true;
                }

            }
        </script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Test Page</title>
    </head>
    <body>
        <div class="container">
            <div class="container_class">
                <div class="title">
                    <font color="#365899" size="18">Gợi ý mua laptop theo câu hỏi</font>
                    <p>Bằng cách trả lời những câu hỏi, hệ thống sẽ gợi ý cho bạn những laptop phù hợp với bạn</p>
                    <font color="red">*Bắt buộc</font>
                </div>
                <form action="DispatcherServlet" name="answer" onSubmit="return formValidation();">
                    <c:import var="questionBank" url="http://localhost:8080/final_v2/question.xml"/>
                    <x:parse xml="${questionBank}" var="questions"/>
                    <x:forEach var="question" select="$questions//question" varStatus="counter">
                        <div class="title">
                            <h3>
                                <x:out select="$question/title" />
                            </h3>
                            <x:forEach var="answer" select="$question//answers/answer">
                                <input type="radio" name=<x:out select="$question/name"/> value=<x:out select="$answer/@value"/>>
                                <label><x:out select="$answer"/></label></br>
                            </x:forEach>
                        </div>
                    </x:forEach>
                    <button class="button" type="submit" name="action" value="Search">
                        Gợi ý
                    </button>
                </form>
            </div>
        </div>

    </body>
</html>
