/*
 * Copyright(c) 2023 Priyak Dey <https://priyakydey.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package com.priyakdey.parker.context;

import java.util.HashMap;
import java.util.Map;

/**
 * An application-wide context that maintains a registry of objects based on their class types.
 * <p>
 * This context serves as a simple dependency injection container, allowing objects to be stored and later retrieved
 * based on their class type. This class is implemented as a Singleton, ensuring there's a single instance
 * of the context throughout the application.
 * </p>
 *
 * @author Priyak Dey
 * @see #put(Object)
 * @see #get(Class)
 */
public final class ApplicationContext {

    private final Map<Class<?>, Object> typeObjectMap = new HashMap<>();

    /**
     * Private constructor to prevent direct instantiation.
     */
    private ApplicationContext() {
    }

    /**
     * Holds the single instance of the ApplicationContext. This approach utilizes the Initialization-on-demand holder idiom,
     * which is a lazy-loaded, thread-safe Singleton.
     */
    private static final class InstanceHolder {
        private static final ApplicationContext instance = new ApplicationContext();
    }

    /**
     * Provides access to the singleton instance of the ApplicationContext.
     *
     * @return The single instance of ApplicationContext.
     */
    public static ApplicationContext getInstance() {
        return InstanceHolder.instance;
    }

    /**
     * Registers an object in the context based on its class type.
     * <p>
     * If the object is not null, it will be stored in the context. Any subsequent call with an object of the same type
     * will overwrite the previous object.
     * </p>
     *
     * @param object The object to be stored.
     * @param <T>    The type of the object.
     */
    public <T> void put(T object) {
        if (object != null) {
            Class<?> keyType = getKeyType(object);
            typeObjectMap.put(keyType, object);
        }
    }

    /**
     * Retrieves an object from the context based on its class type.
     * <p>
     * If an object of the specified type has been previously registered, it will be returned. Otherwise, this method
     * returns null.
     * </p>
     *
     * @param type The class type of the object to retrieve.
     * @param <T>  The expected return type.
     * @return The object of the given type, or null if no such object has been registered.
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> type) {
        return (T) typeObjectMap.get(type);
    }

    private <T> Class<?> getKeyType(T object) {
        Class<?>[] interfaces = object.getClass().getInterfaces();

        // If the object has implemented interfaces, use the first one as key.
        if (interfaces.length > 0) {
            return interfaces[0];
        }

        // If the object hasn't implemented interfaces, but has a superclass other than Object, use that.
        Class<?> superclass = object.getClass().getSuperclass();
        if (superclass != null && !superclass.equals(Object.class)) {
            return superclass;
        }

        // Otherwise, use the object's own class.
        return object.getClass();
    }

}