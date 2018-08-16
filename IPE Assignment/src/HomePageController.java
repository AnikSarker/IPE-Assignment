/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.util.Pair;

public class HomePageController implements Initializable {
    Solution Sol;
    @FXML
    private TextField InputJob;
    @FXML
    private AnchorPane MainPane;
    ArrayList<TextField>Machine1 = new ArrayList<>();
    ArrayList<TextField>Machine2 = new ArrayList<>();
    ArrayList<TextField>AnsDisplay = new ArrayList<>();
    
    ArrayList<Integer>Data1 = new ArrayList<>();
    ArrayList<Integer>Data2 = new ArrayList<>();
    ArrayList<Item>SortedList = new ArrayList<>();
    ArrayList<Boolean>Used = new ArrayList<>();
    ArrayList<Integer>Ans = new ArrayList<>();
    ArrayList<Integer>Event = new ArrayList<>();
    
    ArrayList<Integer>StartTime1= new ArrayList<>();
    ArrayList<Integer>StartTime2 = new ArrayList<>();
    ArrayList<Integer>EndTime1 = new ArrayList<>();
    ArrayList<Integer>EndTime2 = new ArrayList<>();
    
    Integer Time1=0;
    Integer Time2=0;
    Integer TotalFlowTime=0;
    Integer TotalIdleTime1=0;
    Integer TotalIdleTime2=0;
    
    @FXML
    private Label Message;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setMain(Solution Sol){
        Sol=this.Sol;
    }

    public void CreateJobRow(int n){
            double BaseX=150;  double BaseY=100;
            double Height = 30; double Width = 520/n;
            
            for(int i=0; i<n; i++){
                TextField now = new TextField ();
                now.setLayoutX(BaseX+i*Width); now.setLayoutY(BaseY);
                now.setMinHeight(Height); now.setMaxHeight(Height);
                now.setMinWidth(Width); now.setMaxWidth(Width);
                now.setAlignment(Pos.CENTER); now.setPadding(Insets.EMPTY);
                
                now.setText("J"+(i+1));
                now.setFont(Font.font(15));
                now.setEditable(false);
                
                MainPane.getChildren().add(now);
            }
    }
    
    public void CreateMachineRow(int n){      
            double BaseY=130;
                 
            for(int i=1; i<=2; i++){
                final int x = i;
                double Height = 30;
                double Width = 50;
                double BaseX=100;
                
                for(int j=0;j<=n;j++){
                    final int y=j;
                    TextField now = new TextField ();
                    now.setLayoutX(BaseX);  now.setLayoutY(BaseY);
                    BaseX+=Width;
                    
                    now.setFont(Font.font(15));
                    now.setMinHeight(Height);  now.setMaxHeight(Height);
                    now.setMinWidth(Width); now.setMaxWidth(Width);
                    now.setAlignment(Pos.CENTER); now.setPadding(Insets.EMPTY);
                    
                    if(j==0) {now.setText("M"+i); now.setEditable(false);}                  
                    else{
                        if(i==1) Machine1.add(now);
                        else Machine2.add(now);
                    }
                    MainPane.getChildren().add(now);
                    Width=520/n;
                }
                BaseY+=Height;
            }        
    }
    
        public void CreateAnsRow(int n){
            Label AnsLabel = new Label("Scheduling Order");
            AnsLabel.setFont(Font.font(18));
            
            AnsLabel.setLayoutX(340);
            AnsLabel.setLayoutY(310);
            MainPane.getChildren().add(AnsLabel);
            
            double BaseX=150;  double BaseY=350;
            double Height = 30; double Width = 520/n;
            
            for(int i=0; i<n; i++){
                TextField now = new TextField ();
                now.setLayoutX(BaseX+i*Width); now.setLayoutY(BaseY);
                now.setMinHeight(Height); now.setMaxHeight(Height);
                now.setMinWidth(Width); now.setMaxWidth(Width);
                now.setAlignment(Pos.CENTER); now.setPadding(Insets.EMPTY);
                
                now.setFont(Font.font(15));
                now.setEditable(false);
                
                AnsDisplay.add(now);
                MainPane.getChildren().add(now);
            }
    }
        
    public void CalculateAns(int n){
            int Head=0;
            int Tail=n-1;
            for(Item x : SortedList){
                if(!Used.get(x.JobID)){
                    Used.set(x.JobID, true);
                    if(x.MachineID==1) {Event.add(Head); Ans.set(Head, x.JobID); Head++; System.out.println();}
                    else {Event.add(Tail); Ans.set(Tail, x.JobID); Tail--;}
                }
                else Event.add(-1);
            }
            
            StartTime1.clear(); StartTime2.clear();
            EndTime1.clear(); EndTime2.clear();
            
            for(int i=0;i<n;i++) {StartTime1.add(0); StartTime2.add(0);}
            for(int i=0;i<n;i++) {EndTime1.add(0); EndTime2.add(0);}

            for(Integer Job : Ans){
                StartTime1.set(Job, Time1);
                Time1+=Data1.get(Job);
                EndTime1.set(Job, Time1);
                
                Time2=Integer.max(Time1, Time2);
                StartTime2.set(Job, Time2);
                Time2+=Data2.get(Job);
                EndTime2.set(Job, Time2);
                TotalIdleTime1-=Data1.get(Job);
                TotalIdleTime2-=Data2.get(Job);
                TotalFlowTime+=Time2;
                System.out.println(Job+" "+Time1+" "+Time2);
            }
    }

    public Label SetLabel(String s,double x,double y,int FontSize){
        Label Me = new Label(s);
        Me.setFont(Font.font(FontSize));
        Me.setLayoutX(x);
        Me.setLayoutY(y);
        MainPane.getChildren().add(Me);
        return Me;
    }
    
    public void CreateSymbol(){
        Rectangle v = new Rectangle(150,470,20,20);
        v.setFill(javafx.scene.paint.Color.GREY);
        MainPane.getChildren().add(v);
        
        Label Idle = new Label ("Idle");
        Idle.setLayoutX(180);
        Idle.setLayoutY(470);
        Idle.setFont(Font.font(16));
        
        MainPane.getChildren().add(Idle);
    }
    
    public void CreateConfirmButton(int n){      
        Button btn=new Button("Submit");
        btn.setFont(Font.font(15));
        btn.setLayoutX(600);
        btn.setLayoutY(205);
        MainPane.getChildren().add(btn);
        
        btn.setOnMouseClicked((event) ->{
            Data1.clear(); Data2.clear(); SortedList.clear();
            for(TextField x : Machine1){
                try{
                    Data1.add(Integer.parseInt(x.getText()));
                }
                catch(Exception e){
                    Message.setText("Data Format Incorrect");
                    return;
                }
            }
            
            for(TextField x : Machine2){
                try{
                    Data2.add(Integer.parseInt(x.getText()));
                }
                catch(Exception e){
                    Message.setText("Data Format Incorrect");
                    return;
                }
            }
            
            Message.setText("");
            for(Integer i=0;i<Data1.size();i++) SortedList.add(new Item(Data1.get(i), i, 1));
            for(Integer i=0;i<Data2.size();i++) SortedList.add(new Item(Data2.get(i), i, 2));
            Collections.sort(SortedList);
            Used.clear(); for(int i=0;i<n;i++) Used.add(false);
            Ans.clear(); for(int i=0;i<n;i++) Ans.add(-1);
            Event.clear();
            CreateAnsRow(n);
            CalculateAns(n);
            
            Label dialogue=new Label("Start");
            dialogue.setLayoutX(245);
            dialogue.setLayoutY(250);
            dialogue.setFont(Font.font(20));
            MainPane.getChildren().add(dialogue);
            

                                   
            Task task = new Task<Void>() {
              @Override
              public Void call() throws Exception {
                int i=0;
                while(true){
                  final int ii = i;
                  
                  //Phase 1
                  Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(ii<SortedList.size()){
                            final Item x = SortedList.get(ii);
                            final Integer event = Event.get(ii);
                            
                            if(event>=0){
                                 dialogue.setText("Job "+(x.JobID+1)+", Machine "+x.MachineID+", Processing Time "+x.ProcessingTime);
                                 
                                TextField Input = null;
                                TextField Other = null;
                                if(x.MachineID==1) {Input = Machine1.get(x.JobID); Other=Machine2.get(x.JobID);}
                                else {Input = Machine2.get(x.JobID); Other=Machine1.get(x.JobID);}
                                Input.setStyle("-fx-control-inner-background: red;");
                            }
                        }
                        else dialogue.setText("Scheduling Simulation Completed");
                    }                    
                  });
                    try {
                        final Integer event = Event.get(ii);
                        if(event>=0) Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //5 3 4 6 1
                    //9 7 4 1 5
                    
                   //Phase 2
                  Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(ii<SortedList.size()){
                            final Item x = SortedList.get(ii);
                            final Integer event = Event.get(ii);
                            
                            if(event>=0){
                                TextField Input = null;
                                TextField Other = null;
                                if(x.MachineID==1) {Input = Machine1.get(x.JobID); Other=Machine2.get(x.JobID);}
                                else {Input = Machine2.get(x.JobID); Other=Machine1.get(x.JobID);}                                

                                TextField now = AnsDisplay.get(event);
                                now.setText("J"+Integer.toString(x.JobID+1));
                                now.setStyle("-fx-control-inner-background: green;");
                                Input.setStyle("-fx-control-inner-background: black;");
                                Other.setStyle("-fx-control-inner-background: black;");
                            }
                        }
                        
                        if(ii==SortedList.size()-1){
                                    CreateSymbol();
                                    SetLabel("Gantt Chart", 375, 425, 18);
                                    
                                    //Gantt Chart for Machine 1
                                    SetLabel("M1", 110, 535, 16);
                                    
                                    double BaseX=150;
                                    double Scale=520/Time2;
                                    
                                    Rectangle v = new Rectangle(BaseX,530,Time2*Scale,40);
                                    v.setFill(javafx.scene.paint.Color.GREY);
                                    MainPane.getChildren().add(v);
                                    
                                    for(int i=0;i<n;i++){
                                        Integer Job=Ans.get(i);
                                        int StartTime=StartTime1.get(Job);
                                        int EndTime=EndTime1.get(Job);
                                        Rectangle r = new Rectangle(BaseX+StartTime*Scale,530,Data1.get(Job)*Scale,40);
                                        
                                        if(i%2==0) r.setFill(javafx.scene.paint.Color.ORANGE);
                                        else r.setFill(javafx.scene.paint.Color.YELLOW);
                                        MainPane.getChildren().add(r);
                                        SetLabel("J"+(Job+1),BaseX+StartTime*Scale+(Data1.get(Job))*Scale/2-8,540, 16);
                                        Label lbl1 = SetLabel(""+StartTime,BaseX+StartTime*Scale-4, 510, 16);
                                        Label lbl2 = SetLabel(""+EndTime,BaseX+EndTime*Scale-4, 510, 16);
                                        lbl1.setFont(Font.font("Arial Black"));
                                        lbl2.setFont(Font.font("Arial Black"));
                                    }
                                    Label EndMark = SetLabel(""+Time2,BaseX+Time2*Scale-4, 510, 16);
                                    EndMark.setFont(Font.font("Arial Black"));

                                    
                                    //Gantt Chart for Machine 2
                                    SetLabel("M2", 110, 595, 16);
                                    BaseX=150;
                                    Scale=520/Time2;
                                    
                                    Rectangle w = new Rectangle(BaseX,590,Time2*Scale,40);
                                    w.setFill(javafx.scene.paint.Color.GREY);
                                    MainPane.getChildren().add(w);
                                    
                                    Label BegMark = SetLabel(""+0,BaseX+0*Scale-4, 635, 14);
                                    BegMark.setFont(Font.font("Arial Black"));
                                    for(int i=0;i<n;i++){
                                        Integer Job=Ans.get(i);
                                        int StartTime=StartTime2.get(Job);
                                        int EndTime=EndTime2.get(Job);
                                        Rectangle r = new Rectangle(BaseX+StartTime*Scale,590,Data2.get(Job)*Scale,40);
                                        
                                        if(i%2==0) r.setFill(javafx.scene.paint.Color.ORANGE);
                                        else r.setFill(javafx.scene.paint.Color.YELLOW);
                                                                                
                                        MainPane.getChildren().add(r);
                                        SetLabel("J"+(Job+1),BaseX+StartTime*Scale+Data2.get(Job)*Scale/2-8,600, 16);
                                        Label lbl1 = SetLabel(""+StartTime,BaseX+StartTime*Scale-4, 635, 14);
                                        Label lbl2 = SetLabel(""+EndTime,BaseX+EndTime*Scale-4, 635, 14);
                                        lbl1.setFont(Font.font("Arial Black"));
                                        lbl2.setFont(Font.font("Arial Black"));
                                    }
                                    
                                    SetLabel("Total Flow time : "+TotalFlowTime, 230, 470, 16);
                                    SetLabel("M1 Idle time : "+(Time2+TotalIdleTime1), 400, 470, 16);
                                    SetLabel("M2 Idle time : "+(Time2+TotalIdleTime2), 540, 470, 16);
                        }
                    }                    
                  });
                    try {
                        final Integer event = Event.get(ii);
                        if(event>=0) Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i++;
                }
              }
            };
            Thread th = new Thread(task);
            th.setDaemon(true);
            th.start();            
         });      
    }
    
    @FXML
    public void OnActionInputJobField() throws IOException{         
        try{
            int n = Integer.parseInt(InputJob.getText());
            
            for(TextField x : Machine1) MainPane.getChildren().remove(x);
            for(TextField x : Machine2) MainPane.getChildren().remove(x);
            
            Machine1.clear(); Machine2.clear();
            Data1.clear(); Data2.clear();
            
            CreateJobRow(n);
            CreateMachineRow(n);
            CreateConfirmButton(n);
        }
        catch(NumberFormatException e) {Message.setText("Data Format Incorrect");}
        catch(NullPointerException e) {Message.setText("Data Format Incorrect");}
    }
}
