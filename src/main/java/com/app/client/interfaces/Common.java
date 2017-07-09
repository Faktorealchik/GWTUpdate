package com.app.client.interfaces;

import com.app.shared.Club;

import java.util.List;

public interface Common {
    List<Club> getClubs();

    Club getClub(int id);
}

