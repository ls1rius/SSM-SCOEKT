package com.cm.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cm.entity.ChatMsg;
import com.cm.entity.User;
import com.cm.service.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class MyWebSocketHandler implements WebSocketHandler {

    private final static List<WebSocketSession> USERS = new ArrayList<>();
    private final static List<User> USER_ONLINE = new ArrayList<>();

    /*
     *在链接创建完后就在前端显示在线用户
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        USERS.add(webSocketSession);
        //每次有新的连接，就加入到user集合中
        User user = (User) webSocketSession.getAttributes().get("ws_user");
        USER_ONLINE.add(user);

        List<String> usernameList = new ArrayList<>();
        for (User u : USER_ONLINE) {
            String username = u.getUsername();
            usernameList.add(username);
        }

        //String类的format()方法用于创建格式化的字符串以及连接多个字符串对象。
        //这里传到前端的应该是JSON格式
        String messageFormat = "{onlineNum:\"%d\",username:\"%s\" , msgTyp " +
                ":\"%s\"}";
        String msg = String.format(messageFormat, USERS.size(), usernameList,
                "notice");

        TextMessage testMsg = new TextMessage(msg + "");
        //确保每个用户信息都能同步到
        for (WebSocketSession wss : USERS) {
            wss.sendMessage(testMsg);
        }
    }

    /**
     * 客户端发送服务器的消息时的处理函数，在这里收到消息之后可以分发消息
     */
    @Autowired
    private IChatService chatService;

    @Override
    public void handleMessage(WebSocketSession webSocketSession,
                              WebSocketMessage<?> webSocketMessage) throws Exception {

        String messageFormat = null;

        //发送消息的时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String sentMsgDate = dateFormat.format(new Date());

        User user = (User) webSocketSession.getAttributes().get("ws_user");

        String msgContent = webSocketMessage.getPayload() + "";

        JSONObject chat = JSON.parseObject(msgContent);
        //消息的内容
        String msgJSON = chat.get("message").toString();
        //消息的样式
        String msgJSONType = chat.get("type").toString();

        String chatMsg = "chatMsg";

        if (msgJSONType.equals(chatMsg)) {
            //将消息保存到数据库
            ChatMsg chatMessage = new ChatMsg(user.getId(), sentMsgDate,
                    msgJSON);
            chatService.addMessage(chatMessage);

            messageFormat = "{user:\"%s\",sendDate:\"%s\" ," +
                    "sendContent:\"%s\" , msgTyp :\"%s\"}";
            String message = String.format(messageFormat, user.getUsername(),
                    sentMsgDate, msgJSON , "msg");
            TextMessage toMsg = new TextMessage(message + "");
            //遍历所有的用户，发信息，这个要注意哦，要不然不能做到多人同时聊天
            for (WebSocketSession wss : USERS) {
                wss.sendMessage(toMsg);
            }
        }
    }


    @Override
    public void handleTransportError(WebSocketSession webSocketSession,
                                     Throwable throwable) throws Exception {
        USERS.remove(webSocketSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession,
                                      CloseStatus closeStatus) throws Exception {
        User userRemove = (User) webSocketSession.getAttributes().get(
                "ws_user");
        USER_ONLINE.remove(userRemove);
        USERS.remove(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
