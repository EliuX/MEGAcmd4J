package io.github.eliux.mega;

import org.junit.Assert;
import org.junit.Test;

public class MegaServerTest {

    @Test
    public void shouldStartServerWhenMegaInit(){
        //Given
        MegaServer.getCurrent().stop();

        //Then
        Assert.assertNotNull("Session is not available", Mega.init());
    }
}
