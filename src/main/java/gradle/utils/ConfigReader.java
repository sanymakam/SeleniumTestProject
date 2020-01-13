package gradle.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

	public static String getConfigValue(String Key) {

		String configPath = "src/test/resources/config.properties";
		String value = "";

		try {
			FileInputStream fis = new FileInputStream(configPath);
			Properties p = new Properties();
			p.load(fis);
			value = p.getProperty(Key);
		} catch (Exception e) {

		}
		return value;
	}
}
