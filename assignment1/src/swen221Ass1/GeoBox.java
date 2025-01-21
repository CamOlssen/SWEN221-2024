package swen221Ass1;

record GeoBox(GeoPoint ne, GeoPoint sw) {
    GeoBox{
        if(ne.latitude() < sw.latitude() || ne.longitude() < sw.longitude()) throw new IllegalArgumentException();
        if(ne.latitude() - sw.latitude() > 30) throw new IllegalArgumentException();
        if(ne.longitude() - sw.longitude() > 30) throw new IllegalArgumentException();
        if(ne.latitude() > 80) throw new IllegalArgumentException();
        if(sw.latitude() < -80) throw new IllegalArgumentException();
    }
    GeoPoint center(){
        double centerLat = (ne.latitude() + sw.latitude())/2;
        double centerLong = (ne.longitude() + sw.longitude())/2;
        return new GeoPoint(centerLat, centerLong);
    }

    @Override
    public String toString(){
        return "GeoBox[ne="+ne.toString()+", sw="+sw.toString()+"]";
    }
    GeoPoint nw() {
        return new GeoPoint(ne.latitude(), sw.longitude());
    }

    GeoPoint se(){
        return new GeoPoint(sw.latitude(), ne.longitude());
    }

}
