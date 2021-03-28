package team.returnteamname.myhotelcustomer.util;

public class Triplet<K, V, W> extends Pair<K, V>
{
    private final W theThird;

    public Triplet(K key, V value, W theThird)
    {
        super(key, value);
        this.theThird = theThird;
    }

    public W getTheThird()
    {
        return theThird;
    }
}
