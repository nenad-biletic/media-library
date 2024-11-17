package com.medialibrary.repository;

import com.medialibrary.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

  @Query("SELECT b FROM Book b WHERE b.user.id = :userId ORDER BY b.title ASC")
  List<Book> findByUserId(Long userId);

  @Query("SELECT b FROM Book b WHERE b.user.id = :userId AND b.id = :bookId")
  Optional<Book> findByUserIdAndBookId(Long userId, Long bookId);

  @Query("SELECT b FROM Book b WHERE LOWER(b.title) = LOWER(:title) AND b.user.id = :userId")
  Optional<Book> findByTitle(@Param("title") String title, Long userId);

  @Query("SELECT b FROM Book b WHERE LOWER(b.author) = LOWER(:author) AND b.user.id = :userId"
      + " ORDER BY b.title ASC")
  List<Book> findByAuthor(@Param("author") String author, Long userId);

  @Query("SELECT b FROM Book b WHERE LOWER(b.genre) = LOWER(:genre) AND b.user.id = :userId"
      + " ORDER BY b.title ASC")
  List<Book> findByGenre(@Param("genre") String genre, Long userId);
}
