package com.walton.java.autosyncgooglephotos.processor;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class GetTimeTest {
    @Test
    public void testExecute(){
        GetTime getTime = new GetTime();
        String expected="1970/01/01 08:00:00";
        Date stubDate = new Date(0);
        Assert.assertEquals(expected,getTime.execute(stubDate));
    }
}
