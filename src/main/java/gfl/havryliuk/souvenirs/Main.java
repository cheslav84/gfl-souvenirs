package gfl.havryliuk.souvenirs;

import gfl.havryliuk.souvenirs.presenter.action.menu.InitMenu;
import gfl.havryliuk.souvenirs.presenter.action.menu.MainMenu;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Main {


    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {
        log.warn("Welcome to souvenir store!");

        try {
            new InitMenu().execute();
            while (true) {
                new MainMenu().execute();
            }
        } catch (Exception e) {
            showMessage();
        }

    }

    private static void showMessage() {
        log.warn("""
                An error have occurred.\s
                The developer is deeply concern about this and will fix one as soon as possible!\s
                Try please again.""");
        System.exit(0);
    }


}
