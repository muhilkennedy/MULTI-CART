package com.platform.messages;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Muhil
 *
 */
public enum StoreType {

	NFS, GCP;

	public static enum GCP_CONSTANTS {
		
		GCPBUCKET, GCPCONFIG;
		
		public static Stream<GCP_CONSTANTS> stream() {
			return Stream.of(GCP_CONSTANTS.values());
		}
	}

	public static Stream<StoreType> stream() {
		return Stream.of(StoreType.values());
	}

	public static Optional<StoreType> findType(String name) {
		return StoreType.stream().filter(type -> type.name().equalsIgnoreCase(name)).findFirst();
	}
	
	public List<String> getStoreTypeConstants(StoreType type) {
		if (type.equals(StoreType.GCP)) {
			return Stream.of(GCP_CONSTANTS.values()).map(constant -> constant.name()).toList();
		}
		return null;
	}
}
