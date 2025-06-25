package com.github.lucasbarbosaalves.catalog.infrastructure.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lucasbarbosaalves.catalog.ControllerTest;
import com.github.lucasbarbosaalves.catalog.application.category.create.CreateCategoryOutput;
import com.github.lucasbarbosaalves.catalog.application.category.create.CreateCategoryUseCase;
import com.github.lucasbarbosaalves.catalog.application.category.delete.DeleteCategoryUseCase;
import com.github.lucasbarbosaalves.catalog.application.category.retrieve.get.CategoryOutput;
import com.github.lucasbarbosaalves.catalog.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.github.lucasbarbosaalves.catalog.application.category.retrieve.list.CategoryListOutput;
import com.github.lucasbarbosaalves.catalog.application.category.retrieve.list.ListCategoriesUseCase;
import com.github.lucasbarbosaalves.catalog.application.category.update.UpdateCategoryCommand;
import com.github.lucasbarbosaalves.catalog.application.category.update.UpdateCategoryOutput;
import com.github.lucasbarbosaalves.catalog.application.category.update.UpdateCategoryUseCase;
import com.github.lucasbarbosaalves.catalog.domain.category.Category;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryID;
import com.github.lucasbarbosaalves.catalog.domain.category.CategorySearchQuery;
import com.github.lucasbarbosaalves.catalog.domain.category.NotFoundException;
import com.github.lucasbarbosaalves.catalog.domain.exception.DomainException;
import com.github.lucasbarbosaalves.catalog.domain.pagination.Pagination;
import com.github.lucasbarbosaalves.catalog.domain.validation.Error;
import com.github.lucasbarbosaalves.catalog.domain.validation.handler.Notification;
import com.github.lucasbarbosaalves.catalog.infrastructure.category.dto.CreateCategoryRequest;
import com.github.lucasbarbosaalves.catalog.infrastructure.category.dto.UpdateCategoryRequest;
import com.github.lucasbarbosaalves.catalog.infrastructure.category.persistence.CategoryJpaEntity;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Right;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = CategoryAPI.class)
public class CategoryAPITest {

    @Autowired
    private MockMvc mvc; // Apoia nas chamadas HTTP para testar os endpoints da API

    @MockitoBean
    private CreateCategoryUseCase createCategoryUseCase;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private GetCategoryByIdUseCase getCategoryByIdUseCase;

    @MockitoBean
    private UpdateCategoryUseCase updateCategoryUseCase;

    @MockitoBean
    private DeleteCategoryUseCase deleteCategoryUseCase;

    @MockitoBean
    private ListCategoriesUseCase listCategoriesUseCase;

    @Test // CreateCategoryUseCase - Happy Path
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() throws Exception {
        // given
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aInput = new CreateCategoryRequest(expectedName, expectedDescription, expectedIsActive);

        when(createCategoryUseCase.execute(any())).thenReturn(Right(CreateCategoryOutput.from("123")));

        // when
        final var request = post("/categories").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(aInput));

        final var response = this.mvc.perform(request).andDo(print());

        // then
        response.andExpect(status().isCreated()).andExpect(header().string("Location", "/categories/123")).andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$.id", equalTo("123")));

        verify(createCategoryUseCase, times(1)).execute(argThat(cmd -> Objects.equals(expectedName, cmd.name()) && Objects.equals(expectedDescription, cmd.description()) && Objects.equals(expectedIsActive, cmd.isActive())));
    }

    @Test // CreateCategoryUseCase - Invalid Command
    public void givenAInvalidCommand_whenCallsCreateCategory_shouldReturnNotification() throws Exception {
        // given
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";

        final var aInput = new CreateCategoryRequest(expectedName, expectedDescription, expectedIsActive);


        when(createCategoryUseCase.execute(any())).thenReturn(Left(Notification.create(new Error(expectedErrorMessage))));

        // when
        final var request = post("/categories").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(aInput));

        final var response = this.mvc.perform(request).andDo(print());

        // then
        response.andExpect(status().isUnprocessableEntity()).andExpect(header().string("Location", Matchers.nullValue())).andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$.errors", hasSize(1))).andExpect(jsonPath("$.errors[0].message", equalTo(expectedErrorMessage)));

        verify(createCategoryUseCase, times(1)).execute(argThat(cmd -> Objects.equals(expectedName, cmd.name()) && Objects.equals(expectedDescription, cmd.description()) && Objects.equals(expectedIsActive, cmd.isActive())));
    }

    @Test // CreateCategoryUseCase - DomainException
    public void givenAInvalidCommand_whenCallsCreateCategory_shouldReturnDomainException() throws Exception {
        // given
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";

        final var aInput = new CreateCategoryRequest(expectedName, expectedDescription, expectedIsActive);


        when(createCategoryUseCase.execute(any())).thenThrow(DomainException.with(new Error(expectedErrorMessage)));

        // when
        final var request = post("/categories").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(aInput));

        final var response = this.mvc.perform(request).andDo(print());

        // then
        response.andExpect(status().isUnprocessableEntity()).andExpect(header().string("Location", Matchers.nullValue())).andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$.errors", hasSize(1))).andExpect(jsonPath("$.errors[0].message", equalTo(expectedErrorMessage)));

        verify(createCategoryUseCase, times(1)).execute(argThat(cmd -> Objects.equals(expectedName, cmd.name()) && Objects.equals(expectedDescription, cmd.description()) && Objects.equals(expectedIsActive, cmd.isActive())));
    }

    @Test // GetCategoryByIdUseCase - Happy Path
    public void givenAValidId_whenCallsGetCategory_shouldReturnCategory() throws Exception {
        // given
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var expectedId = aCategory.getId();

        when(getCategoryByIdUseCase.execute(any())).thenReturn(CategoryOutput.from(aCategory));

        // when
        final var request = get("/categories/{id}", expectedId.getValue()).contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request).andDo(print());
        // then
        response.andExpect(status().isOk()).andExpect(jsonPath("$.id", equalTo(expectedId.getValue()))).andExpect(jsonPath("$.name", equalTo(expectedName))).andExpect(jsonPath("$.description", equalTo(aCategory.getDescription()))).andExpect(jsonPath("$.is_active", equalTo(aCategory.isActive()))).andExpect(jsonPath("$.created_at", equalTo(aCategory.getCreatedAt().toString()))).andExpect(jsonPath("$.updated_at", equalTo(aCategory.getUpdatedAt().toString()))).andExpect(jsonPath("$.deleted_at", equalTo(aCategory.getDeleteAt())));

        verify(getCategoryByIdUseCase, times(1)).execute(expectedId.getValue());
    }

    @Test  // GetCategoryByIdUseCase - Not Found (Invalid ID)
    public void givenAInvalidId_whenCallsGetCategory_shouldReturnNotFound() throws Exception {
        final var expectedErrorMessage = "Category with ID 123 was not found";
        final var expectedId = CategoryID.from("123");

        when(getCategoryByIdUseCase.execute(any())).thenThrow(NotFoundException.with(Category.class, expectedId));

        // when
        final var request = get("/categories/{id}", expectedId.getValue()).contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request).andDo(print());

        response.andExpect(status().isNotFound()).andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));

    }

    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_shouldReturnCategoryId() throws Exception {
        // given
        final var expectedId = "123";
        final var aCategory = Category.newCategory("Film", null, true);

        when(updateCategoryUseCase.execute(any())).thenReturn(Right(UpdateCategoryOutput.from(expectedId)));

        final var command = new UpdateCategoryRequest(aCategory.getName(), aCategory.getDescription(), aCategory.isActive());
        // when
        final var request = put("/categories/{id}", expectedId).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(command));

        final var response = this.mvc.perform(request).andDo(print());

        // then
        response.andExpect(status().isOk()).andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$.id", equalTo(expectedId)));

        verify(updateCategoryUseCase, times(1)).execute(argThat(cmd -> {
            return Objects.equals(expectedId, cmd.id()) && Objects.equals(aCategory.getName(), cmd.name()) && Objects.equals(aCategory.getDescription(), cmd.description()) && Objects.equals(aCategory.isActive(), cmd.isActive());
        }));

    }

    @Test
    public void givenAInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException() throws Exception {
        // given
        final var expectedId = "123";
        final String expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var expectedErrorMessage = "'name' should not be null";

        when(updateCategoryUseCase.execute(any())).thenReturn(Left(Notification.create(new Error(expectedErrorMessage))));

        final var command = new UpdateCategoryRequest(expectedName, expectedDescription, expectedIsActive);
        // when
        final var request = put("/categories/{id}", expectedId).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(command));

        final var response = this.mvc.perform(request).andDo(print());

        // then
        response.andExpect(status().isUnprocessableEntity()).andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$.errors", hasSize(1))).andExpect(jsonPath("$.errors[0].message", equalTo(expectedErrorMessage)));

        verify(updateCategoryUseCase, times(1)).execute(argThat(cmd -> {
            return Objects.equals(expectedId, cmd.id()) && Objects.equals(expectedName, cmd.name()) && Objects.equals(expectedDescription, cmd.description()) && Objects.equals(expectedIsActive, cmd.isActive());
        }));


    }

    @Test
    public void givenACommandWithInvalidID_whenCallsUpdateCategory_shouldReturnNotFoundException() throws Exception {
        // given
        final var expectedId = "not-found-id";
        final String expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Category with ID not-found-id was not found";

        when(updateCategoryUseCase.execute(any())).thenThrow(NotFoundException.with(Category.class, CategoryID.from(expectedId)));

        final var command = new UpdateCategoryRequest(expectedName, expectedDescription, expectedIsActive);
        // when
        final var request = put("/categories/{id}", expectedId).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(command));

        final var response = this.mvc.perform(request).andDo(print());

        // then
        response.andExpect(status().isNotFound()).andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));

        verify(updateCategoryUseCase, times(1)).execute(argThat(cmd -> Objects.equals(expectedId, cmd.id()) && Objects.equals(expectedName, cmd.name()) && Objects.equals(expectedDescription, cmd.description()) && Objects.equals(expectedIsActive, cmd.isActive())));
    }

    @Test
    public void givenAValidId_whenCallsDeleteCategory_shouldReturnNoContent() throws Exception {
        // when
        final var expectedId = "123";

        doNothing().when(deleteCategoryUseCase).execute(any());

        // when
        final var request = delete("/categories/{id}", expectedId).contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request).andDo(print());

        // then
        response.andExpect(status().isNoContent());

    }

    @Test
    public void givenAValidParams_whenCallsListCategories_shouldReturnCategories() throws Exception {
        final var aCategory = Category.newCategory("Movies", "A category for movies", true);
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "movies";
        final var expectedSort = "description";
        final var expectedDirection = "desc";
        final var expectedItemsCount = 1;
        final var expectedItems = List.of(CategoryListOutput.from(aCategory));
        final var expectedTotal = 1;

        when(listCategoriesUseCase.execute(any()))
                .thenReturn(new Pagination<>(expectedPage, expectedPerPage, expectedTotal, expectedItems));

        // when
        final var request = get("/categories")
                .queryParam("page", String.valueOf(expectedPage))
                .queryParam("perPage", String.valueOf(expectedPerPage))
                .queryParam("sort", expectedSort)
                .queryParam("dir", expectedDirection)
                .queryParam("search", expectedTerms)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request)
                        .andDo(print());

       //then

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.per_page", equalTo(expectedPerPage)))
                .andExpect(jsonPath("$.current_page", equalTo(expectedPage)))
                .andExpect(jsonPath("$.total", equalTo(expectedTotal)))
                .andExpect(jsonPath("$.items", hasSize(expectedItemsCount)))
                .andExpect(jsonPath("$.items[0].id", equalTo(aCategory.getId().getValue())))
                .andExpect(jsonPath("$.items[0].name", equalTo(aCategory.getName())))
                .andExpect(jsonPath("$.items[0].description", equalTo(aCategory.getDescription())))
                .andExpect(jsonPath("$.items[0].is_active", equalTo(aCategory.isActive())))
                .andExpect(jsonPath("$.items[0].created_at", equalTo(aCategory.getCreatedAt().toString())))
                .andExpect(jsonPath("$.items[0].deleted_at", equalTo(aCategory.getDeleteAt())));

        verify(listCategoriesUseCase, times(1)).execute(argThat(query ->
                Objects.equals(expectedPage, query.page()) &&
                Objects.equals(expectedPerPage, query.perPage()) &&
                Objects.equals(expectedTerms, query.terms()) &&
                Objects.equals(expectedSort, query.sort()) &&
                Objects.equals(expectedDirection, query.direction())
        ));
    }

}
