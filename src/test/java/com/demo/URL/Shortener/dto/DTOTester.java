package com.demo.URL.Shortener.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.meanbean.test.BeanTester;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DTOTester {

    @Test
    public void testAllDTOs() {
        BeanTester tester = new BeanTester();
//        tester.testBean(ErrorResponseDTO.class);
        tester.testBean(ShortnerUrlDto.class);
    }
}
