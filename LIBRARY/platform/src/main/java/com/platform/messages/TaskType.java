package com.platform.messages;

import java.util.stream.Stream;

/**
 * @author Muhil
 *
 */
public enum TaskType {

	TODO, APPROVAL;

	public static Stream<TaskType> stream() {
		return Stream.of(TaskType.values());
	}
	
	public static TaskType getType(String type) {
		return stream().filter(tsk -> tsk.name().equalsIgnoreCase(type)).findFirst()
				.orElseThrow(() -> new UnsupportedOperationException());
	}

}
