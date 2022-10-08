package br.com.importando.xlsx;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public WebDriver webDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\evelin.fausto\\Documents\\chromedriver_win33\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(false);
        ChromeDriver webDriver = new ChromeDriver(options);

        webDriver.get("https://web.whatsapp.com/");
        webDriver.executeScript("window.open('https://chrome.google.com/webstore/detail/wa-web-plus-for-whatsapp/ekcgkejcjdcmonfpmnljobemcbpnkamh/related?hl=pt-BR')");

        return webDriver;
    }
}