package com.quanlytapphoa;

import com.quanlytapphoa.gui.DangNhapGUI;
import com.quanlytapphoa.gui.GuiContext;

public class GuiApp {
    public static void main(String[] args) {
        GuiContext.init();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DangNhapGUI().setVisible(true);
            }
        });
    }
}
