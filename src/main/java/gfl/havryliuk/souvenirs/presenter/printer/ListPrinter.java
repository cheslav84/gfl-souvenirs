//package gfl.havryliuk.souvenirs.presenter.printer;
//
//import gfl.havryliuk.souvenirs.entities.Producer;
//import org.apache.logging.log4j.util.Strings;
//
//import java.util.*;
//import java.util.function.BiConsumer;
//import java.util.function.BinaryOperator;
//import java.util.function.Function;
//import java.util.function.Supplier;
//import java.util.stream.*;
//
//
//public class ListPrinter {
//
//    private List<Producer> producers;
//
//    protected ListPrinter(List<Producer> producers) {
//        this.producers = producers;
//    }
//
//
//
//    public Collector<Producer, StringBuilder, String> producerListCollector() {
//            final List<Producer> list = new ArrayList<>();
//            final StringBuilder res = new StringBuilder();
//            return new Collector<>() {
//                @Override
//                public Supplier<StringBuilder> supplier() {
//                    return () -> res;
//                }
//
//                @Override
//                public BiConsumer<StringBuilder, Producer> accumulator() {
//                    return (s, producers) -> list.add(producers);
//                }
//
//                @Override
//                public BinaryOperator<StringBuilder> combiner() {
//                    return null;
//                }
//
//                @Override
//                public Function<StringBuilder, String> finisher() {
//                    return s -> new String(getString(res, list));
//                }
//
//                @Override
//                public Set<Characteristics> characteristics() {
//                    return Set.of(Characteristics.UNORDERED);
//                }
//            };
//        }
//
//        private StringBuilder getString(StringBuilder sb, List<Producer> list) {
//            int firstColonLength = list.stream()
//                    .map(e -> (e.getName()).length())
//                    .mapToInt(e -> e + 1)
//                    .max()
//                    .orElse(10);
//
//            int secondCononLength = list.stream()
//                    .map(e -> (e.getCountry()).length())
//                    .mapToInt(e -> e + 1)
//                    .max()
//                    .orElse(10);
//
//            sb.append(Strings.repeat(Character.toString('—'), firstColonLength + secondCononLength + 5)).append("\n");
//            sb.append("| ");
//            sb.append(String.format("%-" + firstColonLength + "s|", "Name"));
//            sb.append(" ");
//            sb.append(String.format("%-" + secondCononLength + "s|", "Country"));
//            sb.append("\n");
//            sb.append(Strings.repeat(Character.toString('—'), firstColonLength + secondCononLength + 5)).append("\n");
//
//            sb.append(producers.stream().map(p -> {
//                sb.append("| ");
//                sb.append(String.format("%-" + firstColonLength + "s|", p.getName() + " "));
//                sb.append(" ");
//                sb.append(String.format("%-" + secondCononLength + "s|", p.getCountry() + " "));
//                sb.append("\n");
//                return "";
//            }).collect(Collectors.joining()));
//            sb.append(Strings.repeat(Character.toString('—'), firstColonLength + secondCononLength + 5)).append("\n");
//            return sb;
//        }
//
//}
