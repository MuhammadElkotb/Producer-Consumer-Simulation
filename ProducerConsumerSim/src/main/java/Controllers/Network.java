package Controllers;

import Model.*;

import java.util.concurrent.ThreadLocalRandom;

public class Network {


    public static BufferQueue play(){

        BufferQueue bufferQueue0 = new BufferQueue();
        BufferQueue bufferQueue1 = new BufferQueue();
        BufferQueue bufferQueue2 = new BufferQueue();
        BufferQueue bufferQueue3 = new BufferQueue();
        BufferQueue bufferQueue4 = new BufferQueue();

        InputThread inputThread = new InputThread();

        inputThread.addProduct(bufferQueue0);

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
        return bufferQueue2;
    }
}