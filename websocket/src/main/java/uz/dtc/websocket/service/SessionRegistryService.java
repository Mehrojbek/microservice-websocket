package uz.dtc.websocket.service;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bu service qaysi foydalanuvchi (userId) qaysi WebSocket sessiyasiga (sessionId)
 * ulanganligini xotirada saqlab turadi. Bu bizga kerakli foydalanuvchiga 
 * xabar yuborish uchun uning sessiyasini topish imkonini beradi.
 */
@Service
public class SessionRegistryService {

    // Kengaytiriladigan, bir nechta thread'lar bir vaqtda ishlaganda ham
    // xavfsiz ishlaydigan Map.
    // Key: userId (masalan, "user-123"), Value: sessionId (masalan, "a3b1cde2")
    private final Map<String, String> userSessionMap = new ConcurrentHashMap<>();
    
    // Teskari bog'lanish uchun Map. Bu ulanish uzilganda foydalanuvchini topishni osonlashtiradi.
    // Key: sessionId, Value: userId
    private final Map<String, String> sessionUserMap = new ConcurrentHashMap<>();

    /**
     * Yangi sessiyani ro'yxatdan o'tkazadi.
     * @param userId Foydalanuvchi identifikatori
     * @param sessionId WebSocket sessiyasi identifikatori
     */
    public void registerSession(String userId, String sessionId) {
        // Eski ulanish bo'lsa, o'chirib tashlaymiz (bir foydalanuvchi bir vaqtda bitta sessiyada)
        // Agar bir nechta sessiyaga ruxsat berish kerak bo'lsa, bu logikani o'zgartirish kerak.
        String oldSessionId = userSessionMap.put(userId, sessionId);
        if (oldSessionId != null) {
            sessionUserMap.remove(oldSessionId);
        }
        
        sessionUserMap.put(sessionId, userId);
        System.out.printf("[SessionRegistry] User '%s' registered with session '%s'%n", userId, sessionId);
    }

    /**
     * Sessiya uzilganda ro'yxatdan o'chiradi.
     * @param sessionId Ulanishi uzilgan sessiya identifikatori
     */
    public void unregisterSession(String sessionId) {
        String userId = sessionUserMap.remove(sessionId);
        if (userId != null) {
            // Faqat agar sessionId mos kelsa o'chiramiz (eski sessiya bilan adashmaslik uchun)
            userSessionMap.remove(userId, sessionId);
            System.out.printf("[SessionRegistry] Session '%s' for user '%s' unregistered%n", sessionId, userId);
        }
    }

    /**
     * Foydalanuvchi ID'si bo'yicha uning aktiv sessiya ID'sini qaytaradi.
     * @param userId Foydalanuvchi identifikatori
     * @return Sessiya identifikatori yoki null (agar foydalanuvchi online bo'lmasa).
     */
    public String getSessionId(String userId) {
        return userSessionMap.get(userId);
    }
}