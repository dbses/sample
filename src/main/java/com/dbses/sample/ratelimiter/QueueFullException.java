package com.dbses.sample.ratelimiter;

public class QueueFullException extends Exception {

    public QueueFullException(String msg) {
        super(msg);
    }

}
