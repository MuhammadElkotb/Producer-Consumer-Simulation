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
    private CareTaker careTaker;
    private Orginator orginator;

    private boolean onChange = false;
    public boolean stop = false;

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
        this.careTaker = new CareTaker();
        this.orginator = new Orginator();

    }

    public ArrayList<Object> getNetwork() {
        ArrayList<Object> ret = new ArrayList<>();
        ArrayList<Machine> copiedMachines = new ArrayList<>();
        ArrayList<BufferQueue> copiedBuffer = new ArrayList<>();
        ArrayList<Object> carry = new ArrayList<>();
        if(this.getChange() == true){
            this.setChange(false);
            ret.add(this.machines);
            ret.add(this.bufferQueues.values());
            for(Machine machine:this.machines){
                copiedMachines.add(machine.copy());
            }
            for(BufferQueue bufferQueue:this.bufferQueues.values()){
                copiedBuffer.add(bufferQueue.copy());
            }
            carry.add(copiedMachines);
            carry.add(copiedBuffer);
            this.orginator.setNetwork(carry);
            this.careTaker.addSnapshot(this.orginator.saveNetworktoMemento());
        }
        else{
            ret = null;
            this.orginator.setNetwork(ret);
            this.careTaker.addSnapshot(this.orginator.saveNetworktoMemento());
        }

        return ret;
    }

    public ArrayList<ArrayList<Object>> replay(){
        ArrayList<ArrayList<Object>> networks = new ArrayList<>();
        for(int i=0; i < this.careTaker.getSize();i++){
            this.orginator.getNetworkfromMemento(this.careTaker.getSnapshot(i));
            networks.add(this.orginator.getNetwork());
        }
        return networks;
    }


    public void addBufferQueue(String ID, BufferQueue queue){
        this.bufferQueues.put(ID,queue);
    }

     void createStartingQueue(BufferQueue bufferQueue) throws Exception {
        for (int i = 0; i < 50; i++) {
            bufferQueue.enqueue(new Product(), this);
        }
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
        try{
            createStartingQueue(this.bufferQueues.get("Queue999999"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void stop(){
        this.stop = true;
    }


    public void play(){

        this.stop = false;


        try {

            //InputThread inputThread = new InputThread();
            //inputThread.addProduct(this.bufferQueues.get("Queue999999"));

            for(Machine machine:machines){
                for (BufferQueue nextQueue:machine.getNextBufferQueues()){
                    for (BufferQueue prevQueue:machine.getPrevBufferQueues()){
                        machine.activate(prevQueue,nextQueue,this);
                    }
                }
            }
            System.out.println("LOLOLOL");

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}