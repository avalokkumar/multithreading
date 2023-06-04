package com.clay.h_thread_safety_mechanisms.examples.actor_model;

class ResponseMessage {
    private final String result;

    public ResponseMessage(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
