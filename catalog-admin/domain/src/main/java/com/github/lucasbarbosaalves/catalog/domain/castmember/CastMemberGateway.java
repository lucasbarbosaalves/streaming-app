package com.github.lucasbarbosaalves.catalog.domain.castmember;

import com.github.lucasbarbosaalves.catalog.domain.pagination.Pagination;
import com.github.lucasbarbosaalves.catalog.domain.pagination.SearchQuery;

import java.util.Optional;

public interface CastMemberGateway {

    CastMember create(CastMember castMember);

    void deleteById(CastMemberID id);

    Optional<CastMember> findById(CastMemberID id);

    CastMember update(CastMember castMember);

    Pagination<CastMember> findAll(SearchQuery query);
}
