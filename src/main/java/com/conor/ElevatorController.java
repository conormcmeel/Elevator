package com.conor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ElevatorController {

    private static Queue<Request> requests = new LinkedList<>();
    private static int numberOfFloors = 4;

    public static void main(String[] args) throws InterruptedException {

        requests.add(new Request(0, 4));
        requests.add(new Request(3, 2));

        Callable<Request> liftA = new Elevator(requests, numberOfFloors);
        Callable<Request> liftB = new Elevator(requests, numberOfFloors);
        List<Callable<Request>> lifts = new ArrayList<>();
        lifts.add(liftA);
       // lifts.add(liftB);

        ExecutorService service = Executors.newFixedThreadPool(10);
        service.invokeAll(lifts);
    }
}
