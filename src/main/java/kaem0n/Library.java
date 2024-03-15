package kaem0n;

import com.github.javafaker.Company;
import com.github.javafaker.Faker;
import kaem0n.entities.Book;
import kaem0n.entities.Magazine;
import kaem0n.entities.PrintedWork;
import kaem0n.enums.Genre;
import kaem0n.enums.PublicationSchedule;

import java.sql.Array;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Library {
    public static void main(String[] args) {
        Faker faker = new Faker();

        List<PrintedWork> catalog = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Genre[] genres = {Genre.DRAMA, Genre.FANTASY, Genre.HORROR, Genre.ROMANCE};
            com.github.javafaker.Book bookData = faker.book();
            int rndmYear = new Random().nextInt(1900, LocalDate.now().getYear());
            int rndmPages = new Random().nextInt(100, 801);
            int rndmIndex = new Random().nextInt(0, genres.length);
            catalog.add(new Book(bookData.title(), bookData.author(), genres[rndmIndex], rndmYear, rndmPages));
        }
        for (int i = 0; i < 10; i++) {
            PublicationSchedule[] schedules = {PublicationSchedule.WEEKLY, PublicationSchedule.MONTHLY, PublicationSchedule.SEMIANNUALLY };
            Company magazineData = faker.company();
            int rndmYear = new Random().nextInt(1950, LocalDate.now().getYear());
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
                case "5" -> addItemToCatalog(catalog, sc);
                case "6" -> deleteItemFromCatalog(catalog, sc);
                default -> System.err.println("Invalid input. Try again.");
            }
        }
    }

    public static void addItemToCatalog(List<PrintedWork> catalog, Scanner sc) {
        System.out.println();
        System.out.println("WHAT KIND OF ITEM DO YOU WANT TO ADD?");
        System.out.println("1. Book");
        System.out.println("2. Magazine");
        String itemType = sc.nextLine();
        switch (itemType) {
            case "1" -> {
                Book newBook = Book.createBook(sc);
                System.out.println("New book added:");
                System.out.println(newBook);
                catalog.add(newBook);
            }
            case "2" -> {
                Magazine newMagazine = Magazine.createMagazine(sc);
                System.out.println("New magazine added:");
                System.out.println(newMagazine);
                catalog.add(newMagazine);
            }
        }
    }

    public static void deleteItemFromCatalog(List<PrintedWork> catalog, Scanner sc) {
        System.out.println();
        System.out.println("ENTER A VALID ISBN CODE:");
        while (true) {
            try {
                int isbn = Integer.parseInt(sc.nextLine());
                PrintedWork[] itemToRemove = new PrintedWork[1];
                catalog.forEach(item -> {
                    if (isbn == item.getISBNCode()) itemToRemove[0] = item;
                });
                if (itemToRemove[0] != null) {
                    System.out.println("ITEM FOUND:");
                    System.out.println(itemToRemove[0]);
                    catalog.remove(itemToRemove[0]);
                } else {
                    System.err.println("Error: item not found.");
                }
                break;
            } catch (NumberFormatException ex) {
                System.err.println("Error: not a number.");
            }
        }

    }
}
