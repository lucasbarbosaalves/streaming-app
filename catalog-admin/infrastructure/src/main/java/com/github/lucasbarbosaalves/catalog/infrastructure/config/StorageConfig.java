package com.github.lucasbarbosaalves.catalog.infrastructure.config;

import com.github.lucasbarbosaalves.catalog.infrastructure.config.properties.GoogleStorageProperties;
import com.github.lucasbarbosaalves.catalog.infrastructure.config.properties.storage.StorageProperties;
import com.github.lucasbarbosaalves.catalog.infrastructure.services.StorageService;
import com.github.lucasbarbosaalves.catalog.infrastructure.services.impl.GoogleCloudStorage;
import com.github.lucasbarbosaalves.catalog.infrastructure.services.local.InMemoryStorageService;
import com.google.cloud.storage.Storage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class StorageConfig {

    @Bean
    @ConfigurationProperties(value = "storage.catalog.videos")
    public StorageProperties storageProperties() {
        return new StorageProperties();
    }

    @Bean(name = "storageService")
    @Profile({"development", "production"})
    @ConditionalOnMissingBean(StorageService.class)
    public StorageService googleCloudStorage(final GoogleStorageProperties props, final Storage storage) {
        return new GoogleCloudStorage(
                props.getBucket(),
                storage
        );
    }

    @Bean(name = "storageService")
    @Profile({"test", "test-e2e", "test-integration"})
    @ConditionalOnMissingBean(StorageService.class)
    public StorageService inMemoryStorage() {
        return new InMemoryStorageService();
    }
}
