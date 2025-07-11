package com.github.lucasbarbosaalves.catalog.domain.castmember;

import com.github.lucasbarbosaalves.catalog.domain.exception.NotificationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CastMemberTest {

    @Test
    public void givenAValidParams_whenCallsNewMember_thenInstantiateACastMember() {
        final var expectedName = "John Doe";
        final var expectedType = CastMemberType.ACTOR;

        final var actualMember = CastMember.newMember(expectedName, expectedType);

        assertNotNull(actualMember);
        assertNotNull(actualMember.getId());
        assertEquals(expectedName, actualMember.getName());
        assertEquals(expectedType, actualMember.getType());
        assertEquals(actualMember.getUpdatedAt(), actualMember.getCreatedAt());
        assertNotNull(actualMember.getCreatedAt());
        assertNotNull(actualMember.getUpdatedAt());
    }

    @Test
    public void givenAInvalidNullName_whenCallsNewMember_thenShouldReceiveANotification() {
        final String expectedName = null;
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var actualException = assertThrows(NotificationException.class,
                () -> CastMember.newMember(expectedName, expectedType));

        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        
    }

    @Test
    public void givenAInvalidEmptyName_whenCallsNewMember_thenShouldReceiveANotification() {
        final String expectedName =  " ";
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;

        final var actualException = assertThrows(NotificationException.class,
                () -> CastMember.newMember(expectedName, expectedType));

        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

    }

    @Test
    public void givenAInvalidNameWithLengthMoreThan255_whenCallsNewMember_thenShouldReceiveANotification() {
        final String expectedName =  "a".repeat(256);
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedErrorCount = 1;

        final var actualException = assertThrows(NotificationException.class,
                () -> CastMember.newMember(expectedName, expectedType));

        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

    }

    @Test
    public void givenAInvalidNullType_whenCallsNewMember_thenShouldReceiveANotification() {
        final String expectedName =  "John Doe";
        final var expectedErrorMessage = "'type' should not be null";
        final var expectedErrorCount = 1;

        final var actualException = assertThrows(NotificationException.class,
                () -> CastMember.newMember(expectedName, null));

        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

    }

    @Test
    public void givenAValidCastMember_whenCallUpdate_thenShouldReceiveUpdatedCastMember() {
        final var expectedName = "John Doe";
        final var expectedType = CastMemberType.ACTOR;

        final var actualMember = CastMember.newMember("Lucas", CastMemberType.DIRECTOR);

        final var memberUpdated = actualMember.update(expectedName, expectedType);

        assertNotNull(actualMember);
        assertNotNull(actualMember.getId());
        assertEquals(expectedName, memberUpdated.getName());
        assertEquals(expectedType, memberUpdated.getType());
        assertEquals(actualMember.getCreatedAt(), memberUpdated.getCreatedAt());
        assertTrue(memberUpdated.getUpdatedAt().isAfter(actualMember.getCreatedAt()));

    }

    @Test
    public void givenAInvalidNullName_whenCallUpdate_thenShouldReceiveNotificationException() {
        final String expectedName = null;
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var actualMember = CastMember.newMember("Lucas", CastMemberType.DIRECTOR);

        final var actualException = assertThrows(NotificationException.class,
                () -> actualMember.update(expectedName, expectedType));

        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

    }
    
    @Test
    public void givenAInvalidEmptyName_whenCallUpdate_thenShouldReceiveNotificationException() {
        final String expectedName = " ";
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;

        final var actualMember = CastMember.newMember("Lucas", CastMemberType.DIRECTOR);

        final var actualException = assertThrows(NotificationException.class,
                () -> actualMember.update(expectedName, expectedType));

        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

    }

    @Test
    public void givenAInvalidLengthName_whenCallUpdate_thenShouldReceiveNotificationException() {
        final String expectedName = "a".repeat(256);
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedErrorCount = 1;

        final var actualMember = CastMember.newMember("Lucas", CastMemberType.DIRECTOR);

        final var actualException = assertThrows(NotificationException.class,
                () -> actualMember.update(expectedName, expectedType));

        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

    }

    @Test
    public void givenAValidCastMember_whenCallUpdateWithInvalidType_thenShouldReceiveNotificationException() {
        final String expectedName = "John Doe";
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorMessage = "'type' should not be null";
        final var expectedErrorCount = 1;

        final var actualMember = CastMember.newMember("Lucas", expectedType);

        assertNotNull(actualMember);
        assertNotNull(actualMember.getId());

        final var actualException = assertThrows(NotificationException.class,
                () -> actualMember.update(expectedName, null));

        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

    }


}
