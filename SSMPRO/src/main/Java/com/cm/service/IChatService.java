package com.cm.service;


import com.cm.entity.ChatMsg;

import java.util.List;

public interface IChatService {

    void addMessage(ChatMsg chatMsg);

    List<ChatMsg> findMsgList(String conSearch);

    void testMsg();
}