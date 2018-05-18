// Author: Daryl
/*
Bugs:
Crashes if bus stop selected has no buses. SQUASHED!
Sometimes, for direct bus, number is repeated. Can probably be fixed using set. SQUASHED!
For some reason, while searching for "woodlands", a weird line break appears in between. SQUASHED!
Somestimes, transfer shows 2 sets of the same transfer
 */

package busRoutes;

import java.util.HashMap;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.awt.Desktop;
import java.awt.Dimension;
import java.net.URI;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class BusServiceMain {

    protected static final String BUSSTOP = "src/busRoutes/resources/lta-bus_stop_codes.csv";
    protected static final String BUSSTOPLOCATION = "src/busRoutes/resources/lta-bus_stop_locations.csv";
    protected static final String SBSROUTE = "src/busRoutes/resources/lta-sbst_route.csv";
    protected static final String SMRTROUTE = "src/busRoutes/resources/lta-smrt_route.csv";

    protected static HashMap<String, BusStop> busStopList = new HashMap<String, BusStop>();
    protected static HashMap<String, BusStopLocation> busStopLocationList = new HashMap<String, BusStopLocation>();
    protected static HashMap<String, ArrayList> ltabuses = new HashMap<String, ArrayList>();
    protected static HashMap<String, ArrayList> busRoutes = new HashMap<String, ArrayList>();
    protected static HashMap<String, String> busDescList = new HashMap<String, String>();

    public static String[] getUserInputCode() {
        try {
            String[] userInput = new String[2];
            boolean found = false;
            while (!found) {
                userInput[0] = beautify(JOptionPane.showInputDialog("Please enter your starting point"));

                if (busStopList.get(userInput[0]) != null) {
                    found = true;
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "The bus code you entered does not exist!",
                            "Invalid input",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
            found = false;
            while (!found) {
                userInput[1] = beautify(JOptionPane.showInputDialog("Please enter your destination"));

                if (busStopList.get(userInput[1]) != null) {
                    found = true;
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "The bus code you entered does not exist!",
                            "Invalid input",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

            return userInput;
        } catch (Exception e) {
            return new String[1];
        }
    }

    public static String beautify(String userInput) {
        userInput = userInput.trim();
        try {
            userInput = String.format("%05d", Integer.parseInt(userInput));
        } catch (Exception e) {
        }
        return userInput;
    }

    public static void openWeb(String mapURL) throws Exception {
        try {
            boolean clear = false;
            while (!clear) {
                String in = JOptionPane.showInputDialog("Open browser to view the map on Google?\n[y]es / [n]o");
                switch (in) {
                    case "y":
                        Desktop.getDesktop().browse(new URI(mapURL));
                        clear = true;
                        break;
                    case "n":
                        clear = true;
                        break;
                }
            }
        } catch (Exception e) {
            System.exit(0);
        }
    }

    public static String getUserInputDesc(String out) {
        String busStop;
        while (true) {
            busStop = BusSearchName.searchByName(JOptionPane.showInputDialog(out).trim());
            if (busStop.equals("empty")) {
                JOptionPane.showMessageDialog(
                        null,
                        "There are no bus stop names that match your search query",
                        "Invalid input",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                return busStop;
            }
        }
    }

    public static String displayResults(String title, String text, boolean input) {
        JTextArea textArea = new JTextArea(text);
        textArea.setText(text);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setOpaque(false);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setBackground(UIManager.getColor("Label.background"));
        textArea.setFont(UIManager.getFont("Label.font"));
        textArea.setBorder(UIManager.getBorder("Label.border"));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 500));
        if (input) {
            return JOptionPane.showInputDialog(null, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
    }

    public static void run() throws Exception {
        // Initialising hashmaps
        BusMapData.mapBusStops();
        BusMapData.mapBusStopLocations();
        BusMapData.mapBusRoutes();

        try {
            boolean clear = false;
            while (!clear) {
                String in = JOptionPane.showInputDialog("Search by bus code or description?\n[c]ode / [d]esc");
                switch (in) {
                    case "c":
                        String[] userInputCode = getUserInputCode();
                        String[] results1;

                        if (userInputCode[0] != null) {
                            results1 = BusCalculateData.getDirections(userInputCode);
//                            results1 = BusCalculateData.getDirections(new String[]{"44221", "44351"}); // No transfer
//                            results1 = BusCalculateData.getDirections(new String[]{"44221", "43641"}); // 1 transfer needed
//                            results1 = BusCalculateData.getDirections(new String[]{"45529", "44371"}); // No buses in bus stop
                            String outputData = results1[0];
                            displayResults("Route to take", outputData.trim(), false);
                            if (results1[1] != null) {
                                String googleMap = results1[1];
                                openWeb(googleMap);
                            }

                        } else {
                            System.exit(0);
                        }

                        clear = true;
                        break;
                    case "d":
                        String[] userInputDesc = new String[2];

                        boolean ok = false;
                        while (!ok) {
                            userInputDesc[0] = getUserInputDesc("List your first station\n(Type \"show all\" to list all bus stops)");
                            if (userInputDesc[0] != null) {
                                ok = true;
                            } else {
                                System.exit(0);
                            }
                        }
                        ok = false;
                        while (!ok) {
                            userInputDesc[1] = getUserInputDesc("List your second station\n(Type \"show all\" to list all bus stops)");
                            if (userInputDesc[1] != null) {
                                ok = true;
                            } else {
                                System.exit(0);
                            }
                        }

                        String[] results2 = BusCalculateData.getDirections(userInputDesc);
                        String outputData = results2[0];
                        displayResults("Route to take", outputData.trim(), false);
                        if (results2[1] != null) {
                            String googleMap = results2[1];
                            openWeb(googleMap);
                        }
                        clear = true;
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            System.exit(0);
        }
    }
}
