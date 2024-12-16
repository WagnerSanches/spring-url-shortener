package com.demo.URL.Shortener.service.impl;

import com.demo.URL.Shortener.dtos.ShortnerUrlDto;
import com.demo.URL.Shortener.entities.ShortnerUrlEntity;
import com.demo.URL.Shortener.repository.ShortnerRepository;
import com.demo.URL.Shortener.service.ShortnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortnerServiceImpl implements ShortnerService {

    @Autowired
    private ShortnerRepository repository;

    @Override
    public List<ShortnerUrlEntity> getUrls() {
        return repository.findAll();
    }

    @Override
    public ShortnerUrlEntity findUrl(String shortCode) {
        ShortnerUrlEntity shortnerUrlEntity = repository.findByShortCode(shortCode);

        if(shortnerUrlEntity == null) {
            return null;
        }


        shortnerUrlEntity.incrementAccessCount();
        repository.save(shortnerUrlEntity);
        return shortnerUrlEntity;
    }

    @Override
    public ShortnerUrlEntity createUrl(ShortnerUrlDto shortnerUrlDto) {
        return repository.save(ShortnerUrlEntity.of(shortnerUrlDto));
    }

    @Override
    public ShortnerUrlEntity changeUrl(String shortCode, String url) {
        ShortnerUrlEntity shortnerUrlEntity = this.findUrl(shortCode); // optional

        if(shortnerUrlEntity == null) {
            return null;
        }

        shortnerUrlEntity.updateUrl(url);
        return repository.save(shortnerUrlEntity);
    }

    @Override
    public boolean deleteUrl(String shortCode) {
        ShortnerUrlEntity shortnerUrlEntity = this.findUrl(shortCode); // optional

        if(shortnerUrlEntity == null) {
            return false;
        }

        repository.delete(shortnerUrlEntity);

        return true;
    }

}
