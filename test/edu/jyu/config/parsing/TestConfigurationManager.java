package edu.jyu.config.parsing;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import edu.jyu.config.Bean;

public class TestConfigurationManager {

	@Test
	public void testGetBeanConfig() {
		Map<String, Bean> beanConfig = ConfigurationManager.getBeanConfig("/applicationContext.xml");
		for (Entry<String, Bean> e : beanConfig.entrySet()) {
			System.out.println(e.getKey() + ":" + e.getValue());
		}
	}
}
