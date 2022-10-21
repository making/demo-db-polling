package com.example;

import java.time.OffsetDateTime;

public record Usage(Integer id, String firstName, String lastName, int minutes,
					int dataUsage, OffsetDateTime createdAt) {
}
