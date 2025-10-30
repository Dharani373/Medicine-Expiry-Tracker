import java.time.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
public class Main{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        ArrayList<Medicine> list=new ArrayList<>();
        int choice;
        LocalDate today = LocalDate.now();
        while(true) { 
            System.out.println("*------MENU-------*");
            System.out.println("Enter the task : ");
            System.out.println("1. Add a new Medicine\n2. View All Medicines\n3. Check expired Medicines\n4. Delete expired Medicines\n5.exit\n");
            choice=sc.nextInt();
            sc.nextLine();
            switch(choice){
                case 1 : System.out.println("Enter Medice Name");
                         String name = sc.nextLine();

                        System.out.println("Enter Expiry Date(YYYY-MM-DD)");
                        String input = sc.nextLine();
                        LocalDate date = LocalDate.parse(input);
                        // add expiry date into medicine object
                        Medicine m = new Medicine();
                        m.setName(name);
                        m.setExpiryDate(date);
                        System.out.println("Medicine added Successfully");
                        list.add(m);
                    break;
                case 2 : System.out.println("The Medicine present are : ");
                         for(Medicine med : list){
                            System.out.println("Name : "+med.getName());
                            System.out.println("Expiry Date : "+med.getExpiryDate());
                            System.out.println("------------");
                         }

                    break;
                case 3 : int count=0;
                         if(list.isEmpty()){
                            System.out.println("No medicines Available");
                         }
                         for(Medicine med : list){
                            if(med.getExpiryDate().isBefore(today)){
                                System.out.println("Name : "+med.getName());
                                System.out.println("Expiry Date : "+med.getExpiryDate());
                                count++;
                            }
                         }
                         if(count==0){
                            System.out.println("No expired Medicines Found");
                         }
                         break;
                case 4 : int removedCount=0;
                         Iterator<Medicine> itr=list.iterator();
                        while(itr.hasNext()){
                            Medicine med = itr.next();
                            if(med.getExpiryDate().isBefore(today)){
                                //System.out.println("Name : "+med.getName()+" is removed Successfully");
                                itr.remove();
                                removedCount++;
                            }
                        }
                        if(removedCount > 0){
                            System.out.println("All Expired Medicines are removed Successfully");
                        }
                        else{
                            System.out.println("No expired medicines to delete");
                        }
                        break;
                case 5 : System.out.println("You choose to exit");
                        sc.close();
                        return;
                default: System.out.println("Invalid Input Write again");
            }   
        }
    }
}