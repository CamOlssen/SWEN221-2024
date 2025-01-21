package swen221Ass1;

record Address(Street street, City city, String number) {
    Address{
        if(street == null || city == null || number == null || number.isEmpty()) throw new NullPointerException();
    }

    @Override
    public String toString() {
        return "Number "+number+" "+street.name()+", "+city.name();
    }
}
