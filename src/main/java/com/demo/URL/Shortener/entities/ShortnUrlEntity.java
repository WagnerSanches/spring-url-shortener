package com.demo.URL.Shortener.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShortnUrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long task_id;
    private String url;
    private String shortCode;
    private Date createdAt;
    private Date updatedAt;

}
