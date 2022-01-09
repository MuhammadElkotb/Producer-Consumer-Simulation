package Controllers;

import Model.*;

import java.util.Arrays;
import java.util.Comparator;
import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Network {

    private ArrayList<BufferQueue> bufferQueues;

    public Network(int n){
        this.bufferQueues = new ArrayList<>();

        for(int i = 0; i < n; i++){
            this.bufferQueues.add(new BufferQueue());
        }
    }

    public ArrayList<BufferQueue> getBufferQueues() {
        return this.bufferQueues;
    }

    public void setBufferQueue(ArrayList<BufferQueue> bufferQueues) {
        this.bufferQueues = bufferQueues;
    }

    static BufferQueue createStartingQueue() throws Exception {
        BufferQueue startingQueue = new BufferQueue();
        for (int i = 0; i < 20; i++) {
            startingQueue.enqueue(new Product());
        }
        return startingQueue;

    }

    public static void  initialize(MultivaluedMap<productionNetworkElement, productionNetworkElement> forwardProductionNetwork, MultivaluedMap<productionNetworkElement, productionNetworkElement> backwardProductionNetwork){
        HashMap<productionNetworkElement, ArrayList<productionNetworkElement>> modifiedForwardProductionNetwork = new HashMap<>();
        HashMap<productionNetworkElement, ArrayList<productionNetworkElement>> modifiedBackwardProductionNetwork = new HashMap<>();


        for(productionNetworkElement key:forwardProductionNetwork.keySet()){
            if(modifiedForwardProductionNetwork.containsKey(key)){
                modifiedForwardProductionNetwork.get(key).add(forwardProductionNetwork.getFirst(key));
                forwardProductionNetwork.remove(key,forwardProductionNetwork.getFirst(key));
            }else{
                modifiedForwardProductionNetwork.put(key,new ArrayList<productionNetworkElement>((Collection<? extends productionNetworkElement>) forwardProductionNetwork.remove(key)));

            }
        }
        System.out.println(modifiedForwardProductionNetwork.entrySet());

        for(productionNetworkElement key:backwardProductionNetwork.keySet()){
            if(modifiedBackwardProductionNetwork.containsKey(key)){
                modifiedBackwardProductionNetwork.get(key).add(backwardProductionNetwork.getFirst(key));
                backwardProductionNetwork.remove(key,backwardProductionNetwork.getFirst(key));
            }else{
                modifiedBackwardProductionNetwork.put(key,new ArrayList<productionNetworkElement>((Collection<? extends productionNetworkElement>) backwardProductionNetwork.remove(key)));
            }
        }
        System.out.println(modifiedForwardProductionNetwork.entrySet());
    }


    public void play(){

        BufferQueue bufferQueue0 = new BufferQueue();
        BufferQueue bufferQueue1 = new BufferQueue();
        BufferQueue bufferQueue2 = new BufferQueue();
        BufferQueue bufferQueue3 = new BufferQueue();
        BufferQueue bufferQueue4 = new BufferQueue();
        BufferQueue bufferQueue5 = new BufferQueue();

        InputThread inputThread = new InputThread();

        //inputThread.addProduct(bufferQueue0);

        Machine machine1 = new Machine("Machine 1");
        Machine machine2 = new Machine("Machine 2");
        Machine machine3 = new Machine("Machine 3");
        Machine machine4 = new Machine("Machine 4");
        Machine machine5 = new Machine("Machine 5");
        Machine machine6 = new Machine("Machine 6");
        Machine machine7 = new Machine("Machine 7");


        try {

            this.bufferQueues.set(0, createStartingQueue());
            System.out.println(bufferQueue0.getProducts());


            machine1.activate(this.bufferQueues.get(0), this.bufferQueues.get(1));
            machine2.activate(this.bufferQueues.get(1), this.bufferQueues.get(2));
            machine3.activate(this.bufferQueues.get(1), this.bufferQueues.get(2));
            machine4.activate(this.bufferQueues.get(0), this.bufferQueues.get(3));
            machine5.activate(this.bufferQueues.get(2), this.bufferQueues.get(4));
            machine6.activate(this.bufferQueues.get(4), this.bufferQueues.get(5));
            machine7.activate(this.bufferQueues.get(4), this.bufferQueues.get(5));
            machine6.activate(this.bufferQueues.get(3), this.bufferQueues.get(5));
            machine7.activate(this.bufferQueues.get(3), this.bufferQueues.get(5));



        }
        catch (Exception e){
            System.out.println("======================================================================");
            System.out.println(e);
        }
    }
}