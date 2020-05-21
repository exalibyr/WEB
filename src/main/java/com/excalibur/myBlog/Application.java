package com.excalibur.myBlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    //TODO:2)рус транслейт для сайта (пока отложим, там пиздец какой-то)
    //TODO:3)убрать ненужные кнопки Back
    //TODO:4)доработать загрузку файлов (легко заддосить из консоли аяксом)
    //TODO:5)доделать эксепшены в кастомных запросах
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
