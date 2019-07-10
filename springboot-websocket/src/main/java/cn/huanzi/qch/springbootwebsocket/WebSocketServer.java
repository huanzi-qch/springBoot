package cn.huanzi.qch.springbootwebsocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket服务
 */
@RestController
@RequestMapping("/websocket")
@ServerEndpoint(value = "/websocket/{username}", configurator = MyEndpointConfigure.class)
public class WebSocketServer {

    /**
     * 在线人数
     */
    private static int onlineCount = 0;

    /**
     * 在线用户的Map集合，key：用户名，value：Session对象
     */
    private static Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    /**
     * 注入其他类（换成自己想注入的对象）
     */
//    @Autowired
//    private UserService userService;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        //在webSocketMap新增上线用户
        sessionMap.put(username, session);

        //在线人数加加
        WebSocketServer.onlineCount++;

        //通知除了自己之外的所有人
        sendOnlineCount(session, "{'type':'onlineCount','onlineCount':" + WebSocketServer.onlineCount + ",username:'" + username + "'}");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        //下线用户名
        String logoutUserName = "";

        //从webSocketMap删除下线用户
        for (Entry<String, Session> entry : sessionMap.entrySet()) {
            if (entry.getValue() == session) {
                sessionMap.remove(entry.getKey());
                logoutUserName = entry.getKey();
                break;
            }
        }
        //在线人数减减
        WebSocketServer.onlineCount--;

        //通知除了自己之外的所有人
        sendOnlineCount(session, "{'type':'onlineCount','onlineCount':" + WebSocketServer.onlineCount + ",username:'" + logoutUserName + "'}");
    }

    /**
     * 服务器接收到客户端消息时调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            //JSON字符串转 HashMap
            HashMap hashMap = new ObjectMapper().readValue(message, HashMap.class);

            //消息类型
            String type = (String) hashMap.get("type");

            //来源用户
            Map srcUser = (Map) hashMap.get("srcUser");

            //目标用户
            Map tarUser = (Map) hashMap.get("tarUser");

            //如果点击的是自己，那就是群聊
            if (srcUser.get("username").equals(tarUser.get("username"))) {
                //群聊
                groupChat(session,hashMap);
            } else {
                //私聊
                privateChat(session, tarUser, hashMap);
            }

            //后期要做消息持久化

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 通知除了自己之外的所有人
     */
    private void sendOnlineCount(Session session, String message) {
        for (Entry<String, Session> entry : sessionMap.entrySet()) {
            try {
                if (entry.getValue() != session) {
                    entry.getValue().getBasicRemote().sendText(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 私聊
     */
    private void privateChat(Session session, Map tarUser, HashMap hashMap) throws IOException {
        //获取目标用户的session
        Session tarUserSession = sessionMap.get(tarUser.get("username"));

        //如果不在线则发送“对方不在线”回来源用户
        if (tarUserSession == null) {
            session.getBasicRemote().sendText("{\"type\":\"0\",\"message\":\"对方不在线\"}");
        } else {
            hashMap.put("type", "1");
            tarUserSession.getBasicRemote().sendText(new ObjectMapper().writeValueAsString(hashMap));
        }
    }

    /**
     * 群聊
     */
    private void groupChat(Session session,HashMap hashMap) throws IOException {
        for (Entry<String, Session> entry : sessionMap.entrySet()) {
            //自己就不用再发送消息了
            if (entry.getValue() != session) {
                hashMap.put("type", "2");
                entry.getValue().getBasicRemote().sendText(new ObjectMapper().writeValueAsString(hashMap));
            }
        }
    }

    /**
     * 登录
     */
    @RequestMapping("/login/{username}")
    public ModelAndView login(HttpServletRequest request, @PathVariable String username) {
        return new ModelAndView("socketChart.html", "username", username);
    }

    /**
     * 登出
     */
    @RequestMapping("/logout/{username}")
    public String loginOut(HttpServletRequest request, @PathVariable String username) {
        return "退出成功！";
    }

    /**
     * 获取在线用户
     */
    @RequestMapping("/getOnlineList")
    private List<String> getOnlineList(String username) {
        List<String> list = new ArrayList<String>();
        //遍历webSocketMap
        for (Entry<String, Session> entry : WebSocketServer.sessionMap.entrySet()) {
            if (!entry.getKey().equals(username)) {
                list.add(entry.getKey());
            }
        }
        return list;
    }

}
