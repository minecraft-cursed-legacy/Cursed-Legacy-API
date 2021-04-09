package io.github.minecraftcursedlegacy.api.registry;

/**
 * Identifier for game content.
 */
public final class Id {
	public Id(String namespace, String id) {
		this.namespace = namespace;
		this.name = id;
	}

	public Id(String stringForm) {
		String[] strs = stringForm.split(":");

		if (strs.length == 0) {
			throw new RuntimeException("Illegal String Identifier! " + stringForm);
		} else if (strs.length == 1) {
			this.namespace = "minecraft";
			this.name = strs[0].trim();
		} else {
			this.namespace = strs[0].trim();
			this.name = strs[1].trim();
		}
	}

	private final String namespace;
	private final String name;

	public String getNamespace() {
		return this.namespace;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.namespace + ":" + this.name;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof String) {
			return other.equals(this.toString());
		} else if (other instanceof Id) {
			Id otherId = (Id) other;
			return otherId.name.equals(this.name) && otherId.namespace.equals(this.namespace);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int result = 5;
		result = 29 * result + this.namespace.hashCode();
		result = 29 * result + this.name.hashCode();
		return result;
	}
}
