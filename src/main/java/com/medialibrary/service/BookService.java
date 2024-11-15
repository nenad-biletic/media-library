package com.medialibrary.service;

import static com.medialibrary.constants.FieldNames.ID;
import static com.medialibrary.constants.FieldNames.TITLE;

import com.medialibrary.authentication.AuthenticationService;
import com.medialibrary.exception.MediaAlreadyExistsException;
import com.medialibrary.exception.NoSuchMediaExistsException;
import com.medialibrary.model.Book;
import com.medialibrary.model.User;
import com.medialibrary.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookService {

  private final BookRepository bookRepository;
  private final AuthenticationService authenticationService;

  @Autowired
  public BookService(BookRepository bookRepository, AuthenticationService authenticationService) {
    this.bookRepository = bookRepository;
    this.authenticationService = authenticationService;
  }

  public String saveBook(Book book) {
    User user = authenticationService.getCurrentUser();
    Optional<Book> bookOpt = bookRepository.findByTitle(book.getTitle(), user.getId());

    if (bookOpt.isPresent()) {
      throw new MediaAlreadyExistsException(
          "Book with title '" + book.getTitle() + "' already exists!");
    } else {
      book.setUser(user);
      bookRepository.save(book);
      return "Book saved successfully.";
    }
  }

  public List<Book> getBooks() {
    User user = authenticationService.getCurrentUser();
    return bookRepository.findByUserId(user.getId(), Sort.by(Direction.ASC, ID));
  }

  public List<Book> getBooksSortedByName() {
    User user = authenticationService.getCurrentUser();
    return bookRepository.findByUserId(user.getId(), Sort.by(Direction.ASC, TITLE));
  }

  public List<Book> getBooksByAuthor(String author) {
    User user = authenticationService.getCurrentUser();
    return bookRepository.findByAuthor(author, user.getId(), Sort.by(Direction.ASC, TITLE));
  }

  public List<Book> getBooksByGenre(String genre) {
    User user = authenticationService.getCurrentUser();
    return bookRepository.findByGenre(genre, user.getId(), Sort.by(Direction.ASC, TITLE));
  }

  public String updateBook(Book book) {
    User user = authenticationService.getCurrentUser();
    Optional<Book> bookOpt = bookRepository.findByUserIdAndBookId(user.getId(), book.getId());

    if (bookOpt.isEmpty()) {
      throw new NoSuchMediaExistsException("Book with ID " + book.getId() + " does not exist!");
    } else {
      Book existingBook = bookOpt.get();
      existingBook.setTitle(book.getTitle());
      existingBook.setAuthor(book.getAuthor());
      existingBook.setGenre(book.getGenre());
      bookRepository.save(existingBook);
      return "Book updated successfully.";
    }
  }

  public String deleteBook(Long bookId) {
    User user = authenticationService.getCurrentUser();
    Optional<Book> bookOpt = bookRepository.findByUserIdAndBookId(user.getId(), bookId);

    if (bookOpt.isEmpty()) {
      throw new NoSuchMediaExistsException("Book with ID " + bookId + " does not exist!");
    } else {
      bookRepository.deleteById(bookId);
      return "Book deleted successfully.";
    }
  }
}
