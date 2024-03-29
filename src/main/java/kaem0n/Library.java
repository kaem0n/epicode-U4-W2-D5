package kaem0n;

import com.github.javafaker.Company;
import com.github.javafaker.Faker;
import kaem0n.entities.Book;
import kaem0n.entities.Magazine;
import kaem0n.entities.PrintedWork;
import kaem0n.enums.Genre;
import kaem0n.enums.PublicationSchedule;
import kaem0n.exceptions.InvalidISBNCodeException;
import kaem0n.exceptions.InvalidPublicationYearException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

public class Library {
    public static void main(String[] args) {
        File file = new File("src/data.txt");
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
                case "2" -> searchByISBN(catalog, sc);
                case "3" -> searchByPublicationYear(catalog, sc);
                case "4" -> searchByAuthor(catalog, sc);
                case "5" -> addItemToCatalog(catalog, sc);
                case "6" -> deleteItemFromCatalog(catalog, sc);
                case "7" -> saveData(catalog, file);
                case "8" -> loadData(file);
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
            default -> System.err.println("Invalid input.");
        }
    }

    public static void deleteItemFromCatalog(List<PrintedWork> catalog, Scanner sc) {
        System.out.println();
        System.out.println("ENTER A VALID ISBN CODE:");
        while (true) {
            try {
                int isbn = Integer.parseInt(sc.nextLine());
                if (isbn < 100000000 || isbn > 1000000000) {
                    throw new InvalidISBNCodeException("Error: invalid format; ISBN is composed of 9 integer numbers");
                }
                PrintedWork[] itemToRemove = new PrintedWork[1];
                catalog.forEach(item -> {
                    if (isbn == item.getISBNCode()) itemToRemove[0] = item;
                });
                if (itemToRemove[0] != null) {
                    System.out.println("REMOVED ITEM:");
                    System.out.println(itemToRemove[0]);
                    catalog.remove(itemToRemove[0]);
                } else {
                    System.err.println("Error: item not found.");
                }
                break;
            } catch (NumberFormatException ex) {
                System.err.println("Error: not a number.");
            } catch (InvalidISBNCodeException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public static void searchByISBN(List<PrintedWork> catalog, Scanner sc) {
        System.out.println();
        System.out.println("ENTER A VALID ISBN CODE:");
        while (true) {
            try {
                int isbn = Integer.parseInt(sc.nextLine());
                if (isbn < 100000000 || isbn > 1000000000) {
                    throw new InvalidISBNCodeException("Error: invalid format; ISBN is composed of 9 integer numbers");
                }
                PrintedWork[] itemFound = new PrintedWork[1];
                catalog.forEach(item -> {
                    if (isbn == item.getISBNCode()) itemFound[0] = item;
                });
                if (itemFound[0] != null) {
                    if (itemFound[0] instanceof Book) {
                        System.out.println("BOOK FOUND WITH ISBN " + isbn + ":");
                        System.out.println("\"" + itemFound[0].getTitle() + "\" (" + ((Book) itemFound[0]).getGenre() + " - " + itemFound[0].getPublicationYear() + ") by " + ((Book) itemFound[0]).getAuthor());
                    } else if (itemFound[0] instanceof Magazine) {
                        System.out.println("MAGAZINE FOUND WITH ISBN " + isbn + ":");
                        System.out.println("\"" + itemFound[0].getTitle() + "\" (published " + ((Magazine) itemFound[0]).getPublicationSchedule() + " since " + itemFound[0].getPublicationYear() + ")");
                    }
                } else {
                    System.err.println("Error: item not found.");
                }
                break;
            } catch (NumberFormatException ex) {
                System.err.println("Error: not a number.");
            } catch (InvalidISBNCodeException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public static void searchByPublicationYear(List<PrintedWork> catalog, Scanner sc) {
        System.out.println();
        System.out.println("ENTER A YEAR:");
        while (true) {
            try {
                int year = Integer.parseInt(sc.nextLine());
                if (year < 1900 || year > LocalDate.now().getYear()) {
                    throw new InvalidPublicationYearException("Error: year must be between 1900 and " + LocalDate.now().getYear() + ".");
                } else {
                    List<PrintedWork> itemsByYear = catalog.stream().filter(item -> item.getPublicationYear() == year).toList();
                    if (itemsByYear.isEmpty()) System.err.println("No items found published in year " + year);
                    else {
                        System.out.println(itemsByYear.size() + " ITEMS FOUND FOR YEAR " + year + ":");
                        itemsByYear.forEach(item -> System.out.println("- " + item));
                    }
                }
                break;
            } catch (InvalidPublicationYearException ex) {
                System.err.println(ex.getMessage());
            } catch (NumberFormatException ex) {
                System.err.println("Error: not a number.");
            }
        }
    }

    public static void searchByAuthor(List<PrintedWork> catalog, Scanner sc) {
        System.out.println();
        System.out.println("ENTER THE NAME OF THE AUTHOR:");
        String author = sc.nextLine();
        List<Book> booksByAuthor = catalog.stream().filter(item -> item instanceof Book).map(item -> (Book) item)
                .filter(item -> Objects.equals(item.getAuthor(), author)).toList();
        if (booksByAuthor.isEmpty()) System.err.println("No books were found by author " + author);
        else {
            System.out.println(booksByAuthor.size() + " BOOKS FOUND BY AUTHOR " + author + ":");
            booksByAuthor.forEach(item -> System.out.println("- " + item));
        }
    }

    public static void saveData(List<PrintedWork> catalog, File file) {
        try {
            FileUtils.writeStringToFile(file, "Catalog content (updated: " + new Date() + "):" + System.lineSeparator(), StandardCharsets.UTF_8);
            catalog.forEach(item -> {
                try {
                    FileUtils.writeStringToFile(file, "- " + item + System.lineSeparator(), StandardCharsets.UTF_8, true);
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                    ex.printStackTrace();
                }
            });
            System.out.println();
            System.out.println("DATA SAVED SUCCESSFULLY.");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void loadData(File file) {
        try {
            String fileContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            System.out.println();
            System.out.println("LOADED FILE DATA:");
            System.out.println(fileContent);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
