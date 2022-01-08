package Controllers;

import Model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

public class Network {

    static BufferQueue createStartingQueue() throws Exception{
        BufferQueue startingQueue = new BufferQueue();
        for(int i = 0; i < 40; i++){
            startingQueue.enqueue(new Product());
        }
        return startingQueue;

    }
    public static int play(){

        BufferQueue bufferQueue0 = new BufferQueue();
        BufferQueue bufferQueue1 = new BufferQueue();
        BufferQueue bufferQueue3 = new BufferQueue();
        BufferQueue bufferQueue4 = new BufferQueue();
        BufferQueue bufferQueue5 = new BufferQueue();
        BufferQueue bufferQueue6 = new BufferQueue();

        InputThread inputThread = new InputThread();

       // inputThread.addProduct(bufferQueue0);






        Machine machine1 = new Machine("Machine 1");
        Machine machine2 = new Machine("Machine 2");
        Machine machine3 = new Machine("Machine 3");
        Machine machine4 = new Machine("Machine 4");
        Machine machine5 = new Machine("Machine 5");
        Machine machine6 = new Machine("Machine 6");
        Machine machine7 = new Machine("Machine 7");


        int ret = 0;


        try {
            bufferQueue0 = createStartingQueue();

            System.out.println(bufferQueue0.getProducts());



            machine1.activate(bufferQueue0, bufferQueue1);
            machine2.activate(bufferQueue1, bufferQueue3);
            machine3.activate(bufferQueue1, bufferQueue3);
            machine4.activate(bufferQueue0, bufferQueue4);
            machine5.activate(bufferQueue3, bufferQueue5);
            machine6.activate(bufferQueue5, bufferQueue6);
            machine6.activate(bufferQueue4, bufferQueue6);
            machine7.activate(bufferQueue5, bufferQueue6);
            machine7.activate(bufferQueue4, bufferQueue6);





        }
        catch (Exception e){
            System.out.println("======================================================================");
            System.out.println(e);
        }
        return ret;

    }
}
