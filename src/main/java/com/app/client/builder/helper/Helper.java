package com.app.client.builder.helper;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.TextBox;

import java.util.Date;

public class Helper {
    /**
     * очищаем texBox`ы
     */
    public void clear(TextBox... names) {
        for (TextBox name : names) {
            name.setText("");
        }
        names[names.length - 1].setFocus(true);
    }


    /**
     * Возвращает новую дату
     */
    @SuppressWarnings("deprecation")
    public String formatDate(String date) {
        Date d = new Date(date);
        DateTimeFormat dateFormat = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT);
        return dateFormat.format(d);
    }
}
