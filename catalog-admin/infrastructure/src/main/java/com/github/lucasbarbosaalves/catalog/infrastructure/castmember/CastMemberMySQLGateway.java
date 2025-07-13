package com.github.lucasbarbosaalves.catalog.infrastructure.castmember;

import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMember;
import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMemberGateway;
import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMemberID;
import com.github.lucasbarbosaalves.catalog.domain.pagination.Pagination;
import com.github.lucasbarbosaalves.catalog.domain.pagination.SearchQuery;
import com.github.lucasbarbosaalves.catalog.infrastructure.castmember.persistence.CastMemberJpaEntity;
import com.github.lucasbarbosaalves.catalog.infrastructure.castmember.persistence.CastMemberRepository;
import com.github.lucasbarbosaalves.catalog.infrastructure.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
public class CastMemberMySQLGateway implements CastMemberGateway {

    private final CastMemberRepository castMemberRepository;

    public CastMemberMySQLGateway(final CastMemberRepository castMemberRepository) {
        this.castMemberRepository = castMemberRepository;
    }

    @Override
    public CastMember create(final CastMember castMember) {
        return save(castMember);
    }


    @Override
    public void deleteById(final CastMemberID memberID) {
        String id = memberID.getValue();
        if (this.castMemberRepository.existsById(id)) {
            this.castMemberRepository.deleteById(id);
        }
    }

    @Override
    public Optional<CastMember> findById(final CastMemberID id) {
        return this.castMemberRepository.findById(id.getValue())
                .map(CastMemberJpaEntity::toAggregate);
    }

    @Override
    public CastMember update(final CastMember castMember) {
        return save(castMember);
    }

    @Override
    public Pagination<CastMember> findAll(final SearchQuery query) {
        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );

        final var where = Optional.ofNullable(query.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);

        final var pageResult = this.castMemberRepository.findAll(where, page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(CastMemberJpaEntity::toAggregate).toList()
        );
    }

    private Specification<CastMemberJpaEntity> assembleSpecification(final String terms) {
        return SpecificationUtils.like("name", terms);
    }

    private CastMember save(CastMember castMember) {
        return this.castMemberRepository.save(CastMemberJpaEntity.from(castMember)).toAggregate();
    }

    @Override
    public List<CastMemberID> existsByIds(final Iterable<CastMemberID> castMemberIDS) {
        final var ids = StreamSupport.stream(castMemberIDS.spliterator(), false)
                .map(CastMemberID::getValue)
                .toList();
        return this.castMemberRepository.existsByIds(ids).stream()
                .map(CastMemberID::from)
                .toList();
    }
}
