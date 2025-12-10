package com.kloudly;

public class NestedClassDemo {

    public static void main(String[] args) {
        anonymousClassExample1();
        anonymousClassExample2();
        localClassExample();
        innerClassExample();
        staticNestedClassExample();

    }

    static void anonymousClassExample2(){
        new PayrollEngine().runPayroll();
    }
    static void anonymousClassExample1(){
        NotificationService service = new NotificationService();
        service.sendAsyncNotification("Your payroll has been processed!");
    }
    static void localClassExample(){
        BankService service = new BankService();

        service.processTransfer("1234567890", "0987654321", 1200);   // Valid transfer
        service.processTransfer("abc", "xyz", 100);
    }
    static void innerClassExample(){
        //Creating the outer class instance
        EmployeePayroll payroll = new EmployeePayroll("Noël Kamphoa", 5000);
        payroll.setBonus(800);
        payroll.setDeductions(300);

        // Creating the inner class instance
        EmployeePayroll.PayslipCalculator calc = payroll.new PayslipCalculator();

        System.out.println(calc.generatePayslip());
    }

    static void staticNestedClassExample(){
        ShoppingCart.CartItem item = new ShoppingCart.CartItem("P-001", 2, 19.99);

        item.increaseQuantity(1);
        item.updateUnitPrice(17.99);
        item.decreaseQuantity(1);

        System.out.println(item);
    }
}

//Outer class
class ShoppingCart {
    // -------------------------------------------------
    // Stateful Static Nested Class: Represents a Cart Item
    // -------------------------------------------------
    public static class CartItem {

        private final String productId;
        private int quantity;
        private double unitPrice;
        private double totalPrice;

        public CartItem(String productId, int quantity, double unitPrice) {
            this.productId = productId;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            recalculateTotal();
        }

        public void increaseQuantity(int amount) {
            quantity += amount;
            recalculateTotal();
        }

        public void decreaseQuantity(int amount) {
            if (quantity - amount >= 1) {
                quantity -= amount;
            }
            recalculateTotal();
        }

        public void updateUnitPrice(double newPrice) {
            this.unitPrice = newPrice;
            recalculateTotal();
        }

        private void recalculateTotal() {
            this.totalPrice = quantity * unitPrice;
        }

        @Override
        public String toString() {
            return "CartItem{" +
                    "productId='" + productId + '\'' +
                    ", quantity=" + quantity +
                    ", unitPrice=" + unitPrice +
                    ", totalPrice=" + totalPrice +
                    '}';
        }
    }


}

//Outer class
class EmployeePayroll {
    private final String employeeName;
    private final double baseSalary;
    private double bonus;
    private double deductions;

    public EmployeePayroll(String employeeName, double baseSalary) {
        this.employeeName = employeeName;
        this.baseSalary = baseSalary;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }

    // --------------------------------------------------
    // Inner Class: Payslip Calculator
    // --------------------------------------------------
    public class PayslipCalculator {

        public double calculateNetSalary() {
            // Full access to outer instance fields
            return baseSalary + bonus - deductions;
        }

        public String generatePayslip() {
            return "Payslip for " + employeeName + "\n" +
                    "Base Salary: " + baseSalary + "\n" +
                    "Bonus: " + bonus + "\n" +
                    "Deductions: " + deductions + "\n" +
                    "Net Salary: " + calculateNetSalary();
        }
    }
}

//Outer class
class BankService {

    public boolean processTransfer(String fromAccount, String toAccount, double amount) {

        // --------------------------------------------
        // Local Class: used ONLY inside this method
        // --------------------------------------------
        class TransferValidator {

            boolean isAccountValid(String acc) {
                return acc != null && acc.length() == 10;
            }

            boolean isAmountValid(double amt) {
                return amt > 0 && amt <= 5000; // business rule
            }

            boolean validate() {
                return isAccountValid(fromAccount)
                        && isAccountValid(toAccount)
                        && isAmountValid(amount);
            }
        }

        // Create validator inside method
        TransferValidator validator = new TransferValidator();

        if (!validator.validate()) {
            System.out.println("Transfer rejected: validation failed.");
            return false;
        }

        // Continue with the transfer (removed actual logic for brevity)
        System.out.println("Transfer processed successfully.");
        return true;
    }
}
//Outer class
class NotificationService {

    public void sendAsyncNotification(String message) {

        // -----------------------------------------------------
        // Anonymous class implementing Runnable
        // -----------------------------------------------------
        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println("Sending notification: " + message);
                // Here we are simulating the sending logic
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println("Notification sent successfully.");
            }
        };

        // Run the task in a background thread
        new Thread(task).start();
    }
}

abstract class PayrollEventHandler {

    public abstract void onStart();
    public abstract void onProcess();
    public abstract void onComplete();

    public void log(String message) {
        System.out.println("[LOG] " + message);
    }
}

class PayrollEngine {
    public void runPayroll() {
        PayrollEventHandler handler = new PayrollEventHandler() {
            @Override
            public void onStart() {
                log("Payroll started.");
            }
            @Override
            public void onProcess() {
                log("Processing employee data...");
            }
            @Override
            public void onComplete() {
                log("Payroll completed successfully.");
            }
        };
        // Simulate payroll workflow
        handler.onStart();
        handler.onProcess();
        handler.onComplete();
    }
}



