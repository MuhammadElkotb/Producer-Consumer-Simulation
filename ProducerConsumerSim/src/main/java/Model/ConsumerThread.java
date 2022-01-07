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

            } catch (InterruptedException e) {
                try {
                    queue.dequeue();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
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
