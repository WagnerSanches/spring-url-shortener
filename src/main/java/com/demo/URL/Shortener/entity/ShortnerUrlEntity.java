package com.demo.URL.Shortener.entity;

import com.demo.URL.Shortener.dto.ShortnerUrlDto;
import com.demo.URL.Shortener.util.ShortCodeGeneratorUtil;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@DynamicUpdate
public class ShortnerUrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long shortnerUrlId;
    private String url;

    @Column(unique = true)
    private String shortCode;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Integer accessCount;

    public static ShortnerUrlEntity of(ShortnerUrlDto shortnerUrlDto) {
        return ShortnerUrlEntity.builder()
                .url(shortnerUrlDto.getUrl())
                .shortCode(ShortCodeGeneratorUtil.generate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .accessCount(0)
                .build();
    }

    public void updateUrl(String url) {
        this.url = url;
        this.updatedAt = LocalDateTime.now();
    }

    public void incrementAccessCount() {
        this.accessCount++;
    }

}
