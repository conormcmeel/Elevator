package com.conor;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static com.conor.Direction.*;

public class Elevator implements Callable {

    private int numberOfFloors;
    private int currentFloor;
    private int destinationFloor;
    private Queue<Request> requests;
    private Direction direction = STAND;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    @Override
    public Object call() throws Exception {

        final ReentrantLock lock = this.lock;
        lock.lock();

        try{
            if(isBufferEmpty()) {
                waitOnAvailableElement();
            }

            serviceRequest(requests.poll());
        } finally {
            lock.unlock();
        }

        return null;
    }

    private boolean isBufferEmpty() {
        return 0 == requests.size();
    }

    private void waitOnAvailableElement() throws InterruptedException {
        notEmpty.await();
    }

    private void serviceRequest(Request request) {
        System.out.println("Requested to go from floor " + request.getFrom() + " to floor " + request.getTo());
        System.out.println("=======================================");
        openDoors();
        closeDoors();
        moveFloors(request);
        openDoors();
        closeDoors();
    }

    private void openDoors() {
        System.out.println("Opening Doors");
    }

    private void closeDoors() {
        System.out.println("Closing Doors");
    }

    private void moveFloors(Request request) {
        currentFloor = request.getFrom();
        destinationFloor = request.getTo();
        direction = getDestination();

        System.out.println("Stopping at floor " + destinationFloor);
    }

    private Direction getDestination() {

        if(destinationFloor >= numberOfFloors) {
            return STAND;
        } else if(destinationFloor < 0) {
            return STAND;
        } else if(destinationFloor < currentFloor) {
            return DOWN;
        } else if(destinationFloor > currentFloor) {
            return UP;
        }  else {
            return STAND;
        }
    }

    public Elevator(Queue<Request> requests, int numberOfloors) {
        this.requests = requests;
        this.numberOfFloors = numberOfloors;
    }
}
