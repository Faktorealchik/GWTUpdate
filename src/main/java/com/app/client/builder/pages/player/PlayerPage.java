package com.app.client.builder.pages.player;

import com.app.client.interfaces.AppIntAsync;
import com.app.client.builder.helper.DoubleClickTable;
import com.app.client.builder.helper.Helper;
import com.app.shared.Club;
import com.app.shared.Player;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class PlayerPage extends Composite {


    @UiField(provided = true)
    DoubleClickTable table; //FlexTable

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
    Button addButton;
    @UiField
    Button deleteActive;
    @UiField
    public VerticalPanel vertPanel;

    private Player currentPlayer;
    List<Player> players = new LinkedList<>();
    private Helper helper = new Helper();
    private AppIntAsync dbService;

    /**
     * В конструкторе устанавливаем таблицу,
     * добавляем слушателей ко всем кнопкам.
     *
     * @param dbService сервис БД
     */
    public PlayerPage(AppIntAsync dbService) {
        this.dbService = dbService;
        setupTable();
        initWidget(uiBinder.createAndBindUi(this));

        lastName.getElement().setAttribute("placeholder", "Last name");
        firstName.getElement().setAttribute("placeholder", "First name");
        secondName.getElement().setAttribute("placeholder", "Second name");
        dateBirth.getElement().setAttribute("placeholder", "Date of birth");

        dbService.getClubs(new AsyncCallback<List<Club>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Problem with load clubs\n"+caught);
            }

            @Override
            public void onSuccess(List<Club> result) {
                for (Club aResult : result) {
                    clubId.addItem(aResult.getName(), String.valueOf(aResult.getId()));
                }
            }
        });


        createDoubleClickHandler();
        createTableHandler();

        clubId.addKeyDownHandler(event ->
        {
            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                addRow();
            }
        });

        addButton.addClickHandler(event -> addRow());

        setDeleteHandler();
    }

    /**
     * слушатель на клик по таблице (чекбокс устанавливается)
     *
     */
    private void createTableHandler() {
        table.addClickHandler(event -> {
            HTMLTable.Cell cell = table.getCellForEvent(event);
            int rowIndex = cell.getRowIndex();
            int cellIndex = cell.getCellIndex();

            CheckBox checkBox = (CheckBox) table.getWidget(rowIndex, 0);

            //если клинкнули на сам чекбокс(так как он меняется сам, то ничего не делаем
            //но, если нажимать на ячейку 0, рядом с ним, то меняться не будет тоже.
            if (cellIndex == 0) {
                return;
            }

            if (rowIndex > 0) {
                if (checkBox.getValue()) {
                    checkBox.setValue(false);
                } else {
                    checkBox.setValue(true);
                }
                table.setWidget(rowIndex, 0, checkBox);
            }
        });
    }

    /**
     * устанавливаем слушателя на клик по
     * кнопке "удалить активные"
     */
    private void setDeleteHandler() {
        deleteActive.addClickHandler(event ->
        {
            for (int i = 1; i < table.getRowCount(); i++) {
                CheckBox checkBox = (CheckBox) table.getWidget(i, 0);
                if (checkBox.getValue()) {
                    String lastName = table.getText(i, 1);
                    String firstName = table.getText(i, 2);
                    String secondName = table.getText(i, 3);
                    table.removeRow(i);
                    Player b = new Player();
                    b.setLastName(lastName);
                    b.setFirstName(firstName);
                    b.setSecondName(secondName);
                    int id = players.indexOf(b);
                    b = players.get(id);
                    players.remove(b);
                    dbService.deletePlayer(b, new AsyncCallback<Void>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert("problems" + caught);
                        }

                        @Override
                        public void onSuccess(Void result) {
                            Window.alert("Player was deleted");
                        }
                    });
                    i--;
                }
            }
        });
    }


    /**
     * Устанавливаем слушателя на двойной клик
     * получаем строку, на которую кликнули
     * получаем книгу из строки
     * отсылаем запрос на обновление этой книги
     */
    private HandlerRegistration createDoubleClickHandler() {
        return table.addDoubleClickHandler(event -> {
            HTMLTable.Cell cell = table.getCellForEvent(event);
            int rowIndex = cell.getRowIndex();

            String lastName = table.getText(rowIndex, 1);
            String firstName = table.getText(rowIndex, 2);
            String secondName = table.getText(rowIndex, 3);
            Player curPlayer = new Player();
            curPlayer.setLastName(lastName);
            curPlayer.setFirstName(firstName);
            curPlayer.setSecondName(secondName);

            int i = players.indexOf(curPlayer);

            curPlayer = players.get(i);

            History.newItem("EditPage");

            setCurrentPlayer(curPlayer);
            RootPanel.get().add(new EditPage(this, dbService));
        });
    }


    /**
     * проверяем на валидность(существует ли книга, корректно ли все введено)
     * добавляем в массив книг
     * добавляем строчку в таблице
     */
    @SuppressWarnings("deprecation")
    private void addRow() {
        final String lastName = this.lastName.getText().trim();
        final String firstName = this.firstName.getText().trim();
        final String secondName = this.secondName.getText().trim();
        final String dateBirth = helper.formatDate(this.dateBirth.getText());
        final int clubId = Integer.parseInt(this.clubId.getSelectedValue());

        dbService.addPlayer(new Player(lastName, firstName, secondName, new Date(dateBirth), clubId), new AsyncCallback<Player>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed, reconnect.");
                Window.Location.reload();
            }

            @Override
            public void onSuccess(Player result) {
                if (result == null) {
                    Window.alert("this club not exist or other error");
                } else {
                    Window.alert("ok");
                    //TODO: добавить проверку на уже существующего
                    int row = table.getRowCount();
                    table.setWidget(row, 0, new CheckBox());
                    table.setText(row, 1, lastName);
                    table.setText(row, 2, firstName);
                    table.setText(row, 3, secondName);
                    table.setText(row, 4, dateBirth);
                    table.setText(row, 5, String.valueOf(clubId));
                    players.add(result);
                }
            }
        });

        helper.clear(this.lastName, this.firstName, this.secondName, this.dateBirth);
    }


    /**
     * Создаем таблицу
     */
    private void setupTable() {
        table = new DoubleClickTable();
        table.setText(0, 0, "");
        table.setText(0, 1, "Last name");
        table.setText(0, 2, "First name");
        table.setText(0, 3, "Second name");
        table.setText(0, 4, "Date of birth");
        table.setText(0, 5, "Club id");


        dbService.getPlayers(new AsyncCallback<List<Player>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("error load players\n"+caught);
            }

            @Override
            public void onSuccess(List<Player> result) {
                players = result;
                for (int i = 0; i < result.size(); i++) {
                    Player p = result.get(i);

                    table.setWidget(i + 1, 0, new CheckBox());
                    table.setText(i + 1, 1, p.getLastName());
                    table.setText(i + 1, 2, p.getFirstName());
                    table.setText(i + 1, 3, p.getSecondName());
                    table.setText(i + 1, 4, helper.formatDate(String.valueOf(p.getDateBirth())));
                    table.setText(i + 1, 5, String.valueOf(p.getClubId()));
                }
                if (players.size() > 0 && table.getRowCount() < 2) {
                    Window.Location.reload();
                }
            }
        });

        table.setCellPadding(10);
    }


    Player getCurrentPlayer() {
        return currentPlayer;
    }

    private void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }


    interface PlayerUIPage extends UiBinder<VerticalPanel, PlayerPage> {
    }

    private static PlayerPage.PlayerUIPage uiBinder = GWT.create(PlayerPage.PlayerUIPage.class);
}
