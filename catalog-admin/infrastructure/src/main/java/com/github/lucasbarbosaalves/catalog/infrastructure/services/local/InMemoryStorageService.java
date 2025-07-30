package com.github.lucasbarbosaalves.catalog.infrastructure.services.local;

import com.github.lucasbarbosaalves.catalog.domain.resource.Resource;
import com.github.lucasbarbosaalves.catalog.infrastructure.services.StorageService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryStorageService implements StorageService {


    private final Map<String, Resource> storage;

    public InMemoryStorageService() {
        this.storage = new ConcurrentHashMap<>();
    }

    public Map<String, Resource> storage() {
        return this.storage;
    }

    public void reset() {
        this.storage.clear();
    }

    @Override
    public void store(String name, Resource resource) {
        this.storage.put(name, resource);
    }

    @Override
    public void deleteAll(Collection<String> names) {
        names.forEach(this.storage::remove);
    }

    @Override
    public List<String> list(String prefix) {

        if (prefix == null || prefix.isEmpty()) {
            return Collections.emptyList();
        }

        return this.storage.keySet().stream()
                .filter(it -> it.startsWith(prefix))
                .toList();
    }

    @Override
    public Optional<Resource> get(String name) {
        return Optional.ofNullable(this.storage.get(name));
    }
}
