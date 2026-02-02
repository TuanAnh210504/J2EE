package com.example.Bai2.Service;

import com.example.Bai2.Model.Book;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private List<Book> books = new ArrayList<>();
    private int nextId = 4;
    @PostConstruct
    public void initBooks() {
        books.add(new Book(1, "Lập trình Java cơ bản", "Nguyễn Văn A"));
        books.add(new Book(2, "Spring Boot cho người mới", "Trần Văn B"));
        books.add(new Book(3, "Cấu trúc dữ liệu & Giải thuật", "Lê Văn C"));
    }
    public List<Book> getAllBooks() {
        return books;
    }
    public Optional <Book> getBookById(int id) {
        return books.stream().filter(b -> b.getId() == id).findFirst();
    }
    public void addBook(Book book) {
        book.setId(nextId++);
        books.add(book);
    }
    public void updateBook(Book updatedBook) {
        books.stream()
                .filter(book -> book.getId() == updatedBook.getId() )
                .findFirst()
                .ifPresent(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                });
    }
    public void deleteBook(int id) {
        books.removeIf(book -> book.getId() == id);
    }
}
