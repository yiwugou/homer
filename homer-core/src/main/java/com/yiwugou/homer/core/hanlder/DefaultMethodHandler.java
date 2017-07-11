package com.yiwugou.homer.core.hanlder;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DefaultMethodHandler extends AbstractMethodHandler {
    // Uses Java 7 MethodHandle based reflection. As default methods will only
    // exist when
    // run on a Java 8 JVM this will not affect use on legacy JVMs.
    // When Feign upgrades to Java 7, remove the @IgnoreJRERequirement
    // annotation.
    private final MethodHandle unboundHandle;

    // handle is effectively final after bindTo has been called.
    private MethodHandle handle;

    public DefaultMethodHandler(Method defaultMethod) {
        try {
            Class<?> declaringClass = defaultMethod.getDeclaringClass();
            Field field = Lookup.class.getDeclaredField("IMPL_LOOKUP");
            field.setAccessible(true);
            Lookup lookup = (Lookup) field.get(null);

            this.unboundHandle = lookup.unreflectSpecial(defaultMethod, declaringClass);
        } catch (NoSuchFieldException ex) {
            throw new IllegalStateException(ex);
        } catch (IllegalAccessException ex) {
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Bind this handler to a proxy object. After bound,
     * DefaultMethodHandler#invoke will act as if it was called on the proxy
     * object. Must be called once and only once for a given instance of
     * DefaultMethodHandler
     */
    public void bindTo(Object proxy) {
        if (this.handle != null) {
            throw new IllegalStateException("Attempted to rebind a default method handler that was already bound");
        }
        this.handle = this.unboundHandle.bindTo(proxy);
    }

    /**
     * Invoke this method. DefaultMethodHandler#bindTo must be called before the
     * first time invoke is called.
     */
    @Override
    public Object apply(Object[] argv) throws Throwable {
        if (this.handle == null) {
            throw new IllegalStateException("Default method handler invoked before proxy has been bound.");
        }
        return this.handle.invokeWithArguments(argv);
    }
}
