package com.demo.URL.Shortener.service.impl;

import com.demo.URL.Shortener.dto.ShortenerUrlDto;
import com.demo.URL.Shortener.entity.ShortenerUrlEntity;
import com.demo.URL.Shortener.repository.ShortenerRepository;
import com.demo.URL.Shortener.service.ShortenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.demo.URL.Shortener.entity.ShortenerUrlEntity.of;

@Service
public class ShortenerServiceImpl implements ShortenerService {

    Logger logger = LoggerFactory.getLogger(ShortenerServiceImpl.class);

    private final ShortenerRepository repository;

    public ShortenerServiceImpl(ShortenerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ShortenerUrlEntity> getUrls() {
        logger.info("Getting all URL");
        return repository.findAll();
    }

    @Override
    public Optional<ShortenerUrlEntity> getUrl(String shortCode) {
        Optional<ShortenerUrlEntity> shortenerUrlEntity = Optional.ofNullable(repository.findByShortCode(shortCode));

        shortenerUrlEntity.ifPresent(shortener -> {
            shortener.incrementAccessCount();
            repository.save(shortener);
        });

        logger.info("Getting the URL ShortCode: {}", shortCode);
        return shortenerUrlEntity;
    }

    @Override
    public ShortenerUrlEntity createUrl(ShortenerUrlDto shortenerUrlDto) {
        logger.info("Saving a new URL ShortCode");
        return repository.save(of(shortenerUrlDto));
    }

    @Override
    public Optional<ShortenerUrlEntity> changeUrl(String shortCode, String url) {
        Optional<ShortenerUrlEntity> shortenerUrlEntity = this.getUrl(shortCode); // optional

        shortenerUrlEntity.ifPresent((ShortenerUrlEntity shortener) -> {
            shortener.updateUrl(url);
            repository.save(shortener);
        });

        logger.info("Altering the URL ShortCode: {}", shortCode);
        return shortenerUrlEntity;
    }

    @Override
    public boolean deleteUrl(String shortCode) {
        Optional<ShortenerUrlEntity> shortenerUrlEntity = this.getUrl(shortCode); // optional

        shortenerUrlEntity.ifPresent(repository::delete);

        logger.info("Deleting ShortCode: {}", shortCode);
        return shortenerUrlEntity.isPresent();
    }

}
