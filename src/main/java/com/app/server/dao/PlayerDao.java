package com.app.server.dao;

import com.app.shared.Player;
import org.hibernate.Session;

import java.util.List;

public class PlayerDao {
    private final Session session;

    public PlayerDao(Session session) {
        this.session = session;
    }

    public List<Player> getPlayers() {
        return (List<Player>) session.createQuery("from Player").list();
    }

    public Player getPlayer(int id) {
        return (Player) session.load(Player.class, id);
    }

    public void insertPlayer(Player player) {
        session.save(player);
    }

    public void updatePlayer(Player newPlayer) {
        session.update(newPlayer);
    }

    public void deletePlayer(Player player) {
        session.delete(player);
    }
}
