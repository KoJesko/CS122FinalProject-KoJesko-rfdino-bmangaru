import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;

// Interface for tax calculation behavior
interface Taxable {
    BigDecimal calculateTax();

    BigDecimal calculateTaxRecursive(BigDecimal income);
}

// Abstract base class for a person
abstract class Person {
    protected String name;
    protected BigDecimal income;

    public Person(String name, BigDecimal income) {
        this.name = name;
        this.income = income;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getIncome() {
        return income;
    }

    // Abstract method to be implemented by subclasses
    public abstract BigDecimal calculateTax();

    public abstract BigDecimal calculateTaxRecursive(BigDecimal income);
}

// Class representing a taxpayer, extending Person and implementing Taxable
class Taxpayer extends Person implements Taxable {
    private BigDecimal deductions;
    private String filingStatus;

    // Tax brackets for different filing statuses using a 2D array
    private static final Map<String, BigDecimal[]> TAX_BRACKETS = new HashMap<>();

    static {
        // Single filer tax brackets and rates
        TAX_BRACKETS.put("single", new BigDecimal[] {
                BigDecimal.ZERO, new BigDecimal("8500"), new BigDecimal("11700"),
                new BigDecimal("13900"), new BigDecimal("80650"), new BigDecimal("215400"),
                new BigDecimal("1077550"), new BigDecimal("5000000"),
                new BigDecimal("25000000"),
                new BigDecimal(
                        "99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999"),
        });

        // Joint filer tax brackets and rates
        TAX_BRACKETS.put("joint", new BigDecimal[] {
                BigDecimal.ZERO, new BigDecimal("17150"), new BigDecimal("23600"),
                new BigDecimal("27900"), new BigDecimal("161550"), new BigDecimal("323200"),
                new BigDecimal("2155350"), new BigDecimal("5000000"),
                new BigDecimal("25000000"),
                new BigDecimal(
                        "99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999"),
        });

        // Head of household filer tax brackets and rates
        TAX_BRACKETS.put("head of household", new BigDecimal[] {
                BigDecimal.ZERO, new BigDecimal("12800"), new BigDecimal("17650"),
                new BigDecimal("20900"), new BigDecimal("107650"), new BigDecimal("269300"),
                new BigDecimal("1616450"), new BigDecimal("5000000"),
                new BigDecimal("25000000"),
                new BigDecimal(
                        "99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999"),
        });
    }

    public Taxpayer(String name, BigDecimal income, BigDecimal deductions, String filingStatus) {
        super(name, income);
        this.deductions = deductions;
        this.filingStatus = filingStatus.toLowerCase();
    }

    // Calculate tax based on tax brackets and filing status
    @Override
    public BigDecimal calculateTax() {
        BigDecimal[] brackets = TAX_BRACKETS.get(filingStatus);
        BigDecimal taxableIncome = income.subtract(deductions);
        BigDecimal tax = BigDecimal.ZERO;

        // Calculate tax using tax brackets and rates
        for (int i = 0; i < brackets.length - 1; i++) {
            if (taxableIncome.compareTo(brackets[i + 1]) <= 0) {
                tax = tax.add(taxableIncome.subtract(brackets[i]).multiply(getTaxRate(i)));
                break;
            } else {
                tax = tax.add(brackets[i + 1].subtract(brackets[i]).multiply(getTaxRate(i)));
            }
        }

        return tax;
    }

    // Recursive version of tax calculation
    @Override
    public BigDecimal calculateTaxRecursive(BigDecimal income) {
        BigDecimal[] brackets = TAX_BRACKETS.get(filingStatus);
        BigDecimal[] rates = TAX_BRACKETS.get(filingStatus + "_rates");
        BigDecimal tax = BigDecimal.ZERO;

        for (int i = 0; i < brackets.length - 1; i++) {
            if (income.compareTo(brackets[i + 1]) <= 0) {
                BigDecimal bracketTax = income.subtract(brackets[i]).multiply(rates[i]);
                BigDecimal recursiveTax = calculateTaxRecursive(brackets[i]);
                return bracketTax.add(recursiveTax);
            }
        }

        return tax; // Default return if no bracket matches (should not reach here)
    }

    private BigDecimal getTaxRate(int index) {
        BigDecimal[] rates = getTaxRates(filingStatus);
        return rates[index];
    }

    // Helper method to get tax rate based on bracket index
    private BigDecimal[] getTaxRates(String filingStatus) {
        switch (filingStatus) {
            case "single":
                return new BigDecimal[] {
                        new BigDecimal("0.04"), new BigDecimal("0.045"), new BigDecimal("0.0525"),
                        new BigDecimal("0.055"), new BigDecimal("0.06"), new BigDecimal("0.0685"),
                        new BigDecimal("0.0965"), new BigDecimal("0.103"), new BigDecimal("0.109"),
                        new BigDecimal("0.109")
                };
            case "joint":
                return new BigDecimal[] {
                        new BigDecimal("0.04"), new BigDecimal("0.045"), new BigDecimal("0.0525"),
                        new BigDecimal("0.055"), new BigDecimal("0.06"), new BigDecimal("0.0685"),
                        new BigDecimal("0.0965"), new BigDecimal("0.103"), new BigDecimal("0.109"),
                        new BigDecimal("0.109")
                };
            case "head of household":
                return new BigDecimal[] {
                        new BigDecimal("0.04"), new BigDecimal("0.045"), new BigDecimal("0.0525"),
                        new BigDecimal("0.055"), new BigDecimal("0.06"), new BigDecimal("0.0685"),
                        new BigDecimal("0.0965"), new BigDecimal("0.103"), new BigDecimal("0.109"),
                        new BigDecimal("0.109")
                };
            default:
                return new BigDecimal[0]; // Default to empty array if status is not recognized
        }
    }
}

public class TaxFilingSimulation {
    int i = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Taxable> taxpayers = new ArrayList<>();

        // Prompt user to enter taxpayer details
        String choice;
        do {
            try {
                System.out.print("Enter taxpayer name: ");
                String name = scanner.nextLine();

                System.out.print("Enter total income for the year: ");
                BigDecimal income = new BigDecimal(scanner.nextLine());

                System.out.print("Enter total deductions: ");
                BigDecimal deductions = new BigDecimal(scanner.nextLine());

                System.out.print("Enter filing status (single/joint/head of household): ");
                int j = 0;
                String filingStatus = "";
                while (j == 0) {
                    {
                        filingStatus = scanner.nextLine();
                        if (!filingStatus.equalsIgnoreCase("single") && !filingStatus.equalsIgnoreCase("joint")
                                && !filingStatus.equalsIgnoreCase("head of household")) {
                            System.out.println(
                                    "Invalid input. Please enter a valid filing status. (single/joint/head of household)");
                        } else {
                            j++;
                        }
                    }
                }
                // Create a new taxpayer object
                Taxable taxpayer = new Taxpayer(name, income, deductions, filingStatus);
                taxpayers.add(taxpayer);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }

            System.out.print("Do you want to enter another taxpayer? (yes/no): ");
            choice = scanner.nextLine();
        } while (choice.equalsIgnoreCase("yes"));

        // Calculate and display tax for each taxpayer
        System.out.println("\nTax Filing Result:");
        for (Taxable taxpayer : taxpayers) {
            try {
                BigDecimal tax = taxpayer.calculateTax();
                System.out.println("Taxpayer: " + ((Person) taxpayer).getName());
                System.out.println("Tax Owed: $" + tax);
            } catch (Exception e) {
                System.out.println("Error calculating tax for " + ((Person) taxpayer).getName() + ".");
            }
        }

        scanner.close();
    }
}