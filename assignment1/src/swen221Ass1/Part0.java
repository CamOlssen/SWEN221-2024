package swen221Ass1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Part0 {  
  //This just tests that JUnit is working properly
  @Test void junitWorking(){ 
    assertTrue(true);
  }
  //This tests that (at least while running under JUnits)
  //assertions are enabled. We will see details of assertions soon.
  //This test should simply pass. If it fails on your system ask me or a tutor for support.
  @Test void assertionsWorking(){
    assertThrows(AssertionError.class,()->{assert false;});
  }
}
