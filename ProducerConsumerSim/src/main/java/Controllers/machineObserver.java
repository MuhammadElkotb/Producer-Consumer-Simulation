package Controllers;

public class machineObserver {
    private String machineName;
    public machineObserver(String machineName){
        this.machineName = machineName;
    }
    public void update(){
        System.out.println(this.machineName+" is ready");
    }
}
