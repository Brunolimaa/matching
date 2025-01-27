package com.device.matching.util;

import com.device.matching.model.Device;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserAgentParserTest {

    private Device device;

    @BeforeEach
    void setUp() {
        device = new Device();
    }

    @Test
    void testParseUserAgent_withWindowsOS() {
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";

        // Parse User-Agent string
        Device parsedDevice = UserAgentParser.parseUserAgent(userAgent);

        // Assert that the parsed OS and browser information are correct
        assertEquals("Windows 10", parsedDevice.getOsName());
        assertEquals("windows-10.0", parsedDevice.getOsVersion());
        assertEquals("Chrome 9", parsedDevice.getBrowserName());
        assertEquals("91.0.4472.124", parsedDevice.getBrowserVersion());
    }

    @Test
    void testParseUserAgent_withMacOS() {
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";

        // Parse User-Agent string
        Device parsedDevice = UserAgentParser.parseUserAgent(userAgent);

        // Assert that the parsed OS and browser information are correct
        assertEquals("Mac OS X", parsedDevice.getOsName());
        assertEquals("Chrome 9", parsedDevice.getBrowserName());
        assertEquals("91.0.4472.124", parsedDevice.getBrowserVersion());
    }

    @Test
    void testParseUserAgent_withLinux() {
        String userAgent = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";

        // Parse User-Agent string
        Device parsedDevice = UserAgentParser.parseUserAgent(userAgent);

        // Assert that the parsed OS and browser information are correct
        assertEquals("Ubuntu", parsedDevice.getOsName());
        assertEquals("ubuntu", parsedDevice.getOsVersion());
        assertEquals("Chrome 9", parsedDevice.getBrowserName());
        assertEquals("91.0.4472.124", parsedDevice.getBrowserVersion());
    }

    @Test
    void testParseUserAgent_withUnknownOS() {
        String userAgent = "Mozilla/5.0 (Unknown; rv:91.0) Gecko/20100101 Firefox/91.0";

        // Parse User-Agent string
        Device parsedDevice = UserAgentParser.parseUserAgent(userAgent);

        // Assert that the OS is marked as "Unknown"
        assertEquals("unknown-os-version", parsedDevice.getOsVersion());
        assertEquals("Firefox 9", parsedDevice.getBrowserName());
        assertEquals("91.0", parsedDevice.getBrowserVersion());
    }

}
