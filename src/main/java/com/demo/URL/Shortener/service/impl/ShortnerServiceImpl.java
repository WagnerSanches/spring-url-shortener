package com.demo.URL.Shortener.service.impl;

import com.demo.URL.Shortener.dto.ShortnerUrlDto;
import com.demo.URL.Shortener.entity.ShortnerUrlEntity;
import com.demo.URL.Shortener.repository.ShortnerRepository;
import com.demo.URL.Shortener.service.ShortnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShortnerServiceImpl implements ShortnerService {

    Logger logger = LoggerFactory.getLogger(ShortnerServiceImpl.class);

    @Autowired
    private ShortnerRepository repository;

    @Override
    public List<ShortnerUrlEntity> getUrls() {
        logger.info("Getting all URL");
        return repository.findAll();
    }

    @Override
    public Optional<ShortnerUrlEntity> getUrl(String shortCode) {
        Optional<ShortnerUrlEntity> shortnerUrlEntity = Optional.ofNullable(repository.findByShortCode(shortCode));

        shortnerUrlEntity.ifPresent(shortner -> {
            shortner.incrementAccessCount();
            repository.save(shortner);
        });

        logger.info("Getting the URL ShortCode: {}", shortCode);
        return shortnerUrlEntity;
    }

    @Override
    public ShortnerUrlEntity createUrl(ShortnerUrlDto shortnerUrlDto) {
        logger.info("Saving a new URL ShortCode");
        return repository.save(ShortnerUrlEntity.of(shortnerUrlDto));
    }

    @Override
    public Optional<ShortnerUrlEntity> changeUrl(String shortCode, String url) {
        Optional<ShortnerUrlEntity> shortnerUrlEntity = this.getUrl(shortCode); // optional

        shortnerUrlEntity.ifPresent(shortner -> {
            shortner.updateUrl(url);
            repository.save(shortner);
        });

        logger.info("Altering the URL ShortCode: {}", shortCode);
        return shortnerUrlEntity;
    }

    @Override
    public boolean deleteUrl(String shortCode) {
        Optional<ShortnerUrlEntity> shortnerUrlEntity = this.getUrl(shortCode); // optional

        shortnerUrlEntity.ifPresent(shortner -> {
            repository.delete(shortner);
        });

        logger.info("Deleting ShortCode: {}", shortCode);
        return shortnerUrlEntity.isPresent();
    }

}
