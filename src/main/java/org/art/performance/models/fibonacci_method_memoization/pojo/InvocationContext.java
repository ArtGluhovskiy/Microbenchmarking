package org.art.performance.models.fibonacci_method_memoization.pojo;

import java.util.Arrays;

public class InvocationContext {

    private final Class<?> targetClass;
    private final String targetMethod;
    private final Object[] args;

    public InvocationContext(Class<?> targetClass, String targetMethod, Object[] args) {
        this.targetClass = targetClass;
        this.targetMethod = targetMethod;
        this.args = args;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public String getTargetMethod() {
        return targetMethod;
    }

    public Object[] getArgs() {
        return args.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvocationContext)) return false;

        InvocationContext that = (InvocationContext) o;

        if (targetClass != null ? !targetClass.equals(that.targetClass) : that.targetClass != null) return false;
        if (targetMethod != null ? !targetMethod.equals(that.targetMethod) : that.targetMethod != null) return false;
        return Arrays.equals(args, that.args);
    }

    @Override
    public int hashCode() {
        int result = targetClass != null ? targetClass.hashCode() : 0;
        result = 31 * result + (targetMethod != null ? targetMethod.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }

    @Override
    public String toString() {
        return "InvocationContext {" +
                "targetClass = " + targetClass.getSimpleName() +
                ", targetMethod = '" + targetMethod + '\'' +
                ", args = " + Arrays.toString(args) +
                '}';
    }
}
