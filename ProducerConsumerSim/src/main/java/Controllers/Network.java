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
    private ArrayList<Machine> machines;

    static BufferQueue createStartingQueue() throws Exception {
        BufferQueue startingQueue = new BufferQueue();
        for (int i = 0; i < 40; i++) {
            startingQueue.enqueue(new Product());
        }
        return startingQueue;

    }
    public void  initialize(MultivaluedMap<productionNetworkElement, productionNetworkElement> forwardProductionNetwork, MultivaluedMap<productionNetworkElement, productionNetworkElement> backwardProductionNetwork){
        HashMap<productionNetworkElement, ArrayList<productionNetworkElement>> modifiedForwardProductionNetwork = new HashMap<>();
        HashMap<productionNetworkElement, ArrayList<productionNetworkElement>> modifiedBackwardProductionNetwork = new HashMap<>();



        for(productionNetworkElement key:forwardProductionNetwork.keySet()){
            if(modifiedForwardProductionNetwork.containsKey(key)){
                modifiedForwardProductionNetwork.get(key).add(forwardProductionNetwork.getFirst(key));
                forwardProductionNetwork.remove(key,forwardProductionNetwork.getFirst(key));
            }else{
                modifiedForwardProductionNetwork.put(key,new ArrayList<productionNetworkElement>((Collection<? extends productionNetworkElement>) forwardProductionNetwork.getFirst(key)));

            }
        }
        System.out.println(modifiedForwardProductionNetwork.entrySet());

        for(productionNetworkElement key:backwardProductionNetwork.keySet()){
            if(modifiedBackwardProductionNetwork.containsKey(key)){
                modifiedBackwardProductionNetwork.get(key).add(backwardProductionNetwork.getFirst(key));
                backwardProductionNetwork.remove(key,backwardProductionNetwork.getFirst(key));
            }else{
                modifiedBackwardProductionNetwork.put(key,new ArrayList<productionNetworkElement>((Collection<? extends productionNetworkElement>) backwardProductionNetwork.getFirst(key)));
            }
        }
        System.out.println(modifiedForwardProductionNetwork.entrySet());
        this.createMachines(modifiedForwardProductionNetwork,modifiedBackwardProductionNetwork);

    }
    public void createMachines(HashMap<productionNetworkElement, ArrayList<productionNetworkElement>> modifiedForwardProductionNetwork,
                          HashMap<productionNetworkElement, ArrayList<productionNetworkElement>> modifiedBackwardProductionNetwork){
        for(productionNetworkElement element:modifiedForwardProductionNetwork.keySet()){
            Machine machine = new Machine(element.getID());
            this.machines.add(machine);

        }
        for (productionNetworkElement element:modifiedBackwardProductionNetwork.keySet()){
            if(!modifiedForwardProductionNetwork.containsKey(element)){
                Machine machine = new Machine(element.getID());
                this.machines.add(machine);
            }
        }
    }



    public static void play(){

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
    }
}