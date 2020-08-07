package com.dbses.sample.ratelimiter;

import org.junit.Assert;
import org.junit.Test;

public class RateLimiterTest {

    @Test
    public void execute() throws QueueFullException {
        RateLimiter<String, String> rateLimiter = new RateLimiter<>(10);

        String result = rateLimiter.execute(params -> {
            return params + " world";
        }, "hello");
        Assert.assertEquals("hello world", result);
    }

}