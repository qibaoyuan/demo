package demo.example;

import junit.framework.TestCase;
import org.junit.Test;

public class ExampleTest extends TestCase {
    @Test
    public void testAdd() throws Exception {
        Example example = new Example();
        int a1 = 20;
        int a2 = 50;
        assertEquals(70, example.add(a1, a2));
    }
}