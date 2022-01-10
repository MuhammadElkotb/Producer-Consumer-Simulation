package Model;


import Controllers.EventManager;
import Controllers.MachineObserver;
import Controllers.Network;
import javassist.expr.NewArray;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Machine {
    private Product product;
    private final Object object = new Object();
    private String machineName;
    private boolean consumed = false;
    private long serviceTime;
    private ArrayList<BufferQueue> prevBufferQueues;
    private ArrayList<BufferQueue> nextBufferQueues;
    private EventManager manager;

    public Machine(String machineName) {
        this.machineName = machineName;
        this.serviceTime = ThreadLocalRandom.current().nextInt(1000, 20000);
        this.manager = EventManager.getInstance();
        manager.addListener(this.machineName,new MachineObserver(this.machineName));
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getServiceTime() {
        return this.serviceTime;
    }

    public void setServiceTime(long serviceTime) {
        this.serviceTime = serviceTime;
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

    public void activate(BufferQueue prevBufferQueue, BufferQueue nextBufferQueue, Network network){

            Runnable consumer = () -> {

                while (true) {
                    synchronized (object) {

                        try {
                            while (prevBufferQueue.getProducts().isEmpty()) {

                                //System.out.println(this.machineName + " is ready ");
                                manager.notify(this.machineName, network);
                                object.wait();
                            }

                            this.setProduct(prevBufferQueue.dequeue(network));
                            manager.notify(this.machineName, network);
                            System.out.println(this.product);
                            consumed = true;

                            object.wait();
                            object.notifyAll();

                        } catch (Exception e) {
                            e.printStackTrace();
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
                            while (consumed && product != null) {
                                Thread.sleep(serviceTime);
                                object.notifyAll();
                                nextBufferQueue.enqueue(product, network);
                                consumed = false;
                                object.wait();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
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