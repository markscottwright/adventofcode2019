package com.markscottwright.adventofcode2019;

import java.util.LinkedList;

public class Queue<T> extends LinkedList<T> {
    public T pop() {
        return poll();
    }

    public void push(T elem) {
        offer(elem);
    }
}
