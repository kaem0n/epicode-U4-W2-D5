package kaem0n.entities;

import kaem0n.enums.PublicationSchedule;

public class Magazine extends PrintedWork {
    private final PublicationSchedule publicationSchedule;

    public Magazine(String title, int publicationYear, int pages, PublicationSchedule publicationSchedule) {
        super(title, publicationYear, pages);
        this.publicationSchedule = publicationSchedule;
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
