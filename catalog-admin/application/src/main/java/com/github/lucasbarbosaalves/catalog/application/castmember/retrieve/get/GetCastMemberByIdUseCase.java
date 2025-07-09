package com.github.lucasbarbosaalves.catalog.application.castmember.retrieve.get;

import com.github.lucasbarbosaalves.catalog.application.UseCase;

public sealed abstract class GetCastMemberByIdUseCase
        extends UseCase<String, CastMemberOutput>
        permits DefaultGetCastMemberByIdUseCase
{
}
