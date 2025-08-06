package uz.dtc.websocket.model;

// Bu klass client WebSocket'ga ulangandan so'ng o'zini "ro'yxatdan o'tkazish"
// uchun yuboradigan xabar strukturasini belgilaydi.
public record RegisterMessage(String userId) {
}