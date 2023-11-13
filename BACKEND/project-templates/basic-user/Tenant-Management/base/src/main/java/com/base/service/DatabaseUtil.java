package com.base.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.base.configuration.DataSourceProperties;
import com.base.util.Log;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Muhil
 *
 */
@Component
public class DatabaseUtil {

	private static DataSourceProperties dbProperties;

	@Autowired
	public void setDbProperties(DataSourceProperties props) {
		DatabaseUtil.dbProperties = props;
	}

	public static Connection getConnectionInstance() throws SQLException {
		return DriverManager.getConnection(dbProperties.getUrl(), dbProperties.getUsername(),
				dbProperties.getPassword());
	}

	public static boolean executeDML(String sql) throws SQLException {
		try (Connection connection = getConnectionInstance()) {
			Statement statement = connection.createStatement();
			boolean isDone = statement.execute(sql);
			Log.base.debug("executeDML : Excecuted query {} , with execution status {}", sql, isDone);
			return isDone;
		}
	}

	public static List<?> executeDQL(String sql, Class<?> resultClass) throws SQLException {
		List result = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		try (Connection connection = getConnectionInstance()) {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			Log.base.debug("executeDQL : Excecuted query {}", sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				for (int _iterator = 0; _iterator < rsmd.getColumnCount(); _iterator++) {
					Map<String, Object> resultMap = new HashMap<String, Object>();
					String columnName = rsmd.getColumnName(_iterator + 1);
					Object columnValue = rs.getObject(_iterator + 1);
					Log.base.debug("executeDQL : ColumnName - {} , ColumnValue - {}", columnValue, columnValue);
					resultMap.put(columnName, columnValue);
					result.add(mapper.convertValue(resultMap, resultClass));
				}
			}
		}
		Log.base.debug("executeDQL : DQL result {}", result.toArray());
		return result;
	}
	
	public static Object executeDQL(String sql) throws SQLException {
		try (Connection connection = getConnectionInstance()) {
			Statement statement = connection.createStatement();
			Log.base.debug("executeDQL : Excecuting query {}", sql);
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				return rs.getObject(1);
			}
		}
		return null;
	}
	
	public static Object executeDQL(String sql, List<?> clauseValues) throws SQLException {
		try (Connection connection = getConnectionInstance()) {
			PreparedStatement statement = connection.prepareStatement(sql);
			for (int index = 0; index < clauseValues.size(); index++) {
				statement.setObject(index+1, clauseValues.get(index));
			}
			Log.base.debug("executeDQL : Excecuted query {}", statement);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				return rs.getObject(1);
			}
		}
		return null;
	}

}
