package edu.jyu.config.parsing;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import edu.jyu.config.Bean;
import edu.jyu.config.Property;

/**
 * 读取配置文件的类
 * 
 * @author Jason
 */
public class ConfigurationManager {

	/**
	 * 根据指定的路径读取配置文件
	 * 
	 * @param path
	 *            配置文件路径
	 * @return  
	 */
	public static Map<String, Bean> getBeanConfig(String path) {
		// 存放配置信息，返回结果
		Map<String, Bean> result = new HashMap<String, Bean>();
		// 创建解析器
		SAXReader reader = new SAXReader();
		// 加载配置文件
		InputStream is = ConfigurationManager.class.getResourceAsStream(path);
		Document doc = null;
		try {
			doc = reader.read(is);
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException("加载配置文件出错");
		}
		// XPath语句，选取所有 bean元素
		String xpath = "//bean";
		List<Element> beanNodes = doc.selectNodes(xpath);
		// 遍历所有bean节点,并将信息封装到Bean对象中
		for (Element ele : beanNodes) {
			Bean bean = new Bean();
			bean.setName(ele.attributeValue("name"));
			bean.setClassName(ele.attributeValue("class"));
			String scope = ele.attributeValue("scope");
			// 如果指定了scope则设置，不然用默认的singleton
			if (scope != null && scope.trim().length() > 0) {
				bean.setScope(scope);
			}
			// 获取bean节点下所有的property节点
			List<Element> propNodes = ele.elements("property");
			if (propNodes != null) {
				// 遍历property节点，并封装到Property对象中,再添加到所属Bean对象中
				for (Element prop : propNodes) {
					Property p = new Property();
					p.setName(prop.attributeValue("name"));
					p.setValue(prop.attributeValue("value"));
					p.setRef(prop.attributeValue("ref"));
					// 将property添加到所属bean中
					bean.getProperties().add(p);
				}
			}
			result.put(bean.getName(), bean);
		}
		return result;
	}
}
