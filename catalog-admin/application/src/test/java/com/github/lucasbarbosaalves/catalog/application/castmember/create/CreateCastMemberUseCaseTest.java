package com.github.lucasbarbosaalves.catalog.application.castmember.create;

import com.github.lucasbarbosaalves.catalog.application.Fixture;
import com.github.lucasbarbosaalves.catalog.application.UseCaseTest;
import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMemberGateway;
import com.github.lucasbarbosaalves.catalog.domain.exception.NotificationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class CreateCastMemberUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateCastMemberUseCase useCase;

    @Mock
    private CastMemberGateway castMemberGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(castMemberGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsCreateCastMember_shouldReturnIt() {
        // Given
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMembers.type();

        final var aCommand = CreateCastMemberCommand.with(expectedName, expectedType);

        when(castMemberGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        // When
        final var actualOutput = useCase.execute(aCommand);

        //Then
        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(castMemberGateway).create(argThat(member ->
                Objects.equals(member.getName(), expectedName) &&
                        Objects.equals(member.getType(), expectedType) &&
                        Objects.nonNull(member.getId()) &&
                        Objects.nonNull(member.getCreatedAt()) &&
                        Objects.nonNull(member.getUpdatedAt())
        ));
    }

    @Test
    public void givenAInvalidEmptyName_whenCallsCreatCastMember_thenShouldReturnNotificationException() {
        //given
        final String expectedName = " ";
        final var expectedType = Fixture.CastMembers.type();
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;

        final var aCommand =
                CreateCastMemberCommand.with(expectedName, expectedType);

        //when
        final var actualException = assertThrows(NotificationException.class, () -> useCase.execute(aCommand));

        //then
        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

        verify(castMemberGateway, times(0)).create(any());
    }


    @Test
    public void givenAInvalidNullName_whenCallsCreateCastMember_thenShouldReturnNotificationException() {
        //given
        final String expectedName = null;
        final var expectedType = Fixture.CastMembers.type();
        
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand =
                CreateCastMemberCommand.with(expectedName, expectedType);

        //when
        final var actualException = assertThrows(NotificationException.class, () -> useCase.execute(aCommand));

        //then
        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

        verify(castMemberGateway, times(0)).create(any());
    }

    @Test
    public void givenAInvalidNameGreaterThan255_whenCallsCreateCastMember_thenShouldReturnNotificationException() {
        //given
        final String expectedName = "n".repeat(256);
        final var expectedType = Fixture.CastMembers.type();
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedErrorCount = 1;

        final var aCommand =
                CreateCastMemberCommand.with(expectedName, expectedType);

        //when
        final var actualException = assertThrows(NotificationException.class, () -> useCase.execute(aCommand));

        //then
        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

        verify(castMemberGateway, times(0)).create(any());
    }


    @Test
    public void givenAInvalidType_whenCallsCreateCastMember_thenShouldReturnNotificationException() {
        //given
        final String expectedName = "John Doe";
        final var expectedErrorMessage = "'type' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand =
                CreateCastMemberCommand.with(expectedName, null);

        //when
        final var actualException = assertThrows(NotificationException.class, () -> useCase.execute(aCommand));

        //then
        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

        verify(castMemberGateway, times(0)).create(any());
    }
}
