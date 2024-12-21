package com.demo.URL.Shortener.util;


import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UrlShortenerApplicationTest {

    @Test
    public void generate() {
        Assert.notNull(ShortCodeGeneratorUtil.generate(), "it cannot be null!");
        assertEquals(6, ShortCodeGeneratorUtil.generate().length());
    }

}
