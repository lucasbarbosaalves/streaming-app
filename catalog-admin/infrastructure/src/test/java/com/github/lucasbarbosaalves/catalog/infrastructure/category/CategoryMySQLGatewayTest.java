package com.github.lucasbarbosaalves.catalog.infrastructure.category;

import com.github.lucasbarbosaalves.catalog.MySQLGatewayTest;
import com.github.lucasbarbosaalves.catalog.domain.category.Category;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryID;
import com.github.lucasbarbosaalves.catalog.domain.pagination.SearchQuery;
import com.github.lucasbarbosaalves.catalog.infrastructure.category.persistence.CategoryJpaEntity;
import com.github.lucasbarbosaalves.catalog.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


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

    @Test
    public void givenAValidCategory_whenCreate_thenShouldReturnANewCategory() {
        final var extName = "Movies";
        final var extDescription = "All about movies";
        final var extIsActive = true;

        final var category = Category.newCategory(extName, extDescription, extIsActive);

        assertEquals(0, categoryRepository.count());
        final var actualCategory = categoryMySQLGateway.create(category);

        assertEquals(1, categoryRepository.count());

        assertEquals(category.getId(), actualCategory.getId());
        assertEquals(extName, actualCategory.getName());
        assertEquals(extDescription, actualCategory.getDescription());
        assertEquals(extIsActive, actualCategory.isActive());
        assertEquals(category.getCreatedAt(), actualCategory.getCreatedAt());
        assertEquals(category.getUpdatedAt(), actualCategory.getUpdatedAt());
        assertEquals(category.getDeleteAt(), actualCategory.getDeleteAt());
        assertNull(actualCategory.getDeleteAt());

        final var actualEntity = categoryRepository.findById(category.getId().getValue()).get();

        assertEquals(category.getId().getValue(), actualEntity.getId());
        assertEquals(extName, actualEntity.getName());
        assertEquals(extDescription, actualEntity.getDescription());
        assertEquals(extIsActive, actualEntity.isActive());
        assertEquals(category.getCreatedAt(), actualEntity.getCreatedAt());
        assertEquals(category.getUpdatedAt(), actualEntity.getUpdatedAt());
        assertEquals(category.getDeleteAt(), actualEntity.getDeletedAt());
        assertNull(actualCategory.getDeleteAt());
    }


    @Test
    public void givenAValidCategory_whenUpdate_thenShouldReturnACategoryUpdated() {
        final var extName = "Movies";
        final var extDescription = "All about movies";
        final var extIsActive = true;

        final var category = Category.newCategory("Filmes", null, extIsActive);

        assertEquals(0, categoryRepository.count());

        categoryRepository.saveAndFlush(CategoryJpaEntity.from(category));
        assertEquals(1, categoryRepository.count());

        final var actualInvalidEntity = categoryRepository.findById(category.getId().getValue()).get();

        assertEquals("Filmes", actualInvalidEntity.getName());
        assertNull(actualInvalidEntity.getDescription());
        assertEquals(extIsActive, actualInvalidEntity.isActive());

        final var aUpdatedCategory = Category.clone(category).update(extName, extDescription, extIsActive);
        final var actualCategory = categoryMySQLGateway.update(aUpdatedCategory);

        assertEquals(1, categoryRepository.count());

        assertEquals(category.getId(), actualCategory.getId());
        assertEquals(extName, actualCategory.getName());
        assertEquals(extDescription, actualCategory.getDescription());
        assertEquals(extIsActive, actualCategory.isActive());
        assertEquals(category.getCreatedAt(), actualCategory.getCreatedAt());
        assertEquals(category.getDeleteAt(), actualCategory.getDeleteAt());
        assertNull(actualCategory.getDeleteAt());

        final var actualEntity = categoryRepository.findById(category.getId().getValue()).get();

        assertEquals(category.getId().getValue(), actualEntity.getId());
        assertEquals(extName, actualEntity.getName());
        assertEquals(extDescription, actualEntity.getDescription());
        assertEquals(extIsActive, actualEntity.isActive());
        assertEquals(category.getCreatedAt(), actualEntity.getCreatedAt());
        assertTrue(category.getUpdatedAt().isBefore(actualCategory.getUpdatedAt()));
        assertEquals(category.getDeleteAt(), actualEntity.getDeletedAt());
        assertNull(actualCategory.getDeleteAt());
    }


    @Test
    public void givenAValidCategory_whenDelete_thenShouldDeleteCategory() {
        final var extName = "Movies";
        final var extDescription = "All about movies";
        final var extIsActive = true;

        final var category = Category.newCategory(extName, extDescription, extIsActive);

        assertEquals(0, categoryRepository.count());

        categoryRepository.saveAndFlush(CategoryJpaEntity.from(category));
        assertEquals(1, categoryRepository.count());

        categoryMySQLGateway.deleteById(category.getId());
        assertEquals(0, categoryRepository.count());
    }

    @Test
    public void givenAInvalidCategory_whenDelete_thenShouldDeleteCategory() {
        assertEquals(0, categoryRepository.count());

        categoryMySQLGateway.deleteById(CategoryID.from("123"));
        assertEquals(0, categoryRepository.count());
    }

    @Test
    public void givenAValidCategory_whenFindById_thenShouldReturnCategory() {
        final var extName = "Movies";
        final var extDescription = "All about movies";
        final var extIsActive = true;

        final var category = Category.newCategory(extName, extDescription, extIsActive);

        assertEquals(0, categoryRepository.count());

        categoryRepository.saveAndFlush(CategoryJpaEntity.from(category));
        assertEquals(1, categoryRepository.count());


        final var actualCategory = categoryMySQLGateway.findById(category.getId()).get();

        assertEquals(1, categoryRepository.count());

        assertEquals(category.getId(), actualCategory.getId());
        assertEquals(extName, actualCategory.getName());
        assertEquals(extDescription, actualCategory.getDescription());
        assertEquals(extIsActive, actualCategory.isActive());
        assertEquals(category.getCreatedAt(), actualCategory.getCreatedAt());
        assertEquals(category.getUpdatedAt(), actualCategory.getUpdatedAt());
        assertEquals(category.getDeleteAt(), actualCategory.getDeleteAt());
        assertNull(actualCategory.getDeleteAt());

    }

    @Test
    public void givenAValidCategoryIDNotStored_whenFindById_thenShouldReturnEmpty() {

        assertEquals(0, categoryRepository.count());

        final var actualCategory = categoryMySQLGateway.findById(CategoryID.from("invalid id"));

        assertTrue(actualCategory.isEmpty());

    }

    @Test
    public void givenPrePersistedCategories_whenCallsFindAll_shouldReturnPaginated() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;

        final var filmes = Category.newCategory("Filmes", null, true);
        final var series = Category.newCategory("Séries", null, true);
        final var documentarios = Category.newCategory("Documentários", null, true);

        assertEquals(0, categoryRepository.count());

        categoryRepository.saveAll(List.of(
                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)
        ));

        assertEquals(3, categoryRepository.count());

        final var query = new SearchQuery(0, 1, "", "name", "asc");
        final var actualResult = categoryMySQLGateway.findAll(query);

        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPerPage, actualResult.items().size());
        assertEquals(documentarios.getId(), actualResult.items().get(0).getId());
    }

    @Test
    public void givenEmptyCategoriesTable_whenCallsFindAll_shouldReturnEmptyPage() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 0;

        assertEquals(0, categoryRepository.count());

        final var query = new SearchQuery(0, 1, "", "name", "asc");
        final var actualResult = categoryMySQLGateway.findAll(query);

        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(0, actualResult.items().size());
    }

    @Test
    public void givenFollowPagination_whenCallsFindAllWithPage1_shouldReturnPaginated() {
        var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;

        final var filmes = Category.newCategory("Filmes", null, true);
        final var series = Category.newCategory("Séries", null, true);
        final var documentarios = Category.newCategory("Documentários", null, true);

        assertEquals(0, categoryRepository.count());

        categoryRepository.saveAll(List.of(
                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)
        ));

        assertEquals(3, categoryRepository.count());

        var query = new SearchQuery(0, 1, "", "name", "asc");
        var actualResult = categoryMySQLGateway.findAll(query);

        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPerPage, actualResult.items().size());
        assertEquals(documentarios.getId(), actualResult.items().get(0).getId());

        // Page 1
        expectedPage = 1;

        query = new SearchQuery(1, 1, "", "name", "asc");
        actualResult = categoryMySQLGateway.findAll(query);

        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPerPage, actualResult.items().size());
        assertEquals(filmes.getId(), actualResult.items().get(0).getId());

        // Page 2
        expectedPage = 2;

        query = new SearchQuery(2, 1, "", "name", "asc");
        actualResult = categoryMySQLGateway.findAll(query);

        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPerPage, actualResult.items().size());
        assertEquals(series.getId(), actualResult.items().get(0).getId());
    }

    @Test
    public void givenPrePersistedCategoriesAndDocAsTerms_whenCallsFindAllAndTermsMatchesCategoryName_shouldReturnPaginated() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        final var filmes = Category.newCategory("Filmes", null, true);
        final var series = Category.newCategory("Séries", null, true);
        final var documentarios = Category.newCategory("Documentários", null, true);

        assertEquals(0, categoryRepository.count());

        categoryRepository.saveAll(List.of(
                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)
        ));

        assertEquals(3, categoryRepository.count());

        final var query = new SearchQuery(0, 1, "doc", "name", "asc");
        final var actualResult = categoryMySQLGateway.findAll(query);

        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPerPage, actualResult.items().size());
        assertEquals(documentarios.getId(), actualResult.items().get(0).getId());
    }

    @Test
    public void givenPrePersistedCategoriesAndMaisAssistidaAsTerms_whenCallsFindAllAndTermsMatchsCategoryDescription_shouldReturnPaginated() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        final var filmes = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var series = Category.newCategory("Séries", "Uma categoria assistida", true);
        final var documentarios = Category.newCategory("Documentários", "A categoria menos assistida", true);

        assertEquals(0, categoryRepository.count());

        categoryRepository.saveAll(List.of(
                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)
        ));

        assertEquals(3, categoryRepository.count());

        final var query = new SearchQuery(0, 1, "MAIS ASSISTIDA", "name", "asc");
        final var actualResult = categoryMySQLGateway.findAll(query);

        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPerPage, actualResult.items().size());
        assertEquals(filmes.getId().getValue(), actualResult.items().getFirst().getId().getValue());
    }


}