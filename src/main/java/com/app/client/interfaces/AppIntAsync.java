package com.app.client.interfaces;

import com.app.shared.Club;
import com.app.shared.Player;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface AppIntAsync {
    void addPlayer(Player player, AsyncCallback<Player> async);

    void getPlayers(AsyncCallback<List<Player>> async);

    void deletePlayer(Player player, AsyncCallback<Void> async);

    void updatePlayer(Player player, AsyncCallback<Void> async);

    void getClub(int clubId, AsyncCallback<Club> async);

    void getClubs(AsyncCallback<List<Club>> async);

}
