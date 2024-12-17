package com.demo.URL.Shortener.service.impl;

import com.demo.URL.Shortener.dtos.ShortnerUrlDto;
import com.demo.URL.Shortener.entities.ShortnerUrlEntity;
import com.demo.URL.Shortener.repository.ShortnerRepository;
import com.demo.URL.Shortener.service.ShortnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShortnerServiceImpl implements ShortnerService {

    @Autowired
    private ShortnerRepository repository;

    @Override
    public List<ShortnerUrlEntity> getUrls() {
        return repository.findAll();
    }

    @Override
    public Optional<ShortnerUrlEntity> findUrl(String shortCode) {
        Optional<ShortnerUrlEntity> shortnerUrlEntity = Optional.ofNullable(repository.findByShortCode(shortCode));

        shortnerUrlEntity.ifPresent(shortner -> {
            shortner.incrementAccessCount();
            repository.save(shortner);
        });

        return shortnerUrlEntity;
    }

    @Override
    public ShortnerUrlEntity createUrl(ShortnerUrlDto shortnerUrlDto) {
        return repository.save(ShortnerUrlEntity.of(shortnerUrlDto));
    }

    @Override
    public Optional<ShortnerUrlEntity> changeUrl(String shortCode, String url) {
        Optional<ShortnerUrlEntity> shortnerUrlEntity = this.findUrl(shortCode); // optional

        shortnerUrlEntity.ifPresent(shortner -> {
            shortner.updateUrl(url);
            repository.save(shortner);
        });

        return shortnerUrlEntity;
    }

    @Override
    public boolean deleteUrl(String shortCode) {
        Optional<ShortnerUrlEntity> shortnerUrlEntity = this.findUrl(shortCode); // optional

        shortnerUrlEntity.ifPresent(shortner -> {
            repository.delete(shortner);
        });

        return shortnerUrlEntity.isPresent();
    }

}
