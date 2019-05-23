package Sample;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class Sample_test {
	
	//this will pass
  @Test
  public void Sample_calculator_test_correct() {
    Sample_calculator calculator = new Sample_calculator();
    int sum = calculator.evaluate("1+2+3");
    assertEquals(6, sum);
  }
  
  	//this will fail
  @Test
  public void Sample_calculator_test_wrong() {
    Sample_calculator calculator = new Sample_calculator();
    int sum = calculator.evaluate("1+2+3");
    assertEquals(10, sum);
  }
}