package com.bg.bassheadsbg.model.entity.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatMessage {
    private String content;
    private String sender;
    private String originalContent;
    private Long id;
}