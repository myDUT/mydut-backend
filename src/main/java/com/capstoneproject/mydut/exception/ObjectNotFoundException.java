package com.capstoneproject.mydut.exception;

/**
 * @author vndat00
 * @since 5/12/2024
 */
public class ObjectNotFoundException extends CommunityException {
    private final String key;
    private final Object value;
    private final String message;

    public ObjectNotFoundException(String key, Object value) {
        super(true);
        this.key = key;
        this.value = value;
        this.message = String.format("Object not found with value %s = %s", key, value);
    }

    public ObjectNotFoundException(Class<?> objectClass, String key, String value){
        super(true);
        this.key = objectClass.getSimpleName();
        this.value = String.format("%s = %s", key, value);
        this.message = String.format("%s not found with %s = %s", this.key, key, value);
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
