<%-- 
    Document   : hometest
    Created on : Mar 12, 2020, 4:05:35 PM
    Author     : minht
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                
                
                console.log("so cau tra loi: " +x);
                
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
                    <div class="title">
                        <h3>
                            1) Nhu cầu của bạn như thế nào?
                        </h3>
                        <input type="radio" name="question1" value="Học tập">
                        <label for="A">A. Học tập, làm việc văn phòng cơ bản đơn thuần</label></br>

                        <input type="radio" name="question1" value="Dành cho người chơi game">
                        <label for="B">B. Dành cho người chơi game</label></br>

                        <input type="radio" name="question1" value="Dành cho doanh nhân">
                        <label for="C">C. Dành cho doanh nhân</label></br>

                        <input type="radio" name="question1" value="Dành cho người dùng chuyên nghiệp">
                        <label for="D">D. Dành cho người dùng chuyên nghiệp</label></br>
                    </div>

                    <div class="title">
                        <h3>
                            2) Hãng laptop bạn mong muốn?
                        </h3>
                        <input type="radio" name="question2" value="ACER">
                        <label for="A">A. ACER</label></br>

                        <input type="radio" name="question2" value="APPLE">
                        <label for="B">B. APPLE</label></br>

                        <input type="radio" name="question2" value="ASUS">
                        <label for="C">C. ASUS</label></br>

                        <input type="radio" name="question2" value="DELL">
                        <label for="D">D. DELL</label></br>

                        <input type="radio" name="question2" value="MSI">
                        <label for="K">E. MSI</label></br>

                        <input type="radio" name="question2" value="HP">
                        <label for="F">F. HP</label></br>

                        <input type="radio" name="question2" value="LG">
                        <label for="G">G. LG</label></br>

                        <input type="radio" name="question2" value="LENOVO">
                        <label for="H">H. LENOVO</label></br>

                    </div>

                    <div class="title">
                        <h3>
                            3) Kích thước màn hình mà bạn mong muốn?
                        </h3>
                        <input type="radio" name="question3" value="13.3">
                        <label for="A">A. 13.3inch</label></br>

                        <input type="radio" name="question3" value="14">
                        <label for="B">B. 14.0inch</label></br>

                        <input type="radio" name="question3" value="15.6">
                        <label for="C">C. 15.6inch</label></br>

                        <input type="radio" name="question3" value="16.0">
                        <label for="D">D. 16.0inch</label></br>

                        <input type="radio" name="question3" value="17.3">
                        <label for="E">E. 17.3inch</label></br>
                    </div>

                    <div class="title">
                        <h3>
                            4) Bộ nhớ ram bạn mong muốn?
                        </h3>
                        <input type="radio" name="question4" value="4G">
                        <label for="A">A. Cơ bản đơn thuần</label></br>

                        <input type="radio" name="question4" value="8G">
                        <label for="B">B. Trung bình</label></br>

                        <input type="radio" name="question4" value="16G">
                        <label for="C">C. Cao</label></br>

                    </div>

                    <div class="title">
                        <h3>
                            5) Loại cpu bạn mong muốn?
                        </h3>
                        <input type="radio" name="question5" value="i3">
                        <label for="A">A. intel core i3</label></br>

                        <input type="radio" name="question5" value="i5">
                        <label for="B">B. intel core i5</label></br>

                        <input type="radio" name="question5" value="i7">
                        <label for="C">C. intel core i7</label></br>

                        <input type="radio" name="question5" value="Ryzen">
                        <label for="D">D. AMD Ryzen</label></br>
                    </div>

                    <div class="title">
                        <h3>
                            6) Mức độ dung lưu trữ dữ liệu của bạn?
                        </h3>
                        <input type="radio" name="question6" value="Cơ bản đơn thuần">
                        <label for="A">A. Cơ bản đơn thuần</label></br>

                        <input type="radio" name="question6" value="Trung bình">
                        <label for="B">B. Trung bình</label></br>

                        <input type="radio" name="question6" value="Cao">
                        <label for="C">C. Cao</label></br>

                    </div>

                    <div class="title">
                        <h3>
                            7) Hãng chipset đồ họa bạn muốn sử dụng?
                        </h3>
                        <input type="radio" name="question7" value="Intel">
                        <label for="A">A. Intel tích hợp</label></br>

                        <input type="radio" name="question7" value="Nvidia">
                        <label for="B">B. Nvidia</label></br>

                        <input type="radio" name="question7" value="AMD">
                        <label for="C">C. AMD</label></br>

                    </div>
                    
                    <button class="button" type="submit" name="action" value="Search">
                        Gợi ý
                    </button>

                </form>
            </div>
        </div>
    </body>
</html>
