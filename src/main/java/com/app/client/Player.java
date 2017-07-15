package com.app.client;

import com.app.client.builder.pages.player.PlayerPage;
import com.app.client.interfaces.AppInt;
import com.app.client.interfaces.AppIntAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public class Player implements EntryPoint {
    public void onModuleLoad() {
        AppIntAsync dbService = GWT.create(AppInt.class);

        RootPanel.get().add(new PlayerPage(dbService));
    }
}
