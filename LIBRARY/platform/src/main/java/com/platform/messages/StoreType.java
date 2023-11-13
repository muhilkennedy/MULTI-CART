package com.platform.messages;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Muhil
 *
 */
public enum StoreType {

	NFS, GCP;

	public static enum constants {
		GCPCONFIG, GCPBUCKET
	}

	public static Stream<StoreType> stream() {
		return Stream.of(StoreType.values());
	}

	public static Optional<StoreType> findType(String name) {
		return StoreType.stream().filter(type -> type.name().equalsIgnoreCase(name)).findFirst();
	}
}
