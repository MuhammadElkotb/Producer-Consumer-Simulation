package Model;


import java.util.concurrent.ThreadLocalRandom;

public class InputThread {



    public void addProduct(BufferQueue bufferQueue){
        Runnable input = () -> {
            while(true){
                synchronized (this){
                    try{
                        long inputRate = ThreadLocalRandom.current().nextInt(1000, 1500);
                        bufferQueue.getProducts().add(new Product());
                        Thread.sleep(inputRate);
                    }
                    catch (Exception e){
                        System.out.println();
                    }
                }
            }
        };
        Thread inputThread = new Thread(input);
        inputThread.start();
    }
}
