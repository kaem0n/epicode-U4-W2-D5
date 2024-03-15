package kaem0n;

import com.github.javafaker.Company;
import com.github.javafaker.Faker;
import kaem0n.entities.Book;
import kaem0n.entities.Magazine;
import kaem0n.entities.PrintedWork;
import kaem0n.enums.PublicationSchedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Library {
    public static void main(String[] args) {
        Faker faker = new Faker();

        List<PrintedWork> catalog = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            com.github.javafaker.Book bookData = faker.book();
            int rndmYear = new Random().nextInt(1900, 2024);
            int rndmPages = new Random().nextInt(100, 801);
            catalog.add(new Book(bookData.title(), bookData.author(), bookData.genre(), rndmYear, rndmPages));
        }
        for (int i = 0; i < 10; i++) {
            PublicationSchedule[] schedules = {PublicationSchedule.WEEKLY, PublicationSchedule.MONTHLY, PublicationSchedule.SEMIANNUALLY };
            Company magazineData = faker.company();
            int rndmYear = new Random().nextInt(1950, 2024);
            int rndmPages = new Random().nextInt(15, 60);
            int rndmIndex = new Random().nextInt(0, schedules.length);
            catalog.add(new Magazine(magazineData.industry(), rndmYear, rndmPages, schedules[rndmIndex]));
        }

        handleCatalog(catalog);
    }

    public static void handleCatalog(List<PrintedWork> catalog) {
        Scanner sc = new Scanner(System.in);

        loop: while (true) {
            System.out.println();
            System.out.println("CHOOSE AN OPTION (type option number and press Enter):");
            System.out.println("1. Browse all the catalog");
            System.out.println("2. Search an item by ISBN Code");
            System.out.println("3. Search items by publication year");
            System.out.println("4. Search books by author");
            System.out.println("5. Add a new item to catalog");
            System.out.println("6. Remove an item from catalog");
            System.out.println("7. Save data on disk drive");
            System.out.println("8. Load data from disk drive");
            System.out.println("0. Exit");
            String input = sc.nextLine();
            switch (input) {
                case "0" -> {
                    sc.close();
                    break loop;
                }
                case "1" -> {
                    System.out.println();
                    System.out.println("There are " + catalog.size() + " items in the catalog:");
                    catalog.forEach(item -> System.out.println("- " + item));
                }
                default -> System.err.println("Invalid input. Try again.");
            }
        }
    }
}
