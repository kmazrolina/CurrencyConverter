package src.test;
import static org.junit.Assert.*;
public class Test {
    @Test
    public void givenRadius_whenCalculateArea_thenReturnArea() {
        double actualArea = Circle.calculateArea(1d);
        double expectedArea = 3.141592653589793;
        Assert.assertEquals(expectedArea, actualArea);
    }
}