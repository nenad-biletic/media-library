package com.medialibrary.repository;

import com.medialibrary.model.Book;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

  @Query("SELECT b FROM Book b WHERE LOWER(b.title) = LOWER(:title)")
  Book findByTitle(@Param("title") String title);

  @Query("SELECT b FROM Book b WHERE LOWER(b.author) = LOWER(:author)")
  List<Book> findByAuthor(@Param("author") String author, Sort sort);

  @Query("SELECT b FROM Book b WHERE LOWER(b.genre) = LOWER(:genre)")
  List<Book> findByGenre(@Param("genre") String genre, Sort sort);
}
