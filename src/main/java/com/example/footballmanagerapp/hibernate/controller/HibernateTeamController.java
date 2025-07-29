package com.example.footballmanagerapp.hibernate.controller;

import com.example.footballmanagerapp.common.controller.AbstractTeamController;
import com.example.footballmanagerapp.hibernate.service.HibernateTeamService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hibernate/teams")
public class HibernateTeamController extends AbstractTeamController<HibernateTeamService> {
    public HibernateTeamController(HibernateTeamService teamService) {
        super(teamService);
    }
}
