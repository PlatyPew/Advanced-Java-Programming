// Author: PlatyPew

import busRoutes.*;
import mrtRoutes.*;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) throws Exception {

        outerloop:
        while (true) {
            try {
                String option = JOptionPane.showInputDialog("Which service would you like\n1.    Bus routes\n2.    Mrt routes\n3.    Exit");
                switch (option) {
                    case "2":
                        MrtServiceMain.run();
                        break;
                    case "1":
                        BusServiceMain.run();
                        break;
                    case "3":
                        break outerloop;
                    default:
                        JOptionPane.showMessageDialog(null, "Select either \"1\", \"2\" or \"3\"", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                System.exit(0);
            }
        }
    }
}
