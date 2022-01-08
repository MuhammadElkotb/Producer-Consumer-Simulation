package Model;

import java.util.ArrayList;

public class BufferQueue {
    private ArrayList<Product> products;

    public BufferQueue(){
        this.products = new ArrayList<>();
    }

    public ArrayList<Product> getProducts() {
        return this.products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }



    public void enqueue(Product product){
        this.products.add(product);
    }

    public Product dequeue(){
        return this.products.remove(0);
    }


}
