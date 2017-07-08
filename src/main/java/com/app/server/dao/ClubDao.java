package com.app.server.dao;

import com.app.shared.Club;
import com.app.shared.Player;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Александр on 07.07.2017.
 */
public class ClubDao {
    private final Session session;

    public ClubDao(Session session) {
        this.session = session;
    }

    public List<Club> getClubs() {
        return (List<Club>) session.createQuery("from Club").list();
    }

    public Club getClub(int id) {
        return session.load(Club.class, id);
    }

    public void insertClub(Club club) {
        session.save(club);
    }

    public void updateClub(Club club) {
        session.update(club);
    }

    public void deleteClub(int id) {
        session.delete(getClub(id));
    }

    public List<Player> getPlayersForClub(Club club) {
        Query query = session.createQuery("from Player p where p.clubId = :ID")
                .setParameter("ID", club.getId());
        return query.list();
    }
}
