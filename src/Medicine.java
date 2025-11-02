import java.io.Serializable;
import java.time.LocalDate;

public class Medicine implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String batchNumber;
    private int quantity;
    private LocalDate expiryDate;

    // 🔹 Constructor
    public Medicine(String name, String batchNumber, int quantity, LocalDate expiryDate) {
        this.name = name;
        this.batchNumber = batchNumber;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }

    // 🔹 Getters
    public String getName() {
        return name;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    // 🔹 Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    // 🔹 Display format
    @Override
    public String toString() {
        return "Medicine Name: " + name +
               "\nBatch No: " + batchNumber +
               "\nQuantity: " + quantity +
               "\nExpiry Date: " + expiryDate +
               "\n--------------------------";
    }
}
