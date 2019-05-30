package com.example.parseserver.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@EnableScheduling
@Component
public class Parser {
    @Autowired  //автоматически создает
    private ParserConfig config;

    @Autowired
    private ApplicationContext context;///получаем все объекты, которые были созданы
@Autowired
private TaskExecutor taskExecutor;

    @Scheduled(fixedRate = 6000)  ///запускался несколько раз
   /// @Bean
    public void start() throws IOException {
        System.out.println("start..." );
        Document document= Jsoup.connect(config.getLink()).get();///считал документ
        Elements elements=document.select(".course-item a");
        for (Element element:elements)
        {
            // получаем из элемента xml по атрибуту href ссылку
            System.out.println("element = " + element.absUrl("href"));
            ParserThread thread=context.getBean(ParserThread.class);
            thread.setLink(element.absUrl("href"));
            taskExecutor.execute(thread);

        }

    }

}
