import junit.framework.Test;
import junit.framework.TestSuite;

public class Deliverable3 {

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTestSuite(TestCases.class);
    return suite;
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
}
