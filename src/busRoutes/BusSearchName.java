// Author: Daryl
package busRoutes;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

public class BusSearchName {

    public static int listSearchResults(ArrayList<String> results) {
        String data = "";
        for (int i = 0; i < results.size(); i++) {
            data += String.format("  %d. %s\n", i + 1, results.get(i).trim()); //(i + 1) + ".\t" + results.get(i) + "\n";
        }
        boolean valid = false;
        int selection = 0;
        String userInput;

        while (!valid) {
            userInput = BusServiceMain.displayResults("Please select one of the following", data.substring(0, data.length() - 1), true);
            if (userInput == null) {
                break;
            }
            try {
                selection = Integer.parseInt(userInput);
                if (selection <= 0 || selection > results.size()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Choose a number that actually exists on the list",
                            "Invalid input",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    valid = true;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        null,
                        "That's not a number!",
                        "Invalid input",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
        return selection;
    }

    public static String searchByName(String userInput) {
        ArrayList<String> possibleBusStops = new ArrayList<String>();
        boolean all = false;
        if (userInput.equals("show all")) {
            all = true;
        }
        for (String desc : BusServiceMain.busDescList.keySet()) {
            if (desc.toLowerCase().contains(userInput.toLowerCase())) {
                possibleBusStops.add(desc);
            } else if (all) {
                possibleBusStops.add(desc);
            }
        }
        if (possibleBusStops.size() == 0) {
            return "empty";
        }
        Collections.sort(possibleBusStops);
        int choice = listSearchResults(possibleBusStops);
        if (choice != 0) {
            return BusServiceMain.busDescList.get(possibleBusStops.get(choice - 1));
        } else {
            return null;
        }

    }
}
