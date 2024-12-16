package com.demo.URL.Shortener.repository;

import com.demo.URL.Shortener.entities.ShortnUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortnerRepository extends JpaRepository<ShortnUrlEntity, Long> {

}
