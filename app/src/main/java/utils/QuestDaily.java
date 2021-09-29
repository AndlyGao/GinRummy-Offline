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
public class QuestDaily {

    public JSONArray Detail;
    public String dailydtl;
    public Context c;

    public QuestDaily(Context c) {

        this.c = c;
        dailydtl = LoadQuestDetailFromJson();
        try {
            Detail = new JSONArray(dailydtl);


        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("QuestDaily", "111");
        } catch (Exception e) {
            Log.e("QuestDaily", "222");
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
//            Logger.print("MMMMMMMMMM>>> QuestDaily>>> getTaskName>>> Taskobj " +taskobj.toString());

            name = taskobj.getString(Parameters.TASKNAME);

//            Logger.print("MMMMMMMMMM>>> QuestDaily>>> getTaskName>>> Name " + name);

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

//            Logger.print("MMMMMMMMMM>>> QuestDaily>>> getTaskName>>> Taskobj " +taskobj.toString());

            name = taskobj.getString(Parameters.TASKNAMESORT);

//            Logger.print("MMMMMMMMMM>>> QuestDaily>>> getTaskName>>> Name " + name);

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

//            Logger.print("MMMMMMMMMM>>> QuestDaily>>> getTaskName>>> Taskobj " +taskobj.toString());

            name = taskobj.getString(Parameters.TASKCOMPLETE);

//            Logger.print("MMMMMMMMMM>>> QuestDaily>>> getTaskName>>> Name " + name);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return name;
    }


    public int getRewardValue(int Taskid) {


        int achivement = getAchivementCompleteToday(Taskid);
        int claimCount = getClaimCountToday(Taskid);


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

//            Logger.print("MMMMMMMMMM>>> QuestDaily>>> getRewardValue>>> taskobj " +taskobj.toString());

            JSONArray goalArray = taskobj.getJSONArray(Parameters.ARRAYTIME);
            JSONArray rewardArray = taskobj.getJSONArray(Parameters.ARRAYREWARD);

//            Logger.print("MMMMMMMMMM>>> QuestDaily>>> getRewardValue>>> goalArray " +goalArray.toString());
//            Logger.print("MMMMMMMMMM>>> QuestDaily>>> getRewardValue>>> rewardArray " +rewardArray.toString());

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
//            Logger.print("MMMMMMMMMM>>> QuestDaily>>> getRewardValue>>> Reward " +reward);
//            Logger.print("MMMMMMMMMM>>> QuestDaily>>> getRewardValue>>> goal " +goal);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reward;
    }

    public int getCurrentGoalValue(int Taskid) {


        int achivement = getAchivementCompleteToday(Taskid);

        int reward = 0, goal = 0;
        int claimCount = getClaimCountToday(Taskid);

        try {
            JSONObject taskobj = new JSONObject();
            for (int i = 0; i < Detail.length(); i++) {
                int id = Detail.getJSONObject(i).getInt(Parameters.TASKID);
                if (Taskid == id) {

                    taskobj = Detail.getJSONObject(Taskid);
                    break;
                }

            }

//            Logger.print("MMMMMMMMMM>>> QuestDaily>>> getCurrentGoalValue>>> taskobj " +taskobj.toString());

            JSONArray goalArray = taskobj.getJSONArray(Parameters.ARRAYTIME);
            JSONArray rewardArray = taskobj.getJSONArray(Parameters.ARRAYREWARD);

//            Logger.print("MMMMMMMMMM>>> QuestDaily>>> getCurrentGoalValue>>> goalArray " +goalArray.toString());
//            Logger.print("MMMMMMMMMM>>> QuestDaily>>> getCurrentGoalValue>>> rewardArray " +rewardArray.toString());

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
//            Logger.print("MMMMMMMMMM>>> QuestDaily>>> getCurrentGoalValue>>> Reward " +reward);
//            Logger.print("MMMMMMMMMM>>> QuestDaily>>> getCurrentGoalValue>>> goal " +goal);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return goal;
    }


    public int getAchivementOnClaimCollectToday(int Taskid) {
        int achivement = 0;

        achivement = getAchivementCompleteToday(Taskid);
//        Logger.print("XXXXXXXXXXXXXX>>> achivement "+achivement);
        int currentGoal = getCurrentGoalValue(Taskid);
        if (achivement > currentGoal) {
            achivement = currentGoal;
        }

        return achivement;
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

            int ClaimCount = getClaimCountToday(Taskid);

            if ((ClaimCount) >= goalArray.length()) {
                return true;
            }

        } catch (JSONException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return isComplete;
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

            int ClaimCount = getClaimCountToday(Taskid);


            if ((ClaimCount) >= goalArray.length()) {
                return false;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }

        int achivement = 0;
        boolean isComplete = false;

        achivement = getAchivementOnClaimCollectToday(Taskid);
        int currentGoal = getCurrentGoalValue(Taskid);
        if (achivement == currentGoal) {
            return true;
        }

        return isComplete;
    }

    public void setClaimCount(int Taskid, int countcomplete) {

        if (Taskid == Parameters.BIG_GIN_ID) {
            PreferenceManager.SetQuestBigGinTodayCount(PreferenceManager.GetQuestBigGinTodayCount() + countcomplete);
            PreferenceManager.SetQuestBigGinClaimCountToday(PreferenceManager.GetQuestBigGinClaimCountToday() + 1);
        } else if (Taskid == Parameters.GIN_ID) {
            PreferenceManager.SetQuestGinTodayCount(PreferenceManager.GetQuestGinTodayCount() + countcomplete);
            PreferenceManager.SetQuestGinClaimCountToday(PreferenceManager.GetQuestGinClaimCountToday() + 1);
        } else if (Taskid == Parameters.KNOCK_ID) {
            PreferenceManager.SetQuestKnockTodayCount(PreferenceManager.GetQuestKnockTodayCount() + countcomplete);
            PreferenceManager.SetQuestKnockClaimCountToday(PreferenceManager.GetQuestKnockClaimCountToday() + 1);
        } else if (Taskid == Parameters.OFFERWALL_ID) {
            PreferenceManager.SetQuestOfferWallTodayCount(PreferenceManager.GetQuestOfferwallTodayCount() + countcomplete);
            PreferenceManager.SetQuestOfferClaimCountToday(PreferenceManager.GetQuestOfferClaimCountToday() + 1);
        } else if (Taskid == Parameters.VIDEO_ID) {
            PreferenceManager.SetQuestWatchVideoTodayCount(PreferenceManager.GetQuestWatchVideoTodayCount() + countcomplete);
            PreferenceManager.SetQuestVideoClaimCountToday(PreferenceManager.GetQuestVideoClaimCountToday() + 1);
        } else if (Taskid == Parameters.SHARE_ID) {
            PreferenceManager.SetQuestShareTodayCount(PreferenceManager.GetQuestShareTodayCount() + countcomplete);
            PreferenceManager.SetQuestShareClaimCountToday(PreferenceManager.GetQuestShareClaimCountToday() + 1);
        } else if (Taskid == Parameters.STRAIGHT_ID) {
            PreferenceManager.SetQuestStraightTodayCount(PreferenceManager.GetQuestStraightTodayCount() + countcomplete);
            PreferenceManager.SetQuestStraightClaimCountToday(PreferenceManager.GetQuestStraightClaimCountToday() + 1);
        } else if (Taskid == Parameters.OKLAHOMA_ID) {
            PreferenceManager.SetQuestOklahomaTodayCount(PreferenceManager.GetQuestOklahomaTodayCount() + countcomplete);
            PreferenceManager.SetQuestOklahomaClaimCountToday(PreferenceManager.GetQuestOklahomaClaimCountToday() + 1);
        }

    }

    public int getAchivementCompleteToday(int Taskid) {

        if (Taskid == Parameters.BIG_GIN_ID) {
            return (PreferenceManager.GetQuestBigGinCount() - PreferenceManager.GetQuestBigGinTodayCount());
        } else if (Taskid == Parameters.GIN_ID) {
            return (PreferenceManager.GetQuestGinCount() - PreferenceManager.GetQuestGinTodayCount());
        } else if (Taskid == Parameters.KNOCK_ID) {
            return (PreferenceManager.GetQuestKnockCount() - PreferenceManager.GetQuestKnockTodayCount());
        } else if (Taskid == Parameters.OFFERWALL_ID) {
            return (PreferenceManager.GetQuestOfferwallCount() - PreferenceManager.GetQuestOfferwallTodayCount());
        } else if (Taskid == Parameters.VIDEO_ID) {
            return (PreferenceManager.GetQuestWatchVideoCount() - PreferenceManager.GetQuestWatchVideoTodayCount());
        } else if (Taskid == Parameters.SHARE_ID) {
            return (PreferenceManager.GetQuestShareCount() - PreferenceManager.GetQuestShareTodayCount());
        } else if (Taskid == Parameters.STRAIGHT_ID) {
            return (PreferenceManager.GetQuestStraightCount() - PreferenceManager.GetQuestStraightTodayCount());
        } else if (Taskid == Parameters.OKLAHOMA_ID) {
            return (PreferenceManager.GetQuestOklahomaCount() - PreferenceManager.GetQuestOklahomaTodayCount());
        }

        return 0;
    }


    public int getClaimCountToday(int Taskid) {

        if (Taskid == Parameters.BIG_GIN_ID) {
            return (PreferenceManager.GetQuestBigGinClaimCountToday());
        } else if (Taskid == Parameters.GIN_ID) {
            return (PreferenceManager.GetQuestGinClaimCountToday());
        } else if (Taskid == Parameters.KNOCK_ID) {
            return (PreferenceManager.GetQuestKnockClaimCountToday());
        } else if (Taskid == Parameters.OFFERWALL_ID) {
            return (PreferenceManager.GetQuestOfferClaimCountToday());
        } else if (Taskid == Parameters.VIDEO_ID) {
            return (PreferenceManager.GetQuestVideoClaimCountToday());
        } else if (Taskid == Parameters.SHARE_ID) {
            return (PreferenceManager.GetQuestShareClaimCountToday());
        } else if (Taskid == Parameters.STRAIGHT_ID) {
            return (PreferenceManager.GetQuestStraightClaimCountToday());
        } else if (Taskid == Parameters.OKLAHOMA_ID) {
            return (PreferenceManager.GetQuestOklahomaClaimCountToday());
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
        }


        return 0;
    }


    public String LoadQuestDetailFromJson() {


        String json = null;
        try {

            InputStream is = c.getAssets().open("quest_daily.json");
            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
//            Logger.print("MMMMMMMMMMMMMM>>>> LoadQuestDetailFromJson:>>Exception111  " );
            ex.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
//            Logger.print("MMMMMMMMMMMMMM>>>> LoadQuestDetailFromJson:>>Exception222  " );
        }

        return json;


    }

}
