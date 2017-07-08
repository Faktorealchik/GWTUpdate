package com.app.client;

import com.app.shared.Club;
import com.app.shared.Player;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("Club")
public interface ClubInt extends RemoteService {
    List<Club> getClubs();

    Club getClub(int id);

    void insertClub(Club club);

    void updateClub(Club club);

    void deleteClub(int id);

    List<Player> getPlayersForClub(Club club);
}
