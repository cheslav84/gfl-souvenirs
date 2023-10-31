package gfl.havryliuk.souvenirs.presenter.printer;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.entities.dto.SouvenirsByYearDto;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class SouvenirsByYearPrinter<T> extends ConsoleLoggingPrinter<SouvenirsByYearDto> {

    public SouvenirsByYearPrinter(List<? extends Entity> entities) {
//        super(entities);
        super((List<SouvenirsByYearDto>) entities);
    }

    @Override
    public void print() {
        String collect = entities.stream()
                .collect(entitiesCollector());
        log.info("{}", collect);
    }


    @Override
    public StringBuilder getString(StringBuilder sb, List<SouvenirsByYearDto> souvenirsByYear) {


        List<Souvenir> allSouvenirs = souvenirsByYear.stream()
                .flatMap(p -> p.getSouvenirs().stream())
                .collect(Collectors.toList());

        List<Integer> tableColumnsLength = new ArrayList<>();

        tableColumnsLength.add(getSouvenirsColumnLength(allSouvenirs, getSouvenirNameFieldLength()));
        tableColumnsLength.add(getSouvenirsColumnLength(allSouvenirs, getPriceFieldLength()));
//        tableColumnsLength.add(getSouvenirsColumnLength(allSouvenirs, getProductionDateFieldLength()));

        String rowLine = getTableRowLine(getTableLength(tableColumnsLength));

        sb.append(rowLine).append("\n");

        sb.append(souvenirsByYear.stream().map(sy -> {
            sb.append("| ").append(formatYearRow(sy, tableColumnsLength)).append("\n");
            sb.append(rowLine).append("\n");
            sb.append("| ").append(String.format("%-" + tableColumnsLength.get(0) + "s|", "Name"));
            sb.append(" ").append(String.format("%-" + tableColumnsLength.get(1) + "s|", "Price"));
            List<Souvenir> souvenirs = sy.getSouvenirs();
                sb.append(souvenirs.stream().map(s -> {
                sb.append("\n");
                sb.append(rowLine).append("\n");
                sb.append("| ").append(String.format("%-" + tableColumnsLength.get(0) + "s|", s.getName() + " "));
                sb.append(" ").append(String.format("%-" + tableColumnsLength.get(1) + "s|", s.getPrice() + " "));

//                sb.append(" ").append(String.format("%-" + tableColumnsLength.get(4) + "s|", s.getProductionDate()
//                        .format(DateTimeFormatter.ISO_LOCAL_DATE) + " "));
                return "";
            }).collect(Collectors.joining()));
                sb.append("\n").append(rowLine).append("\n");
            return "";
        }).collect(Collectors.joining()));
        return sb;
    }

    private String formatYearRow(SouvenirsByYearDto sy, List<Integer> tableColumnsLength) {
        return String.format("%-" + (getTableLength(tableColumnsLength) - tableColumnsLength.size() - 1)
                + "s|", "Production year: " + sy.getProductionYear() + " ");
    }

    private static Function<Souvenir, Integer> getSouvenirNameFieldLength() {
        return e -> (e.getName()).length();
    }

    private static Function<Souvenir, Integer> getPriceFieldLength() {
        return e -> String.valueOf(e.getPrice()).length();
    }

//    private static Function<Souvenir, Integer> getProductionDateFieldLength() {
//        return e -> (e.getProductionDate().format(DateTimeFormatter.ISO_LOCAL_DATE)).length();
//    }

    protected int getSouvenirsColumnLength(List<Souvenir> producers, Function<Souvenir, Integer> field) {
        return producers.stream()
                .map(field)
                .mapToInt(e -> e + 1)
                .max()
                .orElse(10);
    }
}
