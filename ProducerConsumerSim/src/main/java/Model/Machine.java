package Model;

import java.util.ArrayList;

public class Machine {

    String serviceTime;
    String ID;
    ArrayList<QueueI> nextQueues ;
    ArrayList<QueueI> backQueues ;
    ArrayList<ProducerThread> producers ;
    ArrayList<ConsumerThread> consumers ;


    public Machine(String serviceTime , ArrayList<QueueI> nextQueues, ArrayList<QueueI> backQueues){
        this.serviceTime = serviceTime;
        this.backQueues = backQueues;
        this.nextQueues = nextQueues;
        this.producers = new ArrayList<>();
        this.consumers = new ArrayList<>();
        for(QueueI nextQueue:nextQueues){
            producers.add(new ProducerThread(nextQueue));
        }
        for(QueueI backQueue:backQueues){
            consumers.add(new ConsumerThread(backQueue));
        }
    }
    public String getServiceTime() {
        return this.serviceTime;
    }
    public ArrayList<QueueI> getNextQueues() throws Exception{
        if(nextQueues.size() == 0){
            throw new Exception("THIS MACHINE POINTS TO NO NEXT QUEUES");
        }
        return this.nextQueues;
    }
    public ArrayList<QueueI> getBackQueues() throws Exception{
        if(backQueues.size() == 0){
            throw new Exception("THIS MACHINE POINTS TO NO BACK QUEUES");
        }
        return this.backQueues;
    }

    public void addNextQueue(QueueI queue) {
        this.nextQueues.add(queue);
    }
    public void addBackQueue(QueueI queue) {
        this.backQueues.add(queue);
    }
    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }
    public void activate(){
        while (true){
            for (ProducerThread producer:producers){
                producer.run();
            }
            for (ConsumerThread consumer:consumers){
                consumer.run();
            }
        }
    }

}
