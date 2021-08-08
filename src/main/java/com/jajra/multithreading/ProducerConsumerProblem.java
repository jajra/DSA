package src.main.java.com.jajra.multithreading;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ProducerConsumerProblem {

    public static void main(String[] args) throws InterruptedException {

        BlockingDeque<Object> queue = new LinkedBlockingDeque<>(10);

        int consumer = 4;
        int producer = 3;

        for (int i = 0; i < consumer; i++) {
            new Thread(new Consumer(queue)).start();
        }

        for (int i = 0; i < producer; i++) {
            new Thread(new Producer(queue)).start();
        }
        Thread.sleep(1000);
    }
}


class Producer implements Runnable {

    BlockingQueue<Object> queue;

    Producer(BlockingQueue blockingQueue) {
        this.queue = blockingQueue;
    }

    @Override
    public void run() {

        try {

            while (true) {
                Object newObject = getResource();
                queue.put(newObject);
                System.out.println("Produced resource - Queue size now = " + queue.size());
            }

        } catch (InterruptedException ex) {
            System.out.println("Producer INTERRUPTED");

        }
    }

    private Object getResource() {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            System.out.println("Producer Read INTERRUPTED");
        }
        return new Object();
    }
}


class Consumer implements Runnable {

    BlockingQueue<Object> queue;

    Consumer(BlockingQueue blockingQueue) {
        this.queue = blockingQueue;
    }

    @Override
    public void run() {

        try {

            while (true) {
                Object takeObject = queue.take();
                //System.out.println("takeObject "+takeObject);
                System.out.println("Consumed resource - Queue size now = " + queue.size());
                take(takeObject);
            }

        } catch (Exception ex) {
            System.out.println("CONSUMER INTERRUPTED");
        }

    }

    private void take(Object takeObject) {

        try {
            Thread.sleep(5000);
        } catch (Exception ex) {
            System.out.println("CONSUMER read INTERRUPTED");
        }
        System.out.println("Consumed Object : " + takeObject);
    }
}