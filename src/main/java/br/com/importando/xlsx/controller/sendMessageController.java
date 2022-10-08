package br.com.importando.xlsx.controller;

import br.com.importando.xlsx.controller.request.MessageRequest;
import br.com.importando.xlsx.service.MessageWhatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/zap-zap")
@Slf4j
@RequiredArgsConstructor
public class sendMessageController {

    private final MessageWhatsService messageWhatsService;


    @PostMapping
    public void receberMensagem(@RequestBody MessageRequest request) {
        messageWhatsService.sendMessageToContacts(request);
    }
}
