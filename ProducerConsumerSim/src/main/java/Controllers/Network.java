package Controllers;

import Model.*;

import java.util.Arrays;
import java.util.Comparator;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Network {
    private ArrayList<Machine> machines;
    private ArrayList<BufferQueue> bufferQueues;
    private boolean onChange = false;

    public ArrayList<Machine> getMachines() {
        return this.machines;
    }

    public void setMachines(ArrayList<Machine> machines) {
        this.machines = machines;
    }

    public void flipChange(){
        this.onChange = !this.onChange;
    }
    public void setChange(boolean change){
        this.onChange = change;
    }
    public boolean getChange(){
        return this.onChange;
    }

    public Network(int n, int m){
        this.bufferQueues = new ArrayList<>();
        for(int i = 0; i < n; i++){
            this.bufferQueues.add(new BufferQueue(String.valueOf(i)));
        }
        this.machines = new ArrayList<>();
        for(int i = 0; i < m; i++){
            this.machines.add(new Machine("Machine " + String.valueOf(i)));
        }
    }

    public ArrayList<Object> getNetwork() {
        System.out.println("CHANGE -> " + this.getChange());
        if(this.getChange() == true){
            this.setChange(false);
            ArrayList<Object> ret = new ArrayList<>();
            ret.add(this.machines);
            ret.add(this.bufferQueues);
            return ret;
        }
        else
            return null;
    }

    public void setBufferQueue(ArrayList<BufferQueue> bufferQueues) {
        this.bufferQueues = bufferQueues;
    }

     BufferQueue createStartingQueue() throws Exception {
        BufferQueue startingQueue = new BufferQueue("0");
        for (int i = 0; i < 20; i++) {
            startingQueue.enqueue(new Product(), this);
        }
        return startingQueue;

    }

    public void  initialize(MultivaluedHashMap<productionNetworkElement, productionNetworkElement> forwardProductionNetwork, MultivaluedHashMap<productionNetworkElement, productionNetworkElement> backwardProductionNetwork){
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
            ArrayList<BufferQueue> queues = new ArrayList<>();
            for (productionNetworkElement valueElement:modifiedForwardProductionNetwork.get(element)){
                BufferQueue queue = new BufferQueue(valueElement.getID());
                queues.add(queue);
            }
            machine.setNextBufferQueues(queues);
            this.machines.add(machine);

        }
        for (productionNetworkElement element:modifiedBackwardProductionNetwork.keySet()){
            if(!modifiedForwardProductionNetwork.containsKey(element)){
                Machine machine = new Machine(element.getID());
                ArrayList<BufferQueue> queues1 = new ArrayList<>();
                for (productionNetworkElement valueElement:modifiedBackwardProductionNetwork.get(element)){
                    BufferQueue queue = new BufferQueue(valueElement.getID());
                    queues1.add(queue);
                }
                machine.setPrevBufferQueues(queues1);
                this.machines.add(machine);
            }else {
                for(Machine machine:this.machines){
                    if(machine.getMachineName() == element.getID()) {
                        ArrayList<BufferQueue> queues2 = new ArrayList<>();
                        for (productionNetworkElement valueElement : modifiedBackwardProductionNetwork.get(element)) {
                            BufferQueue queue = new BufferQueue(valueElement.getID());
                            queues2.add(queue);
                        }
                        machine.setPrevBufferQueues(queues2);
                        break;
                    }
                }
            }
        }
    }

    public void play(){



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
            System.out.println(this.bufferQueues.get(0).getProducts());


            this.getMachines().get(0).activate(this.bufferQueues.get(0), this.bufferQueues.get((1)), this);
            this.getMachines().get(1).activate(this.bufferQueues.get(1), this.bufferQueues.get((2)), this);
            this.getMachines().get(2).activate(this.bufferQueues.get(2), this.bufferQueues.get((3)), this);
            this.getMachines().get(3).activate(this.bufferQueues.get(3), this.bufferQueues.get((4)), this);
            this.getMachines().get(4).activate(this.bufferQueues.get(4), this.bufferQueues.get((5)), this);




        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}