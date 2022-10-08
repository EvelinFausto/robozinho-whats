package br.com.importando.xlsx.service;

import br.com.importando.xlsx.controller.request.MessageRequest;
import br.com.importando.xlsx.dto.ContatoDto;
import br.com.importando.xlsx.enums.MessageDefault;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageWhatsService {

    @Autowired
    private WebDriver webDriver;
    private final FileImportService fileImportService;

    private void messageWhats(String newPhone, String message) {
        try {
            //Botão que add novo contato
            WebElement addNewPhone = addNewphone();
            addNewPhone.click();
            Thread.sleep(5000);
            //escreve o novo contato
            WebElement textBoxNewPhone = findTextBoxNewPhone();
            textBoxNewPhone.click();
            textBoxNewPhone.sendKeys(newPhone);
            textBoxNewPhone.sendKeys(Keys.RETURN);
            Thread.sleep(10000);

            boolean contactEquals = contactEquals(newPhone);
            if (!contactEquals) {
                Actions action = new Actions(webDriver);
                action.sendKeys(Keys.ESCAPE).build().perform();
                log.error("Não foi possível enviar a mensagem para o contato, telefone invalido {}", newPhone);
                Thread.sleep(5000);
                return;
            }

            Thread.sleep(20000);

            //mensagemTextoDoContato
            WebElement caixaMensagem = findCaixaTexto();
            caixaMensagem.sendKeys(message);
            caixaMensagem.sendKeys(Keys.RETURN);
            Thread.sleep(5000);
        } catch (Exception e) {
            log.error("Não foi possível enviar a mensagem para o contato {}", newPhone, e);
            Actions esc = new Actions(webDriver);
            esc.sendKeys(Keys.ESCAPE).build().perform();
        }
    }

    private WebElement addNewphone() {
        String xPathNewContat = "//*[@id=\"startNonContactChat\"]/div/span";
        return webDriver.findElement(By.xpath(xPathNewContat));
    }

    private WebElement findTextBoxNewPhone() {
        String xPathAddNewContat = "//div[6]/div[1]/div/input";
        return webDriver.findElement(By.xpath(xPathAddNewContat));
    }

    private WebElement findCaixaTexto() {
        String xPathCaixaTexto = "//*[@id=\"main\"]/footer//*[@role='textbox']";
        return webDriver.findElement(By.xpath(xPathCaixaTexto));
    }

    private WebElement findContato(String newPhone) {
        String xPathContato = "//*[@id=\"main\"]/header/div[2]/div/div/span";
        return webDriver.findElement(By.xpath(xPathContato));
    }

    private boolean contactEquals(String newPhone) {
        WebElement contato = findContato(newPhone);
        String text = contato.getText().replaceAll("\\p{Punct}", "");
        String result = text.replaceAll("\\s+", "");
        char celularOrFixo = result.charAt(4);
        if (celularOrFixo != 3) {
            int length = result.length();
            if (length == 12) {
                result = addCharToStringUsingSubString(result, "9", 4);
            }
        }
        newPhone = "55" + newPhone;
        if (result.equals(newPhone)) {
            return true;
        }
        return false;
    }


    public void sendMessageToContacts(MessageRequest request) {
        List<ContatoDto> contatoDtos = fileImportService.readWorksheet();
        String message = null;
        for (ContatoDto contact : contatoDtos) {
            if (request.getMessage() == null) {
                message = createMessageDefault(request.getMessageDefault(), contact.getName());
            } else {
                message = createMessage(request.getMessage(), contact.getName());
            }
            if (message != null)
                if(request.getIsDigite2()) {
                    message = message + MessageDefault.DIGITE2.getMessage() + contact.getName();
                }
                messageWhats(contact.getPhone(), message);
        }
    }

    private String saudacao() {
        int hour = LocalDateTime.now().getHour();
        if (hour > 2 && hour <= 12) {
            return "Bom dia ";
        }
        if (hour <= 18 && hour > 12) {
            return "Boa tarde ";
        }
        if (18 < hour && hour <= 23 || (0 < hour && hour <= 2)) {
            return "Boa noite ";
        }
        return "Olá ";
    }

    private String createMessageDefault(MessageDefault messageDefault, String nameContact) {
        String saudacao = saudacao();
        switch (messageDefault) {
            case MINUTINHO:
                return "Olá " + nameContact + MessageDefault.MINUTINHO.getMessage();
            case MINUTINHO2:
                return saudacao + nameContact + MessageDefault.MINUTINHO2.getMessage();
            case LIMITE_EMPRESTIMO:
                return saudacao + MessageDefault.LIMITE_EMPRESTIMO.getMessage();
            case OPORTUNIDADE_EMPRESTIMO:
                return saudacao + nameContact + MessageDefault.OPORTUNIDADE_EMPRESTIMO.getMessage();
            case CARTAO:
                return saudacao + nameContact + MessageDefault.CARTAO.getMessage();
            case RESPOSTA:
                return MessageDefault.RESPOSTA.getMessage();
            case DIGITE2:
                return MessageDefault.DIGITE2.getMessage() + nameContact;
            default:
                return saudacao + MessageDefault.MINUTINHO.getMessage();
        }
    }

    private String createMessage(String message, String nameContact) {
        String saudacao = saudacao();
        return saudacao + nameContact + ", " + message;
    }

    private static String addCharToStringUsingSubString(String str, String c, int pos) {
        return str.substring(0, pos) + c + str.substring(pos);
    }
}