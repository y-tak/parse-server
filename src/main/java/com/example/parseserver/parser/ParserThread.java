package com.example.parseserver.parser;

import com.example.parseserver.entity.Course;
import com.example.parseserver.repositorium.CourseRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope("prototype")/// создает несколько объектов
public class ParserThread implements Runnable {
    @Autowired
    private CourseRepository repository;
    private  String link;

    public CourseRepository getRepository() {
        return repository;
    }

    public void setRepository(CourseRepository repository) {
        this.repository = repository;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public void run() {
        Document document= null;
        try {
            document = Jsoup.connect(link).get();
            System.out.println(Thread.currentThread().getName()+" "+ document.title());
            Course course=new Course();
            Element title=document.select(".header__h").first();
            course.setTitle(title.text());


            Elements dot_title=document.select("dot__title");
            for (Element element:dot_title)
            {
                if ("Длительность".equals(element.text()))
                {course.setDuration(element.nextElementSibling().text());}
            }
            System.out.println("course = " + course);
            repository.save(course);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
