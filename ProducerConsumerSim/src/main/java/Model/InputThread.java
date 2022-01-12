package Model;


import java.util.concurrent.ThreadLocalRandom;

public class InputThread {



    public void addProduct(BufferQueue bufferQueue){
        Runnable input = () -> {
            int inputProduts = ThreadLocalRandom.current().nextInt(30, 500);
            int ctr = 0;
            while(true){
                synchronized (this){

                    try{
                        if(ctr > inputProduts)
                            break;
                        long inputRate = ThreadLocalRandom.current().nextInt(200, 1000);
                        bufferQueue.getProducts().add(new Product());
                        Thread.sleep(inputRate);
                        ctr++;

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