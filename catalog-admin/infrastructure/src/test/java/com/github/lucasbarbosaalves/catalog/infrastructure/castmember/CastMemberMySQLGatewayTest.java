package com.github.lucasbarbosaalves.catalog.infrastructure.castmember;

import com.github.lucasbarbosaalves.catalog.Fixture;
import com.github.lucasbarbosaalves.catalog.MySQLGatewayTest;
import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMember;
import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMemberID;
import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMemberType;
import com.github.lucasbarbosaalves.catalog.domain.pagination.SearchQuery;
import com.github.lucasbarbosaalves.catalog.infrastructure.castmember.persistence.CastMemberJpaEntity;
import com.github.lucasbarbosaalves.catalog.infrastructure.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.github.lucasbarbosaalves.catalog.Fixture.CastMember.type;
import static com.github.lucasbarbosaalves.catalog.Fixture.name;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MySQLGatewayTest
public class CastMemberMySQLGatewayTest {

    @Autowired
    private CastMemberMySQLGateway gateway;

    @Autowired
    private CastMemberRepository repository;

    @Test
    void givenAValidCastMember_whenCallsCreate_shouldPersistIt() {
        final var expectedName = "John Doe";
        final var expectedType = CastMemberType.DIRECTOR;

        CastMember member = CastMember.newMember(expectedName, expectedType);
        final var expectedId = member.getId();

        assertEquals(0, repository.count());

        final var actualMember = gateway.create(CastMember.with(member)); // Cópia defensiva para evitar side effects

        assertEquals(1, repository.count());
        assertEquals(expectedId, actualMember.getId());
        assertEquals(expectedName, actualMember.getName());
        assertEquals(expectedType, actualMember.getType());
        assertEquals(member.getCreatedAt(), actualMember.getCreatedAt());
        assertEquals(member.getUpdatedAt(), actualMember.getUpdatedAt());

        // Evitar usar get() diretamente no Optional da implementação - Nos testes é aceitável.
        CastMemberJpaEntity persistedMember = repository.findById(expectedId.getValue()).get();

        assertEquals(expectedId.getValue(), persistedMember.getId());
        assertEquals(expectedName, persistedMember.getName());
        assertEquals(expectedType, persistedMember.getType());
        assertEquals(member.getCreatedAt(), persistedMember.getCreatedAt());
        assertEquals(member.getUpdatedAt(), persistedMember.getUpdatedAt());
    }

    @Test
    public void givenAValidCastMember_whenCallsDeleteById_shouldDeletedIt() {
        // given
        final var member = CastMember.newMember("Lucas", CastMemberType.ACTOR);
        repository.saveAndFlush(CastMemberJpaEntity.from(member));
        assertEquals(1, repository.count());

        // when
        gateway.deleteById(CastMemberID.from("123"));

        // then
        assertEquals(1, repository.count());
    }
    @Test
    void givenAnInvalidCastMemberId_whenCallsDeleteById_shouldReturnOk() {
        // given
        final var member = CastMember.newMember("Lucas", CastMemberType.ACTOR);
        repository.saveAndFlush(CastMemberJpaEntity.from(member));

        assertEquals(1, repository.count());
        // when
        gateway.deleteById(member.getId());
        // then
        assertEquals(0, repository.count());
    }

    @Test
    void findById() {
    }

    @Test
    public void givenAValidCastMember_whenCallsUpdate_shouldRefreshedIt() {
        final var expectedName = name();
        final var expectedType = CastMemberType.ACTOR;

        CastMember member = CastMember.newMember("Lucas", CastMemberType.DIRECTOR);
        final var expectedId = member.getId();

        final var currentMember = repository.saveAndFlush(CastMemberJpaEntity.from(member));

        assertEquals(1, repository.count());
        assertEquals("Lucas", currentMember.getName());
        assertEquals(CastMemberType.DIRECTOR, currentMember.getType());

        final var actualMember = gateway.update(CastMember.with(member).update(expectedName, expectedType));

        assertEquals(1, repository.count());
        assertEquals(expectedId, actualMember.getId());
        assertEquals(expectedName, actualMember.getName());
        assertEquals(expectedType, actualMember.getType());
        assertEquals(member.getCreatedAt(), actualMember.getCreatedAt());
        assertTrue(member.getUpdatedAt().isBefore(actualMember.getUpdatedAt()));

        CastMemberJpaEntity persistedMember = repository.findById(expectedId.getValue()).get();

        assertEquals(expectedId.getValue(), persistedMember.getId());
        assertEquals(expectedName, persistedMember.getName());
        assertEquals(expectedType, persistedMember.getType());
        assertEquals(member.getCreatedAt(), persistedMember.getCreatedAt());
        assertTrue(member.getUpdatedAt().isBefore(persistedMember.getUpdatedAt()));
    }

    @Test
    public void givenAValidCastMember_whenCallsDeleteById_shouldDeleteIt() {
        // given
        final var aMember = CastMember.newMember(name(), type());

        repository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        assertEquals(1, repository.count());

        // when
        gateway.deleteById(aMember.getId());

        // then
        assertEquals(0, repository.count());
    }

    @Test
    public void givenAnInvalidId_whenCallsDeleteById_shouldBeIgnored() {
        // given
        final var aMember = CastMember.newMember(name(), type());

        repository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        assertEquals(1, repository.count());

        // when
        gateway.deleteById(CastMemberID.from("123"));

        // then
        assertEquals(1, repository.count());
    }

    @Test
    public void givenAValidCastMember_whenCallsFindById_shouldReturnIt() {
        // given
        final var expectedName = name();
        final var expectedType = type();

        final var aMember = CastMember.newMember(expectedName, expectedType);
        final var expectedId = aMember.getId();

        repository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        assertEquals(1, repository.count());

        // when
        final var actualMember = gateway.findById(expectedId).get();

        // then
        assertEquals(expectedId, actualMember.getId());
        assertEquals(expectedName, actualMember.getName());
        assertEquals(expectedType, actualMember.getType());
        assertEquals(aMember.getCreatedAt(), actualMember.getCreatedAt());
        assertEquals(aMember.getUpdatedAt(), actualMember.getUpdatedAt());
    }

    @Test
    public void givenAnInvalidId_whenCallsFindById_shouldReturnEmpty() {
        // given
        final var aMember = CastMember.newMember(name(), type());

        repository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        assertEquals(1, repository.count());

        // when
        final var actualMember = gateway.findById(CastMemberID.from("123"));

        // then
        assertTrue(actualMember.isEmpty());
    }

    @Test
    public void givenEmptyCastMembers_whenCallsFindAll_shouldReturnEmpty() {
        // given
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "name";
        final var expectedDirection = "asc";
        final var expectedTotal = 0;

        final var aQuery =
                new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        // when
        final var actualPage = gateway.findAll(aQuery);

        // then
        assertEquals(expectedPage, actualPage.currentPage());
        assertEquals(expectedPerPage, actualPage.perPage());
        assertEquals(expectedTotal, actualPage.total());
        assertEquals(expectedTotal, actualPage.items().size());
    }

    @ParameterizedTest
    @CsvSource({
            "vin,0,10,1,1,Vin Diesel",
            "taran,0,10,1,1,Quentin Tarantino",
            "jas,0,10,1,1,Jason Momoa",
            "har,0,10,1,1,Kit Harington",
            "MAR,0,10,1,1,Martin Scorsese",
    })
    public void givenAValidTerm_whenCallsFindAll_shouldReturnFiltered(
            final String expectedTerms,
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedName
    ) {
        // given
        mockMembers();

        final var expectedSort = "name";
        final var expectedDirection = "asc";

        final var aQuery =
                new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        // when
        final var actualPage = gateway.findAll(aQuery);

        // then
        assertEquals(expectedPage, actualPage.currentPage());
        assertEquals(expectedPerPage, actualPage.perPage());
        assertEquals(expectedTotal, actualPage.total());
        assertEquals(expectedItemsCount, actualPage.items().size());
        assertEquals(expectedName, actualPage.items().get(0).getName());
    }

    @ParameterizedTest
    @CsvSource({
            "name,asc,0,10,5,5,Jason Momoa",
            "name,desc,0,10,5,5,Vin Diesel",
            "createdAt,asc,0,10,5,5,Kit Harington",
            "createdAt,desc,0,10,5,5,Martin Scorsese",
    })
    public void givenAValidSortAndDirection_whenCallsFindAll_shouldReturnSorted(
            final String expectedSort,
            final String expectedDirection,
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedName
    ) {
        // given
        mockMembers();

        final var expectedTerms = "";

        final var aQuery =
                new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        // when
        final var actualPage = gateway.findAll(aQuery);

        // then
        assertEquals(expectedPage, actualPage.currentPage());
        assertEquals(expectedPerPage, actualPage.perPage());
        assertEquals(expectedTotal, actualPage.total());
        assertEquals(expectedItemsCount, actualPage.items().size());
        assertEquals(expectedName, actualPage.items().get(0).getName());
    }

    @ParameterizedTest
    @CsvSource({
            "0,2,2,5,Jason Momoa;Kit Harington",
            "1,2,2,5,Martin Scorsese;Quentin Tarantino",
            "2,2,1,5,Vin Diesel",
    })
    public void givenAValidPagination_whenCallsFindAll_shouldReturnPaginated(
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedNames
    ) {
        // given
        mockMembers();

        final var expectedTerms = "";
        final var expectedSort = "name";
        final var expectedDirection = "asc";

        final var aQuery =
                new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        // when
        final var actualPage = gateway.findAll(aQuery);

        // then
        assertEquals(expectedPage, actualPage.currentPage());
        assertEquals(expectedPerPage, actualPage.perPage());
        assertEquals(expectedTotal, actualPage.total());
        assertEquals(expectedItemsCount, actualPage.items().size());

        int index = 0;
        for (final var expectedName : expectedNames.split(";")) {
            assertEquals(expectedName, actualPage.items().get(index).getName());
            index++;
        }
    }

    private void mockMembers() {
        repository.saveAllAndFlush(List.of(
                CastMemberJpaEntity.from(CastMember.newMember("Kit Harington", CastMemberType.ACTOR)),
                CastMemberJpaEntity.from(CastMember.newMember("Vin Diesel", CastMemberType.ACTOR)),
                CastMemberJpaEntity.from(CastMember.newMember("Quentin Tarantino", CastMemberType.DIRECTOR)),
                CastMemberJpaEntity.from(CastMember.newMember("Jason Momoa", CastMemberType.ACTOR)),
                CastMemberJpaEntity.from(CastMember.newMember("Martin Scorsese", CastMemberType.DIRECTOR))
        ));
    }
}