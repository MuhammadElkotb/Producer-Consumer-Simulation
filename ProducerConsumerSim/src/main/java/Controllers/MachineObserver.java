package Controllers;

public class MachineObserver implements Observer{
    private String machineName;
    public MachineObserver(String machineName){
        this.machineName = machineName;
    }
    public void update(){
    }
    public void update(Network network){}
}
