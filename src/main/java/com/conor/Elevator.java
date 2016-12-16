package com.conor;

import java.util.List;

public class Elevator implements Runnable {

    private List<Request> requestBank;
    private int previousFloor;
    private int currentFloor;

    public Elevator(List<Request> requests) {
        this.requestBank = requests;
    }

    @Override
    public void run() {

        while (true) {

            Request request = getNextRequest();

            serviceRequest(request);
        }
    }

    private Request getNextRequest() {

        synchronized (requestBank) {

            while (requestBank.isEmpty()) {
                try {
                    requestBank.wait();
                } catch (InterruptedException e) {
                    logError(Thread.currentThread().getName());
                }
            }
            return requestBank.get(0);
        }
    }

    private void serviceRequest(Request request) {

        for(Integer destination : request.getDestinations()){

            //doors are closed and i'm moving, but i'll check for intermediate floors just in case
            Request intermediateRequest = null;
            if(!requestBank.isEmpty()) {
                    //search request bank for intermediate requests
            }

            previousFloor = currentFloor;
            currentFloor = destination;
            System.out.println(Thread.currentThread().getName() + " went from floor " + previousFloor + " to floor " + currentFloor);
        }
    }

    private static void logError(String name) {
        System.out.print(name + "interrupted");
    }
}

