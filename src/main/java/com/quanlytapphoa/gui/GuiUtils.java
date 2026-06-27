package com.quanlytapphoa.gui;

import com.quanlytapphoa.bus.BusinessException;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

final class GuiUtils {
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final DecimalFormat MONEY_FORMAT = new DecimalFormat("#,##0.##");

    private GuiUtils() {
    }

    static String text(JTextField field) {
        return field.getText() == null ? "" : field.getText().trim();
    }

    static int parseInt(String value, String fieldName) {
        try {
            return Integer.parseInt(normalize(value));
        } catch (NumberFormatException ex) {
            throw new BusinessException(fieldName + " phai la so nguyen");
        }
    }

    static double parseDouble(String value, String fieldName) {
        try {
            return Double.parseDouble(normalize(value).replace(",", ""));
        } catch (NumberFormatException ex) {
            throw new BusinessException(fieldName + " phai la so");
        }
    }

    static Date parseDateOrNull(String value, String fieldName) {
        String normalized = normalize(value);
        if (normalized.length() == 0) {
            return null;
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
            format.setLenient(false);
            return format.parse(normalized);
        } catch (ParseException ex) {
            throw new BusinessException(fieldName + " dung dinh dang yyyy-MM-dd");
        }
    }

    static Date parseDateOrToday(String value, String fieldName) {
        Date date = parseDateOrNull(value, fieldName);
        return date == null ? new Date() : date;
    }

    static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(DATE_PATTERN).format(date);
    }

    static String formatMoney(double value) {
        return MONEY_FORMAT.format(value);
    }

    static int selectedModelRow(JTable table) {
        int row = table.getSelectedRow();
        return row < 0 ? -1 : table.convertRowIndexToModel(row);
    }

    static boolean confirm(Component parent, String message) {
        return JOptionPane.showConfirmDialog(parent, message, "Xac nhan", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION;
    }

    static void info(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Thong bao", JOptionPane.INFORMATION_MESSAGE);
    }

    static void error(Component parent, Exception ex) {
        JOptionPane.showMessageDialog(parent, ex.getMessage(), "Loi", JOptionPane.ERROR_MESSAGE);
    }

    private static String normalize(String value) {
        return value == null ? "" : value.trim();
    }
}
