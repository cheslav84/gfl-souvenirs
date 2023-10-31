package gfl.havryliuk.souvenirs.presenter.printer;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.entities.Producer;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

@Setter
public abstract class ConsoleLoggingPrinter<T extends Entity> {

    protected List<T> entities;

    public ConsoleLoggingPrinter(List<T> entities) {
        this.entities = entities;
    }

    public abstract StringBuilder getString(StringBuilder sb, List<T> entities);

    public abstract void print();

    public Collector<T, StringBuilder, String> entitiesCollector() {
        final List<T> entities = new ArrayList<>();
        final StringBuilder res = new StringBuilder();
        return new Collector<>() {
            @Override
            public Supplier<StringBuilder> supplier() {
                return () -> res;
            }

            @Override
            public BiConsumer<StringBuilder, T> accumulator() {
                return (s, entity) -> entities.add(entity);
            }

            @Override
            public BinaryOperator<StringBuilder> combiner() {
                return null;
            }

            @Override
            public Function<StringBuilder, String> finisher() {
                return s -> new String(getString(res, entities));
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of(Characteristics.UNORDERED);
            }
        };
    }

    protected int getTableLength(List<Integer> tableColumnsLength) {
        int tableLength = tableColumnsLength.stream()
                .mapToInt(Integer::intValue)
                .sum();
        return tableLength + tableColumnsLength.size() * 2 + 1;
    }

    protected int getColumnLength(List<T> producers, Function<T, Integer> field) {
        return producers.stream()
                .map(field)
                .mapToInt(e -> e + 1)
                .max()
                .orElse(10);
    }

    protected String getTableRowLine(int tableLength) {
        return Strings.repeat(Character.toString('—'), tableLength);
    }

}
