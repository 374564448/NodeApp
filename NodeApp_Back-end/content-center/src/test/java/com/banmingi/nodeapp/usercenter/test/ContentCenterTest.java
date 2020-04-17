package com.banmingi.nodeapp.usercenter.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * @auther 半命i 2020/3/22
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ContentCenterTest {


    @Test
    public void SentinelTest() throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        for (int i = 0; i < 10000; i++) {
            String object =
                    restTemplate.getForObject("http://localhost:8020/actuator/sentinel", String.class);
            Thread.sleep(500);
        }
    }
}
