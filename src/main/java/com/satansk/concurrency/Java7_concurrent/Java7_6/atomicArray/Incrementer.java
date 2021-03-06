package com.satansk.concurrency.Java7_concurrent.Java7_6.atomicArray;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Author:  satansk
 * Date:    17:14 at 2015/7/18
 * Email:   satansk@hotmail.com
 */
public class Incrementer implements Runnable {
    private AtomicIntegerArray vector;

    public Incrementer(AtomicIntegerArray vector) {
        this.vector = vector;
    }

    @Override
    public void run() {
        for (int i = 0; i < vector.length(); i++) {
            vector.getAndIncrement(i);
        }
    }
}
