## multi-threaded simulation of a traffic signal intersection. 
The intersection has four lanes, one for each direction of traffic, and the traffic signal cycles through green lights for each direction. The simulation involves multiple threads, including a thread for each lane of traffic, a thread for the traffic signal, and a thread for monitoring the intersection.

### New state: 
When a thread for a new lane of traffic is created, it starts in the new state. For example, when a car approaches the intersection from the north, a new thread is created to represent the northbound lane.

### Runnable state: 
When a thread is ready to execute, it moves to the runnable state. For example, when the traffic signal turns green for the northbound lane, the northbound thread moves to the runnable state and begins moving cars through the intersection.

### Blocked state: 
When a thread is waiting for a resource, it moves to the blocked state. For example, if the northbound thread encounters a red light, it moves to the blocked state and waits for the signal to turn green.

### Terminated state: 
When a thread completes its task, it moves to the terminated state. For example, when all the cars have cleared the northbound lane, the northbound thread completes its task and moves to the terminated state.