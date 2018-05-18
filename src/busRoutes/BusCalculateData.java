// Author: Daryl
package busRoutes;

import java.util.ArrayList;
import java.util.HashSet;

public class BusCalculateData {

    public static String[] getDirections(String[] userInput) {
        String[] direction = new String[2];
        boolean requireTransfer = true;
        ArrayList<String> listOfServices = new ArrayList<>();
        ArrayList<String> transfer = new ArrayList<>();

        if (BusServiceMain.busStopLocationList.get(userInput[0]) == null || BusServiceMain.busStopLocationList.get(userInput[1]) == null) {
            direction[0] = "One of these bus stops do not have any buses!";
            direction[1] = null;
            return direction;
        }

        direction[1] = String.format("https://maps.google.com/?saddr=%s,%s&daddr=%s,%s",
                BusServiceMain.busStopLocationList.get(userInput[0]).getY_coords(),
                BusServiceMain.busStopLocationList.get(userInput[0]).getX_coords(),
                BusServiceMain.busStopLocationList.get(userInput[1]).getY_coords(),
                BusServiceMain.busStopLocationList.get(userInput[1]).getX_coords());

        if (userInput[0].equals(userInput[1])) { // Check if station inputted is the same
            direction[0] = "Are you high? You are already there!\n\n"
                    + "Bus code: " + BusServiceMain.busStopLocationList.get(userInput[0]).getBusCode() + "\n"
                    + "Bus stop description: " + BusServiceMain.busStopLocationList.get(userInput[0]).getBusStopDesc() + "\n"
                    + "Road description: " + BusServiceMain.busStopLocationList.get(userInput[0]).getRoadDesc();
            return direction;
        }

        // Gets all non transfer buses
        for (LtaBuses busObjStart : (ArrayList<LtaBuses>) BusServiceMain.ltabuses.get(userInput[0])) {
            for (LtaBuses busObjEnd : (ArrayList<LtaBuses>) BusServiceMain.ltabuses.get(userInput[1])) {
                if (busObjStart.getServiceNo().equals(busObjEnd.getServiceNo())) {
                    requireTransfer = false;
                    listOfServices.add(busObjStart.getServiceNo());
                }
            }
        }

        if (requireTransfer) {
            direction[0] = "Bus transfer required!\n"
                    + "Start:\n"
                    + "      Bus code: " + BusServiceMain.busStopLocationList.get(userInput[0]).getBusCode() + "\n"
                    + "      Bus stop description: " + BusServiceMain.busStopLocationList.get(userInput[0]).getBusStopDesc() + "\n"
                    + "      Road description: " + BusServiceMain.busStopLocationList.get(userInput[0]).getRoadDesc() + "\n"
                    + "End:\n"
                    + "      Bus code: " + BusServiceMain.busStopLocationList.get(userInput[1]).getBusCode() + "\n"
                    + "      Bus stop description: " + BusServiceMain.busStopLocationList.get(userInput[1]).getBusStopDesc() + "\n"
                    + "      Road description: " + BusServiceMain.busStopLocationList.get(userInput[1]).getRoadDesc() + "\n\n";

            // Get all bus numbers from ltabuses
            // Loop through all numbers and feed them into busRoutes
            int transfers = 0;
            for (LtaBuses busStartObj : (ArrayList<LtaBuses>) BusServiceMain.ltabuses.get(userInput[0])) {
                for (LtaBuses busEndObj : (ArrayList<LtaBuses>) BusServiceMain.ltabuses.get(userInput[1])) {
                    transfer = getSimilar(BusServiceMain.busRoutes.get(busStartObj.getServiceNo()), BusServiceMain.busRoutes.get(busEndObj.getServiceNo())); // Find similarities in bus codes for 
                    if (!transfer.isEmpty()) { // Checks to make sure there is a way to transfer
                        direction[0] += "Take the bus: " + busStartObj.getServiceNo() + "\n"
                                + "Transfer to bus " + busEndObj.getServiceNo() + " at:\n"
                                + "      Bus code: " + BusServiceMain.busStopLocationList.get(transfer.get(0)).getBusCode() + "\n"
                                + "      Bus stop description: " + BusServiceMain.busStopLocationList.get(transfer.get(0)).getBusStopDesc() + "\n"
                                + "      Road description: " + BusServiceMain.busStopLocationList.get(transfer.get(0)).getRoadDesc() + "\n\n";
                        transfers++;
                    }
                }
            }
            
            if (transfers == 0) {
                direction[0] += "Sorry, this program does not support routes with 2 or more transfers";
            }

            return direction;
        } else {
            direction[0] = "No transfer required\n"
                    + "Take the direct bus: " + String.join(", ", new HashSet<String>(listOfServices)) + "\n"
                    + "Start:\n"
                    + "      Bus code: " + BusServiceMain.busStopLocationList.get(userInput[0]).getBusCode() + "\n"
                    + "      Bus stop description: " + BusServiceMain.busStopLocationList.get(userInput[0]).getBusStopDesc() + "\n"
                    + "      Road description: " + BusServiceMain.busStopLocationList.get(userInput[0]).getRoadDesc() + "\n"
                    + "End:\n"
                    + "      Bus code: " + BusServiceMain.busStopLocationList.get(userInput[1]).getBusCode() + "\n"
                    + "      Bus stop description: " + BusServiceMain.busStopLocationList.get(userInput[1]).getBusStopDesc() + "\n"
                    + "      Road description: " + BusServiceMain.busStopLocationList.get(userInput[1]).getRoadDesc();
            return direction;
        }
    }

    public static ArrayList<String> getSimilar(ArrayList<String> startBus, ArrayList<String> endBus) {
        ArrayList<String> pureBusCode = new ArrayList<>(); // Just a variable to store codes that are actually numbers
        startBus.retainAll(endBus);
        for (String busCode : startBus) {
            try {
                Integer.parseInt(busCode); // Determines if number can be parsed
                pureBusCode.add(busCode);
            } catch (NumberFormatException e) {
            }
        }
        return pureBusCode;
    }
}
