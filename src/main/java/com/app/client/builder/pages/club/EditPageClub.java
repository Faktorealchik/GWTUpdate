package com.app.client.builder.pages.club;

import com.app.client.ClubIntAsync;
import com.app.client.builder.helper.DoubleClickTable;
import com.app.shared.Club;
import com.app.shared.Player;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.List;

public class EditPageClub extends Composite {
    interface EditUIPageClub extends UiBinder<VerticalPanel, EditPageClub> {
    }

    private static EditPageClub.EditUIPageClub uiBinder = GWT.create(EditPageClub.EditUIPageClub.class);


    @UiField
    TextBox name;

    @UiField
    Button changeButton;
    @UiField
    Button exitButton;
    @UiField
    VerticalPanel verticalPanel;
    @UiField
    HTMLPanel htmlpanel;
    @UiField(provided = true)
    DoubleClickTable gridTable;

    private ClubIntAsync dbService;
    private ClubPage clubPage;

    public EditPageClub(ClubPage clubPage, ClubIntAsync dbService) {
        this.dbService = dbService;
        this.clubPage = clubPage;
        setupTable();
        initWidget(uiBinder.createAndBindUi(this));
        clubPage.vertPanel.setVisible(false);
        clubPage.table.setVisible(false);

        Club club = clubPage.getCurrentClub();
        name.setText(club.getName());

        int i = clubPage.clubs.indexOf(club);

        changeButton.addClickHandler(event -> {
            final String name = this.name.getText().trim();

            int id = club.getId();
            Club b = new Club(name);
            b.setId(id);
            dbService.updateClub(b, new AsyncCallback<Void>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("Problems with update: \n" + caught);
                }

                @Override
                public void onSuccess(Void result) {
                    Window.alert("We update him. ");
                }
            });
            clubPage.clubs.set(i, b);
            clubPage.table.setText(i + 1, 1, name);

            exit();
        });

        exitButton.addClickHandler(event -> {
            //нужно установить новую книгу, чтобы для нее создавался новый класс EditPage
            //иначе просто нельзя будет опять редактировать эту книгу
            clubPage.clubs.set(i, club);//playerPage.getPlayers.indexOf(currentPlayer) вместо i
            exit();
        });
    }

    /**
     * при выходе восстановить прошлые данные, удалить этот класс из RootPanel
     */
    private void exit() {
        changeButton.setVisible(false);
        clubPage.vertPanel.setVisible(true);
        clubPage.table.setVisible(true);
        this.name.setText("");
        RootPanel.get().remove(this);

        History.back();
    }

    private List<Player> list;

    private void setupTable() {
        gridTable = new DoubleClickTable();
        // создает таблицу
        gridTable.setText(0, 0, "Name of club");
        gridTable.setText(0, 1, "Players");

        Club club = clubPage.getCurrentClub();
        gridTable.setText(1, 0, club.getName());

        dbService.getPlayersForClub(club, new AsyncCallback<List<Player>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("troubles with load name players");
            }

            @Override
            public void onSuccess(List<Player> result) {
                for (int i = 0; i < result.size(); i++) {
                    Player p = result.get(i);
                    gridTable.setText(i + 1, 1,
                            p.getLastName() + " " + p.getFirstName() + " " + p.getSecondName());
                }
            }
        });

        gridTable.setCellPadding(10);
    }
}
