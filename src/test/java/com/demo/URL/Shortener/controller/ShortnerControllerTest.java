package com.demo.URL.Shortener.controller;


import com.demo.URL.Shortener.dtos.ShortnerUrlDto;
import com.demo.URL.Shortener.entities.ShortnerUrlEntity;
import com.demo.URL.Shortener.service.ShortnerService;
import com.demo.URL.Shortener.utils.ShortCodeGeneratorUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import javax.swing.text.html.Option;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@WebMvcTest(ShortnerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShortnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ShortnerService shortnerService;

    @Autowired
    private ObjectMapper objectMapper;

    private String URL;
    private String shortCode;
    private ShortnerUrlEntity shortnerUrlEntity;

    @BeforeAll
    public void setup() {
        URL = "localhost:8080/teste";
        shortCode = "abc1";

        this.shortnerUrlEntity = ShortnerUrlEntity.builder()
                .shortnerUrlId(1L)
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
        List<ShortnerUrlEntity> shortnerUrlEntities = List.of(this.shortnerUrlEntity);

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
        ShortnerUrlDto shortnerUrlDto = new ShortnerUrlDto(URL);

        given(this.shortnerService.createUrl(any(ShortnerUrlDto.class))).willReturn(this.shortnerUrlEntity);

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
        given(this.shortnerService.getUrl(this.shortCode)).willReturn(Optional.of(this.shortnerUrlEntity));

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
        this.shortnerUrlEntity.updateUrl(newUrl);
        ShortnerUrlDto shortnerUrlDto = new ShortnerUrlDto(newUrl);

        given(this.shortnerService.changeUrl(shortCode, newUrl)).willReturn(Optional.of(this.shortnerUrlEntity));
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
