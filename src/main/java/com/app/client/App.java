package com.app.client;

import com.app.client.builder.pages.player.PlayerPage;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public class App implements EntryPoint {

    public void onModuleLoad() {
        AppIntAsync dbService = GWT.create(AppInt.class);

        RootPanel.get().add(new PlayerPage(dbService));
    }
}
