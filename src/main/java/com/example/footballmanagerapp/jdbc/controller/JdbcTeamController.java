package com.example.footballmanagerapp.jdbc.controller;

import com.example.footballmanagerapp.common.controller.AbstractTeamController;
import com.example.footballmanagerapp.jdbc.service.JdbcTeamService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jdbc/teams")
public class JdbcTeamController extends AbstractTeamController<JdbcTeamService> {
    public JdbcTeamController(JdbcTeamService teamService) {
        super(teamService);
    }
}
