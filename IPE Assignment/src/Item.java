public class Item implements Comparable<Item> {
    int ProcessingTime;
    int JobID;
    int MachineID;

    public Item(int a,int b,int c) {ProcessingTime=a; JobID=b; MachineID=c;}

    @Override
    public int compareTo(Item o) {return ProcessingTime-o.ProcessingTime;}
    public void Print(){System.out.println(ProcessingTime+" "+JobID+" "+MachineID);}
}
