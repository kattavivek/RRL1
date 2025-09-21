package com.springbootbackend.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class User {
	
	
    @Test
	void testArrays() {
		int []expected= {2,4,6,8};
		int []actual= {4,8,6,2};
		
		Arrays.sort(actual);
		
		assertArrayEquals(expected, actual);
		assertEquals(expected, actual);
	}
}
