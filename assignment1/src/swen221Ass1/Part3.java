package swen221Ass1;

import static org.junit.jupiter.api.Assertions.*;
import static swen221Ass1.Part2.npe;
//As you can see from the above, having general testing methods
//split across unrelated files can get messy.
//Would a TestsUtil file with those methods be a better solution?

import static swen221Ass1.Part2.box;
//Having the above imports from a variety of sources may be problematic.
//But what about this one?
//Is this one ok since it come from a file testing those types?
//What do you think?
//Should creating basic data to facilitate testing a responsibility
//centralized in the test of those classes?

import java.util.Set;

import org.junit.jupiter.api.Test;

class Part3 {
  @Test void validStreet() {
    new Street(box(),"Willis street",2300);
  }
  @Test void streetIsRecord() {
    assertTrue(new Street(box(),"Willis street",2300) instanceof Record);
  }
  @Test void streetIntLength() {
    @SuppressWarnings("unused")
    int length= new Street(box(),"Willis street",2300).length();
    //it would not compile if it is not an int/Integer.
  }
  @Test void streetDataAccess1() {
    Street s= new Street(box(),"Willis street",2300);
    assertEquals(box(),s.boundingBox());
    assertEquals("Willis street",s.name());
    assertEquals(2300,s.length());
  }  
  @Test void streetDataAccess2() {
    Street s= new Street(box(),"Cuba street",1230);
    assertEquals(box(),s.boundingBox());
    assertEquals("Cuba street",s.name());
    assertEquals(1230,s.length());
  }  
  @Test void invalidStreetExample() {
    npe(()->new Street(null,"Willis street",2300));
  }
  @Test void invalidStreetExample2() {
    npe(()->new Street(box(),null,2300));
  }
  @Test void streetToString1() {
    assertEquals("Kelburn parade",new Street(box(),"Kelburn parade",2300).toString());
  }
  @Test void streetToString2() {
    assertEquals("Cuba street",new Street(box(),"Cuba street",1230).toString());
  }

  //City tests. 
  //Tests for different classes should go in their own individual files
  //But for the assignment sake this make it easier for you to see
  //how much progress you are doing for task 3 overall
  @Test void validCity() { city(); }

  @Test void cityNotRecord() {
    Object c= new City("Avalon", 500);
    assertFalse(c instanceof Record);
  }

  //We will see the meaning of Set<?> soon.
  //For now, you can just read it as 'any kind of Set'
  @Test void citySetOfStreets() {
    City c= new City("Avalon", 500);
    assertTrue(c.streets() instanceof Set<?>);
  }

  @Test void cityDataAccess1() {
    City c= new City("Wellington", 4);
    assertEquals("Wellington",c.name());
    assertEquals(4,c.population());
  }
  @Test void cityDataAccess2() {
    City c= new City("Genoa", 5);
    assertEquals("Genoa",c.name());
    assertEquals(5,c.population());
  }
  @Test void invalidCityExample() {
    npe(()->new City(null, 1));
  }
  @Test void cityUpdatePopulation() {
    City c=new City("Avalon", 500);
    c.population(2000);
    assertEquals(2000,c.population());
  }
  @Test void cityInvalidUpdatePopulation() {
    City c=new City("Avalon", 500);
    Part1.illegal(()->c.population(-2000));
  }
  @Test void cityAddStreet() {
    Street street = new Street(box(),"Main Street", 500);
    City c= new City("Avalon", 100);
    assertEquals(0,c.streets().size());
    c.addStreet(street);
    assertTrue(c.streets().contains(street));
    assertEquals(1,c.streets().size());
  }
  @Test void cityStreetsEmptyOnCreation() {
    City c= new City("Avalon", 100);
    assertTrue(c.streets().isEmpty());
  }
  
  static Street street1(){ return new Street(box(), "Main Street1", 501); }
  static Street street2(){ return new Street(box(), "Main Street2", 502); }
  static City city(){ 
    City res= new City("Wellington", 34);
    res.addStreet(street1());
    res.addStreet(street2());
    return res;
  }
  
  static Address address1(){ return new Address(street1(),city(),"13r"); }

  static Address address2(){ return new Address(street2(),city(),"12"); }

  @Test void validAddress() { address1(); }

  @Test void invalidAddressExample1() {
    npe(()->new Address(null,city(),"13r"));
  }  
  @Test void invalidAddressExample2() {
    npe(()->new Address(street1(),null,"13r"));
  }  
  
  @Test void invalidAddressExample3() {
    npe(()->new Address(street1(),city(),null));
  }  

  @Test void addressIsRecord() {
    assertTrue(new Address(street1(),city(),"13r") instanceof Record);
  }
  @Test void addressDataAccess1() {
    City c= city();
    Address a= new Address(street1(),c,"132b");
    assertEquals(street1(),a.street());
    assertEquals(c,a.city());
    assertEquals("132b",a.number());
  }
  @Test void addressDataAccess2() {
    City c= city();
    Address a= new Address(street2(),c,"43");
    assertEquals(street2(),a.street());
    assertEquals(c,a.city());
    assertEquals("43",a.number());
  }
}