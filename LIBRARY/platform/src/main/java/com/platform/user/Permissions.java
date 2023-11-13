package com.platform.user;

import java.util.stream.Stream;

/**
 * @author Muhil
 * List of valid permissions.
 * New Permissions added here are loaded automatically during server startup.
 */
public enum Permissions {
	
	SUPER_USER("SuperUser"), ADMIN("Admin"), CUSTOMER_SUPPORT("CustomerSupport"), MANAGE_USERS("ManageUsers"),
	EDIT_USERS("EditUsers"), MANAGE_ORDERS("ManageOrders"), EDIT_ORDERS("EditOrders"),
	MANAGE_PROMOTIONS("ManagePromotions"), EDIT_PROMOTIONS("EditPromotions"), MANAGE_PRODUCTS("ManageProducts"),
	EDIT_PRODUCTS("EditProducts"), POINT_OF_SALE("PointOfSale"), MANAGE_CATEGORIES("ManageCategories"),
	EDIT_CATEGORIES("EditCategories"), MANAGE_REPORTING("ManageReporting"), MANAGE_COUPONS("ManageCoupons"),
	EDIT_COUPONS("EditCoupons");

	private String value;

	Permissions(String value) {
		this.value = value;
	}
	
	public String getPermissionUniqueName() {
		return this.value;
	}
	
	public static Stream<Permissions> stream() {
		return Stream.of(Permissions.values());
	}
	
	public static String getPermissionsAsString(Permissions[] permissions) {
		StringBuffer str = new StringBuffer();
		str.append("[ ");
		Stream.of(permissions).forEach(permission -> {
			str.append(permission.getPermissionUniqueName());
		});
		str.append(" ]");
		return str.toString();
	}

	public static String getAllPermissionsAsString() {
		StringBuffer str = new StringBuffer();
		str.append("[ ");
		Stream.of(Permissions.values()).forEach(permission -> {
			str.append(permission.getPermissionUniqueName());
		});
		str.append(" ]");
		return str.toString();
	}

}
