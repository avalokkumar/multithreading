package com.clay.h_thread_safety_mechanisms.examples.actor_model;

class RequestMessage {
    private final String data;

    public RequestMessage(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}