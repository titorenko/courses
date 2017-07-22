package common;

@FunctionalInterface
public interface BiIntConsumer{

    /**
     * Performs this operation on the given arguments.
     *
     * @param left the first argument
     * @param right the second argument
     */
    void accept(int left, int right);
}