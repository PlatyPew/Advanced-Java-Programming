// Author: Daryl
package busRoutes;

public class LtaBuses {

    private final String busCode;
    private final String routeSeq;
    private final String serviceNo;
    private final int direction;
    private final String distance;

    public LtaBuses(
            String busCode,
            String routeSeq,
            String serviceNo,
            int direction,
            String distance
    ) {

        this.busCode = busCode;
        this.serviceNo = serviceNo;
        this.routeSeq = routeSeq;
        this.direction = direction;
        this.distance = distance;
    }

    public String getBusCode() {
        return this.busCode;
    }

    public String getServiceNo() {
        return this.serviceNo;
    }

    public String getRouteSeq() {
        return this.routeSeq;
    }

    public int getDirection() {
        return this.direction;
    }

    public String getDistance() {
        return this.distance;
    }
}
