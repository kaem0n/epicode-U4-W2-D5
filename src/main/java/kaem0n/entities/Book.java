package kaem0n.entities;

import kaem0n.enums.Genre;
import kaem0n.exceptions.InvalidPageNumberException;
import kaem0n.exceptions.InvalidPublicationYearException;

import java.time.LocalDate;
import java.util.Scanner;

public class Book extends PrintedWork {
    private final String author;
    private final Genre genre;

    public Book(String title, String author, Genre genre, int publicationYear, int pages) {
        super(title, publicationYear, pages);
        this.author = author;
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }

    public static Book createBook(Scanner sc) {
        System.out.println();
        System.out.println("ENTER THE TITLE:");
        String title = sc.nextLine();
        System.out.println("ENTER THE AUTHOR'S NAME:");
        String author = sc.nextLine();
        System.out.println("ENTER THE GENRE (Available: Drama, Fantasy, Horror, Romance):");
        Genre genre;
        while (true) {
            try {
                genre = Genre.valueOf((sc.nextLine().toUpperCase()));
                break;
            } catch (IllegalArgumentException ex) {
                System.err.println("Error: genre not available.");
            }
        }
        System.out.println("ENTER PUBLICATION YEAR:");
        int year;
        while (true) {
            try {
                year = Integer.parseInt(sc.nextLine());
                if (year < 1900 || year > LocalDate.now().getYear()) {
                    throw new InvalidPublicationYearException("Error: year must be between 1900 and " + LocalDate.now().getYear() + ".");
                } else break;
            } catch (InvalidPublicationYearException ex) {
                System.err.println(ex.getMessage());
            } catch (NumberFormatException ex) {
                System.err.println("Error: not a number.");
            }
        }
        System.out.println("ENTER NUMBER OF PAGES:");
        int pages;
        while (true) {
            try {
                pages = Integer.parseInt(sc.nextLine());
                if (pages < 100 || pages > 800) {
                    throw new InvalidPageNumberException("Error: page number must be between 100 and 800.");
                } else break;
            } catch (InvalidPageNumberException ex) {
                System.err.println(ex.getMessage());
            } catch (NumberFormatException ex) {
                System.err.println("Error: not a number.");
            }
        }
        return new Book(title, author, genre, year, pages);
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBNCode=" + ISBNCode +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", publicationYear=" + publicationYear +
                ", pages=" + pages +
                '}';
    }
}
