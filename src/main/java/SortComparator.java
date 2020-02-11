import java.util.Comparator;

public class SortComparator<T> {

    private Engine.FormatEnum feOrder;

    SortComparator(Engine.FormatEnum feOrder) {
        this.feOrder = feOrder;
    }

    boolean compareTo(T i1, T i2) {
        Comparator<T> comparator = (T o1, T o2) -> {
            if (i1 instanceof Integer) {
                return ((Integer) o1).compareTo((Integer) o2);
            } else {
                return ((String) o1).compareTo((String) o2);
            }
        };

        if (feOrder == Engine.FormatEnum.ASC) {
            return comparator.compare(i1, i2) < 0;
        }
        return comparator.compare(i1, i2) >= 0;
    }
}
