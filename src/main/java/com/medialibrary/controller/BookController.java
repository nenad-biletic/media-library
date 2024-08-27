package com.medialibrary.controller;

import static com.medialibrary.constants.AttributeNames.BOOK;
import static com.medialibrary.constants.AttributeNames.BOOKS;
import static com.medialibrary.constants.Messages.ERROR;
import static com.medialibrary.constants.Messages.SUCCESS;
import static com.medialibrary.constants.Templates.ADD_BOOK;
import static com.medialibrary.constants.Templates.BOOKS_TABLE;
import static com.medialibrary.constants.Templates.DELETE_BOOK;
import static com.medialibrary.constants.Templates.UPDATE_BOOK;

import com.medialibrary.exception.MediaAlreadyExistsException;
import com.medialibrary.exception.NoSuchMediaExistsException;
import com.medialibrary.model.Book;
import com.medialibrary.service.BookService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/library/books")
public class BookController {

  private final BookService bookService;
  private List<Book> books = new ArrayList<>();

  public BookController(@Autowired BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/add")
  public String addBook(Model model) {
    model.addAttribute(BOOK, new Book());
    return ADD_BOOK;
  }

  @PostMapping("/save")
  public String saveBook(Model model, @ModelAttribute Book book) {
    try {
      String result = bookService.saveBook(book);
      model.addAttribute(SUCCESS, result);
    } catch (MediaAlreadyExistsException e) {
      model.addAttribute(ERROR, e.getMessage());
    }
    return ADD_BOOK;
  }

  @GetMapping
  public String getBooks(Model model) {
    books = bookService.getBooks();
    model.addAttribute(BOOKS, books);
    return BOOKS_TABLE;
  }

  @GetMapping("/sort")
  public String getBooksSortedByName(Model model) {
    books = bookService.getBooksSortedByName();
    model.addAttribute(BOOKS, books);
    return BOOKS_TABLE;
  }

  @GetMapping("/author/{author}")
  public String getBooksByAuthor(Model model, @PathVariable String author) {
    books = bookService.getBooksByAuthor(author);
    model.addAttribute(BOOKS, books);
    return BOOKS_TABLE;
  }

  @GetMapping("/genre/{genre}")
  public String getBooksByGenre(Model model, @PathVariable String genre) {
    books = bookService.getBooksByGenre(genre);
    model.addAttribute(BOOKS, books);
    return BOOKS_TABLE;
  }

  @GetMapping("/update")
  public String getUpdateInfo(Model model, Book book) {
    model.addAttribute(BOOK, book);
    return UPDATE_BOOK;
  }

  @PutMapping("/update")
  public String updateBook(Model model, @ModelAttribute Book book) {
    try {
      String result = bookService.updateBook(book);
      model.addAttribute(SUCCESS, result);
    } catch (NoSuchMediaExistsException e) {
      model.addAttribute(ERROR, e.getMessage());
    }
    return UPDATE_BOOK;
  }

  @GetMapping("/delete")
  public String getDeleteInfo() {
    return DELETE_BOOK;
  }

  @DeleteMapping("/delete")
  public String deleteBook(Model model, @RequestParam Long id) {
    try {
      String result = bookService.deleteBook(id);
      model.addAttribute(SUCCESS, result);
    } catch (NoSuchMediaExistsException e) {
      model.addAttribute(ERROR, e.getMessage());
    }
    return DELETE_BOOK;
  }
}
