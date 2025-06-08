package com.github.lucasbarbosaalves.catalog.application;

/**
 * Abstract class representing a use case in the application layer.
 * It defines the input and output types for the use case.
 *
 * @param <IN>  The type of input for the use case.
 * @param <OUT> The type of output from the use case.
 */
public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN param);

}