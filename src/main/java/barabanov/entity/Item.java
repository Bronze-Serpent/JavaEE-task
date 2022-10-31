package barabanov.entity;

public class Item
{
    private final IDToken token;
    private final long count;
    private final long level;


    public Item(IDToken token, long count, long level)
    {
        this.token = token;
        this.count = count;
        this.level = level;
    }

    public IDToken getToken() {return token;}

    public long getCount() {return count;}

    public long getLevel() {return level;}
}
