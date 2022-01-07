package Model;
import java.util.ArrayList;

public class Queue implements QueueI {

    private ArrayList<Product> products;
    private ArrayList<MachineI> machines;
    private static int maxSize = 10000;

    public Queue(){
        this.products = new ArrayList<>();
        this.machines = new ArrayList<>();
    }


    @Override
    public void setQueue(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public ArrayList<Product> getQueue() {
        return null;
    }

    @Override
    public int getSize() {
        return this.products.size();
    }

    @Override
    public void enqueue(Product product) throws Exception{

        if(this.products.size() + 1 > maxSize){
            throw new Exception("QUEUE IS FULL CANNOT ENQUEUE");
        }
        this.products.add(product);
        System.out.println("ENQUEUED SUCCESSFULLY");


    }

    @Override
    public Product dequeue() throws Exception{
        if(this.products.size() == 0){
            throw new Exception("QUEUE IS EMPTY CANNOT DEQUEUE");
        }
        Product product = this.products.remove(0);
        System.out.println("DEQUEUED SUCCESSFULLY");
        return product;
    }

}
