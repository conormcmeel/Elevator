package com.conor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ElevatorController {

    public static final int NUMBER_OF_FLOORS = 5;
    public static final int NUMBER_OF_ELEVATORS = 4;

    public static void main(String[] args) throws InterruptedException, IOException{

        Queue<Request> userRequests = new LinkedList<>();

        startElevators(NUMBER_OF_ELEVATORS, userRequests);

        while(true) {

            Set<Integer> destinations = getDestinations();
            System.out.println();

            Request request = new Request(destinations);
            userRequests.add(request);
            synchronized (userRequests) {
                userRequests.notifyAll();
            }

            Thread.sleep(1000);
        }
    }

    private static void startElevators(int numberPfElevators, Queue<Request> requests) {
        for(int i=1; i<=numberPfElevators; i++) {
            Runnable elevator = new Elevator(requests);
            Thread t = new Thread(elevator);
            t.setName("Elevator-" + i);
            t.start();
        }
    }

    private static Set<Integer> getDestinations() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Set<Integer> destinations = new TreeSet<>();

        System.out.print("\nEnter destinations, followed by a comma: ");

        try {
            String[] input = br.readLine().split(",");
            destinations = parseUserInput(input);
        } catch (NumberFormatException e) {
            logError(e);
        }

        return destinations;
    }

    private static Set<Integer> parseUserInput(String[] input) throws IOException {
        Set<Integer> destinations = new TreeSet<>();
        try {
            for(int i=0; i<input.length; i++) {
                destinations.add(Integer.parseInt(input[i]));
            }
        } catch (NumberFormatException e) {
            logError(e);
        }

        return destinations;
    }

    private static void logError(NumberFormatException e) {
        System.err.println("Unable to parse user input" + e.getStackTrace());
    }
}
