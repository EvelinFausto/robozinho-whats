package br.com.importando.xlsx.controller.request;

import br.com.importando.xlsx.enums.MessageDefault;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {

    @Builder.Default
    private MessageDefault messageDefault = MessageDefault.MINUTINHO2;

    private String message;

    private Boolean isDigite2;
}