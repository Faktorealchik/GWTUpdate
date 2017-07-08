//package com.app.client.builder.pages;
//
//import com.google.gwt.core.shared.GWT;
//import com.google.gwt.uibinder.client.UiBinder;
//import com.google.gwt.uibinder.client.UiField;
//import com.google.gwt.user.client.History;
//import com.google.gwt.user.client.ui.Button;
//import com.google.gwt.user.client.ui.Composite;
//import com.google.gwt.user.client.ui.RootPanel;
//import com.google.gwt.user.client.ui.VerticalPanel;
//
//public class EntityPage extends Composite {
//
//    interface EntityUIBuilder extends UiBinder<VerticalPanel, EntityPage> {
//    }
//
//    private static EntityPage.EntityUIBuilder uiBinder = GWT.create(EntityPage.EntityUIBuilder.class);
//
//    @UiField
//    Button addButton;
//    @UiField
//    Button deleteActive;
//
//    public EntityPage() {
//        initWidget(uiBinder.createAndBindUi(this));
//
//        addButton.addClickHandler(event -> {
//            RootPanel.get().clear();
//            History.newItem("Players");
//            RootPanel.get().add(new PlayerPage());
//            RootPanel.get().remove(this);
//        });
//
//        deleteActive.addClickHandler(event -> {
//            RootPanel.get().clear();
//            History.newItem("Clubs");
//            RootPanel.get().add(new ClubPage());
//            RootPanel.get().remove(this);
//        });
//    }
//}
