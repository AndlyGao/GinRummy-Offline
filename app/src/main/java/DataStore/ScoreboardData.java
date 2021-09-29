package DataStore;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Artoon on 11-Oct-16.
 */

public class ScoreboardData {

    int seatIndex;
    JSONArray points = new JSONArray();
    JSONArray st = new JSONArray();
    String username;
    String pp;
    int ps;
    String uid;
    JSONObject nCards;

    public JSONObject getnCards() {
        return nCards;
    }

    public void setnCards(JSONObject nCards) {
        this.nCards = nCards;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public String getPp() {
        return pp;
    }

    public void setPp(String pp) {
        this.pp = pp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public JSONArray getSt() {
        return st;
    }

    public void setSt(JSONArray st) {
        this.st = st;
    }

    public JSONArray getPoints() {
        return points;
    }

    public void setPoints(JSONArray points) {
        this.points = points;
    }

    public int getSeatIndex() {
        return seatIndex;
    }

    public void setSeatIndex(int seatIndex) {
        this.seatIndex = seatIndex;
    }

    @Override
    public String toString() {
        return "St => " + getSt().toString() + " points => " + points.toString() + " ps => " + getPs();
    }
}
