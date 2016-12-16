package com.conor;

import java.util.Set;

public class Request {
    private Set<Integer> destinations;

    public Request(Set<Integer> destinations) {
        this.destinations = destinations;
    }

    public Set<Integer> getDestinations() {
        return destinations;
    }
}
