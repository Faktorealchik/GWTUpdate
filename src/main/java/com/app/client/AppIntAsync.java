package com.app.client;

import com.app.shared.Player;
import com.app.shared.Club;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Date;
import java.util.List;

public interface AppIntAsync {
    void addPlayer(String lastName, String firstName, String secondName, Date dateBirth, int clubId, AsyncCallback<Player> async);

    void getPlayers(AsyncCallback<List<Player>> async);

    void deletePlayer(Player player, AsyncCallback<Void> async);

    void updatePlayer(Player player, AsyncCallback<Void> async);

    void getClubName(int clubId, AsyncCallback<Club> async);
}
