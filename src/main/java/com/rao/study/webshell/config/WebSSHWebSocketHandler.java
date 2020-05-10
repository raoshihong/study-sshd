package com.rao.study.webshell.config;

import com.rao.study.webshell.constant.ConstantPool;
import com.rao.study.webshell.service.WebSSHService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

/**
 *
 */
@Component
public class WebSSHWebSocketHandler implements WebSocketHandler {

    private Logger logger = LoggerFactory.getLogger(WebSSHWebSocketHandler.class);

    @Autowired
    private WebSSHService webSSHService;

    /**
     * 客户端与服务端建立websocket连接后的回调
     * @param webSocketSession
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        logger.info("afterConnectionEstablished");
        webSSHService.initConnection(webSocketSession);
    }

    /**
     * 收到消息的回调
     * @param webSocketSession
     * @param webSocketMessage
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        logger.info("handleMessage");
        if (webSocketMessage instanceof TextMessage) {
            logger.info("用户:{},发送命令:{}", webSocketSession.getAttributes().get(ConstantPool.USER_UUID_KEY), webSocketMessage.toString());
            //调用service接收消息
            webSSHService.recvHandle(((TextMessage) webSocketMessage).getPayload(), webSocketSession);
        } else if (webSocketMessage instanceof BinaryMessage) {

        } else if (webSocketMessage instanceof PongMessage) {

        } else {
            System.out.println("Unexpected WebSocket message type: " + webSocketMessage);
        }
    }

    /**
     * 传输失败的回调
     * @param webSocketSession
     * @param throwable
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        logger.info("handleTransportError");
    }

    /**
     * 关闭连接后的回调
     * @param webSocketSession
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        logger.info("afterConnectionClosed");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
