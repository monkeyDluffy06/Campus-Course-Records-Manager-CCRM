package edu.ccrm.service;

import java.util.List;

/**
 * A generic interface for classes that support search operations.
 * @param <T> The type of object to be searched for.
 */
public interface Searchable<T> {
    List<T> search(String query);
}
