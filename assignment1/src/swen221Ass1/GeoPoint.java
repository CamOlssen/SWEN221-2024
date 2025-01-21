package swen221Ass1;

record GeoPoint(double latitude, double longitude) {
    GeoPoint{
        if(latitude > 90 || latitude < -90 || longitude >= 180 || longitude < -180){ //valid point ranges
            throw new IllegalArgumentException();
        }
    }
    @Override
    public String toString() {
        return toString(8);
    }

    /**
     * @param precision The number of decimal places to truncate our values to
     * @return A string of the point's latitude and longitude values.
     */
    public String toString(int precision){
        String latString = String.format("[latitude: %."+precision+"f, ", latitude);
        String longString = String.format("longitude: %."+precision+"f]", longitude);
        return latString + longString;
    }

    /**
     * @param point the GeoPoint to calculate the midpoint with
     * @return a new GeoPoint at the average of our two points' coordinates
     */
    public GeoPoint average(GeoPoint point){
        double averageLat = (latitude + point.latitude())/2;
        double averageLong = (point.longitude() + longitude)/2;
        return new GeoPoint(averageLat, averageLong);
    }
}