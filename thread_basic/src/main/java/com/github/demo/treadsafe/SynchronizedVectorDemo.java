package com.github.demo.treadsafe;

import com.github.common.ThreadSafe;

import java.util.Vector;

public class SynchronizedVectorDemo {

	@ThreadSafe
	public static Object getLast(Vector list) {
		synchronized(list) {
			int lastIndex = list.size() - 1;
			return list.get(lastIndex);
		}
	}

	@ThreadSafe
	public static void deleteLast(Vector list) {
		synchronized(list) {
			int lastIndex = list.size() - 1;
			list.remove(lastIndex);
		}
	}

	public static Object getLastNotThreadSafe(Vector list) {
		int lastIndex = list.size() - 1;
		return list.get(lastIndex);
	}

	public static void deleteLastNotThreadSafe(Vector list) {
		int lastIndex = list.size() - 1;
		list.remove(lastIndex);
	}
}
