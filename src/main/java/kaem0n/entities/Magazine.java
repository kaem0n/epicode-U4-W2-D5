package kaem0n.entities;

import kaem0n.enums.PublicationSchedule;
import kaem0n.exceptions.InvalidPageNumberException;
import kaem0n.exceptions.InvalidPublicationYearException;

import java.time.LocalDate;
import java.util.Scanner;

public class Magazine extends PrintedWork {
    private final PublicationSchedule publicationSchedule;

    public Magazine(String title, int publicationYear, int pages, PublicationSchedule publicationSchedule) {
        super(title, publicationYear, pages);
        this.publicationSchedule = publicationSchedule;
    }

    public PublicationSchedule getPublicationSchedule() {
        return publicationSchedule;
    }

    public static Magazine createMagazine(Scanner sc) {
        System.out.println();
        System.out.println("ENTER THE TITLE:");
        String title = sc.nextLine();
        System.out.println("ENTER THE PUBLICATION SCHEDULE (Available: weekly, monthly, semiannually):");
        PublicationSchedule schedule;
        while (true) {
            try {
                schedule = PublicationSchedule.valueOf((sc.nextLine().toUpperCase()));
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
                if (year < 1950 || year > LocalDate.now().getYear()) {
                    throw new InvalidPublicationYearException("Error: year must be between 1950 and " + LocalDate.now().getYear() + ".");
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
                if (pages < 15 || pages > 60) {
                    throw new InvalidPageNumberException("Error: page number must be between 15 and 60.");
                } else break;
            } catch (InvalidPageNumberException ex) {
                System.err.println(ex.getMessage());
            } catch (NumberFormatException ex) {
                System.err.println("Error: not a number.");
            }
        }
        return new Magazine(title, year, pages, schedule);
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "ISBNCode=" + ISBNCode +
                ", title='" + title + '\'' +
                ", publicationYear=" + publicationYear +
                ", pages=" + pages +
                ", publicationSchedule=" + publicationSchedule +
                '}';
    }
}
