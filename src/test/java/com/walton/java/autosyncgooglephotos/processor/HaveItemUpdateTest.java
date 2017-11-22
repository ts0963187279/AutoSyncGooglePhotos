package com.walton.java.autosyncgooglephotos.processor;

import org.junit.Assert;
import org.junit.Test;

public class HaveItemUpdateTest {
    @Test
    public void testExecute(){
        HaveItemUpdate haveItemUpdate = new HaveItemUpdate();
        boolean expected = true;
        Assert.assertEquals(expected,haveItemUpdate.execute(1));
        expected = false;
        Assert.assertEquals(expected,haveItemUpdate.execute(1));
        expected = true;
        Assert.assertEquals(expected,haveItemUpdate.execute(0));
    }
}
