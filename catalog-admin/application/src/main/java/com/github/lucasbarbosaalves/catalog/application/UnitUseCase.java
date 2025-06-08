package com.github.lucasbarbosaalves.catalog.application;

public abstract class UnitUseCase<IN> {

    public abstract void execute(IN param);
}
