package com.platform.entity;

import java.util.Set;

import com.platform.user.Permissions;

/**
 * @author Muhil
 * this interface can be used to get user permissions from app side.
 */
public interface BasePermissions extends BaseObject {
	
	Set<Permissions> getUserPermissions();

}
