package com.example.footballmanagerapp.hibernate.controller;

import com.example.footballmanagerapp.common.controller.AbstractPlayerController;
import com.example.footballmanagerapp.hibernate.service.HibernatePlayerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hibernate/players")
public class HibernatePlayerController extends AbstractPlayerController<HibernatePlayerService> {
    public HibernatePlayerController(HibernatePlayerService service) {
        super(service);
    }
}
