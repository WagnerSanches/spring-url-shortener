package com.demo.URL.Shortener.service;

import com.demo.URL.Shortener.entities.ShortnUrlEntity;

import java.util.List;

public interface IShortnerService {

    List<ShortnUrlEntity> findAll();

}