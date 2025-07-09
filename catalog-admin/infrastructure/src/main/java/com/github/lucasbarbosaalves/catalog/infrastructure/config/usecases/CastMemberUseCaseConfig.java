package com.github.lucasbarbosaalves.catalog.infrastructure.config.usecases;

import com.github.lucasbarbosaalves.catalog.application.castmember.create.CreateCastMemberUseCase;
import com.github.lucasbarbosaalves.catalog.application.castmember.create.DefaultCreateCastMemberUseCase;
import com.github.lucasbarbosaalves.catalog.application.castmember.delete.DefaultDeleteCastMemberUseCase;
import com.github.lucasbarbosaalves.catalog.application.castmember.delete.DeleteCastMemberUseCase;
import com.github.lucasbarbosaalves.catalog.application.castmember.retrieve.get.DefaultGetCastMemberByIdUseCase;
import com.github.lucasbarbosaalves.catalog.application.castmember.retrieve.get.GetCastMemberByIdUseCase;
import com.github.lucasbarbosaalves.catalog.application.castmember.retrieve.list.DefaultListCastMemberUseCase;
import com.github.lucasbarbosaalves.catalog.application.castmember.retrieve.list.ListCastMemberUseCase;
import com.github.lucasbarbosaalves.catalog.application.castmember.update.DefaultUpdateCastMemberUseCase;
import com.github.lucasbarbosaalves.catalog.application.castmember.update.UpdateCastMemberUseCase;
import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMemberGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class CastMemberUseCaseConfig {

    private final CastMemberGateway castMemberGateway;

    public CastMemberUseCaseConfig(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Bean
    public CreateCastMemberUseCase createCastMemberUseCase() {
        return new DefaultCreateCastMemberUseCase(castMemberGateway);
    }

    @Bean
    public DeleteCastMemberUseCase deleteCastMemberUseCase() {
        return new DefaultDeleteCastMemberUseCase(castMemberGateway);
    }

    @Bean
    public GetCastMemberByIdUseCase getCastMemberByIdUseCase() {
        return new DefaultGetCastMemberByIdUseCase(castMemberGateway);
    }

    @Bean
    public ListCastMemberUseCase listCastMemberUseCase() {
        return new DefaultListCastMemberUseCase(castMemberGateway);
    }

    @Bean
    public UpdateCastMemberUseCase updateCastMemberUseCase() {
        return new DefaultUpdateCastMemberUseCase(castMemberGateway);
    }

}
