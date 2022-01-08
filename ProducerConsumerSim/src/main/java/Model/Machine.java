package Model;


import com.sun.enterprise.module.HK2Module;

import java.util.concurrent.ThreadLocalRandom;

public class Machine {
    private Product product;
    private final Object object = new Object();
    private String machineName;
    private boolean consumed = false;
    private long serviceTime;
    private Thread produceThread;
    private Thread consumeThread;

    public Machine(String machineName){
        this.machineName = machineName;
        this.serviceTime = ThreadLocalRandom.current().nextInt(800, 3501);
    }


    public void activate(BufferQueue prevBufferQueue, BufferQueue nextBufferQueue){





        Runnable consumer = () -> {

            while(true){

                synchronized (prevBufferQueue){

                    try{
                        System.out.println(this.machineName);
                        while(prevBufferQueue.getProducts().isEmpty()) {
                            System.out.println(this.machineName + " is ready ");

                            prevBufferQueue.wait();
                            System.out.println("lol");


                        }
                        product = prevBufferQueue.dequeue();
                        consumed = true;
                        produceThread.run();
//                        object.notify();
//                        object.wait();
                    }
                    catch (Exception e){
                        System.out.println(e);
                    }
                }
            }
        };


        Runnable producer = () -> {
            while(true){
                synchronized (nextBufferQueue ){
                    try{
                        while(consumed){
                            if(nextBufferQueue.getProducts().isEmpty()){
                                Thread.sleep(serviceTime);
                                nextBufferQueue.enqueue(product);
                                System.out.println("notify");
                                nextBufferQueue.notifyAll();
                            }else {
                                Thread.sleep(serviceTime);
                                nextBufferQueue.enqueue(product);
                            }

                            System.out.println("Servicing" + " - "
                                    + product);
//                            object.notify();
                            System.out.println(this.machineName + " " + prevBufferQueue.getProducts());
                            System.out.println(this.machineName + " " + nextBufferQueue.getProducts());

                            consumed = false;
                            consumeThread.run();
//                            object.wait();
                        }
                    }
                    catch (Exception e){
                        System.out.println(e);
                    }
                }
            }
        };

        consumeThread = new Thread(consumer);
        produceThread = new Thread(producer);
        consumeThread.start();

    }
}