package com.app.client.dbService;

import com.app.server.ClubImpl;
import com.app.shared.Club;
import com.app.shared.Player;
import org.junit.Test;

import java.util.List;

public class DBServiceTest {


    @Test
    public void test() {
        ClubImpl club = new ClubImpl();
        Club c = club.getClub(1);
        System.out.println(c.getName());
        List<Player> playersForClub = club.getPlayersForClub(c);
        System.out.println(playersForClub);
    }
}