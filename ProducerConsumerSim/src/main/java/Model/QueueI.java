package Model;

import java.util.ArrayList;

public interface QueueI {
    ArrayList<Product> products = null;



    void enqueue(Product product) throws Exception;
    Product dequeue() throws Exception;

    void setQueue(ArrayList<Product> products);
    ArrayList<Product> getQueue();

    int getSize();




}
