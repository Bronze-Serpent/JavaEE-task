package barabanov.service;

import barabanov.ORM.DAOProgress;
import barabanov.entity.IDToken;
import barabanov.entity.Progress;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class ProgressService
{

    private final DAOProgress daoProgress;


    public ProgressService(DAOProgress daoProgress) {this.daoProgress = daoProgress; }

    public List<Progress> readAllWithPlId(long plId) throws SQLException { return daoProgress.readAllWithPlId(plId); }


    public void writeToDB(List<Progress> progresses) throws SQLException {
        for(Progress progress : progresses)
            writeToDB(progress);
    }

    public void writeToDB(Progress p) throws SQLException { daoProgress.create(p);}

    public Progress readFromDB(long id) throws SQLException { return daoProgress.readById(id); }

    public void updateDB(Progress p) throws SQLException { daoProgress.update(p);}

    public void deleteFromDB(long id) throws SQLException { daoProgress.delete(id); }


    public static JSONObject progressToJson(Progress progress)
    {
        JSONObject jsonProgress = new JSONObject();

        jsonProgress.put("id", progress.getToken().getId());
        jsonProgress.put("playerId", progress.getToken().getPlayerId());
        jsonProgress.put("resourceId", progress.getToken().getResourceId());
        jsonProgress.put("score", progress.getScore());
        jsonProgress.put("maxScore", progress.getMaxScore());

        return jsonProgress;
    }


    public static JSONArray progressesToJson(List<Progress> progresses)
    {
        JSONArray jsonProgressArr = new JSONArray();
        for (Progress progress : progresses)
            jsonProgressArr.add(progressToJson(progress));

        return jsonProgressArr;
    }


    public static List<Progress> jsonToProgresses(JSONArray jArrProgress)
    {
        List<Progress> progresses = new LinkedList<>();

        for (Object o : jArrProgress)
        {
            JSONObject jPrgs = (JSONObject) o;
            progresses.add(new Progress(new IDToken((long) jPrgs.get("id"), (long) jPrgs.get("playerId"),
                    (long) jPrgs.get("resourceId")), (long) jPrgs.get("score"), (long) jPrgs.get("maxScore")));
        }

        return progresses;
    }
}
