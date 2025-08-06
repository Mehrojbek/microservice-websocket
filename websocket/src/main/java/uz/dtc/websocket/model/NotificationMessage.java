package uz.dtc.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

// Bu klass REST controller orqali kelgan va WebSocket orqali yuboriladigan
// xabar strukturasini belgilaydi.
// `record` - Java 16+ da kelgan, o'zgaruvchan bo'lmagan (immutable) DTO yaratishning qulay usuli.
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationMessage implements Serializable {
    private String userId;
    private String content;
}