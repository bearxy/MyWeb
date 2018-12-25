package com.misterfat.generator.tool.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jdom.JDOMException;

public class Dom4jUtil {

	/**
	 * 
	 * ������������ȡxml�ĵ�����
	 *
	 * @param file
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 * 
	 * @author ��ĭȻ
	 * @throws DocumentException
	 *
	 * @since 2016��5��24��
	 *
	 * @update:[�������YYYY-MM-DD][����������][�������]
	 */
	public static Document getDocument(File file) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(file);
		return document;
	}

	/**
	 * 
	 * ������������ȡ�ĵ���Ԫ��
	 *
	 * @param file
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 * 
	 * @author ��ĭȻ
	 * @throws DocumentException
	 *
	 * @since 2016��5��24��
	 *
	 * @update:[�������YYYY-MM-DD][����������][�������]
	 */
	public static Element getRootElement(File file) throws DocumentException {
		return getDocument(file).getRootElement();
	}

	/**
	 * 
	 * ������������ȡ�ĵ�Ԫ��
	 *
	 * @param file
	 * @param tagName
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 * 
	 * @author ��ĭȻ
	 * @throws DocumentException
	 *
	 * @since 2016��5��24��
	 *
	 * @update:[�������YYYY-MM-DD][����������][�������]
	 */
	@SuppressWarnings("unchecked")
	public static List<Element> getElementsByTagName(File file, String tagName) throws DocumentException {
		return getDocument(file).getRootElement().elements(tagName);
	}

	/**
	 * 
	 * ��������������תΪDOM�ڵ�
	 *
	 * @param bean
	 * @param parentElement
	 * @param elementName
	 * @param ignore
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * 
	 * @author ��ĭȻ
	 *
	 * @since 2016��5��24��
	 *
	 * @update:[�������YYYY-MM-DD][����������][�������]
	 */
	public static Element beanToElement(Object bean, Element parentElement, String elementName, String[] ignore)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Class<? extends Object> beanClass = bean.getClass();
		Element tableElement = parentElement.addElement(elementName);
		BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			boolean isIgnore = false;
			for (int j = 0; j < ignore.length; j++) {
				if (propertyName.equals(ignore[j])) {
					isIgnore = true;
				}
			}
			if (!propertyName.equals("class") && !isIgnore) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				generateDom(tableElement, propertyName, result);

			}
		}
		return tableElement;
	}

	/**
	 * 
	 * ��������������DOM�ṹ
	 *
	 * @param bean
	 * @param parentElement
	 * @param elementName
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * 
	 * @author ��ĭȻ
	 *
	 * @since 2016��5��24��
	 *
	 * @update:[�������YYYY-MM-DD][����������][�������]
	 */
	public static Element beanToElement(Object bean, Element parentElement, String elementName)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return beanToElement(bean, parentElement, elementName, new String[0]);
	}

	/**
	 * 
	 * ��������������DOM�ṹ
	 *
	 * @param bean
	 * @param parentElement
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * 
	 * @author ��ĭȻ
	 *
	 * @since 2016��5��24��
	 *
	 * @update:[�������YYYY-MM-DD][����������][�������]
	 */
	public static Element beanToElement(Object bean, Element parentElement)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String className = bean.getClass().getName().toLowerCase();
		String elementName = className.substring(className.lastIndexOf(".") + 1);
		return beanToElement(bean, parentElement, elementName);
	}

	/**
	 * 
	 * ��������������DOM�ṹ
	 *
	 * @param parentElement
	 * @param propertyName
	 * @param object
	 * 
	 * @author ��ĭȻ
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 *
	 * @since 2016��5��24��
	 *
	 * @update:[�������YYYY-MM-DD][����������][�������]
	 */
	@SuppressWarnings("unchecked")
	public static void generateDom(Element parentElement, String propertyName, Object object)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		if (object != null) {
			if (object instanceof String) {
				parentElement.addAttribute(propertyName, (String) object);
			} else if (object instanceof Integer) {
				parentElement.addAttribute(propertyName, String.valueOf(object));
			} else if (object instanceof String[]) {
				String[] array = (String[]) object;
				parentElement.addAttribute(propertyName, ArrayUtil.join(array, ","));
			} else if (object instanceof List) {
				List<Object> list = (List<Object>) object;
				if (!list.isEmpty()) {
					for (Object obj : list) {
						beanToElement(obj, parentElement);
					}
				}
			}
		}
	}

}
