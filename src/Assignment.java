// Author: PlatyPew

import busRoutes.*;
import javax.swing.JOptionPane;
import mrtRoutes.*;

public class Assignment {

    public static void main(String[] args) throws Exception {

        boolean legit = false;
        while (!legit) {
            try {
                String option = JOptionPane.showInputDialog("Which service would you like\n1.    Bus \n2.    Mrt routes");
                switch (option) {
                    case "1":
                        MrtServiceMain.run();
                        legit = true;
                        break;
                    case "2":
                        BusServiceMain.run();
                        legit = true;
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Select either \"b\" or \"m\"", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
            }
        }

    }
}
