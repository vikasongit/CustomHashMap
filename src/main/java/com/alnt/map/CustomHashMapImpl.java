package com.alnt.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author vikas.singla
 *
 * @param <K>
 * @param <V>
 */
public class CustomHashMapImpl<K, V> implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	private ArrayList<Integer> listOfHashCodes = null;

	private ArrayList<ArrayList<KeyValue<K, V>>> listOfValues = null;

	public ArrayList<ArrayList<KeyValue<K, V>>> getListOfValue() {
		return listOfValues;
	}

	public void setListOfValues(
			ArrayList<ArrayList<KeyValue<K, V>>> listOfValues) {
		this.listOfValues = listOfValues;
	}

	public CustomHashMapImpl() {
		listOfHashCodes = new ArrayList<Integer>();
		listOfValues = new ArrayList<ArrayList<KeyValue<K, V>>>();
	}

	/**
	 * @param key
	 * @param value
	 */
	public void put(K key, V value) {
		int hashcode = getHashCode(key);
		put(hashcode, key, value);
	}

	/**
	 * @param hashcode
	 * @param key
	 * @param value
	 */
	private void put(int hashcode, K key, V value) {
		ArrayList<KeyValue<K, V>> listOfKeyValues = null;

		if (listOfHashCodes.size() > 0
				&& listOfHashCodes.indexOf(hashcode) >= 0) {
			int i = listOfHashCodes.indexOf(hashcode);
			listOfKeyValues = listOfValues.get(i);
		}

		if (listOfKeyValues == null) {
			listOfKeyValues = new ArrayList<KeyValue<K, V>>();
			listOfKeyValues.add(new KeyValue<K, V>(key, value));
			listOfHashCodes.add(hashcode);
			listOfValues
					.add(listOfHashCodes.indexOf(hashcode), listOfKeyValues);
		} else {
			boolean done = false;
			for (int counter = 0; counter < listOfKeyValues.size(); counter++) {
				KeyValue<K, V> keyValue = listOfKeyValues.get(counter);
				if (key.equals(keyValue.getKey())) {
					keyValue.setValue(value);
					done = true;
					break;
				}
			}
			if (!done)
				listOfKeyValues.add(new KeyValue<K, V>(key, value));
		}

	}

	private ArrayList<Integer> getListOfHashCodes() {
		return listOfHashCodes;
	}

	private void setListOfHashCodes(ArrayList<Integer> listOfHashCodes) {
		this.listOfHashCodes = listOfHashCodes;
	}

	/**
	 * @param key
	 * @return
	 */
	public V get(K key) {
		int hashcode = getHashCode(key);
		ArrayList<KeyValue<K, V>> listOfKeyValues = null;

		if (listOfHashCodes.indexOf(hashcode) >= 0) {
			int i = listOfHashCodes.indexOf(hashcode);
			listOfKeyValues = listOfValues.get(i);
			return get(listOfKeyValues, key);

		}
		return null;
	}

	/**
	 * @param key
	 */
	public void remove(K key) {
		KeyValue<K, V> keyValue = null;
		ArrayList<KeyValue<K, V>> listOfKeyValues = null;

		int hashcode = getHashCode(key);
		if (listOfHashCodes.indexOf(hashcode) >= 0) {
			int i = listOfHashCodes.indexOf(hashcode);
			listOfKeyValues = listOfValues.get(i);
		}

		if (listOfKeyValues == null)
			return;

		for (int counter = 0; counter < listOfKeyValues.size(); counter++) {
			keyValue = listOfKeyValues.get(counter);
			if (key != null && keyValue.getKey() != null
					&& keyValue.getKey().equals(key)) {
				listOfKeyValues.remove(counter);
			}
			if (key == null && keyValue.getKey() == null) {
				listOfKeyValues.remove(counter);
			}

		}
	}

	/**
	 * @param list
	 * @param key
	 * @return
	 */
	private V get(ArrayList<KeyValue<K, V>> listOfKeyValues, K key) {
		Iterator<KeyValue<K, V>> it = listOfKeyValues.iterator();
		while (it.hasNext()) {
			KeyValue<K, V> kv = it.next();
			if (key != null && key.equals(kv.getKey()))
				return kv.getValue();
			if (key == null && kv.getKey() == null)
				return kv.getValue();
		}
		return null;
	}

	/**
	 * @param key
	 * @return
	 */
	private int getHashCode(K key) {
		if (key == null)
			return 0;

		int hash = key.hashCode();
		return hash;
	}

}

/**
 * @author vikas.singla
 *
 * @param <K>
 * @param <V>
 */
class KeyValue<K, V> {
	K key;
	V value;

	public KeyValue(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return getKey() + "=" + getValue();
	}

}