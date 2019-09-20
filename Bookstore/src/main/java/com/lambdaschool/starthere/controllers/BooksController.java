package com.lambdaschool.starthere.controllers;

import com.lambdaschool.starthere.models.Book;
import com.lambdaschool.starthere.models.ErrorDetail;
import com.lambdaschool.starthere.services.BookService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class BooksController {
    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    BookService bookService;

    @ApiOperation(value = "Get all books", response = Book.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integr", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Books Found", response = Book.class), @ApiResponse(code = 404, message = "Books Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/books/books", produces = {"application/json"})
    public ResponseEntity<?> listAllBooks(HttpServletRequest request, @PageableDefault(page = 0, size = 10)
            Pageable pageable) {
        logger.warn(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed!");
        List<Book> myBooks = bookService.findAll(pageable);
        return new ResponseEntity<>(myBooks, HttpStatus.OK);
    }

    @ApiOperation(value = "Update book info", response = Book.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Book Info Updated", response = void.class), @ApiResponse(code = 500, message = "Book Not Updated, Error Occurred", response = ErrorDetail.class)})
    @PutMapping(value = "/data/books/{bookid}")
    public ResponseEntity<?> updateBook(HttpServletRequest request, @RequestBody Book updateBook, @PathVariable long bookid) {
        logger.warn(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed!");
        bookService.update(updateBook, bookid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Add a book with optional corresponding author", response = Book.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Book Added", response = void.class), @ApiResponse(code = 500, message = "Book Not Added, Error Occurred", response = ErrorDetail.class)})
    @PostMapping(value = "/data/books/{bookid}/authors/{authorid}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addBook(HttpServletRequest request, @PathVariable long bookid, @PathVariable long authorid) throws URISyntaxException {
        logger.warn(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed!");
        bookService.addBookAuthorCombo(bookid, authorid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a book", response = Book.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Book Deleted", response = void.class), @ApiResponse(code = 500, message = "Book Not Deleted, Error Occurred", response = ErrorDetail.class)})
    @DeleteMapping(value = "/data/books/{id}")
    public ResponseEntity<?> deleteBook(HttpServletRequest request, @PathVariable long id) {
        logger.warn(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed!");
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}