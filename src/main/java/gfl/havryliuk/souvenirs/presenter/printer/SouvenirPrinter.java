package gfl.havryliuk.souvenirs.presenter.printer;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class SouvenirPrinter<T> extends ConsoleLoggingPrinter<Souvenir> {

    public SouvenirPrinter(List<? extends Entity> entities) {
//        super(entities);
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

        String tableLength = getTableRowLine(getTableLength(tableColumnsLength));

        sb.append(tableLength).append("\n");
        sb.append("| ").append(String.format("%-" + tableColumnsLength.get(0) + "s|", "Name"));
        sb.append(" ").append(String.format("%-" + tableColumnsLength.get(1) + "s|", "Price"));
        sb.append(" ").append(String.format("%-" + tableColumnsLength.get(2) + "s|", "Produced"));
        sb.append("\n");
        sb.append(tableLength).append("\n");

        sb.append(souvenirs.stream().map(p -> {
            sb.append("| ").append(String.format("%-" + tableColumnsLength.get(0) + "s|", p.getName() + " "));
            sb.append(" ").append(String.format("%-" + tableColumnsLength.get(1) + "s|", p.getPrice() + " "));
            sb.append(" ").append(String.format("%-" + tableColumnsLength.get(2) + "s|", p.getProductionDate()
                    .format(DateTimeFormatter.ISO_LOCAL_DATE) + " "));
            sb.append("\n");
            return "";
        }).collect(Collectors.joining()));
        sb.append(tableLength).append("\n");
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

}
