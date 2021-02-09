package Tests;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

/**
 * This class do not have any test yet because those methods are
 * all private and requires user inputs.
 */
class PlayerInputsTest {

    @Test
    void TestGetIndexOfCardToPlay() {
        String data = "4";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        //PlayerInputs playerInputs = new PlayerInputs();

    }

}