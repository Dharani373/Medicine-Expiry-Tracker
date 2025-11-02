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
        System.out.println("\nMedicines nearing expiry (within " + daysThreshold + " days):");
        boolean found = false;

        for (Medicine m : medicines) {
            if (!m.getExpiryDate().isBefore(today) &&
                m.getExpiryDate().isBefore(today.plusDays(daysThreshold))) {
                System.out.println("Name: " + m.getName() +
                        " | Batch: " + m.getBatchNumber() +
                        " | Expiry: " + m.getExpiryDate());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No medicines nearing expiry.");
        }
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

    // Optional getter
    public List<Medicine> getMedicines() {
        return medicines;
    }
}
