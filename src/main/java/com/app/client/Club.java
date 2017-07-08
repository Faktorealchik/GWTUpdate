package com.app.client;

import com.app.client.builder.pages.club.ClubPage;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public class Club implements EntryPoint {
    @Override
    public void onModuleLoad() {
        ClubIntAsync dbService = GWT.create(ClubInt.class);

        RootPanel.get().add(new ClubPage(dbService));
    }
}
