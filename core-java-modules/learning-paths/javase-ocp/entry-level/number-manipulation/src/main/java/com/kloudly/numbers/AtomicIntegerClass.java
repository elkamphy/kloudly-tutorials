package com.kloudly.numbers;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;

public class AtomicIntegerClass {

    public static void main(String[] args) throws InterruptedException {
        createAtomicInteger();
        getAndSet();
        incrementAndDecrement();
        addAndGet();
        compareAndSet();
        accumulateAndGet();
        realWorldApplication();
    }

    static void accumulateAndGet(){
        AtomicInteger balance = new AtomicInteger(1000);
        IntBinaryOperator function = (IntBinaryOperator) Integer::sum;
        int newBalance = balance.accumulateAndGet(500,function);//balance = 1500, newBalance = 1500;
    }
    static void createAtomicInteger() {
        AtomicInteger withoutInitialValue = new AtomicInteger();//Initial value is 0
        AtomicInteger withInitialValue = new AtomicInteger(10);//Initial value is 10
    }

    static void compareAndSet(){
        AtomicInteger counter = new AtomicInteger(10);//counter = 10
        boolean check1 = counter.compareAndSet(5,20);//check1 = false, counter = 10
        boolean check2 = counter.compareAndSet(10,20);//check2 = true, counter = 20

        AtomicInteger stock = new AtomicInteger(50); // Initial stock
        int currentStock = stock.get();
        //Some code
        if (stock.compareAndSet(currentStock, currentStock - 1)) {
            System.out.println(Thread.currentThread().getName() + ": Stock updated successfully! Remaining stock: " + stock.get());
            //More code
        }else{
            System.out.println("The stock was updated by a different thread");
            //Adapt the code
        }
    }

    static void addAndGet(){
        AtomicInteger pageViewCount = new AtomicInteger(0);//pageViewCount = 0
        int value = pageViewCount.getAndAdd(10);//pageViewCount = 10, value=0
        value = pageViewCount.addAndGet(5);//pageViewCount = 15, value=15
    }

    static void incrementAndDecrement(){
        AtomicInteger counter1 = new AtomicInteger(10);//counter1 = 10
        AtomicInteger counter2 = new AtomicInteger(10);//counter2 = 10
        AtomicInteger counter3 = new AtomicInteger(10);//counter3 = 10
        AtomicInteger counter4 = new AtomicInteger(10);//counter4 = 10
        int value1 = counter1.incrementAndGet();//counter1 = 11, value1=11
        int value2 = counter2.getAndIncrement();//counter2 = 11, value2=10
        int value3 = counter3.decrementAndGet();//counter3 = 9 value3=9
        int value4 = counter4.getAndDecrement();//counter4 = 9, value4=10
    }
    static void getAndSet() {
        AtomicInteger counter = new AtomicInteger(10);//counter = 10
        int value = counter.get();//counter = 10, value=10
        counter.set(15);//counter = 15, value=10
        value = counter.get();//counter = 15, value=15
        value = counter.getAndSet(20);//counter = 20, value=15
        value = counter.get();//counter = 20, value=20
    }

    static void realWorldApplication() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);

        // Create 20 threads to increment the counter
        Thread[] threads = new Thread[20];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new CustomCounter(counter));
            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Final Counter Value: " + counter.get());//20 000
    }

}

class CustomCounter implements Runnable{
    private final AtomicInteger counter;

    public CustomCounter(AtomicInteger counter){
        this.counter = counter;
    }
    @Override
    public void run() {
        for (int j = 0; j < 1000; j++) {
            counter.incrementAndGet(); // Atomic increment
        }
    }
}
