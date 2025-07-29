package com.example.footballmanagerapp.jdbc.controller;

import com.example.footballmanagerapp.common.controller.AbstractPlayerController;
import com.example.footballmanagerapp.jdbc.service.JdbcPlayerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jdbc/players")
public class JdbcPlayerController extends AbstractPlayerController<JdbcPlayerService> {
    public JdbcPlayerController(JdbcPlayerService service) {
        super(service);
    }
}
