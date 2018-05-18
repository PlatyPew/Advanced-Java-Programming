// Authors: Daryl, Chun Yu
/*
Bus Features:
Allow the searching by code
Validates if code actually exists
Does auto zfill for bus codes that has only 4 digits
Shows all buses to take
Lists down the start and end destination with the respective bus codes, bus stop description and road description
Allows searching by bus stop description
Can 'guess' what the user is thinking
Can lists down every station
Allows users to view points on map on google maps
JOptionPane can use scroll bar
Has validation when choosing stations
Allows users to stop program if they press 'cancel' on the JOptionPane

MRT Features:

*/

import busRoutes.*;
import mrtRoutes.*;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) throws Exception {

        outerloop:
        while (true) {
            try {
                String option = JOptionPane.showInputDialog("Which service would you like\n"
                        + "1.    Bus routes\n"
                        + "2.    Mrt routes\n"
                        + "3.    Exit");
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
                break outerloop;
            }
        }
    }
}
