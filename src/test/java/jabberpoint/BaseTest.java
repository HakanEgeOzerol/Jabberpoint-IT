package jabberpoint;

import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    @BeforeAll
    public static void setUpHeadlessMode() {
        // Ensure we're running in headless mode for CI environments
        System.setProperty("java.awt.headless", "true");
    }
} 