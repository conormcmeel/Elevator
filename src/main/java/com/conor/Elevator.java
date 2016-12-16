package com.conor;

import java.util.Queue;

public class Elevator implements Runnable {

    private Queue<Request> requestBank;
    private int oldFloor;
    private int currentFloor;

    public Elevator(Queue<Request> requests) {
        this.requestBank = requests;
    }

    @Override
    public void run() {

        while (true) {
            synchronized (requestBank) {

                while (requestBank.isEmpty()) {
                    try {
                        requestBank.wait();
                    } catch (InterruptedException e) {
                        System.out.print(Thread.currentThread().getName() + "interrupted");
                    }
                }

                Request request = requestBank.poll();

                for(Integer destination : request.getDestinations()){
                    oldFloor = currentFloor;
                    currentFloor = destination;
                    System.out.println(Thread.currentThread().getName() + " went from floor " + oldFloor + " to floor " + currentFloor);
                }
            }
        }
    }
}
