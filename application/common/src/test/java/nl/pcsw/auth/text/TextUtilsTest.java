package nl.pcsw.auth.text;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextUtilsTest {

    @Test
    public void stringDoesContainText() {
        assertTrue(TextUtils.hasText(" Er zit text in!    "));
    }

    @Test
    public void stringDoesNotContainText() {
        assertFalse(TextUtils.hasText("                  "));
    }
}
