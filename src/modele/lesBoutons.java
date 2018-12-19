/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import javafx.scene.control.Button;

/**
 *
 * @author AngeliqueLeRoux
 */
public class lesBoutons
{
    private Session maSession;
    private Button monButton;

    public lesBoutons(Session maSession, Button monButton)
    {
        this.maSession = maSession;
        this.monButton = monButton;
    }

    public lesBoutons()
    {}
    
    public Session getMaSession()
    {
        return maSession;
    }

    public void setMaSession(Session maSession)
    {
        this.maSession = maSession;
    }

    public Button getMonButton()
    {
        return monButton;
    }

    public void setMonButton(Button monButton)
    {
        this.monButton = monButton;
    }
    
        
}
