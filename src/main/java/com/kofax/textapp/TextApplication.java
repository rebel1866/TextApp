package com.kofax.textapp;

import com.kofax.textapp.controller.TextAppController;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {"application.properties"})
public class TextApplication {
    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(TextApplication.class).web(WebApplicationType.NONE)
                .headless(false).bannerMode(Banner.Mode.OFF).run(args);
        TextAppController textAppController = context.getBean(TextAppController.class);
        textAppController.initApp();
    }
}
