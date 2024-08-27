package com.medialibrary.service;

import static com.medialibrary.constants.FieldNames.ID;
import static com.medialibrary.constants.FieldNames.TITLE;

import com.medialibrary.exception.MediaAlreadyExistsException;
import com.medialibrary.exception.NoSuchMediaExistsException;
import com.medialibrary.model.Book;
import com.medialibrary.repository.BookRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  private final BookRepository bookRepository;

  public BookService(@Autowired BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public String saveBook(Book book) {
    Book existingBook = bookRepository.findByTitle(book.getTitle());
    if (existingBook == null) {
      bookRepository.save(book);
      return "Book saved successfully.";
    } else {
      throw new MediaAlreadyExistsException("Book already exists!");
    }
  }

  public List<Book> getBooks() {
    return bookRepository.findAll(Sort.by(Direction.ASC, ID));
  }

  public List<Book> getBooksSortedByName() {
    return bookRepository.findAll(Sort.by(Direction.ASC, TITLE));
  }

  public List<Book> getBooksByAuthor(String author) {
    return bookRepository.findByAuthor(author,
        Sort.by(Direction.ASC, TITLE));
  }

  public List<Book> getBooksByGenre(String genre) {
    return bookRepository.findByGenre(genre, Sort.by(Direction.ASC, TITLE));
  }

  public String updateBook(Book book) {
    Book existingBook = bookRepository.findById(book.getId()).orElse(null);
    if (existingBook == null) {
      throw new NoSuchMediaExistsException("Book with ID " + book.getId() + " does not exist!");
    } else {
      existingBook.setTitle(book.getTitle());
      existingBook.setAuthor(book.getAuthor());
      existingBook.setGenre(book.getGenre());
      bookRepository.save(existingBook);
      return "Book updated successfully.";
    }
  }

  public String deleteBook(Long id) {
    Book existingBook = bookRepository.findById(id).orElse(null);
    if (existingBook == null) {
      throw new NoSuchMediaExistsException("Book with ID " + id + " does not exist!");
    } else {
      bookRepository.deleteById(id);
      return "Book deleted successfully.";
    }
  }
}
