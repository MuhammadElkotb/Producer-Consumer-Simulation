package Model;

import java.util.ArrayList;

public class BufferQueue {
    private ArrayList<Product> products;
    private String ID;

    public BufferQueue(String ID){
        this.ID = ID;
        this.products = new ArrayList<>();
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
            this.notify();
        }

    }

    public Product dequeue() throws Exception{
        synchronized (this){
            while(this.products.size() == 0) this.wait();
            return this.products.remove(0);

        }

    }
}
