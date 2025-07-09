package com.github.lucasbarbosaalves.catalog.application.castmember.delete;

import com.github.lucasbarbosaalves.catalog.application.UnitUseCase;

public sealed abstract class DeleteCastMemberUseCase extends UnitUseCase<String> permits DefaultDeleteCastMemberUseCase {
}
