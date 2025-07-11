package com.github.lucasbarbosaalves.catalog.infrastructure.castmember.presenters;

import com.github.lucasbarbosaalves.catalog.application.castmember.retrieve.get.CastMemberOutput;
import com.github.lucasbarbosaalves.catalog.application.castmember.retrieve.list.CastMemberListOutput;
import com.github.lucasbarbosaalves.catalog.infrastructure.castmember.models.CastMemberListResponse;
import com.github.lucasbarbosaalves.catalog.infrastructure.castmember.models.CastMemberResponse;

public interface CastMemberPresenter {

    static CastMemberResponse present(final CastMemberOutput aMember) {
        return new CastMemberResponse(
                aMember.id(),
                aMember.name(),
                aMember.type().name(),
                aMember.createdAt().toString(),
                aMember.updatedAt().toString()
        );
    }

    static CastMemberListResponse present(final CastMemberListOutput aMember) {
        return new CastMemberListResponse(
                aMember.id(),
                aMember.name(),
                aMember.type().name(),
                aMember.createdAt().toString()
        );
    }
}