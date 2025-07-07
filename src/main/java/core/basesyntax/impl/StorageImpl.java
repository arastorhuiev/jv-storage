package core.basesyntax.impl;

import core.basesyntax.Storage;

public class StorageImpl<K, V> implements Storage<K, V> {
    private static final int MAX_STORAGE_ITEMS = 10;

    private final Object[] keys;
    private final Object[] values;
    private int size;

    public StorageImpl() {
        this.keys = new Object[MAX_STORAGE_ITEMS];
        this.values = new Object[MAX_STORAGE_ITEMS];
        this.size = 0;
    }

    @Override
    public void put(K key, V value) {
        for (int i = 0; i < size; i++) {
            boolean isKeyNull = this.isKeyNull(key, i);
            boolean isKeyAllowable = this.isAllowableKey(key, i);
            if (isKeyNull || isKeyAllowable) {
                values[i] = value;
                return;
            }
        }

        this.checkStorageHealth(key, value);
    }

    @Override
    public V get(K key) {
        for (int i = 0; i < this.size; i++) {
            boolean isKeyNull = this.isKeyNull(key, i);
            boolean isKeyAllowable = this.isAllowableKey(key, i);
            if (isKeyNull || isKeyAllowable) {
                return (V) values[i];
            }
        }
        
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    private boolean isKeyNull(K key, int index) {
        return this.keys[index] == null && key == null;
    }

    private boolean isAllowableKey(K key, int index) {
        return this.keys[index] != null && this.keys[index].equals(key);
    }

    private void checkStorageHealth(K key, V value) {
        if (this.size < MAX_STORAGE_ITEMS) {
            keys[size] = key;
            values[size] = value;
            size++;
        } else {
            throw new IllegalStateException("Storage is full. Maximum allowed length: " + MAX_STORAGE_ITEMS);
        }
    }
}
