package gfl.havryliuk.souvenirs.presenter.printer;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.entities.Producer;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class ProducerPrinter<T> extends ConsoleLoggingPrinter<Producer> {

    public ProducerPrinter(List<? extends Entity> entities) {
        super((List<Producer>) entities);
    }

    @Override
    public void print() {
        String collect = entities.stream()
                .collect(entitiesCollector());
        log.info("{}", collect);
    }


    @Override
    public StringBuilder getString(StringBuilder sb, List<Producer> producers) {

        List<Integer> tableColumnsLength = new ArrayList<>();
        tableColumnsLength.add(getColumnLength(producers, getNameFieldLength()));
        tableColumnsLength.add(getColumnLength(producers, getCountryFieldLength()));

        String rowLine = getTableRowLine(getTableLength(tableColumnsLength));

        sb.append(rowLine).append("\n");
        sb.append("| ").append(String.format("%-" + tableColumnsLength.get(0) + "s|", "Name"));
        sb.append(" ").append(String.format("%-" + tableColumnsLength.get(1) + "s|", "Country"));
        sb.append("\n");
        sb.append(rowLine).append("\n");

        sb.append(producers.stream().map(p -> {
            sb.append("| ").append(String.format("%-" + tableColumnsLength.get(0) + "s|", p.getName() + " "));
            sb.append(" ").append(String.format("%-" + tableColumnsLength.get(1) + "s|", p.getCountry() + " "));
            sb.append("\n");
            return "";
        }).collect(Collectors.joining()));
        sb.append(rowLine).append("\n");
        return sb;
    }


    private Function<Producer, Integer> getNameFieldLength() {
        return e -> (e.getName()).length();
    }

    private Function<Producer, Integer> getCountryFieldLength() {
        return e -> (e.getCountry()).length();
    }

}
