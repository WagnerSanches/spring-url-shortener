package com.demo.URL.Shortener.controller;


import com.demo.URL.Shortener.dto.ShortenerUrlDto;
import com.demo.URL.Shortener.entity.ShortenerUrlEntity;
import com.demo.URL.Shortener.service.ShortenerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@WebMvcTest(ShortenerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShortnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ShortenerService shortnerService;

    @Autowired
    private ObjectMapper objectMapper;

    private String URL;
    private String shortCode;
    private ShortenerUrlEntity shortenerUrlEntity;

    @BeforeAll
    public void setup() {
        URL = "localhost:8080/teste";
        shortCode = "abc1";

        this.shortenerUrlEntity = ShortenerUrlEntity.builder()
                .shortenerUrlId(1L)
                .url(URL)
                .shortCode(shortCode)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .accessCount(0)
                .build();
    }

    @Order(1)
    @Test
    public void getUrls() throws Exception {
        System.out.println("------------------- ShortnerControllerTest.getUrls() ------------------");
        List<ShortenerUrlEntity> shortnerUrlEntities = List.of(this.shortenerUrlEntity);

        given(this.shortnerService.getUrls()).willReturn(shortnerUrlEntities);

        ResultActions response = this.mockMvc.perform(get("/url-shortner")
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(shortnerUrlEntities.size())));

    }

    @Order(2)
    @Test
    public void createUrl() throws Exception {
        System.out.println("------------------- ShortnerControllerTest.createUrl() ------------------");
        ShortenerUrlDto shortnerUrlDto = new ShortenerUrlDto(URL);

        given(this.shortnerService.createUrl(any(ShortenerUrlDto.class))).willReturn(this.shortenerUrlEntity);

        ResultActions response = this.mockMvc.perform(post("/url-shortner")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(shortnerUrlDto)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.url", is(URL)));
    }

    @Order(3)
    @Test
    public void findUrl() throws Exception {
        System.out.println("------------------- ShortnerControllerTest.findUrl() ------------------");
        given(this.shortnerService.getUrl(this.shortCode)).willReturn(Optional.of(this.shortenerUrlEntity));

        ResultActions response = this.mockMvc.perform(get("/url-shortner/{shortCode}", this.shortCode)
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortCode", is(shortCode)));
    }

    @Order(4)
    @Test
    public void changeUrl() throws Exception {
        System.out.println("------------------- ShortnerControllerTest.changeUrl() ------------------");
        String newUrl = "localhost:3333";
        this.shortenerUrlEntity.updateUrl(newUrl);
        ShortenerUrlDto shortnerUrlDto = new ShortenerUrlDto(newUrl);

        given(this.shortnerService.changeUrl(shortCode, newUrl)).willReturn(Optional.of(this.shortenerUrlEntity));
        ResultActions response = this.mockMvc.perform(patch("/url-shortner/{shortCode}", this.shortCode)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(shortnerUrlDto)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url", is(newUrl)));
    }

    @Order(5)
    @Test
    public void deleteUrl() throws Exception {
        System.out.println("------------------- ShortnerControllerTest.deleteUrl() ------------------");
        given(this.shortnerService.deleteUrl(this.shortCode)).willReturn(true);

        ResultActions response = this.mockMvc.perform(delete("/url-shortner/{shortCode}", this.shortCode));

        response.andExpect(status().isNoContent())
                .andDo(print());
    }

}
