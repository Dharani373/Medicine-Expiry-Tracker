
import java.time.LocalDate;

public class Medicine {
    private String name;
    private String batchNumber;
    private int  quantity;
    private LocalDate expiryDate;

    Medicine(){

    }
    
    public Medicine(String nm,String batchNo){
        this.name=nm;
        this.batchNumber=batchNo;
    }

    public Medicine(String nm,String batchNo,int quantity,LocalDate date){
        this.name=nm;
        this.batchNumber=batchNo;
        this.quantity=quantity;
        this.expiryDate=date;
    }

    public String getName(){
        return this.name;
    }

    public String getBatchNumber(){
        return  this.batchNumber;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public LocalDate getExpiryDate(){
        return  this.expiryDate;
    }

    public void setName(String nm){
        this.name=nm;
    }

    public void setBatchNumber(String batchNo){
        this.batchNumber=batchNo;
    }

    public void setQuantity(int quantity){
        this.quantity=quantity;
    }

    public void setExpiryDate(LocalDate date){
        this.expiryDate=date;
    }
}
