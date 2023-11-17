package com.platform.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.platform.annotations.PIIData;
import com.platform.session.PlatformBaseSession;
import com.platform.user.Permissions;

/**
 * @author Muhil
 * mask pii data on api response based on user permissions
 */
public class PIIDataSerializer extends StdSerializer<Object> implements ContextualSerializer {

	private static final long serialVersionUID = 1L;
	private Permissions[] allowedRolePermissions;
	private int visibleCharacters = 0;

	public PIIDataSerializer(Permissions[] allowedRoles) {
		this();
		this.allowedRolePermissions = allowedRoles;
	}
	
	public PIIDataSerializer(Permissions[] allowedRoles, int visibleCharacters) {
		this();
		this.allowedRolePermissions = allowedRoles;
		this.visibleCharacters = visibleCharacters;
	}

	public PIIDataSerializer() {
		super(Object.class);
	}

	@Override
	public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
			throws JsonMappingException {
		Optional<PIIData> annotation = Optional.ofNullable(property).map(prop -> prop.getAnnotation(PIIData.class));
		return new PIIDataSerializer(
				annotation.map(PIIData::allowedRolePermissions).orElse(new Permissions[] { Permissions.SUPER_USER }),
				annotation.get().visibleCharacters());
	}

	@Override
	public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		String piiData = value.toString();
		if (!getUserPermissions().stream()
				.anyMatch(permission -> Arrays.asList(allowedRolePermissions).contains(permission))) {
			piiData = piiData.replaceAll("\\w(?=\\w{" + visibleCharacters + "})", "x"); // w{0} - indicates last charaters to be visible.
		}
		gen.writeString(piiData);
	}

	public Set<Permissions> getUserPermissions() {
		return PlatformBaseSession.getUser().getUserPermissions();
	}
}
