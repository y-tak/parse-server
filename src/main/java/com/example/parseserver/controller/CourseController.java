package com.example.parseserver.controller;

import com.example.parseserver.entity.Course;
import com.example.parseserver.repositorium.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;


    @GetMapping("/courses") //получить инфу
    Iterable<Course> findAll()
    {
        return courseRepository.findAll();
    }

    //@RequestMapping(value=) //запрос инфу
    //@PutMapping("/") обновить инфу
    ///@DeleteMapping("/") //удалить инфу

}
