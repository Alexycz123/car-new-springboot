package com.ycz.controller;

import com.ycz.config.ApplicationHelper;
import com.ycz.domain.SysUserMsg;
import com.ycz.service.SysUserMsgService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
@ServerEndpoint("/websocket/{id}")
public class WebSocketController {

    private final SysUserMsgService sysUserMsgService=
            (SysUserMsgService) ApplicationHelper.getBean("sysUserMsgServiceImpl2");


    private static int onlineCount = 0;
    private static ConcurrentHashMap<String, WebSocketController> webSocketSet = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private static Logger log = LogManager.getLogger(WebSocketController.class);
    private String id = "";

//    public static List<SysUserMsg> sysUserMsgList=new ArrayList<>();
    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(@PathParam(value = "id") String id, Session session) {
        this.session = session;
        this.id = id;//接收到发送消息的人员编号
        webSocketSet.put(id, this);     //加入set中
        addOnlineCount();           //在线数加1
        log.info("用户"+id+"加入！当前在线人数为" + getOnlineCount());

        for (Map.Entry<String, WebSocketController> entry : webSocketSet.entrySet()) {
            System.out.println(entry.getKey().toString());
        }

        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());

    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * 再存入数据库
     * */
    @OnMessage
    public  void  onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);
        //可以自己约定字符串内容，比如 内容|0 表示信息群发，内容|X 表示信息发给id为X的用户
        String sendMessage = message.split("[|]")[0];
        String sendUserId = message.split("[|]")[1];

        /*
        先弄个静态类 List 类
        存入 发送者和接受者
        static
         */
        SysUserMsg sysUserMsg=new SysUserMsg();
        sysUserMsg.setSendUserId(Integer.parseInt(this.id));
        sysUserMsg.setReceiveUserId(Integer.parseInt(sendUserId));//收到的id
        sysUserMsg.setSendMsg(sendMessage);
        sysUserMsg.setSendTime(new Date());
        System.out.println(sysUserMsg);
//        sysUserMsgList.add(sysUserMsg);
        sysUserMsgService.insert(sysUserMsg);


        try {
            if(sendUserId.equals("0"))
                sendtoAll(sendMessage);
            else
                sendtoUser(sendMessage,sendUserId);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 错误也要将数据存入数据库
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 发送信息给指定ID用户，如果用户不在线则返回不在线信息给自己
     * @param message
     * @param sendUserId
     * @throws IOException
     */
    public void sendtoUser(String message,String sendUserId) throws IOException {

        if (webSocketSet.get(sendUserId) != null) {
            if(!id.equals(sendUserId))
                webSocketSet.get(sendUserId).sendMessage( "用户" + id +
                        "发来消息："+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +
                         "<br>"+ message );
            else
                webSocketSet.get(sendUserId).sendMessage(message);
        } else {
            //如果用户不在线则返回不在线信息给自己
            sendtoUser("当前用户不在线",id);
        }
    }

    /**
     * 发送信息给所有人
     * @param message
     * @throws IOException
     */
    public void sendtoAll(String message) throws IOException {
        for (String key : webSocketSet.keySet()) {
            try {
                webSocketSet.get(key).sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketController.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketController.onlineCount--;
    }


}
