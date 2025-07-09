package com.github.lucasbarbosaalves.catalog.application.castmember.retrieve.list;

import com.github.lucasbarbosaalves.catalog.application.UseCase;
import com.github.lucasbarbosaalves.catalog.domain.pagination.Pagination;
import com.github.lucasbarbosaalves.catalog.domain.pagination.SearchQuery;

public sealed abstract class ListCastMemberUseCase extends UseCase<SearchQuery, Pagination<CastMemberListOutput>>
        permits DefaultListCastMemberUseCase {
}
