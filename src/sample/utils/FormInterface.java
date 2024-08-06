package sample.utils;

import javax.swing.*;

public interface FormInterface {

    // Method in which the form's main components will be created
    void createMainComponents(JPanel panel);

    // Method to get the info from the form's input fields
    Object getFormInfo();

    // Method to clear form's input fields
    void clear();
}
