package edu.jyu.config;

/**
 * 对应配置文件中bean标签下的property标签的信息
 * 
 * @author Jason
 */
public class Property {
	private String name;
	private String value;
	private String ref;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	@Override
	public String toString() {
		return "Property [name=" + name + ", value=" + value + ", ref=" + ref + "]";
	}
	
}
