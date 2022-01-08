package Model;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class Machine {
    private Product product;
    private final Object object = new Object();
    private String machineName;
    private boolean consumed = false;
    private long serviceTime;
    private ArrayList<BufferQueue> prevBufferQueues;
    private ArrayList<BufferQueue> nextBufferQueues;

    public Machine(String machineName) {
        this.machineName = machineName;
        this.serviceTime = ThreadLocalRandom.current().nextInt(800, 3501);
    }

    public String getMachineName() {
        return machineName;
    }

    public void setNextBufferQueues(ArrayList<BufferQueue> nextBufferQueues) {
        this.nextBufferQueues = nextBufferQueues;
    }

    public void setPrevBufferQueues(ArrayList<BufferQueue> prevBufferQueues) {
        this.prevBufferQueues = prevBufferQueues;
    }

    public void activate(BufferQueue prevBufferQueue, BufferQueue nextBufferQueue){



            Runnable consumer = () -> {

                while (true) {
                    synchronized (object) {

                        try {
                            while (prevBufferQueue.getProducts().isEmpty()) {
                                System.out.println(this.machineName + " is ready ");
                                object.wait();
                            }

                            product = prevBufferQueue.dequeue();
                            consumed = true;

                            object.wait();
                            object.notifyAll();

                        } catch (Exception e) {
                            System.out.println("CONSUMER");
                            System.out.println(e);
                        }

                    }
                }
            };




            Runnable producer = () -> {
                while (true) {
                    synchronized (object) {

                        try {
                            if (!prevBufferQueue.getProducts().isEmpty() && !consumed) {
                                object.notifyAll();
                            }
                            while (consumed) {
                                Thread.sleep(serviceTime);
                                object.notifyAll();
                                nextBufferQueue.enqueue(product);
                                System.out.println("Servicing" + " - " + this.machineName + " - " + this.serviceTime + " - "
                                        + product);
                                System.out.println(this.machineName + " Prev " + prevBufferQueue.getProducts());
                                System.out.println(this.machineName + " Next " + nextBufferQueue.getProducts());
                                consumed = false;
                                object.wait();
                            }
                        } catch (Exception e) {
                            System.out.println("PRODUCER");
                            System.out.println(e);
                        }


                    }
                }

            };


            Thread produceThread = new Thread(producer);
            Thread consumeThread = new Thread(consumer);

//            this.prevBufferQueue = prevBufferQueue;
//            this.nextBufferQueue = nextBufferQueue;
            produceThread.start();
            consumeThread.start();

    }

}