package kaem0n.entities;

import java.util.Random;

public abstract class PrintedWork {
    protected final long ISBNCode;
    protected final String title;
    protected final int publicationYear;
    protected final int pages;

    protected PrintedWork(String title, int publicationYear, int pages) {
        this.ISBNCode = new Random().nextInt(100000000, 1000000000);
        this.title = title;
        this.publicationYear = publicationYear;
        this.pages = pages;
    }

    public long getISBNCode() {
        return ISBNCode;
    }

    public String getTitle() {
        return title;
    }

    public int getPublicationYear() {
        return publicationYear;
    }
}
