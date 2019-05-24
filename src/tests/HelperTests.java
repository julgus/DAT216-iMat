package tests;

import controls.MyProfileController;
import helper.Helper;
import org.junit.Assert;
import org.junit.Test;

public class HelperTests {
    @Test
    public void testCensorText(){
        Assert.assertEquals("**tt",  Helper.onlyShowLastCharacters("katt", 2));
        Assert.assertEquals("",  Helper.onlyShowLastCharacters("", 2));
        Assert.assertEquals("tt",  Helper.onlyShowLastCharacters("tt", 2));
        Assert.assertEquals(null,  Helper.onlyShowLastCharacters(null, 2));
        Assert.assertEquals("katt",  Helper.onlyShowLastCharacters("katt",  20));
    }
}
