package DataStore;

import org.json.JSONException;
import org.json.JSONObject;

import utils.Parameters;

public class Item_Table {

    int ActivePlayers, MaxPotValue, MaxSeat;
    long MaxBootValue, BootMaxValue, BootValue;
    String TableName, TableSpeed, TableId;
    int isFeatured;
    int points;

    public Item_Table(JSONObject data) {
        // TODO Auto-generated constructor stub

        try {

            setActivePlayers(data.getInt(Parameters.CategoryActivePlayers));
            setBootMinValue(data.getInt(Parameters.MinimumBetValue));
            setBootMaxValue(data.getInt(Parameters.MaximumBetValue));
            setMaxBootValue(data.getInt(Parameters.MinimumChips));
            setMaxSeat(3);
            setTableName(data.getString(Parameters.CategoryName));
            setTableId(data.getString(Parameters._id));
            setIsFeatured(data.getInt(Parameters.FlagIsFeatured));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(int isFeatured) {
        this.isFeatured = isFeatured;
    }

    public long getMaxBootValue() {
        return MaxBootValue;
    }

    public void setMaxBootValue(long maxBootValue) {
        MaxBootValue = maxBootValue;
    }

    public int getActivePlayers() {
        return ActivePlayers;
    }

    public void setActivePlayers(int activePlayers) {
        ActivePlayers = activePlayers;
    }


    //min boot minbv
    public long getBootMinValue() {
        return BootValue;
    }

    public void setBootMinValue(long bootValue) {
        BootValue = bootValue;
    }

    //min boot maxbv
    public long getBootMaxValue() {
        return BootMaxValue;
    }

    public void setBootMaxValue(long bootMaxValue) {
        BootMaxValue = bootMaxValue;
    }

    public int getMaxSeat() {
        return MaxSeat;
    }

    public void setMaxSeat(int maxSeat) {
        MaxSeat = maxSeat;
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
    }

    public String getTableId() {
        return TableId;
    }

    public void setTableId(String tableId) {
        TableId = tableId;
    }

}
