package com.user.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.user.entity.EmployeeRole;
import com.user.entity.Permission;
import com.user.entity.Role;
import com.user.entity.RolePermission;
import com.user.jpa.repository.EmployeeRoleRepository;
import com.user.jpa.repository.PermissionRepository;
import com.user.jpa.repository.RolePermissionRepository;
import com.user.jpa.repository.RolesRepository;
import com.user.service.RolePermissionService;

/**
 * @author muhil
 */
@Service
@Qualifier("RPDAOService")
public class RolePermissionDaoService implements RolePermissionService {
	
	@Autowired
	private RolesRepository roleRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Autowired
	private RolePermissionRepository rpRepository;
	
	@Autowired
	private EmployeeRoleRepository erRepository;

	@Override
	public List<Role> findAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public List<Permission> findAllPermission() {
		return permissionRepository.findAll();
	}

	@Override
	public List<Permission> findAllPermission(Long roleId) {
		return permissionRepository.findPermissionForRole(roleId);
	}
	
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}
	
	public RolePermission saveRolePermissionMap(RolePermission rp) {
		return rpRepository.save(rp);
	}
	
	public EmployeeRole saveEmployeeRoleMap(EmployeeRole er) {
		return erRepository.save(er);
	}

	@Override
	public Role findRoleById(Long rootId) {
		return roleRepository.findById(rootId).get();
	}
	
	public List<Role> getAllEmployeeRoles(Long rootId){
		return roleRepository.findAllEmployeeRoles(rootId);
	}
	
	@Transactional("transactionManager")
	public void deleteEmployeeRoles(Long employeeId, List<Long> rootIds) {
		erRepository.deleteEmployeeRoles(rootIds, employeeId);
	}

}
