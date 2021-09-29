package utils;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Milin Artoon on 10-Jan-17.
 */
public class QuestLifeTime {

    public JSONArray Detail;
    public String dailydtl;
    public Context c;

    public QuestLifeTime(Context c) {

        this.c = c;
        dailydtl = LoadQuestDetailFromJson();
        try {
            Detail = new JSONArray(dailydtl);
            Logger.print("MMMMMMMMMM>>> QuestLifeTime>>> Detail " + Detail.toString());


        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("QuestLifeTime", "111");
        } catch (Exception e) {
            Log.e("QuestLifeTime", "222");
            e.printStackTrace();
        }

    }

    //  TASKID     |  TASK NAME
    //==> 0        | => BIG GIN
    //==> 1        | => GIN
    //==> 2        | => KNOCK
    //==> 3        | => STRAIGHT
    //==> 4        | => OKLAHOMA
    //==> 5        | => OFFERWALL
    //==> 6        | => WATCH VIDEO
    //==> 7        | => SHARE
    //==> 8        | => Remove Ads
    //==> 9        | => PAck 0.99
    //==> 10       | => PAck 2.99
    //==> 11       | => PAck 4.99
    //==> 12       | => PAck 9.99


    public String getTaskName(int Taskid) {

        String name = "";

        try {
            JSONObject taskobj = new JSONObject();
            for (int i = 0; i < Detail.length(); i++) {
                int id = Detail.getJSONObject(i).getInt(Parameters.TASKID);
                if (Taskid == id) {

                    taskobj = Detail.getJSONObject(Taskid);
                    break;
                }

            }

            //  Logger.print("MMMMMMMMMM>>> QuestLifeTime>>> getTaskName>>> Taskobj " +taskobj.toString());

            name = taskobj.getString(Parameters.TASKNAME);

            Logger.print("MMMMMMMMMM>>> QuestLifeTime>>> getTaskName>>> Name " + name);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return name;
    }

    public String getTaskCompletedText(int Taskid) {

        String name = "";

        try {
            JSONObject taskobj = new JSONObject();
            for (int i = 0; i < Detail.length(); i++) {
                int id = Detail.getJSONObject(i).getInt(Parameters.TASKID);
                if (Taskid == id) {

                    taskobj = Detail.getJSONObject(Taskid);
                    break;
                }

            }

            Logger.print("MMMMMMMMMM>>> QuestDaily>>> getTaskName>>> Taskobj " + taskobj.toString());

            name = taskobj.getString(Parameters.TASKCOMPLETE);

            Logger.print("MMMMMMMMMM>>> QuestDaily>>> getTaskName>>> Name " + name);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return name;
    }

    public String getTaskSortName(int Taskid) {

        String name = "";

        try {
            JSONObject taskobj = new JSONObject();
            for (int i = 0; i < Detail.length(); i++) {
                int id = Detail.getJSONObject(i).getInt(Parameters.TASKID);
                if (Taskid == id) {

                    taskobj = Detail.getJSONObject(Taskid);
                    break;
                }

            }

            Logger.print("MMMMMMMMMM>>> QuestDaily>>> getTaskName>>> Taskobj " + taskobj.toString());

            name = taskobj.getString(Parameters.TASKNAMESORT);

            Logger.print("MMMMMMMMMM>>> QuestDaily>>> getTaskName>>> Name " + name);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return name;
    }


    public int getRewardValue(int Taskid) {


        int achivement = getTotalAchivementComplete(Taskid);
        int claimCount = getClaimCountLife(Taskid);


        int reward = 0, goal = 0;

        try {
            JSONObject taskobj = new JSONObject();
            for (int i = 0; i < Detail.length(); i++) {
                int id = Detail.getJSONObject(i).getInt(Parameters.TASKID);
                if (Taskid == id) {

                    taskobj = Detail.getJSONObject(Taskid);
                    break;
                }

            }

            //     Logger.print("MMMMMMMMMM>>> QuestLifeTime>>> getRewardValue>>> taskobj " +taskobj.toString());

            JSONArray goalArray = taskobj.getJSONArray(Parameters.ARRAYTIME);
            JSONArray rewardArray = taskobj.getJSONArray(Parameters.ARRAYREWARD);

            //  Logger.print("MMMMMMMMMM>>> QuestLifeTime>>> getRewardValue>>> goalArray " +goalArray.toString());
            //  Logger.print("MMMMMMMMMM>>> QuestLifeTime>>> getRewardValue>>> rewardArray " +rewardArray.toString());

//            for(int i=0;i<goalArray.length()-1;i++){
//
//                if(achivement>goalArray.getInt(i)){
//
//                    goal=goalArray.getInt(i+1);
//                    reward=rewardArray.getInt(i+1);
//                }else{
//                    goal=goalArray.getInt(i);
//                    reward=rewardArray.getInt(i);
//                    break;
//                }
//
//            }

            if (claimCount < goalArray.length()) {
                goal = goalArray.getInt(claimCount);
                reward = rewardArray.getInt(claimCount);
            } else {
                goal = goalArray.getInt(goalArray.length() - 1);
                reward = rewardArray.getInt(rewardArray.length() - 1);
            }

            Logger.print("MMMMMMMMMM>>> QuestLifeTime>>> getRewardValue>>> Reward " + reward);
            // Logger.print("MMMMMMMMMM>>> QuestLifeTime>>> getRewardValue>>> goal " +goal);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reward;
    }

    public int getCurrentGoalValue(int Taskid) {


        int achivement = getTotalAchivementComplete(Taskid);

        int reward = 0, goal = 0;
        int claimCount = getClaimCountLife(Taskid);

        try {
            JSONObject taskobj = new JSONObject();
            for (int i = 0; i < Detail.length(); i++) {
                int id = Detail.getJSONObject(i).getInt(Parameters.TASKID);
                if (Taskid == id) {

                    taskobj = Detail.getJSONObject(Taskid);
                    break;
                }

            }

            //Logger.print("MMMMMMMMMM>>> QuestLifeTime>>> getCurrentGoalValue>>> taskobj " +taskobj.toString());

            JSONArray goalArray = taskobj.getJSONArray(Parameters.ARRAYTIME);
            JSONArray rewardArray = taskobj.getJSONArray(Parameters.ARRAYREWARD);

            // Logger.print("MMMMMMMMMM>>> QuestLifeTime>>> getCurrentGoalValue>>> goalArray " +goalArray.toString());
            // Logger.print("MMMMMMMMMM>>> QuestLifeTime>>> getCurrentGoalValue>>> rewardArray " +rewardArray.toString());

//            for(int i=0;i<goalArray.length()-1;i++){
//
//                if(achivement>goalArray.getInt(i)){
//
//                    goal=goalArray.getInt(i+1);
//                    reward=rewardArray.getInt(i+1);
//                }else{
//                    goal=goalArray.getInt(i);
//                    reward=rewardArray.getInt(i);
//                    break;
//                }
//
//            }

            if (claimCount < goalArray.length()) {
                goal = goalArray.getInt(claimCount);
                reward = rewardArray.getInt(claimCount);
            } else {
                goal = goalArray.getInt(goalArray.length() - 1);
                reward = rewardArray.getInt(rewardArray.length() - 1);
            }
            // Logger.print("MMMMMMMMMM>>> QuestLifeTime>>> getCurrentGoalValue>>> Reward " +reward);
            Logger.print("MMMMMMMMMM>>> QuestLifeTime>>> getCurrentGoalValue>>> goal " + goal);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return goal;
    }


    public int getAchivementOnClaimCollectLife(int Taskid) {
        int achivement = 0;

        achivement = getTotalAchivementComplete(Taskid);
        int currentGoal = getCurrentGoalValue(Taskid);
        if (achivement > currentGoal) {
            achivement = currentGoal;
        }

        return achivement;
    }


    public int getNoachivmentRemain() {
        int achivement = Detail.length();

        for (int i = 0; i < Detail.length(); i++) {
            if (getisAllTaskAchived(i)) {
                achivement--;
            }

        }
        return achivement;
    }

    public boolean getisAllTaskAchived(int Taskid) {

        boolean isComplete = false;
        try {
            JSONObject taskobj = new JSONObject();
            for (int i = 0; i < Detail.length(); i++) {
                int id = Detail.getJSONObject(i).getInt(Parameters.TASKID);
                if (Taskid == id) {

                    taskobj = Detail.getJSONObject(Taskid);
                    break;
                }

            }


            JSONArray goalArray = taskobj.getJSONArray(Parameters.ARRAYTIME);

            int ClaimCount = getClaimCountLife(Taskid);

            if (Taskid <= 5) {

                if ((ClaimCount) >= goalArray.length()) {
                    return true;
                }
            }

        } catch (JSONException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return isComplete;
    }

    public int getNoachivmentCompleted() {
        int achivement = 0;

        for (int i = 0; i < Detail.length(); i++) {
            if (getisCurrentGoalAchive(i)) {
                achivement++;
            }
        }
        return achivement;
    }


    public boolean getisCurrentGoalAchive(int Taskid) {
        try {
            JSONObject taskobj = new JSONObject();
            for (int i = 0; i < Detail.length(); i++) {
                int id = Detail.getJSONObject(i).getInt(Parameters.TASKID);
                if (Taskid == id) {

                    taskobj = Detail.getJSONObject(Taskid);
                    break;
                }
            }

            JSONArray goalArray = taskobj.getJSONArray(Parameters.ARRAYTIME);

            int ClaimCount = getClaimCountLife(Taskid);
            Logger.print("QQQQQQQQQQQ>>> getisCurrentGoalAchive>>> ClaimCount " + ClaimCount);

            if (Taskid <= Parameters.SHARE_ID) {

                Logger.print("QQQQQQQQQQQ>>> getisCurrentGoalAchive>>> 111");
                if ((ClaimCount) >= goalArray.length()) {
                    Logger.print("QQQQQQQQQQQ>>> getisCurrentGoalAchive>>> 111 >>> false");
                    return false;
                }
            } else {
                if (Taskid == Parameters.REMOVE_ADS_ID) {

                    if (PreferenceManager.getisshowAd() || PreferenceManager.GetQuestRemoveAdsCount() == 0) {
                        return false;
                    } else {

                        return ClaimCount < goalArray.length();
                    }
                } else if (Taskid == Parameters.PACK_One_Doll_ID) {

                    return !PreferenceManager.GetQuestPackOneDollerCollected();

                } else if (Taskid == Parameters.PACK_Three_Doll_ID) {
                    return !PreferenceManager.GetQuestPackThreeDollerCollected();
                } else if (Taskid == Parameters.PACK_Five_Doll_ID) {
                    return !PreferenceManager.GetQuestPackFiveDollerCollected();
                } else if (Taskid == Parameters.PACK_Ten_Doll_ID) {
                    return !PreferenceManager.GetQuestPackTenDollerCollected();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
        boolean isComplete = false;

        int achivement = 0;


        achivement = getAchivementOnClaimCollectLife(Taskid);
        int currentGoal = getCurrentGoalValue(Taskid);
        Logger.print("QQQQQQQQQQQ>>> getisCurrentGoalAchive>>> achivement " + achivement);
        Logger.print("QQQQQQQQQQQ>>> getisCurrentGoalAchive>>> currentGoal " + currentGoal);
        if (achivement == currentGoal) {
            return true;
        }

        return isComplete;
    }

    public void setClaimCount(int Taskid) {
        if (Taskid == Parameters.BIG_GIN_ID) {
            PreferenceManager.SetQuestBigGinClaimCountLife(PreferenceManager.GetQuestBigGinClaimCountLife() + 1);
        } else if (Taskid == Parameters.GIN_ID) {
            PreferenceManager.SetQuestGinClaimCountLife(PreferenceManager.GetQuestGinClaimCountLife() + 1);
        } else if (Taskid == Parameters.KNOCK_ID) {
            PreferenceManager.SetQuestKnockClaimCountLife(PreferenceManager.GetQuestKnockClaimCountLife() + 1);
        } else if (Taskid == Parameters.OFFERWALL_ID) {
            PreferenceManager.SetQuestOfferClaimCountLife(PreferenceManager.GetQuestOfferClaimCountLife() + 1);
        } else if (Taskid == Parameters.VIDEO_ID) {
            PreferenceManager.SetQuestVideoClaimCountLife(PreferenceManager.GetQuestVideoClaimCountLife() + 1);
        } else if (Taskid == Parameters.SHARE_ID) {
            PreferenceManager.SetQuestShareClaimCountLife(PreferenceManager.GetQuestShareClaimCountLife() + 1);
        } else if (Taskid == Parameters.STRAIGHT_ID) {
            PreferenceManager.SetQuestStraightClaimCountLife(PreferenceManager.GetQuestStraightClaimCountLife() + 1);
        } else if (Taskid == Parameters.OKLAHOMA_ID) {
            PreferenceManager.SetQuestOklahomaClaimCountLife(PreferenceManager.GetQuestOklahomaClaimCountLife() + 1);
        } else if (Taskid == Parameters.REMOVE_ADS_ID) {
            PreferenceManager.SetQuestRemoveAdsClaimCountLife(PreferenceManager.GetQuestRemoveAdsClaimCountLife() + 1);
        } else if (Taskid == Parameters.PACK_One_Doll_ID) {
            PreferenceManager.SetQuestPackOneDollerCollected(true);
            PreferenceManager.SetQuestPackOneDollerCount(0);
        } else if (Taskid == Parameters.PACK_Three_Doll_ID) {
            PreferenceManager.SetQuestPackThreeDollerCollected(true);
            PreferenceManager.SetQuestPackThreeDollerCount(0);

        } else if (Taskid == Parameters.PACK_Five_Doll_ID) {
            PreferenceManager.SetQuestPackFiveDollerCollected(true);
            PreferenceManager.SetQuestPackFiveDollerCount(0);
        } else if (Taskid == Parameters.PACK_Ten_Doll_ID) {
            PreferenceManager.SetQuestPackTenDollerCollected(true);
            PreferenceManager.SetQuestPackTenDollerCount(0);
        }


    }

//    public int getAchivementCompleteToday(int Taskid){
//
//
//        if(Taskid == 0){
//            return (PreferenceManager.GetQuestGinCount()-PreferenceManager.GetQuestGinTodayCount());
//        }else
//        if(Taskid == 1){
//            return (PreferenceManager.GetQuestKnockCount()-PreferenceManager.GetQuestKnockTodayCount());
//        }else
//        if(Taskid == 2){
//            return (PreferenceManager.GetQuestOfferwallCount()-PreferenceManager.GetQuestOfferwallTodayCount());
//        }else
//        if(Taskid == 3){
//            return (PreferenceManager.GetQuestWatchVideoCount()-PreferenceManager.GetQuestWatchVideoTodayCount());
//        }else
//        if(Taskid == 4){
//            return (PreferenceManager.GetQuestShareCount()-PreferenceManager.GetQuestShareTodayCount());
//        }
//
//        return 0;
//    }


    public int getClaimCountLife(int Taskid) {

        if (Taskid == Parameters.BIG_GIN_ID) {
            return (PreferenceManager.GetQuestBigGinClaimCountLife());
        } else if (Taskid == Parameters.GIN_ID) {
            return (PreferenceManager.GetQuestGinClaimCountLife());
        } else if (Taskid == Parameters.KNOCK_ID) {
            return (PreferenceManager.GetQuestKnockClaimCountLife());
        } else if (Taskid == Parameters.OFFERWALL_ID) {
            return (PreferenceManager.GetQuestOfferClaimCountLife());
        } else if (Taskid == Parameters.VIDEO_ID) {
            return (PreferenceManager.GetQuestVideoClaimCountLife());
        } else if (Taskid == Parameters.SHARE_ID) {
            return (PreferenceManager.GetQuestShareClaimCountLife());
        } else if (Taskid == Parameters.STRAIGHT_ID) {
            return (PreferenceManager.GetQuestStraightClaimCountLife());
        } else if (Taskid == Parameters.OKLAHOMA_ID) {
            return (PreferenceManager.GetQuestOklahomaClaimCountLife());
        } else if (Taskid == Parameters.REMOVE_ADS_ID) {
            return (PreferenceManager.GetQuestRemoveAdsClaimCountLife());
        }


        return 0;
    }


    public int getTotalAchivementComplete(int Taskid) {

        if (Taskid == Parameters.BIG_GIN_ID) {
            return (PreferenceManager.GetQuestBigGinCount());
        } else if (Taskid == Parameters.GIN_ID) {
            return (PreferenceManager.GetQuestGinCount());
        } else if (Taskid == Parameters.KNOCK_ID) {
            return (PreferenceManager.GetQuestKnockCount());
        } else if (Taskid == Parameters.OFFERWALL_ID) {
            return (PreferenceManager.GetQuestOfferwallCount());
        } else if (Taskid == Parameters.VIDEO_ID) {
            return (PreferenceManager.GetQuestWatchVideoCount());
        } else if (Taskid == Parameters.SHARE_ID) {
            return (PreferenceManager.GetQuestShareCount());
        } else if (Taskid == Parameters.STRAIGHT_ID) {
            return (PreferenceManager.GetQuestStraightCount());
        } else if (Taskid == Parameters.OKLAHOMA_ID) {
            return (PreferenceManager.GetQuestOklahomaCount());
        } else if (Taskid == Parameters.REMOVE_ADS_ID) {
            return (PreferenceManager.GetQuestRemoveAdsCount());
        } else if (Taskid == Parameters.PACK_One_Doll_ID) {
            return (PreferenceManager.GetQuestPackOneDollerCount());
        } else if (Taskid == Parameters.PACK_Three_Doll_ID) {
            return (PreferenceManager.GetQuestPackThreeDollerCount());
        } else if (Taskid == Parameters.PACK_Five_Doll_ID) {
            return (PreferenceManager.GetQuestPackFiveDollerCount());
        } else if (Taskid == Parameters.PACK_Ten_Doll_ID) {
            return (PreferenceManager.GetQuestPackTenDollerCount());
        }
        return 0;
    }


    public String LoadQuestDetailFromJson() {


        String json = null;
        try {

            InputStream is = c.getAssets().open("quest_lifetime.json");
            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            Logger.print("MMMMMMMMMMMMMM>>>> LoadQuestDetailFromJson:>>Exception111  ");
            ex.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            Logger.print("MMMMMMMMMMMMMM>>>> LoadQuestDetailFromJson:>>Exception222  ");
        }

        return json;


    }

}
