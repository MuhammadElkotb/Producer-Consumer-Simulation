package Controllers;

public class testCtr {

    private int ctr;


    public void count(){
        for(int i = 0; i < 500000; i++){
            this.ctr = i;
            System.out.println(i);
        }
    }

    public int getCtr() {
        return this.ctr;
    }
}
