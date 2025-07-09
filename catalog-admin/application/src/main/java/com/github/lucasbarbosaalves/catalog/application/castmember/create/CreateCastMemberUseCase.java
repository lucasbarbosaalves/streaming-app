package com.github.lucasbarbosaalves.catalog.application.castmember.create;

import com.github.lucasbarbosaalves.catalog.application.UseCase;

public sealed abstract class CreateCastMemberUseCase extends UseCase<CreateCastMemberCommand, CreateCastMemberOutput> permits DefaultCastMemberUseCase {
}
