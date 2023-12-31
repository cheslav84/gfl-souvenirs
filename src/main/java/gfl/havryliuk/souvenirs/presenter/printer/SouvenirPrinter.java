package gfl.havryliuk.souvenirs.presenter.printer;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class SouvenirPrinter<T> extends ConsoleLoggingPrinter<Souvenir> {

    public SouvenirPrinter(List<? extends Entity> entities) {
        super((List<Souvenir>) entities);
    }

    @Override
    public void print() {
        String collect = entities.stream()
                .collect(entitiesCollector());
        log.info("{}", collect);
    }


    @Override
    public StringBuilder getString(StringBuilder sb, List<Souvenir> souvenirs) {


        List<Integer> tableColumnsLength = new ArrayList<>();
        tableColumnsLength.add(getColumnLength(souvenirs, getNameFieldLength()));
        tableColumnsLength.add(getColumnLength(souvenirs, getPriceFieldLength()));
        tableColumnsLength.add(getColumnLength(souvenirs, getProductionDateFieldLength()));
        tableColumnsLength.add(getColumnLength(souvenirs, getProducerNameFieldLength()));

        String rowLine = getTableRowLine(getTableLength(tableColumnsLength));

        sb.append(rowLine).append("\n");
        sb.append("| ").append(String.format("%-" + tableColumnsLength.get(0) + "s|", "Name"));
        sb.append(" ").append(String.format("%-" + tableColumnsLength.get(1) + "s|", "Price"));
        sb.append(" ").append(String.format("%-" + tableColumnsLength.get(2) + "s|", "Produced"));
        sb.append(" ").append(String.format("%-" + tableColumnsLength.get(3) + "s|", "Producer"));
        sb.append("\n");
        sb.append(rowLine).append("\n");

        sb.append(souvenirs.stream().map(p -> {
            sb.append("| ").append(String.format("%-" + tableColumnsLength.get(0) + "s|", p.getName() + " "));
            sb.append(" ").append(String.format("%-" + tableColumnsLength.get(1) + "s|", p.getPrice() + " "));
            sb.append(" ").append(String.format("%-" + tableColumnsLength.get(2) + "s|", p.getProductionDate()
                    .format(DateTimeFormatter.ISO_LOCAL_DATE) + " "));
            sb.append(" ").append(String.format("%-" + tableColumnsLength.get(3) + "s|", p.getProducer().getName() + " "));
            sb.append("\n");
            return "";
        }).collect(Collectors.joining()));
        sb.append(rowLine).append("\n");
        return sb;
    }

    private static Function<Souvenir, Integer> getNameFieldLength() {
        return e -> (e.getName()).length();
    }

    private static Function<Souvenir, Integer> getPriceFieldLength() {
        return e -> String.valueOf(e.getPrice()).length();
    }

    private static Function<Souvenir, Integer> getProductionDateFieldLength() {
        return e -> (e.getProductionDate().format(DateTimeFormatter.ISO_LOCAL_DATE)).length();
    }

    private static Function<Souvenir, Integer> getProducerNameFieldLength() {
        return e -> String.valueOf(e.getProducer().getName()).length();
    }

}
