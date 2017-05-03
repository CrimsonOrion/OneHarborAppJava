/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OneHarborAppUI;
import javax.swing.JOptionPane;

/**
 *
 * @author jim
 */
public class MessageBox
{
    /**
     * Displays a simple message box with a blue circle and '!' along with the supplied message and title.
     * @param infoMessage The message that will appear in the body of the message box.
     * @param titleBar The message that will appear in the title portion of the message box.
     */
    public static void InfoBox(String infoMessage, String titleBar){
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Displays an error message box with a red circle and 'X' along with the supplied message and title.
     * @param errorMessage The message that will appear in the body of the message box.
     * @param titleBar The message that will appear in the title portion of the message box.
     */
    public static void ErrorBox(String errorMessage, String titleBar){
        JOptionPane.showMessageDialog(null, errorMessage, titleBar, JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Displays an error message box with a red circle and 'X' along with the supplied message followed by exception message, as well as title.
     * @param errorMessage The message that will appear in the body of the message box.
     * @param titleBar The message that will appear in the title portion of the message box.
     * @param ex The exception message variable.
     */
    public static void ErrorBox(String errorMessage, String titleBar, Exception ex){
        JOptionPane.showMessageDialog(null, errorMessage + "\r\n\r\n" + ex, titleBar, JOptionPane.ERROR_MESSAGE);
    }
}
