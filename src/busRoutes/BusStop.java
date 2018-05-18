// Author: PlatyPew
package busRoutes;

public class BusStop {

    private String busCode;
    private String roadDesc;
    private String busStopDesc;

    public BusStop(String busCode, String roadDesc, String busStopDesc) {
        this.busCode = busCode;
        this.roadDesc = roadDesc;
        this.busStopDesc = busStopDesc;
    }

    public String getBusCode() {
        return this.busCode;
    }

    public String getRoadDesc() {
        return this.roadDesc;
    }

    public String getBusStopDesc() {
        return this.busStopDesc;
    }
}
