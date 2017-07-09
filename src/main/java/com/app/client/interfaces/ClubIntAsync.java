package com.app.client.interfaces;

import com.app.shared.Club;
import com.app.shared.Player;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface ClubIntAsync {
    void getClubs(AsyncCallback<List<Club>> async);

    void getClub(int id, AsyncCallback<Club> async);

    void insertClub(Club club, AsyncCallback<Void> async);

    void updateClub(Club club, AsyncCallback<Void> async);

    void deleteClub(int id, AsyncCallback<Void> async);

    void getPlayersForClub(Club club, AsyncCallback<List<Player>> async);

    void addClub(String name, AsyncCallback<Club> async);
}
