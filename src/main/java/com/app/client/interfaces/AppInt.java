package com.app.client.interfaces;

import com.app.shared.Player;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("MySampleApplicationService")
public interface AppInt extends RemoteService, Common {
    Player addPlayer(Player player);

    List<Player> getPlayers();

    void updatePlayer(Player player);

    void deletePlayer(Player player);
}
