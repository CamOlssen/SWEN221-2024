package swen221Ass1;

record Street(GeoBox boundingBox, String name, int length) {
    /**
     * @param boundingBox The GeoBox which encompasses the street.
     * @param name The street's name.
     * @param length The street's length in meters. Must be positive.
     */
    Street{
        if(boundingBox == null || name.isEmpty() || name == null) throw new NullPointerException();
        if(length <= 0) throw new IllegalArgumentException();
    }

    /**
     * @return The street's name
     */
    @Override
    public String toString() {
        return name;
    }
}
