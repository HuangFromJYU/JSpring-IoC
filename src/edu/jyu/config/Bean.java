package edu.jyu.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 对应配置文件中bean标签的信息
 * 
 * @author Jason
 */

public class Bean {
	
	public static final String SINGLETON = "singleton";
	public static final String PROTOTYPE = "prototype";
	
	private String name;
	private String className;
	// 默认创建的bean对象设置成是单例的
	private String scope = SINGLETON;
	private List<Property> properties = new ArrayList<Property>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public String toString() {
		return "Bean [name=" + name + ", className=" + className + ", scope=" + scope + ", properties=" + properties
				+ "]";
	}
	
}
