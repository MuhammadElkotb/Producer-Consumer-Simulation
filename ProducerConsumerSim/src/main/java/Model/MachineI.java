package Model;

import java.util.ArrayList;

public interface MachineI {

    String serviceTime = "";
    ArrayList<QueueI> nextQueues = null;

    void service(Product product);

    void setServiceTime(String serviceTime);
    String getServiceTime();

    void addQueue(QueueI queue);

    ArrayList<QueueI> getNextQueues() throws Exception;
}
