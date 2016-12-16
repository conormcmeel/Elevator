package com.conor;

import java.util.Queue;

public class Elevator implements Runnable {

    private Queue<Request> userRequests;
    private int oldFloor;
    private int currentFloor;

    public Elevator(Queue<Request> requests) {
        this.userRequests = requests;
    }

    @Override
    public void run() {

        while (true) {
            synchronized (userRequests) {

                while (userRequests.isEmpty()) {
                    try {
                        userRequests.wait();
                    } catch (InterruptedException e) {
                        System.out.print(Thread.currentThread().getName() + "interrupted");
                    }
                }

                Request request = userRequests.poll();

                for(Integer destination : request.getDestinations()){
                    oldFloor = currentFloor;
                    currentFloor = destination;
                    System.out.println(Thread.currentThread().getName() + " went from floor " + oldFloor + " to floor " + currentFloor);
                }
            }
        }
    }
}
