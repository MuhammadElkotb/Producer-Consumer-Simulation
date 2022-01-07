package Model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Machine implements MachineI{

    String serviceTime;

    ArrayList<QueueI> nextQueues;

    public Machine(String serviceTime){
        this.serviceTime = serviceTime;
        this.nextQueues = new ArrayList<>();
    }

    public String getServiceTime() {
        return this.serviceTime;
    }

    @Override
    public void addQueue(QueueI queue) {
        this.nextQueues.add(queue);
    }

    @Override
    public ArrayList<QueueI> getNextQueues() throws Exception{
        if(nextQueues.size() == 0){
            throw new Exception("THIS MACHINE POINTS TO NO QUEUES");
        }
        return this.nextQueues;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    @Override
    public void service(Product product) {

    }
}
