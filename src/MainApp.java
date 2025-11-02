
import java.time.LocalDate;
import java.util.Scanner;

public class MainApp{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MedicineManager manager = new MedicineManager();

        while (true) {
            System.out.println("\n====== Medicine Expiry Tracker ======");
            System.out.println("1. Add New Medicine");
            System.out.println("2. View All Medicines");
            System.out.println("3. Search Medicine by Name");
            System.out.println("4. Update Medicine Details");
            System.out.println("5. Delete a Medicine");
            System.out.println("6. Check Expired Medicines");
            System.out.println("7. Remove Expired Medicines");
            System.out.println("8. Show Near Expiry Medicines");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(" Invalid input. Please enter a number.");
                continue;
            }

            switch(choice) {
                case 1:
                    System.out.print("Enter Medicine Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Batch Number: ");
                    String batch = sc.nextLine();

                    System.out.print("Enter Quantity: ");
                    int qty = Integer.parseInt(sc.nextLine());

                    System.out.print("Enter Expiry Date (YYYY-MM-DD): ");
                    LocalDate date = LocalDate.parse(sc.nextLine());

                    Medicine newMed = new Medicine(name, batch, qty, date);
                    manager.addMedicine(newMed);
                    break;

                case 2:
                    manager.viewAllMedicines();
                    break;

                case 3:
                    System.out.print("Enter Medicine Name to Search: ");
                    String searchName = sc.nextLine();
                    Medicine found = manager.searchMedicineByName(searchName);
                    if (found != null) {
                        System.out.println("\n Medicine Found:");
                        System.out.println("Name: " + found.getName());
                        System.out.println("Batch No: " + found.getBatchNumber());
                        System.out.println("Quantity: " + found.getQuantity());
                        System.out.println("Expiry Date: " + found.getExpiryDate());
                    }
                    break;

                case 4:
                    System.out.print("Enter Medicine Name to Update: ");
                    String updateName = sc.nextLine();
                    System.out.print("Enter New Expiry Date (YYYY-MM-DD): ");
                    LocalDate newDate = LocalDate.parse(sc.nextLine());
                    System.out.print("Enter New Quantity: ");
                    int newQty = Integer.parseInt(sc.nextLine());
                    manager.updateMedicine(updateName, newDate, newQty);
                    break;

                case 5:
                    System.out.print("Enter Medicine Name to Delete: ");
                    String delName = sc.nextLine();
                    manager.deleteMedicine(delName);
                    break;

                case 6:
                    System.out.println("Expired Medicines as of Today:");
                    manager.alertNearExpiry(0); // threshold 0 = already expired
                    break;

                case 7:
                    manager.removeExpiredMedicines();
                    break;

                case 8:
                    System.out.print("Enter days to check (e.g., 30): ");
                    int days = Integer.parseInt(sc.nextLine());
                    manager.alertNearExpiry(days);
                    break;

                case 9:
                    System.out.println(" Exiting the program. Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println(" Invalid choice. Try again.");
            }
        }
    }
}
