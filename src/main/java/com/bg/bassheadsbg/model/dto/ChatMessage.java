package com.bg.bassheadsbg.model.dto;

import com.bg.bassheadsbg.model.enums.MessageType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    private String content;
    private String sender;
    private MessageType messageType;

}
