package pawelsmolarski95.gmail.com.tablefootball.infrastructure.utils;

import pawelsmolarski95.gmail.com.tablefootball.infrastructure.exception.BadRequestException;

public final class Utils {
    private Utils() {
    }

    /**
     * Checks that the specified object reference is not {@code null}. This
     * method is designed primarily for doing parameter validation in methods
     * and constructors, as demonstrated below:
     * <blockquote><pre>
     * public Foo(Bar bar) {
     *     this.bar = Objects.requireNonNull(bar);
     * }
     * </pre></blockquote>
     *
     * @param obj the object reference to check for nullity
     * @param <T> the type of the reference
     * @return {@code obj} if not {@code null}
     * @throws BadRequestException if {@code obj} is {@code null}
     */
    public static <T> T requireNonNullBadRequest(T obj) {
        if (obj == null)
            throw new BadRequestException();
        return obj;
    }

    /**
     * Checks that the specified object reference is not {@code null} and
     * throws a customized {@link BadRequestException} if it is. This method
     * is designed primarily for doing parameter validation in methods and
     * constructors with multiple parameters, as demonstrated below:
     * <blockquote><pre>
     * public Foo(Bar bar, Baz baz) {
     *     this.bar = Objects.requireNonNull(bar, "bar must not be null");
     *     this.baz = Objects.requireNonNull(baz, "baz must not be null");
     * }
     * </pre></blockquote>
     *
     * @param obj     the object reference to check for nullity
     * @param message detail message to be used in the event that a {@code
     *                NullPointerException} is thrown
     * @param <T> the type of the reference
     * @return {@code obj} if not {@code null}
     * @throws BadRequestException if {@code obj} is {@code null}
     */
    public static <T> T requireNonNullBadRequest(T obj, String message) {
        if (obj == null)
            throw new BadRequestException(message);
        return obj;
    }
}
