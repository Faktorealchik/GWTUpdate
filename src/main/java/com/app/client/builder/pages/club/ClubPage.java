package com.app.client.builder.pages.club;

import com.app.client.interfaces.ClubIntAsync;
import com.app.client.builder.helper.Helper;
import com.app.shared.Club;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

import java.util.ArrayList;
import java.util.List;

public class ClubPage extends Composite implements RemoteService {

    @UiField
    TextBox name;
    @UiField
    HTMLPanel panel;

    @UiField
    Button addButton;
    @UiField
    Button delete;
    @UiField
    VerticalPanel vertPanel;

    private ClubIntAsync dbService;
    List<Club> clubs = new ArrayList<>();
    private Helper helper;
    CellTable<Club> table;
    SimplePager simplePager;
    private Club club;

    public ClubPage(ClubIntAsync dbService) {
        this.dbService = dbService;

        table = new CellTable<>();

        //теперь можем выбирать элементы.
        SingleSelectionModel<Club> selectionModel = new SingleSelectionModel<Club>();
        table.setSelectionModel(selectionModel);

        setupTable(selectionModel);
        helper = new Helper();

        initWidget(uiBinder.createAndBindUi(this));
        simplePager = new SimplePager();
        simplePager.setDisplay(table);

        panel.add(table);
        panel.add(simplePager);

        table.addDomHandler(
            event -> {
                Club selected = selectionModel.getSelectedObject();
                if (selected != null) {
                    setCurrentClub(selected);

                    History.newItem("EditPage");
                    RootPanel.get().add(new EditPageClub(this, dbService));
                }
            },
            DoubleClickEvent.getType());

        addButton.addClickHandler(event -> {
            addRow();
        });


        delete.addClickHandler(event -> {
            Club selectedObject = selectionModel.getSelectedObject();
            dbService.deleteClub(selectedObject.getId(), new AsyncCallback<Void>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("problems\n" + caught);
                }

                @Override
                public void onSuccess(Void result) {
                    clubs.remove(selectedObject);
                    table.setRowData(clubs);
                    table.setPageSize(5);
                    Window.alert("Club was deleted");
                }
            });
        });
    }

    private void addRow() {
        final String name = this.name.getText().trim();

        dbService.addClub(name, new AsyncCallback<Club>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Trouble with adding club");
            }

            @Override
            public void onSuccess(Club result) {
                Window.alert("Success adding new club ! ");
                Window.Location.reload();
            }
        });

        helper.clear(this.name);
    }

    private void setupTable(final SelectionModel<Club> selectionModel) {
        table.setPageSize(5);
        table.setWidth("50%");

        Column<Club, Boolean> checkColumn = new Column<Club, Boolean>(
            new CheckboxCell(true, false)) {
            @Override
            public Boolean getValue(Club object) {
                return selectionModel.isSelected(object);
            }
        };
        table.addColumn(checkColumn);

        TextColumn<Club> clubId = new TextColumn<Club>() {
            @Override
            public String getValue(Club club) {
                return String.valueOf(club.getId());
            }
        };
        table.addColumn(clubId, "ID of club");

        TextColumn<Club> nameColumn = new TextColumn<Club>() {
            @Override
            public String getValue(Club club) {
                return club.getName();
            }
        };
        table.addColumn(nameColumn, "Name");

        dbService.getClubs(new AsyncCallback<List<Club>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Error load clubs\n"+caught);
            }

            @Override
            public void onSuccess(List<Club> result) {
                clubs = result;
                AsyncDataProvider<Club> provider = new AsyncDataProvider<Club>() {
                    @Override
                    protected void onRangeChanged(HasData<Club> display) {
                        int start = display.getVisibleRange().getStart();
                        int end = start + display.getVisibleRange().getLength();
                        end = end >= clubs.size() ? clubs.size() : end;
                        List<Club> sub = clubs.subList(start, end);
                        updateRowData(start, sub);
                    }
                };

                provider.addDataDisplay(table);
                provider.updateRowCount(clubs.size(), true);
            }
        });
    }

    private void setCurrentClub(Club club) {
        this.club = club;
    }

    Club getCurrentClub() {
        return this.club;
    }

    interface ClubUIPage extends UiBinder<VerticalPanel, ClubPage> {
    }

    private static ClubPage.ClubUIPage uiBinder = GWT.create(ClubPage.ClubUIPage.class);

}
