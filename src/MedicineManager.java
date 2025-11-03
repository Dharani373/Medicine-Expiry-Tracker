import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class MedicineManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Medicine> medicines;
    private static final String FILE_NAME = "medicines.dat"; // use .dat for object data

    public MedicineManager() {
        loadMedicines(); // Load data when the app starts
    }

    // Add a medicine
    public void addMedicine(Medicine med) {
        medicines.add(med);
        saveMedicines();
    }

    // View all medicines
    public void viewAllMedicines() {
        if (medicines.isEmpty()) {
            System.out.println("No medicines available.");
            return;
        }

        System.out.println("\n---- Medicine List ----");
        for (Medicine m : medicines) {
            System.out.println("Name: " + m.getName());
            System.out.println("Batch No: " + m.getBatchNumber());
            System.out.println("Quantity: " + m.getQuantity());
            System.out.println("Expiry Date: " + m.getExpiryDate());
            System.out.println("-----------------------");
        }
    }

    // Search by name
    public Medicine searchMedicineByName(String name) {
        for (Medicine m : medicines) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        System.out.println("Medicine not found: " + name);
        return null;
    }

    // Update medicine details
    public void updateMedicine(String name, LocalDate newExpiry, int newQty) {
        Medicine m = searchMedicineByName(name);
        if (m != null) {
            m.setExpiryDate(newExpiry);
            m.setQuantity(newQty);
            saveMedicines();
            System.out.println("Medicine updated successfully!");
        }
    }

    // Delete a medicine
    public void deleteMedicine(String name) {
        boolean removed = medicines.removeIf(m -> m.getName().equalsIgnoreCase(name));
        if (removed) {
            saveMedicines();
            System.out.println("Medicine deleted successfully!");
        } else {
            System.out.println("Medicine not found.");
        }
    }

    // Remove expired medicines
    public void removeExpiredMedicines() {
        LocalDate today = LocalDate.now();
        boolean removed = medicines.removeIf(m -> m.getExpiryDate().isBefore(today));
        if (removed) {
            saveMedicines();
            System.out.println("Expired medicines removed.");
        } else {
            System.out.println("No expired medicines found.");
        }
    }

    // Alert for near expiry medicines
    public void alertNearExpiry(int daysThreshold) {
    LocalDate today = LocalDate.now();
    System.out.println("\n=== Expiry Alert ===");

    boolean nearExpiryFound = false;
    boolean expiredFound = false;

    // 1️⃣ Show near-expiry medicines
    System.out.println("\nMedicines nearing expiry (within " + daysThreshold + " days):");
    for (Medicine m : medicines) {
        if (!m.getExpiryDate().isBefore(today) &&
            m.getExpiryDate().isBefore(today.plusDays(daysThreshold))) {
            System.out.println("Name: " + m.getName() +
                    " | Batch: " + m.getBatchNumber() +
                    " | Expiry: " + m.getExpiryDate());
            nearExpiryFound = true;
        }
    }
    if (!nearExpiryFound)
        System.out.println("No medicines nearing expiry.");

    // 2️⃣ Show already expired medicines
    System.out.println("\nExpired medicines:");
    for (Medicine m : medicines) {
        if (m.getExpiryDate().isBefore(today)) {
            System.out.println("Name: " + m.getName() +
                    " | Batch: " + m.getBatchNumber() +
                    " | Expired on: " + m.getExpiryDate());
            expiredFound = true;
        }
    }
    if (!expiredFound)
        System.out.println("No expired medicines.");
    }

    // Save medicines (Object Serialization)
    @SuppressWarnings("unchecked")
    public void saveMedicines() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(medicines);
        } catch (IOException e) {
            System.out.println("Error saving medicines: " + e.getMessage());
        }
    }

    // Load medicines (Object Deserialization)
    @SuppressWarnings("unchecked")
    private void loadMedicines() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            medicines = new ArrayList<>();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            medicines = (List<Medicine>) ois.readObject();
        } catch (Exception e) {
            medicines = new ArrayList<>();
            System.out.println("⚠️ No existing data found or failed to load.");
        }
    }

    // 🔹 Sort medicines by different criteria
    public void sortMedicines(int choice) {
        if (medicines.isEmpty()) {
            System.out.println("No medicines to sort.");
            return;
        }

        switch (choice) {
            case 1:
                medicines.sort(Comparator.comparing(Medicine::getName));
                System.out.println("Medicines sorted by Name.");
                break;
            case 2:
                medicines.sort(Comparator.comparing(Medicine::getExpiryDate));
                System.out.println("Medicines sorted by Expiry Date.");
                break;
            case 3:
                medicines.sort(Comparator.comparingInt(Medicine::getQuantity));
                System.out.println("Medicines sorted by Quantity.");
                break;
            default:
                System.out.println("Invalid sort option.");
        }
        viewAllMedicines(); // show result
    }

    // 🔹 Filter medicines that are low in stock
    public void filterLowStock(int threshold) {
        boolean found = false;
        System.out.println("\nMedicines with quantity below " + threshold + ":");
        for (Medicine m : medicines) {
            if (m.getQuantity() < threshold) {
                System.out.println("Name: " + m.getName() +
                               " | Batch: " + m.getBatchNumber() +
                               " | Quantity: " + m.getQuantity());
                found = true;
            }
        }
        if (!found) {
            System.out.println("All medicines are sufficiently stocked.");
        }
    }
    public void saveToCSV(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
        // 🏷️ Write column headers first
            pw.println("Name,BatchNumber,Quantity,ExpiryDate");

        // 🧾 Then write all medicine data
            for (Medicine m : medicines) {
                pw.println(m.getName() + "," +
                       m.getBatchNumber() + "," +
                       m.getQuantity() + "," +
                       m.getExpiryDate());
            }

             System.out.println("💾 Data saved successfully to " + filename);
            } catch (IOException e) {
            System.out.println("❌ Error saving data: " + e.getMessage());
        }
    }


    // 📂 Load all medicines from CSV file
    public void loadFromCSV(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("📄 No existing data file found. Starting fresh.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            medicines.clear();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    String name = data[0];
                    String batch = data[1];
                    int qty = Integer.parseInt(data[2]);
                    LocalDate date = LocalDate.parse(data[3]);
                    medicines.add(new Medicine(name, batch, qty, date));
                }
            }
            System.out.println("Data loaded successfully from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
    // Optional getter
    public List<Medicine> getMedicines() {
        return medicines;
    }
}
