package jabberpoint.ui;

public interface DialogService {
   void showAboutBox();
   boolean confirmExit();
   String getUserInput(String prompt);
   void showWarningMessage(String message);
   void showInfoMessage(String message);
   void showErrorMessage(String message);
}
