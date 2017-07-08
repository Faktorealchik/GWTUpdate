package com.app.client.builder.pages.club;

import com.app.client.ClubIntAsync;
import com.app.client.builder.helper.DoubleClickTable;
import com.app.client.builder.helper.Helper;
import com.app.shared.Club;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.ArrayList;
import java.util.List;

public class ClubPage extends Composite {

    @UiField(provided = true)
    DoubleClickTable table; //FlexTable

    @UiField
    TextBox name;

    @UiField
    Button addButton;
    @UiField
    Button deleteActive;
    @UiField
    VerticalPanel vertPanel;

    private ClubIntAsync dbService;
    List<Club> clubs = new ArrayList<>();
    private Helper helper;

    public ClubPage(ClubIntAsync dbService) {
        this.dbService = dbService;
        helper = new Helper();
        setupTable();
        initWidget(uiBinder.createAndBindUi(this));

        createDoubleClickHandler();

        //TODO: убрать магию, в первом случае не работает, зато как сейчас - все хорошо
        createTableHandler(table);

//        addButton.setVisible(false);
//        addButton.addClickHandler(event -> {
//            RootPanel.get().clear();
//            History.newItem("Players");
//            RootPanel.get().add(new PlayerPage(dbService));
//        });
    }

    private Club club;

    private void setCurrentClub(Club club) {
        this.club = club;
    }

    public Club getCurrentClub() {
        return this.club;
    }

    /**
     * Устанавливаем слушателя на двойной клик
     * получаем строку, на которую кликнули
     * получаем книгу из строки
     * отсылаем запрос на обновление этой книги
     */
    public HandlerRegistration createDoubleClickHandler() {
        return table.addDoubleClickHandler(event -> {
            HTMLTable.Cell cell = table.getCellForEvent(event);
            int rowIndex = cell.getRowIndex();

            String name = table.getText(rowIndex, 2);
            Club club = new Club();
            club.setName(name);
            int i = clubs.indexOf(club);
            setCurrentClub(clubs.get(i));

            History.newItem("EditPage");
            RootPanel.get().add(new EditPageClub(this, dbService));

        });
    }

    /**
     * слушатель на клик по таблице (чекбокс устанавливается)
     *
     * @param table
     */
    public void createTableHandler(DoubleClickTable table) {
        table.addClickHandler(event -> {
            HTMLTable.Cell cell = table.getCellForEvent(event);
            int index = cell.getRowIndex();
            int cellIndex = cell.getCellIndex();

            CheckBox checkBox = (CheckBox) table.getWidget(index, 0);

            //если клинкнули на сам чекбокс(так как он меняется сам, то ничего не делаем
            //но, если нажимать на ячейку 0, рядом с ним, то меняться не будет тоже.
            if (cellIndex == 0) {
                return;
            }

            if (index > 0) {
                if (checkBox.getValue()) {
                    checkBox.setValue(false);
                } else {
                    checkBox.setValue(true);
                }
                table.setWidget(index, 0, checkBox);
            }
        });
    }


    /**
     * Создаем таблицу
     */
    private void setupTable() {
        table = new DoubleClickTable();
        table.setText(0, 0, "");
        table.setText(0, 1, "Clubs id");
        table.setText(0, 2, "Clubs name");
        table.setText(0, 3, "Clubs players");

        dbService.getClubs(new AsyncCallback<List<Club>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("can`t get clubs\n" + caught);
            }

            @Override
            public void onSuccess(List<Club> result) {
                clubs = result;
                for (int i = 0; i < result.size(); i++) {
                    Club club = result.get(i);
                    table.setWidget(i + 1, 0, new CheckBox());
                    table.setText(i + 1, 1, String.valueOf(club.getId()));
                    table.setText(i + 1, 2, club.getName());
                    table.setText(i + 1, 3, "players");
                }
            }
        });


        table.setCellPadding(10);
    }

    interface ClubUIPage extends UiBinder<VerticalPanel, ClubPage> {
    }

    private static ClubPage.ClubUIPage uiBinder = GWT.create(ClubPage.ClubUIPage.class);

}
