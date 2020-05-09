package io.github.minecraftcursedlegacy.impl.event;

/**
 * For bypassing lambda restrictions.
 */
public class Wrapper<T> {
	private T value;

	public T getValue() {
		return this.value;
	}

	public void setValue(T value) {
		this.value = value;
	}
}
