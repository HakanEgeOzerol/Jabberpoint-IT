package jabberpoint.ui;

import javax.swing.*;
import java.awt.*;

public class DefaultDialogService implements DialogService {
    private Frame frame;
    public DefaultDialogService(Frame frame) {
        this.frame = frame;
    }

    @Override
    public void showAboutBox() {
        AboutBox.show(frame);
    }
    @Override
    public boolean confirmExit() {
        int result = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to exit?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        return result == JOptionPane.YES_OPTION;
    }

    @Override
    public String getUserInput(String prompt) {
        return JOptionPane.showInputDialog(frame, prompt);
    }
    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    @Override
    public void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }
    @Override
    public void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}
