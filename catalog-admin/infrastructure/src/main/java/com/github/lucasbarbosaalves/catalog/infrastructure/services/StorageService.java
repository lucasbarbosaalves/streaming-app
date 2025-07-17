package com.github.lucasbarbosaalves.catalog.infrastructure.services;


import com.github.lucasbarbosaalves.catalog.domain.resource.Resource;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * StorageService is a facade interface for storage-related operations.
 * It can be extended in the future to include methods for various storage operations.
 * Currently, it serves as a marker interface for storage services.
 */
public interface StorageService {

    /**
     * Stores a resource with the given name.
     *
     * @param name     the name of the resource
     * @param resource the resource to be stored
     */
    void store(String name, Resource resource);

    /**
     * Deletes a resource with the given name.
     *
     * @param names the name of the resource to be deleted
     */
    void deleteAll(Collection<String> names);

    /**
     * Lists all resources with names starting with the given prefix.
     *
     * @param prefix the prefix to filter resource names
     * @return a list of resource names that start with the given prefix
     */
    List<String> list(String prefix);

    /**
     * Retrieves a resource by its name.
     *
     * @param name the name of the resource to be retrieved
     * @return the resource associated with the given name
     */
    Optional<Resource> get(String name);
}
