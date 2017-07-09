package com.app.client.interfaces;

import com.app.shared.Club;
import com.app.shared.Player;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("Club")
public interface ClubInt extends RemoteService, Common {

    void insertClub(Club club);

    void updateClub(Club club);

    void deleteClub(int id);

    List<Player> getPlayersForClub(Club club);

    Club addClub(String name);
}
