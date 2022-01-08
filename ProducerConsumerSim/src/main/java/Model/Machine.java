package Model;


import java.text.DateFormat;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class Machine {
    private Product product;
    private final Object object = new Object();
    private String machineName;
    private boolean consumed = false;
    private long serviceTime;
    private BufferQueue prevBufferQueue;
    private BufferQueue nextBufferQueue;

    public Machine(String machineName){
        this.machineName = machineName;
        this.serviceTime = ThreadLocalRandom.current().nextInt(800, 3501);


    }


    public void activate(BufferQueue prevBufferQueue, BufferQueue nextBufferQueue){

        Runnable consumer = () -> {
            while(true){
                synchronized (object){
                    try{
                        while(this.prevBufferQueue.getProducts().isEmpty()) {
                            System.out.println(this.machineName + " is ready ");
                            object.wait();
                        }
                        product = this.prevBufferQueue.dequeue();
                        consumed = true;
                        object.wait();
                        object.notify();
                    }
                    catch (Exception e){
                        System.out.println(e);
                    }
                }
            }
        };


        Runnable producer = () -> {
            while(true){
                synchronized (object){
                    try{
                        if(!this.prevBufferQueue.getProducts().isEmpty() && !consumed) {
                            object.notify();
                        }
                        while(consumed){
                            Thread.sleep(serviceTime);
                            this.nextBufferQueue.enqueue(product);
                            System.out.println("Servicing" + " - " + this.machineName + " - "
                                    + product);
                            System.out.println(this.machineName + " " + this.prevBufferQueue.getProducts());
                            System.out.println(this.machineName + " " + this.nextBufferQueue.getProducts());
                            object.notify();
                            consumed = false;
                            object.wait();
                        }
                    }
                    catch (Exception e){
                        System.out.println(e);
                    }
                }
            }
        };


        Thread consumeThread = new Thread(consumer);
        Thread produceThread = new Thread(producer);

        consumeThread.start();
        produceThread.start();
    }
}