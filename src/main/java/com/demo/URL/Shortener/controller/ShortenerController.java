package com.demo.URL.Shortener.controller;

import com.demo.URL.Shortener.dto.ShortenerUrlDto;
import com.demo.URL.Shortener.entity.ShortenerUrlEntity;
import com.demo.URL.Shortener.exception.URLNotFoundException;
import com.demo.URL.Shortener.service.ShortenerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("url-shortener")
public class ShortenerController {

    private final ShortenerService service;

    public ShortenerController(ShortenerService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<ShortenerUrlEntity>> getUrls() {
        return ResponseEntity.ok(service.getUrls());
    }

    @PostMapping()
    public ResponseEntity<ShortenerUrlEntity> createUrl(@RequestBody ShortenerUrlDto shortenerUrlDto) throws URISyntaxException {
        ShortenerUrlEntity shortenerUrlEntity = service.createUrl(shortenerUrlDto);

        return ResponseEntity.created(new URI("/url-shortener/" + shortenerUrlEntity.getShortCode())).body(shortenerUrlEntity);
    }

    @GetMapping("{shortCode}")
    public ResponseEntity<ShortenerUrlEntity> getUrl(@PathVariable String shortCode) {
        return service.getUrl(shortCode).map(ResponseEntity::ok).orElseThrow(URLNotFoundException::new);
    }

    @PatchMapping("{shortCode}")
    public ResponseEntity<ShortenerUrlEntity> changeUrl(@PathVariable String shortCode, @RequestBody ShortenerUrlDto shortenerUrlDto) {
        return service.changeUrl(shortCode, shortenerUrlDto.getUrl()).map(ResponseEntity::ok).orElseThrow(URLNotFoundException::new);
    }

    @DeleteMapping("{shortCode}")
    public ResponseEntity<String> deleteUrl(@PathVariable String shortCode) {

        if (!service.deleteUrl(shortCode)) throw new URLNotFoundException();

        return ResponseEntity.noContent().build();
    }

}
