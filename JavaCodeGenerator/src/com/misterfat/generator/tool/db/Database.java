package com.misterfat.generator.tool.db;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.misterfat.generator.tool.util.DBUtil;
import com.misterfat.generator.tool.util.LogUtil;

public abstract class Database implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7072891906090118243L;

	protected String schema;
	protected String owner;
	protected String url;
	protected String username;
	protected String password;

	protected List<Table> tableList;

	/**
	 * 
	 * 功能描述：获取驱动
	 *
	 * @return
	 * 
	 * @author 耿沫然
	 *
	 * @since 2016年7月19日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	protected abstract String getDriver();

	/**
	 * 
	 * 功能描述：查询表的相关信息
	 *
	 * @param tableNames
	 * @return {table_schema,table_name,table_type,table_comment}
	 * @throws SQLException
	 * 
	 * @author 耿沫然
	 *
	 * @since 2015年11月19日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public abstract List<Table> getTalbes(String... tableNames) throws SQLException;

	/**
	 * 
	 * 功能描述：查询库中所有表
	 *
	 * @return
	 * @throws SQLException
	 * 
	 * @author 耿沫然
	 *
	 * @since 2016年6月24日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public abstract List<Table> getAllTalbes() throws SQLException;

	/**
	 * 
	 * 功能描述：查询所有列
	 *
	 * @param table
	 * @return {column_name,nullable,data_type,data_length,scale,column_comment}
	 * @throws SQLException
	 * 
	 * @author 耿沫然
	 *
	 * @since 2015年11月19日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public abstract List<Map<String, Object>> queryAllColumns(String tableName) throws SQLException;

	/**
	 * 
	 * 功能描述：查询主键
	 *
	 * @param tableName
	 * @return
	 * @throws SQLException
	 * 
	 * @author 耿沫然
	 *
	 * @since 2016年7月19日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public abstract List<Map<String, Object>> queryPrimarykeys(String tableName) throws SQLException;

	/**
	 * 
	 * 功能描述：从URL中获取Schema
	 *
	 * @param url
	 * @return
	 * 
	 * @author 耿沫然
	 *
	 * @since 2016年6月6日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public String getSchemaFromUrl(String url) {
		String tempUrl = formatUrl(url);
		String schema = tempUrl.substring(tempUrl.lastIndexOf("/") + 1,
				tempUrl.indexOf("?") == -1 ? tempUrl.length() : tempUrl.indexOf("?"));
		return schema;
	}
	
	public String getSqlServerSchemaFromUrl(String url) {
		return url.split("=")[1];		
	}

	/**
	 * 
	 * 功能描述：格式化URL
	 *
	 * @param url
	 * @return
	 * 
	 * @author 耿沫然
	 *
	 * @since 2016年6月6日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public String formatUrl(String url) {
		String tempUrl = url.replace("\\\\", "/").replace("\\", "/");
		return tempUrl;
	}

	/**
	 * 
	 * 功能描述：执行查询
	 *
	 * @param sql
	 * @return
	 * @throws SQLException
	 * 
	 * @author 耿沫然
	 *
	 * @since 2016年7月19日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public List<Map<String, Object>> executeQuery(String sql) throws SQLException {
		LogUtil.log(sql);
		DBUtil.initDataSource(getDriver(), getUrl(), getUsername(), getPassword());
		return DBUtil.executeQuery(sql);
	}

	/**
	 * 
	 * 功能描述：执行更新 (多个SQL在同一事务中)
	 *
	 * @param sql
	 * @throws SQLException
	 * 
	 * @author 耿沫然
	 *
	 * @since 2016年7月19日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public void executeUpdate(String... sql) throws SQLException {
		DBUtil.initDataSource(getDriver(), getUrl(), getUsername(), getPassword());
		DBUtil.executeUpdate(sql);
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Table> getTableList() {
		return tableList;
	}

	public void setTableList(List<Table> tableList) {
		this.tableList = tableList;
	}

	public Database(String schema, String owner, String url, String username, String password) {
		super();
		this.schema = schema;
		this.owner = owner;
		this.url = formatUrl(url);
		this.username = username;
		this.password = password;
	}

	public Database(String schema, String url, String username, String password) {
		super();
		this.schema = schema;
		this.url = formatUrl(url);
		this.username = username;
		this.password = password;
	}

	public Database(String url, String username, String password) {
		super();
		//this.schema = getSchemaFromUrl(url);
		this.schema = getSqlServerSchemaFromUrl(url);
		this.url = formatUrl(url);
		this.username = username;
		this.password = password;
	}

	public Database() {
		super();
	}

}