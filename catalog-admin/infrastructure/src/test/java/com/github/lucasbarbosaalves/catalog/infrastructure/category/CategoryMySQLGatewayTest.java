package com.github.lucasbarbosaalves.catalog.infrastructure.category;

import com.github.lucasbarbosaalves.catalog.infrastructure.MySQLGatewayTest;
import com.github.lucasbarbosaalves.catalog.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@MySQLGatewayTest
public class CategoryMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway categoryMySQLGateway;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testInjectedDependencies() {
        assertNotNull(categoryMySQLGateway, "CategoryMySQLGateway should not be null");
        assertNotNull(categoryRepository, "CategoryRepository should not be null");
    }



}