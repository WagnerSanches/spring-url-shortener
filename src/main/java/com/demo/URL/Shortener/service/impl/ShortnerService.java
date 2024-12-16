package com.demo.URL.Shortener.service.impl;

import com.demo.URL.Shortener.entities.ShortnUrlEntity;
import com.demo.URL.Shortener.repository.ShortnerRepository;
import com.demo.URL.Shortener.service.IShortnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortnerService implements IShortnerService {

    @Autowired
    private ShortnerRepository repository;

    public List<ShortnUrlEntity> findAll() {
        return repository.findAll();
    }


}
