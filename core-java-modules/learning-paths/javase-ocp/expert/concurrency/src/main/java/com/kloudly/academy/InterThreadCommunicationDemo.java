package com.kloudly.academy;

import java.util.LinkedList;
import java.util.Queue;

/**
 * InterThreadCommunicationDemo -demonstrates all major inter-thread
 * communication tools: sleep(), yield(), join(), wait(), notify(), notifyAll().
 *
 * <p>This class accompanies the article "Inter-Thread Communication in Java"
 * published at https://nkamphoa.com.
 *
 * <p>Each private method corresponds to a code snippet discussed in the article.
 * Run main() to observe all mechanisms in action.
 */
public class InterThreadCommunicationDemo {

    // -------------------------------------------------------------------------
    // Static nested classes -one per wait/notify section
    // -------------------------------------------------------------------------

    /**
     * Section 5 -the simplest possible wait/notify handshake.
     * One thread waits for data; another sets it and notifies.
     */
    static class DataHolder {
        private String  data  = null;
        private boolean ready = false;

        public synchronized void setData(String value) {
            data  = value;
            ready = true;
            notify(); // wake the one thread waiting on this object's monitor
        }

        public synchronized String waitForData() throws InterruptedException {
            while (!ready) { // re-check the condition after every wakeup
                wait();      // atomically releases the lock and suspends
            }
            return data;
        }
    }

    /**
     * Sections 6 and 8 -bounded producer-consumer queue.
     * Producer waits when full; consumer waits when empty.
     */
    static class BoundedQueue {
        private final Queue<Integer> queue = new LinkedList<>();
        private final int capacity;

        BoundedQueue(int capacity) { this.capacity = capacity; }

        public synchronized void produce(int item) throws InterruptedException {
            while (queue.size() == capacity) {
                wait(); // full -release lock and wait for a consumer
            }
            queue.add(item);
            System.out.printf("[%s] Produced: %d  (size=%d)%n",
                    Thread.currentThread().getName(), item, queue.size());
            notifyAll(); // wake waiting consumers (and other waiting producers)
        }

        public synchronized int consume() throws InterruptedException {
            while (queue.isEmpty()) {
                wait(); // empty -release lock and wait for a producer
            }
            int item = queue.poll();
            System.out.printf("[%s] Consumed: %d  (size=%d)%n",
                    Thread.currentThread().getName(), item, queue.size());
            notifyAll(); // wake waiting producers (and other waiting consumers)
            return item;
        }
    }

    /**
     * Section 7 -single-slot message box for demonstrating the while rule.
     * With notifyAll(), multiple receivers wake up; only one finds a message;
     * the while loop sends the others back to wait() without error.
     */
    static class MessageBox {
        private String message = null;

        public synchronized void post(String msg) throws InterruptedException {
            while (message != null) { // occupied -wait until the current message is taken
                wait();
            }
            message = msg;
            System.out.println("Posted:   " + msg);
            notifyAll(); // wake all waiting receivers
        }

        public synchronized String take() throws InterruptedException {
            while (message == null) { // no message yet -keep waiting (never use 'if' here)
                wait();
            }
            String taken = message;
            message = null;
            System.out.println("Taken:    " + taken);
            notifyAll(); // wake any waiting posters
            return taken;
        }
    }

    /**
     * Section 9 -best-practices channel.
     * Dedicated private lock object; notifyAll(); while loops on both sides.
     */
    static class SafeChannel {
        private final Object lock = new Object(); // private -no external code can interfere
        private String  message   = null;
        private boolean available = false;

        public void send(String msg) throws InterruptedException {
            synchronized (lock) {
                while (available) {
                    lock.wait(); // wait until the previous message is consumed
                }
                message   = msg;
                available = true;
                System.out.println("Sent:     " + msg);
                lock.notifyAll();
            }
        }

        public String receive() throws InterruptedException {
            synchronized (lock) {
                while (!available) {
                    lock.wait(); // wait until a message is available
                }
                String received = message;
                message   = null;
                available = false;
                lock.notifyAll();
                return received;
            }
        }
    }

    // -------------------------------------------------------------------------
    // Entry point
    // -------------------------------------------------------------------------

    public static void main(String[] args) throws InterruptedException {
        InterThreadCommunicationDemo demo = new InterThreadCommunicationDemo();

        System.out.println("=== Section 1: Thread.sleep() ===");
        demo.demonstrateSleep();

        System.out.println("\n=== Section 2: Thread.yield() ===");
        demo.demonstrateYield();

        System.out.println("\n=== Section 3: Thread.join() ===");
        demo.demonstrateJoin();

        System.out.println("\n=== Section 4: Thread.join(timeout) ===");
        demo.demonstrateJoinWithTimeout();

        System.out.println("\n=== Section 5: Basic wait() and notify() ===");
        demo.demonstrateBasicWaitNotify();

        System.out.println("\n=== Section 6: Bounded Producer-Consumer Queue ===");
        demo.demonstrateProducerConsumer();

        System.out.println("\n=== Section 7: The 'while' Rule ===");
        demo.demonstrateWhileRule();

        System.out.println("\n=== Section 8: notify() vs notifyAll() ===");
        demo.demonstrateNotifyAllVsNotify();

        System.out.println("\n=== Section 9: Best Practices ===");
        demo.demonstrateBestPractices();
    }

    // -------------------------------------------------------------------------
    // Article snippet 1 -Thread.sleep()
    // -------------------------------------------------------------------------

    private void demonstrateSleep() throws InterruptedException {
        Thread worker = new Thread(() -> {
            System.out.println("Worker: starting task");
            try {
                Thread.sleep(200); // pause for 200 ms -does NOT release any held lock
            } catch (InterruptedException e) {
                // Restore interrupt status; do not swallow this exception
                Thread.currentThread().interrupt();
                System.out.println("Worker: sleep was interrupted");
                return;
            }
            System.out.println("Worker: task complete");
        }, "sleeping-worker");

        worker.start();
        worker.join(); // wait for the worker to finish before continuing
    }

    // -------------------------------------------------------------------------
    // Article snippet 2 -Thread.yield()
    // -------------------------------------------------------------------------

    private void demonstrateYield() throws InterruptedException {
        Runnable task = () -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println(Thread.currentThread().getName() + " -step " + i);
                Thread.yield(); // hint to the scheduler: willing to give up CPU time now
            }
        };

        Thread t1 = new Thread(task, "thread-A");
        Thread t2 = new Thread(task, "thread-B");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    // -------------------------------------------------------------------------
    // Article snippet 3 -Thread.join()
    // -------------------------------------------------------------------------

    private void demonstrateJoin() throws InterruptedException {
        Thread dataLoader = new Thread(() -> {
            try {
                System.out.println("Loader: fetching data...");
                Thread.sleep(150); // simulate I/O or a slow computation
                System.out.println("Loader: data ready");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "data-loader");

        dataLoader.start();
        System.out.println("Main:   waiting for data loader...");
        dataLoader.join(); // main thread blocks here until dataLoader reaches TERMINATED
        System.out.println("Main:   loader finished -proceeding with loaded data");
    }

    // -------------------------------------------------------------------------
    // Article snippet 4 -Thread.join(timeout)
    // -------------------------------------------------------------------------

    private void demonstrateJoinWithTimeout() throws InterruptedException {
        Thread slowTask = new Thread(() -> {
            try {
                Thread.sleep(2000); // deliberately slow task
                System.out.println("Slow task: done");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "slow-task");

        slowTask.start();
        slowTask.join(300); // wait at most 300 ms

        if (slowTask.isAlive()) {
            System.out.println("Main: slow task still running after timeout -state: "
                    + slowTask.getState());
            slowTask.interrupt(); // cancel it so the JVM can exit cleanly
        }
        slowTask.join(); // wait for the interrupted thread to finish
    }

    // -------------------------------------------------------------------------
    // Article snippet 5 -basic wait() / notify() handshake
    // -------------------------------------------------------------------------

    private void demonstrateBasicWaitNotify() throws InterruptedException {
        DataHolder holder = new DataHolder();

        Thread consumer = new Thread(() -> {
            try {
                String value = holder.waitForData();
                System.out.println("Received: " + value);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "consumer-thread");

        Thread producer = new Thread(() -> {
            try {
                Thread.sleep(50); // simulate preparation work
                holder.setData("Hello from producer");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "producer-thread");

        consumer.start(); // consumer starts first so it is already waiting
        producer.start();
        consumer.join();
        producer.join();
    }

    // -------------------------------------------------------------------------
    // Article snippet 6 -bounded producer-consumer queue (1 producer, 1 consumer)
    // -------------------------------------------------------------------------

    private void demonstrateProducerConsumer() throws InterruptedException {
        BoundedQueue queue = new BoundedQueue(3);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 6; i++) { queue.produce(i); Thread.sleep(20); }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "producer");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 6; i++) { queue.consume(); Thread.sleep(50); }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "consumer");

        producer.start(); consumer.start();
        producer.join();  consumer.join();
    }

    // -------------------------------------------------------------------------
    // Article snippet 7 -MessageBox demonstrating the 'while' rule
    // -------------------------------------------------------------------------

    private void demonstrateWhileRule() throws InterruptedException {
        MessageBox box = new MessageBox();

        // Two receivers compete; notifyAll() wakes both after each post;
        // the while loop correctly sends the empty-handed receiver back to wait()
        Thread receiver1 = new Thread(() -> {
            try { System.out.println("[receiver-1] Got: " + box.take()); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "receiver-1");

        Thread receiver2 = new Thread(() -> {
            try { System.out.println("[receiver-2] Got: " + box.take()); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "receiver-2");

        Thread sender = new Thread(() -> {
            try {
                Thread.sleep(30);
                box.post("Message A"); // wakes both receivers; only one takes it
                box.post("Message B"); // the other loops back in while() and takes this one
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "sender");

        receiver1.start(); receiver2.start(); sender.start();
        receiver1.join();  receiver2.join();  sender.join();
    }

    // -------------------------------------------------------------------------
    // Article snippet 8 -notifyAll() with 2 producers and 2 consumers
    // -------------------------------------------------------------------------

    private void demonstrateNotifyAllVsNotify() throws InterruptedException {
        BoundedQueue queue = new BoundedQueue(2);
        int ITEMS = 6;

        Thread p1 = new Thread(() -> {
            try { for (int i = 1; i <= ITEMS / 2; i++) { queue.produce(i); Thread.sleep(10); } }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "producer-1");

        Thread p2 = new Thread(() -> {
            try { for (int i = ITEMS / 2 + 1; i <= ITEMS; i++) { queue.produce(i); Thread.sleep(10); } }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "producer-2");

        Thread c1 = new Thread(() -> {
            try { for (int i = 0; i < ITEMS / 2; i++) { queue.consume(); Thread.sleep(40); } }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "consumer-1");

        Thread c2 = new Thread(() -> {
            try { for (int i = 0; i < ITEMS / 2; i++) { queue.consume(); Thread.sleep(40); } }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "consumer-2");

        p1.start(); p2.start(); c1.start(); c2.start();
        p1.join();  p2.join();  c1.join();  c2.join();
    }

    // -------------------------------------------------------------------------
    // Article snippet 9 -best practices: private lock + notifyAll() + while loops
    // -------------------------------------------------------------------------

    private void demonstrateBestPractices() throws InterruptedException {
        SafeChannel channel = new SafeChannel();

        Thread sender = new Thread(() -> {
            try {
                channel.send("Message 1");
                channel.send("Message 2");
                channel.send("Message 3");
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "sender");

        Thread receiver = new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    System.out.println("Received: " + channel.receive());
                    Thread.sleep(30);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "receiver");

        receiver.start(); sender.start();
        receiver.join();  sender.join();
    }
}
