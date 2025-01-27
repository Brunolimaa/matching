package com.device.matching.util;

import com.device.matching.model.Device;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

public class UserAgentParser {

    /**
     * Parses the User-Agent string and extracts device information.
     *
     * @param userAgent The User-Agent string.
     * @return A Device object with the extracted information.
     */
    public static Device parseUserAgent(String userAgent) {
        Device device = new Device();

        // Parsing the User-Agent string to get detailed information
        UserAgent userAgentInfo = UserAgent.parseUserAgentString(userAgent);

        // Extracting the operating system details
        OperatingSystem os = userAgentInfo.getOperatingSystem();
        String osName = os.getName();
        String osVersion = extractOsVersion(userAgent, osName); // Extracting the OS version

        // Extracting the browser details
        Browser browser = userAgentInfo.getBrowser();
        String browserName = browser.getName();
        String browserVersion = String.valueOf(browser.getVersion(userAgent));

        // Formatting the OS version by trimming, converting to lowercase, and replacing spaces with hyphens
        var osVersionFormatted = osVersion.trim().toLowerCase().replaceAll("\\s+", "-");

        // Setting the parsed details in the Device object
        device.setOsName(osName);
        device.setOsVersion(osVersionFormatted);
        device.setBrowserName(browserName);
        device.setBrowserVersion(browserVersion);

        // Setting a unique device ID based on the formatted OS version and browser version
        device.setDeviceId(osVersionFormatted, browserVersion);

        return device;
    }

    /**
     * A helper method to try to extract the operating system version from the User-Agent string.
     */
    private static String extractOsVersion(String userAgent, String osName) {
        if (userAgent.contains("Windows NT")) {
            // For Windows OS, extract the version
            return userAgent.replaceAll(".*Windows NT (\\d+\\.\\d+).*", "Windows $1");
        } else if (userAgent.contains("Mac OS X")) {
            // For MacOS, extract the version
            return userAgent.replaceAll(".*Mac OS X (\\d+\\.\\d+).*", "Mac OS X $1");
        } else if (userAgent.contains("Linux")) {
            // For Linux, check for specific Linux distributions
            if (userAgent.contains("Ubuntu")) {
                return "Ubuntu";
            } else if (userAgent.contains("Fedora")) {
                return "Fedora";
            } else if (userAgent.contains("Debian")) {
                return "Debian";
            } else if (userAgent.contains("CentOS")) {
                return "CentOS";
            } else {
                // If no specific distribution is identified, return "Linux"
                return "Linux";
            }
        }
        return "Unknown OS Version"; // If OS is unknown or not recognized
    }

    /**
     * Extracts the operating system version from the User-Agent string.
     * @param userAgent The complete User-Agent string.
     * @param osName The name of the operating system.
     * @return The operating system version if available.
     */
    private static String extractVersionFromUserAgent(String userAgent, String osName) {
        // Find the index of the operating system name in the User-Agent string
        int index = userAgent.indexOf(osName);
        if (index != -1) {
            String version = userAgent.substring(index + osName.length()).trim();
            // Checks if the version is followed by a non-alphanumeric character
            if (version.matches(".*\\d.*")) {
                // Returns the version up to the first space
                return version.split(" ")[0];
            }
        }
        return "Unknown Version"; // If version extraction fails, return this
    }
}
