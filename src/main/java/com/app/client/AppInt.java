package com.app.client;

import com.app.shared.Player;
import com.app.shared.Club;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.Date;
import java.util.List;

@RemoteServiceRelativePath("MySampleApplicationService")
public interface AppInt extends RemoteService {
    Player addPlayer(String lastName, String firstName, String secondName, Date dateBirth, int clubId);

    List<Player> getPlayers();

    void updatePlayer(Player player);

    void deletePlayer(Player player);

    Club getClubName(int clubId);
}
