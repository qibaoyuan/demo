package edu.learning.sample;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by SunLian on 2014/7/21.
 */
public class MCMCTest extends TestCase {
    private static final double DELTA=1e-15;

    @Test
    public void testIsTrue()throws Exception{
        MCMC mcmc = new MCMC();
        double x=0.1;
        double y=0.5;
        assertEquals(mcmc.isTrue(x, y), true);

    }
    @Test
    public void testCalculator()throws Exception{
        MCMC mcmc=new MCMC();
        assertEquals(3.1415927,mcmc.calculator(),DELTA);
    }
}