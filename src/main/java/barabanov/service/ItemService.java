package barabanov.service;

import barabanov.ORM.DAOItem;
import barabanov.entity.IDToken;
import barabanov.entity.Item;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class ItemService
{

    private final DAOItem daoItem;


    public ItemService(DAOItem daoItem) { this.daoItem = daoItem; }

    public List<Item> readAllWithPlId(long plId) throws SQLException { return daoItem.readAllWithPlId(plId); }


    public void writeToDB(List<Item> items) throws SQLException
    {
        for (Item item : items)
            writeToDB(item);
    }

    public void writeToDB(Item i) throws SQLException { daoItem.create(i);}

    public Item readFromDB(long id) throws SQLException { return daoItem.readById(id); }

    public void updateDB(Item i) throws SQLException { daoItem.update(i);}

    public void deleteFromDB(long id) throws SQLException { daoItem.delete(id); }


    public static JSONObject itemToJson(Item item)
    {
        JSONObject jsonItem = new JSONObject();

        jsonItem.put("id", item.getToken().getId());
        jsonItem.put("playerId", item.getToken().getPlayerId());
        jsonItem.put("resourceId", item.getToken().getResourceId());
        jsonItem.put("count", item.getCount());
        jsonItem.put("level", item.getLevel());

        return jsonItem;
    }


    public static List<Item> jsonToItems(JSONArray jArrItem)
    {
        List<Item> items = new LinkedList<>();

        for (Object o : jArrItem)
        {
            JSONObject jPrgs = (JSONObject) o;
            items.add(new Item(new IDToken((long) jPrgs.get("id"), (long) jPrgs.get("playerId"),
                    (long) jPrgs.get("resourceId")), (long) jPrgs.get("count"), (long) jPrgs.get("level")));
        }

        return items;
    }


    public static JSONArray itemToJson(List<Item> items)
    {
        JSONArray jsonItemsArr = new JSONArray();
        for (Item item : items)
            jsonItemsArr.add(itemToJson(item));

        return jsonItemsArr;
    }
}
