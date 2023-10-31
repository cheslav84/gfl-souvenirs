package gfl.havryliuk.souvenirs.presenter.printer;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class ProducerWithSouvenirsPrinter<T> extends ConsoleLoggingPrinter<Producer> {

    public ProducerWithSouvenirsPrinter(List<? extends Entity> entities) {
//        super(entities);
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


        List<Souvenir> allSouvenirs = producers.stream()
                .flatMap(p -> p.getSouvenirs().stream())
                .collect(Collectors.toList());

        List<Integer> tableColumnsLength = new ArrayList<>();
        tableColumnsLength.add(getColumnLength(producers, getProducerNameFieldLength()));
        tableColumnsLength.add(getColumnLength(producers, getCountryFieldLength()));
        tableColumnsLength.add(getSouvenirsColumnLength(allSouvenirs, getSouvenirNameFieldLength()));
        tableColumnsLength.add(getSouvenirsColumnLength(allSouvenirs, getPriceFieldLength()));
        tableColumnsLength.add(getSouvenirsColumnLength(allSouvenirs, getProductionDateFieldLength()));

        String rowLine = getTableRowLine(getTableLength(tableColumnsLength));

        sb.append(rowLine).append("\n");
        sb.append("| ").append(String.format("%-" + tableColumnsLength.get(0) + "s|", "Name"));
        sb.append(" ").append(String.format("%-" + tableColumnsLength.get(1) + "s|", "Country"));
        sb.append(" ").append(String.format("%-" + tableColumnsLength.get(2) + "s|", "Souvenir name"));
        sb.append(" ").append(String.format("%-" + tableColumnsLength.get(3) + "s|", "Price"));
        sb.append(" ").append(String.format("%-" + tableColumnsLength.get(4) + "s|", "Produced"));
        sb.append("\n");
        sb.append(rowLine).append("\n");

        sb.append(producers.stream().map(p -> {
            sb.append("| ").append(String.format("%-" + tableColumnsLength.get(0) + "s|", p.getName() + " "));
            sb.append(" ").append(String.format("%-" + tableColumnsLength.get(1) + "s|", p.getCountry() + " "));
            List<Souvenir> souvenirs = p.getSouvenirs();

            sb.append(souvenirs.stream().map(s -> {
                sb.append("\n");
                sb.append("| ").append(String.format("%-" + tableColumnsLength.get(0) + "s|", " "));
                sb.append(" ").append(String.format("%-" + tableColumnsLength.get(1) + "s|", " "));
                sb.append(" ").append(String.format("%-" + tableColumnsLength.get(2) + "s|", s.getName() + " "));
                sb.append(" ").append(String.format("%-" + tableColumnsLength.get(3) + "s|", s.getPrice() + " "));
                sb.append(" ").append(String.format("%-" + tableColumnsLength.get(4) + "s|", s.getProductionDate()
                        .format(DateTimeFormatter.ISO_LOCAL_DATE) + " "));
                return "";
            }).collect(Collectors.joining()));
            sb.append("\n");
            return "";
        }).collect(Collectors.joining()));
        sb.append(rowLine).append("\n");
        return sb;
    }

    private Function<Producer, Integer> getProducerNameFieldLength() {
        return e -> (e.getName()).length();
    }

    private Function<Producer, Integer> getCountryFieldLength() {
        return e -> (e.getCountry()).length();
    }

    private static Function<Souvenir, Integer> getSouvenirNameFieldLength() {
        return e -> (e.getName()).length();
    }

    private static Function<Souvenir, Integer> getPriceFieldLength() {
        return e -> String.valueOf(e.getPrice()).length();
    }

    private static Function<Souvenir, Integer> getProductionDateFieldLength() {
        return e -> (e.getProductionDate().format(DateTimeFormatter.ISO_LOCAL_DATE)).length();
    }

    protected int getSouvenirsColumnLength(List<Souvenir> producers, Function<Souvenir, Integer> field) {
        return producers.stream()
                .map(field)
                .mapToInt(e -> e + 1)
                .max()
                .orElse(10);
    }
}
