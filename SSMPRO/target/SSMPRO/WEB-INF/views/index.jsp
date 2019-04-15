<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String path = request.getContextPath();%>
<html>
<link rel="stylesheet" href="<%=path%>/static/css/bootstrap.min.css"
type="text/css">
<script src="<%=path%>/static/js/jquery.min.js"></script>
<script src="<%=path%>/static/js/bootstrap.min.js"
        type="text/javascript"></script>
<body>

<div id="container" style="width:500px">

    <div id="header">
        <h1 id="title">chat-room</h1></div>
    <div class="middle">
        <div id="menu">
            <p id="tou">欢迎来到聊天室</p>
        </div>

        <div class="chatter" id="chatter">
            <p id="msg"></p>
        </div>
    </div>
    <div id="content">
        <textarea class="form-control" rows="3" placeholder="我想说....."
                  id="msgContent">
        </textarea>
    </div>

    <div style="background-color: #F8F8F8;">
        <div id="buttons">
            <button id="butSent" type="button" class="btn btn-default"
                    onclick="getConnection()">连接
            </button>
            <button type="button" class="btn btn-default"
                    onclick="sendMsgClose()">断开
            </button>
            <button type="button" class="btn btn-default" onclick="sendMsg()">
                发送
            </button>
        </div>
    </div>

    <div id="footer">
        Designed by Annie
    </div>
</div>
</body>
<script>

    var wsServer = null;
    wsServer = "ws://" + location.host + "${pageContext.request.contextPath}" + "/websocket";

    // wsServer = "ws://172.18.64.52:8079/websocket";

    var websocket = null;

    //打开链接
    function getConnection() {
        if (websocket == null) {
            websocket = new WebSocket(wsServer);
            websocket.onopen = function (evnt) {
                alert("链接服务器成功!");
            };
            //从后台接受数据的函数
            websocket.onmessage = function (evnt) {
                var onlineUser = $("#onlineUser");
                //将收到的数据转换成对象
                var message = eval("(" + evnt.data + ")");
                //显示在线人数及在线用户
                if (message.msgTyp === "notice") {
                    var htmlOnline;
                    $("#onlineNum").text(message.onlineNum);
                    htmlOnline = "<p> " + message.userName + " </p>";
                    //实时更新在线用户
                    onlineUser.html("");
                    $(onlineUser).append(htmlOnline);
                } else if (message.msgTyp === "msg") {
                    showChat(evnt);
                }
            };
            websocket.onerror = function (evnt) {
                alert("发生错误，与服务器断开了链接!")
            };
            websocket.onclose = function (evnt) {
                alert("与服务器断开了链接!")
            };
        } else {
            alert("连接已存在!")
        }
    }


    function showChat(evnt) {
        var message = eval("(" + evnt.data + ")");
        var msg = $("#msg");
        //msg.html是之前的聊天内容，空一行
        msg.html(msg.html() + "<br/>" + "用户: " + message.user + " 发送时间：" +   message.sendDate + "<br/>" + message.sendContent);
    }


    function sendMsg() {
        var msg = $("#msgContent");
        if (websocket == null) {
            alert("连接未开启!");
            return;
        }
        var message = msg.val();
        //输入完成后，清空输入区
        msg.val("");
        if (message == null || message === "") {
            alert("输入不能为空的哦");
            return;
        }
        //向后台MyWebSocketHandler中的handerMessage发送信息
        //这里将信息转成JSON格式发送
        websocket.send(JSON.stringify({
            message: message,
            type: "chatMsg"
        }));
    }

    /**
     * 关闭连接
     */
    function closeConnection() {
        if (websocket != null) {
            websocket.close();
            websocket = null;
            alert("已经关闭连接")
        } else {
            alert("未开启连接")
        }
    }
</script>
</html>
