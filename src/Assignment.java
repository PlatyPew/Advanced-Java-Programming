// Author: PlatyPew
import busRoutes.*;
import javax.swing.JOptionPane;
import mrtRoutes.*;

public class Assignment {

    public static void banner(String title, String text) {
        JOptionPane.showMessageDialog(null, text, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) throws Exception {

        boolean legit = false;
        while (!legit) {
            try {
                String option = JOptionPane.showInputDialog("Which service would you like\n[b]us / [m]rt routes");
                switch (option) {
                    case "m":
                        banner("Credits", "You have selected MRT routes\nCoded by Lim Chun Yu");
                        MrtServiceMain.run();
                        legit = true;
                        break;
                    case "b":
                        banner("Credits", "You have selected Bus routes\nCoded by Daryl Lim");
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
