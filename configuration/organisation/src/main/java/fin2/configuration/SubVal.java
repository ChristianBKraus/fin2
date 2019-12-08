package fin2.configuration;

public interface SubVal<T> {
    T subVal(T entry, boolean change) throws Exception;
}
