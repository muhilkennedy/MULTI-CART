package com.platform.messages;

import java.util.stream.Stream;

/**
 * @author Muhil
 *
 */
public enum TaskStatus {

	NOT_STARTED, PENDING, COMPLETED, EXPIRED, EXPIRING, SIGNED, DECLINED, APPROVED, AWAITING_RESPONSE, REVOKED;

	public static Stream<TaskStatus> stream() {
		return Stream.of(TaskStatus.values());
	}
	
	public static TaskStatus getStatus(String type) {
		return stream().filter(tsk -> tsk.name().equalsIgnoreCase(type)).findFirst()
				.orElseThrow(() -> new UnsupportedOperationException());
	}

}
