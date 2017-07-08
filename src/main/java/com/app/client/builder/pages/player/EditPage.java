package com.app.client.builder.pages.player;

import com.app.client.AppIntAsync;
import com.app.client.builder.helper.Helper;
import com.app.shared.Player;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.Date;

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
    ListBox clubId;

    @UiField
    Button changeButton;
    @UiField
    Button exitButton;
    @UiField
    VerticalPanel verticalPanel;
    @UiField
    HTMLPanel htmlpanel;
    @UiField(provided = true)
    Grid gridTable;


    private Helper helper = new Helper();

    /**
     * Обновляет ячейку, на которую нажали
     * из
     *
     * @param playerPage мы вызываем текущую книгу(на нее мы нажали)
     *                   и затем мы обновляем ее и ячейку
     * @param dbService
     */
    EditPage(PlayerPage playerPage, AppIntAsync dbService) {

        setupTable(playerPage);
        initWidget(uiBinder.createAndBindUi(this));
        playerPage.vertPanel.setVisible(false);
        playerPage.table.setVisible(false);

        //TODO: дублирование(сетаптэйбл)
        Player currentPlayer = playerPage.getCurrentPlayer();
        String birth = helper.formatDate(String.valueOf(currentPlayer.getDateBirth()));

        lastName.setText(currentPlayer.getLastName());
        firstName.setText(currentPlayer.getFirstName());
        secondName.setText(currentPlayer.getSecondName());
        dateBirth.setText(birth);
//        clubId.addItem();
        clubId.setText(String.valueOf(currentPlayer.getClubId()));
        clubId.get

        int i = playerPage.players.indexOf(currentPlayer);

        changeButton.addClickHandler(event -> {
            final String lastName = this.lastName.getText().trim();
            final String firstName = this.firstName.getText().trim();
            final String secondName = this.secondName.getText().trim();
            final String st = helper.formatDate(this.dateBirth.getText());
            final int clubId = Integer.parseInt(this.clubId.getText());

            int id = currentPlayer.getId();
            Player b = new Player(lastName, firstName, secondName, new Date(st), clubId);
            b.setId(id);
            dbService.updatePlayer(b, new AsyncCallback<Void>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("Problems with update: \n" + caught);
                }

                @Override
                public void onSuccess(Void result) {
                    Window.alert("We update him. ");
                }
            });

            playerPage.players.set(i, b);
            playerPage.table.setText(i + 1, 1, lastName);
            playerPage.table.setText(i + 1, 2, firstName);
            playerPage.table.setText(i + 1, 3, secondName);
            playerPage.table.setText(i + 1, 4, st);
            playerPage.table.setText(i + 1, 5, String.valueOf(clubId));

            exit(playerPage);
        });

        exitButton.addClickHandler(event -> {
            //нужно установить новую книгу, чтобы для нее создавался новый класс EditPage
            //иначе просто нельзя будет опять редактировать эту книгу
            playerPage.players.set(i, currentPlayer);//playerPage.getPlayers.indexOf(currentPlayer) вместо i
            exit(playerPage);
        });
    }

    /**
     * при выходе восстановить прошлые данные, удалить этот класс из RootPanel
     */
    private void exit(PlayerPage entityPage) {
        changeButton.setVisible(false);
        entityPage.vertPanel.setVisible(true);
        entityPage.table.setVisible(true);
        helper.clear(this.lastName, this.firstName, this.secondName, this.clubId, this.dateBirth);
        RootPanel.get().remove(this);

        History.back();
    }

    private void setupTable(PlayerPage playerPage) {
        gridTable = new Grid(2, 5);
        // создает таблицу
        gridTable.setText(0, 0, "Last Name");
        gridTable.setText(0, 1, "First name");
        gridTable.setText(0, 2, "Second name");
        gridTable.setText(0, 3, "Date of Birth");
        gridTable.setText(0, 4, "ID of club");

        Player currentPlayer = playerPage.getCurrentPlayer();
        gridTable.setText(1, 0, currentPlayer.getLastName());
        gridTable.setText(1, 1, currentPlayer.getFirstName());
        gridTable.setText(1, 2, currentPlayer.getSecondName());
        String birth = helper.formatDate(String.valueOf(currentPlayer.getDateBirth()));
        gridTable.setText(1, 3, birth);
        gridTable.setText(1, 4, String.valueOf(currentPlayer.getClubId()));

        gridTable.setCellPadding(10);
    }
}
