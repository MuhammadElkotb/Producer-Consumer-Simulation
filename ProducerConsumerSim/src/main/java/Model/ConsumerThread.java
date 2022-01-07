package Model;

public class ConsumerThread extends Thread{
    private QueueI queue;
    public ConsumerThread(QueueI queue){
        this.queue = queue;
    }
    @Override
    public void run(){
        if(queue.getQueue().isEmpty()){
            try {
                wait();
                queue.dequeue();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try {
                queue.dequeue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
