package com.app.client.builder.pages.player;

import com.app.client.builder.helper.Helper;
import com.app.client.interfaces.AppIntAsync;
import com.app.shared.Club;
import com.app.shared.Player;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class EditPage extends Composite {
    interface EditUIPage extends UiBinder<VerticalPanel, EditPage> {
    }

    private static EditPage.EditUIPage uiBinder = GWT.create(EditPage.EditUIPage.class);

    @UiField
    TextBox lastName;
    @UiField
    TextBox firstName;
    @UiField
    TextBox secondName;
    @UiField
    TextBox dateBirth;
    @UiField
    ListBox club;

    @UiField
    Button changeButton;
    @UiField
    Button exitButton;
    @UiField
    Button editButton;
//    @UiField
//    Button deleteButton;

    @UiField
    VerticalPanel verticalPanel;
    @UiField
    HTMLPanel htmlpanel;
    @UiField(provided = true)
    Grid gridTable;


    private Helper helper = new Helper();
    private AppIntAsync dbService;
    private PlayerPage playerPage;

    /**
     * Обновляет ячейку, на которую нажали
     * из
     *
     * @param playerPage мы вызываем текущую книгу(на нее мы нажали)
     *                   и затем мы обновляем ее и ячейку
     * @param dbService  сервис базы данных
     */
    @SuppressWarnings("deprecation")
    EditPage(PlayerPage playerPage, AppIntAsync dbService) {
        this.dbService = dbService;
        this.playerPage = playerPage;
        Player currentPlayer = playerPage.getCurrentPlayer();
        String birth = helper.formatDate(String.valueOf(currentPlayer.getDateBirth()));

        setupTable(currentPlayer, birth);
        initWidget(uiBinder.createAndBindUi(this));

        playerPage.panel.setVisible(false);
        playerPage.vertPanel.setVisible(false);
        playerPage.table.setVisible(false);

        lastName.setText(currentPlayer.getLastName());
        firstName.setText(currentPlayer.getFirstName());
        secondName.setText(currentPlayer.getSecondName());
        dateBirth.setText(birth);

        dbService.getClubs(new AsyncCallback<List<Club>>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(List<Club> result) {
                for (Club aResult : result) {
                    if (currentPlayer.getClubId() == aResult.getId()) {
                        gridTable.setText(1, 4, aResult.getName());

                    }
                    club.addItem(aResult.getName(), String.valueOf(aResult.getId()));
                }
            }
        });

        int i = playerPage.players.indexOf(currentPlayer);

        changeButton.addClickHandler(event -> {
            change(i, currentPlayer);
        });

        editButton.addClickHandler(event -> {
            gridTable.setVisible(false);
            lastName.setVisible(true);
            firstName.setVisible(true);
            secondName.setVisible(true);
            dateBirth.setVisible(true);
            club.setVisible(true);
            changeButton.setVisible(true);
            editButton.setVisible(false);
        });

        exitButton.addClickHandler(event -> {
            //нужно установить нового игрока, чтобы для него создавался новый класс EditPage
            //иначе просто нельзя будет опять редактировать эту книгу
            playerPage.players.set(i, currentPlayer);
            exit();
        });
    }

    private void change(int i, Player player) {
        final String lastName = this.lastName.getText().trim();
        final String firstName = this.firstName.getText().trim();
        final String secondName = this.secondName.getText().trim();
        final String st = helper.formatDate(this.dateBirth.getText());
        final int clubId = Integer.parseInt(this.club.getSelectedValue());

        int id = player.getId();
        Player p = new Player(lastName, firstName, secondName, new Date(st), clubId);
        p.setId(id);

        dbService.updatePlayer(p, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Problems with update: \n" + caught);
            }

            @Override
            public void onSuccess(Void result) {
                Window.alert("Player was updated ");
            }
        });

        playerPage.players.set(i, p);
        List<Player> newContactLst = Collections.singletonList(p);
        playerPage.table.setRowData(i, newContactLst);
        exit();
    }

    /**
     * при выходе восстановить прошлые данные, удалить этот класс из RootPanel
     */
    private void exit() {
        changeButton.setVisible(false);
        playerPage.vertPanel.setVisible(true);
        playerPage.table.setVisible(true);
        playerPage.panel.setVisible(true);
        helper.clear(this.lastName, this.firstName, this.secondName, this.dateBirth);
        RootPanel.get().remove(this);
        History.back();
    }

    private void setupTable(Player currentPlayer, String birth) {
        gridTable = new Grid(2, 5);
        // создает таблицу
        gridTable.setText(0, 0, "Last Name");
        gridTable.setText(0, 1, "First name");
        gridTable.setText(0, 2, "Second name");
        gridTable.setText(0, 3, "Date of Birth");
        gridTable.setText(0, 4, "Name of club");

        gridTable.setText(1, 0, currentPlayer.getLastName());
        gridTable.setText(1, 1, currentPlayer.getFirstName());
        gridTable.setText(1, 2, currentPlayer.getSecondName());
        gridTable.setText(1, 3, birth);

        gridTable.setCellPadding(10);
    }
}