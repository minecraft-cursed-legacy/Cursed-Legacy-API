package io.github.minecraftcursedlegacy.impl.event;

/**
 * For bypassing lambda restrictions.
 * TODO move to AtomicReference, maybe. Might be slower though?
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
