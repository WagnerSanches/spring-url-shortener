package com.demo.URL.Shortener.service;

import com.demo.URL.Shortener.dtos.ShortnerUrlDto;
import com.demo.URL.Shortener.entities.ShortnerUrlEntity;

import java.util.List;
import java.util.Optional;

public interface ShortnerService {

    List<ShortnerUrlEntity> getUrls();
    ShortnerUrlEntity createUrl(ShortnerUrlDto shortnerUrlDto);
    Optional<ShortnerUrlEntity> getUrl(String shortCode);
    Optional<ShortnerUrlEntity> changeUrl(String shortCode, String url);
    boolean deleteUrl(String shortCode);
}