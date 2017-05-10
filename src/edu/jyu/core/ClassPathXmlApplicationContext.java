package edu.jyu.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

import edu.jyu.config.Bean;
import edu.jyu.config.Property;
import edu.jyu.config.parsing.ConfigurationManager;

public class ClassPathXmlApplicationContext implements BeanFactory {
	// 存放配置文件信息
	private Map<String, Bean> config;
	// 存放bean对象的容器
	private Map<String, Object> context = new HashMap<>();

	/**
	 * 将配置文件中设置为单例的bean对象创建好放入容器中
	 * 
	 * @param path
	 */
	public ClassPathXmlApplicationContext(String path) {
		// 读取配置文件中bean的信息
		config = ConfigurationManager.getBeanConfig(path);
		// 遍历初始化bean
		if (config != null) {
			for (Entry<String, Bean> e : config.entrySet()) {
				// 获取bean信息
				String beanName = e.getKey();
				Bean bean = e.getValue();
				// 如果设置成单例的才创建好bean对象放进容器中
				if (bean.getScope().equals(Bean.SINGLETON)) {
					Object beanObj = createBeanByConfig(bean);
					context.put(beanName, beanObj);
				}
			}

		}
	}

	/**
	 * 根据bean的配置信息创建bean对象
	 * 
	 * @param bean
	 * @return
	 */
	private Object createBeanByConfig(Bean bean) {
		// 根据bean信息创建对象
		Class clazz = null;
		Object beanObj = null;
		try {
			clazz = Class.forName(bean.getClassName());
			// 创建bean对象
			beanObj = clazz.newInstance();
			// 获取bean对象中的property配置
			List<Property> properties = bean.getProperties();
			// 遍历bean对象中的property配置,并将对应的value或者ref注入到bean对象中
			for (Property prop : properties) {
				Map<String, Object> params = new HashMap<>();
				if (prop.getValue() != null) {
					params.put(prop.getName(), prop.getValue());
					// 将value值注入到bean对象中
					BeanUtils.populate(beanObj, params);
				} else if (prop.getRef() != null) {
					Object ref = context.get(prop.getRef());
					// 如果依赖对象还未被加载则递归创建依赖的对象
					if (ref == null) {
						//下面这句的错误在于传入了当前bean配置信息，这会导致不断递归最终发生StackOverflowError
						//解决办法是传入依赖对象的bean配置信息
						//ref = createBeanByConfig(bean);
						ref = createBeanByConfig(config.get(prop.getRef()));
					}
					params.put(prop.getName(), ref);
					// 将ref对象注入bean对象中
					BeanUtils.populate(beanObj, params);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("创建" + bean.getClassName() + "对象失败");
		}
		return beanObj;
	}

	@Override
	public Object getBean(String name) {
		Bean bean = config.get(name);
		Object beanObj = null;
		if (bean.getScope().equals(Bean.SINGLETON)) {
			// 如果将创建bean设置成单例则在容器中找
			beanObj = context.get(name);
		} else if (bean.getScope().equals(Bean.PROTOTYPE)) {
			// 如果是prototype则新创建一个对象
			beanObj = createBeanByConfig(bean);
		}
		return beanObj;
	}

}
