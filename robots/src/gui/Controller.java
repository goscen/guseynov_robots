package gui;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;
import java.util.Timer;

public class Controller {
    private View view;
    private Model model;

    Controller(View view, Model model) {
        this.view = view;
        this.model = model;
    }


}
