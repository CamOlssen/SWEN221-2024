package swen221Ass1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

//As you can see, we are starting to get compounding returns
//from our methods defining convenient data to test
import static swen221Ass1.Part2.box;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Part4 {
  static Building simpleB(){ return Building.of(Part2.box(), Part3.address1()); }

  static Building multiB(){ return Building.of(Part2.box(), Part3.address1(),Set.of(Part3.address2())); }

  @Test void validSimple(){ simpleB(); }

  @Test void validMultiAddress(){ multiB(); }

  @Test void invalidBuildingExample(){ 
    Part2.npe(()->Building.of(null, Part3.address1()));
  }
  @Test void invalidBuildingExampleAlt(){ 
    Part2.npe(()->Building.of(Part2.box(), null));
  }
  @Test void invalidBuildingExampleAlt2(){ 
    Part1.illegal(()->Building.of(Part2.box(), Part3.address1(),Set.of()));
  }
  @Test void invalidBuildingExampleAlt3(){ 
    Part2.npe(()->Building.of(Part2.box(), Part3.address1(),null));
  }
  //(5,6, 2,2) does not overlap with (1,1,0,0)
  @Test void invalidBuildingExampleAlt4(){ 
    Part1.illegal(()->Building.of(Part2.box(5,6, 2,2), Part3.address1()));
  }  
  //About the test below. Think: what are advantages and disadvantages
  //of using string representations for testing equality?
  @Test void buildingDataAccess1(){
    Building b= simpleB();
    Assertions.assertEquals(Part2.box().toString(),
      b.boundingBox().toString());
    Assertions.assertEquals(Part3.address1().toString(),
      b.primaryAddress().toString());
    assertEquals(Set.of(),
      b.secondaryAddresses());  
  }
  @Test void buildingDataAccess2(){
    Building b= multiB();
    Assertions.assertEquals(Part2.box().toString(),
      b.boundingBox().toString());
    Assertions.assertEquals(Part3.address1().toString(),
      b.primaryAddress().toString());
    Assertions.assertEquals(Set.of(Part3.address2()).toString(),
      b.secondaryAddresses().toString());  
  }
  
  //We will see .getClass() later.
  //This test is about the optimization requirement.
  //We do not want buildings with multiple addresses to be the same
  //type as buildings with only one.
  //We will see later that the correct terminology for this is:
  //"Checking that their dynamic types are different"  
  @Test void twoKindsOfBuildings(){
    assertTrue( simpleB().getClass()!=multiB().getClass() );
  }
  @Test void secondaryAliasEncapsulatedIn(){
    Set<Address> s= new HashSet<>(Set.of(Part3.address2()));
    Building b= Building.of(Part2.box(), Part3.address1(),s);
    assertEquals(1, b.secondaryAddresses().size());
    s.clear();
    assertEquals(1,b.secondaryAddresses().size());
  }
  //How to pass this last test? give a look to
  //Collections.unmodifiableSet or Set.copyOf
  @Test void secondaryAliasEncapsulatedOut(){
    Set<Address> s= new HashSet<>(Set.of(Part3.address2()));
    Building b= Building.of(Part2.box(), Part3.address1(),s);
    assertEquals(1, b.secondaryAddresses().size());
    assertThrows(UnsupportedOperationException.class,
      ()->b.secondaryAddresses().clear());
    //This tests attempts to check that the user can not
    //remove streets from the collection of streets of a city.
    //What would otherwise happen if a street was removed from a city,
    //when valid addresses are using that street?
  }
  
  //And now some tests checking overlapping GeoBoxes
  //look carefully how the code below makes easy
  //to write a lot of tests.
  void checkOverlap(boolean ok, GeoBox box1, GeoBox box2){
    City c= Part3.city();
    Street s1= new Street(box1, "a", 1);
    Street s2= new Street(box2, "a", 1);
    c.addStreet(s1);
    c.addStreet(s2);
    Address a1= new Address(s1,c,"12a");
    Address a2= new Address(s2,c,"23r");
    Building.of(box1,a1);//ok no failure here ever
    if(ok) {
      Building.of(box1,a1,Set.of(a2));//may fail
      Building.of(box1,a2);//We test both as primary and as secondary
    }
    else {
      Part1.illegal(()->Building.of(box1,a1,Set.of(a2)));
      Part1.illegal(()->Building.of(box1,a2));
    }
  }
  void overlapKo(GeoBox box){ checkOverlap(false, Part2.box(1, 1, 0, 0), box); }
 
  @Test void aboveWithAGap(){ overlapKo(Part2.box(2.1, 1, 1.1, 0)); }
  
  @Test void belowWithAGap(){ overlapKo(Part2.box(-0.1, 1, -1.1, 0)); }

  @Test void toTheRightWithAGap(){ overlapKo(Part2.box(1, 2.1, 0, 1.1)); }

  @Test void toTheLeftWithAGap(){ overlapKo(Part2.box(1, -0.1, 0, -1.1)); }

  @Test void diagonallyAboveToTheRightWithAGap(){ overlapKo(Part2.box(2.1, 2.1, 1.1, 1.1)); }

  @Test void diagonallyAboveToTheLeftWithAGap(){ overlapKo(Part2.box(2.1, -0.1, 1.1, -1.1)); }

  @Test void diagonallyBelowToTheRightWithAGap(){ overlapKo(Part2.box(-0.1, 2.1, -1.1, 1.1)); }

  @Test void diagonallyBelowToTheLeftWithAGap(){ overlapKo(Part2.box(-0.1, -0.1, -1.1, -1.1)); }

  @Test void acrossTheTopWithAGap(){ overlapKo(Part2.box(2.1, 2, 1.1, -1)); }

  @Test void alongTheRightSideWithAGap(){ overlapKo(Part2.box(2, 2.1, -1, 1.1)); }

  @Test void alongTheLeftSideWithAGap(){ overlapKo(Part2.box(2, -0.1, -1, -1.1)); }

  @Test void acrossTheBottomTopWithAGap(){ overlapKo(Part2.box(-0.1, 2, -1.1, -1)); }

  void overlapOk(GeoBox box){ checkOverlap(true, Part2.box(1, 1, 0, 0), box); }

  @Test void allOutside(){ overlapOk(Part2.box(2, 2, -1, -1)); }

  @Test void allInside(){ overlapOk(Part2.box(0.9, 0.9, 0.1, 0.1)); }

  @Test void cornerInsideNE(){ overlapOk(Part2.box(1.5, 1.5, 0.5, 0.5)); }

  @Test void cornerInsideNW(){ overlapOk(Part2.box(1.5, 0.5, 0.5, -0.5)); }

  @Test void cornerInsideSE(){ overlapOk(Part2.box(0.5, 1.5, -0.5, 0.5)); }

  @Test void cornerInsideSW(){ overlapOk(Part2.box(0.5, 0.5, -0.5, -0.5)); }

  @Test void sideInsideN(){ overlapOk(Part2.box(1.1, 0.9, 0.9, 0.1)); }

  @Test void sideInsideS(){ overlapOk(Part2.box(0.1, 0.9, -0.1, 0.1)); }

  @Test void sideInsideE(){ overlapOk(Part2.box(0.9, 1.1, 0.1, 0.9)); }

  @Test void sideInsideW(){ overlapOk(Part2.box(0.9, 0.1, 0.1, -0.1)); }
  
  @Test void crossTall(){ overlapOk(Part2.box(2, 0.75, -1, 0.25)); }

  @Test void crossLarge(){ overlapOk(Part2.box(0.75, 2, 0.25, -1)); }
}