package com.supplier.util;

import java.sql.SQLException;
import java.util.Arrays;

import com.base.server.BaseSession;
import com.base.util.DatabaseUtil;
import com.base.util.Log;
import com.platform.annotations.ClassMetaProperty;
import com.supplier.entity.Supplier;

/**
 * @author Muhil
 *
 */
public class SupplierUtil {

	public static synchronized String generateSupplierUniqueName() throws SQLException {
		Long count = (Long) DatabaseUtil.executeDQL("select count(*) from Supplier where tenantid = ? ",
				Arrays.asList(BaseSession.getTenantId()));
		ClassMetaProperty classMeta = Supplier.class.getAnnotation(ClassMetaProperty.class);
		String uniqueName = String.format("%s%s%s", classMeta.code(), BaseSession.getTenantId(), ++count);
		Log.supplier.debug("Generated Unique Name for Supplier {}", uniqueName);
		return uniqueName;
	}

}
