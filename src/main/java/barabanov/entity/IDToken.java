package barabanov.entity;

public class IDToken
{
    private final long id;
    private final long playerId;
    private final long resourceId;

    public IDToken(long id, long playerId, long resourceId)
    {
        this.id = id;
        this.playerId = playerId;
        this.resourceId = resourceId;
    }

    public long getId() {return id;}

    public long getPlayerId() {return playerId;}

    public long getResourceId() {return resourceId;}
}
