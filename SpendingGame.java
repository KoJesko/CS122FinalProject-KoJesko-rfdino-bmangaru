import java.util.*;
import java.util.Scanner;;

public class SpendingGame {
    public static void main(String[] args) {
        double balance = 15000000; // Ivan Seidenberg's donation
        boolean teslaPurchased = false;
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> purchases = new HashMap<>();
        System.out.println("Spend Ivan Seidenberg's $15 Million donation to Pace University recklessly!");
        while (balance > 0) {
            System.out.println("Balance: $" + balance);
            System.out.println("1. Apples ($1)\n2. Candies ($2)\n3. Kessel Lunch ($10)\n4. Steam Gaming Gift Cards ($20)\n5. Palworld copy ($25)\n6. Stocks ($50)\n7. Donate to Charity ($100)\n8. Expensive Dinner ($500)\n9. iPhone X ($1000)\n10. iPhone 15 Pro Max ($1500)\n11. Samsung Galaxy ZFold ($2000)\n12. Gaming Beast ($5000)\n13. 80000 Robux ($10000)\n14. Used Subaru Forester ($12500)\n15. FSD Software ($15000)\n16. Tesla Model Y ($50000)\n17. Maybach ($250000)\n18. New home ($800000)\n19. Mansion ($5000000)");
            System.out.println("Enter the number of the item you want to purchase:");

            int choice = scanner.nextInt();
            double cost = 0;
            String item = "";

            switch (choice) {
                case 1: cost = 1; item = "Apples"; break;
                case 2: cost = 2; item = "Candies"; break;
                case 3: cost = 10; item = "Kessel Lunch"; break;
                case 4: cost = 20; item = "Steam Gaming Gift Cards"; break;
                case 5: cost = 25; item = "Palworld copy"; break;
                case 6: cost = 50; item = "Stocks"; break;
                case 7: cost = 100; item = "Donate to Charity"; break;
                case 8: cost = 500; item = "Expensive Dinner"; break;
                case 9: cost = 1000; item = "iPhone X"; break;
                case 10: cost = 1500; item = "iPhone 15 Pro Max"; break;
                case 11: cost = 2000; item = "Samsung Galaxy ZFold"; break;
                case 12: cost = 5000; item = "Gaming Beast"; break;
                case 13: cost = 10000; item = "80000 Robux"; break;
                case 14: cost = 12500; item = "Used Subaru Forester"; break;
                case 15: 
                    if (teslaPurchased) {
                        cost = 15000; 
                        item = "FSD Software";
                    } else {
                        System.out.println("You need to purchase a Tesla Model Y first.");
                        continue;
                    }
                    break;
                case 16: 
                    cost = 50000; 
                    teslaPurchased = true;
                    item = "Tesla Model Y";
                    break;
                case 17: cost = 250000; item = "Maybach"; break;
                case 18: cost = 800000; item = "New home"; break;
                case 19: cost = 5000000; item = "Mansion"; break;
                default: System.out.println("Invalid choice. Try again."); continue;
            }

            if (cost > balance) {
                System.out.println("You don't have enough money for this purchase. Try something else.");
            } else {
                balance -= cost;
                purchases.put(item, purchases.getOrDefault(item, 0) + 1);
                System.out.println("Purchase successful!");
            }
        }

        System.out.println("You've spent all the money. Game over.");
        System.out.println("Here's a summary of your purchases:");
        for (Map.Entry<String, Integer> entry : purchases.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        scanner.close();
    }
}