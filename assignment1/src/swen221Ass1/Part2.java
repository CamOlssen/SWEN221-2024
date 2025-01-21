package swen221Ass1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class Part2 {
  
  //making a method returning reusable data can be better than storing the data
  //in a field of the test suite: the data will be new every time and not influenced in
  //any way by the fact that some former test may have handled it (mutating it, for example)
  //A more advanced solution would be to use '@BeforeEach' as we will see later in the course.
  static GeoPoint zero() { return new GeoPoint(0d,0d); }
  
  static GeoPoint one() { return new GeoPoint(1d,1d); }
  
  static void npe(Executable e){//See, like 'invalid' but for NPE
    assertThrows(NullPointerException.class,e);
  }
  
  //More methods to create test data. Here showing an example using parameters
  static GeoBox box(double n, double e, double s, double w){
    return new GeoBox(new GeoPoint(n,e), new GeoPoint(s,w));
  }
  static GeoBox box(){ return box(1,1,0,0); }

  @Test void isRecord() { assertTrue(box() instanceof Record); }
  
  @Test void valid1() { new GeoBox(one(),zero()); }

  @Test void valid2() { new GeoBox(zero(),zero()); }
  
  @Test void valid3() {
    var a= new GeoPoint(0d, -160d);
    var b= new GeoPoint(0d, -170d);
    new GeoBox(a,b);
  }

  
  //Oh, how can we be using 'illegal' like this since it is defined
  //in another class? look to line '4' of this file to discover it!
  //Yes, as you can see, also Junit stuff is 'import static', that is
  //why we can write 'assertEquals' directly.
  @Test void invalid() { Part1.illegal(()->new GeoBox(zero(),one())); }

  @Test void maximumVerticalSpanOK() {
    GeoPoint ne = new GeoPoint(50d, 10d);
    GeoPoint sw = new GeoPoint(20d, 10d);
    new GeoBox(ne, sw);
  }
  @Test void maximumVerticalSpanNotOK1() {
    GeoPoint ne = new GeoPoint(51d, 10d);
    GeoPoint sw = new GeoPoint(20d, 10d);
    Part1.illegal(()->new GeoBox(ne, sw));
  }
  @Test void maximumVerticalSpanNotOK2() {
    GeoPoint ne = new GeoPoint(50d, 10d);
    GeoPoint sw = new GeoPoint(-20d, 10d);
    Part1.illegal(()->new GeoBox(ne, sw));
  }
  @Test void latitudeExceedsNorthBound() {
    Part1.illegal(()->new GeoBox(new GeoPoint(80.001d, 0), new GeoPoint(70, 0)));
  }
  @Test void latitudeExceedsSouthBound() {
    Part1.illegal(()->new GeoBox(new GeoPoint(-80.001d, 0), new GeoPoint(-70, 0)));
  }
  @Test void verticalSpanWithinLimit() {
    new GeoBox(new GeoPoint(30, 0), new GeoPoint(0, 0));
  }
  @Test void verticalSpanExceedsLimit() {
    Part1.illegal(()->new GeoBox(new GeoPoint(70, 0), new GeoPoint(-51, 0)));
  }
  @Test void horizontalSpanWithinLimit() {
    new GeoBox(new GeoPoint(0, 30), new GeoPoint(0, 0));
  }
  @Test void horizontalSpanExceedsLimit() {
    Part1.illegal(()->new GeoBox(new GeoPoint(0, 60), new GeoPoint(0, 1)));
  }
  @Test void horizontalSpanExceedsLimitCrossingAntimeridian() {
    Part1.illegal(()->new GeoBox(new GeoPoint(0, 150), new GeoPoint(0, -155)));
  }
  @Test void horizontalSpanOkLimitCrossingAntimeridianCenter() {
    new GeoBox(new GeoPoint(0, -170), new GeoPoint(0, 170));
  }
  @Test void northeastCornerIsNortheast() {
    new GeoBox(new GeoPoint(12, 21), new GeoPoint(6, 8));
  }
  @Test void northeastCornerIsNotNortheast1() {
    Part1.illegal(()->new GeoBox(new GeoPoint(2, 21), new GeoPoint(6, 8)));
  }
  @Test void northeastCornerIsNotNortheast2() {
    Part1.illegal(()->new GeoBox(new GeoPoint(12, 21), new GeoPoint(6, 28)));
  }
  @Test void geoBoxToString() {
    assertEquals(
      "GeoBox[ne=[latitude: 1.00000000, longitude: 1.00000000], sw=[latitude: 0.00000000, longitude: 0.00000000]]",
      box().toString()); 
  }
  @Test void centerPoint1() {
    assertEquals(new GeoPoint(0.5d, 0.5d), new GeoBox(one(),zero()).center());
  }
  @Test void centerPoint2() {
    var p= new GeoPoint(-10d, -10d);
    assertEquals(new GeoPoint(-5d, -5d), new GeoBox(zero(),p).center());
  }

  @Test void centerPoint3() {
    var a= new GeoPoint(0d, 170d);
    var b= new GeoPoint(0d, 160d);
    assertEquals(new GeoPoint(0d, 165d), new GeoBox(a,b).center());
  }
  @Test void centerPoint4() {
    var a= new GeoPoint(0d, -160d);
    var b= new GeoPoint(0d, -170d);
    assertEquals(new GeoPoint(0d, -165d), new GeoBox(a,b).center());
  }
  @Test void centerPoint5() {
    var a= new GeoPoint(0d, -175d);
    var b= new GeoPoint(0d, +175d);
    assertEquals(new GeoPoint(0d,-180d), new GeoBox(a,b).center());
  }
  @Test void centerPoint6() {
    var a= new GeoPoint(0d, -175d);
    var b= new GeoPoint(0d, +176d);
    assertEquals(new GeoPoint(0d,-179.5d), new GeoBox(a,b).center());
  }
  @Test void basePoints() {
    GeoBox box = new GeoBox(one(),zero());
    assertEquals(box.ne(),one());
    assertEquals(box.sw(),zero());
  }
  @Test void derivedPointsSE() {
    GeoBox box = new GeoBox(one(),zero());
    assertEquals(new GeoPoint(0d,1d),box.se());
  }
  @Test void derivedPointsNW() {
    GeoBox box = new GeoBox(one(),zero());
    assertEquals(new GeoPoint(1d,0d),box.nw());
  }
}
