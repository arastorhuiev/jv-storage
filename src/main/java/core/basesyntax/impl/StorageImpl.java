package core.basesyntax.impl;

import core.basesyntax.Storage;

public class StorageImpl<K, V> implements Storage<K, V> {
    private static final int MAX_STORAGE_ITEMS = 10;

    private final K[] keys;
    private final V[] values;
    private int size;

    @SuppressWarnings("unchecked")
    public StorageImpl() {
        this.keys = (K[]) new Object[MAX_STORAGE_ITEMS];
        this.values = (V[]) new Object[MAX_STORAGE_ITEMS];
    }

    @Override
    public void put(K key, V value) {
        for (int i = 0; i < size; i++) {
            boolean isKeyValid = this.isKeyValid(key, i);
            if (isKeyValid) {
                values[i] = value;
                return;
            }
        }

        this.addValue(key, value);
    }

    @Override
    public V get(K key) {
        for (int i = 0; i < this.size; i++) {
            boolean isKeyValid = this.isKeyValid(key, i);
            if (isKeyValid) {
                return (V) values[i];
            }
        }

        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    private boolean isKeyValid(K key, int index) {
        boolean isKeyNull = this.isKeyNull(key, index);
        boolean isKeyAllowable = this.isAllowableKey(key, index);
        return isKeyNull || isKeyAllowable;
    }

    private boolean isKeyNull(K key, int index) {
        return this.keys[index] == null && key == null;
    }

    private boolean isAllowableKey(K key, int index) {
        return this.keys[index] != null && this.keys[index].equals(key);
    }

    private void addValue(K key, V value) {
        boolean isStorageFull = this.size >= MAX_STORAGE_ITEMS;
        if (isStorageFull) {
            throw new IllegalStateException("Storage is full. Maximum allowed size: "
                    + MAX_STORAGE_ITEMS
            );
        }

        keys[size] = key;
        values[size] = value;
        size++;
    }
}
