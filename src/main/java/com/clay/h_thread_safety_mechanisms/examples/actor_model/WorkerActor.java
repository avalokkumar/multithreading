package com.clay.h_thread_safety_mechanisms.examples.actor_model;

import akka.actor.AbstractActor;

class WorkerActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        // Define the behavior for receiving messages
        return receiveBuilder()
                .match(RequestMessage.class, this::processRequest)
                .build();
    }

    // Process the received request
    private void processRequest(RequestMessage request) {
        // Perform some computation or business logic based on the request data
        String requestData = request.getData();
        String result = "Processed: " + requestData;

        // Create a response message with the result
        ResponseMessage response = new ResponseMessage(result);

        // Send the response back to the sender
        getSender().tell(response, getSelf());
    }
}