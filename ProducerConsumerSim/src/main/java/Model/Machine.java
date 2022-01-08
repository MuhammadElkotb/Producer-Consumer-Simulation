package Model;


import java.util.concurrent.ThreadLocalRandom;

public class Machine {
    private Product product;
    private final Object object = new Object();
    private String machineName;
    private boolean consumed = false;
    private long serviceTime;

    public Machine(String machineName){
        this.machineName = machineName;
        this.serviceTime = ThreadLocalRandom.current().nextInt(800, 3501);
    }


    public void activate(BufferQueue prevBufferQueue, BufferQueue nextBufferQueue){




        Runnable consumer = () -> {
            while(true){
                synchronized (object){
                    try{
                        while(prevBufferQueue.getProducts().isEmpty()) {
                            System.out.println(this.machineName + " is ready ");
                            Thread.sleep(600);
                        }
                        product = prevBufferQueue.dequeue();
                        consumed = true;
                        object.notify();
                        object.wait();
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
                        while(product != null && consumed){
                            Thread.sleep(serviceTime);
                            nextBufferQueue.enqueue(product);
                            System.out.println("Servicing" + " - "
                                    + product);
                            object.notify();
                            System.out.println(this.machineName + " " + prevBufferQueue.getProducts());
                            System.out.println(this.machineName + " " + nextBufferQueue.getProducts());

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