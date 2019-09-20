package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.Book;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface BookService {
    ArrayList<Book> findAll(Pageable pageable);

    Book update(Book book, long id);

    void delete(long id);

    void addBookAuthorCombo(long bookid, long authorid);
}
