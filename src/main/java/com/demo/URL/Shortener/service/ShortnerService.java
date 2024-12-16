package com.demo.URL.Shortener.service;

import com.demo.URL.Shortener.dtos.ShortnerUrlDto;
import com.demo.URL.Shortener.entities.ShortnerUrlEntity;

import java.util.List;

public interface ShortnerService {

    List<ShortnerUrlEntity> getUrls();
    ShortnerUrlEntity createUrl(ShortnerUrlDto shortnerUrlDto);
    ShortnerUrlEntity findUrl(String shortCode);
    ShortnerUrlEntity changeUrl(String shortCode, String url);
    boolean deleteUrl(String shortCode);
}