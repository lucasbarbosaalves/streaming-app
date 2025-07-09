package com.github.lucasbarbosaalves.catalog.application.castmember.update;

import com.github.lucasbarbosaalves.catalog.application.UseCase;

public sealed abstract class UpdateCastMemberUseCase extends UseCase<UpdateCastMemberCommand, UpdateCastMemberOutput> permits DefaultUpdateCastMemberUseCase {
}façanha: criou comandos, saídas e lógica
