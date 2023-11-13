package com.platform.user;

import java.util.Arrays;
import java.util.List;

/**
 * @author Muhil
 * List of default user roles. Each Role will have multiple permissions
 *         mapped to it.
 */
public enum Roles {

	CUSTOMER_SUPPORT_ADMIN("CustomerSupportAdmin", new Permissions[] { Permissions.SUPER_USER }), // Customer support admin role is only for tenant management admin level activities
	Admin("Admin",
			new Permissions[] { Permissions.ADMIN, Permissions.CUSTOMER_SUPPORT, Permissions.EDIT_CATEGORIES,
					Permissions.EDIT_ORDERS, Permissions.EDIT_PRODUCTS, Permissions.EDIT_PROMOTIONS,
					Permissions.EDIT_USERS, Permissions.MANAGE_CATEGORIES, Permissions.MANAGE_ORDERS,
					Permissions.MANAGE_PRODUCTS, Permissions.MANAGE_PROMOTIONS, Permissions.MANAGE_REPORTING,
					Permissions.MANAGE_USERS, Permissions.POINT_OF_SALE }),
	SALES_MANAGER("SalesManager",
			new Permissions[] { Permissions.EDIT_CATEGORIES, Permissions.EDIT_ORDERS, Permissions.EDIT_PRODUCTS,
					Permissions.EDIT_PROMOTIONS, Permissions.MANAGE_CATEGORIES, Permissions.MANAGE_ORDERS,
					Permissions.MANAGE_PRODUCTS, Permissions.MANAGE_PROMOTIONS, Permissions.MANAGE_REPORTING }),
	USER_MANAGER("UserManager", new Permissions[] { Permissions.EDIT_USERS, Permissions.MANAGE_USERS }),
	SALES_STAFF("SalesStaff",
			new Permissions[] { Permissions.POINT_OF_SALE, Permissions.EDIT_ORDERS, Permissions.EDIT_PRODUCTS,
					Permissions.MANAGE_ORDERS, Permissions.MANAGE_PRODUCTS }),
	MARKETING_STAFF("MarketingStaff",
			new Permissions[] { Permissions.EDIT_PROMOTIONS, Permissions.MANAGE_PROMOTIONS, Permissions.EDIT_COUPONS,
					Permissions.MANAGE_COUPONS }),
	SUPPORT_STAFF("SupportStaff", new Permissions[] { Permissions.CUSTOMER_SUPPORT, Permissions.MANAGE_USERS,
			Permissions.MANAGE_ORDERS, Permissions.EDIT_COUPONS, Permissions.MANAGE_COUPONS });

	private String roleName;
	private Permissions[] permissions;
	
	Roles(String value, Permissions[] permissions) {
		this.roleName = value;
		this.permissions = permissions;
	}
	
	Roles(String value) {
		this.roleName = value;
	}

	public String getRoleUniqueName() {
		return this.roleName;
	}
	
	public Permissions[] getRolePermissions() {
		return this.permissions;
	}
	
	public static List<Roles> getDefaultRoles() {
		return Arrays.asList(Roles.Admin, Roles.SALES_MANAGER, Roles.USER_MANAGER, Roles.SALES_STAFF,
				Roles.MARKETING_STAFF, Roles.SUPPORT_STAFF);
	}

}
