import java.util.ArrayList;
import java.util.Scanner;

// Interface for tax calculation behavior
interface Taxable {
    double calculateTax();
}

// Abstract base class for a person
abstract class Person {
    protected String name;
    protected double income;

    public Person(String name, double income) {
        this.name = name;
        this.income = income;
    }

    public String getName() {
        return name;
    }

    public double getIncome() {
        return income;
    }

    // Abstract method to be implemented by subclasses
    public abstract double calculateTax();
}

// Class representing a taxpayer, extending Person and implementing Taxable
class Taxpayer extends Person implements Taxable {
    private double deductions;
    private String filingStatus;

    public Taxpayer(String name, double income, double deductions, String filingStatus) {
        super(name, income);
        this.deductions = deductions;
        this.filingStatus = filingStatus;
    }

    // Calculate tax based on tax brackets (single, joint, or head of household)
    @Override
    public double calculateTax() {
        double taxableIncome = getIncome() - deductions;
        double tax = 0.0;

        switch (filingStatus.toLowerCase()) {
            case "single":
                tax = calculateSingleTax(taxableIncome);
                break;
            case "joint":
                tax = calculateJointTax(taxableIncome);
                break;
            case "head of household":
                tax = calculateHeadOfHouseholdTax(taxableIncome);
                break;
            default:
                System.out.println("Invalid filing status. Using single filer tax brackets.");
                tax = calculateSingleTax(taxableIncome);
                break;
        }

        return tax;
    }

    // Calculate tax for single filers
    private double calculateSingleTax(double taxableIncome) {
        double tax = 0.0;

        if (taxableIncome <= 12800) {
            tax = taxableIncome * 0.04; 
        } else if (taxableIncome <= 17650) {
            tax = 12800 * 0.04 + (taxableIncome - 12800) * 0.045; 
        } else if (taxableIncome <= 20900) {
            tax = 12800 * 0.04 + (17650 - 12800) * 0.045 + (taxableIncome - 17650) * 0.0525; 
        } else if (taxableIncome <= 107650) {
            tax = 12800 * 0.04 + (17650 - 12800) * 0.045 + (20900 - 17650) * 0.0525 + (taxableIncome - 20900) * 0.055; 
        } else if (taxableIncome <= 269300) {
            tax = 12800 * 0.04 + (17650 - 12800) * 0.045 + (20900 - 17650) * 0.0525 + (107650 - 20900) * 0.055
                    + (taxableIncome - 107650) * 0.06;
        } else if (taxableIncome <= 1616450) {
            tax = 12800 * 0.04 + (17650 - 12800) * 0.045 + (20900 - 17650) * 0.0525 + (107650 - 20900) * 0.055
                    + (269300 - 107650) * 0.06 + (taxableIncome - 269300) * 0.0685;
        } else if (taxableIncome <= 5000000) {
            tax = 12800 * 0.04 + (17650 - 12800) * 0.045 + (20900 - 17650) * 0.0525 + (107650 - 20900) * 0.055
                    + (269300 - 107650) * 0.06 + (1616450 - 269300) * 0.0685 + (taxableIncome - 1616450) * 0.0965;
        } else if (taxableIncome <= 25000000) {
            tax = 12800 * 0.04 + (17650 - 12800) * 0.045 + (20900 - 17650) * 0.0525 + (107650 - 20900) * 0.055
                    + (269300 - 107650) * 0.06 + (1616450 - 269300) * 0.0685 + (5000000 - 1616450) * 0.103
                    + (taxableIncome - 5000000) * 0.103;
        } else {
            tax = 12800 * 0.04 + (17650 - 12800) * 0.045 + (20900 - 17650) * 0.0525 + (107650 - 20900) * 0.055
                    + (269300 - 107650) * 0.06 + (1616450 - 269300) * 0.0685 + (5000000 - 1616450) * 0.103
                    + (25000000 - 5000000) * 0.109 + (taxableIncome - 25000000) * 0.109;
        }

        return tax;
    }

    // Calculate tax for joint filers
    private double calculateJointTax(double taxableIncome) {
        double tax = 0.0;

        if (taxableIncome <= 17150) {
            tax = taxableIncome * 0.04; 
        } else if (taxableIncome <= 23600) {
            tax = 17150 * 0.04 + (taxableIncome - 17150) * 0.045; 
        } else if (taxableIncome <= 27900) {
            tax = 17150 * 0.04 + (23600 - 17150) * 0.045 + (taxableIncome - 23600) * 0.0525; 
        } else if (taxableIncome <= 161550) {
            tax = 17150 * 0.04 + (23600 - 17150) * 0.045 + (27900 - 23600) * 0.0525 + (taxableIncome - 27900) * 0.055; 
        } else if (taxableIncome <= 323200) {
            tax = 17150 * 0.04 + (23600 - 17150) * 0.045 + (27900 - 23600) * 0.0525 + (161550 - 27900) * 0.055
                    + (taxableIncome - 161550) * 0.06;
        } else if (taxableIncome <= 2155350) {
            tax = 17150 * 0.04 + (23600 - 17150) * 0.045 + (27900 - 23600) * 0.0525 + (161550 - 27900) * 0.055
                    + (323200 - 161550) * 0.06 + (taxableIncome - 323200) * 0.0685;
        } else if (taxableIncome <= 5000000) {
            tax = 17150 * 0.04 + (23600 - 17150) * 0.045 + (27900 - 23600) * 0.0525 + (161550 - 27900) * 0.055
                    + (323200 - 161550) * 0.06 + (2155350 - 323200) * 0.0685 + (taxableIncome - 2155350) * 0.0965;
        } else if (taxableIncome <= 25000000) {
            tax = 17150 * 0.04 + (23600 - 17150) * 0.045 + (27900 - 23600) * 0.0525 + (161550 - 27900) * 0.055
                    + (323200 - 161550) * 0.06 + (2155350 - 323200) * 0.0685 + (5000000 - 2155350) * 0.103
                    + (taxableIncome - 5000000) * 0.103;
        } else {
            tax = 17150 * 0.04 + (23600 - 17150) * 0.045 + (27900 - 23600) * 0.0525 + (161550 - 27900) * 0.055
                    + (323200 - 161550) * 0.06 + (2155350 - 323200) * 0.0685 + (5000000 - 2155350) * 0.103
                    + (25000000 - 5000000) * 0.109 + (taxableIncome - 25000000) * 0.109;
        }

        return tax;
    }

    // Calculate tax for head of household filers
    private double calculateHeadOfHouseholdTax(double taxableIncome) {
        double tax = 0.0;

        if (taxableIncome <= 12800) {
            tax = taxableIncome * 0.04; 
        } else if (taxableIncome <= 17650) {
            tax = 12800 * 0.04 + (taxableIncome - 12800) * 0.045; 
        } else if (taxableIncome <= 20900) {
            tax = 12800 * 0.04 + (17650 - 12800) * 0.045 + (taxableIncome - 17650) * 0.0525; 
        } else if (taxableIncome <= 107650) {
            tax = 12800 * 0.04 + (17650 - 12800) * 0.045 + (20900 - 17650) * 0.0525 + (taxableIncome - 20900) * 0.055; 
        } else if (taxableIncome <= 269300) {
            tax = 12800 * 0.04 + (17650 - 12800) * 0.045 + (20900 - 17650) * 0.0525 + (107650 - 20900) * 0.055
                    + (taxableIncome - 107650) * 0.06;
        } else if (taxableIncome <= 1616450) {
            tax = 12800 * 0.04 + (17650 - 12800) * 0.045 + (20900 - 17650) * 0.0525 + (107650 - 20900) * 0.055
                    + (269300 - 107650) * 0.06 + (taxableIncome - 269300) * 0.0685;
        } else if (taxableIncome <= 5000000) {
            tax = 12800 * 0.04 + (17650 - 12800) * 0.045 + (20900 - 17650) * 0.0525 + (107650 - 20900) * 0.055
                    + (269300 - 107650) * 0.06 + (1616450 - 269300) * 0.0685 + (taxableIncome - 1616450) * 0.0965;
        } else if (taxableIncome <= 25000000) {
            tax = 12800 * 0.04 + (17650 - 12800) * 0.045 + (20900 - 17650) * 0.0525 + (107650 - 20900) * 0.055
                    + (269300 - 107650) * 0.06 + (1616450 - 269300) * 0.0685 + (5000000 - 1616450) * 0.103
                    + (taxableIncome - 5000000) * 0.103;
        } else {
            tax = 12800 * 0.04 + (17650 - 12800) * 0.045 + (20900 - 17650) * 0.0525 + (107650 - 20900) * 0.055
                    + (269300 - 107650) * 0.06 + (1616450 - 269300) * 0.0685 + (5000000 - 1616450) * 0.103
                    + (25000000 - 5000000) * 0.109 + (taxableIncome - 25000000) * 0.109;
        }

        return tax;
    }
}

public class TaxFilingSimulation {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Taxable> taxpayers = new ArrayList<>();

        // Prompt user to enter taxpayer details
        String choice;
        do {
            System.out.print("Enter taxpayer name: ");
            String name = scanner.nextLine();

            System.out.print("Enter total income for the year: ");
            double income = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter total deductions: ");
            double deductions = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter filing status (single/joint/head of household): ");
            String filingStatus = scanner.nextLine();

            // Create a new taxpayer object
            Taxable taxpayer = new Taxpayer(name, income, deductions, filingStatus);
            taxpayers.add(taxpayer);

            System.out.print("Do you want to enter another taxpayer? (yes/no): ");
            choice = scanner.nextLine();
        } while (choice.equalsIgnoreCase("yes"));

        // Calculate and display tax for each taxpayer
        System.out.println("\nTax Filing Result:");
        for (Taxable taxpayer : taxpayers) {
            double tax = taxpayer.calculateTax();
            if (taxpayer instanceof Person) {
                System.out.println("Taxpayer: " + ((Person) taxpayer).getName());
            }
            System.out.println("Tax Owed: $" + tax);
        }

        scanner.close();
    }
}