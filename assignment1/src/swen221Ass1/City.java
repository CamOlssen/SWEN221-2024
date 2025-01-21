package swen221Ass1;

import java.util.HashSet;
import java.util.Set;

class City {
    String name; //The name of the city.
    int population; //Population of the city.
    Set<Street> streets = new HashSet<>(); //HashSet of all streets.

    /**
     * @param name The name of the city.
     * @param population The city's population. Must be positive.
     */
    City(String name, int population){
        if(name == null || name.isEmpty()) throw new NullPointerException();
        if(population < 0) throw new IllegalArgumentException();
        this.name = name;
        this.population = population;
    }

    /**
     * @return the city's name
     */
    String name(){
        return name;
    }

    /**
     * @return The city's population
     */
    int population(){
        return population;
    }

    /**
     * @param update The city's new population.
     */
     void population(int update){
        if(update < 0)
            throw new IllegalArgumentException();
        population = update;
    }

    /**
     * @return Set of streets in the city.
     */
    Set streets(){
        return streets;
    }

    /**
     * @param s The new street to add
     */
    void addStreet(Street s){
        streets.add(s);
    }
}
