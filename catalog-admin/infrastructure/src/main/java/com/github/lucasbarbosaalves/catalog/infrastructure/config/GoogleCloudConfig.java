package com.github.lucasbarbosaalves.catalog.infrastructure.config;

import com.github.lucasbarbosaalves.catalog.infrastructure.config.properties.GoogleCloudProperties;
import com.github.lucasbarbosaalves.catalog.infrastructure.config.properties.GoogleStorageProperties;
import com.google.api.gax.retrying.RetrySettings;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.http.HttpTransportOptions;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.threeten.bp.Duration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@Configuration
@Profile({"development", "production"})
public class GoogleCloudConfig {


    /**
     * Creates a GoogleCloudProperties bean for configuring Google Cloud.
     *
     * @return a GoogleCloudProperties instance
     */
    @Bean
    @ConfigurationProperties("google.cloud")
    public GoogleCloudProperties googleCloudProperties() {
        return new GoogleCloudProperties();
    }

    /**
     * Creates a GoogleStorageProperties bean for configuring Google Cloud Storage.
     *
     * @return a GoogleStorageProperties instance
     */
    @Bean
    @ConfigurationProperties("google.cloud.storage.catalog.videos")
    public GoogleStorageProperties googleStorageProperties() {
        return new GoogleStorageProperties();
    }

    /**
     * Creates a GoogleCredentials bean from the Base64 encoded JSON credentials.
     *
     * @param properties the GoogleCloudProperties containing the Base64 encoded credentials
     * @return a GoogleCredentials instance
     */
    @Bean
    public Credentials credentials(final GoogleCloudProperties properties) {
        final var jsonContent = Base64.getDecoder().decode(properties.getCredentials());

        try (final var stream = new ByteArrayInputStream(jsonContent)) {
            return GoogleCredentials.fromStream(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a Storage bean for Google Cloud Storage with custom transport and retry settings.
     *
     * @param credentials       the credentials for accessing Google Cloud
     * @param storageProperties the properties for configuring Google Cloud Storage
     * @return a Storage instance
     */
    @Bean
    public Storage storage(final Credentials credentials, final GoogleStorageProperties storageProperties) {
        final var transportOptions = HttpTransportOptions.newBuilder()
                .setConnectTimeout(storageProperties.getConnectTimeout())
                .setReadTimeout(storageProperties.getReadTimeout())
                .build();

        final var retrySetting = RetrySettings.newBuilder()
                .setInitialRetryDelay(Duration.ofMillis(storageProperties.getRetryDelay()))
                .setMaxRetryDelay(Duration.ofMillis(storageProperties.getRetryMaxDelay()))
                .setMaxAttempts(storageProperties.getRetryMaxAttempts())
                .setRetryDelayMultiplier(storageProperties.getRetryMultiplier())
                .build();

        StorageOptions options = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .setTransportOptions(transportOptions)
                .setRetrySettings(retrySetting)
                .build();

        return options.getService();
    }
}
