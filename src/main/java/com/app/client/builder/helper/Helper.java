package com.app.client.builder.helper;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.TextBox;
import com.app.shared.Player;

import java.util.Date;
import java.util.List;

public class Helper {

    /**
     * validateString String
     *
     * @param st строка, которая будет проверена на валидность
     */
    public boolean validateString(String st) {
        if (!st.matches("^[0-9A-Za-zА-Яа-я\\.\\s]{1,30}$")) {
            Window.alert("This name/author incorrect '" + st + "' is not valid (1-30 symbols).");
            return false;
        }
        return true;
    }

    /**
     * validateNumber из textBox (count, price)
     * возвращает значение указанное в textBox`e
     * */
    public int validateNumber(TextBox box){
        try {
            return Integer.parseInt(box.getText());
        } catch (NumberFormatException e) {
            Window.alert("Not a number.");
            box.setFocus(true);
            return -1;
        }
    }

    /**
     * очищаем texBox`ы
     */
    public void clear(TextBox name, TextBox author, TextBox price, TextBox count, TextBox another) {
        name.setText("");
        author.setText("");
        price.setText("");
        count.setText("");
        another.setText("");
        name.setFocus(true);
    }

    /**
     * Проверяем существует ли книга
     * если да-возвращаем null
     * нет- добавляем книгу в список книг, возвращаем ее
     */
    public Player checkBook(List<Player> players, String name, String author, double price, int count) {
        Player player = new Player();
        player.setLastName(name);
        player.setFirstName(author);
//        player.setPrice(price);
//        player.setCount(count);

        if (players.contains(player)) {
            return null;
        } else {
            players.add(player);
            return player;
        }
    }


    /**
     * Возвращает новую дату
     * */
    public String formatDate(String date){
        Date d = new Date(date);
        DateTimeFormat dateFormat = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT);
        return dateFormat.format(d);
    }

    /**
     * слушатель на клик по таблице (чекбокс устанавливается)
     * @param table
     */
    public void createTableHandler(DoubleClickTable table) {
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
}
