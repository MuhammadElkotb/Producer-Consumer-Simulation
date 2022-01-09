package Controllers;

public class MachineObserver implements Observer{
    private String machineName;
    public MachineObserver(String machineName){
        this.machineName = machineName;
    }
    public void update(){
        System.out.println(this.machineName+" is ready");
    }
}
