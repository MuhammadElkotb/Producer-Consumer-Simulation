package Model;

import Controllers.BufferQueueObserver;
import Controllers.EventManager;
import Controllers.Network;

import java.util.ArrayList;

public class BufferQueue {
    private ArrayList<Product> products;
    private String bufferID;
    private EventManager manager;
    private int size;

    public BufferQueue(String bufferID){
        this.bufferID = bufferID;
        this.products = new ArrayList<>();
        this.manager = EventManager.getInstance();
        this.manager.addListener((this.bufferID),new BufferQueueObserver());
    }


    public String getID() {
        return bufferID;
    }

    public synchronized ArrayList<Product> getProducts() {
        synchronized (this){
            return this.products;

        }
    }

    public int getSize() {
        return this.products.size();
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }



    public synchronized void enqueue(Product product, Network network) throws Exception{
        synchronized (this){
            this.products.add(product);
            this.manager.notify(this.bufferID, network);
            this.notify();
        }

    }

    public Product dequeue(Network network) throws Exception{
        synchronized (this){
            while(this.products.size() == 0) this.wait();
            this.manager.notify(this.bufferID, network);
            return this.products.remove(0);
        }
    }
}
