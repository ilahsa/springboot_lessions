package com.alta.hello.dao;

import com.alta.hello.domain.Author;

import java.util.List;

/**
 * Created by baiba on 2018-11-13.
 */
public interface AuthorDao {
    int add(Author author);
    int update(Author author);
    int delete(Long id);
    Author findAuthor(Long id);
    List<Author> findAuthorList();
}
