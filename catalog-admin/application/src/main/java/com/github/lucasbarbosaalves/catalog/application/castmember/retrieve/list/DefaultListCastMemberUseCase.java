package com.github.lucasbarbosaalves.catalog.application.castmember.retrieve.list;

import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMemberGateway;
import com.github.lucasbarbosaalves.catalog.domain.pagination.Pagination;
import com.github.lucasbarbosaalves.catalog.domain.pagination.SearchQuery;

import java.util.Objects;

public final class DefaultListCastMemberUseCase extends ListCastMemberUseCase {

    private final CastMemberGateway castMemberGateway;

    public DefaultListCastMemberUseCase(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Override
    public Pagination<CastMemberListOutput> execute(SearchQuery query) {
        return this.castMemberGateway.findAll(query)
                .map(CastMemberListOutput::from);
    }
}
