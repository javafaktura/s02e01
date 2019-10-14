package io.github.javafaktura.s02e01.logic;

import java.util.Map.Entry;
import java.util.Optional;

import static java.util.Map.Entry.comparingByValue;
import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

public class SomeService {

    private final SomeRepository repository;

    public SomeService(SomeRepository repository) {
        this.repository = requireNonNull(repository, "repository cannot be null");
    }

    public Optional<String> mostPopularValue() {
        return repository.fetchData().stream()
                .collect(collectingAndThen(
                        groupingBy(identity(), counting()),
                        map -> map.entrySet().stream()
                                .max(comparingByValue())
                                .map(Entry::getKey)));
    }
}
