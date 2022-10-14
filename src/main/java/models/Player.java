package models;

import java.util.LinkedList;
import java.util.List;

public class Player
{

    private final long playerId;

    private String nickname;

    private final List<Progress> progresses = new LinkedList<>();

    private final List<Currency> currencies = new LinkedList<>();

    private final List<Item> items = new LinkedList<>();


    public Player(long playerId, String nickname)
    {
        this.playerId = playerId;
        this.nickname = nickname;
    }


    public void addProgress(Progress prs)
    {
        progresses.add(prs);
    }

    public void addCurrency(Currency curr)
    {
        currencies.add(curr);
    }

    public void addItem(Item item)
    {
        items.add(item);
    }

    public void setNickname(String nickname) {this.nickname = nickname;}

    public long getPlayerId() {return playerId;}

    public String getNickname() {return nickname;}

    public List<Progress> getProgresses() {return progresses;}

    public List<Currency> getCurrencies() {return currencies;}

    public List<Item> getItems() {return items;}
}
