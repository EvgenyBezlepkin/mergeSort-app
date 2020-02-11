import java.util.*;

public class SortModel<T> {

    private SortComparator<T> sortComparator;

    SortModel(SortComparator<T> sortComparator) {
        this.sortComparator = sortComparator;
    }

    public List<T> sortFile(List<T> currentList) {

        if (currentList.size() >= 2) {

            List<T> leftList = new ArrayList<>(currentList.subList(0, currentList.size() / 2));
            List<T> rightList = new ArrayList<>(currentList.subList(currentList.size() / 2, currentList.size()));

            sortFile(leftList);
            sortFile(rightList);
            mergeFiles(currentList, leftList, rightList);
        }
        return currentList;
    }


    private void mergeFiles(List<T> result, List<T> left, List<T> right) {
        int i1 = 0;
        int i2 = 0;

        for (int i = 0; i < result.size(); i++) {
            if (i2 >= right.size()
                    || (i1 < left.size()
                    && sortComparator.compareTo(left.get(i1), right.get(i2)))) {
                result.set(i, left.get(i1));
                i1++;
            } else {
                result.set(i, right.get(i2));
                i2++;
            }
        }
    }
}







