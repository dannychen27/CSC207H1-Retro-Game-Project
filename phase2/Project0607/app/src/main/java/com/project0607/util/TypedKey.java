package com.project0607.util;

/**
 * A key for use with TypedMap. The key is parameterized with a type that is not used anywhere (a
 * phantom type). This allows type information to be associated with the key for use in the
 * TypedMap. Two TypedKeys are considered equivalent if their names are the same, not their type.
 * So, it is up to the user of TypedKey to ensure that if two keys with the same name are used in
 * the same place, they should have the same type associated with it.
 *
 * @param <T> The type associated with the key. Used to indicate the type of values
 *           that should be used with this key in a TypedMap.
 */
public class TypedKey<T> {

  /** The name of the key. */
  private final String name;

  /**
   * Create a new TypedKey.
   *
   * @param name The name associated with this key.
   */
  public TypedKey(String name) {
    this.name = name;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof TypedKey && ((TypedKey) obj).name.equals(name);
  }

  @Override
  public String toString() {
    return name;
  }
}
