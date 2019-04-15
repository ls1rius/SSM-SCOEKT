package com.cm.service.impl;

import com.cm.dao.IChatDao;
import com.cm.entity.ChatMsg;
import com.cm.service.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("chatService")
public class ChatServiceImpl implements IChatService{

    @Resource
    private IChatDao chatDao;

    /*
    根据搜索内容查找聊天记录
     */
    @Override
    public List<ChatMsg> findMsgList(String conSearch) {
        for (ChatMsg chat : chatDao.findMsg(conSearch)){
            System.out.println(chat);
        }
        return  chatDao.findMsg(conSearch);
    }

    @Override
    public void addMessage(ChatMsg chatMsg) {
        chatDao.addMsg(chatMsg);
    }

    public void testMsg(){
        List<ChatMsg> list = chatDao.testMsg();
        for (ChatMsg chat : list){
            System.out.println(chat);
        }
    }
}
