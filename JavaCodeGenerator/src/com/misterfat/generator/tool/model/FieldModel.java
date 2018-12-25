package com.misterfat.generator.tool.model;

import java.io.Serializable;

import com.misterfat.generator.tool.gen.TypeTool;
import com.misterfat.generator.tool.util.CamelCaseUtil;

/**
 * 
 * ���ֶζ���
 *
 * @author ��ĭȻ
 *
 * @version
 *
 * @since 2015��11��19��
 */
public class FieldModel implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8345180647731207742L;

	// �Ƿ�Ϊ����
	private String primaryKey;

	// �ֶ�����
	private String name;

	// ������
	private String propertyName;

	// ����ĸ��д������
	private String firstUpperPropertyName;

	// �ֶ�ע��
	private String comment;

	// �ֶ�����
	private String fieldType;

	// SQL�ֶ�����
	private String jdbcType;

	// JAVA��������
	private String javaType;

	// �Ƿ����Ϊ��
	private String nullable;

	// �ֶγ���
	private String fieldLength;

	// ��ֵ��Χ
	private String scale;

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.propertyName = CamelCaseUtil.toCamelCase(name);
		this.firstUpperPropertyName = CamelCaseUtil.toCapitalizeCamelCase(name);
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
		this.jdbcType = TypeTool.fieldToJdbc(fieldType);
		this.javaType = TypeTool.fieldToJava(fieldType);
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getFieldLength() {
		return fieldLength;
	}

	public void setFieldLength(String fieldLength) {
		this.fieldLength = fieldLength;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getFirstUpperPropertyName() {
		return firstUpperPropertyName;
	}

	public void setFirstUpperPropertyName(String firstUpperPropertyName) {
		this.firstUpperPropertyName = firstUpperPropertyName;
	}

}