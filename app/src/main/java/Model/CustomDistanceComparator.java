package Model;

import java.util.Comparator;

/**
 * Created by ILIAS on 18/7/2018.
 */

public class CustomDistanceComparator  implements Comparator<Distance> {

    @Override
    public int compare(Distance o1, Distance o2) {
        return o1.getDistance().compareTo(o2.getDistance());
    }
}
