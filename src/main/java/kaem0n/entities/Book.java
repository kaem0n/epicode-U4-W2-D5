package kaem0n.entities;

public class Book extends PrintedWork {
    private final String author;
    private final String genre;

    public Book(String title, String author, String genre, int publicationYear, int pages) {
        super(title, publicationYear, pages);
        this.author = author;
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
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
