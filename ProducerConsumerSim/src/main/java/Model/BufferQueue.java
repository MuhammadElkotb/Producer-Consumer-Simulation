package Model;

import Controllers.BufferQueueObserver;
import Controllers.EventManager;

import java.util.ArrayList;

public class BufferQueue {
    private ArrayList<Product> products;
    private String ID;
    private EventManager manager;

    public BufferQueue(String ID){
        this.ID = ID;
        this.products = new ArrayList<>();
        this.manager = EventManager.getInstance();
        this.manager.addListener(("Queue"+this.ID),new BufferQueueObserver());
    }

    public String getID() {
        return ID;
    }

    public synchronized ArrayList<Product> getProducts() {
        return this.products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }



    public synchronized void enqueue(Product product) throws Exception{
        synchronized (this){
            this.products.add(product);
            this.manager.notify("Queue"+this.ID);
            this.notify();
        }

    }

    public Product dequeue() throws Exception{
        synchronized (this){
            while(this.products.size() == 0) this.wait();
            this.manager.notify("Queue"+this.ID);
            return this.products.remove(0);

        }

    }
}
