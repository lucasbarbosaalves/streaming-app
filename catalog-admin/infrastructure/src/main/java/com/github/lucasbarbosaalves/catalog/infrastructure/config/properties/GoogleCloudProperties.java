package com.github.lucasbarbosaalves.catalog.infrastructure.config.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * GoogleCloudProperties holds the configuration properties for Google Cloud.
 * It includes the project ID and credentials in Base64 encoded format.
 * This class implements InitializingBean to log its properties after initialization.
 */
public class GoogleCloudProperties implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(GoogleCloudProperties.class);

    private String credentials;

    private String projectId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug(toString());
    }

    @Override
    public String toString() {
        return "GoogleCloudProperties{" +
                ", projectId='" + projectId + '\'' +
                '}';
    }
}
