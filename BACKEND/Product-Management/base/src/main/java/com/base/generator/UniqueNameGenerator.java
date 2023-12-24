package com.base.generator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EnumSet;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.tuple.ValueGenerator;

import com.base.server.BaseSession;
import com.base.util.AnnotationUtil;
import com.base.util.Log;
import com.platform.util.PlatformUtil;

/**
 * @author Muhil kennedy
 * Generate custom unique Id by sequence,
 * NOTE : can be used with id column only.
 */
public class UniqueNameGenerator implements IdentifierGenerator {

	private static final long serialVersionUID = 1L;

	/*@Override
	public EnumSet<EventType> getEventTypes() {
		return null;
	}

	@Override
	public Object generate(SharedSessionContractImplementor session, Object owner, Object currentValue,
			EventType eventType) {
		try (Connection connection = session.getJdbcConnectionAccess().obtainConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(String.format("select count(*) from %s where tenantid = %s",
					AnnotationUtil.getClassTableName(currentValue), BaseSession.getTenantId()));
			if (rs.next()) {
				Integer nextVal = rs.getInt(1) + 10;
				String classCode = AnnotationUtil.getClassMetaCode(currentValue);
				return classCode + PlatformUtil.COLON_SEPERATOR + nextVal;
			} else {
				throw new HibernateException("Something went wrong in RealmIdGenerator");
			}
		} catch (SQLException e) {
			Log.base.error("UniqueNameGenerator : Exception {}", e);
			throw new HibernateException(e.getMessage());
		}
	}*/



	@Override
	public synchronized Object generate(SharedSessionContractImplementor session, Object object)
			throws HibernateException {
		try (Connection connection = session.getJdbcConnectionAccess().obtainConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(String.format("select count(*) from %s where tenantid = %s",
					AnnotationUtil.getClassTableName(object), BaseSession.getTenantId()));
			if (rs.next()) {
				Integer nextVal = rs.getInt(1) + 10;
				String classCode = AnnotationUtil.getClassMetaCode(object);
				return classCode + PlatformUtil.COLON_SEPERATOR + nextVal;
			} else {
				throw new HibernateException("Something went wrong in RealmIdGenerator");
			}
		} catch (SQLException e) {
			Log.base.error("UniqueNameGenerator : Exception {}", e);
			throw new HibernateException(e.getMessage());
		}
	}
	
}
