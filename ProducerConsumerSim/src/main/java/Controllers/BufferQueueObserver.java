package Controllers;

public class BufferQueueObserver implements Observer{
    public BufferQueueObserver(){}

    @Override
    public void update(){

    }

    @Override
    public void update(Network network) {
        network.incrementCtr();
    }
}
