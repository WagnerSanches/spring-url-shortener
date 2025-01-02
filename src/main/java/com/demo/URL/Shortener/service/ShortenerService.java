package com.demo.URL.Shortener.service;

import com.demo.URL.Shortener.dto.ShortenerUrlDto;
import com.demo.URL.Shortener.entity.ShortenerUrlEntity;

import java.util.List;
import java.util.Optional;

public interface ShortenerService {

    List<ShortenerUrlEntity> getUrls();
    ShortenerUrlEntity createUrl(ShortenerUrlDto shortenerUrlDto);
    Optional<ShortenerUrlEntity> getUrl(String shortCode);
    Optional<ShortenerUrlEntity> changeUrl(String shortCode, String url);
    boolean deleteUrl(String shortCode);
}