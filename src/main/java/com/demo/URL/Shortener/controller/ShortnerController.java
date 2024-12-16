package com.demo.URL.Shortener.controller;

import com.demo.URL.Shortener.entities.ShortnUrlEntity;
import com.demo.URL.Shortener.service.IShortnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// seguranca
// docker compose

@RestController
public class ShortnerController {

    @Autowired
    private IShortnerService service;

    @GetMapping("")
    public ResponseEntity<List<ShortnUrlEntity>> get() {
        return ResponseEntity.ok(service.findAll());
    }
}
