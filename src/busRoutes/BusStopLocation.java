/* 
@author Daryl
*/
package busRoutes;

public class BusStopLocation extends BusStop {

    private String busCode;
    private String zID;
    private double x_coords;
    private double y_coords;

    public BusStopLocation(
            String busCode,
            String roadDesc,
            String busStopDesc,
            String zID,
            double x_coords,
            double y_coords
    ) {
        super(busCode, roadDesc, busStopDesc);

        this.zID = zID;
        this.x_coords = x_coords;
        this.y_coords = y_coords;
        this.busCode = busCode;
    }

    public String getZID() {
        return this.zID;
    }

    public double getX_coords() {
        return this.x_coords;
    }

    public double getY_coords() {
        return this.y_coords;
    }

    public String getBusCode() {
        return this.busCode;
    }
}
