package Model;

public class ProducerThread extends Thread{
    private QueueI queue;
    public ProducerThread(QueueI queue){
        this.queue = queue;
    }
    @Override
    public void run(){
        if(queue.getQueue().isEmpty()){
            try {
                queue.enqueue(new Product(ColorGenerator.generate()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            notify();
        }else {
            try {
                queue.enqueue(new Product(ColorGenerator.generate()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
