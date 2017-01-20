package GUI;

import StartGUI.Main;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;


public class RootController {
    Main mainApp;
    @FXML
    MenuItem menuItemFileCandidati;
    @FXML
    MenuItem menuItemFileSectii;
    @FXML
    MenuItem menuItemFileInscrieri;
    
    public RootController()
    {

    }

    public void setMainApplic(Main main)
    {
        this.mainApp=main;
    }

    @FXML
    public void handleCandidati()
    {
        mainApp.initCandidatViewLayout();
    }

    @FXML
    public void handleSectii()
    {
        mainApp.initSectiiViewLayout();
    }
    
    @FXML
    public void handleInscrieri()
    {
        mainApp.initInscrieriViewLayout();
    }
    
    @FXML
    public void handleSave()
    {
        mainApp.save();
    }
    
    @FXML
    public void handleAboutUs()
    {
        mainApp.initAboutUsViewLayout();
    }
    
    @FXML
    public void handleClose()
    {
        mainApp.close();
    }
    
}
