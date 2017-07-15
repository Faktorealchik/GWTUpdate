package com.app.client.builder.pages.player;

import com.app.client.builder.helper.Helper;
import com.app.client.interfaces.AppIntAsync;
import com.app.shared.Club;
import com.app.shared.Player;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.SingleSelectionModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerPage extends Composite implements RemoteService {

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
    HTMLPanel panel; //таблица
    @UiField
    HTMLPanel htmlPanel; //текстбоксы

    @UiField
    Button editPlayer;
    @UiField
    Button addNewPlayer;
    @UiField
    Button exitButton;
    @UiField
    Button addButton;
    @UiField
    Button deleteButton;
    @UiField
    VerticalPanel vertPanel;

    private AppIntAsync dbService;
    List<Player> players = new ArrayList<>();
    private Helper helper;
    CellTable<Player> table;
    SimplePager simplePager;
    private Player currentPlayer;

    public PlayerPage(AppIntAsync dbService) {
        this.dbService = dbService;

        table = new CellTable<>();

        //теперь можем выбирать элементы.
        SingleSelectionModel<Player> selectionModel = new SingleSelectionModel<>();
        table.setSelectionModel(selectionModel);
        setupTable();
        helper = new Helper();
        initWidget(uiBinder.createAndBindUi(this));

        lastName.getElement().setAttribute("placeholder", "Last name");
        firstName.getElement().setAttribute("placeholder", "First name");
        secondName.getElement().setAttribute("placeholder", "Second name");
        dateBirth.getElement().setAttribute("placeholder", "Date of birth");

        simplePager = new SimplePager();
        simplePager.setDisplay(table);

        panel.add(table);
        panel.add(simplePager);

        deleteButton.addClickHandler(event -> {
            String st = Window.prompt("Are you shure? Yes/no", "Yes");
            if (st.equalsIgnoreCase("yes")) {
                Player selectedObject = selectionModel.getSelectedObject();
                dbService.deletePlayer(selectedObject, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("problems\n" + caught);
                    }

                    @Override
                    public void onSuccess(Void result) {
                        players.remove(selectedObject);
                        table.setRowData(players);
                        table.setPageSize(20);
                        Window.alert("Player was deleted");
                    }
                });
            }
        });

        editPlayer.addClickHandler(event -> {
            Player selected = selectionModel.getSelectedObject();
            if (selected != null) {
                setCurrentPlayer(selected);
                History.newItem("EditPage");
                RootPanel.get().add(new EditPage(this, dbService));
            } else {
                Window.alert("Please select player ");
            }
        });

        addButton.addClickHandler(event -> {
            panel.setVisible(true);
            addButton.setVisible(false);
            exitButton.setVisible(false);
            addNewPlayer.setVisible(true);
            htmlPanel.setVisible(false);
            editPlayer.setVisible(true);
            deleteButton.setVisible(true);
            addRow();
        });

        addNewPlayer.addClickHandler(event -> {
            panel.setVisible(false);
            htmlPanel.setVisible(true);
            exitButton.setVisible(true);
            editPlayer.setVisible(false);
            addButton.setVisible(true);
            addNewPlayer.setVisible(false);
            deleteButton.setVisible(false);
        });

        exitButton.addClickHandler(event -> {
            editPlayer.setVisible(true);
            deleteButton.setVisible(true);
            htmlPanel.setVisible(false);
            panel.setVisible(true);
            addButton.setVisible(false);
            exitButton.setVisible(false);
            addNewPlayer.setVisible(true);
        });
    }

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
                Window.alert("Player added successful");
                Window.Location.reload();
            }
        });

        helper.clear(this.lastName, this.firstName, this.secondName, this.dateBirth);
    }

    private void setupTable() {
        table.setPageSize(20);
        table.setWidth("50%");


        TextColumn<Player> playerId = new TextColumn<Player>() {
            @Override
            public String getValue(Player p) {
                return String.valueOf(p.getId());
            }
        };
        table.addColumn(playerId, "ID of player");

        TextColumn<Player> lastName = new TextColumn<Player>() {
            @Override
            public String getValue(Player p) {
                return p.getLastName();
            }
        };
        table.addColumn(lastName, "Last name");

        TextColumn<Player> firstName = new TextColumn<Player>() {
            @Override
            public String getValue(Player p) {
                return p.getFirstName();
            }
        };
        table.addColumn(firstName, "First name");

        TextColumn<Player> secondName = new TextColumn<Player>() {
            @Override
            public String getValue(Player p) {
                return p.getSecondName();
            }
        };
        table.addColumn(secondName, "Second name");

        TextColumn<Player> dateBirth = new TextColumn<Player>() {
            @Override
            public String getValue(Player p) {
                return helper.formatDate(String.valueOf(p.getDateBirth()));
            }
        };
        table.addColumn(dateBirth, "Date of birth");

        TextColumn<Player> id = new TextColumn<Player>() {
            @Override
            public String getValue(Player p) {
                return String.valueOf(p.getClubId());
            }
        };
        table.addColumn(id, "Club id");

        dbService.getPlayers(new AsyncCallback<List<Player>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Error load players\n" + caught);
            }

            @Override
            public void onSuccess(List<Player> result) {
                players = result;
                AsyncDataProvider<Player> provider = new AsyncDataProvider<Player>() {
                    @Override
                    protected void onRangeChanged(HasData<Player> display) {
                        updateRowData(0, players);
                    }
                };

                provider.addDataDisplay(table);
                provider.updateRowCount(players.size(), true);

                dbService.getClubs(new AsyncCallback<List<Club>>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Problem with load clubs\n" + caught);
                    }

                    @Override
                    public void onSuccess(List<Club> result) {
                        for (Club aResult : result) {
                            clubId.addItem(aResult.getName(), String.valueOf(aResult.getId()));
                        }
                    }
                });
            }
        });
    }

    Player getCurrentPlayer() {
        return currentPlayer;
    }

    private void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }


    interface PlayerUIPage extends UiBinder<VerticalPanel, PlayerPage> {
    }

    private static PlayerUIPage uiBinder = GWT.create(PlayerUIPage.class);

}
