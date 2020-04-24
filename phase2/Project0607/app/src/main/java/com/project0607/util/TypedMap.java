package com.project0607.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * A map with type safety, where the type of the value of an entry depends on the type of the key.
 * See {@link TypedKey} for what the keys are and how type information is tracked for a key. The use
 * case for this data structure is to ensure that the type when inserting and retrieving a value are
 * not too specific or too general. Consider the following example, where we would like to store two
 * statistics: - An integer, representing some amount of enemies killed. - An value of <code>
 * enum Rank { Bad, Okay, Good },</code> representing player rank. Then, with a traditional HashMap
 * (or any Map implementors), it is impossible to have compile-time type safety. We either have
 * <code>Map&lt;String, Object&gt;</code> which would be too general, allowing for an integer to be
 * used as a Rank, or we have <code>Map&lt;String, Integer&gt;</code> or <code>
 * Map&lt;String, Rank&gt;</code>, which would be too specific, disallowing other types of values to
 * be put into the map. TypedMap purposely does not implement the Collection or Map interface, as
 * that would expose methods that could circumvent type safety and possibly break invariants.
 */
public class TypedMap {

  /** The map to delegate storage to. */
  private final Map<TypedKey<?>, Object> map;

  /** Creates a new TypedMap with the default backing map. */
  public TypedMap() {
    this.map = new HashMap<>();
  }

  /**
   * Creates a new TypedMap based on HashMap<String, Object>. Should only be used by InternalSave.
   *
   * @param map the HashMap<String, Object>
   */
  TypedMap(HashMap<String, Object> map) {
    HashMap<TypedKey<?>, Object> loadMap = new HashMap<>();
    for (String key : map.keySet()) {
      loadMap.put(new TypedKey(key), map.get(key));
    }
    this.map = loadMap;
  }

  /**
   * Gets a value from a map.
   *
   * @param key The key to use.
   * @param <T> The type of the key and value.
   * @return The value in the map, or null if the entry does not exist.
   */
  @SuppressWarnings("unchecked")
  public <T> T get(TypedKey<T> key) {
    return (T) map.get(key);
  }

  /**
   * Gets a value from a map, with a default value if the entry does not exist.
   *
   * @param key The key to use.
   * @param def The default value.
   * @param <T> The type of the key and value.
   * @return The value in the map, or the default if the entry does not exist.
   */
  @SuppressWarnings("unchecked")
  public <T> T getOrDefault(TypedKey<T> key, T def) {
    return (T) map.getOrDefault(key, def);
  }

  /**
   * Puts an entry into the map.
   *
   * @param key The key to use.
   * @param value The value of the entry.
   * @param <T> The type of the key and value.
   */
  public <T> void put(TypedKey<T> key, T value) {
    map.put(key, value);
  }

  /**
   * Performs an update operation. If the entry associated with the key exists, the function is ran
   * on the value of that entry and put back into the map.
   *
   * @param key The key to use.
   * @param run The mapping function.
   * @param <T> The type of the key and value.
   */
  public <T> void update(TypedKey<T> key, Function<T, T> run) {
    if (map.containsKey(key)) {
      map.put(key, run.apply(get(key)));
    }
  }

  /**
   * Performs an update-or-insert operation. If the entry associated with the key exists, the
   * function is ran on the value of that entry and put back into the map. Otherwise, the default
   * value is put into the map.
   *
   * @param key The key to use.
   * @param runIfPresent The mapping function.
   * @param def The default value.
   * @param <T> The type of the key and value.
   */
  public <T> void upsert(TypedKey<T> key, Function<T, T> runIfPresent, T def) {
    if (map.containsKey(key)) {
      map.put(key, runIfPresent.apply(get(key)));
    } else {
      map.put(key, def);
    }
  }

  /** @return a HashMap<String, Object> representation of this TypedMap. */
  HashMap<String, Object> getHashMap() {
    HashMap<String, Object> newMap = new HashMap<>();
    for (TypedKey key : map.keySet()) {
      newMap.put(key.toString(), map.get(key));
    }
    return newMap;
  }
}
