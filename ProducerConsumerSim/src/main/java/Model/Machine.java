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

    public Machine(String machineName, long serviceTime){
        this.machineName = machineName;
        //this.serviceTime = ThreadLocalRandom.current().nextInt(800, 3501);
        this.serviceTime = serviceTime;

    }


    public void activate(BufferQueue prevBufferQueue, BufferQueue nextBufferQueue){

        Runnable consumer = () -> {
            while(true){
                synchronized (object){
                    try{
                        while(prevBufferQueue.getProducts().isEmpty()) {
                            System.out.println(this.machineName + " is ready ");
                            object.wait();
                        }
                        product = prevBufferQueue.dequeue();
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
                        if(!prevBufferQueue.getProducts().isEmpty() && !consumed) {
                            object.notify();
                        }
                        while(consumed){
                            Thread.sleep(serviceTime);
                            nextBufferQueue.enqueue(product);
                            System.out.println("Servicing" + " - " + this.machineName + " - "
                                    + product);
                            System.out.println(this.machineName + " " + prevBufferQueue.getProducts());
                            System.out.println(this.machineName + " " + nextBufferQueue.getProducts());
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