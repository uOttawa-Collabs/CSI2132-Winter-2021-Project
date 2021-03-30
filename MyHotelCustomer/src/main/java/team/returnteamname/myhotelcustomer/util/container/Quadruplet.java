package team.returnteamname.myhotelcustomer.util.container;

public class Quadruplet<K, U, V, W> extends Triplet<K, U, V>
{
    private final W theFourth;

    public Quadruplet(K key, U value, V theThird, W theFourth)
    {
        super(key, value, theThird);
        this.theFourth = theFourth;
    }

    public W getTheFourth()
    {
        return theFourth;
    }
}
