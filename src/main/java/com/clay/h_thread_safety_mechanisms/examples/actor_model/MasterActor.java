package com.clay.h_thread_safety_mechanisms.examples.actor_model;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

class MasterActor extends AbstractActor {
    private final ActorRef worker;

    public MasterActor() {
        // Create an instance of the worker actor
        worker = getContext().actorOf(Props.create(WorkerActor.class));
    }

    @Override
    public Receive createReceive() {
        // Define the behavior for receiving messages
        return receiveBuilder()
                .match(RequestMessage.class, this::processRequest)
                .match(ResponseMessage.class, this::processResponse)
                .build();
    }

    // Process the received request
    private void processRequest(RequestMessage request) {
        // Forward the request to the worker actor for processing
        worker.tell(request, getSelf());
    }

    // Process the received response
    private void processResponse(ResponseMessage response) {
        // Print the result received from the worker actor
        System.out.println("Received response: " + response.getResult());
    }
}