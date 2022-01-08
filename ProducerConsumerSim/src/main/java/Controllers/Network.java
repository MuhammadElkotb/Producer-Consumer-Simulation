package Controllers;

import Model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Network {
    public static void  initialize(Map<productionNetworkElement, productionNetworkElement> forwardProductionNetwork,Map<productionNetworkElement, productionNetworkElement> backwardProductionNetwork){
        HashMap<productionNetworkElement, ArrayList<productionNetworkElement>> modifiedForwardProductionNetwork = new HashMap<>();
        HashMap<productionNetworkElement, ArrayList<productionNetworkElement>> modifiedBackwardProductionNetwork = new HashMap<>();

        for(productionNetworkElement key:forwardProductionNetwork.keySet()){
            if(modifiedForwardProductionNetwork.containsKey(key)){
                modifiedForwardProductionNetwork.get(key).add(forwardProductionNetwork.get(key));
            }else{
                modifiedForwardProductionNetwork.put(key,new ArrayList<productionNetworkElement>((Collection<? extends productionNetworkElement>) forwardProductionNetwork.get(key)));
            }
        }
        System.out.println(modifiedForwardProductionNetwork.entrySet());

        for(productionNetworkElement key:backwardProductionNetwork.keySet()){
            if(modifiedBackwardProductionNetwork.containsKey(key)){
                modifiedBackwardProductionNetwork.get(key).add(backwardProductionNetwork.get(key));
            }else{
                modifiedBackwardProductionNetwork.put(key,new ArrayList<productionNetworkElement>((Collection<? extends productionNetworkElement>) backwardProductionNetwork.get(key)));
            }
        }
        System.out.println(modifiedForwardProductionNetwork.entrySet());
    }

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