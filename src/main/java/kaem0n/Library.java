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

        catalog.forEach(item -> System.out.println("- " + item));
    }
}
