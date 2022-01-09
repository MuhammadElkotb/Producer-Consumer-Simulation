package Model;

import Controllers.BufferQueueObserver;
import Controllers.EventManager;

import java.util.ArrayList;

public class BufferQueue {
    private ArrayList<Product> products;
    private String bufferID;
    private EventManager manager;

    public BufferQueue(String bufferID){
        this.bufferID = bufferID;
        this.products = new ArrayList<>();
        this.manager = EventManager.getInstance();
        this.manager.addListener(("Queue"+this.bufferID),new BufferQueueObserver());
    }

    public BufferQueue(){
        this.products = new ArrayList<>();
        this.manager = EventManager.getInstance();
        this.manager.addListener(("Queue"+this.bufferID),new BufferQueueObserver());

    }

    public String getID() {
        return bufferID;
    }

    public synchronized ArrayList<Product> getProducts() {
        synchronized (this){
            return this.products;

        }
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }



    public synchronized void enqueue(Product product) throws Exception{
        synchronized (this){
            this.products.add(product);
            this.manager.notify("Queue"+this.bufferID);
            this.notify();
        }

    }

    public Product dequeue() throws Exception{
        synchronized (this){
            while(this.products.size() == 0) this.wait();
            this.manager.notify("Queue"+this.bufferID);
            return this.products.remove(0);

        }

    }
}
