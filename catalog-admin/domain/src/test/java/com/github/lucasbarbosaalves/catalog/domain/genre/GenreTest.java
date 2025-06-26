package com.github.lucasbarbosaalves.catalog.domain.genre;


import com.github.lucasbarbosaalves.catalog.domain.category.CategoryID;
import com.github.lucasbarbosaalves.catalog.domain.exception.NotificationException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GenreTest {

    @Test
    public void giveValidParams_whenCallNewGenre_shouldInstantiateGenre() {
        //given
        final var expectedName = "Action";
        final var expectedIsActive = true;
        final var expectedCategories = 0;

        //when
        final var actualGenre = Genre.newGenre(expectedName, expectedIsActive);

        //then
        assertNotNull(actualGenre);
        assertNotNull(actualGenre.getId());
        assertEquals(expectedName, actualGenre.getName());
        assertEquals(expectedIsActive, actualGenre.isActive());
        assertEquals(expectedCategories, actualGenre.getCategories().size());
        assertNotNull(actualGenre.getCreatedAt());
        assertNotNull(actualGenre.getUpdatedAt());
        assertNull(actualGenre.getDeletedAt());

    }

    @Test
    public void giveInvalidEmptyName_whenCallNewGenreAndValidate_shouldReceiveADomainException() {
        //given
        final String expectedName = " ";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;

        final var actualException = assertThrows(NotificationException.class, () -> {
            Genre.newGenre(expectedName, expectedIsActive);
        });

        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());

    }

    @Test
    public void giveInvalidNameWithLengthGreaterThan255_whenCallNewGenreAndValidate_shouldReceiveAError() {
        //given
        final String expectedName = "a".repeat(256);
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedErrorCount = 1;


        final var actualException = assertThrows(NotificationException.class, () -> {
            Genre.newGenre(expectedName, expectedIsActive);
        });

        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());

    }

    @Test
    public void givenAnActiveGenre_whenCallsInactivate_shouldReturnGenreDeactivated() {
        final var expectedName = "Action";
        final var expectedIsActive = false;
        final var expectedCategories = 0;

        //when
        final var actualGenre = Genre.newGenre(expectedName, true);

        assertTrue(actualGenre.isActive());
        assertNull(actualGenre.getDeletedAt());

        final var actualCreatedAt = actualGenre.getCreatedAt();
        final var actualUpdatedAt = actualGenre.getUpdatedAt();
        actualGenre.deactivate();

        //then
        assertNotNull(actualGenre);
        assertNotNull(actualGenre.getId());
        assertEquals(expectedName, actualGenre.getName());
        assertEquals(expectedIsActive, actualGenre.isActive());
        assertEquals(expectedCategories, actualGenre.getCategories().size());
        assertNotNull(actualGenre.getCreatedAt());
        assertNotNull(actualGenre.getUpdatedAt());
        assertEquals(actualUpdatedAt, actualCreatedAt);
        assertTrue(actualCreatedAt.isBefore(actualGenre.getUpdatedAt()));
        assertNotNull(actualGenre.getDeletedAt());

    }

    @Test
    public void givenInactiveGenre_whenCallsActivate_shouldReturnGenreDeactivated() {
        final var expectedName = "Action";
        final var expectedIsActive = true;
        final var expectedCategories = 0;

        //when
        final var actualGenre = Genre.newGenre(expectedName, false);

        assertFalse(actualGenre.isActive());
        assertNotNull(actualGenre.getDeletedAt());

        final var actualCreatedAt = actualGenre.getCreatedAt();
        final var actualUpdatedAt = actualGenre.getUpdatedAt();
        actualGenre.activate();

        //then
        assertNotNull(actualGenre);
        assertNotNull(actualGenre.getId());
        assertEquals(expectedName, actualGenre.getName());
        assertEquals(expectedIsActive, actualGenre.isActive());
        assertEquals(expectedCategories, actualGenre.getCategories().size());
        assertNotNull(actualGenre.getCreatedAt());
        assertNotNull(actualGenre.getUpdatedAt());
        assertEquals(actualUpdatedAt, actualCreatedAt);
        assertTrue(actualCreatedAt.isBefore(actualGenre.getUpdatedAt()));
        assertNull(actualGenre.getDeletedAt());

    }

    @Test
    public void givenAValidGenre_whenCallsUpdate_shouldReturnGenreUpdated() {
        final var expectedName = "Action";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(CategoryID.from("123"));

        //when
        final var actualGenre = Genre.newGenre("Comedy", false);

        assertFalse(actualGenre.isActive());
        assertNotNull(actualGenre.getDeletedAt());

        final var actualCreatedAt = actualGenre.getCreatedAt();
        final var actualUpdatedAt = actualGenre.getUpdatedAt();

        actualGenre.update(expectedName, expectedIsActive, expectedCategories);

        //then
        assertNotNull(actualGenre);
        assertNotNull(actualGenre.getId());
        assertEquals(expectedName, actualGenre.getName());
        assertEquals(expectedIsActive, actualGenre.isActive());
        assertEquals(expectedCategories, actualGenre.getCategories());
        assertEquals(actualUpdatedAt, actualCreatedAt);
        assertTrue(actualCreatedAt.isBefore(actualGenre.getUpdatedAt()));
        assertNull(actualGenre.getDeletedAt());

    }


    @Test
    public void givenAValidInactiveGenre_whenCallsUpdateWithActivate_shouldReturnGenreUpdated() {
        final var expectedName = "Action";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(CategoryID.from("123"));

        //when
        final var actualGenre = Genre.newGenre("Comedy", false);

        assertFalse(actualGenre.isActive());
        assertNotNull(actualGenre.getDeletedAt());

        final var actualCreatedAt = actualGenre.getCreatedAt();
        final var actualUpdatedAt = actualGenre.getUpdatedAt();

        actualGenre.update(expectedName, expectedIsActive, expectedCategories);

        //then
        assertNotNull(actualGenre);
        assertNotNull(actualGenre.getId());
        assertEquals(expectedName, actualGenre.getName());
        assertEquals(expectedIsActive, actualGenre.isActive());
        assertEquals(expectedCategories, actualGenre.getCategories());
        assertEquals(actualUpdatedAt, actualCreatedAt);
        assertTrue(actualCreatedAt.isBefore(actualGenre.getUpdatedAt()));
        assertNull(actualGenre.getDeletedAt());

    }

    @Test
    public void givenAValidInactiveGenre_whenCallsUpdateWithInactive_shouldReturnGenreUpdated() {
        final var expectedName = "Action";
        final var expectedIsActive = false;
        final var expectedCategories = List.of(CategoryID.from("123"));

        //when
        final var actualGenre = Genre.newGenre("Comedy", true);

        assertTrue(actualGenre.isActive());
        assertNull(actualGenre.getDeletedAt());

        final var actualCreatedAt = actualGenre.getCreatedAt();
        final var actualUpdatedAt = actualGenre.getUpdatedAt();

        actualGenre.update(expectedName, expectedIsActive, expectedCategories);

        //then
        assertNotNull(actualGenre);
        assertNotNull(actualGenre.getId());
        assertEquals(expectedName, actualGenre.getName());
        assertEquals(expectedIsActive, actualGenre.isActive());
        assertEquals(expectedCategories, actualGenre.getCategories());
        assertEquals(actualUpdatedAt, actualCreatedAt);
        assertTrue(actualCreatedAt.isBefore(actualGenre.getUpdatedAt()));
        assertNotNull(actualGenre.getDeletedAt());

    }

   
    @Test
    public void givenAValidGenre_whenCallUpdateWithEmptyName_shouldReceiveNotificationException() {
        final var expectedName = " ";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(CategoryID.from("123"));
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        final var actualGenre = Genre.newGenre("acao", false);

        final var actualException = assertThrows(NotificationException.class, () -> {
            actualGenre.update(expectedName, expectedIsActive, expectedCategories);
        });

        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAValidGenre_whenCallUpdateWithNullName_shouldReceiveNotificationException() {
        final String expectedName = null;
        final var expectedIsActive = true;
        final var expectedCategories = List.of(CategoryID.from("123"));
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var actualGenre = Genre.newGenre("acao", false);

        final var actualException = assertThrows(NotificationException.class, () -> {
            actualGenre.update(expectedName, expectedIsActive, expectedCategories);
        });

        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

}