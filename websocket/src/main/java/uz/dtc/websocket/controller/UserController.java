package uz.dtc.websocket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import uz.dtc.websocket.model.RegisterMessage;
import uz.dtc.websocket.service.SessionRegistryService;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final SessionRegistryService sessionRegistry;

    /**
     * Client WebSocket'ga ulangandan so'ng o'zini "tanishtirish" uchun shu manzilga xabar yuboradi.
     * Manzil: /app/register
     */
    @MessageMapping("/register")
    public void register(@Payload RegisterMessage message, SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        String userId = message.userId();
        sessionRegistry.registerSession(userId, sessionId);
    }
}