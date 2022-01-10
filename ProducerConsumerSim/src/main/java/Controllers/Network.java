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


    private HashMap<String,BufferQueue> bufferQueues;

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

    public Network(){
        this.bufferQueues = new HashMap<>();
        this.machines = new ArrayList<>();


    }

    public ArrayList<Object> getNetwork() {
        System.out.println("CHANGE -> " + this.getChange());
        if(this.getChange() == true){
            this.setChange(false);

            ArrayList<Object> ret = new ArrayList<>();
            ret.add(this.machines);
            ret.add((ArrayList<BufferQueue>) this.bufferQueues.values());
            return ret;
        }
        else
            return null;
    }


    public void addBufferQueue(String ID, BufferQueue queue){
        this.bufferQueues.put(ID,queue);
    }

     BufferQueue createStartingQueue() throws Exception {
        BufferQueue startingQueue = new BufferQueue("0");
        for (int i = 0; i < 20; i++) {
            startingQueue.enqueue(new Product(), this);
        }
        return startingQueue;

    }

    public void  initialize(HashMap<String, String[]> forwardProductionNetwork, HashMap<String, String[]> backwardProductionNetwork){

        //forward initilaization
        for(String element:forwardProductionNetwork.keySet()){
            Machine machine = new Machine(element);
            ArrayList<BufferQueue> queues = new ArrayList<>();
            for (String valueElement:forwardProductionNetwork.get(element)){
                if(this.bufferQueues.containsKey(valueElement)){
                    queues.add(this.bufferQueues.get(valueElement));
                }else {
                    BufferQueue queue = new BufferQueue(valueElement);
                    queues.add(queue);
                    this.addBufferQueue(valueElement,queue);
                }

            }
            machine.setNextBufferQueues(queues);
            this.machines.add(machine);

        }
        //backward initilaization

        for (String element:backwardProductionNetwork.keySet()){
            if(!backwardProductionNetwork.containsKey(element)){
                Machine machine = new Machine(element);
                ArrayList<BufferQueue> queues1 = new ArrayList<>();
                for (String valueElement:backwardProductionNetwork.get(element)){
                    if(this.bufferQueues.containsKey(valueElement)){
                        queues1.add(this.bufferQueues.get(valueElement));
                    }else {
                        BufferQueue queue = new BufferQueue(valueElement);
                        queues1.add(queue);
                        this.addBufferQueue(valueElement,queue);
                    }
                }
                machine.setPrevBufferQueues(queues1);
                this.machines.add(machine);
            }else {
                for(Machine machine:this.machines){
                    if(machine.getMachineName() == element) {
                        ArrayList<BufferQueue> queues2 = new ArrayList<>();
                        for (String valueElement : backwardProductionNetwork.get(element)) {
                            if(this.bufferQueues.containsKey(valueElement)){
                                queues2.add(this.bufferQueues.get(valueElement));
                            }else {
                                BufferQueue queue = new BufferQueue(valueElement);
                                queues2.add(queue);
                                this.addBufferQueue(valueElement, queue);
                            }
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



        try {

            for(Machine machine:machines){
                for (BufferQueue nextQueue:machine.getNextBufferQueues()){
                    for (BufferQueue prevQueue:machine.getPrevBufferQueues()){
                        machine.activate(prevQueue,nextQueue,this);
                    }
                }
            }




        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}