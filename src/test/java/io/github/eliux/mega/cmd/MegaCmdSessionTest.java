package io.github.eliux.mega.cmd;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

import static org.junit.Assert.*;

public class MegaCmdSessionTest {


    @Test
    public void parseSessionIDShouldBeOk() {
        //Given
        String validResponse = "Your (secret) session is: Ae9r6XXXqUZGhXEIUoy7C85XhPq9vOAr2Sc94axXXXX-T3JZZE9kOEt3dDjWGMscV2il65Zo-mFMEXXX";

        //When
        final Optional<String> id = MegaCmdSession.parseSessionID(validResponse);

        //Then
        Assert.assertTrue(id.isPresent());

        Assert.assertEquals(
                "Ae9r6XXXqUZGhXEIUoy7C85XhPq9vOAr2Sc94axXXXX-T3JZZE9kOEt3dDjWGMscV2il65Zo-mFMEXXX",
                id.get()
        );
    }
}
