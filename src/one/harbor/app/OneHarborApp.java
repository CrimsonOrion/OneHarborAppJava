/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package one.harbor.app;
import OneHarborAppUI.OneHarborAppUI;
import OneHarborAppUI.MessageBox;
import javax.swing.UIManager;

/**
 *
 * @author eimi_
 */
public class OneHarborApp {
    

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // Set to windows UI
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
            MessageBox.ErrorBox("Uh, oh.  Something didn't go right.", "Error", e);
        }

        // Open up the main window
        OneHarborAppUI UI = new OneHarborAppUI();
        UI.setVisible(true);
    }
}
