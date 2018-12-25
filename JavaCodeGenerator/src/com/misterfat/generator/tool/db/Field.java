package com.misterfat.generator.tool.db;

import java.io.Serializable;

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
public class Field implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5296890070697589853L;

	// �Ƿ�Ϊ����
	private String primaryKey;

	// ��������
	private String name;

	// ����ע��
	private String comment;

	// ��������
	private String fieldType;

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

}