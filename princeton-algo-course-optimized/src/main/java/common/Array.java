package common;

public class Array {
    
    @SuppressWarnings("unchecked")
    public static <T> T[] newArray(int size) {
        return (T[]) new Object[size];
    }

    public static <T> void swap(final T[] a, final int i1, final int i2) {
        T temp = a[i1];
        a[i1] = a[i2];
        a[i2] = temp;
    }
}
