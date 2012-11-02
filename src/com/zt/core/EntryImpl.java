package com.zt.core;

import java.util.Map.Entry;

public class EntryImpl<K,V> implements Entry<K, V> {

	private K key;
	
	private V value;
	
	
	public EntryImpl() {
		super();
	}

	public EntryImpl(K key, V value) {
		this.key = key;
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see core.Entry#getKey()
	 */
	public K getKey() {
		
		return key;
	}

	/* (non-Javadoc)
	 * @see core.Entry#getValue()
	 */
	public V getValue() {
		
		return value;
	}

	/* (non-Javadoc)
	 * @see core.Entry#setValue(V)
	 */
	public V setValue(V value) {
		this.value=value;
		return value;
	}
	
}
