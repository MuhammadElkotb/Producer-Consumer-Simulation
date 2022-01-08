package Controllers;

import Model.*;

import java.util.concurrent.ThreadLocalRandom;

public class Network {
    private static BufferQueue createStartingQueue(){
        BufferQueue startingQueue = new BufferQueue();
        for(int i = 0; i < 15; i++){
            startingQueue.enqueue(new Product());
        }
        return startingQueue;
    }

    public static void play(){

        BufferQueue bufferQueue0 = new BufferQueue();
        bufferQueue0 = createStartingQueue();
        BufferQueue bufferQueue1 = new BufferQueue();
        BufferQueue bufferQueue2 = new BufferQueue();
        BufferQueue bufferQueue3 = new BufferQueue();
        BufferQueue bufferQueue4 = new BufferQueue();

        Machine machine1 = new Machine("Machine 1");
        Machine machine2 = new Machine("Machine 2");
        Machine machine3 = new Machine("Machine 3");
        Machine machine4 = new Machine("Machine 4");

        try {

            machine1.activate(bufferQueue0, bufferQueue1);
            machine2.activate(bufferQueue1, bufferQueue2);
            machine3.activate(bufferQueue2, bufferQueue3);
            machine4.activate(bufferQueue3, bufferQueue4);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
