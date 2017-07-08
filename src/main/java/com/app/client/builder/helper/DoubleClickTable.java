package com.app.client.builder.helper;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * Класс делает следующую вещь:
 *
 * Помогает добавить в "двойной клик" event,
 * с помощью которого будем узнавать, на какую ячейку нажали
 */
public class DoubleClickTable extends FlexTable {
    class MyCell extends Cell {
        MyCell(int rowIndex, int cellIndex) {
            super(rowIndex, cellIndex);
        }
    }

    public Cell getCellForEvent(MouseEvent<? extends EventHandler> event) {
        Element td = getEventTargetCell(Event.as(event.getNativeEvent()));
        if (td == null) {
            return null;
        }

        int row = TableRowElement.as(td.getParentElement()).getSectionRowIndex();
        int column = TableCellElement.as(td).getCellIndex();
        return new MyCell(row, column);
    }
}