package Controllers;

public class Memento {
    private Object network;

    public Memento(Object network){
        this.network = network;
    }
    public Object getNetwork() {
        return network;
    }
}
