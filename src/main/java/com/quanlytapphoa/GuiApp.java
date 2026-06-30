package com.quanlytapphoa;

import com.quanlytapphoa.gui.DangNhapGUI;
import com.quanlytapphoa.gui.GuiContext;
import java.awt.EventQueue;

public final class GuiApp {
    private GuiApp() {
    }

    public static void main(String[] args) {
        GuiContext.init();
        EventQueue.invokeLater(() -> new DangNhapGUI().setVisible(true));
    }
}
