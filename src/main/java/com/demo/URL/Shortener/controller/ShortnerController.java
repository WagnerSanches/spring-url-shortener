package com.demo.URL.Shortener.controller;

import com.demo.URL.Shortener.dtos.ShortnerUrlDto;
import com.demo.URL.Shortener.entities.ShortnerUrlEntity;
import com.demo.URL.Shortener.service.ShortnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

// seguranca (handler)
// docker compose
// optional

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
    public ResponseEntity<ShortnerUrlEntity> findUrl(@PathVariable String shortCode) {
        return ResponseEntity.ok(service.findUrl(shortCode));
    }

    @PatchMapping("{shortCode}")
    public ResponseEntity<ShortnerUrlEntity> changeUrl(
            @PathVariable String shortCode,
            @RequestBody ShortnerUrlDto shortnerUrlDto) {

        return ResponseEntity.ok(service.changeUrl(shortCode, shortnerUrlDto.getUrl()));
    }

    @DeleteMapping("{shortCode}")
    public ResponseEntity<String> deleteUrl(@PathVariable String shortCode) {
        if(service.deleteUrl(shortCode)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
