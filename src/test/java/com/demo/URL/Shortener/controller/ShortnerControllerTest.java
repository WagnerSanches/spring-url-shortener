package com.demo.URL.Shortener.controller;


import com.demo.URL.Shortener.dtos.ShortnerUrlDto;
import com.demo.URL.Shortener.entities.ShortnerUrlEntity;
import com.demo.URL.Shortener.service.ShortnerService;
import com.demo.URL.Shortener.utils.ShortCodeGeneratorUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
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

    private final String URL = "localhost:8080/teste";
    private ShortnerUrlEntity shortnerUrlEntity;

    @BeforeAll
    public void setup() {
        this.shortnerUrlEntity = ShortnerUrlEntity.builder()
                .shortnerUrlId(1L)
                .url(URL)
                .shortCode(ShortCodeGeneratorUtil.generate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .accessCount(0)
                .build();
    }

    @Order(1)
    @Test
    public void getUrls() throws Exception {
        System.out.println("------------------- ShortnerControllerTest.getUrls() ------------------");
        ShortnerUrlDto shortnerUrlDto = new ShortnerUrlDto(URL);

        given(this.shortnerService.createUrl(any(ShortnerUrlDto.class))).willReturn(this.shortnerUrlEntity);

        ResultActions response = this.mockMvc.perform(post("/url-shortner")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(shortnerUrlDto)));

        response.andDo(print())
                .andExpect(status().isCreated());
    }
}
