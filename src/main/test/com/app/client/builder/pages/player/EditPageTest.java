package com.app.client.builder.pages.player;


import com.app.client.builder.helper.Helper;
import com.app.server.impl.PlayerImpl;
import com.app.shared.Player;
import org.junit.Test;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class EditPageTest {

    Helper helper = new Helper();
    PlayerImpl app = new PlayerImpl();

    @Test
    public void test() {
        List<Player> players = app.getPlayers();

        for (Player player : players) {
            System.out.println(player.getDateBirth());
            String birth = helper.formatDate(String.valueOf(player.getDateBirth()));
            System.out.println(birth);
            player.setDateBirth(new Date("1"));
            System.out.println(player.getDateBirth());
            System.out.println("----------");
        }
    }
}