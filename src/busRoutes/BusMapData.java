/* 
@author Daryl
*/
package busRoutes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BusMapData {

    public static void mapBusStops() throws IOException { // Put lta-bus_stop_codes.csv information into hashmap BUSSTOP
        BufferedReader bus_stop = new BufferedReader(new FileReader(BusServiceMain.BUSSTOP));
        String line = bus_stop.readLine(); // Removes header (first line) from csv file
        while ((line = bus_stop.readLine()) != null) {
            String[] bus_stop_columns = line.split(","); // Splits the CSV file using the comma as the delimeter
            String busCode;
            try {
                busCode = String.format("%05d", Integer.parseInt(bus_stop_columns[0])); // zfills the string with a trailing zero
            } catch (Exception e) {
                busCode = bus_stop_columns[0];
            }
            BusServiceMain.busStopList.put(busCode, new BusStop(busCode, bus_stop_columns[1], bus_stop_columns[2]));

            // For searching purposes
            BusServiceMain.busDescList.put(bus_stop_columns[2], busCode); // Sets bus stop description as the key and bus code as the value
        }
    }

    public static void mapBusStopLocations() throws IOException {
        BufferedReader bus_stop_location = new BufferedReader(new FileReader(BusServiceMain.BUSSTOPLOCATION));
        String line = bus_stop_location.readLine(); // Removes header (first line) from csv file
        while ((line = bus_stop_location.readLine()) != null) {
            String[] bus_stop_location_columns = line.split(",");
            String busCode;
            try {
                busCode = String.format("%05d", Integer.parseInt(bus_stop_location_columns[3]));
            } catch (Exception e) {
                busCode = bus_stop_location_columns[3];
            }
            BusStop bus_stop_obj = BusServiceMain.busStopList.get(busCode); // Getting BusStop object as superclass (If that even makes sense)
            if (bus_stop_obj != null) {
                BusServiceMain.busStopLocationList.put(busCode, new BusStopLocation( // Uses bus code as key and BusStopLocation object as value
                        bus_stop_obj.getBusCode(), // Bus Code
                        bus_stop_obj.getRoadDesc(), // Road Description
                        bus_stop_obj.getBusStopDesc(), // Bus Stop Description
                        bus_stop_location_columns[2], // ZID
                        Double.parseDouble(bus_stop_location_columns[0]), // X coords
                        Double.parseDouble(bus_stop_location_columns[1]) // Y coords
                ));
            }
        }
    }

    public static void mapBusRoutes() throws IOException {
        for (String key : BusServiceMain.busStopList.keySet()) { // Init hashmap with buscode(key) and arraylist(array of ltabuses objects)
            BusServiceMain.ltabuses.put(key, new ArrayList());
        }
        for (int i = 0; i < 2; i++) { // Stores both SBS and SMRT buses to LtaBuses hashmap
            BufferedReader bus_route = new BufferedReader(new FileReader(new String[]{BusServiceMain.SBSROUTE, BusServiceMain.SMRTROUTE}[i]));;
            String line1 = bus_route.readLine(); // Removes header (first line) from csv file
            while ((line1 = bus_route.readLine()) != null) {
                String[] bus_route_columns = line1.split(",");
                String busCode;
                try {
                    busCode = String.format("%05d", Integer.parseInt(bus_route_columns[3]));
                } catch (Exception e) {
                    busCode = bus_route_columns[3];
                }
                BusServiceMain.ltabuses.get(busCode).add(new LtaBuses( // Addes LtaBuses Object into array
                        busCode,
                        bus_route_columns[2], //Route
                        bus_route_columns[0], // Bus number
                        Integer.parseInt(bus_route_columns[1]), // Direction
                        bus_route_columns[4] // Distance
                ));
                if (BusServiceMain.busRoutes.get(bus_route_columns[0]) == null) { // Adds service number as key
                    BusServiceMain.busRoutes.put(bus_route_columns[0], new ArrayList());
                }
                BusServiceMain.busRoutes.get(bus_route_columns[0]).add(busCode); // Uses an array which contains all buss codes for respective servuce number as value

            }
        }
    }

}
