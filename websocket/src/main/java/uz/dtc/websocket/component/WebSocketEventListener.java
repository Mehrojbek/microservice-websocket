package uz.dtc.websocket.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import uz.dtc.websocket.service.SessionRegistryService;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SessionRegistryService sessionRegistry;

    /**
     * Bu metod WebSocket ulanishi uzilganda avtomatik ishga tushadi.
     * U sessionRegistry'dan foydalanuvchining sessiyasini o'chirib tashlaydi.
     */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        log.info("WebSocket session disconnected: {}", sessionId);
        sessionRegistry.unregisterSession(sessionId);
    }
}