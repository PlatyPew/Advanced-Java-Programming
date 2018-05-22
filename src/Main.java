/*
@author Chun Yu
@author Daryl

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
Shows where you are on the map when you search for the line
Allows for more than one interchange between routes
Image for each respective mrt lines
Find fastest route to destination from all possible routes
Returns number of stations between all stops
User can search using either station desc or station numberOfStops on same seach bar
Drop down table for transfer stations
Allows users to stop program if they press 'cancel' on the JOptionPane
Has validation when choosing stations
Addition of NE line
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
