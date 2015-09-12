/**
 * Created by daleappleby on 11/09/15.
 */
@FunctionalInterface
public interface TreeFunc<T> {
    abstract T applyOver(T element);
}
