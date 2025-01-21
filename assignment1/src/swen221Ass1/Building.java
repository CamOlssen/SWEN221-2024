package swen221Ass1;

import java.util.Set;

interface Building {
    static Building of(GeoBox boundingBox, Address primaryAddress){
        if(boundingBox == null || primaryAddress == null)
            throw new NullPointerException();
        overlapTest(boundingBox, primaryAddress.street().boundingBox());
        return new SingleAddressBuilding(boundingBox, primaryAddress);
    }
    static Building of(GeoBox boundingBox, Address primaryAddress, Set<Address> secondaryAddresses){
        if(boundingBox == null || primaryAddress == null || secondaryAddresses == null)
            throw new NullPointerException();
        if(secondaryAddresses.isEmpty()) throw new IllegalArgumentException();
        Set<Address> newAddresses = Set.copyOf(secondaryAddresses);
        for(Address a : newAddresses){
            overlapTest(boundingBox, a.street().boundingBox());
        }
        return new MultiAddressBuilding(boundingBox, primaryAddress, newAddresses);
    }
    static void overlapTest(GeoBox box1, GeoBox box2){
        if(box2.sw().latitude() > box1.ne().latitude() ||
                box2.sw().longitude() >box1.ne().longitude() ||
                box1.sw().latitude() > box2.ne().latitude() ||
                box1.sw().longitude() > box2.ne().longitude()) throw new IllegalArgumentException();
    };
    GeoBox boundingBox();
    Address primaryAddress();
    Set<Address> secondaryAddresses();
}

record SingleAddressBuilding(GeoBox boundingBox, Address primaryAddress) implements Building {
    @Override
    public Set<Address> secondaryAddresses() {
        return Set.of();
    }
}

record MultiAddressBuilding(GeoBox boundingBox, Address primaryAddress, Set<Address> secondaryAddresses) implements Building{

}