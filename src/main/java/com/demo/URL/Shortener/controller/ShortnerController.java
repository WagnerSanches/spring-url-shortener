package com.demo.URL.Shortener.controller;

import com.demo.URL.Shortener.dto.ShortnerUrlDto;
import com.demo.URL.Shortener.entity.ShortnerUrlEntity;
import com.demo.URL.Shortener.exception.URLNotFoundException;
import com.demo.URL.Shortener.service.ShortnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

// docker compose

@RestController
@RequestMapping("url-shortner")
public class ShortnerController {

    @Autowired
    private ShortnerService service;

    @GetMapping()
    public ResponseEntity<List<ShortnerUrlEntity>> getUrls() {
        return ResponseEntity.ok(service.getUrls());
    }

    @PostMapping()
    public ResponseEntity<ShortnerUrlEntity> createUrl(@RequestBody ShortnerUrlDto shortnerUrlDto) throws URISyntaxException {
        ShortnerUrlEntity shortnerUrlEntity = service.createUrl(shortnerUrlDto);

        return ResponseEntity
                .created(new URI("/url-shortner/" + shortnerUrlEntity.getShortCode()))
                .body(shortnerUrlEntity);
    }

    @GetMapping("{shortCode}")
    public ResponseEntity<ShortnerUrlEntity> getUrl(@PathVariable String shortCode) {
        return service.getUrl(shortCode)
                .map(ResponseEntity::ok)
                .orElseThrow(URLNotFoundException::new);
    }

    @PatchMapping("{shortCode}")
    public ResponseEntity<ShortnerUrlEntity> changeUrl(
            @PathVariable String shortCode,
            @RequestBody ShortnerUrlDto shortnerUrlDto) {

        return service.changeUrl(shortCode, shortnerUrlDto.getUrl())
                .map(ResponseEntity::ok)
                .orElseThrow(URLNotFoundException::new);
    }

    @DeleteMapping("{shortCode}")
    public ResponseEntity<String> deleteUrl(@PathVariable String shortCode) {

        if(!service.deleteUrl(shortCode))
            throw new URLNotFoundException();

        return ResponseEntity.noContent().build();
    }

}
