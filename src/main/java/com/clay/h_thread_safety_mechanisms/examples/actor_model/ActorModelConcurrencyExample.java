package com.clay.h_thread_safety_mechanisms.examples.actor_model;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * In this implementation, we have the RequestMessage and ResponseMessage classes that represent the messages exchanged between actors.
 *
 * We also have the WorkerActor class that extends AbstractActor and defines the behavior for processing requests.
 *
 * The MasterActor class extends AbstractActor as well and acts as the entry point for processing requests. It creates an instance of the WorkerActor and forwards the received requests to it.
 *
 * Finally, in the main method of the ActorModelConcurrencyExample class, we create an actor system, instantiate the MasterActor, send multiple requests to it
 * 
 */
public class ActorModelConcurrencyExample {
    public static void main(String[] args) {
        // Create an actor system
        ActorSystem system = ActorSystem.create("MySystem");

        // Create an instance of the master actor
        ActorRef master = system.actorOf(Props.create(MasterActor.class));

        // Send multiple requests to the master actor
        master.tell(new RequestMessage("Request 1"), ActorRef.noSender());
        master.tell(new RequestMessage("Request 2"), ActorRef.noSender());
        master.tell(new RequestMessage("Request 3"), ActorRef.noSender());

        // Sleep for a while to allow processing of messages
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Terminate the actor system
        system.terminate();
    }
}