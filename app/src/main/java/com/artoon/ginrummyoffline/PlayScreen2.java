package com.artoon.ginrummyoffline;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.os.SystemClock;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.artoon.sort.Card;
import com.artoon.sort.Hand;
import com.artoon.sort.ICardSet;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;

import DataStore.Item_Card;
import DataStore.Item_Table;
import DataStore.Player_UserInfo;
import DataStore.ScoreboardData;
import utils.C;
import utils.CircularImageView;
import utils.DefaultExceptionHandler;
import utils.Logger;
import utils.MagicTextView;
import utils.Parameters;
import utils.PreferenceManager;
import utils.ResponseCodes;
import utils.Rotate3dAnimationX;
import utils.SQLiteManager;

/**
 * Created by savan.nasit on 6/6/2017.
 */
public class PlayScreen2 extends Activity implements OnClickListener {
    public static Handler handler;
    public static InterstitialAd mInterstitial;
    static AdRequest adRequest;
    static AdRequest adRequest2;
    private final int[] CardDefault = {R.drawable.c1, R.drawable.c2, R.drawable.c3,
            R.drawable.c4, R.drawable.c5, R.drawable.c6, R.drawable.c7,
            R.drawable.c8, R.drawable.c9, R.drawable.c10, R.drawable.c11,
            R.drawable.c12, R.drawable.c13, R.drawable.l1, R.drawable.l2,
            R.drawable.l3, R.drawable.l4, R.drawable.l5, R.drawable.l6,
            R.drawable.l7, R.drawable.l8, R.drawable.l9, R.drawable.l10,
            R.drawable.l11, R.drawable.l12, R.drawable.l13, R.drawable.k1,
            R.drawable.k2, R.drawable.k3, R.drawable.k4, R.drawable.k5,
            R.drawable.k6, R.drawable.k7, R.drawable.k8, R.drawable.k9,
            R.drawable.k10, R.drawable.k11, R.drawable.k12, R.drawable.k13,
            R.drawable.f1, R.drawable.f2, R.drawable.f3, R.drawable.f4,
            R.drawable.f5, R.drawable.f6, R.drawable.f7, R.drawable.f8,
            R.drawable.f9, R.drawable.f10, R.drawable.f11, R.drawable.f12,
            R.drawable.f13};
    private final int[] Select_Card = {R.drawable.c1_1, R.drawable.c2_1, R.drawable.c3_1,
            R.drawable.c4_1, R.drawable.c5_1, R.drawable.c6_1, R.drawable.c7_1,
            R.drawable.c8_1, R.drawable.c9_1, R.drawable.c10_1,
            R.drawable.c11_1, R.drawable.c12_1, R.drawable.c13_1,
            R.drawable.l1_1, R.drawable.l2_1, R.drawable.l3_1, R.drawable.l4_1,
            R.drawable.l5_1, R.drawable.l6_1, R.drawable.l7_1, R.drawable.l8_1,
            R.drawable.l9_1, R.drawable.l10_1, R.drawable.l11_1,
            R.drawable.l12_1, R.drawable.l13_1, R.drawable.k1_1,
            R.drawable.k2_1, R.drawable.k3_1, R.drawable.k4_1, R.drawable.k5_1,
            R.drawable.k6_1, R.drawable.k7_1, R.drawable.k8_1, R.drawable.k9_1,
            R.drawable.k10_1, R.drawable.k11_1, R.drawable.k12_1,
            R.drawable.k13_1, R.drawable.f1_1, R.drawable.f2_1,
            R.drawable.f3_1, R.drawable.f4_1, R.drawable.f5_1, R.drawable.f6_1,
            R.drawable.f7_1, R.drawable.f8_1, R.drawable.f9_1,
            R.drawable.f10_1, R.drawable.f11_1, R.drawable.f12_1,
            R.drawable.f13_1};
    private final int[] Green_Card = {R.drawable.c_g_1, R.drawable.c_g_2, R.drawable.c_g_3,
            R.drawable.c_g_4, R.drawable.c_g_5, R.drawable.c_g_6, R.drawable.c_g_7,
            R.drawable.c_g_8, R.drawable.c_g_9, R.drawable.c_g_10,
            R.drawable.c_g_11, R.drawable.c_g_12, R.drawable.c_g_13,
            R.drawable.l_g_1, R.drawable.l_g_2, R.drawable.l_g_3,
            R.drawable.l_g_4, R.drawable.l_g_5, R.drawable.l_g_6, R.drawable.l_g_7,
            R.drawable.l_g_8, R.drawable.l_g_9, R.drawable.l_g_10,
            R.drawable.l_g_11, R.drawable.l_g_12, R.drawable.l_g_13, R.drawable.k_g_1, R.drawable.k_g_2, R.drawable.k_g_3,
            R.drawable.k_g_4, R.drawable.k_g_5, R.drawable.k_g_6, R.drawable.k_g_7,
            R.drawable.k_g_8, R.drawable.k_g_9, R.drawable.k_g_10,
            R.drawable.k_g_11, R.drawable.k_g_12, R.drawable.k_g_13, R.drawable.f_g_1, R.drawable.f_g_2, R.drawable.f_g_3,
            R.drawable.f_g_4, R.drawable.f_g_5, R.drawable.f_g_6, R.drawable.f_g_7,
            R.drawable.f_g_8, R.drawable.f_g_9, R.drawable.f_g_10,
            R.drawable.f_g_11, R.drawable.f_g_12, R.drawable.f_g_13};
    private final int[] Blue_Card = {R.drawable.c_b_1, R.drawable.c_b_2, R.drawable.c_b_3,
            R.drawable.c_b_4, R.drawable.c_b_5, R.drawable.c_b_6, R.drawable.c_b_7,
            R.drawable.c_b_8, R.drawable.c_b_9, R.drawable.c_b_10,
            R.drawable.c_b_11, R.drawable.c_b_12, R.drawable.c_b_13,
            R.drawable.l_b_1, R.drawable.l_b_2, R.drawable.l_b_3,
            R.drawable.l_b_4, R.drawable.l_b_5, R.drawable.l_b_6, R.drawable.l_b_7,
            R.drawable.l_b_8, R.drawable.l_b_9, R.drawable.l_b_10,
            R.drawable.l_b_11, R.drawable.l_b_12, R.drawable.l_b_13, R.drawable.k_b_1, R.drawable.k_b_2, R.drawable.k_b_3,
            R.drawable.k_b_4, R.drawable.k_b_5, R.drawable.k_b_6, R.drawable.k_b_7,
            R.drawable.k_b_8, R.drawable.k_b_9, R.drawable.k_b_10,
            R.drawable.k_b_11, R.drawable.k_b_12, R.drawable.k_b_13, R.drawable.f_b_1, R.drawable.f_b_2, R.drawable.f_b_3,
            R.drawable.f_b_4, R.drawable.f_b_5, R.drawable.f_b_6, R.drawable.f_b_7,
            R.drawable.f_b_8, R.drawable.f_b_9, R.drawable.f_b_10,
            R.drawable.f_b_11, R.drawable.f_b_12, R.drawable.f_b_13};
    private final String[] cardString = {"c-1", "c-2", "c-3", "c-4", "c-5", "c-6", "c-7",
            "c-8", "c-9", "c-10", "c-11", "c-12", "c-13", "l-1", "l-2", "l-3",
            "l-4", "l-5", "l-6", "l-7", "l-8", "l-9", "l-10", "l-11", "l-12",
            "l-13", "k-1", "k-2", "k-3", "k-4", "k-5", "k-6", "k-7", "k-8",
            "k-9", "k-10", "k-11", "k-12", "k-13", "f-1", "f-2", "f-3", "f-4",
            "f-5", "f-6", "f-7", "f-8", "f-9", "f-10", "f-11", "f-12", "f-13"};
    private final ArrayList<ImageView> selectedCard = new ArrayList<>();
    private final ImageView[] ivMyCards = new ImageView[11];
    private final ImageView[] ivLeftRobotCard = new ImageView[11];
    private final ImageView[] ivRightRobotCard = new ImageView[11];
    private final int[] closeCardLocationXY = new int[2];
    private final int[] selectedCardXY = new int[2];
    private final int[] closeDeckXY = new int[2];
    private final int[] lastCardOfUser = new int[2];
    private final boolean[] DealCardToSeat = {false, false, false};
    private final boolean[] UserTurnStarted = {
            false, false, false};
    private final ImageView[] ivBootAnim = new ImageView[7];
    private final int[] posX = new int[14];
    private final ImageView[] Coin_Animation = new ImageView[10];
    private final Handler gameHandler = new Handler();
    private final Handler bootValueAnimationHandler = new Handler();
    ArrayList<HashMap<String, String>> playingUsers = new ArrayList<>();
    int start = 1000;
    int end = 150000;
    SQLiteManager sqLiteManager;

    TextView tvAutosortOnOff;
    Item_Table tableinfo;
    ImageView iv_2X;
    Animation up, Down;
    HashMap<String, String> myMap = new HashMap<>();
    int totalGameCounter;
    ImageView linedead;
    FrameLayout frmtable, frmTemp11, frmTemp12;
    LinearLayout ivScore;
    LinearLayout ivScore_knock;
    TextView tv_knock_for, tv_knock_score;
    //========================= Bonus Timer With pause ane resume ======================
    FrameLayout frm_timer;
    TextView tv_bonus_timer;
    LinearLayout frm_time_list;
    FrameLayout[] frm_time = new FrameLayout[6];
    TextView[] tv_timer = new TextView[6];
    TextView[] tv_chips = new TextView[6];
    ImageView[] iv_line = new ImageView[5];
    ImageView[] iv_red_glow = new ImageView[6];
    ImageView[] iv_green = new ImageView[6];
    long[] Chips = {100, 250, 500, 1000, 2500, 5000};
    long[] time = {300000, 600000, 1200000, 2700000, 5400000, 7200000};

    boolean isClaimEnable;
    Handler hidehandler;
    CountDownTimer Bonushcdtimer;
    ImageView ivGinRummyText;
    int userdeal = 0;
    ArrayList<Item_Card> usercard = new ArrayList<>();
    String PickCard = " ";
    Handler animationHandler = new Handler();
    String[] COLOR = {"l", "k", "c", "f"};

    Handler mHandler;
    Runnable mRunnable;
    //========================= Timer With pause ane resume ======================
    CountDownTimer cdt;
    boolean isTimerStart;
    String msg;
    private TextView HelpText, tvTotalBetValue, hello;
    private FrameLayout ivScoreboard;
    private ImageView ivScoreboard22;
    private TextView NotificationText;
    private C c;
    private TextView tvRoundNo;
    private int second;
    private int timercounter = 30000;
    private Timer timer = new Timer();
    private int Screen_Hight, Screen_Width;
    private final View.OnLongClickListener CardlongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            // TODO Auto-generated method stub

            Logger.print("onLongClick on CARD");
            LayoutParams layoutParams = (LayoutParams) v
                    .getLayoutParams();
            layoutParams.width = getwidth(126);
            layoutParams.height = getheight(162);
            v.setLayoutParams(layoutParams);
            return true;
        }
    };
    private int gameCounter = 0;
    private Typeface tf;
    private JSONObject senddata = new JSONObject();
    private CountDownTimer mCountDownTimer, mCountDownTimer1;
    private int BottomDeadWood, RightDeadWood, LeftDeadWood;
    private int required_knock = 10;
    private ImageView getSelectedCard, ivCloseCard, ivCloseCard9, ivCloseDeck9, ivCloseDeck, ivCardOnDeck, ivCardOnDeck9,
            ivUserOneCard, ivUserTwoCard, ivUserThreeCard, ivCardForDistribution;
    private CircularImageView ivProfileImage, ivProfileImageTwo, ivProfileImageThree;
    private int[] pickCardAnimRightUserXY = new int[2];
    private int[] pickCardAnimLeftUserXY = new int[2];
    private int BottomSeatIndex = 0;
    private int LeftSeatIndex = 1;
    private int RightSeatIndex = 2;
    private FrameLayout frmUserCards;
    private String selectedCardToRemove;
    private int[] closeCardXY = new int[2];
    private int cardDistributeCounter = 0;
    private boolean isAnimStop = false;
    private boolean isFirstTurn = false;
    private Timer UserTimer;
    private float totalTime = 0;
    private int seconds = 0;
    private int cnt = 0;
    private float currentProgress = 0;
    private float incProgress = 0;
    private int User_turn_cnt = 0;
    private ArrayList<Item_Card> openBunchCard = new ArrayList<>();
    private ArrayList<Item_Card> closeBunchCard = new ArrayList<>();
    private ImageView ivPickCard, ivPickCard9;
    private int cardsize = 0;
    private ArrayList<Item_Card> card_RightUser = new ArrayList<>(),
            card_LeftUser = new ArrayList<>(),
            card_BottomUser = new ArrayList<>();
    private boolean isUserTurn, hasTakenCard, isgin, isknock;
    private TextView tvCardCounter;
    private TextView tvScore;
    private TextView tvScoreThree;
    private TextView tvScoreTwo;
    private TextView tvPoints;
    private boolean isCardThrown = false;
    private int PileCardCounter = 52;
    private Button btnSkip, btnKnock, btnGin;
    private ImageView ivUserTwoThrownCard, ivUserOneThrownCard, ivUserTwoThrownCard2, ivUserOneThrownCard2, ivGiftIcon,
            ivGiftIconThree, ivGiftIconTwo, ivGiftIconLtoB, ivGiftIconLtoR,
            ivGiftIconThreeBtoR, ivGiftIconThreeBtoL, ivGiftIconTwoRtoL,
            ivGiftIconTwoRtoB;
    private FrameLayout StandUp_Btn;
    private ImageView ivUserOneBootChip;
    private ImageView ivUserTwoBootChip;
    private ImageView ivUserThreeBootChip;
    private ImageView ivSkipUserOne, ivSkipUserTwo, ivSkipUserThree, ivTimeOutUserOne,
            ivTimeOutUserTwo, ivTimeOutUserThree, ivKnockUserOne,
            ivKnockUserTwo, ivKnockUserThree, ivGinUserOne, ivGinUserTwo,
            ivGinUserThree, ivGinUserOne2, ivGinUserTwo2, ivGinUserThree2;
    private boolean UserWinner[] = {false, false, false};
    private ImageView LeftUserRing, RightUserRing, BottomUserRing;
    private MagicTextView ivUserNameOne, ivUserNameTwo, ivUserNameThree;
    private FrameLayout frmLeftUserOpenCard, frmRightUserOpenCard;
    private FrameLayout frmMainLayout;
    private ImageView ivHandCloseDeck, ivHandOpenCard;
    private Animation handAnimation;
    private Dialog ConfirmationDialog;
    private int round;
    private ImageView ivDrawUserThree, ivDrawUserTwo, ivDrawUserOne;
    private boolean isTutorial = false;
    private boolean isDraw = false;
    private ImageView ivBottomUserCard, BottomUserVipTag, LeftUserVipTag,
            RightUserVipTag;
    private FrameLayout frmDeckBackGround, frmDeckBackGround9;
    private TextView tvBootAmtGti;
    private TextView tvTableNameGti;
    private TextView tvMinChipsGti;
    private LinearLayout llTableInfo;
    private ImageView ivWinninngRoundOne, ivWinninngRoundTwo, ivWinninngRoundThree,
            ivWinninngCircleOne, ivWinninngCircleTwo, ivWinninngCircleThree;
    private boolean isFromPrivateTable = false;
    private boolean isSeeMyCardCalledFromGti = false;
    private boolean isDefaultSequencewise = true;
    private Button btnDrawNew, btnFaceUp, btnDiscard;
    private TextView exit_text, switch_text, table_info_text, scoreboard_text;
    private FrameLayout back_menu_frame;
    private FrameLayout close_menu;
    private Animation FromLeft, ToLeft;
    private Animation animUserRing;
    private boolean messageShown = false, showControls = false;
    private AnimationSet setToLeft, setToRight;
    private TextView tvController;
    private CheckBox cbSound;
    private CheckBox cbVibrate;
    private CheckBox cbController;
    private LayoutParams frmMesage;
    private ToggleButton tglSorting;
    private boolean isSortButtonClickable = false;
    private Animation buttonPressed, heartBeatAnimation;
    private boolean RefreshView = false;
    private boolean isAllowtoCardClick = false, isCardTouchable = false;
    private Button btngroup;
    private LinearLayout groupBgLinear;
    private LinearLayout llExitToLobbyWrapper;
    private LinearLayout llSwitchTableWrapper;
    private LinearLayout llTableInfoWrapper;
    private LinearLayout llScoreboardWrapper, llControllsWrapper;
    private FrameLayout frmDeadwoodContainer;
    private TextView tvUserScore;
    private int numberOfPlayers = 2;
    private TextView tvPointsGti;
    private Handler messageHandler = new Handler();
    private int CURRENT_USER_TURN = 0;
    private int INITIAL_USER_TURN = 0;
    private int PREVIOUS_USER_TURN = 0;
    private int BOOT_VALUE = 0;
    private int GAME_POINT = 0;
    private int ROUND_NUMBER = 0;
    private boolean GAME_OVER = false;
    private boolean isCardPicked = false;
    private boolean isUserPicked = false;
    private ScoreboardData[] scoreboardData = new ScoreboardData[3];
    private Player_UserInfo[] players = new Player_UserInfo[3];
    private ToggleButton tglAutoSorting;
    private CheckBox cbAutoSort;
    private ArrayList<Item_Table> TableData;
    private boolean ispause;
    private Dialog ConfirmationDialogInfo;
    private boolean isRobotCardShow = false;
    private boolean is3danimation = false;
    private LinearLayout llThemesWrapper;
    private TextView tvbootAmount, tvdeadwoodlbl, tvdeadwoodScore, tvdeadwoodScore2;
    private boolean isGameinBackGround = false;
    //===============================================================================
    private FrameLayout fmUserOne;
    private boolean isMove = false;
    private int GroupnumberCounter = 0;
    private long firstTap = 0, firstTapToShowMessage = 0;
    private boolean cardTouch1 = true;
    private long mLastClickTime = 0;
    private int throwncardindex = -1;
    private AnimationSet setLeftSDC, setRightSDC, setBottomSDC;
    private boolean DealingAnimationCompleted = false;
    private int cardW;
    private int CARD_WIDTH;
    private int CARD_HEIGHT;
    private int highCardCalue = 0;
    private int iCardThrowOnDeck;
    private TranslateAnimation tAnimBottomPFTC;
    private final View.OnTouchListener cardTouch = new View.OnTouchListener() {
        int index1;
        int _xDelta, _yDelta;
        long startTime;
        int cardPos = -1;
        int tempK;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            // Logger.print("Touch At => " + v.getTag().toString() + " cardTouch => " + cardTouch + " cardTouchble => " + isCardTouchable);
            if (!isCardTouchable || !DealCardToSeat[BottomSeatIndex]) {
                return false;
            }
            if (!cardTouch1 && ivMyCards[cardPos] != v)
                return false;
            /*if (removeCardTouch)
                return false;*/
            for (int k = 0; k < ivMyCards.length; k++) {
                if (v == ivMyCards[k]) {
                    final int X = (int) event.getRawX();
                    final int Y = (int) event.getRawY();
                    // float Xx = 0, Yy;
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            Logger.print("ACtion DOWN called");
                            isMove = false;
                            cardTouch1 = false;
                            //removeCardTouch = false;
                            index1 = k;
                            cardPos = k;
                            tempK = k;
                            startTime = SystemClock.elapsedRealtime();
                            if (selectedCard.size() == 0)
                                firstTapToShowMessage = SystemClock.elapsedRealtime();
                            LayoutParams lParams = (LayoutParams) v.getLayoutParams();
                            _xDelta = X - lParams.leftMargin;
                            _yDelta = Y - lParams.topMargin;
                            v.setLayoutParams(lParams);
                            return true;
                        // break;
                        case MotionEvent.ACTION_UP:
                            Logger.print("Action Up ->>>>>>>>> elaps - start > 345 => ");
                            cardPos = -1;
                            cardTouch1 = true;
                            int cardLength = card_BottomUser.size();
                            //int top = (int) event.getRawY() + (v.getHeight() / 2);
                            //int left = (int) ((int) event.getRawX() - (v.getWidth() / 1.5));
                            /*if (cardLength <= 8)
                                left = left - getwidth(140);*/
                            try {
                                if (SystemClock.elapsedRealtime() - startTime > 345) {
                                    Logger.print("Action Up ->>>>>>>>> elaps - start > 345 => " + (SystemClock.elapsedRealtime() - startTime));
                                    if (isPointInsideOpenDeck(event.getRawX(), event.getRawY() + v.getHeight())) {
                                        if (!isUserTurn) {
                                            Logger.print("!isAllowtoCardClick && !isMove 33333");
                                            NotificationText.setVisibility(View.VISIBLE);
                                            NotificationText.setText("Oops! It's not your turn");
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    NotificationText.setVisibility(View.INVISIBLE);
                                                }
                                            }, 2000);
                                        } else if (isUserTurn && !hasTakenCard) {
                                            NotificationText.setVisibility(View.VISIBLE);
                                            NotificationText.setText("Please take the card from deck");
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    NotificationText.setVisibility(View.INVISIBLE);
                                                }
                                            }, 2000);
                                        } else if (isAllowtoCardClick) {
                                            if (selectedCard.size() > 0)
                                                selectedCard.clear();
                                            selectedCard.add((ImageView) v);
                                            closeCardClick();
                                            if (selectedCard.size() > 0) {
                                                //selectedCard.remove(0);
                                                selectedCard.clear();
                                                btnDiscard.setVisibility(View.GONE);
                                            }
                                        }
                                    } else {
                                        if (selectedCard.size() > 0) {
                                            //selectedCard.remove(0);
                                            selectedCard.clear();
                                            btnDiscard.setVisibility(View.GONE);
                                        }
                                        int c = /*FindCardNumber1(top, left)*/tempK;
                                        Logger.print("=>>>>>>>>> c " + c + " " + index1 + " " + cardLength);
                                        if (c != -1) {
                                            if (k >= cardLength) {
                                                k = cardLength - 1;
                                            }
                                            try {
                                                card_BottomUser.get(k).setGroupNumber(card_BottomUser.get(c).getGroupNumber());
                                                if (!(index1 == 10 && cardLength == 10) && !(index1 == 7 && cardLength == 7)) {
                                                    if (c < index1) {
                                                        for (int i = index1; i - 1 >= c; i--) {
                                                            Logger.print("cccccciiiic 111 " + (index1));
                                                            Collections.swap(card_BottomUser, i, i - 1);
                                                        }
                                                    } else if (c > index1) {
                                                        if (c == cardLength - 1) {
                                                            for (int i = index1; i + 1 <= c; i++) {
                                                                Logger.print("cccccciiiic 2221 " + (i));
                                                                Collections.swap(card_BottomUser, i, i + 1);
                                                            }
                                                        } else {
                                                            for (int i = index1; i + 1 < c; i++) {
                                                                Logger.print("cccccciiiic 2222 " + (i));
                                                                Collections.swap(card_BottomUser, i, i + 1);
                                                            }
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    //}
                                    reDrawUserCards("");
                                } else if (isAllowtoCardClick && !isMove) {
                                    ivMyCards[k].setVisibility(View.VISIBLE);
                                    for (int i = 0; i < ivMyCards.length; i++) {
                                        if (v == ivMyCards[i]) {
                                            Logger.print("Selected Card 11=> " + selectedCard.size());
                                            boolean isCloseCardClicked = false;
                                            if (selectedCard.size() == 1 && SystemClock.elapsedRealtime() - firstTap < 460) {
                                                getSelectedCard = selectedCard.get(0);
                                                Logger.print("Selected Card 22=> " + selectedCard.size());
                                                if (ivMyCards[i].getTag().toString().contentEquals(getSelectedCard.getTag().toString())) {
                                                    Logger.print("Selected Card 33=> " + selectedCard.size());
                                                    if (ivHandOpenCard.getAnimation() != null)
                                                        ivHandOpenCard.clearAnimation();
                                                    ivHandOpenCard.setVisibility(View.INVISIBLE);
                                                    btnDrawNew.setVisibility(View.GONE);
                                                    btnFaceUp.setVisibility(View.GONE);
                                                    isCloseCardClicked = true;
                                                    closeCardClick();
                                                }
                                            }
                                            final LayoutParams frm = (LayoutParams) ivMyCards[i].getLayoutParams();
                                            Logger.print("Selected card SIZE => " + selectedCard.size());
                                            if (selectedCard.size() >= 1) {
                                                Logger.print("Selected card is Contain in Array => " + isContaintselected(i));
                                                ImageView iSelect = selectedCard.get(0);
                                                LayoutParams frmFirstCard = (LayoutParams) iSelect.getLayoutParams();
                                                frmFirstCard.bottomMargin = getheight(0);
                                                frmFirstCard.width = CARD_WIDTH;
                                                frmFirstCard.height = CARD_HEIGHT;
                                                frmFirstCard.topMargin = CARD_WIDTH * 30 / 190;
                                                frmFirstCard.bottomMargin = 0;
                                                iSelect.setLayoutParams(frmFirstCard);
                                                iSelect.setPadding(0, 0, 0, 0);
                                                iSelect.setImageResource(0);
                                                selectedCard.remove(0);
                                                int selectIndex = Arrays.asList(ivMyCards).indexOf(iSelect);
                                                try {
                                                    if (selectIndex - 1 >= 0) {
                                                        frmFirstCard = (LayoutParams) ivMyCards[selectIndex - 1].getLayoutParams();
                                                        frmFirstCard.bottomMargin = getheight(0);
                                                        frmFirstCard.width = CARD_WIDTH;
                                                        frmFirstCard.height = CARD_HEIGHT;
                                                        frmFirstCard.topMargin = CARD_WIDTH * 30 / 190;
                                                        frmFirstCard.bottomMargin = 0;
                                                        ivMyCards[selectIndex - 1].setLayoutParams(frmFirstCard);
                                                    }
                                                    if (selectIndex + 1 < cardLength) {
                                                        frmFirstCard = (LayoutParams) ivMyCards[selectIndex + 1].getLayoutParams();
                                                        frmFirstCard.bottomMargin = getheight(0);
                                                        frmFirstCard.width = CARD_WIDTH;
                                                        frmFirstCard.height = CARD_HEIGHT;
                                                        frmFirstCard.topMargin = CARD_WIDTH * 30 / 190;
                                                        frmFirstCard.bottomMargin = 0;
                                                        ivMyCards[selectIndex + 1].setLayoutParams(frmFirstCard);
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                if (ivMyCards[i] != iSelect) {
                                                    frm.width = (int) (CARD_WIDTH * 1.2f);
                                                    frm.height = (int) (CARD_HEIGHT * 1.2f);
                                                    //frm.bottomMargin = getheight(10);
                                                    selectedCard.add(ivMyCards[i]);
                                                    ivMyCards[i].setLayoutParams(frm);
                                                    ivMyCards[i].setImageResource(R.drawable.selectcard);
                                                    LayoutParams frm1;
                                                    try {
                                                        if (i - 1 >= 0) {
                                                            frm1 = (LayoutParams) ivMyCards[i - 1].getLayoutParams();
                                                            frm1.width = (int) (CARD_WIDTH * 1.1f);
                                                            frm1.height = (int) (CARD_HEIGHT * 1.1f);
                                                            //frm1.bottomMargin = getheight(10);
                                                            ivMyCards[i - 1].setLayoutParams(frm1);
                                                        }
                                                        if (i + 1 < cardLength) {
                                                            frm1 = (LayoutParams) ivMyCards[i + 1].getLayoutParams();
                                                            frm1.width = (int) (CARD_WIDTH * 1.1f);
                                                            frm1.height = (int) (CARD_HEIGHT * 1.1f);
                                                            //frm1.bottomMargin = getheight(10);
                                                            ivMyCards[i + 1].setLayoutParams(frm1);
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                } else {
                                                    if (ivHandOpenCard.getAnimation() != null)
                                                        ivHandOpenCard.clearAnimation();
                                                    ivHandOpenCard.setVisibility(View.INVISIBLE);
                                                }
                                            } else if (!isCloseCardClicked) {
                                                frm.width = (int) (CARD_WIDTH * 1.2f);
                                                frm.height = (int) (CARD_HEIGHT * 1.2f);
                                                // frm.bottomMargin = getheight(10);
                                                try {
                                                    ivMyCards[i].setLayoutParams(frm);
                                                    selectedCard.add(ivMyCards[i]);
                                                    LayoutParams frm1;
                                                    if (i - 1 >= 0) {
                                                        frm1 = (LayoutParams) ivMyCards[i - 1].getLayoutParams();
                                                        frm1.width = (int) (CARD_WIDTH * 1.1f);
                                                        frm1.height = (int) (CARD_HEIGHT * 1.1f);
                                                        //frm1.bottomMargin = getheight(10);
                                                        ivMyCards[i - 1].setLayoutParams(frm1);
                                                    }
                                                    if (i + 1 < cardLength) {
                                                        frm1 = (LayoutParams) ivMyCards[i + 1].getLayoutParams();
                                                        frm1.width = (int) (CARD_WIDTH * 1.1f);
                                                        frm1.height = (int) (CARD_HEIGHT * 1.1f);
                                                        //frm1.bottomMargin = getheight(10);
                                                        ivMyCards[i + 1].setLayoutParams(frm1);
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                ivHandOpenCard.setVisibility(View.VISIBLE);
                                                ivHandOpenCard.startAnimation(handAnimation);
                                            }
                                            firstTap = SystemClock.elapsedRealtime();
                                            Logger.print("Selected card size before show Discard button => " + selectedCard.size());
                                            if (selectedCard.size() == 1 && !isCardThrown) {
                                                if (showControls)
                                                    btnDiscard.setVisibility(View.VISIBLE);
                                            } else {
                                                btnDiscard.setVisibility(View.GONE);
                                            }
                                        }
                                    }
                                } else if (!isAllowtoCardClick && !isMove) {
                                    Logger.print("!isAllowtoCardClick && !isMove");
                                    for (int i = 0; i < ivMyCards.length; i++) {
                                        if (v == ivMyCards[i]) {
                                            final LayoutParams frm = (LayoutParams) ivMyCards[i].getLayoutParams();
                                            if (isContaintselected(i)) {
                                                Logger.print("!isAllowtoCardClick && !isMove 22222 " + selectedCard.toString() + " " + (SystemClock.elapsedRealtime() - firstTap) + " " + isUserTurn);
                                                if (selectedCard.size() >= 1 && SystemClock.elapsedRealtime() - firstTapToShowMessage < 460 && !isUserTurn) {
                                                    Logger.print("!isAllowtoCardClick && !isMove 33333");
                                                    NotificationText.setVisibility(View.VISIBLE);
                                                    NotificationText.setText("Oops! It's not your turn");
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            NotificationText.setVisibility(View.INVISIBLE);
                                                        }
                                                    }, 2000);
                                                } else if (isUserTurn && !hasTakenCard) {
                                                    NotificationText.setVisibility(View.VISIBLE);
                                                    NotificationText.setText("Please take the card from deck");
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            NotificationText.setVisibility(View.INVISIBLE);
                                                        }
                                                    }, 2000);
                                                }
                                            }
                                            if (selectedCard.size() >= 1) {
                                                Logger.print("Selected card is Contain in Array => " + isContaintselected(i));
                                                ImageView iSelect = selectedCard.get(0);
                                                cardW = getwidth(120);
                                                LayoutParams frmFirstCard = (LayoutParams) iSelect.getLayoutParams();
                                                frmFirstCard.bottomMargin = getheight(0);
                                                frmFirstCard.width = CARD_WIDTH;
                                                frmFirstCard.height = CARD_HEIGHT;
                                                frmFirstCard.topMargin = CARD_WIDTH * 30 / 190;
                                                frmFirstCard.bottomMargin = 0;
                                                iSelect.setLayoutParams(frmFirstCard);
                                                iSelect.setPadding(0, 0, 0, 0);
                                                iSelect.setImageResource(0);
                                                selectedCard.remove(0);
                                                int selectIndex = Arrays.asList(ivMyCards).indexOf(iSelect);
                                                if (selectIndex - 1 >= 0) {
                                                    frmFirstCard = (LayoutParams) ivMyCards[selectIndex - 1].getLayoutParams();
                                                    frmFirstCard.width = CARD_WIDTH;
                                                    frmFirstCard.height = CARD_HEIGHT;
                                                    frmFirstCard.topMargin = CARD_WIDTH * 30 / 190;
                                                    frmFirstCard.bottomMargin = 0;
                                                    ivMyCards[selectIndex - 1].setLayoutParams(frmFirstCard);
                                                }
                                                if (selectIndex + 1 < cardLength) {
                                                    frmFirstCard = (LayoutParams) ivMyCards[selectIndex + 1].getLayoutParams();
                                                    frmFirstCard.width = CARD_WIDTH;
                                                    frmFirstCard.height = CARD_HEIGHT;
                                                    frmFirstCard.topMargin = CARD_WIDTH * 30 / 190;
                                                    frmFirstCard.bottomMargin = 0;
                                                    ivMyCards[selectIndex + 1].setLayoutParams(frmFirstCard);
                                                }
                                                if (ivMyCards[i] != iSelect) {
                                                    frm.width = (int) (CARD_WIDTH * 1.2f);
                                                    frm.height = (int) (CARD_HEIGHT * 1.2f);
                                                    //  frm.bottomMargin = getheight(10);
                                                    selectedCard.add(ivMyCards[i]);
                                                    ivMyCards[i].setLayoutParams(frm);
                                                    LayoutParams frm1;
                                                    if (i - 1 >= 0) {
                                                        frm1 = (LayoutParams) ivMyCards[i - 1].getLayoutParams();
                                                        frm1.width = (int) (CARD_WIDTH * 1.1f);
                                                        frm1.height = (int) (CARD_HEIGHT * 1.1f);
                                                        //frm1.bottomMargin = getheight(10);
                                                        ivMyCards[i - 1].setLayoutParams(frm1);
                                                    }
                                                    if (i + 1 < cardLength) {
                                                        frm1 = (LayoutParams) ivMyCards[i + 1].getLayoutParams();
                                                        frm1.width = (int) (CARD_WIDTH * 1.1f);
                                                        frm1.height = (int) (CARD_HEIGHT * 1.1f);
                                                        //frm1.bottomMargin = getheight(10);
                                                        ivMyCards[i + 1].setLayoutParams(frm1);
                                                    }
                                                }
                                            /*w = getwidth(120);
                                            FrameLayout.LayoutParams frm1 = (FrameLayout.LayoutParams) ivMyCards[i - 1].getLayoutParams();
                                            frm1.width = w;
                                            frm1.height = w * 163 / 120;
                                            ivMyCards[i - 1].setLayoutParams(frm1);
                                            frm1 = (FrameLayout.LayoutParams) ivMyCards[i + 1].getLayoutParams();
                                            frm1.width = w;
                                            frm1.height = w * 163 / 120;
                                            ivMyCards[i + 1].setLayoutParams(frm1);*/
                                            } else {
                                            /*w = getwidth(138);
                                            h = w * 187 / 138;
                                            frm.width = w;
                                            frm.height = h;*/
                                                frm.width = (int) (CARD_WIDTH * 1.2f);
                                                frm.height = (int) (CARD_HEIGHT * 1.2f);
                                                //  frm.bottomMargin = getheight(10);
                                                ivMyCards[i].setLayoutParams(frm);
                                                selectedCard.add(ivMyCards[i]);
                                                LayoutParams frm1;
                                                if (i - 1 >= 0) {
                                                    frm1 = (LayoutParams) ivMyCards[i - 1].getLayoutParams();
                                                    frm1.width = (int) (CARD_WIDTH * 1.1f);
                                                    frm1.height = (int) (CARD_HEIGHT * 1.1f);
                                                    //frm1.bottomMargin = getheight(10);
                                                    ivMyCards[i - 1].setLayoutParams(frm1);
                                                }
                                                if (i + 1 < cardLength) {
                                                    frm1 = (LayoutParams) ivMyCards[i + 1].getLayoutParams();
                                                    frm1.width = (int) (CARD_WIDTH * 1.1f);
                                                    frm1.height = (int) (CARD_HEIGHT * 1.1f);
                                                    //frm1.bottomMargin = getheight(10);
                                                    ivMyCards[i + 1].setLayoutParams(frm1);
                                                }
                                            }
                                            Logger.print("Selected Card => " + selectedCard.toString());
                                        }
                                    }
                                } else {
                                    Logger.print("final else condition");
                                    reDrawUserCards("");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                return true;
                            }
                            /*if (selectedCard.size() <= 1) {
                                btngroup.setVisibility(View.GONE);
                            }*/
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            /*if (c.isAutoSortOn) {
                                return false;
                            }*/
                            if (SystemClock.elapsedRealtime() - startTime > 200) {
                                ivMyCards[k].setRotation(0);
                                int cardSize = card_BottomUser.size();
                                //int top1 = (int) event.getRawY() + (v.getHeight() / 2);
                                int left1 = (int) ((int) event.getRawX() - v.getWidth() / 1.5);
                                if (cardSize <= 8)
                                    left1 = left1 - getwidth(140);
                                int w = CARD_WIDTH;
                                int h = CARD_HEIGHT;
                                try {
                                    for (int i = 0; i < cardSize; i++) {
                                        LayoutParams frm = ((LayoutParams) ivMyCards[i].getLayoutParams());
                                        frm.width = w;
                                        frm.height = h;
                                        /*frm.topMargin = h * 5 / 163;
                                        frm.bottomMargin = 0;*/
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    for (int p = 0; p < cardSize; p++) {
                                        if (left1 > posX[cardSize - 1]) {
                                            try {
                                                if (k != cardSize - 1) {
                                                    Logger.print("_CHECKING55555555555555555555555");
                                                    LayoutParams fm = (LayoutParams) ivMyCards[cardSize - 1].getLayoutParams();
                                                    fm.leftMargin = posX[cardSize - 2] - getwidth(140);
                                                    /*fm.topMargin = h * 5 / 163;
                                                    fm.bottomMargin = 0;*/
                                                    fm.width = w;
                                                    fm.height = h;
                                                } else {
                                                    Logger.print("_CHECKING66666666666666666666666");
                                                    LayoutParams fm = (LayoutParams) ivMyCards[cardSize - 2].getLayoutParams();
                                                    fm.leftMargin = posX[cardSize - 2] - getwidth(140);
                                                    /*fm.topMargin = h * 5 / 163;
                                                    fm.bottomMargin = 0;*/
                                                    fm.width = w;
                                                    fm.height = h;
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            tempK = cardSize - 1;
                                        } else if (left1 > posX[p] && p < cardSize - 1 && left1 < posX[p + 1]) {
                                            int f = p;
                                            int s = p + 1;
                                            for (int q = 0; q < cardSize; q++) {
                                                if (q < f && !(s <= q)) {
                                                    if (q >= k) {
                                                        Logger.print("_CHECKING11111111111111111111111");
                                                        LayoutParams fm = (LayoutParams) ivMyCards[q + 1].getLayoutParams();
                                                        fm.leftMargin = posX[q] - getwidth(140);
                                                        /*fm.topMargin = h * 5 / 163;
                                                        fm.bottomMargin = 0;*/
                                                        fm.width = w;
                                                        fm.height = h;
                                                        tempK = f + 1;
                                                    } else {
                                                        Logger.print("_CHECKING22222222222222222222222 : " + f + " , " + s);
                                                        if (f == 1) {
                                                            LayoutParams fm = (LayoutParams) ivMyCards[0].getLayoutParams();
                                                            fm.leftMargin = posX[0] - getwidth(140);
                                                            fm.width = w;
                                                            fm.height = h;
                                                            /*fm.topMargin = h * 5 / 163;
                                                            fm.bottomMargin = 0;*/
                                                            tempK = f;
                                                        } else {
                                                            LayoutParams fm = (LayoutParams) ivMyCards[q + 1].getLayoutParams();
                                                            fm.leftMargin = posX[q + 1] - getwidth(140);
                                                            fm.width = w;
                                                            fm.height = h;
                                                            /*fm.topMargin = h * 5 / 163;
                                                            fm.bottomMargin = 0;*/
                                                            tempK = f;
                                                        }
                                                    }
                                                } else if (s > q) {
                                                    if (q < k) {
                                                        Logger.print("_CHECKING3333333333333333333333333 : " + f + " , " + s);
                                                        Logger.print("_CHANGE_POS_2 i : " + k);
                                                        LayoutParams fm = (LayoutParams) ivMyCards[q].getLayoutParams();
                                                        fm.leftMargin = posX[q + 1] - getwidth(140);
                                                        fm.width = w;
                                                        fm.height = h;
                                                        /*fm.topMargin = h * 5 / 163;
                                                        fm.bottomMargin = 0;*/
                                                        tempK = f;
                                                    } else {
                                                        Logger.print("_CHECKING44444444444444444444444444 : " + f + " , " + s);
                                                        Logger.print("_CHANGE_POS_2 i : " + k + " posX[1] => " + posX[1]);
                                                       /*if (f == 0) {
                                                            FrameLayout.LayoutParams fm = (FrameLayout.LayoutParams) ivMyCards[0].getLayoutParams();
                                                            fm.leftMargin = posX[1] - getwidth(140);
                                                            tempK = f;
                                                        } else {*/
                                                        LayoutParams fm = (LayoutParams) ivMyCards[q + 1].getLayoutParams();
                                                        fm.leftMargin = posX[q + 1] - getwidth(140);
                                                        fm.width = w;
                                                        fm.height = h;
                                                       /* fm.topMargin = h * 5 / 163;
                                                        fm.bottomMargin = 0;*/
                                                        tempK = f + 1;
                                                        // }
                                                    }
                                                }
                                            }
                                            int w1 = CARD_WIDTH;
                                            int h1 = CARD_HEIGHT;
                                            if (tempK >= 1) {
                                                LayoutParams fm = (LayoutParams) ivMyCards[tempK - 1].getLayoutParams();
                                                // fm.leftMargin = posX[f] - getwidth(140);
                                                fm.width = (int) (w1 * 1.1f);
                                                fm.height = (int) (h1 * 1.1f);
                                                /*fm.topMargin = h1 * 5 / 163;
                                                fm.bottomMargin = 0;*/
                                            }
                                            if (tempK >= 0) {
                                                LayoutParams fm1 = (LayoutParams) ivMyCards[tempK].getLayoutParams();
                                                //  fm1.leftMargin = posX[s] - getwidth(140);
                                                fm1.width = (int) (w1 * 1.1f);
                                                fm1.height = (int) (h1 * 1.1f);
                                                /*fm1.topMargin = h1 * 5 / 163;
                                                fm1.bottomMargin = 0;*/
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    ivMyCards[k].bringToFront();
                                    Logger.print("ccccccccc tempK => " + tempK + " k => " + k + " cardSize => " + cardSize);
                                    for (int i = tempK; i < ivMyCards.length; i++) {
                                        if (i != k)
                                            ivMyCards[i].bringToFront();
                                    }
                                    Logger.print("ccccccccc posX[tempK] => " + posX[tempK] + " posX[cardSize - 1] => " + posX[cardSize - 1]);
                                    if (left1 > posX[cardSize - 1]) {
                                        ivMyCards[k].bringToFront();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                isMove = true;
                                Logger.print("=>>>>>>>>>>>>>> " + X + " " + Y + " " + _xDelta + " " + _yDelta);
                                LayoutParams layoutParams = (LayoutParams) v.getLayoutParams();
                                //layoutParams = new LayoutParams(v.getLayoutParams());
                                int w3 = (int) (CARD_WIDTH * 1.2f);
                                int h3 = (int) (CARD_HEIGHT * 1.2f);
                                try {
                                    if (X > posX[cardSize - 1] + getwidth(280) && (cardSize >= 10)) {
                                        layoutParams.leftMargin = ((posX[cardSize - 1]) + getwidth(210)) - _xDelta;
                                        layoutParams.topMargin = Y - _yDelta;
                                    } else if (X > posX[cardSize - 1] + getwidth(420) && (cardSize >= 6)) {
                                        layoutParams.leftMargin = ((posX[cardSize - 1]) + getwidth(420)) - _xDelta;
                                        layoutParams.topMargin = Y - _yDelta;
                                    } else if (X < posX[0]) {
                                        layoutParams.leftMargin = (posX[0]) - _xDelta;
                                        layoutParams.topMargin = Y - _yDelta;
                                    } else {
                                        layoutParams.leftMargin = X - _xDelta;
                                        layoutParams.topMargin = Y - _yDelta;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                //layoutParams.bottomMargin = 0;
                                layoutParams.gravity = Gravity.NO_GRAVITY;
                                layoutParams.width = w3;
                                layoutParams.height = h3;
                                /*layoutParams.bottomMargin = 0;*/
                                v.setLayoutParams(layoutParams);
                            }
                            return true;
                    }
                    break;
                }
            }
            return false;
        }
    };
    private ArrayList<String> PileCards = new ArrayList<>();
    private boolean dontAllowToClickOnCloseDeck = false;
    private NotificationManager notificationManager;
    private Timer mTimer1;
    private Handler mTimerHandler = new Handler();
    private long milliLeft;
    //===============================================================================
    private boolean isGameComeFromPauseMode = false;
    private ImageView ivCardForDistribution9;

    public static void showAds() {
        Logger.print("GETTTTTT ADDDDDDDDDDDDDDDDD Winner class C class");
        try {
            adRequest = new AdRequest.Builder()
                    .build();
            mInterstitial.loadAd(adRequest);
            mInterstitial.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    try {
                        requestNewInterstitial();
                        if (RoundWinner.handler != null) {
                            RoundWinner.handler.sendEmptyMessage(ResponseCodes.AdsClolse);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void requestNewInterstitial() {
        try {
            if (mInterstitial != null) {
                if (adRequest == null) {
                    adRequest = new AdRequest.Builder()
                            //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                            .build();
                }
                mInterstitial.loadAd(adRequest);
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    private static HashMap sortByValues(HashMap map) {
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        // Here I am copying the sorted list in HashMap
        // using LinkedHashMap to preserve the insertion order
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    //    private long lostChip;
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Logger.print("MMMMMMMMMMMMMMM>>> onRestoreInstanceState ::::Called");
        Logger.print("MMMMMMMMMMMMMMM>>> Background issue Genrated 2222...");
        if (isUsernearToWin()) {
            PreferenceManager.setChips(PreferenceManager.getChips() + PreferenceManager.getBootvalue());
        }

        BonustimerPause();
        finish();
        Intent i = getBaseContext().getPackageManager().
                getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        super.onRestoreInstanceState(savedInstanceState);
    }

    private boolean isUsernearToWin() {
        if (PreferenceManager.getRoundNumber() > 0) {
            if (PreferenceManager.getNumberOfPlayers() > 2) {
                return (PreferenceManager.getBottomUSerScore() >= PreferenceManager.getrightUSerScore()) && (PreferenceManager.getBottomUSerScore() >= PreferenceManager.getleftUSerScore());
            } else if (PreferenceManager.getNumberOfPlayers() <= 2) {
                return PreferenceManager.getBottomUSerScore() >= PreferenceManager.getrightUSerScore();
            }
        } else {
            return true;
        }
        return false;
    }

    protected void LoadTableList() {
        TableData = new ArrayList<>();
        try {
            String stadata = loadJSONFromAssettable();
            JSONObject obj = new JSONObject(stadata);
            JSONArray array = obj.getJSONArray("data");
            if (array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    Item_Table item = new Item_Table(array.getJSONObject(i));
                    TableData.add(item);
                }
            }
            tableinfo = TableData.get(c.tableindex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String loadJSONFromAssettable() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("tutorial.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_playscreen2);
        Logger.print("MMMMMMMMMMMMMMM>>> >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        c = C.getInstance();
        c.gameCount = 0;
        c.lostChip = 0;
        Logger.print(">>>> LOST CHIP 1111 >>> " + c.lostChip);
        // Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler(this));
        if (c.width == 0 || c.height == 0) {
            Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler(this));
        }
        String mode = "DashBoard";
        if (PreferenceManager.getgameType() == 0) {
            mode = "DashBoard";
        } else if (PreferenceManager.getgameType() == 1) {
            mode = "Play On Table";
        } else if (PreferenceManager.getgameType() == 2) {
            mode = "how To Play";
        }
        LoadTableList();
        sqLiteManager = new SQLiteManager(getApplicationContext());
        Screen_Hight = c.height;
        Screen_Width = c.width;
        mInterstitial = new InterstitialAd(this);
        mInterstitial.setAdUnitId("ca-app-pub-4109577825364690/7427397966");/*this.getResources().getString(R.string.interstitial_ads_id)*/
        c.exitfromplaying = PreferenceManager.getexitfromplaying();

        up = AnimationUtils.loadAnimation(this, R.anim.slide_in_top2);
        Down = AnimationUtils.loadAnimation(this, R.anim.slide_down2);
        PreferenceManager.setexitfromplaying(true);
        c.exitfromplaying = PreferenceManager.getexitfromplaying();
        PreferenceManager.setChipsBeforePlay(PreferenceManager.getChips());
        c.ChipBeforePlay = PreferenceManager.getChipsBeforePlay();
        numberOfPlayers = PreferenceManager.getNumberOfPlayers();
        PreferenceManager.setNumberOfPlayers(numberOfPlayers);
        isGameComeFromPauseMode = false;
        Music_Manager m1 = new Music_Manager(this);
        findViewIds1();
        setScreen();
        initHandler();
        LoadStuff();
        String gametype = "Gin Rummy";
        if (c.gameType == 0) {
            gametype = "Gin Rummy";

            ivGinRummyText.setBackgroundResource(0);
        } else if (c.gameType == 1) {
            gametype = "Straight Gin";

            ivGinRummyText.setBackgroundResource(0);
            tvdeadwoodScore2.setVisibility(View.GONE);
        } else if (c.gameType == 2) {
            gametype = "Hollywood Gin";

            ivGinRummyText.setBackgroundResource(0);
        } else if (c.gameType == 3) {
            gametype = "Oklahoma Gin";

            ivGinRummyText.setBackgroundResource(0);
        }

        setGTI();

        PreferenceManager.isPlayingScreenOpen = true;
        c.isPlayingScreenOpen = true;
        messageHandler = new Handler();
        showAds();

        final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                // get the crash info
                Logger.print("<><><><><><><><><><><><><><><><><>PlaYING SCREEN<><><><><><><><><><><><><><><><><><><><><><>");
                if (!PlayScreen2.this.isFinishing()) {

                    BonustimerPause();
                    PlayScreen2.this.finish();

                }
                defaultHandler.uncaughtException(thread, ex);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = ResponseCodes.ShowPlayButton;
                if (Dashboard.handler != null) {
                    Dashboard.handler.sendMessage(msg);
                }
            }
        }, 3000);
        // ============ New Bonush timer ==========
        if (c.BonusmilliLeft == 0) {
            BonustimerStart(time[PreferenceManager.getBonusTimerCount()]);
        } else {
            BonustimerResume();
        }

        if (!is3danimation) {
            frmtable.setRotationX(35);
            frmDeckBackGround.setRotationX(25);
        }


    }

    private void getRange() {
        playingUsers = sqLiteManager.getPlayersList(start, end);
        Collections.shuffle(playingUsers);
    }

    private void setGTI() {
        if (numberOfPlayers == 3) {
            BottomSeatIndex = 0;
            LeftSeatIndex = 1;
            RightSeatIndex = 2;
        } else {
            BottomSeatIndex = 0;
            RightSeatIndex = 1;
            LeftSeatIndex = 2;
        }
        scoreboardData[BottomSeatIndex] = new ScoreboardData();
        scoreboardData[LeftSeatIndex] = new ScoreboardData();
        scoreboardData[RightSeatIndex] = new ScoreboardData();
        playingUsers = sqLiteManager.getPlayersList(start, end);
        myMap = sqLiteManager.getUserData(PreferenceManager.get_id());
        if (myMap.get("played") != null)
            totalGameCounter = Integer.parseInt(myMap.get("played"));
//        Logger.print("Game played by User => " + totalGameCounter);
        Collections.shuffle(playingUsers);
//        Logger.print("><><><><><><><" + playingUsers.size());
        if (playingUsers.size() < 2) {
            end = end + 5000;
            getRange();
        } else {
            players[RightSeatIndex] = new Player_UserInfo();
            players[RightSeatIndex].setUsername(getProUserName(playingUsers.get(0).get(c.NAME)));
            int index = Integer.valueOf(playingUsers.get(0).get(c.IMAGE)) - 1;
            if (index > 119) {
                index = 118;
            }
            players[RightSeatIndex].setProfilePicture(c.leaderImage[index]);

            players[RightSeatIndex].setUserId(playingUsers.get(0).get(c.ID));
            ivUserNameTwo.setText(players[RightSeatIndex].getUsername());
            ivProfileImageTwo.setImageResource(players[RightSeatIndex].getProfilePicture());
            tvScoreTwo.setText("0");
            if (numberOfPlayers == 3) {
                players[LeftSeatIndex] = new Player_UserInfo();
                players[LeftSeatIndex].setUsername(getProUserName(playingUsers.get(1).get(c.NAME)));
                int index2 = Integer.valueOf(playingUsers.get(1).get(c.IMAGE)) - 1;
                if (index2 > 119) {
                    index2 = 114;
                }
                try {
                    players[LeftSeatIndex].setProfilePicture(c.leaderImage[index2]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                players[LeftSeatIndex].setUserId(playingUsers.get(1).get(c.ID));
                ivUserNameOne.setText(players[LeftSeatIndex].getUsername());
                ivProfileImage.setImageResource(players[LeftSeatIndex].getProfilePicture());
                tvScore.setText("0");
            }
        }
//        Logger.print("MMMMMMMMMMMMMMMMM>>>>>" + playingUsers.toString());
        players[BottomSeatIndex] = new Player_UserInfo();
        players[BottomSeatIndex].setUsername(getProUserName(PreferenceManager.getUserName()));
        players[BottomSeatIndex].setProfilePicture(PreferenceManager.getprofilepic());
        players[BottomSeatIndex].setUserId(PreferenceManager.get_id());
        ivUserNameThree.setText(players[BottomSeatIndex].getUsername());
        tvScoreThree.setText("0");
        if (PreferenceManager.get_picPath().length() > 0) {
            try {
                Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                ivProfileImageThree.setImageDrawable(d);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ivProfileImageThree.setImageResource(PreferenceManager.getprofilepic());
        }
        BOOT_VALUE = (int) PreferenceManager.getBootvalue();

        GAME_POINT = (int) PreferenceManager.getPlayerPoints(c.gameType, PreferenceManager.getNumberOfPlayers());

        PreferenceManager.setpoints(GAME_POINT);
        ROUND_NUMBER = 0;

        tvPoints.setText(GAME_POINT + "");
        tvbootAmount.setText(c.getNumberFormatedValue(BOOT_VALUE));

        RoundTimerStartProcess(5, true);
    }

    public void Disableautosort() {
        tglSorting.setAlpha(.5f);
        tglSorting.setEnabled(false);
        tglAutoSorting.setAlpha(.5f);
        tglAutoSorting.setEnabled(false);
    }

    public void Enableautosort() {
        tglSorting.setAlpha(1.0f);
        tglSorting.setEnabled(true);
        tglAutoSorting.setAlpha(1.0f);
        tglAutoSorting.setEnabled(true);
    }

    private void startNewGame() {
        Logger.print("start new game called");
        iv_2X.setVisibility(View.GONE);
        ROUND_NUMBER = 0;
        PreferenceManager.setRoundNumber(ROUND_NUMBER);
        PreferenceManager.setBottomUSerScore(0);
        PreferenceManager.setrightUSerScore(0);
        PreferenceManager.setleftUSerScore(0);
        totalGameCounter++;
        ivScoreboard.setVisibility(View.INVISIBLE);
        scoreboardData[BottomSeatIndex] = new ScoreboardData();
        scoreboardData[RightSeatIndex] = new ScoreboardData();
        scoreboardData[LeftSeatIndex] = new ScoreboardData();
        sqLiteManager.incrementGamePlayed(PreferenceManager.get_id());
        if (c.gameType == 0) {
            PreferenceManager.SetGinPlayed(PreferenceManager.GetGinPlayed() + 1);
        } else if (c.gameType == 1) {
            PreferenceManager.SetStraightPlayed(PreferenceManager.GetStraightPlayed() + 1);
        } else if (c.gameType == 3) {
            PreferenceManager.SetOklahomaPlayed(PreferenceManager.GetOklahomaPlayed() + 1);
        } else if (c.gameType == 2) {
            PreferenceManager.SetHollyWoodPlayed(PreferenceManager.GetHollyWoodPlayed() + 1);
        }
        Dashboard.savedGamesUpdate();
        if (numberOfPlayers == 3) {
            Arrays.fill(DealCardToSeat, Boolean.TRUE);
        } else {
            Arrays.fill(DealCardToSeat, Boolean.FALSE);
            DealCardToSeat[BottomSeatIndex] = true;
            DealCardToSeat[RightSeatIndex] = true;
        }
        senddata = new JSONObject();
        Disableautosort();

        if (PreferenceManager.getChips() >= BOOT_VALUE) {
            messageHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Logger.print(">>>>>>>>>>>1111111111111111");
                    Logger.print(">>>>>>>>>>>PreferenceManager.getChips() " + PreferenceManager.getChips());
                    if (c.isPlayingScreenOpen) {
                        Logger.print(">>>>>>>>>>>2222222222222222");
                        collectBootValueAnimation(DealCardToSeat);

                    }
                }
            }, 500);
            startRound();
        } else {
            c.showMessageOnDashboard = true;
            c.errorCode = C.NO_CHIPS;
            BonustimerPause();
            finish();

        }
    }

    private void startRound() {
        Logger.print(">>>>>> TWO X PROBLM >>>> startRound() CALLED ");
        Disableautosort();
        NotificationText.setVisibility(View.GONE);

        iv_2X.setVisibility(View.GONE);
        c.isDouble = false;
        c.multi = 1;
        BottomDeadWood = 0;
        RightDeadWood = 0;
        LeftDeadWood = 0;
        PileCardCounter = 52;
        User_turn_cnt = 0;
        isCardPicked = false;

        if (!isDraw) {
            ROUND_NUMBER++;
            PreferenceManager.setRoundNumber(ROUND_NUMBER);
        } else {
            isDraw = false;
        }
        tvRoundNo.setText("" + ROUND_NUMBER);
        if (senddata.length() > 0) {
            ivScoreboard.setVisibility(View.VISIBLE);
        } else {
            ivScoreboard.setVisibility(View.INVISIBLE);
        }
        CURRENT_USER_TURN = 0;
        GAME_OVER = false;
        messageHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                StartDealingProcess();
            }
        }, 2000);
        if (is3danimation) {
            messageHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (GameisOn()) {
                        ObjectAnimator animation = ObjectAnimator.ofFloat(frmtable, "rotationX", 0f, 35f);
                        animation.setDuration(2000);
                        animation.setRepeatCount(0);
                        animation.setInterpolator(new LinearInterpolator());
                        animation.start();
                        ObjectAnimator animation2 = ObjectAnimator.ofFloat(frmDeckBackGround, "rotationX", 0f, 25f);
                        animation2.setDuration(2000);
                        animation2.setRepeatCount(0);
                        animation2.setInterpolator(new LinearInterpolator());
                        animation2.start();
                        ivGinRummyText.startAnimation(Down);
                    }
                }
            }, 11000);
        }
        int dur = 9000;
        if (is3danimation) {
            dur = 11000;
        }
        messageHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                INITIAL_USER_TURN = CURRENT_USER_TURN = new Random().nextInt(numberOfPlayers);

                ivGinRummyText.setVisibility(View.VISIBLE);
                frmtable.setRotationX(35);
                frmDeckBackGround.setRotationX(25);
                JSONObject jsonObject = new JSONObject();
                JSONObject jsonObjectData = new JSONObject();
                try {
                    jsonObject.put(Parameters.NextTurn, CURRENT_USER_TURN);
                    if (openBunchCard.size() > 0) {
                        jsonObject.put(Parameters.PileCard, getCardString(openBunchCard.get(0)));
                    }
                    jsonObjectData.put(Parameters.data, jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Enableautosort();
                isFirstTurn = true;
                if (c.isPlayingScreenOpen) {
                    UserTurnStartedProcess(jsonObjectData.toString());
                }
            }
        }, dur);
    }

    private void callUserTurnStart() {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectData = new JSONObject();
        try {
            if (!isCardPicked && User_turn_cnt > 0) {
                if (INITIAL_USER_TURN == CURRENT_USER_TURN) {
                    jsonObject.put(Parameters.Passturntype, "OPST");
                } else {
                    jsonObject.put(Parameters.Passturntype, "PST");
                }
            }
            jsonObject.put(Parameters.NextTurn, CURRENT_USER_TURN);
            jsonObject.put(Parameters.PrevTurn, PREVIOUS_USER_TURN);
            if (openBunchCard.size() > 0) {
                jsonObject.put(Parameters.PileCard, getCardString(openBunchCard.get(0)));
            }

            jsonObjectData.put(Parameters.data, jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Logger.print("USerTurnStarted =>>>>>>>>>>>>>>>>>>>>>>>> " + jsonObjectData.toString());
        UserTurnStartedProcess(jsonObjectData.toString());
    }

    private void passTurn() {
        if (numberOfPlayers == 3) {
            if (CURRENT_USER_TURN == 2) {
                CURRENT_USER_TURN = 0;
                PREVIOUS_USER_TURN = 2;
            } else {
                PREVIOUS_USER_TURN = CURRENT_USER_TURN;
                CURRENT_USER_TURN++;
            }
        } else {
            if (CURRENT_USER_TURN == 1) {
                CURRENT_USER_TURN = 0;
                PREVIOUS_USER_TURN = 1;
            } else {
                PREVIOUS_USER_TURN = CURRENT_USER_TURN;
                CURRENT_USER_TURN++;
            }
        }
        Logger.print("CURRENT_USER_TURN =>>> " + CURRENT_USER_TURN);
        callUserTurnStart();
    }

    // <<<--- Number increment animation on Collection of Boot Value --->>>
    private void IncrementCounterChip(int end) {

        bootValueAnimationHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTotalBetValue.setText("" + c.getNumberFormatedValue(BOOT_VALUE * numberOfPlayers));
                    }
                });
            }
        }, 1000);
    }

    private void LoadStuff() {
        handAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.handpointer);
        FromLeft = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_in_right1);
        ToLeft = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_in_left1);
        animUserRing = AnimationUtils
                .loadAnimation(getApplicationContext(),
                        R.anim.profile_progress_ring_scale);
    }

    private void findViewIds1() {
        // TODO Auto-generated method stub
        SetNewTimerScreenId();
        CARD_WIDTH = getwidth(140);
        CARD_HEIGHT = CARD_WIDTH * 190 / 140;
        tf = Typeface.createFromAsset(getAssets(),
                "font/PoetsenOne-Regular.ttf");
        iv_2X = findViewById(R.id.iv_2X);
        iv_2X.setVisibility(View.GONE);
        ivScore = findViewById(R.id.ivScore);
        ivScore_knock = findViewById(R.id.ivScore_knock);
        tvdeadwoodlbl = findViewById(R.id.tvdeadwoodlbl);
        tvdeadwoodScore = findViewById(R.id.tvdeadwoodScore);
        tvdeadwoodScore2 = findViewById(R.id.tvdeadwoodScore2);
        tvdeadwoodlbl.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(22));
        tvdeadwoodlbl.setTypeface(tf);
        tv_knock_for = findViewById(R.id.tv_knock_for);
        tv_knock_for.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(22));
        tv_knock_for.setTypeface(tf);
        tv_knock_score = findViewById(R.id.tv_knock_score);
        tv_knock_score.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tv_knock_score.setTypeface(tf);
        tvdeadwoodScore.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvdeadwoodScore.setTypeface(tf);
        tvdeadwoodScore.setPadding(0, getheight(2), 0, 0);
        tvdeadwoodScore2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvdeadwoodScore2.setTypeface(tf);
        tvdeadwoodScore2.setPadding(0, getheight(2), 0, 0);
        frmtable = findViewById(R.id.frmtable);
        frmTemp11 = findViewById(R.id.frmTemp11);
        frmTemp12 = findViewById(R.id.frmTemp11);
        ivGinRummyText = findViewById(R.id.ivGinRummyText);
        ivGinRummyText.setVisibility(View.INVISIBLE);
        buttonPressed = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.buttonpressed);
        heartBeatAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.heartbeat);
        tvbootAmount = findViewById(R.id.tvbootAmount);
        tvbootAmount.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        tvbootAmount.setTypeface(tf, Typeface.BOLD);
        TextView tvbootAmountText = findViewById(R.id.tvbootAmountText);
        tvbootAmountText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        tvbootAmountText.setTypeface(tf, Typeface.BOLD);
        llThemesWrapper = findViewById(R.id.llThemesWrapper);
        llThemesWrapper.setOnClickListener(this);
        TextView tvTheme = findViewById(R.id.tvTheme);
        //  tvTheme.setOnClickListener(this);
        tvTheme.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvTheme.setTypeface(tf, Typeface.BOLD);
        TextView tvAutoSort = findViewById(R.id.tvAutoSort);
        tvAutoSort.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvAutoSort.setTypeface(tf, Typeface.BOLD);
        tvAutosortOnOff = findViewById(R.id.tvAutosortOnOff);
        tvAutosortOnOff.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvAutosortOnOff.setTypeface(tf, Typeface.BOLD);
        frmDeadwoodContainer = findViewById(R.id.frmDeadwoodContainer);
        frmDeadwoodContainer.setVisibility(View.INVISIBLE);
        //frmDeadwoodContainer = (FrameLayout) findViewById(R.id.frmDeadwoodContainer);
        ivScore.setVisibility(View.INVISIBLE);
        ivScore_knock.setVisibility(View.INVISIBLE);

        ivMyCards[0] = findViewById(R.id.ivCard1);
        ivMyCards[1] = findViewById(R.id.ivCard2);
        ivMyCards[2] = findViewById(R.id.ivCard3);
        ivMyCards[3] = findViewById(R.id.ivCard4);
        ivMyCards[4] = findViewById(R.id.ivCard5);
        ivMyCards[5] = findViewById(R.id.ivCard6);
        ivMyCards[6] = findViewById(R.id.ivCard7);
        ivMyCards[7] = findViewById(R.id.ivCard8);
        ivMyCards[8] = findViewById(R.id.ivCard9);
        ivMyCards[9] = findViewById(R.id.ivCard10);
        ivMyCards[10] = findViewById(R.id.ivCard11);
        ivLeftRobotCard[0] = findViewById(R.id.ivCardRL1);
        ivLeftRobotCard[1] = findViewById(R.id.ivCardRL2);
        ivLeftRobotCard[2] = findViewById(R.id.ivCardRL3);
        ivLeftRobotCard[3] = findViewById(R.id.ivCardRL4);
        ivLeftRobotCard[4] = findViewById(R.id.ivCardRL5);
        ivLeftRobotCard[5] = findViewById(R.id.ivCardRL6);
        ivLeftRobotCard[6] = findViewById(R.id.ivCardRL7);
        ivLeftRobotCard[7] = findViewById(R.id.ivCardRL8);
        ivLeftRobotCard[8] = findViewById(R.id.ivCardRL9);
        ivLeftRobotCard[9] = findViewById(R.id.ivCardRL10);
        ivLeftRobotCard[10] = findViewById(R.id.ivCardRL11);
        ivRightRobotCard[0] = findViewById(R.id.ivCardRR1);
        ivRightRobotCard[1] = findViewById(R.id.ivCardRR2);
        ivRightRobotCard[2] = findViewById(R.id.ivCardRR3);
        ivRightRobotCard[3] = findViewById(R.id.ivCardRR4);
        ivRightRobotCard[4] = findViewById(R.id.ivCardRR5);
        ivRightRobotCard[5] = findViewById(R.id.ivCardRR6);
        ivRightRobotCard[6] = findViewById(R.id.ivCardRR7);
        ivRightRobotCard[7] = findViewById(R.id.ivCardRR8);
        ivRightRobotCard[8] = findViewById(R.id.ivCardRR9);
        ivRightRobotCard[9] = findViewById(R.id.ivCardRR10);
        ivRightRobotCard[10] = findViewById(R.id.ivCardRR11);
        groupBgLinear = findViewById(R.id.groupBgLinear);
        llExitToLobbyWrapper = findViewById(R.id.llExitToLobbyWrapper);
        llExitToLobbyWrapper.setOnClickListener(this);
        llSwitchTableWrapper = findViewById(R.id.llSwitchTableWrapper);
        llSwitchTableWrapper.setOnClickListener(this);
        llTableInfoWrapper = findViewById(R.id.llTableInfoWrapper);
        llTableInfoWrapper.setOnClickListener(this);
        llScoreboardWrapper = findViewById(R.id.llScoreboardWrapper);
        llScoreboardWrapper.setOnClickListener(this);
        llControllsWrapper = findViewById(R.id.llControllsWrapper);
        llControllsWrapper.setOnClickListener(this);
        for (ImageView ivMyCard : ivMyCards) {
            ivMyCard.setOnTouchListener(cardTouch);
            ivMyCard.setOnLongClickListener(CardlongClick);

        }
        tglSorting = findViewById(R.id.tglSortButton);
        tglAutoSorting = findViewById(R.id.tglAutoSortButton22);
        cbAutoSort = findViewById(R.id.cbAutoSort);
        if (isTutorial) {
            tglSorting.setVisibility(View.INVISIBLE);
            tglAutoSorting.setVisibility(View.INVISIBLE);
        }
        c.isAutoSortOn = PreferenceManager.getisAutosort();
        if (PreferenceManager.getisAutosort()) {
            tvAutosortOnOff.setText("Auto Sort On");
            cbAutoSort.setChecked(true);
            tglAutoSorting.setChecked(true);
            PreferenceManager.setisAutosort(true);
        } else {
            tvAutosortOnOff.setText("Auto Sort Off");
            cbAutoSort.setChecked(false);
            tglAutoSorting.setChecked(false);
            PreferenceManager.setisAutosort(false);
        }
        cbAutoSort.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                c.isAutoSortOn = isChecked;
                JSONObject jsonObject = new JSONObject();
                if (c.isAutoSortOn) {
                    if (!tglAutoSorting.isChecked()) {
                        tglAutoSorting.setChecked(true);
                        PreferenceManager.setisAutosort(true);
                    }

                } else {
                    if (tglAutoSorting.isChecked()) {
                        tglAutoSorting.setChecked(false);
                        PreferenceManager.setisAutosort(false);
                    }

                }
            }
        });
        tglAutoSorting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
            }
        });
        tglAutoSorting.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                c.isAutoSortOn = isChecked;
                PreferenceManager.setisAutosort(c.isAutoSortOn);
                JSONObject jsonObject = new JSONObject();
                if (c.isAutoSortOn) {
                    btnDiscard.setVisibility(View.GONE);
                    ivHandOpenCard.setVisibility(View.GONE);
                    ivHandOpenCard.clearAnimation();
                    tvAutosortOnOff.setText("Auto Sort On");

                    if (!cbAutoSort.isChecked()) {
                        cbAutoSort.setChecked(true);
                    }
                    try {

                        jsonObject.put("asc", 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    reDrawUserCards("");
                } else {
                    tvAutosortOnOff.setText("Auto Sort Off");
                    try {
                        if (cbAutoSort.isChecked()) {
                            cbAutoSort.setChecked(false);
                        }
                        jsonObject.put("asc", 0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        tglSorting.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                Logger.print("isSortButtonClickable => " + isSortButtonClickable);
                if (!isSortButtonClickable)
                    return;
                if (btnDiscard.getVisibility() == View.VISIBLE) {
                    if (ivHandOpenCard.getAnimation() != null) {
                        ivHandOpenCard.clearAnimation();
                    }
                    ivHandOpenCard.setVisibility(View.INVISIBLE);
                    btnDiscard.setVisibility(View.GONE);
                }
                if (selectedCard.size() > 0) {
                    selectedCard.clear();
                }
                if (isChecked && card_BottomUser.size() > 0
                        && isSortButtonClickable) {
                    Music_Manager.buttonclick();
                    isDefaultSequencewise = false;
                    tglSorting
                            .setBackgroundResource(R.drawable.sort_cards1);
                    card_BottomUser = new ArrayList<>(sortingTrioandSeq(false));
                    reDrawUserCards("");
                } else if (card_BottomUser.size() > 0
                        && isSortButtonClickable) {
                    Music_Manager.buttonclick();
                    isDefaultSequencewise = true;
                    tglSorting
                            .setBackgroundResource(R.drawable.sort_cards);
                    card_BottomUser = new ArrayList<>(sortingTrioandSeq(false));
                    reDrawUserCards("");
                }
            }
        });
        btnDrawNew = findViewById(R.id.btnDrawNew);
        btnDrawNew.setOnClickListener(this);
        btnDrawNew.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnDrawNew.setPadding(0, getheight(20), 0, 0);
        btnDrawNew.setTypeface(tf);
        btnFaceUp = findViewById(R.id.btnFaceUp);
        btnFaceUp.setOnClickListener(this);
        btnFaceUp.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        btnFaceUp.setTypeface(tf);
        TextView tvColumnOne = findViewById(R.id.tvColumnOne);
        tvColumnOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        TextView tvColumnTwo = findViewById(R.id.tvColumnTwo);
        tvColumnTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        TextView tvColumnThree = findViewById(R.id.tvColumnThree);
        tvColumnThree.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        btnDiscard = findViewById(R.id.btnDiscard);
        btnDiscard.setOnClickListener(this);
        btnDiscard.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        btnDiscard.setTypeface(tf);
        ivBootAnim[0] = findViewById(R.id.iv1);
        ivBootAnim[1] = findViewById(R.id.iv2);
        ivBootAnim[2] = findViewById(R.id.iv3);
        ivBootAnim[3] = findViewById(R.id.iv4);
        ivBootAnim[4] = findViewById(R.id.iv5);
        ivBootAnim[5] = findViewById(R.id.iv6);
        ivBootAnim[6] = findViewById(R.id.iv7);
        frmUserCards = findViewById(R.id.fmuserCards);
        ivCloseCard = findViewById(R.id.ivCloseCard);
        ivCloseCard9 = findViewById(R.id.ivCloseCard9);
        ivCloseCard9.setVisibility(View.INVISIBLE);
        ivCloseCard.setOnClickListener(this);
        ivCloseCard.setClickable(false);
        ivCloseDeck = findViewById(R.id.ivCloseDeck);
        ivCloseDeck9 = findViewById(R.id.ivCloseDeck9);
        ivCloseDeck9.setVisibility(View.INVISIBLE);
        ivCloseDeck.setOnClickListener(this);
        ivCloseDeck.setClickable(false);
        ivBottomUserCard = findViewById(R.id.ivBottomUserCard);
        frmDeckBackGround = findViewById(R.id.frmDeckBackGround);
        frmDeckBackGround9 = findViewById(R.id.frmDeckBackGround9);
        ivCardOnDeck = findViewById(R.id.ivCardOnDeck);
        ivCardOnDeck9 = findViewById(R.id.ivCardOnDeck9);
        ivCardOnDeck9.setVisibility(View.INVISIBLE);
        ivUserOneCard = findViewById(R.id.ivUserOneCard);
        ivUserTwoCard = findViewById(R.id.ivUserTwoCard);
        ivUserThreeCard = findViewById(R.id.ivUserThreeCard);
        ivCardForDistribution = findViewById(R.id.ivCardForDistribution);
        ivCardForDistribution9 = findViewById(R.id.ivCardForDistribution9);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        ivProfileImage.setOnClickListener(this);
        ivProfileImageTwo = findViewById(R.id.ivProfileImageTwo);
        ivProfileImageTwo.setOnClickListener(this);
        ivProfileImageThree = findViewById(R.id.ivProfileImageThree);
        ivProfileImageThree.setOnClickListener(this);
        NotificationText = findViewById(R.id.notificationText);
        NotificationText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        NotificationText.setTypeface(tf, Typeface.BOLD);
        NotificationText.setVisibility(View.GONE);
        tvTotalBetValue = findViewById(R.id.tvTotalBetValue);
        tvTotalBetValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvTotalBetValue.setTypeface(tf, Typeface.BOLD);
        tvTotalBetValue.setText("");
        ivUserOneThrownCard = findViewById(R.id.ivUserOneThrownCard);
        ivUserTwoThrownCard = findViewById(R.id.ivUserTwoThrownCard);
        ivUserOneThrownCard2 = findViewById(R.id.ivUserOneThrownCard2);
        ivUserTwoThrownCard2 = findViewById(R.id.ivUserTwoThrownCard2);
        ivUserOneThrownCard2.setRotationX(25);
        ivUserTwoThrownCard2.setRotationX(25);
        tvCardCounter = findViewById(R.id.tvCardCounter);
        tvCardCounter.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));
        tvCardCounter.setTypeface(tf, Typeface.BOLD);
        ivGiftIcon = findViewById(R.id.ivGiftIcon);
        ivGiftIcon.setOnClickListener(this);
        tvScore = findViewById(R.id.tvScore);
        tvScore.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(22));
        tvScore.setTypeface(tf, Typeface.BOLD);
        ivGiftIconTwo = findViewById(R.id.ivGiftIconTwo);
        ivGiftIconTwo.setOnClickListener(this);
        tvScoreTwo = findViewById(R.id.tvScoreTwo);
        tvScoreTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(22));
        tvScoreTwo.setTypeface(tf, Typeface.BOLD);
        ivGiftIconThree = findViewById(R.id.ivGiftIconThree);
        ivGiftIconThree.setOnClickListener(this);
        tvScoreThree = findViewById(R.id.tvScoreThree);
        tvScoreThree.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(22));
        tvScoreThree.setTypeface(tf, Typeface.BOLD);
        TextView tvDeadwoodText = findViewById(R.id.tvDeadwoodText);
        tvDeadwoodText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(20));
        tvDeadwoodText.setTypeface(tf, Typeface.BOLD);
        tvUserScore = findViewById(R.id.tvUserScore);
        tvUserScore.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));
        tvUserScore.setTypeface(tf, Typeface.BOLD);
        StandUp_Btn = findViewById(R.id.StandUp_Btn);
        StandUp_Btn.setOnClickListener(this);
        ivUserOneBootChip = findViewById(R.id.ivUserOneBootChip);
        ivUserTwoBootChip = findViewById(R.id.ivUserTwoBootChip);
        ivUserThreeBootChip = findViewById(R.id.ivUserThreeBootChip);
        btnSkip = findViewById(R.id.btnSkip);
        btnSkip.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(40));
        btnSkip.setTypeface(tf, Typeface.BOLD);
        btnSkip.setOnClickListener(this);
        btnSkip.setVisibility(View.INVISIBLE);
        ivSkipUserOne = findViewById(R.id.ivSkipUserOne);
        ivSkipUserOne.setVisibility(View.INVISIBLE);
        ivSkipUserTwo = findViewById(R.id.ivSkipUserTwo);
        ivSkipUserTwo.setVisibility(View.INVISIBLE);
        ivSkipUserThree = findViewById(R.id.ivSkipUserThree);
        ivSkipUserThree.setVisibility(View.INVISIBLE);
        ivTimeOutUserOne = findViewById(R.id.ivTimeOutUserOne);
        ivTimeOutUserOne.setVisibility(View.INVISIBLE);
        ivTimeOutUserTwo = findViewById(R.id.ivTimeOutUserTwo);
        ivTimeOutUserTwo.setVisibility(View.INVISIBLE);
        ivTimeOutUserThree = findViewById(R.id.ivTimeOutUserThree);
        ivTimeOutUserThree.setVisibility(View.INVISIBLE);
        ivKnockUserOne = findViewById(R.id.ivKnockUserOne);
        ivKnockUserOne.setVisibility(View.INVISIBLE);
        ivKnockUserTwo = findViewById(R.id.ivKnockUserTwo);
        ivKnockUserTwo.setVisibility(View.INVISIBLE);
        ivKnockUserThree = findViewById(R.id.ivKnockUserThree);
        ivKnockUserThree.setVisibility(View.INVISIBLE);
        ivGinUserOne = findViewById(R.id.ivGinUserOne);
        ivGinUserOne.setVisibility(View.INVISIBLE);
        ivGinUserTwo = findViewById(R.id.ivGinUserTwo);
        ivGinUserTwo.setVisibility(View.INVISIBLE);
        ivGinUserThree = findViewById(R.id.ivGinUserThree);
        ivGinUserThree.setVisibility(View.INVISIBLE);
        ivGinUserOne2 = findViewById(R.id.ivGinUserOne2);
        ivGinUserOne2.setVisibility(View.INVISIBLE);
        ivGinUserTwo2 = findViewById(R.id.ivGinUserTwo2);
        ivGinUserTwo2.setVisibility(View.INVISIBLE);
        ivGinUserThree2 = findViewById(R.id.ivGinUserThree2);
        ivGinUserThree2.setVisibility(View.INVISIBLE);
        tvPoints = findViewById(R.id.tvPoints);
        tvPoints.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvPoints.setTypeface(tf, Typeface.BOLD);
        tvPoints.setText("");
        TextView tvPointsText = findViewById(R.id.tvPointsText);
        tvPointsText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvPointsText.setTypeface(tf, Typeface.BOLD);
        TextView tvPointsColumn = findViewById(R.id.tvPointsColumn);
        tvPointsColumn.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvPointsColumn.setTypeface(tf, Typeface.BOLD);
        btnKnock = findViewById(R.id.btnKnock);
        btnKnock.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(30));
        btnKnock.setTypeface(tf, Typeface.BOLD);
        btnKnock.setOnClickListener(this);
        btnKnock.setVisibility(View.INVISIBLE);
        btnGin = findViewById(R.id.btnGin);
        btnGin.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(40));
        btnGin.setTypeface(tf, Typeface.BOLD);
        btnGin.setOnClickListener(this);
        btnGin.setVisibility(View.INVISIBLE);
        ivUserNameOne = findViewById(R.id.ivUserNameOne);
        ivUserNameOne.setStroke((float) (Screen_Width * 0.75) / 1280f,
                Color.BLACK);
        ivUserNameOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));
        ivUserNameOne.setTypeface(tf, Typeface.BOLD);
        ivUserNameTwo = findViewById(R.id.ivUserNameTwo);
        ivUserNameTwo.setStroke((float) (Screen_Width * 0.75) / 1280f,
                Color.BLACK);
        ivUserNameTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));
        ivUserNameTwo.setTypeface(tf, Typeface.BOLD);
        ivUserNameThree = findViewById(R.id.ivUserNameThree);
        ivUserNameThree.setStroke((float) (Screen_Width * 0.75) / 1280f,
                Color.BLACK);
        ivUserNameThree.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));
        ivUserNameThree.setTypeface(tf, Typeface.BOLD);
        LeftUserRing = findViewById(R.id.LeftUserRing);
        LeftUserRing.setVisibility(View.INVISIBLE);
        RightUserRing = findViewById(R.id.RightUserRing);
        RightUserRing.setVisibility(View.INVISIBLE);
        BottomUserRing = findViewById(R.id.BottomUserRing);
        BottomUserRing.setVisibility(View.INVISIBLE);
        HelpText = findViewById(R.id.help_text);
        HelpText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        HelpText.setVisibility(View.INVISIBLE);
        HelpText.setTypeface(tf, Typeface.BOLD);
        TextView tvRoundNoText = findViewById(R.id.tvRoundNoText);
        tvRoundNoText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvRoundNoText.setTypeface(tf, Typeface.BOLD);
        TextView tvColumnRound = findViewById(R.id.tvColumnRound);
        tvColumnRound.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvColumnRound.setTypeface(tf, Typeface.BOLD);
        tvRoundNo = findViewById(R.id.tvRoundNo);
        tvRoundNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvRoundNo.setTypeface(tf, Typeface.BOLD);
        frmRightUserOpenCard = findViewById(R.id.frmRightUserOpenCard);
        frmRightUserOpenCard.setVisibility(View.INVISIBLE);
        frmLeftUserOpenCard = findViewById(R.id.frmLeftUserOpenCard);
        frmLeftUserOpenCard.setVisibility(View.INVISIBLE);
        frmMainLayout = findViewById(R.id.frmMain);
        frmMainLayout.setOnClickListener(this);
        ivHandOpenCard = findViewById(R.id.ivHandOpenCard);
        ivHandOpenCard.setVisibility(View.INVISIBLE);
        ivHandCloseDeck = findViewById(R.id.ivHandCloseDeck);
        ivHandCloseDeck.setVisibility(View.INVISIBLE);
        ivGiftIconLtoB = findViewById(R.id.ivGiftIconLtoB);
        ivGiftIconLtoB.setVisibility(View.INVISIBLE);
        ivGiftIconLtoR = findViewById(R.id.ivGiftIconLtoR);
        ivGiftIconLtoR.setVisibility(View.INVISIBLE);
        ivGiftIconThreeBtoR = findViewById(R.id.ivGiftIconThreeBtoR);
        ivGiftIconThreeBtoR.setVisibility(View.INVISIBLE);
        ivGiftIconThreeBtoL = findViewById(R.id.ivGiftIconThreeBtoL);
        ivGiftIconThreeBtoL.setVisibility(View.INVISIBLE);
        ivGiftIconTwoRtoL = findViewById(R.id.ivGiftIconTwoRtoL);
        ivGiftIconTwoRtoL.setVisibility(View.INVISIBLE);
        ivGiftIconTwoRtoB = findViewById(R.id.ivGiftIconTwoRtoB);
        ivGiftIconTwoRtoB.setVisibility(View.INVISIBLE);
        ivScoreboard = findViewById(R.id.ivScoreboard);
        ivScoreboard.setVisibility(View.INVISIBLE);
        ivScoreboard.setOnClickListener(this);
        ivScoreboard22 = findViewById(R.id.ivScoreboard22);
        ivScoreboard22.setOnClickListener(this);
        ivPickCard = findViewById(R.id.ivPickCard);
        ivPickCard9 = findViewById(R.id.ivPickCard9);
        ivPickCard.setVisibility(View.INVISIBLE);
        ivPickCard9.setVisibility(View.INVISIBLE);
        ivDrawUserOne = findViewById(R.id.ivDrawUserOne);
        ivDrawUserOne.setVisibility(View.INVISIBLE);
        ivDrawUserTwo = findViewById(R.id.ivDrawUserTwo);
        ivDrawUserTwo.setVisibility(View.INVISIBLE);
        ivDrawUserThree = findViewById(R.id.ivDrawUserThree);
        ivDrawUserThree.setVisibility(View.INVISIBLE);
        LeftUserVipTag = findViewById(R.id.LeftUserVipTag);
        LeftUserVipTag.setVisibility(View.INVISIBLE);
        RightUserVipTag = findViewById(R.id.RightUserVipTag);
        RightUserVipTag.setVisibility(View.INVISIBLE);
        BottomUserVipTag = findViewById(R.id.BottomUserVipTag);
        BottomUserVipTag.setVisibility(View.INVISIBLE);
        TextView tvTitleGti = findViewById(R.id.tvTitleGti);
        tvTitleGti.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvTitleGti.setPadding(0, getheight(6), 0, getheight(6));
        tvTitleGti.setTypeface(tf, Typeface.BOLD);
        TextView tvBootAmtGtiText = findViewById(R.id.tvBootAmtGtiText);
        tvBootAmtGtiText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvBootAmtGtiText.setPadding(0, getheight(6), 0, getheight(6));
        tvBootAmtGtiText.setTypeface(tf, Typeface.BOLD);
        tvBootAmtGti = findViewById(R.id.tvBootAmtGti);
        tvBootAmtGti.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvBootAmtGti.setPadding(0, getheight(6), 0, getheight(6));
        tvBootAmtGti.setTypeface(tf, Typeface.BOLD);
        TextView tvTableNameGtiText = findViewById(R.id.tvTableNameGtiText);
        tvTableNameGtiText
                .setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvTableNameGtiText.setPadding(0, getheight(6), 0, getheight(6));
        tvTableNameGtiText.setTypeface(tf, Typeface.BOLD);
        tvTableNameGti = findViewById(R.id.tvTableNameGti);
        tvTableNameGti.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvTableNameGti.setPadding(0, getheight(6), 0, 0);
        tvTableNameGti.setTypeface(tf, Typeface.BOLD);
        TextView tvMinChipsGtiText = findViewById(R.id.tvMinChipsGtiText);
        tvMinChipsGtiText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvMinChipsGtiText.setPadding(0, getheight(6), 0, getheight(6));
        tvMinChipsGtiText.setTypeface(tf, Typeface.BOLD);
        tvMinChipsGti = findViewById(R.id.tvMinChipsGti);
        tvMinChipsGti.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvMinChipsGti.setPadding(0, getheight(6), 0, 0);
        tvMinChipsGti.setTypeface(tf, Typeface.BOLD);
        TextView tvPointsTextGti = findViewById(R.id.tvPointsTextGti);
        tvPointsTextGti.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvPointsTextGti.setPadding(0, getheight(6), 0, getheight(6));
        tvPointsTextGti.setTypeface(tf, Typeface.BOLD);
        tvPointsGti = findViewById(R.id.tvPointsGti);
        tvPointsGti.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvPointsGti.setPadding(0, getheight(6), 0, 0);
        tvPointsGti.setTypeface(tf, Typeface.BOLD);
        llTableInfo = findViewById(R.id.llTableInfo);
        llTableInfo.setVisibility(View.INVISIBLE);
        ivWinninngRoundOne = findViewById(R.id.ivWinninngRoundOne);
        ivWinninngRoundOne.setVisibility(View.INVISIBLE);
        ivWinninngRoundTwo = findViewById(R.id.ivWinninngRoundTwo);
        ivWinninngRoundTwo.setVisibility(View.INVISIBLE);
        ivWinninngRoundThree = findViewById(R.id.ivWinninngRoundThree);
        ivWinninngRoundThree.setVisibility(View.INVISIBLE);
        ivWinninngCircleOne = findViewById(R.id.ivWinninngCircleOne);
        ivWinninngCircleOne.setVisibility(View.INVISIBLE);
        ivWinninngCircleTwo = findViewById(R.id.ivWinninngCircleTwo);
        ivWinninngCircleTwo.setVisibility(View.INVISIBLE);
        ivWinninngCircleThree = findViewById(R.id.ivWinninngCircleThree);
        ivWinninngCircleThree.setVisibility(View.INVISIBLE);
        exit_text = findViewById(R.id.exit_text);
        exit_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        exit_text.setTypeface(tf, Typeface.BOLD);
        switch_text = findViewById(R.id.switch_text);
        switch_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        switch_text.setTypeface(tf, Typeface.BOLD);
        table_info_text = findViewById(R.id.table_info_text);
        table_info_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        table_info_text.setTypeface(tf, Typeface.BOLD);
        scoreboard_text = findViewById(R.id.scoreboard_text);
        scoreboard_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        scoreboard_text.setTypeface(tf, Typeface.BOLD);
        tvController = findViewById(R.id.tvController);
        tvController.setOnClickListener(this);
        tvController.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvController.setTypeface(tf, Typeface.BOLD);
        cbController = findViewById(R.id.cbController);
        cbController.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    showControls = true;
                    PreferenceManager.setControllVisibility(true);
                    if (isUserTurn && hasTakenCard && selectedCard.size() > 0) {
                        btnDiscard.setVisibility(View.VISIBLE);
                    } else if (isUserTurn && !hasTakenCard) {
                        if (!dontAllowToClickOnCloseDeck)
                            btnFaceUp.setVisibility(View.VISIBLE);
                        if (!isFirstTurn)
                            btnDrawNew.setVisibility(View.VISIBLE);
                    }
                } else {
                    showControls = false;
                    PreferenceManager.setControllVisibility(false);
                    btnFaceUp.setVisibility(View.GONE);
                    btnDrawNew.setVisibility(View.GONE);
                    btnDiscard.setVisibility(View.GONE);
                }
            }
        });
        if (PreferenceManager.getControllVisibility()) {
            showControls = true;
            cbController.setChecked(true);
        } else {
            showControls = false;
            cbController.setChecked(false);
        }
        cbSound = findViewById(R.id.cbSound);
        cbSound.setPadding(getwidth(70), 0, 0, 0);
        cbSound.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        cbSound.setTypeface(tf, Typeface.BOLD);
        cbSound.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                cbSound.startAnimation(buttonPressed);
                if (isChecked) {
                    cbSound.setBackgroundResource(R.drawable.btnsoundon);
                    PreferenceManager.setMute(false);
                    Music_Manager.Mute = false;
                } else {
                    cbSound.setBackgroundResource(R.drawable.btnsoundoff);
                    PreferenceManager.setMute(true);
                    Music_Manager.Mute = true;
                }

            }
        });
        if (PreferenceManager.getMute()) {
            cbSound.setBackgroundResource(R.drawable.btnsoundoff);
            cbSound.setChecked(false);
            Music_Manager.Mute = true;
        } else {
            cbSound.setBackgroundResource(R.drawable.btnsoundon);
            cbSound.setChecked(true);
            Music_Manager.Mute = false;
        }
        cbVibrate = findViewById(R.id.cbVibRate);
        cbVibrate.setPadding(getwidth(70), 0, 0, 0);
        cbVibrate.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        cbVibrate.setTypeface(tf, Typeface.BOLD);
        cbVibrate.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                cbVibrate.startAnimation(buttonPressed);
                if (isChecked) {
                    cbVibrate.setBackgroundResource(R.drawable.vibrate_on);
                    PreferenceManager.setVibrate(true);
                } else {
                    cbVibrate.setBackgroundResource(R.drawable.vibrate_off);
                    PreferenceManager.setVibrate(false);
                }

            }
        });
        if (!PreferenceManager.getVibrate()) {
            cbVibrate.setBackgroundResource(R.drawable.vibrate_off);
            cbVibrate.setChecked(false);
        } else {
            cbVibrate.setBackgroundResource(R.drawable.vibrate_on);
            cbVibrate.setChecked(true);
        }
        back_menu_frame = findViewById(R.id.back_menu_frame);
        back_menu_frame.setOnClickListener(this);
        close_menu = findViewById(R.id.close_menu);
        close_menu.setOnClickListener(this);
        fmUserOne = findViewById(R.id.fmUserOne);
        if (numberOfPlayers == 2) {
            fmUserOne.setVisibility(View.INVISIBLE);
        } else {
            fmUserOne.setVisibility(View.VISIBLE);
        }
    }

    private void SetNewTimerScreenId() {
        //======== Main Timer ====
        hidehandler = new Handler();
        frm_timer = findViewById(R.id.frm_timer);
        frm_timer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClaimEnable) {
                    tv_chips[PreferenceManager.getBonusTimerCount()].setText("Collected");
                    tv_timer[PreferenceManager.getBonusTimerCount()].setText("" + getTimeInFormate(time[PreferenceManager.getBonusTimerCount()]));
                    iv_green[PreferenceManager.getBonusTimerCount()].setVisibility(View.INVISIBLE);
                    iv_red_glow[PreferenceManager.getBonusTimerCount()].setVisibility(View.INVISIBLE);
                    long claimChip = Chips[PreferenceManager.getBonusTimerCount()];
                    PreferenceManager.setChips(PreferenceManager.getChips() + claimChip);
                    ShowErrorPopup(claimChip + " Chips Added Successfully...", false,
                            "Success");
                    if (PreferenceManager.getBonusTimerCount() < 5) {
                        Logger.print(">>>>>>> BONUS COUNT BRFORE 222 >>> " + PreferenceManager.getBonusTimerCount());
                        PreferenceManager.setBonusTimerCount(PreferenceManager.getBonusTimerCount() + 1);
                        Logger.print(">>>>>>> BONUS COUNT AFTER 222 >>> " + PreferenceManager.getBonusTimerCount());
                    } else {
                        PreferenceManager.setBonusTimerCount(0);
                        for (int i = 0; i < tv_chips.length; i++) {
                            tv_chips[i].setText("" + Chips[i] + " Chips");
                        }
                    }
                    BonustimerStart(time[PreferenceManager.getBonusTimerCount()]);
                } else {
                    if (frm_time_list.getVisibility() != View.VISIBLE) {
                        Animation enter = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_line_straight);
                        frm_time_list.setVisibility(View.VISIBLE);
                        if (frm_time_list.getAnimation() != null) {
                            frm_time_list.clearAnimation();
                        }
                        frm_time_list.startAnimation(enter);
                        show();
                    } else {
//                        hidehandler.removeCallbacksAndMessages(hideList);
//                        hidehandler.removeCallbacks(hideList);
                        //hidehandler.removeCallbacksAndMessages(null);
                        if (hidehandler != null) {
                            hidehandler.removeCallbacksAndMessages(null);
                            hidehandler = null;
                        }
                        Animation exit = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_line_reverse);
                        if (frm_time_list.getAnimation() != null) {
                            frm_time_list.clearAnimation();
                        }
                        frm_time_list.startAnimation(exit);
                        frm_time_list.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        tv_bonus_timer = findViewById(R.id.tv_bonus_timer);
        tv_bonus_timer.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(26));
        tv_bonus_timer.setTypeface(c.typeface, Typeface.BOLD);
        //======== Timmer List ========
        frm_time_list = findViewById(R.id.frm_time_list);
        frm_time_list.setVisibility(View.INVISIBLE);
        for (int i = 0; i < frm_time.length; i++) {
            frm_time[i] = findViewById(getResources()
                    .getIdentifier("frm_time_" + i, "id", getPackageName()));
            tv_timer[i] = findViewById(getResources()
                    .getIdentifier("tv_timer_" + i, "id", getPackageName()));
            tv_chips[i] = findViewById(getResources()
                    .getIdentifier("tv_chips_" + i, "id", getPackageName()));
            iv_red_glow[i] = findViewById(getResources()
                    .getIdentifier("iv_red_glow_" + i, "id", getPackageName()));
            iv_green[i] = findViewById(getResources()
                    .getIdentifier("iv_green_" + i, "id", getPackageName()));
            tv_timer[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(20));
            tv_timer[i].setTypeface(c.typeface, Typeface.BOLD);
            tv_chips[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(20));
            tv_chips[i].setTypeface(c.typeface, Typeface.BOLD);
            tv_timer[i].setText("" + Chips[i] + " Chips");
            tv_timer[i].setText("" + getTimeInFormate(time[i]));
            iv_green[i].setVisibility(View.INVISIBLE);
            iv_red_glow[i].setVisibility(View.INVISIBLE);
        }
        for (int i = 0; i < PreferenceManager.getBonusTimerCount(); i++) {
            tv_chips[i].setText("Collected");
        }
        for (int i = 0; i < iv_line.length; i++) {
            iv_line[i] = findViewById(getResources()
                    .getIdentifier("iv_line_" + i, "id", getPackageName()));
        }
        SetNewTimerScreenLayout();
    }

    private void show() {
        if (hidehandler != null) {
            hidehandler.removeCallbacksAndMessages(null);
            hidehandler = null;
        }
        hidehandler = new Handler();
        hidehandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (frm_time_list.getVisibility() == View.VISIBLE) {
                    Animation exit = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_line_reverse);
                    if (frm_time_list.getAnimation() != null) {
                        frm_time_list.clearAnimation();
                    }
                    frm_time_list.startAnimation(exit);
                    frm_time_list.setVisibility(View.INVISIBLE);
                }
            }
        }, 5000);
    }

    private String getTimeInFormate(long time) {
        long second = time / 1000;
        long minute = second / 60;
        long sec = second % 60;
        String m = getinNumFormat(minute);
        String s = getinNumFormat(sec);
        return m + ":" + s;
    }

    private String getinNumFormat(long num) {
        if (num < 10) {
            return "0" + num;
        } else {
            return "" + num;
        }
    }

    private void SetNewTimerScreenLayout() {
        FrameLayout.LayoutParams frm;
        LinearLayout.LayoutParams ln;
        int w, h;
        //======== Main Timer ====
        frm = (FrameLayout.LayoutParams) findViewById(R.id.frm_timer).getLayoutParams();
        frm.width = getwidth(201);
        frm.height = getheight(86);
        frm.leftMargin = getwidth(280);
        frm.topMargin = getheight(10);
        w = getwidth(139);
        h = getheight(67);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_green_glow).getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.leftMargin = getwidth(55);
        frm.topMargin = getheight(13);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.tv_bonus_timer).getLayoutParams();
        frm.leftMargin = getwidth(35);
        w = getwidth(87);
        h = getheight(86);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_red_glow).getLayoutParams();
        frm.width = w;
        frm.height = h;
        //======== Timmer List ========
        w = getwidth(298);
        h = getheight(419);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.frm_time_list).getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.leftMargin = getwidth(-30);
        frm.topMargin = getheight(91);
        for (int i = 0; i < frm_time.length; i++) {
            w = getwidth(141);
            h = getheight(61);
            ln = (LinearLayout.LayoutParams) frm_time[i].getLayoutParams();
            ln.width = w;
            ln.height = h;
            w = getwidth(98);
            h = getheight(47);
            frm = (FrameLayout.LayoutParams) iv_green[i].getLayoutParams();
            frm.width = w;
            frm.height = h;
            frm.leftMargin = getwidth(40);
            frm.topMargin = getheight(9);
            w = getwidth(61);
            h = getheight(61);
            frm = (FrameLayout.LayoutParams) iv_red_glow[i].getLayoutParams();
            frm.width = w;
            frm.height = h;
            frm = (FrameLayout.LayoutParams) tv_timer[i].getLayoutParams();
            frm.leftMargin = getwidth(61);
            frm.topMargin = getheight(2);
            ln = (LinearLayout.LayoutParams) tv_chips[i].getLayoutParams();
            ln.leftMargin = getwidth(10);
            ln.topMargin = getheight(2);
        }
        for (int i = 0; i < iv_line.length; i++) {
            w = getwidth(285);
            h = getheight(3);
            ln = (LinearLayout.LayoutParams) iv_line[i].getLayoutParams();
            ln.width = w;
            ln.height = h;
        }
    }

    public void BonustimerStart(long timeLengthMilli) {
        Logger.print(">>>>>>> BONUS COUNT >>>> Time Start 111 >>>  " + time[PreferenceManager.getBonusTimerCount()]);
        c.isBonusTimerStart = true;
        isClaimEnable = false;
        iv_green[PreferenceManager.getBonusTimerCount()].setVisibility(View.VISIBLE);
        iv_red_glow[PreferenceManager.getBonusTimerCount()].setVisibility(View.VISIBLE);
        if (tv_bonus_timer.getAnimation() != null) {
            tv_bonus_timer.clearAnimation();
        }
        Bonushcdtimer = new CountDownTimer(timeLengthMilli, 1000) {
            @Override
            public void onTick(long milliTillFinish) {
//                Logger.print(">>>>>> BONUS TIMER >>> "+getTimeInFormate(milliTillFinish));
                c.BonusmilliLeft = milliTillFinish;
                tv_bonus_timer.setText("" + getTimeInFormate(milliTillFinish));
                tv_timer[PreferenceManager.getBonusTimerCount()].setText("" + getTimeInFormate(milliTillFinish));
            }

            @Override
            public void onFinish() {
                tv_timer[PreferenceManager.getBonusTimerCount()].setText("" + getTimeInFormate(0));
                tv_bonus_timer.setText("Collect");
                tv_bonus_timer.startAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.freechipanim));
                isClaimEnable = true;
            }
        }.start();
    }

    public void BonustimerPause() {
        if (Bonushcdtimer != null && c.isBonusTimerStart) {
            Bonushcdtimer.cancel();
            Bonushcdtimer = null;
        }
    }

    private void BonustimerResume() {
        if (c.isBonusTimerStart) {
            Logger.print(">>>>>>> BONUS COUNT >>>> Time Start 222 >>>  " + c.BonusmilliLeft);
            BonustimerStart(c.BonusmilliLeft);
        }
    }

    private boolean isPointInsideOpenDeck(float x, float y) {
        int location[] = new int[2];
        frmtable.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];
        Logger.print("=>>>>>>>>>> " + viewX + " " + viewY + " " + x + " " + y + " " + frmtable.getWidth() + " " + frmtable.getHeight());
        //point is inside view bounds
        return (x > viewX && x < (viewX + frmtable.getWidth())) &&
                (y > viewY && y < (viewY + frmtable.getHeight()));
    }

    private boolean isContaintselected(int i) {
        // TODO Auto-generated method stub
        // for (int j = 0; j < selectedCard.size(); j++)
        try {
            for (int j = 0; j < selectedCard.size(); j++) {
                if (ivMyCards[i].getTag().toString().equalsIgnoreCase(selectedCard.get(j).getTag().toString())) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        if (v == frmMainLayout) {
            Logger.print("ONCLICK >>>> " + frm_time_list.getVisibility());
            if (frm_time_list.getVisibility() == View.VISIBLE) {
                if (hidehandler != null) {
                    hidehandler.removeCallbacksAndMessages(null);
                    hidehandler = null;
                }
                Animation exit = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_line_reverse);
                if (frm_time_list.getAnimation() != null) {
                    frm_time_list.clearAnimation();
                }
                frm_time_list.startAnimation(exit);
                frm_time_list.setVisibility(View.INVISIBLE);
            }
        }
        if (v == ivScoreboard22) {
            Music_Manager.buttonclick();
            String JSonScore = loadJSONFromAsset();
            Intent i = new Intent(
                    getApplicationContext(),
                    RoundWinner.class);
            i.putExtra("roundWinnerData",
                    JSonScore);
            i.putExtra("timer", 14);
            i.putExtra("round", 1);
            i.putExtra("point", 100);
            i.putExtra("winner", true);
            i.putExtra("test", false);
            startActivity(i);
            overridePendingTransition(android.R.anim.slide_in_left, 0);
        } else if (v == ivCloseCard) {
            Music_Manager.buttonclick();
            closeCardClick();
        } else if (v == ivCloseDeck) {
            Music_Manager.buttonclick();
            closeDeckClick();
        } else if (v == ivScoreboard) {
            Music_Manager.buttonclick();
            v.startAnimation(buttonPressed);
            try {
                if (senddata.length() > 0) {
                    Logger.print("########################################## 22" + senddata.toString());
                    JSONArray array = senddata.getJSONArray("pi");
                    Intent i = new Intent(getApplicationContext(),
                            Scorboard.class);
                    i.putExtra("A1", array.toString());
                    startActivity(i);
                    overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
                } else {
                    ShowErrorPopup("Scoreboard not generated yet!", false,
                            "Scoreboard");
                }
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } else if (v == StandUp_Btn) {
            Music_Manager.buttonclick();
            v.startAnimation(buttonPressed);
            if (back_menu_frame.getVisibility() == View.VISIBLE) {
                back_menu_frame.setVisibility(View.GONE);
            } else {
                back_menu_frame.setVisibility(View.VISIBLE);
                back_menu_frame.bringToFront();
                back_menu_frame.startAnimation(FromLeft);
            }
        } else if (v == btnSkip) {
            Music_Manager.buttonclick();
            v.startAnimation(buttonPressed);
            btnFaceUp.setVisibility(View.GONE);
            btnDrawNew.setVisibility(View.GONE);
            btnDiscard.setVisibility(View.GONE);
            if (btnSkip.getVisibility() == View.VISIBLE) {
                if (btnSkip.getAnimation() != null) {
                    btnSkip.clearAnimation();
                }
                btnSkip.setVisibility(View.GONE);
            }
            ivCloseCard.setClickable(false);
            if (ivHandOpenCard.getAnimation() != null)
                ivHandOpenCard.clearAnimation();
            ivHandOpenCard.setVisibility(View.INVISIBLE);
            isFirstTurn = false;
            if (PlayScreen2.handler != null) {
                Message msg = new Message();
                msg.what = ResponseCodes.skipbtnclick;
                PlayScreen2.handler.sendMessage(msg);
            }
        } else if (v == btnKnock) {
            Music_Manager.buttonclick();
            isUserTurn = false;
            isCardTouchable = false;
            setClickable(ResponseCodes.ThrowCardOnDeckResp);
            btnDiscard.setVisibility(View.GONE);
            if (btnKnock.getVisibility() == View.VISIBLE) {
                if (btnKnock.getAnimation() != null) {
                    btnKnock.clearAnimation();
                }
                btnKnock.setVisibility(View.INVISIBLE);
            }
            if (ivScore_knock.getVisibility() == View.VISIBLE) {
                if (ivScore_knock.getAnimation() != null) {
                    ivScore_knock.clearAnimation();
                }
                ivScore_knock.setVisibility(View.INVISIBLE);
            }
            btnFaceUp.setVisibility(View.GONE);
            btnDrawNew.setVisibility(View.GONE);
            if (ivHandOpenCard.getAnimation() != null)
                ivHandOpenCard.clearAnimation();
            ivHandOpenCard.setVisibility(View.INVISIBLE);
            v.startAnimation(buttonPressed);
            Message msg = new Message();
            msg.what = ResponseCodes.knockvissible;
            PlayScreen2.handler.sendMessage(msg);
            isknock = false;
            // leaveTable=true;
        } else if (v == btnGin) {
            Music_Manager.buttonclick();
            isCardTouchable = false;
            isUserTurn = false;
            btnFaceUp.setVisibility(View.GONE);
            btnDrawNew.setVisibility(View.GONE);
            btnDiscard.setVisibility(View.GONE);
            v.startAnimation(buttonPressed);
            if (ivHandOpenCard.getAnimation() != null)
                ivHandOpenCard.clearAnimation();
            ivHandOpenCard.setVisibility(View.INVISIBLE);
            Message msg = new Message();
            msg.what = ResponseCodes.ginvissible;
            PlayScreen2.handler.sendMessage(msg);
            isgin = false;
            isknock = false;
            if (btnGin.getVisibility() == View.VISIBLE) {
                if (btnGin.getAnimation() != null) {
                    btnGin.clearAnimation();
                }
                btnGin.setVisibility(View.INVISIBLE);
            }
        } else if (v == ivProfileImageThree) {
            Music_Manager.buttonclick();
            Intent inprofile = new Intent(getApplication(), Activity_Profile.class);
            inprofile.putExtra("uid", PreferenceManager.get_id());
            inprofile.putExtra("isFromPlaying", true);
            startActivity(inprofile);
            overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
        } else if (v == btnDrawNew) {
            Music_Manager.buttonclick();

            btnFaceUp.setVisibility(View.GONE);
            btnDrawNew.setVisibility(View.GONE);
            btnDiscard.setVisibility(View.GONE);
            closeDeckClick();
        } else if (v == btnFaceUp) {


            Music_Manager.buttonclick();
            btnFaceUp.setVisibility(View.GONE);
            btnDrawNew.setVisibility(View.GONE);
            btnDiscard.setVisibility(View.GONE);
            closeCardClick();
        } else if (v == btnDiscard) {
            Music_Manager.buttonclick();
            if (btnKnock.getVisibility() == View.VISIBLE) {
                if (btnKnock.getAnimation() != null) {
                    btnKnock.clearAnimation();
                }
                btnKnock.setVisibility(View.INVISIBLE);
            }
            if (ivScore_knock.getVisibility() == View.VISIBLE) {
                if (ivScore_knock.getAnimation() != null) {
                    ivScore_knock.clearAnimation();
                }
                ivScore_knock.setVisibility(View.INVISIBLE);
            }
            if (btnGin.getVisibility() == View.VISIBLE) {
                if (btnGin.getAnimation() != null) {
                    btnGin.clearAnimation();
                }
                btnGin.setVisibility(View.INVISIBLE);
            }
            btnFaceUp.setVisibility(View.GONE);
            btnDrawNew.setVisibility(View.GONE);
            btnDiscard.setVisibility(View.GONE);

            closeCardClick();
        } else if (v == llExitToLobbyWrapper) {
            Music_Manager.buttonclick();
            v.startAnimation(buttonPressed);
            back_menu_frame.setVisibility(View.GONE);
            back_menu_frame.startAnimation(ToLeft);
            FOR_LEAVE();
        } else if (v == llTableInfoWrapper) {
            Music_Manager.buttonclick();
            v.startAnimation(buttonPressed);
            back_menu_frame.setVisibility(View.GONE);
            back_menu_frame.startAnimation(ToLeft);
            String bValTI = c.getNumberFormatedValue(BOOT_VALUE);
            String iValTI = c.getNumberFormatedValue(BOOT_VALUE * 3);
            if (BOOT_VALUE == 500) {
                iValTI = c.getNumberFormatedValue(BOOT_VALUE);
            }
            llTableInfo.setVisibility(View.VISIBLE);
            tvBootAmtGti.setText("" + bValTI);
            if (isFromPrivateTable) {
                tvTableNameGti.setText("Private Table");
            } else {
                tvTableNameGti.setText("" + tableinfo.getTableName());
            }
            tvMinChipsGti.setText("" + iValTI);
            tvPointsGti.setText("" + GAME_POINT);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    llTableInfo.setVisibility(View.INVISIBLE);
                }
            }, 4000);
        } else if (v == close_menu) {
            Music_Manager.buttonclick();
            v.startAnimation(buttonPressed);
            back_menu_frame.setVisibility(View.GONE);
            back_menu_frame.startAnimation(ToLeft);
        } else if (v == back_menu_frame) {
            Music_Manager.buttonclick();
            back_menu_frame.setVisibility(View.GONE);
            back_menu_frame.startAnimation(ToLeft);
        }
    }

    private void closeDeckClick() {
        if (!isTutorial) {
            setClickable(1);
        }
        isCardPicked = true;
        isUserPicked = true;
        // Log.e("CLOSED", "CRAD");
        btnFaceUp.setVisibility(View.GONE);
        btnDrawNew.setVisibility(View.GONE);
        btnDiscard.setVisibility(View.GONE);
        if (ivHandOpenCard.getAnimation() != null)
            ivHandOpenCard.clearAnimation();
        if (ivHandCloseDeck.getAnimation() != null)
            ivHandCloseDeck.clearAnimation();
        ivHandCloseDeck.setVisibility(View.INVISIBLE);
        ivHandOpenCard.setVisibility(View.INVISIBLE);
        JSONObject jsonObject = new JSONObject();
        try {
            if (closeBunchCard.size() > 0) {
                jsonObject.put(Parameters.Card, getCardString(closeBunchCard.get(closeBunchCard.size() - 1)));
            } else {
                jsonObject.put(Parameters.Card, "");
            }
            jsonObject.put(Parameters.SeatIndex, CURRENT_USER_TURN);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put(Parameters.data, jsonObject);
            PickFromPileProcess(jsonObject1.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // <<<--- When User Click on Open Card --->>>
    private void closeCardClick() {
        Logger.print("Click on Close CARD");
        btnFaceUp.setVisibility(View.GONE);
        btnDrawNew.setVisibility(View.GONE);
        btnDiscard.setVisibility(View.GONE);
        if (!isCardThrown && hasTakenCard) {
            Logger.print("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ => "
                    + selectedCard.size());
            isSortButtonClickable = false;
            Logger.print("isSortButtonClickable => " + isSortButtonClickable);
            if (selectedCard.size() > 0) {
                isCardThrown = true;
                isUserTurn = false;
                if (btnGin.getAnimation() != null) {
                    btnGin.clearAnimation();
                }
                btnGin.setVisibility(View.GONE);
                if (btnKnock.getAnimation() != null) {
                    btnKnock.clearAnimation();
                }
                if (ivScore_knock.getVisibility() == View.VISIBLE) {
                    if (ivScore_knock.getAnimation() != null) {
                        ivScore_knock.clearAnimation();
                    }
                    ivScore_knock.setVisibility(View.INVISIBLE);
                }
                btnGin.setVisibility(View.GONE);
                isCardTouchable = false;
                isAllowtoCardClick = false;
                ivCloseCard.setClickable(false);
                if (RightUserRing.getAnimation() != null) {
                    RightUserRing.clearAnimation();
                }
                if (LeftUserRing.getAnimation() != null) {
                    LeftUserRing.clearAnimation();
                }
                if (BottomUserRing.getAnimation() != null) {
                    BottomUserRing.clearAnimation();
                }
                if (UserTimer != null) {
                    UserTimer.cancel();
                    UserTimer = null;
                }
                LeftUserRing.setVisibility(View.INVISIBLE);
                RightUserRing.setVisibility(View.INVISIBLE);
                BottomUserRing.setVisibility(View.INVISIBLE);
                if (btnKnock.getVisibility() == View.VISIBLE) {
                    if (btnKnock.getAnimation() != null) {
                        btnKnock.clearAnimation();
                    }
                    btnKnock.setVisibility(View.INVISIBLE);
                }
                if (ivScore_knock.getVisibility() == View.VISIBLE) {
                    if (ivScore_knock.getAnimation() != null) {
                        ivScore_knock.clearAnimation();
                    }
                    ivScore_knock.setVisibility(View.INVISIBLE);
                }
                getSelectedCard = selectedCard.get(0);
                // ivCloseCard9 = selectedCard.get(0);
                //getSelectedCard.setPadding(0, 0, 0, 0);
                //getSelectedCard.setBackgroundResource(R.drawable.cardselector);
                int w = CARD_WIDTH;
                int h = CARD_HEIGHT;
                int index = Arrays.asList(ivMyCards).indexOf(getSelectedCard);
                LayoutParams removeBottomMargin = (LayoutParams) getSelectedCard
                        .getLayoutParams();
                removeBottomMargin.width = w;
                removeBottomMargin.height = h;
                removeBottomMargin.topMargin = w * 30 / 190;
                removeBottomMargin.bottomMargin = 0;
                getSelectedCard.setLayoutParams(removeBottomMargin);
                if (index - 1 >= 0) {
                    removeBottomMargin = (LayoutParams) ivMyCards[index - 1].getLayoutParams();
                    removeBottomMargin.width = w;
                    removeBottomMargin.height = h;
                    removeBottomMargin.topMargin = w * 30 / 190;
                    removeBottomMargin.bottomMargin = 0;
                    ivMyCards[index - 1].setLayoutParams(removeBottomMargin);
                }
                if (index + 1 < ivMyCards.length) {
                    removeBottomMargin = (LayoutParams) ivMyCards[index + 1].getLayoutParams();
                    removeBottomMargin.width = w;
                    removeBottomMargin.height = h;
                    removeBottomMargin.topMargin = w * 30 / 190;
                    removeBottomMargin.bottomMargin = 0;
                    ivMyCards[index + 1].setLayoutParams(removeBottomMargin);
                }
                selectedCard.clear();
                getSelectedCard.setRotation(0);
                getSelectedCard.getLocationOnScreen(selectedCardXY);
                getSelectedCard.setVisibility(View.GONE);
                ivCloseCard9.getLocationOnScreen(closeCardLocationXY);
                ivCloseCard9.setVisibility(View.VISIBLE);
                ivCloseCard9.bringToFront();
//                ivCloseCard9.setImageResource(CardDefault[throwncardindex]);
                int x1TCODUser = selectedCardXY[0];
                int y1TCODUser = selectedCardXY[1];
                int xTCODUser = closeCardLocationXY[0];
                int yTCODUser = closeCardLocationXY[1];
//                TranslateAnimation tAnimationTCODUser = new TranslateAnimation( x1TCODUser - xTCODUser
//                         , 0, y1TCODUser - yTCODUser,0);
//                tAnimationTCODUser.setDuration(600);
//                tAnimationTCODUser.setFillAfter(false);
                TranslateAnimation tAnimationTCODUser = new TranslateAnimation(x1TCODUser - xTCODUser
                        , 0, y1TCODUser - yTCODUser, 0);
                tAnimationTCODUser.setDuration(600);
                tAnimationTCODUser.setFillAfter(false);
                Rotate3dAnimationX rotateThrowCardTCODUser = new Rotate3dAnimationX(0, 25, (ivCloseCard9.getWidth() / 2), ivCloseCard9.getHeight(), 0, false);
                rotateThrowCardTCODUser.setDuration(600);
                rotateThrowCardTCODUser.setInterpolator(new LinearInterpolator());
//                Rotate3dAnimationX zrotate = new Rotate3dAnimationX(45,0,(ivCardOnDeck9.getWidth() / 2),(ivCardOnDeck9.getHeight() /2),0, false);
//                zrotate.setDuration(600);
//
//                zrotate.setInterpolator(new LinearInterpolator());
//
                ScaleAnimation scale = new ScaleAnimation(1, 1.25f, 1, 1.25f, Animation.RELATIVE_TO_SELF, 1.25f,
                        Animation.RELATIVE_TO_SELF, 1.25f);
                scale.setFillAfter(true);
                scale.setDuration(300);
                ScaleAnimation scale2 = new ScaleAnimation(1, .8f, 1f, .8f, Animation.RELATIVE_TO_SELF, .8f,
                        Animation.RELATIVE_TO_SELF, .8f);
                scale2.setFillAfter(true);
                scale2.setStartOffset(300);
                scale2.setDuration(300);
                AnimationSet animSetBottomTCODUser = new AnimationSet(false);
                animSetBottomTCODUser.addAnimation(scale);
                animSetBottomTCODUser.addAnimation(scale2);
                animSetBottomTCODUser.addAnimation(tAnimationTCODUser);
                animSetBottomTCODUser.addAnimation(rotateThrowCardTCODUser);
                if (getSelectedCard != null) {
                    try {
                        selectedCardToRemove = getSelectedCard.getTag()
                                .toString();
                        Logger.print("CARD Thrown => "
                                + selectedCardToRemove);
                        throwncardindex = Arrays.asList(cardString).indexOf(
                                selectedCardToRemove);
                        ivCloseCard9.setImageResource(CardDefault[throwncardindex]);
                        selectedCardToRemove = selectedCardToRemove
                                .trim();
                        removeElement(card_BottomUser,
                                getItemCard(selectedCardToRemove));
                        Logger.print("ThrowCardOnDeckProcess called XXXX Bottom"
                                + card_BottomUser.toString()
                                + " "
                                + selectedCardToRemove);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                btnDiscard.setVisibility(View.GONE);
                // tAnimation.setInterpolator(new AccelerateInterpolator());
                animSetBottomTCODUser.getAnimations().get(0).setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub
                        Music_Manager.throwcard();
                        isCardTouchable = true;
                        setClickable(ResponseCodes.ThrowCardOnDeckResp);
                        if (ivHandOpenCard.getAnimation() != null)
                            ivHandOpenCard.clearAnimation();
                        ivHandOpenCard.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // TODO Auto-generated method stub

                    }
                });
                animationHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        //if (getSelectedCard != null) {
                        if (throwncardindex >= 0) {
                            ivCloseCard.setVisibility(View.VISIBLE);
                            ivCloseCard
                                    .setImageResource(CardDefault[throwncardindex]);
                            ivCloseCard.setTag(cardString[throwncardindex]);
                        }
                        ivCloseCard9.setVisibility(View.INVISIBLE);
                        //{"en":"TCOD","data":{"c":"c-11","si":1}}
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(Parameters.Card, selectedCardToRemove);
                            jsonObject.put(Parameters.SeatIndex, BottomSeatIndex);
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put(Parameters.data, jsonObject);
                            ThrowCardOnDeckProcess(jsonObject1.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // }
                        /*card_BottomUser = new ArrayList<Item_Card>(Sort(
                                card_BottomUser, BottomSeatIndex));*/
                        reDrawUserCards("");
                        isSortButtonClickable = true;
                    }
                }, 600);
                ivCloseCard9.startAnimation(animSetBottomTCODUser);
            } /*else {
                // Toast.makeText(getApplicationContext(),
                // "Please Select Card To Throw", Toast.LENGTH_SHORT)
                // .show();
            }*/
        } else if (!hasTakenCard && !isCardThrown) {
            if (openBunchCard.size() > 0) {
                Logger.print("OPEN CARDS =>" + openBunchCard.toString()
                        + " " + isFirstTurn);
                setClickable(2);
                isCardPicked = true;
                isUserPicked = true;
                if (isFirstTurn) {
                    if (btnSkip.getVisibility() == View.VISIBLE) {
                        if (btnSkip.getAnimation() != null) {
                            btnSkip.clearAnimation();
                        }
                        btnSkip.setVisibility(View.GONE);
                        isFirstTurn = false;
                    }
                }
                if (ivHandOpenCard.getAnimation() != null)
                    ivHandOpenCard.clearAnimation();
                if (ivHandCloseDeck.getAnimation() != null)
                    ivHandCloseDeck.clearAnimation();
                ivHandCloseDeck.setVisibility(View.INVISIBLE);
                ivHandOpenCard.setVisibility(View.INVISIBLE);
                //{"en":"PFTC","data":{"c":"l-2","si":1}}
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(Parameters.Card, getCardString(openBunchCard.get(openBunchCard.size() - 1)));
                    jsonObject.put(Parameters.SeatIndex, CURRENT_USER_TURN);
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put(Parameters.data, jsonObject);
                    PickFromThrownCardsProcess(jsonObject1.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // Toast.makeText(getApplicationContext(),
                // "There is no cards", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setClickable(int responseCode) {
        Logger.print("Set Clickable called => " + responseCode);
        if (isTutorial)
            return;
        if (responseCode == ResponseCodes.SwitchTableResp) {
            ivProfileImage.setClickable(false);
            ivProfileImageTwo.setClickable(false);
            ivProfileImageThree.setClickable(false);
            ivGiftIcon.setClickable(false);
            ivGiftIconTwo.setClickable(false);
            ivGiftIconThree.setClickable(false);
        } else if (responseCode == ResponseCodes.UserTurnStartedResp) {
            for (ImageView ivMyCard : ivMyCards) {
                ivMyCard.setClickable(false);
            }
            ivCloseCard.setClickable(true);
            ivCloseDeck.setClickable(true);
            isAllowtoCardClick = false;
        } else if (responseCode == 1) {
            // pick from pile
            ivCloseDeck.setClickable(false);
            ivCloseCard.setClickable(false);
        } else if (responseCode == ResponseCodes.PickFromPileResp) {
            for (ImageView ivMyCard : ivMyCards) {
                ivMyCard.setClickable(true);
            }
            ivCloseCard.setClickable(true);
            isAllowtoCardClick = true;
        } else if (responseCode == ResponseCodes.ThrowCardOnDeckResp) {
            ivCloseCard.setClickable(false);
            ivCloseDeck.setClickable(false);
            for (ImageView ivMyCard : ivMyCards) {
                ivMyCard.setClickable(false);
            }
            isAllowtoCardClick = false;
        } else if (responseCode == 2) {
            // Pick from thrown card
            Logger.print("------------------------------------------------CLicked");
            ivCloseCard.setClickable(false);
            ivCloseDeck.setClickable(false);
        } else if (responseCode == ResponseCodes.PickFromThrownCardsResp) {
            ivCloseCard.setClickable(true);
            ivCloseDeck.setClickable(false);
            for (ImageView ivMyCard : ivMyCards) {
                ivMyCard.setClickable(true);
            }
            isAllowtoCardClick = true;
        }
    }

    private void removeElement(ArrayList<Item_Card> userCardArray,
                               Item_Card removeCard) {
        Iterator<Item_Card> itr = userCardArray.iterator();
        while (itr.hasNext()) {
            Item_Card card = itr.next();
            if (card.equals(removeCard)) {
                itr.remove();
                return;
            }
        }
    }

    private void distributeCardsAnimation(int startIndex,
                                          final int totalCardToDistribute) {
        DealingAnimationCompleted = false;
        int[] userOneCardXYSDC = new int[2];
        int[] userTwoCardXYSDC = new int[2];
        final int[] userThreeCardXYSDC = new int[2];
        frmTemp11.bringToFront();
        Logger.print("ANIMATION FORM " + startIndex + " "
                + totalCardToDistribute);
        ivCardOnDeck.setVisibility(View.GONE);
        ivCardForDistribution.setVisibility(View.VISIBLE);
        ivCloseDeck.setVisibility(View.VISIBLE);
        tvCardCounter.setVisibility(View.VISIBLE);
        ivCardForDistribution.bringToFront();
        if (back_menu_frame.getVisibility() == View.VISIBLE) {
            back_menu_frame.bringToFront();
        }
        if (ivCardForDistribution.getAnimation() != null) {
            ivCardForDistribution.clearAnimation();
        }
        userdeal = 0;
        usercard = new ArrayList<>();
        cardDistributeCounter = 0;
        PileCardCounter = 51;
        isAnimStop = false;
        final int duration = 150;
        final int duration2 = 300;
        ivCloseDeck.getLocationOnScreen(closeDeckXY);
        final int xSDC = closeDeckXY[0];
        final int ySDC = closeDeckXY[1];
        ScaleAnimation scaleAnimSDC = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnimSDC.setDuration(duration);
        scaleAnimSDC.setFillAfter(false);
        // Logger.print("Distribute card..................");
        isAnimStop = false;
        if (DealCardToSeat[LeftSeatIndex]) {
            //  Logger.print("Left =>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            ivUserOneCard.getLocationOnScreen(userOneCardXYSDC);
            int userOneCardX = userOneCardXYSDC[0] - getwidth(25);
            int userOneCardY = userOneCardXYSDC[1] - getheight(38);
            TranslateAnimation tAnimationLeftSDC = new TranslateAnimation(0,
                    userOneCardX - xSDC, 0, userOneCardY - ySDC);
            tAnimationLeftSDC.setDuration(duration);
            tAnimationLeftSDC.setFillAfter(false);
            setLeftSDC = new AnimationSet(true);
            setLeftSDC.addAnimation(scaleAnimSDC);
            setLeftSDC.addAnimation(tAnimationLeftSDC);
        }
        if (DealCardToSeat[RightSeatIndex]) {
            //  Logger.print("Right =>>>>>>>>>>>>>>>>>>>>>>");
            ivUserTwoCard.getLocationOnScreen(userTwoCardXYSDC);
            int userTwoCardX = userTwoCardXYSDC[0] - getwidth(25);
            int userTwoCardY = userTwoCardXYSDC[1] - getheight(38);
            TranslateAnimation tAnimationRightSDC = new TranslateAnimation(0,
                    userTwoCardX - xSDC, 0, userTwoCardY - ySDC);
            tAnimationRightSDC.setDuration(duration);
            tAnimationRightSDC.setFillAfter(false);
            setRightSDC = new AnimationSet(true);
            setRightSDC.addAnimation(scaleAnimSDC);
            setRightSDC.addAnimation(tAnimationRightSDC);
        }
        final TranslateAnimation[] tAnimation = new TranslateAnimation[10];
        if (DealCardToSeat[BottomSeatIndex]) {
            // Logger.print("Bottom =>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>..>>>>")
            int leftmargin = 0;
            if (PreferenceManager.getNumberOfPlayers() == 3) {
                leftmargin = getwidth(100);
            }
            HelpText.setText("");
            HelpText.setVisibility(View.INVISIBLE);
            if (senddata.length() > 0)
                ivScoreboard.setVisibility(View.VISIBLE);

            ivCardForDistribution9.setBackgroundResource(R.drawable.card);
            ivCardForDistribution9.setVisibility(View.INVISIBLE);
            ivCardForDistribution9.getLocationOnScreen(closeDeckXY);
            final int xSDC2 = closeDeckXY[0];
            final int ySDC2 = closeDeckXY[1];

            setBottomSDC = new AnimationSet(false);
            setBottomSDC.setFillAfter(true);
            userdeal = 0;
            for (int c = 0; c < tAnimation.length; c++) {
                ivMyCards[c].getLocationOnScreen(userThreeCardXYSDC);
                int userThreeCardX = userThreeCardXYSDC[0];
                int userThreeCardY = userThreeCardXYSDC[1];
                tAnimation[c] = new TranslateAnimation(0, userThreeCardX - xSDC2, 0, userThreeCardY - ySDC2);
                tAnimation[c].setDuration(duration);
                tAnimation[c].setFillAfter(false);
            }

            try {
                setBottomSDC.setFillAfter(false);
                setBottomSDC.addAnimation(tAnimation[userdeal]);
                ivCardForDistribution9.startAnimation(tAnimation[userdeal]/*setBottomSDC*/);

            } catch (NullPointerException n) {
                n.printStackTrace();
            }

        }
        try {
            for (TranslateAnimation aTAnimation : tAnimation) {
                aTAnimation.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // Logger.print("Strat" + " Bottom");
                        if (GameisOn())
                            Music_Manager.play_CardDealing();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (ivCardForDistribution9.getAnimation() != null)
                            ivCardForDistribution9.clearAnimation();
                        // ivBottomUserCard.setVisibility(View.VISIBLE);
                        PileCardCounter--;
                        Logger.print("CARD COUNTER >>> 3");
                        tvCardCounter.setText("" + PileCardCounter);
                        cardDistributeCounter++;
                        Logger.print(">>> CARDS BOTTOM >>>> " + userdeal + " >> " + card_BottomUser.get(userdeal));
                        usercard.add(card_BottomUser.get(userdeal));
                        Logger.print(">>>>> DEALING Before >>>> " + userdeal);
                        userdeal++;
                        Logger.print(">>>>> DEALING After  >>>> " + userdeal);
                        redrawCardDealing(usercard);

                        if (!isAnimStop) {
                            if (cardDistributeCounter >= totalCardToDistribute) {
                                DealingAnimationCompleted = true;
                                if (ivCardForDistribution.getAnimation() != null) {
                                    ivCardForDistribution.clearAnimation();
                                }
                                if (ivCardForDistribution9.getAnimation() != null) {
                                    ivCardForDistribution9.clearAnimation();
                                }
                                ivBottomUserCard.setVisibility(View.INVISIBLE);
                                ivCardForDistribution.setVisibility(View.GONE);
                                ivCardForDistribution9.setVisibility(View.GONE);
                                cardDistributeCounter = 0;
                                card_BottomUser = new ArrayList<>(Sort(
                                        card_BottomUser, BottomSeatIndex));
                                Logger.print("CARD COUNTER >>> 4");
                                tvCardCounter.setText("" + PileCardCounter);
                                if (!DealCardToSeat[RightSeatIndex]) {
                                    ivUserTwoCard.setVisibility(View.INVISIBLE);
                                }
                                if (!DealCardToSeat[LeftSeatIndex]) {
                                    ivUserOneCard.setVisibility(View.INVISIBLE);
                                }
                                reDrawUserCards("");
                            } else {
                                if (ivCardForDistribution.getAnimation() != null)
                                    ivCardForDistribution.clearAnimation();

                                if (ivCardForDistribution9.getAnimation() != null)
                                    ivCardForDistribution9.clearAnimation();
                                ivCardForDistribution9.setVisibility(View.GONE);

                                if (DealCardToSeat[LeftSeatIndex] && setLeftSDC != null)
                                    ivCardForDistribution
                                            .startAnimation(setLeftSDC);
                                else if (DealCardToSeat[RightSeatIndex] && setRightSDC != null)
                                    ivCardForDistribution
                                            .startAnimation(setRightSDC);
                                else {
                                    if (ivCardForDistribution.getAnimation() != null) {
                                        ivCardForDistribution.clearAnimation();
                                    }
                                    if (ivCardForDistribution9.getAnimation() != null) {
                                        ivCardForDistribution9.clearAnimation();
                                    }
                                    ivUserOneCard.setVisibility(View.INVISIBLE);
                                    ivUserTwoCard.setVisibility(View.INVISIBLE);
                                    ivCardForDistribution
                                            .setVisibility(View.GONE);
                                    ivCardForDistribution9
                                            .setVisibility(View.GONE);
                                }
                            }
                        } else {
                            cardDistributeCounter = 0;
                            int userCount = 0;
                            for (boolean b : DealCardToSeat) {
                                if (b)
                                    userCount++;
                            }
                            if (userCount == 2) {
                                PileCardCounter = (51 - (cardsize * 2));
                            } else if (userCount == 3) {
                                PileCardCounter = (51 - (cardsize * 3));
                            }
                            Logger.print("CARD COUNTER >>> 5");
                            tvCardCounter.setText("" + PileCardCounter);
                            if (ivCardForDistribution.getAnimation() != null) {
                                ivCardForDistribution.clearAnimation();
                            }
                            if (ivCardForDistribution9.getAnimation() != null) {
                                ivCardForDistribution9.clearAnimation();
                            }
                            ivCardForDistribution.setVisibility(View.GONE);
                            ivCardForDistribution9.setVisibility(View.GONE);
                            card_BottomUser = new ArrayList<>(Sort(card_BottomUser, BottomSeatIndex));
                            reDrawUserCards("");
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();

            {
                if (ivCardForDistribution9.getAnimation() != null)
                    ivCardForDistribution9.clearAnimation();

                PileCardCounter--;
                Logger.print("CARD COUNTER >>> 6");
                tvCardCounter.setText("" + PileCardCounter);
                cardDistributeCounter++;
                Logger.print(">>> CARDS BOTTOM >>>> " + userdeal + " >> " + card_BottomUser.get(userdeal));
                usercard.add(card_BottomUser.get(userdeal));
                Logger.print(">>>>> DEALING Before >>>> " + userdeal);
                userdeal++;
                Logger.print(">>>>> DEALING After  >>>> " + userdeal);
                redrawCardDealing(usercard);

                if (!isAnimStop) {
                    if (cardDistributeCounter >= totalCardToDistribute) {
                        DealingAnimationCompleted = true;
                        if (ivCardForDistribution.getAnimation() != null) {
                            ivCardForDistribution.clearAnimation();
                        }
                        if (ivCardForDistribution9.getAnimation() != null) {
                            ivCardForDistribution9.clearAnimation();
                        }
                        ivBottomUserCard.setVisibility(View.INVISIBLE);
                        ivCardForDistribution.setVisibility(View.GONE);
                        ivCardForDistribution9.setVisibility(View.GONE);
                        cardDistributeCounter = 0;
                        card_BottomUser = new ArrayList<>(Sort(
                                card_BottomUser, BottomSeatIndex));
                        Logger.print("CARD COUNTER >>> 7");
                        tvCardCounter.setText("" + PileCardCounter);
                        if (!DealCardToSeat[RightSeatIndex]) {
                            ivUserTwoCard.setVisibility(View.INVISIBLE);
                        }
                        if (!DealCardToSeat[LeftSeatIndex]) {
                            ivUserOneCard.setVisibility(View.INVISIBLE);
                        }
                        reDrawUserCards("");
                    } else {
                        if (ivCardForDistribution.getAnimation() != null)
                            ivCardForDistribution.clearAnimation();

                        if (ivCardForDistribution9.getAnimation() != null)
                            ivCardForDistribution9.clearAnimation();
                        ivCardForDistribution9.setVisibility(View.GONE);

                        if (DealCardToSeat[LeftSeatIndex] && setLeftSDC != null)
                            ivCardForDistribution
                                    .startAnimation(setLeftSDC);
                        else if (DealCardToSeat[RightSeatIndex] && setRightSDC != null)
                            ivCardForDistribution
                                    .startAnimation(setRightSDC);
                        else {
                            if (ivCardForDistribution.getAnimation() != null) {
                                ivCardForDistribution.clearAnimation();
                            }
                            if (ivCardForDistribution9.getAnimation() != null) {
                                ivCardForDistribution9.clearAnimation();
                            }
                            ivUserOneCard.setVisibility(View.INVISIBLE);
                            ivUserTwoCard.setVisibility(View.INVISIBLE);
                            ivCardForDistribution
                                    .setVisibility(View.GONE);
                            ivCardForDistribution9
                                    .setVisibility(View.GONE);
                        }
                    }
                } else {
                    cardDistributeCounter = 0;
                    int userCount = 0;
                    for (boolean b : DealCardToSeat) {
                        if (b)
                            userCount++;
                    }
                    if (userCount == 2) {
                        PileCardCounter = (51 - (cardsize * 2));
                    } else if (userCount == 3) {
                        PileCardCounter = (51 - (cardsize * 3));
                    }
                    Logger.print("CARD COUNTER >>> 8");
                    tvCardCounter.setText("" + PileCardCounter);
                    if (ivCardForDistribution.getAnimation() != null) {
                        ivCardForDistribution.clearAnimation();
                    }
                    if (ivCardForDistribution9.getAnimation() != null) {
                        ivCardForDistribution9.clearAnimation();
                    }
                    ivCardForDistribution.setVisibility(View.GONE);
                    ivCardForDistribution9.setVisibility(View.GONE);
                    card_BottomUser = new ArrayList<>(Sort(card_BottomUser, BottomSeatIndex));
                    reDrawUserCards("");
                }
            }
        }
        if (setLeftSDC != null) {
            setLeftSDC.getAnimations().get(1)
                    .setAnimationListener(new AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            Logger.print("Strat   Left");
                            if (GameisOn())
                                Music_Manager.play_CardDealing();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if (ivCardForDistribution.getAnimation() != null)
                                ivCardForDistribution.clearAnimation();
                            PileCardCounter--;
                            Logger.print("CARD COUNTER >>> 9");
                            tvCardCounter.setText("" + PileCardCounter);
                            cardDistributeCounter++;
                           /* Logger.print("Card DistrinBute Left=> "
                                    + cardDistributeCounter + " " + PileCardCounter);*/
                            if (!isAnimStop) {
                                if (cardDistributeCounter >= totalCardToDistribute) {
                                    DealingAnimationCompleted = true;
                                    if (ivCardForDistribution.getAnimation() != null) {
                                        ivCardForDistribution.clearAnimation();
                                    }
                                    ivCardForDistribution.setVisibility(View.GONE);
                                    ivBottomUserCard.setVisibility(View.INVISIBLE);
                                    if (!DealCardToSeat[RightSeatIndex]) {
                                        ivUserTwoCard.setVisibility(View.INVISIBLE);
                                    }
                                    if (!DealCardToSeat[LeftSeatIndex]) {
                                        ivUserOneCard.setVisibility(View.INVISIBLE);
                                    }
                                    Logger.print("CARD COUNTER >>> 10");
                                    tvCardCounter.setText("" + PileCardCounter);
                                    cardDistributeCounter = 0;
                                    card_BottomUser = new ArrayList<>(Sort(
                                            card_BottomUser, BottomSeatIndex));
                                    reDrawUserCards("");
                                } else {
//                                    if (ivUserOneCard.getVisibility() != View.VISIBLE) {
                                    ivUserOneCard.setVisibility(View.INVISIBLE);
//                                    }
                                    if (ivCardForDistribution.getAnimation() != null)
                                        ivCardForDistribution.clearAnimation();
                                    /*if (DealCardToSeat[RightSeatIndex])
                                        Logger.print("Right True 3");
                                    else if (DealCardToSeat[BottomSeatIndex])
                                        Logger.print("Bottom True 4");*/
                                    if (DealCardToSeat[RightSeatIndex] && setRightSDC != null)
                                        ivCardForDistribution
                                                .startAnimation(setRightSDC);
                                    else if (DealCardToSeat[BottomSeatIndex]) {
                                        Logger.print(">>>> DEALING >>> Right >>> " + userdeal);
                                        ivCardForDistribution9
                                                .startAnimation(tAnimation[userdeal]/*setBottomSDC*/);
                                    } else {
                                        if (ivCardForDistribution.getAnimation() != null) {
                                            ivCardForDistribution.clearAnimation();
                                        }
                                        ivCardForDistribution.setVisibility(View.GONE);
                                        ivUserOneCard.setVisibility(View.INVISIBLE);
                                        ivUserTwoCard.setVisibility(View.INVISIBLE);
                                    }
                                }
                            } else {
                                cardDistributeCounter = 0;
                                if (ivCardForDistribution.getAnimation() != null) {
                                    ivCardForDistribution.clearAnimation();
                                }
                                ivCardForDistribution.setVisibility(View.GONE);
                                int userCount = 0;
                                for (boolean b : DealCardToSeat) {
                                    if (b)
                                        userCount++;
                                }
                                if (userCount == 2) {
                                    PileCardCounter = (51 - (cardsize * 2));
                                } else if (userCount == 3) {
                                    PileCardCounter = (51 - (cardsize * 3));
                                }
                                Logger.print("CARD COUNTER >>> 11");
                                tvCardCounter.setText("" + PileCardCounter);
                                card_BottomUser = new ArrayList<>(Sort(
                                        card_BottomUser, BottomSeatIndex));
                                reDrawUserCards("");
                            }
                        }
                    });
        }
        if (setRightSDC != null) {
            setRightSDC.getAnimations().get(1)
                    .setAnimationListener(new AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            Logger.print("Strat Right========");
                            if (GameisOn())
                                Music_Manager.play_CardDealing();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if (ivCardForDistribution.getAnimation() != null)
                                ivCardForDistribution.clearAnimation();
                            PileCardCounter--;
                            Logger.print("CARD COUNTER >>> 12");
                            tvCardCounter.setText("" + PileCardCounter);
                            cardDistributeCounter++;
                            // Logger.print("Card DistrinBute Right=> "
                            // + cardDistributeCounter + " " + PileCardCounter);
                            if (!isAnimStop) {
                                //Logger.print(">>>> 1");
                                if (cardDistributeCounter >= totalCardToDistribute) {
                                    //Logger.print(">>>> 2");
                                    DealingAnimationCompleted = true;
                                    if (ivCardForDistribution.getAnimation() != null) {
                                        ivCardForDistribution.clearAnimation();
                                    }
                                    ivCardForDistribution.setVisibility(View.GONE);
                                    ivBottomUserCard.setVisibility(View.INVISIBLE);
                                    if (!DealCardToSeat[RightSeatIndex]) {
                                        ivUserTwoCard.setVisibility(View.INVISIBLE);
                                    }
                                    if (!DealCardToSeat[LeftSeatIndex]) {
                                        ivUserOneCard.setVisibility(View.INVISIBLE);
                                    }
                                    Logger.print("CARD COUNTER >>> 13");
                                    tvCardCounter.setText("" + PileCardCounter);
                                    cardDistributeCounter = 0;
                                    //card_BottomUser = SortMyCardbyColor(card_BottomUser);
                                    card_BottomUser = new ArrayList<>(Sort(
                                            card_BottomUser, BottomSeatIndex));
                                    reDrawUserCards("");
                                } else {
                                    // Logger.print(">>>> 3");
//                                    if (ivUserTwoCard.getVisibility() != Vie) {
                                    ivUserTwoCard.setVisibility(View.INVISIBLE);
//                                    }
                                    if (ivCardForDistribution.getAnimation() != null)
                                        ivCardForDistribution.clearAnimation();
                                    /*if (DealCardToSeat[BottomSeatIndex])
                                        Logger.print("Bottom True 5");
                                    else if (DealCardToSeat[LeftSeatIndex])
                                        Logger.print("Left True 6");*/
                                    if (DealCardToSeat[BottomSeatIndex]) {
                                        Logger.print(">>>> DEALING >>> Right >>> " + userdeal);
                                        ivCardForDistribution9
                                                .startAnimation(tAnimation[userdeal]/*setBottomSDC*/);
                                    } else if (DealCardToSeat[LeftSeatIndex] && setLeftSDC != null)
                                        ivCardForDistribution
                                                .startAnimation(setLeftSDC);
                                    else {
                                        if (ivCardForDistribution.getAnimation() != null) {
                                            ivCardForDistribution.clearAnimation();
                                        }
                                        ivCardForDistribution
                                                .setVisibility(View.GONE);
                                        ivUserOneCard.setVisibility(View.INVISIBLE);
                                        ivUserTwoCard.setVisibility(View.INVISIBLE);
                                    }
                                }
                            } else {
                                // Logger.print(">>>> 4");
                                int userCount = 0;
                                for (boolean b : DealCardToSeat) {
                                    if (b)
                                        userCount++;
                                }
                                if (userCount == 2) {
                                    PileCardCounter = (51 - (cardsize * 2));
                                } else if (userCount == 3) {
                                    PileCardCounter = (51 - (cardsize * 3));
                                }
                                Logger.print("CARD COUNTER >>> 14");
                                tvCardCounter.setText("" + PileCardCounter);
                                cardDistributeCounter = 0;
                                if (ivCardForDistribution.getAnimation() != null) {
                                    ivCardForDistribution.clearAnimation();
                                }
                                ivCardForDistribution.setVisibility(View.GONE);
                                card_BottomUser = new ArrayList<>(Sort(
                                        card_BottomUser, BottomSeatIndex));
                                //card_BottomUser = SortMyCardbyColor(card_BottomUser);
                                reDrawUserCards("");
                            }
                        }
                    });
        }
        if (DealCardToSeat[BottomSeatIndex]) {
            ivCardForDistribution9.startAnimation(/*tAnimation[userdeal]*/setBottomSDC);
        } else if (DealCardToSeat[LeftSeatIndex]) {
            ivCardForDistribution.startAnimation(setLeftSDC);
        } else if (DealCardToSeat[RightSeatIndex]) {
            ivCardForDistribution.startAnimation(setRightSDC);
        }
    }

    private ArrayList<Item_Card> sortingTrioandSeq(boolean checkOnlyLastCard) {
        ArrayList<Item_Card> valiCards = new ArrayList<>();
        ArrayList<Item_Card> invaliCards = new ArrayList<>();
        ArrayList<ArrayList<Integer>> ar = new ArrayList<>();
        ArrayList<Item_Card> sortedArray = new ArrayList<>();
        ArrayList<Item_Card> front = new ArrayList<>();
        ArrayList<Item_Card> back = new ArrayList<>();
        for (int i = 0; i < card_BottomUser.size(); i++) {
            if (card_BottomUser.get(i).getIsValidGroup()) {
                valiCards.add(card_BottomUser.get(i));
            } else {
                invaliCards.add(card_BottomUser.get(i));
            }
        }
        for (int i = 0; i < valiCards.size(); i++) {
            int num = valiCards.get(i).getGroupNumber();
            ArrayList<Integer> array = new ArrayList<>();
            for (int j = i; j < valiCards.size(); j++) {
                if (num == valiCards.get(j).getGroupNumber()) {
                    array.add(j);
                    i = j;
                } else {
                    break;
                }
            }
            ar.add(array);
        }
        boolean isinSequence, isInSet;
        int arSize = ar.size();
        for (int i = 0; i < arSize; i++) {
            isinSequence = false;
            isInSet = checkForSet(ar, valiCards, i);
            Logger.print("AAAAAAAAAsetn for validatio SET => " + ar.toString());
            if (!isInSet) {
                isinSequence = checkForSequenceNew(ar, valiCards, i);
            }
            Logger.print("AAAAAAAAAisInSequence => " + i + " " + isinSequence + " " + isInSet);
            if (isInSet) {
                for (int c = invaliCards.size() - 1; c >= 0; c--) {
                    Logger.print("AAAAAAAAisInSetCompare => " + valiCards.get(ar.get(i).get(0)).getCardValue() + " " + invaliCards.get(c).getCardValue());
                    if (valiCards.get(ar.get(i).get(0)).getCardValue() == invaliCards.get(c).getCardValue()) {
                        //valiCards.add(ar.get(i).size(), invaliCards.get(c));
                        sortedArray.add(invaliCards.get(c));
                        invaliCards.remove(c);
                    }
                }
                int start = ar.get(i).get(0);
                int end = start + ar.get(i).size();
                for (int k = start; k < end; k++) {
                    sortedArray.add(valiCards.get(k));
                }
            } else if (isinSequence) {
                front.clear();
                back.clear();
                Collections.sort(invaliCards);
                //Collections.reverse(invaliCards);
                Logger.print("_SORTING 11111->>>>>>>>>>>>>>>>>>>>>>>>>>> 1 => " + invaliCards.toString());
                Item_Card fCard = valiCards.get(ar.get(i).get(0));
                for (int c = invaliCards.size() - 1; c >= 0; c--) {
                    if (fCard.getCardColor() == invaliCards.get(c).getCardColor()) {
                        Logger.print("AAAAAAAAisInSeqCompare => 1 " + (fCard.getCardValue() - 1) + " " + (invaliCards.get(c).getCardValue()));
                        if (fCard.getCardValue() - 1 == invaliCards.get(c).getCardValue()) {
                            //valiCards.add(ar.get(i).get(0), invaliCards.get(c));
                            front.add(invaliCards.get(c));
                            fCard = invaliCards.get(c);
                            invaliCards.remove(c);
                        }
                    }
                }
                Logger.print("Front Card => " + front.toString());
                //Collections.sort(invaliCards);
                Collections.reverse(invaliCards);
                Item_Card bCard = valiCards.get(ar.get(i).get(ar.get(i).size() - 1));
                for (int c = invaliCards.size() - 1; c >= 0; c--) {
                    Logger.print("FFFFFFFFFFFFFFF i and ar => " + i + " " + ar);
                    Logger.print("FFFFFFFFFFFFFFF pos 1 => " + ar.get(i).get(ar.get(i).size() - 1) + " " + valiCards.size());
                    Logger.print("FFFFFFFFFFFFFFF invalid card => " + c + " " + invaliCards.size() + " " + invaliCards.toString());
                    if (bCard.getCardColor() == invaliCards.get(c).getCardColor()) {
                        Logger.print("AAAAAAAAisInSeqCompare => 2 " + bCard.getCardValue() + " " + (invaliCards.get(c).getCardValue() + 1));
                        if ((bCard.getCardValue() + 1) == invaliCards.get(c).getCardValue()) {
                            //valiCards.add(ar.get(i).get(ar.get(i).size() - 1), invaliCards.get(c));
                            back.add(invaliCards.get(c));
                            bCard = invaliCards.get(c);
                            invaliCards.remove(c);
                        }
                    }
                }
                Collections.reverse(front);
                sortedArray.addAll(front);
                int start = ar.get(i).get(0);
                int end = start + ar.get(i).size();
                Logger.print("group array => " + i + " " + ar);
                Logger.print("STart => " + start + " End => " + end + " size => " + valiCards.size());
                for (int k = start; k < end; k++) {
                    //Logger.print("Index number =>>>>>> " + ar.get(i).get(k));
                    sortedArray.add(valiCards.get(k));
                }
                sortedArray.addAll(back);
            }
        }
        if (isDefaultSequencewise) {
            Collections.sort(invaliCards);
        } else {
            SortTripleWise(invaliCards);
        }
        sortedArray.addAll(invaliCards);
        Logger.print("AAAAAAAAinvalidCards =>>>>>>>>>>>> " + sortedArray.toString());
        return sortedArray;
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("scoreboard.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void redrawCardDealing(ArrayList<Item_Card> userCard) {
        for (ImageView ivMyCard : ivMyCards) {
            if (ivMyCard.getAnimation() != null) {
                ivMyCard.clearAnimation();
            }
            ivMyCard.setVisibility(View.INVISIBLE);
        }
        Logger.print(">>> USERCARD >>> " + userCard);
        if (card_BottomUser.size() <= 7 || card_BottomUser.size() == 8) {
            LayoutParams frm = (LayoutParams) findViewById(
                    R.id.fmuserCards).getLayoutParams();
            frm.leftMargin = getwidth(100);
            frm.bottomMargin = getheight(-40);
            frmUserCards.setLayoutParams(frm);
            LayoutParams frmGroup = (LayoutParams) groupBgLinear.getLayoutParams();
            frmGroup.leftMargin = getwidth(179);
            frmGroup.topMargin = getheight(40);
            groupBgLinear.setLayoutParams(frmGroup);
        } else {
            LayoutParams frm = (LayoutParams) findViewById(
                    R.id.fmuserCards).getLayoutParams();
            frm.leftMargin = getwidth(0);
            frm.bottomMargin = getheight(-40);
            frmUserCards.setLayoutParams(frm);
            LayoutParams frmGroup = (LayoutParams) groupBgLinear.getLayoutParams();
            frmGroup.leftMargin = getwidth(79);
            frmGroup.topMargin = getheight(40);
            groupBgLinear.setLayoutParams(frmGroup);
        }
        int usercardhalf = userCard.size() / 2;
        int strt = (card_BottomUser.size() / 2) - (usercardhalf);
        int end = (card_BottomUser.size() / 2) + (usercard.size() - usercardhalf);
        Logger.print(">>> USERCARD strt>>> " + strt);
        Logger.print(">>> USERCARD end >>> " + end);
        int ci = 0;
        for (int i = 0; i < userCard.size(); i++) {
            ivMyCards[i].setVisibility(View.VISIBLE);
            Item_Card card = userCard.get(ci);
            ci++;
            String s1 = getCardString(card);
            int indexOfs1 = Arrays.asList(cardString).indexOf(s1);
            //ImageView ivCard = ivMyCards[i];
            ivMyCards[i].setImageResource(CardDefault[indexOfs1]);
        }
    }

    private void reDrawUserCards(String pickCard) {
        // TODO Auto-generated method stub
        int pickCardIndex = -1;
        if (ivBottomUserCard.getVisibility() == View.VISIBLE) {
            ivBottomUserCard.setVisibility(View.INVISIBLE);
        }
        tvdeadwoodScore.setText("" + BottomDeadWood);
        tvdeadwoodScore2.setText("/" + required_knock);
        int myCardsImagesSize = ivMyCards.length;
        for (ImageView ivMyCard : ivMyCards) {
            if (ivMyCard.getAnimation() != null) {
                ivMyCard.clearAnimation();
            }
            ivMyCard.setVisibility(View.INVISIBLE);
        }
        frmUserCards.invalidate();
        frmUserCards.bringToFront();
        groupBgLinear.bringToFront();
        if (card_BottomUser.size() <= 7 || card_BottomUser.size() == 8) {
            LayoutParams frm = (LayoutParams) findViewById(
                    R.id.fmuserCards).getLayoutParams();
            frm.leftMargin = getwidth(100);
            frm.bottomMargin = getheight(-40);
            frmUserCards.setLayoutParams(frm);
            LayoutParams frmGroup = (LayoutParams) groupBgLinear.getLayoutParams();
            frmGroup.leftMargin = getwidth(179);
            frmGroup.topMargin = getheight(40);
            groupBgLinear.setLayoutParams(frmGroup);
        } else {
            LayoutParams frm = (LayoutParams) findViewById(
                    R.id.fmuserCards).getLayoutParams();
            frm.leftMargin = getwidth(0);
            frm.bottomMargin = getheight(-40);
            frmUserCards.setLayoutParams(frm);
            LayoutParams frmGroup = (LayoutParams) groupBgLinear.getLayoutParams();
            frmGroup.leftMargin = getwidth(79);
            frmGroup.topMargin = getheight(40);
            groupBgLinear.setLayoutParams(frmGroup);
        }
        boolean n = DrawGroup(false, pickCard);
        selectedCard.clear();
        int w, h;
        for (int i = 0; i <= card_BottomUser.size() && i < 11 && i < myCardsImagesSize; i++) {
            ImageView ivCard = ivMyCards[i];
            LayoutParams frm = ((LayoutParams) ivCard
                    .getLayoutParams());
            frm.width = CARD_WIDTH;
            frm.height = CARD_HEIGHT;
            frm.leftMargin = getwidth(68) * (i + 1);
            frm.topMargin = CARD_HEIGHT * 5 / 190;
            frm.bottomMargin = 0;
            frm.gravity = Gravity.BOTTOM;
            w = getwidth(140);
            posX[i] = w + getwidth(68) * (i + 1);
            if (card_BottomUser.size() == 10) {
                frm.topMargin = getheight(30);
            } else if (card_BottomUser.size() == 11) {
                frm.topMargin = getheight(30);
            } else if (card_BottomUser.size() == 7) {
                frm.topMargin = getheight(50);
            } else if (card_BottomUser.size() == 8) {
                frm.topMargin = getheight(50);
            }
            ivCard.setLayoutParams(frm);
            ivCard.bringToFront();
            ivCard.setVisibility(View.VISIBLE);
            ivCard.setPadding(0, 0, 0, 0);
            ivCard.setImageResource(0);
            if (i < card_BottomUser.size()) {
                Item_Card card = card_BottomUser.get(i);
                String s1 = getCardString(card);
                if (s1.contentEquals(pickCard)) {
                    pickCardIndex = i;
                }
            } else {
                ivCard.setVisibility(View.INVISIBLE);
            }
        }
        if (pickCardIndex >= 0 && !isTutorial && !isgin) {
            LayoutParams layoutParams = (LayoutParams) ivMyCards[pickCardIndex]
                    .getLayoutParams();
            w = (int) (CARD_WIDTH * 1.2);
            h = (int) (CARD_HEIGHT * 1.2);
            layoutParams.width = w;
            layoutParams.height = h;
            ivMyCards[pickCardIndex].setLayoutParams(layoutParams);
            //ivMyCards[pickCardIndex].setPadding(getwidth(1), getwidth(6), getwidth(4), getwidth(1));
            ivMyCards[pickCardIndex].setImageResource(R.drawable.selectcard);
            selectedCard.add(ivMyCards[pickCardIndex]);
            if (showControls) {
                btnDiscard.setVisibility(View.VISIBLE);
                ivHandOpenCard.setVisibility(View.VISIBLE);
                ivHandOpenCard.startAnimation(handAnimation);
            }
        }
        Logger.print(card_BottomUser.toString());
        /*try {
            JSONObject jCards = new JSONObject();
            JSONObject knockCards = getSetSequence(false);
            jCards.put(Parameters.Cards, knockCards);
            EmitManager.Process(jCards, "SKGC");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    private boolean DrawGroup(boolean isToConsiderLastCardForSort, String pickCard) {
        try {
            int userCardSize = card_BottomUser.size();
            ArrayList<ArrayList<Integer>> ar = new ArrayList<>();
            if (c.isAutoSortOn) {
                card_BottomUser = new ArrayList<>(Sort(
                        card_BottomUser, BottomSeatIndex));
            } else {
                setAllCardInSequence(pickCard);
            }
            for (int i = 0; i < userCardSize; i++) {
                int num = card_BottomUser.get(i).getGroupNumber();
                ArrayList<Integer> array = new ArrayList<>();
                for (int j = i; j < card_BottomUser.size(); j++) {
                    if (num == card_BottomUser.get(j).getGroupNumber()) {
                        array.add(j);
                        i = j;
                    } else {
                        break;
                    }
                }
                ar.add(array);
            }
            boolean isinSequence, isInSet;
            int arSize = ar.size();
            //if (isToConsiderLastCardForSort) {
            for (int i = 0; i < arSize; i++) {
                isinSequence = false;
                isInSet = checkForSet(ar, card_BottomUser, i);
                if (!isInSet) {
                    isinSequence = checkForSequenceNew(ar, card_BottomUser, i);
                }
                if (isInSet || isinSequence) {
                    for (int t = 0; t < ar.get(i).size(); t++) {
                        Item_Card icCard = card_BottomUser.get(ar.get(i).get(t));
                        icCard.setIsValidGroup(true);
                    }
                } else {
                    for (int t = 0; t < ar.get(i).size(); t++) {
                        Item_Card icCard = card_BottomUser.get(ar.get(i).get(t));
                        icCard.setIsValidGroup(false);
                    }
                }
            }
            arSize = ar.size();
            BottomDeadWood = 0;
            int groupColorCounter = 0;
            for (int i = 0; i < arSize; i++) {
                int start = ar.get(i).get(0);
                int end = start + ar.get(i).size();
                isinSequence = card_BottomUser.get(start).getIsValidGroup();
                if (isinSequence) {
                    for (int j = start; j < end; j++) {
                        try {
                            Item_Card card = card_BottomUser.get(j);
                            String s1 = getCardString(card);
                            int indexOfs1 = Arrays.asList(cardString).indexOf(s1);
                            ImageView ivCard = ivMyCards[j];
                            if (groupColorCounter == 0) {
                                ivCard.setBackgroundResource(Select_Card[indexOfs1]);
                            } else if (groupColorCounter == 1) {
                                ivCard.setBackgroundResource(Green_Card[indexOfs1]);
                            } else if (groupColorCounter == 2) {
                                ivCard.setBackgroundResource(Blue_Card[indexOfs1]);
                            }
                            ivCard.setTag(s1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    groupColorCounter++;
                    if (groupColorCounter == 3) {
                        groupColorCounter = 0;
                    }
                } else {
                    try {
                        BottomDeadWood += getDeadwoodSumofGroup(ar, i);
                        for (int j = start; j < end; j++) {
                            Item_Card card = card_BottomUser.get(j);
                            String s1 = getCardString(card);
                            int indexOfs1 = Arrays.asList(cardString).indexOf(s1);
                            ImageView ivCard = ivMyCards[j];
                            ivCard.setBackgroundResource(CardDefault[indexOfs1]);
                            ivCard.setTag(s1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (card_BottomUser.size() > cardsize) {
                BottomDeadWood = BottomDeadWood - highCardCalue;
            }
            if (DealCardToSeat[BottomSeatIndex] && card_BottomUser.size() > 0) {
                DeadwoodCount();
                highCardCalue = 0;
                if (ivScore.getVisibility() != View.VISIBLE) {
                    ivScore.setVisibility(View.VISIBLE);
                }
                tvdeadwoodScore.setText("" + BottomDeadWood);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void setAllCardInSequence(String pickCard) {
        GroupnumberCounter = 0;
        int startIndex = 0;
        int cardSize = card_BottomUser.size();
        ArrayList<Integer> validSeq = new ArrayList<>();
        ArrayList<Integer> validSet = new ArrayList<>();
        boolean isInsequence, isLastCardConsidered = false;
        ArrayList<Item_Card> unmatchedCard = new ArrayList<>();
        if (pickCard != null && !TextUtils.isEmpty(pickCard)) {
            int lastIndex = card_BottomUser.size() - 1;
            Item_Card lastCard = null;
            if (lastIndex != -1)
                lastCard = card_BottomUser.get(lastIndex);
            for (int i = 0; i < card_BottomUser.size(); i++) {
                if (!card_BottomUser.get(i).getIsValidGroup())
                    unmatchedCard.add(card_BottomUser.get(i));
            }
            boolean isValidCardFound = false;
            int groupNumber = -2;
            unmatchedCard = new ArrayList<>(Sort(unmatchedCard, BottomSeatIndex));
            for (int i = 0; i < unmatchedCard.size(); i++) {
                if (unmatchedCard.get(i).getIsValidGroup()) {
                    try {
                        if (unmatchedCard.get(i).equals(lastCard)) {
                            isValidCardFound = true;
                            groupNumber = unmatchedCard.get(i).getGroupNumber();
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (isValidCardFound && groupNumber != -2) {
                for (int i = unmatchedCard.size() - 1; i >= 0; i--) {
                    if (unmatchedCard.get(i).getGroupNumber() == groupNumber) {
                        card_BottomUser.remove(unmatchedCard.get(i));
                        card_BottomUser.add(0, unmatchedCard.get(i));
                        isLastCardConsidered = true;
                    }
                }
            }
        }
        for (int i = 0; i < card_BottomUser.size(); i++) {
            card_BottomUser.get(i).setGroupNumber(-1);
        }
        for (int i = startIndex; i < cardSize - 1; i++) {
            isInsequence = false;
            validSeq.clear();
            validSeq.add(i);
            for (int j = i; j < cardSize - 1; j++) {
                try {
                    if (card_BottomUser.get(j + 1).getCardValue() - card_BottomUser.get(j).getCardValue() == 1 && card_BottomUser.get(j + 1).getCardColor() == card_BottomUser.get(j).getCardColor()) {
                        validSeq.add(j + 1);
//                        if (card_BottomUser.size() < (j + 3)) {
                        if ((j + 3) < card_BottomUser.size()) {
                            if (((card_BottomUser.get(j + 1).getCardValue() == card_BottomUser.get(j + 2).getCardValue()) &&
                                    (card_BottomUser.get(j + 1).getCardValue() == card_BottomUser.get(j + 3).getCardValue()))) {
                                if (!((j + 4) < card_BottomUser.size() && (card_BottomUser.get(j + 1).getCardValue() == card_BottomUser.get(j + 4).getCardValue())))
                                    validSeq.clear();
                                break;
                            }
                        }
                    } else {
                        break;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
            if (validSeq.size() >= 3) {
                //Logger.print("_NSORTING ->>>>>>>>>>>>>>>>>>>>>>>>>>> 1 => " + (card_BottomUser.get(validSeq.get(0)).getCardValue() - 1) + " == " + (card_BottomUser.get(card_BottomUser.size() - 1).getCardValue()));
                if (pickCard != null && !TextUtils.isEmpty(pickCard)) {
                    if (!isLastCardConsidered && (card_BottomUser.get(validSeq.get(0)).getCardValue() - 1) == card_BottomUser.get(card_BottomUser.size() - 1).getCardValue() &&
                            card_BottomUser.get(validSeq.get(0)).getCardColor() == card_BottomUser.get(card_BottomUser.size() - 1).getCardColor()) {
                        //Logger.print("_NSORTING ->>>>>>>>>>>>>>>>>>>>>>>>>>> 2 => ");
                        card_BottomUser.add(validSeq.get(0), card_BottomUser.get(card_BottomUser.size() - 1));
                        validSeq.add(validSeq.get(validSeq.size() - 1) + 1);
                        card_BottomUser.remove(card_BottomUser.size() - 1);
                        isLastCardConsidered = true;
                        //Logger.print("_NSORTING ->>>>>>>>>>>>>>>>>>>>>>>>>>> X2 => " + card_BottomUser.toString());
                    }
                    //Logger.print("_NSORTING ->>>>>>>>>>>>>>>>>>>>>>>>>>> 3 => " + (card_BottomUser.get(validSeq.get(validSeq.size() - 1)).getCardValue() + 1) + " == " + (card_BottomUser.get(card_BottomUser.size() - 1).getCardValue()));
                    if (!isLastCardConsidered && (card_BottomUser.get(validSeq.get(validSeq.size() - 1)).getCardValue() + 1) == card_BottomUser.get(card_BottomUser.size() - 1).getCardValue() &&
                            card_BottomUser.get(validSeq.get(validSeq.size() - 1)).getCardColor() == card_BottomUser.get(card_BottomUser.size() - 1).getCardColor()) {
                        //Logger.print("_NSORTING ->>>>>>>>>>>>>>>>>>>>>>>>>>> 4 => ");
                        card_BottomUser.add(validSeq.get(validSeq.size() - 1) + 1, card_BottomUser.get(card_BottomUser.size() - 1));
                        validSeq.add(validSeq.get(validSeq.size() - 1) + 1);
                        card_BottomUser.remove(card_BottomUser.size() - 1);
                        isLastCardConsidered = true;
                        //Logger.print("_NSORTING ->>>>>>>>>>>>>>>>>>>>>>>>>>> X3 => " + card_BottomUser.toString());
                    }
                }
                GroupnumberCounter++;
                //Logger.print("_NSORTING ->>>>>>>>>>>>>>>>>>>>>>>>>>> XXX => " + validSeq.get(0) + " " + validSeq.get(validSeq.size() - 1));
                try {
                    for (int m = validSeq.get(0); m <= validSeq.get(validSeq.size() - 1); m++) {
                        card_BottomUser.get(m).setGroupNumber(GroupnumberCounter);
                        card_BottomUser.get(m).setCardInPair(true);
                        card_BottomUser.get(m).setIsValidGroup(true);
                        i = m;
                    }
                    isInsequence = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                validSeq.clear();
            }
            if (!isInsequence) {
                validSet.clear();
                validSet.add(i);
                for (int j = i; j < cardSize - 1; j++) {
                    if (card_BottomUser.get(j).getCardValue() == card_BottomUser.get(j + 1).getCardValue()) {
                        validSet.add(j + 1);
                        if ((j + 3) < card_BottomUser.size()) {
                            Logger.print("XXXXXXXXXXX ---- " + validSet.size() + " " + j);
                            if ((((card_BottomUser.get(j + 2).getCardValue() - card_BottomUser.get(j + 1).getCardValue() == 1) && card_BottomUser.get(j + 2).getCardColor() == card_BottomUser.get(j + 1).getCardColor()) &&
                                    (((card_BottomUser.get(j + 3).getCardValue() - card_BottomUser.get(j + 2).getCardValue() == 1)) && (card_BottomUser.get(j + 3).getCardColor() == card_BottomUser.get(j + 2).getCardColor())))) {
                                if ((j + 4 < card_BottomUser.size()) && (((card_BottomUser.get(j + 4).getCardValue() - card_BottomUser.get(j + 3).getCardValue() == 1)) && (card_BottomUser.get(j + 4).getCardColor() == card_BottomUser.get(j + 3).getCardColor()))) {

                                } else {
                                    if (validSet.size() > 0)
                                        validSet.remove(validSet.size() - 1);
                                    Logger.print("XXXXXXXXXXX 2 ---- " + validSet.size() + " " + j);
                                    break;
                                }
                            }
                        }
                    } else {
                        break;
                    }
                }
                if (validSet.size() >= 3) {
                    if (pickCard != null && !TextUtils.isEmpty(pickCard)) {
                        if (!isLastCardConsidered && (card_BottomUser.get(validSeq.get(0)).getCardValue() == card_BottomUser.get(card_BottomUser.size() - 1).getCardValue())) {
                            card_BottomUser.add(validSet.get(0), card_BottomUser.get(card_BottomUser.size() - 1));
                            card_BottomUser.remove(card_BottomUser.size() - 1);
                            validSet.add((validSet.get(validSet.size() - 1) + 1));
                            isLastCardConsidered = true;
                        }
                    }
                    GroupnumberCounter++;
                    try {
                        for (int m = validSet.get(0); m <= validSet.get(validSet.size() - 1); m++) {
                            card_BottomUser.get(m).setGroupNumber(GroupnumberCounter); // EXCEPTION LINE
                            card_BottomUser.get(m).setCardInPair(false);
                            card_BottomUser.get(m).setIsValidGroup(false);
                            i = m;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    validSet.clear();
                }
            }
        }
        boolean increase = true;
        for (int g = 0; g < card_BottomUser.size(); g++) {
            if (card_BottomUser.get(g).getGroupNumber() == -1) {
                if (increase) {
                    GroupnumberCounter++;
                    increase = false;
                }
                card_BottomUser.get(g).setGroupNumber(GroupnumberCounter);
                card_BottomUser.get(g).setCardInPair(false);
                card_BottomUser.get(g).setIsValidGroup(false);
            }
        }
        if (!TextUtils.isEmpty(pickCard)) {
            pickCard = "";
            setAllCardInSequence("");
        }
    }

    private int getDeadwoodSumofGroup(ArrayList<ArrayList<Integer>> ar, int i) {
        int sum = 0;
        for (int j = 0; j < ar.get(i).size(); j++) {
            String cardStr = getCardString(card_BottomUser.get(ar.get(i).get(j)));
            String cardVal = getCardValue(cardStr);
            int cardValue = Integer.parseInt(cardVal);
            int addableValue;
            if (cardValue >= 11)
                addableValue = 10;
            else
                addableValue = cardValue;
            sum += addableValue;
            if (addableValue > highCardCalue) {
                highCardCalue = addableValue;
            }
        }
        return sum;
    }

    private void setCardInSequence(ArrayList<ArrayList<Integer>> ar, ArrayList<Item_Card> userCards, int i) {
        int totalCardsInGroup = ar.get(i).size();
        ArrayList<Item_Card> cardmain = new ArrayList<>();
        ArrayList<Item_Card> sortedCard = new ArrayList<>();
        int diff;
        for (int j = 0; j < totalCardsInGroup; j++) {
            cardmain.add(userCards.get(ar.get(i).get(j)));
        }
        Collections.sort(cardmain);
        int groupSizeOfNonJokerCard = cardmain.size();
        if (groupSizeOfNonJokerCard > 0) {
            sortedCard.add(cardmain.get(0));
        }
        for (int y = 0; y < groupSizeOfNonJokerCard - 1; y++) {
            diff = cardmain.get(y + 1).getCardValue() - cardmain.get(y).getCardValue();
            if (diff == 1) {
                sortedCard.add(cardmain.get(y + 1));
            }
        }
        for (int x = 0; x < totalCardsInGroup; x++) {
            userCards.set(ar.get(i).get(x), sortedCard.get(x));
        }
    }

    public String getCardValue(String card) {
        if (card != null) {
            String[] splitedCard = card.split("-");
            return splitedCard[1];
        }
        return "";
    }

    public int getCardValue2(String card) {
        if (card != null) {
            String[] splitedCard = card.split("-");
            int value = Integer.valueOf(splitedCard[1]);
            return value;
        }
        return 0;
    }

    private boolean checkForSet(ArrayList<ArrayList<Integer>> ar,
                                ArrayList<Item_Card> userCards, int i) {
        // TODO Auto-generated method stub
        ArrayList<Item_Card> cardar = new ArrayList<>();
        ArrayList<Item_Card> cardmain = new ArrayList<>();
        if (ar.get(i).size() > 4) {
            return false;
        }
        for (int j = 0; j < ar.get(i).size(); j++) {
            cardmain.add(userCards.get(ar.get(i).get(j)));
        }
        Collections.sort(cardmain);
        for (int j = 0; j < cardmain.size() - 1; j++) {
            if (cardmain.get(j).getCardColor() != cardmain.get(j + 1).getCardColor()) {
                if (cardmain.get(j).getCardValue() == cardmain.get(j + 1).getCardValue()) {
                    cardar.add(cardmain.get(j));
                }
            }
        }
        return cardar.size() == ar.get(i).size() && cardar.size() > 2 || cardar.size() == ar.get(i).size() - 1 && cardar.size() >= 2;
    }

    private boolean checkForSequenceNew(ArrayList<ArrayList<Integer>> ar, ArrayList<Item_Card> userCards, int i) {
        int totalCardsInGroup = ar.get(i).size();
        if (totalCardsInGroup <= 2) {
            return false;
        }
        ArrayList<Item_Card> cardmain = new ArrayList<>();
        int jokerCounter = 0;
        for (int j = 0; j < totalCardsInGroup; j++) {
            cardmain.add(userCards.get(ar.get(i).get(j)));
        }
        int diff;
        int seqCounter = 1;
        int groupSizeOfNonJokerCard = cardmain.size();
        for (int k = 0; k < groupSizeOfNonJokerCard - 1; k++) {
            if (cardmain.get(k + 1).getCardColor() != cardmain.get(k).getCardColor()) {
                return false;
            }
            diff = cardmain.get(k + 1).getCardValue() - cardmain.get(k).getCardValue();
            if (diff == 1) {
                seqCounter++;
            } else if (diff == 0) {
                return false;
            } else if (diff > 1) {
                return false;
            }
        }
        return totalCardsInGroup == (seqCounter + jokerCounter);
    }

    private void redrawRobotCard(int seatIndex) {
        if (isRobotCardShow) {
            if (seatIndex == LeftSeatIndex) {
                for (ImageView ivLeftCard : ivLeftRobotCard) {
                    ivLeftCard.setVisibility(View.INVISIBLE);
                }
                Logger.print(">>> Left Cards >>> " + card_LeftUser);
                Logger.print(">>> Left Cards >>> " + card_LeftUser.size());
                for (int i = 0; i < card_LeftUser.size(); i++) {
                    ivLeftRobotCard[i].setVisibility(View.VISIBLE);
                    int index = Arrays.asList(cardString).indexOf(getCardString(card_LeftUser.get(i)));
                    ivLeftRobotCard[i].setBackgroundResource(CardDefault[index]);
                }
            } else if (seatIndex == RightSeatIndex) {
                for (ImageView ivRightCard : ivRightRobotCard) {
                    ivRightCard.setVisibility(View.INVISIBLE);
                }
                Logger.print(">>> Right Cards >>> " + card_RightUser);
                Logger.print(">>> Right Cards >>> " + card_RightUser.size());
                for (int i = 0; i < card_RightUser.size(); i++) {
                    ivRightRobotCard[i].setVisibility(View.VISIBLE);
                    int index = Arrays.asList(cardString).indexOf(getCardString(card_RightUser.get(i)));
                    ivRightRobotCard[i].setBackgroundResource(CardDefault[index]);
                }
            }
        }
    }

    private void setScreen() {
        // TODO Auto-generated method stub
        LayoutParams frm;
        LinearLayout.LayoutParams lin;
        //RelativeLayout.LayoutParams rel = null;
        int w, h;
        frmMesage = (LayoutParams) findViewById(
                R.id.notificationText).getLayoutParams();
        w = getwidth(80);
        frm = (LayoutParams) findViewById(R.id.iv_2X)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;
        frm.topMargin = w * 100 / 80;
        frm.leftMargin = getwidth(20);

        w = getwidth(230);
        h = w * 132 / 230;
        frm = (LayoutParams) frmDeadwoodContainer.getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = getheight(130);
        w = getwidth(20);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.ivBootChips).getLayoutParams();
        lin.width = w;
        lin.height = w;
        lin.rightMargin = w * 4 / 20;
        frm = (FrameLayout.LayoutParams) findViewById(R.id.llBootAmount).getLayoutParams();
        frm.leftMargin = getwidth(110);
        frm.topMargin = getheight(20);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.lnrbootchip)
                .getLayoutParams();
        lin.topMargin = getheight(5);
        w = getwidth(280);
        h = w * 75 / 280;
        lin = (LinearLayout.LayoutParams) findViewById(R.id.llExitToLobbyWrapper).getLayoutParams();
        lin.width = w;
        lin.height = h;
        lin.bottomMargin = h * 15 / 75;
        lin.leftMargin = w * 10 / 280;
        lin = (LinearLayout.LayoutParams) findViewById(R.id.llSwitchTableWrapper).getLayoutParams();
        lin.width = w;
        lin.height = h;
        lin.bottomMargin = h * 15 / 75;
        lin.leftMargin = w * 10 / 280;
        lin = (LinearLayout.LayoutParams) findViewById(R.id.llTableInfoWrapper).getLayoutParams();
        lin.width = w;
        lin.height = h;
        lin.bottomMargin = h * 15 / 75;
        lin.leftMargin = w * 10 / 280;
        lin = (LinearLayout.LayoutParams) findViewById(R.id.llControllsWrapper).getLayoutParams();
        lin.width = w;
        lin.height = h;
        lin.bottomMargin = h * 15 / 75;
        lin.leftMargin = w * 10 / 280;
        lin = (LinearLayout.LayoutParams) findViewById(R.id.llScoreboardWrapper).getLayoutParams();
        lin.width = w;
        lin.height = h;
        lin.bottomMargin = h * 15 / 75;
        lin.leftMargin = w * 10 / 280;
        lin = (LinearLayout.LayoutParams) findViewById(R.id.cbSound).getLayoutParams();
        lin.width = w;
        lin.height = h;
        lin.bottomMargin = h * 15 / 75;
        lin.leftMargin = w * 10 / 280;
        lin = (LinearLayout.LayoutParams) findViewById(R.id.cbVibRate).getLayoutParams();
        lin.width = w;
        lin.height = h;
        lin.bottomMargin = h * 15 / 75;
        lin.leftMargin = w * 10 / 280;
        lin = (LinearLayout.LayoutParams) findViewById(R.id.llThemesWrapper).getLayoutParams();
        lin.width = w;
        lin.height = h;
        lin.bottomMargin = h * 10 / 75;
        lin.leftMargin = w * 10 / 280;
        lin = (LinearLayout.LayoutParams) findViewById(R.id.llAutoSort).getLayoutParams();
        lin.width = w;
        lin.height = h;
        lin.bottomMargin = h * 15 / 75;
        lin.leftMargin = w * 10 / 280;
        w = getwidth(60);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.ivAutoSortIcon).getLayoutParams();
        lin.width = w;
        lin.height = w;
        lin.leftMargin = w * 14 / 60;
        int leftMargin = getwidth(70);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.exit_text).getLayoutParams();
        lin.leftMargin = leftMargin;
        lin = (LinearLayout.LayoutParams) findViewById(R.id.switch_text).getLayoutParams();
        lin.leftMargin = leftMargin;
        lin = (LinearLayout.LayoutParams) findViewById(R.id.table_info_text).getLayoutParams();
        lin.leftMargin = leftMargin;
        lin = (LinearLayout.LayoutParams) findViewById(R.id.tvController).getLayoutParams();
        lin.leftMargin = leftMargin;
        lin = (LinearLayout.LayoutParams) findViewById(R.id.tvTheme).getLayoutParams();
        lin.leftMargin = leftMargin;
        lin = (LinearLayout.LayoutParams) findViewById(R.id.scoreboard_text).getLayoutParams();
        lin.leftMargin = getwidth(18);
        w = getwidth(30);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.ivScoreboardIcon).getLayoutParams();
        lin.width = w;
        lin.height = w;
        lin.leftMargin = w * 24 / 30;
        w = getwidth(100);
        w = getwidth(190);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.llGamePrice)
                .getLayoutParams();
        lin.width = w;
        lin.height = w * 130 / 190;
        lin = (LinearLayout.LayoutParams) findViewById(R.id.tvTotalBetValue)
                .getLayoutParams();
        lin.leftMargin = w * 4 / 190;
        lin = (LinearLayout.LayoutParams) findViewById(R.id.llBootContainer)
                .getLayoutParams();
        lin.leftMargin = w * 20 / 190;
        lin = (LinearLayout.LayoutParams) findViewById(R.id.llPoints)
                .getLayoutParams();
        lin.topMargin = getheight(10);
        lin.leftMargin = w * 20 / 190;
        lin = (LinearLayout.LayoutParams) findViewById(R.id.llRound)
                .getLayoutParams();
        lin.topMargin = getheight(10);
        lin.leftMargin = w * 20 / 190;
        w = getwidth(40);
        int w1 = getwidth(40);
        frm = (LayoutParams) findViewById(R.id.llTemp11)
                .getLayoutParams();
        frm.topMargin = getheight(10);
        frm.bottomMargin = getheight(10);
        frm.rightMargin = getwidth(10);
        frm.leftMargin = getwidth(10);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.StandUp_Btn)
                .getLayoutParams();
        lin.width = getwidth(80);
        lin.height = getwidth(80);
        w = getwidth(230);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivScoreboard)
                .getLayoutParams();
        frm.width = getwidth(80);
        frm.height = getwidth(80);
        frm.rightMargin = getwidth(230);
        frm.topMargin = getheight(10);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.ivScoreboard22)
                .getLayoutParams();
        lin.width = getwidth(80);
        lin.height = getwidth(80);
        w = getwidth(300);
        h = w * 200 / 300;
        frm = (LayoutParams) findViewById(R.id.frmDeckBackGround)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
//        frm.bottomMargin = getheight(60);
        frm.bottomMargin = getheight(150);
        frm = (LayoutParams) findViewById(R.id.frmDeckBackGround9)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
//        frm.bottomMargin = getheight(60);
        frm.bottomMargin = getheight(150);
        frm = (LayoutParams) findViewById(R.id.frmTemp11)
                .getLayoutParams();
        frm.topMargin = h * 18 / 200;
        frm.leftMargin = w * 20 / 300;
        frm = (LayoutParams) findViewById(R.id.frmTemp119)
                .getLayoutParams();
        frm.topMargin = h * 18 / 200;
        frm.leftMargin = w * 20 / 300;
        frm = (LayoutParams) findViewById(R.id.frmTemp12)
                .getLayoutParams();
        frm.topMargin = h * 18 / 200;
        frm.leftMargin = w * 150 / 300;
        frm = (LayoutParams) findViewById(R.id.frmTemp129)
                .getLayoutParams();
        frm.topMargin = h * 18 / 200;
        frm.leftMargin = w * 150 / 300;
        w = getwidth(114);
        frm = (LayoutParams) findViewById(R.id.ivDeckBg1)
                .getLayoutParams();
        frm.width = w;
        frm.height = w * 152 / 114;
        w = getwidth(130);
        frm = (LayoutParams) findViewById(R.id.ivCloseDeck)
                .getLayoutParams();
        frm.width = w;
        frm.height = w * 156 / 130;
        w = getwidth(130);
        frm = (LayoutParams) findViewById(R.id.ivCloseDeck9)
                .getLayoutParams();
        frm.width = w;
        frm.height = w * 156 / 130;
        w = getwidth(60);
        frm = (LayoutParams) findViewById(R.id.tvCardCounter)
                .getLayoutParams();
        frm.width = frm.height = w;
        frm.rightMargin = getwidth(12);
        w = getwidth(120);
        frm = (LayoutParams) findViewById(R.id.ivCardOnDeck)
                .getLayoutParams();
        frm.width = w;
        frm.height = w * 156 / 120;
        w = getwidth(120);
        frm = (LayoutParams) findViewById(R.id.ivCardOnDeck9)
                .getLayoutParams();
        frm.width = w;
        frm.height = w * 156 / 120;
//        frm.leftMargin = getwidth(506);
//        frm.topMargin = getheight(30);
        w = getwidth(120);
        frm = (LayoutParams) findViewById(
                R.id.ivCardForDistribution).getLayoutParams();
        frm.width = w;
        frm.height = w * 156 / 120;
        w = getwidth(120);
        frm = (LayoutParams) findViewById(
                R.id.ivCardForDistribution9).getLayoutParams();
        frm.width = w;
        frm.height = w * 156 / 120;
//        frm.leftMargin = getwidth(508);
//        frm.topMargin = getheight(30);
        frm = (LayoutParams) findViewById(R.id.ivHandCloseDeck)
                .getLayoutParams();
        frm.width = getwidth(80);
        frm.height = getwidth(80);
        frm.rightMargin = getwidth(20);
        frm.bottomMargin = getheight(100);
        w = getwidth(104);
        h = w * 150 / 104;
        frm = (LayoutParams) findViewById(R.id.ivDeckBg2)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.leftMargin = w * 22 / 104;
        frm.topMargin = h * 4 / 150;
        frm.rightMargin = w * 4 / 104;
        frm.bottomMargin = h * 4 / 150;
        w = getwidth(130);
        h = w * 160 / 130;
        frm = (LayoutParams) findViewById(R.id.ivCloseCard)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = h * 4 / 160;
        frm.rightMargin = w * 10 / 130;
        frm.bottomMargin = h * 4 / 160;
        w = getwidth(130);
        h = w * 160 / 130;
        frm = (LayoutParams) findViewById(R.id.ivCloseCard9)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = h * 4 / 160;
        frm.rightMargin = w * 10 / 130;
        frm.bottomMargin = h * 4 / 160;
        frm = (LayoutParams) findViewById(R.id.ivPickCard)
                .getLayoutParams();
        frm.width = w;
        frm.height = w * 156 / 120;
        frm.topMargin = h * 4 / 160;
        frm.rightMargin = w * 10 / 130;
        frm.bottomMargin = h * 4 / 160;
        frm = (LayoutParams) findViewById(R.id.ivPickCard9)
                .getLayoutParams();
        frm.width = w;
        frm.height = w * 156 / 120;
        frm.topMargin = h * 4 / 160;
        frm.rightMargin = w * 10 / 130;
        frm.bottomMargin = h * 4 / 160;
//        frm.leftMargin = getwidth(646);
//        frm.topMargin = getheight(30);
        w = getwidth(950);
        h = w * 510 / 950;
        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivtable).getLayoutParams();
        frm.height = h;
        frm.width = w;
        w = getwidth(150);
        h = w * 400 / 400;
        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivScore).getLayoutParams();
        frm.height = w;
        frm.width = w;
        frm.leftMargin = getwidth(130);
        frm.bottomMargin = getheight(13);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivScore_knock).getLayoutParams();
        frm.height = w;
        frm.width = w;
        frm.leftMargin = getwidth(130);
        frm.bottomMargin = getheight(13);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.linedead).getLayoutParams();
        lin.width = getwidth(120);
        lin.height = getheight(2);
//        frm.topMargin= getheight(20);
        w = getwidth(320);
        h = w * 160 / 320;
        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivGinRummyText).getLayoutParams();
        frm.height = h;
        frm.width = w;
        frm.topMargin = getheight(20);
        frm = (LayoutParams) findViewById(R.id.ivHandOpenCard)
                .getLayoutParams();
        frm.width = getwidth(80);
        frm.height = getwidth(80);
        frm.leftMargin = getwidth(120);
        frm.bottomMargin = getheight(80);
        w = getwidth(690);
        frm = (LayoutParams) findViewById(R.id.llTemp12)
                .getLayoutParams();
        frm.topMargin = getheight(10);
        frm.bottomMargin = getheight(10);
        frm.rightMargin = getwidth(10);
        frm.leftMargin = getwidth(10);
        w = getwidth(30);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.ivBootAmountChips).getLayoutParams();
        lin.width = w;
        lin.height = w;
        // lin = (LinearLayout.LayoutParams) findViewById(R.id.ivTotalChips)
        // .getLayoutParams();
        // lin.leftMargin = getwidth(18);
        // lin.width = getwidth(30);
        // lin.height = getwidth(30);
        // frm = (FrameLayout.LayoutParams) findViewById(R.id.tvPoints)
        // .getLayoutParams();
        // frm.leftMargin = getwidth(24);
        frm = (LayoutParams) findViewById(R.id.llTopTwoUser)
                .getLayoutParams();
        frm.bottomMargin = getheight(80);
        // user LeftSide
        frm = (FrameLayout.LayoutParams) findViewById(R.id.fmUserOne)
                .getLayoutParams();
//        frm.leftMargin = getwidth(20);
        w = getwidth(180);
        frm = (LayoutParams) findViewById(R.id.ivProfileImage)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;
        frm.leftMargin = w * 20 / 180;
        frm = (LayoutParams) findViewById(R.id.ivUserNameOne)
                .getLayoutParams();
//        frm.leftMargin = w * -10 / 180;
        frm.topMargin = w * 180 / 180;
        frm = (LayoutParams) findViewById(R.id.LeftUserRing)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;
        frm.leftMargin = w * 20 / 180;
        w = getwidth(60);
        h = w * 56 / 60;
        frm = (LayoutParams) findViewById(R.id.LeftUserVipTag)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.leftMargin = w * 4 / 60;
        frm.topMargin = h * -14 / 56;
        w = getwidth(60);
        frm = (LayoutParams) findViewById(R.id.ivGiftIcon)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;
        frm.topMargin = w;
        frm = (LayoutParams) findViewById(R.id.ivGiftIconLtoB)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;
        frm.topMargin = w;
        frm = (LayoutParams) findViewById(R.id.ivGiftIconLtoR)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;
        frm.topMargin = w;
        w = getwidth(60);
        h = w /* 44 / 100*/;
        frm = (LayoutParams) findViewById(R.id.tvScore)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.rightMargin = w * 30 / 60;
//        frm.topMargin = h * 105 / 44;
        w = getwidth(60);
        h = w * 80 / 60;
        frm = (LayoutParams) findViewById(R.id.ivUserOneCard)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = h * 34 / 80;
        frm.rightMargin = w * 16 / 60;
        w = getwidth(60);
        frm = (LayoutParams) findViewById(R.id.ivUserOneThrownCard)
                .getLayoutParams();
        frm.width = w;
        frm.height = w * 80 / 60;
        frm.topMargin = h * 34 / 80;
        frm.rightMargin = w * 16 / 60;
        w = getwidth(60);
        frm = (LayoutParams) findViewById(R.id.ivUserOneThrownCard2)
                .getLayoutParams();
        frm.width = w;
        frm.height = w * 80 / 60;
        frm.topMargin = h * 34 / 80;
        frm.rightMargin = w * 16 / 60;
        frm = (LayoutParams) findViewById(R.id.ivUserOneBootChip)
                .getLayoutParams();
        frm.width = getwidth(40);
        frm.height = getwidth(40);
        frm.bottomMargin = getheight(60);
        frm.leftMargin = getwidth(10);
        w = getwidth(40);
        h = w * 16 / 40;
        frm = (LayoutParams) findViewById(R.id.ivSkipUserOne)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = h * 40 / 16;
        frm.leftMargin = w * 140 / 40;
        frm = (LayoutParams) findViewById(R.id.ivTimeOutUserOne)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = h * 40 / 16;
        frm.leftMargin = w * 140 / 40;
        frm = (LayoutParams) findViewById(R.id.ivDrawUserOne)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = h * 40 / 16;
        frm.leftMargin = w * 140 / 40;
        frm = (LayoutParams) findViewById(R.id.ivGinUserOne)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = h * 40 / 16;
        frm.leftMargin = w * 140 / 40;
        h = w * 10 / 40;
        frm = (LayoutParams) findViewById(R.id.ivKnockUserOne)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = h * 40 / 10;
        frm.leftMargin = w * 140 / 40;
        frm = (FrameLayout.LayoutParams) findViewById(R.id.fmUserTwo)
                .getLayoutParams();

        w = getwidth(180);
        frm = (LayoutParams) findViewById(R.id.ivProfileImageTwo)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;
        frm.leftMargin = w * 20 / 180;
        frm = (LayoutParams) findViewById(R.id.ivUserNameTwo)
                .getLayoutParams();

        frm.topMargin = w * 180 / 180;
        frm = (LayoutParams) findViewById(R.id.RightUserRing)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;
        frm.leftMargin = w * 20 / 180;
        w = getwidth(60);
        h = w * 56 / 60;
        frm = (LayoutParams) findViewById(R.id.RightUserVipTag)
                .getLayoutParams();
        frm.width = w;
        frm.height = w * 56 / 60;
        frm.leftMargin = w * 4 / 60;
        frm.topMargin = h * -14 / 56;
        w = getwidth(60);
        frm = (LayoutParams) findViewById(R.id.ivGiftIconTwo)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;
        frm.topMargin = w;
        frm = (LayoutParams) findViewById(R.id.ivGiftIconTwoRtoL)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;
        frm.topMargin = w;
        frm = (LayoutParams) findViewById(R.id.ivGiftIconTwoRtoB)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;
        frm.topMargin = w;
        w = getwidth(60);
        h = w;
        frm = (LayoutParams) findViewById(R.id.tvScoreTwo)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;
        frm.rightMargin = w * 30 / 60;

        w = getwidth(60);
        h = w * 80 / 60;
        frm = (LayoutParams) findViewById(R.id.ivUserTwoCard)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = h * 34 / 80;
        frm.rightMargin = w * 16 / 60;

        w = getwidth(60);
        h = w * 80 / 60;
        frm = (LayoutParams) findViewById(R.id.ivUserThreeCard)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = h * 34 / 80;
        frm.rightMargin = w * 16 / 60;

        w = getwidth(60);
        h = w * 80 / 60;
        frm = (LayoutParams) findViewById(R.id.ivUserTwoThrownCard)
                .getLayoutParams();
        frm.width = w;
        frm.height = w * 80 / 60;
        frm.topMargin = h * 34 / 80;
        frm.rightMargin = w * 16 / 60;
        w = getwidth(60);
        h = w * 80 / 60;
        frm = (LayoutParams) findViewById(R.id.ivUserTwoThrownCard2)
                .getLayoutParams();
        frm.width = w;
        frm.height = w * 80 / 60;
        frm.topMargin = h * 34 / 80;
        frm.rightMargin = w * 16 / 60;
        frm = (LayoutParams) findViewById(R.id.ivUserTwoBootChip)
                .getLayoutParams();
        frm.width = getwidth(40);
        frm.height = getwidth(40);
        frm.bottomMargin = getheight(60);
        frm.leftMargin = getwidth(10);
        w = getwidth(40);
        h = w * 16 / 40;
        frm = (LayoutParams) findViewById(R.id.ivSkipUserTwo)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = h * 40 / 16;

        frm = (LayoutParams) findViewById(R.id.ivTimeOutUserTwo)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = h * 40 / 16;

        frm = (LayoutParams) findViewById(R.id.ivDrawUserTwo)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = h * 40 / 16;

        frm = (LayoutParams) findViewById(R.id.ivGinUserTwo)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = h * 40 / 16;

        h = w * 10 / 40;
        frm = (LayoutParams) findViewById(R.id.ivKnockUserTwo)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = h * 40 / 10;
        lin = (LinearLayout.LayoutParams) findViewById(R.id.fmUserThree)
                .getLayoutParams();

        lin.topMargin = getheight(20);
        w = getwidth(60);
        h = w * 56 / 60;
        frm = (LayoutParams) findViewById(R.id.BottomUserVipTag)
                .getLayoutParams();
        frm.width = w;
        frm.height = w * 56 / 60;
        frm.leftMargin = w * 4 / 60;
        frm.topMargin = h * -14 / 56;
        w = getwidth(180);
        frm = (LayoutParams) findViewById(R.id.ivProfileImageThree)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;

        frm = (LayoutParams) findViewById(R.id.ivUserNameThree)
                .getLayoutParams();

        frm.topMargin = w * 180 / 180;
        frm = (LayoutParams) findViewById(R.id.BottomUserRing)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;

        w = getwidth(60);
        frm = (LayoutParams) findViewById(R.id.ivGiftIconThree)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;
        frm.topMargin = w;
        frm = (LayoutParams) findViewById(R.id.ivGiftIconThreeBtoR)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;
        frm.topMargin = w;
        frm = (LayoutParams) findViewById(R.id.ivGiftIconThreeBtoL)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;
        frm.topMargin = w;
        w = getwidth(60);
        h = w * 44 / 100;
        frm = (LayoutParams) findViewById(R.id.tvScoreThree)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;
        frm.rightMargin = w * 30 / 60;
//        frm.width = w;
//        frm.height = h;
//        frm.rightMargin = w * 14 / 100;
//        frm.topMargin = h * 105 / 44;
        frm = (LayoutParams) findViewById(R.id.ivUserThreeBootChip)
                .getLayoutParams();
        frm.width = getwidth(40);
        frm.height = getwidth(40);
        frm.bottomMargin = getheight(60);
        frm.leftMargin = getwidth(10);
        w = getwidth(40);
        h = w * 14 / 40;
        frm = (LayoutParams) findViewById(R.id.ivSkipUserThree)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = h * 40 / 14;
        frm.leftMargin = w * 140 / 40;
        frm = (LayoutParams) findViewById(R.id.ivTimeOutUserThree)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = h * 40 / 14;
        frm.leftMargin = w * 140 / 40;
        frm = (LayoutParams) findViewById(R.id.ivDrawUserThree)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = h * 40 / 14;
        frm.leftMargin = w * 140 / 40;
        frm = (LayoutParams) findViewById(R.id.ivGinUserThree)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = h * 40 / 14;
        frm.leftMargin = w * 140 / 40;
        h = w * 10 / 40;
        frm = (LayoutParams) findViewById(R.id.ivKnockUserThree)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = h * 40 / 10;
        frm.leftMargin = w * 140 / 40;
        frm = (LayoutParams) findViewById(R.id.fmuserCards)
                .getLayoutParams();
        frm.bottomMargin = getheight(-40);
        frm = (LayoutParams) findViewById(R.id.groupBgLinear)
                .getLayoutParams();
        frm.topMargin = getheight(200);
        w = getwidth(120);
        frm = (LayoutParams) findViewById(R.id.ivCard1)
                .getLayoutParams();
        frm.leftMargin = getwidth(68);
        frm.width = CARD_WIDTH;
        frm.height = CARD_HEIGHT;
        frm.topMargin = getheight(40);
        frm = (LayoutParams) findViewById(R.id.ivCard2)
                .getLayoutParams();
        frm.leftMargin = getwidth(68 * 2);
        frm.width = CARD_WIDTH;
        frm.height = CARD_HEIGHT;
        frm.topMargin = getheight(40);
        frm = (LayoutParams) findViewById(R.id.ivCard3)
                .getLayoutParams();
        frm.leftMargin = getwidth(68 * 3);
        frm.topMargin = getheight(40);
        frm.width = CARD_WIDTH;
        frm.height = CARD_HEIGHT;
        frm = (LayoutParams) findViewById(R.id.ivCard4)
                .getLayoutParams();
        frm.leftMargin = getwidth(68 * 4);
        frm.topMargin = getheight(40);
        frm.width = CARD_WIDTH;
        frm.height = CARD_HEIGHT;
        frm = (LayoutParams) findViewById(R.id.ivCard5)
                .getLayoutParams();
        frm.leftMargin = getwidth(68 * 5);
        frm.topMargin = getheight(40);
        frm.width = CARD_WIDTH;
        frm.height = CARD_HEIGHT;
        frm = (LayoutParams) findViewById(R.id.ivCard6)
                .getLayoutParams();
        frm.leftMargin = getwidth(68 * 6);
        frm.topMargin = getheight(40);
        frm.width = CARD_WIDTH;
        frm.height = CARD_HEIGHT;
        frm = (LayoutParams) findViewById(R.id.ivCard7)
                .getLayoutParams();
        frm.leftMargin = getwidth(68 * 7);
        frm.topMargin = getheight(40);
        frm.width = CARD_WIDTH;
        frm.height = CARD_HEIGHT;
        frm = (LayoutParams) findViewById(R.id.ivCard8)
                .getLayoutParams();
        frm.leftMargin = getwidth(68 * 8);
        frm.topMargin = getheight(40);
        frm.width = CARD_WIDTH;
        frm.height = CARD_HEIGHT;
        frm = (LayoutParams) findViewById(R.id.ivCard9)
                .getLayoutParams();
        frm.leftMargin = getwidth(68 * 9);
        frm.topMargin = getheight(40);
        frm.width = CARD_WIDTH;
        frm.height = CARD_HEIGHT;
        frm = (LayoutParams) findViewById(R.id.ivCard10)
                .getLayoutParams();
        frm.leftMargin = getwidth(68 * 10);
        frm.topMargin = getheight(40);
        frm.width = CARD_WIDTH;
        frm.height = CARD_HEIGHT;
        frm = (LayoutParams) findViewById(R.id.ivCard11)
                .getLayoutParams();
        frm.leftMargin = getwidth(68 * 11);
        frm.topMargin = getheight(40);
        frm.width = CARD_WIDTH;
        frm.height = CARD_HEIGHT;
        frm = (LayoutParams) findViewById(R.id.frmLeftUserOpenCard)
                .getLayoutParams();
        frm.topMargin = getheight(360);
        frm = (LayoutParams) findViewById(R.id.frmRightUserOpenCard)
                .getLayoutParams();
        frm.topMargin = getheight(360);
        w = getwidth(558);
        frm = (LayoutParams) findViewById(R.id.notificationText)
                .getLayoutParams();
        frm.width = w;
        frm.height = w * 90 / 558;
        frm.topMargin = getheight(80);
        frm = (LayoutParams) findViewById(R.id.help_text)
                .getLayoutParams();
        frm.width = w;
        frm.height = w * 90 / 558;
        frm.bottomMargin = getheight(20);
        w = getwidth(145);
        h = w * 145 / 145;
        w = getwidth(13 * 16);
        h = w * (9 * 16) / (13 * 16);
        frm = (LayoutParams) findViewById(R.id.btnKnock)
                .getLayoutParams();
        frm.width = w;
        frm.height = h /* 86 / 224*/;
        frm.bottomMargin = getheight(90);
        frm.rightMargin = getwidth(100);
        frm = (LayoutParams) findViewById(R.id.btnGin)
                .getLayoutParams();
        frm.width = w;
        frm.height = h /* 86 / 224*/;
        frm.bottomMargin = getheight(90);
        frm.rightMargin = getwidth(100);
        frm = (LayoutParams) findViewById(R.id.btnSkip)
                .getLayoutParams();
        frm.width = w;
        frm.height = h /* 86 / 224*/;
        frm.bottomMargin = getheight(90);
        frm.rightMargin = getwidth(100);
        frm = (LayoutParams) findViewById(R.id.btnDrawNew)
                .getLayoutParams();
        frm.width = w;
        frm.height = h /* 86 / 224*/;
        frm.bottomMargin = getheight(90);
        frm.rightMargin = getwidth(100);
        frm = (LayoutParams) findViewById(R.id.btnFaceUp)
                .getLayoutParams();
        frm.width = w;
        frm.height = h /* 86 / 224*/;
        frm.bottomMargin = getheight(90);
        frm.leftMargin = getwidth(100);
        frm = (LayoutParams) findViewById(R.id.btnDiscard)
                .getLayoutParams();
        frm.width = w;
        frm.height = h /* 86 / 224*/;
        frm.bottomMargin = getheight(90);
        frm.leftMargin = getwidth(100);
        frm = (LayoutParams) findViewById(R.id.tglSortButton)
                .getLayoutParams();
        frm.width = getwidth(90);
        frm.height = getwidth(90);
        frm.rightMargin = getwidth(55);
        frm.bottomMargin = getheight(10);
        frm = (LayoutParams) findViewById(R.id.tglAutoSortButton22)
                .getLayoutParams();
        frm.width = getwidth(90);
        frm.height = getwidth(90);
        frm.rightMargin = getwidth(55);
        frm.bottomMargin = getheight(105);
        frm = (LayoutParams) findViewById(R.id.tvAutosortOnOff)
                .getLayoutParams();
        frm.width = getwidth(200);
        frm.height = getwidth(50);
        frm.rightMargin = getwidth(0);
        frm.bottomMargin = getheight(200);
        // frm = (FrameLayout.LayoutParams) findViewById(R.id.ivLeaderBoard)
        // .getLayoutParams();
        // frm.width = getwidth(90);
        // frm.height = getwidth(90);
        // frm.rightMargin = getwidth(6);
        // frm.bottomMargin = getheight(100);
        // frm = (FrameLayout.LayoutParams) findViewById(R.id.llRoundInfo)
        // .getLayoutParams();
        // frm.leftMargin = getwidth(14);
        // frm.topMargin = getheight(90);
        frm = (LayoutParams) findViewById(R.id.iv1)
                .getLayoutParams();
        frm.width = getwidth(40);
        frm.height = getwidth(40);
        frm = (LayoutParams) findViewById(R.id.iv2)
                .getLayoutParams();
        frm.width = getwidth(40);
        frm.height = getwidth(40);
        frm = (LayoutParams) findViewById(R.id.iv3)
                .getLayoutParams();
        frm.width = getwidth(40);
        frm.height = getwidth(40);
        frm = (LayoutParams) findViewById(R.id.iv4)
                .getLayoutParams();
        frm.width = getwidth(40);
        frm.height = getwidth(40);
        frm = (LayoutParams) findViewById(R.id.iv5)
                .getLayoutParams();
        frm.width = getwidth(40);
        frm.height = getwidth(40);
        frm = (LayoutParams) findViewById(R.id.iv6)
                .getLayoutParams();
        frm.width = getwidth(40);
        frm.height = getwidth(40);
        frm = (LayoutParams) findViewById(R.id.iv7)
                .getLayoutParams();
        frm.width = getwidth(40);
        frm.height = getwidth(40);
        w = getwidth(90);
        frm = (LayoutParams) findViewById(R.id.ivBottomUserCard)
                .getLayoutParams();
        frm.width = w;
        frm.height = w * 120 / 90;
        w = getwidth(500);
        frm = (LayoutParams) findViewById(R.id.llTableInfo)
                .getLayoutParams();
        frm.width = w;
        frm.height = w * 280 / 500;
        lin = (LinearLayout.LayoutParams) findViewById(R.id.ivLineGti)
                .getLayoutParams();
        lin.width = getwidth(480);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.tvTableNameGtiText)
                .getLayoutParams();
        lin.width = getwidth(220);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.tvTableNameGti)
                .getLayoutParams();
        lin.width = getwidth(180);
        lin.leftMargin = getwidth(10);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.tvBootAmtGtiText)
                .getLayoutParams();
        lin.width = getwidth(220);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.tvBootAmtGti)
                .getLayoutParams();
        lin.width = getwidth(180);
        lin.leftMargin = getwidth(10);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.tvMinChipsGtiText)
                .getLayoutParams();
        lin.width = getwidth(220);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.tvMinChipsGti)
                .getLayoutParams();
        lin.width = getwidth(180);
        lin.leftMargin = getwidth(10);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.tvPointsTextGti)
                .getLayoutParams();
        lin.width = getwidth(220);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.tvPointsGti)
                .getLayoutParams();
        lin.width = getwidth(180);
        lin.leftMargin = getwidth(10);
        frm = (LayoutParams) findViewById(R.id.ivWinninngCircleOne)
                .getLayoutParams();
        frm.height = getwidth(170);
        frm.width = getwidth(170);
        frm.leftMargin = getwidth(6);
        frm.topMargin = getheight(-6);
        frm = (LayoutParams) findViewById(R.id.ivWinninngRoundOne)
                .getLayoutParams();
        frm.height = getwidth(240);
        frm.width = getwidth(240);
        frm.topMargin = getheight(-42);
        frm.leftMargin = getwidth(-26);
        frm = (LayoutParams) findViewById(R.id.ivWinninngCircleTwo)
                .getLayoutParams();
        frm.height = getwidth(170);
        frm.width = getwidth(170);
        frm.leftMargin = getwidth(6);
        frm.topMargin = getheight(-6);
        frm = (LayoutParams) findViewById(R.id.ivWinninngRoundTwo)
                .getLayoutParams();
        frm.height = getwidth(240);
        frm.width = getwidth(240);
        frm.topMargin = getheight(-42);
        frm.leftMargin = getwidth(-26);
        frm = (LayoutParams) findViewById(
                R.id.ivWinninngCircleThree).getLayoutParams();
        frm.height = getwidth(170);
        frm.width = getwidth(170);
        frm.leftMargin = getwidth(6);
        frm.topMargin = getheight(-6);
        frm = (LayoutParams) findViewById(R.id.ivWinninngRoundThree)
                .getLayoutParams();
        frm.height = getwidth(240);
        frm.width = getwidth(240);
        frm.topMargin = getheight(-42);
        frm.leftMargin = getwidth(-26);
        frm = (LayoutParams) findViewById(R.id.back_menu_frame)
                .getLayoutParams();
        frm.width = getwidth(1280);
        frm.height = getheight(720);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.close_menu)
                .getLayoutParams();
        lin.width = getwidth(81);
        lin.height = getwidth(81);
        w = getwidth(40);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.cbController)
                .getLayoutParams();
        lin.width = w;
        lin.height = w;
        lin = (LinearLayout.LayoutParams) findViewById(R.id.cbAutoSort)
                .getLayoutParams();
        lin.width = w;
        lin.height = w;
        lin.leftMargin = w * 26 / 40;
        frm = (LayoutParams) findViewById(R.id.frmPlayingSettingBg)
                .getLayoutParams();
        frm.width = getwidth(640);
        w = getwidth(45);
        h = w * 14 / 45;
        frm = (LayoutParams) findViewById(R.id.ivGinUserOne2)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = h * 45 / 14;
        frm.leftMargin = w * 140 / 45;
        frm = (LayoutParams) findViewById(R.id.ivGinUserTwo2)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = h * 45 / 14;
        //frm.leftMargin = w * 140 / 45;
        frm = (LayoutParams) findViewById(R.id.ivGinUserThree2)
                .getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = h * 45 / 14;
        frm.leftMargin = w * 140 / 45;
    }

    private int getheight(int val) {
        // TODO Auto-generated method stub
        return Screen_Hight * val / 720;
    }

    private int getwidth(int val) {
        // TODO Auto-generated method stub
        return Screen_Width * val / 1280;
    }

    private void initHandler() {
        // TODO Auto-generated method stub
        handler = new Handler(new Callback() {
            @Override
            public boolean handleMessage(final Message msg) {
                // TODO Auto-generated method stub
                Logger.print("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL " + msg.what);
                switch (msg.what) {
                    case ResponseCodes.Start_New_Game:
//                        startNewGame();
                        break;
                    case ResponseCodes.Start_RoundTimer_In_Playscreen:
//                        int timer = Integer.parseInt(msg.obj.toString());
//                        if (ROUND_NUMBER == 0) {
//                            RoundTimerStartProcess(0, true);
//                        } else {
//                            RoundTimerStartProcess(0, false);
//                        }
                        startNewGame();
                        break;
                    case ResponseCodes.Start_New_Round:
//                        Logger.print(">>>>>> TWO X PROBLM >>>> Start_New_Round MESSAGE RECIVED ");

                        NotificationText.setVisibility(View.VISIBLE);
                        NotificationText.setText("New Round Starting...");
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startRound();
                            }
                        }, 2000);

//                        RoundTimerStartProcess(0, false);
                        break;
                    case ResponseCodes.ScoreBoardResp:
                        try {
                            Logger.print("SCOREBOARD => " + msg.obj.toString());
                            JSONObject jObjScore = new JSONObject(msg.obj.toString());
                            JSONArray senddataArray = jObjScore.getJSONArray(Parameters.data);
                            JSONArray PlayerInfo = new JSONArray();
                            JSONObject myScoreData = new JSONObject();
                            for (int i = 0; i < senddataArray.length(); i++) {
                                JSONObject jObj1 = senddataArray.getJSONObject(i);
                                if (jObj1.length() > 0) {
                                    if (jObj1.getJSONArray("st").length() > 0) {
                                        JSONObject pInfo = new JSONObject();
                                        pInfo.put(Parameters.SeatIndex, jObj1.getInt(Parameters.SeatIndex));
                                        pInfo.put(Parameters.points, jObj1.getJSONArray(Parameters.points));
                                        pInfo.put("st", jObj1.getJSONArray("st"));
                                        pInfo.put(Parameters.User_Name, jObj1.getJSONObject(Parameters.User_Info).getString(Parameters.User_Name));
                                        pInfo.put(Parameters.ProfilePicture, jObj1.getJSONObject(Parameters.User_Info).getString(Parameters.ProfilePicture));
                                        pInfo.put(Parameters.totalsum, jObj1.getInt(Parameters.totalsum));
                                        pInfo.put(Parameters.Cards, jObj1.getJSONArray(Parameters.Cards));
                                        pInfo.put(Parameters.User_Id, jObj1.getJSONObject(Parameters.User_Info).getString(Parameters._id));
                                        PlayerInfo.put(pInfo);
                                    }
                                }
                            }
                            if (PlayerInfo.length() > 0) {
                                myScoreData.put(Parameters.PlayersInfo, PlayerInfo);
                                /*JSONObject myData = new JSONObject();
                                myData.put(Parameters.data, myScoreData);*/
                                senddata = myScoreData;
                                Logger.print("########################################## " + senddata.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case ResponseCodes.NotificationResp:
                        try {
                            JSONObject jObject = new JSONObject(msg.obj.toString());
                            JSONObject obj1 = jObject
                                    .getJSONObject(Parameters.data);
                            if (obj1.getJSONObject("keyd") != null) {
                                JSONObject obj = obj1.getJSONObject("keyd");
                                String strname = obj.getString("un");
                                String change = " Change Theme.";
                                String str = strname + change;
                                NotificationText.setVisibility(View.VISIBLE);
                                NotificationText.setText(str);
                                Logger.print("THEME CHAGE NAME:" + strname);
                            }
                            /*String key = jObject.getJSONObject(Parameters.data)
                                    .getString(Parameters.Key);*/
                            // showToast(jObject.getJSONObject(Parameters.data)
                            // .getString(Parameters.Message),
                            // Toast.LENGTH_SHORT);
                           /* if (!GameisOn()) {
                                // showNotification(jObject.getJSONObject(
                                // Parameters.data).getString(
                                // Parameters.Message));
                            }*/
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        break;
                    case ResponseCodes.PickFromPileResp:
                        PickFromPileProcess(msg.obj.toString());
                        break;
                    case ResponseCodes.PickFromThrownCardsResp:
                        PickFromThrownCardsProcess(msg.obj.toString());
                        break;
                    case ResponseCodes.WinnerDeclaredResp:
                        if (GameisOn()) {
                            if (is3danimation) {
                                ObjectAnimator animation = ObjectAnimator.ofFloat(frmtable, "rotationX", 35f, 0f);
                                animation.setDuration(2000);
                                animation.setRepeatCount(0);
                                animation.setInterpolator(new LinearInterpolator());
                                animation.start();
                                ObjectAnimator animation2 = ObjectAnimator.ofFloat(frmDeckBackGround, "rotationX", 25f, 0f);
                                animation2.setDuration(2000);
                                animation2.setRepeatCount(0);
                                animation2.setInterpolator(new LinearInterpolator());
                                animation2.start();
                                ivGinRummyText.setVisibility(View.INVISIBLE);
                                ivGinRummyText.startAnimation(up);
                            }
                        }
                        final JSONObject jObject12;
                        // c.showAds(getApplicationContext());
                        isSortButtonClickable = false;
                        isCardTouchable = false;
                        try {
                            jObject12 = new JSONObject(msg.obj.toString());
                            final JSONObject data11 = jObject12
                                    .getJSONObject(Parameters.data);
                            senddata = new JSONObject(data11.toString());
                            final JSONArray array12 = data11
                                    .getJSONArray(Parameters.PlayersInfo);
                            /*int len = array12.getJSONObject(0)
                                    .getJSONArray(Parameters.points).length();*/
                            Logger.print("CDT Finish 2" + array12.toString());
                            // if(len>0){
                            int winnerSCreenOpenTimeDelay = 14000;
                            if (array12.length() <= 1) {
                                winnerSCreenOpenTimeDelay = 2000;
                            }
                            try {
                                // if (array12.length() >= 2) {
                                Logger.print("ROund Winner Data 0 => " + jObject12.toString());
                                mCountDownTimer1 = new CountDownTimer(2000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // TODO Auto-generated method stub
                                    }

                                    @Override
                                    public void onFinish() {
                                        // TODO Auto-generated method stub
                                        // if (!isDestroyCalled) {
                                        ivScoreboard.setVisibility(View.INVISIBLE);
                                        resetAfterWinning();
                                        if (is3danimation) {
                                            frmtable.setRotationX(0);
                                            frmDeckBackGround.setRotationX(0);
                                            ivGinRummyText.setVisibility(View.INVISIBLE);
                                        }
                                        try {
                                            if (array12.length() > 1) {
                                                Intent i = new Intent(
                                                        getApplicationContext(),
                                                        RoundWinner.class);
                                                i.putExtra("roundWinnerData",
                                                        jObject12.toString());
                                                i.putExtra("timer", 14);
                                                i.putExtra("round", ROUND_NUMBER);
                                                i.putExtra("point", GAME_POINT);
                                                i.putExtra("winner", true);
                                                startActivity(i);
                                                overridePendingTransition(android.R.anim.slide_in_left, 0);
                                                ROUND_NUMBER = 0;
                                            }
                                        } catch (Exception e) {
                                            // TODO: handle exception
                                            e.printStackTrace();
                                        }
                                        // }
                                    }
                                };
                                mCountDownTimer1.start();
                                mCountDownTimer = new CountDownTimer(winnerSCreenOpenTimeDelay + 1000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // TODO Auto-generated method stub
                                    }

                                    @Override
                                    public void onFinish() {
                                        // TODO Auto-generated method stub
                                        //if (!isDestroyCalled) {
                                        ivScoreboard.setVisibility(View.INVISIBLE);
                                        try {
//                                            resetAfterWinning();
//                                            Intent kl = new Intent(
//                                                    getApplicationContext(),
//                                                    Activity_GameWinner.class);
//                                            kl.putExtra(Parameters.data,
//                                                    array12.toString());
//                                            kl.putExtra("roundno", round);
//                                            startActivity(kl);
//                                            overridePendingTransition(android.R.anim.slide_in_left, 0);
                                        } catch (Exception e) {
                                            // TODO: handle exception
                                            e.printStackTrace();
                                        }
                                        // }
                                    }
                                };
                                mCountDownTimer.start();
                                WinnerDeclareProcess(array12);
                                isknock = false;
                                isgin = false;
                                final String str = msg.obj.toString();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // TODO Auto-generated method stub
                                        WinnerDeclaredProcess(str, "B");
                                    }
                                }, 2000);
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            // }
                        } catch (JSONException e2) {
                            // TODO Auto-generated catch block
                            e2.printStackTrace();
                        }
                        break;
                    case ResponseCodes.DrawResp:
                        Logger.print(msg.obj.toString());
                        declareDraw();
                        break;
                    case ResponseCodes.RoundResp:
                        Logger.print("RRRRRRRROOOOOOOOOOUUUUUUUUNNNNDDDDDD 1=> "
                                + round);
                        try {
                            final JSONObject jObjRound = new JSONObject(msg.obj
                                    .toString());
                            JSONObject jObjRound1;
                            jObjRound1 = jObjRound.getJSONObject(Parameters.data);
                            round = jObjRound1.getInt("round");
                            tvRoundNo.setText("" + round);
                            Logger.print("RRRRRRRROOOOOOOOOOUUUUUUUUNNNNDDDDDD => "
                                    + round);
                        } catch (JSONException e2) {
                            // TODO Auto-generated catch block
                            e2.printStackTrace();
                        }
                        break;
                    case ResponseCodes.RoundTimerStartResp:
                        gameCounter++;
                        try {
                            if (is3danimation) {
                                if (GameisOn()) {
                                    ObjectAnimator animation = ObjectAnimator.ofFloat(frmtable, "rotationX", 35f, 0f);
                                    animation.setDuration(2000);
                                    animation.setRepeatCount(0);
                                    animation.setInterpolator(new LinearInterpolator());
                                    animation.start();
                                    ObjectAnimator animation2 = ObjectAnimator.ofFloat(frmDeckBackGround, "rotationX", 25f, 0f);
                                    animation2.setDuration(2000);
                                    animation2.setRepeatCount(0);
                                    animation2.setInterpolator(new LinearInterpolator());
                                    animation2.start();
                                    ivGinRummyText.setVisibility(View.INVISIBLE);
                                    ivGinRummyText.startAnimation(up);
                                }
                            }
                            final JSONObject jObject = new JSONObject(msg.obj
                                    .toString());
                            final JSONObject data1 = jObject
                                    .getJSONObject(Parameters.data);
                            if (data1.has(Parameters.PlayersInfo)) {
                                if (!isDraw)
                                    senddata = new JSONObject(data1.toString());
                                round = data1.getInt("round");
                                tvRoundNo.setText("" + ROUND_NUMBER);
                                if (UserTimer != null) {
                                    UserTimer.cancel();
                                    UserTimer = null;
                                }
                                final JSONArray pData = data1
                                        .getJSONArray(Parameters.PlayersInfo);
                                int score;
                                for (int i = 0; i < pData.length(); i++) {
                                    int si = pData.getJSONObject(i).getInt(
                                            Parameters.SeatIndex);
                                    if (si == BottomSeatIndex) {
                                        score = pData.getJSONObject(i).getInt(
                                                Parameters.totalsum);
                                        tvScoreThree.setText("" + score);
                                    } else if (si == RightSeatIndex) {
                                        score = pData.getJSONObject(i).getInt(
                                                Parameters.totalsum);
                                        tvScoreTwo.setText("" + score);
                                    } else if (si == LeftSeatIndex) {
                                        score = pData.getJSONObject(i).getInt(
                                                Parameters.totalsum);
                                        tvScore.setText("" + score);
                                    }
                                }
                                LeftUserRing.setVisibility(View.INVISIBLE);
                                RightUserRing.setVisibility(View.INVISIBLE);
                                BottomUserRing.setVisibility(View.INVISIBLE);
                                Logger.print("TIMER 1");
                                mCountDownTimer = new CountDownTimer(10000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // TODO Auto-generated method stub
                                    }

                                    @Override
                                    public void onFinish() {
                                        // TODO Auto-generated method stub
                                        if (is3danimation) {
                                            frmtable.setRotationX(0);
                                            frmDeckBackGround.setRotationX(0);
                                        }
                                        ivGinRummyText.setVisibility(View.INVISIBLE);
                                        Logger.print("CDT Finish");
                                        // ResetForNewRound(false);
                                        NotificationText
                                                .setVisibility(View.GONE);
                                        NotificationText.setText("");
                                        try {
                                            //RoundTimerStartProcess(5, false);
                                            //startRound();
                                            //============================================
                                            //isDraw = false;
                                            //===========================================
                                            // }
                                        } catch (Exception e) {
                                            // TODO: handle exception
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                mCountDownTimer.start();
                                /*if (!isUserLeft)
                                    Music_Manager.play_Winner();*/
                                // c.showAds(getApplicationContext());
                                final JSONObject jObject1 = new JSONObject(
                                        msg.obj.toString());
                                mCountDownTimer1 = new CountDownTimer(2000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                    }

                                    @Override
                                    public void onFinish() {
                                        try {
                                            if (jObject1.getJSONObject(Parameters.data)
                                                    .length() > 0) {
                                                if (ivScore.getVisibility() == View.VISIBLE) {
                                                    ivScore.setVisibility(View.INVISIBLE);
                                                }
                                                Logger.print("pData => " + pData.toString());
                                                if (pData.length() > 1/* && !isDraw*/) {
                                                    Logger.print("Round winner data 11 => " + jObject1.toString());
                                                    Intent i = new Intent(
                                                            getApplicationContext(),
                                                            RoundWinner.class);
                                                    i.putExtra("roundWinnerData",
                                                            jObject1.toString());
                                                    i.putExtra("round", ROUND_NUMBER);
                                                    i.putExtra("point", GAME_POINT);
                                                    i.putExtra("draw", isDraw);
                                                    i.putExtra("timer", 14);
                                                    startActivity(i);
                                                }
                                                clearSomeData();
                                                ResetForNewRound();
                                            }
                                        } catch (Exception e) {
                                            // TODO: handle exception
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                mCountDownTimer1.start();
                            } else {
                                senddata = new JSONObject();
                                if (gameCounter > 1) {
                                    Logger.print("Game Number =>>>>>>>>>>>>>>>>>>>>>> "
                                            + gameCounter);
                                    clearSomeData();
                                    ResetForNewRound();
                                }
                                Logger.print("TIMERRRR " + c.ROUND_START_TIMER);
                                RoundTimerStartProcess(c.ROUND_START_TIMER, true);
                                //HelpText.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
//                        if (!GameisOn()) {
//                            showNotification("New round started..");
//                        }
                        break;
                    case ResponseCodes.ShowMessage:
                        HelpText.setVisibility(View.VISIBLE);
                        HelpText.setText("Waiting for next round");
                        break;
                    case ResponseCodes.UserTurnStartedResp:
                        // StandUp_Btn.setEnabled(true);redraw
                        // Setting_Btn.setEnabled(true);
                        // isAnimStop = true;
                        // Logger.print("USET TURN => " + msg.obj.toString());
                        /*if (selectedCard.size() > 0)
                            selectedCard.clear();*/
                        if (btnGin.getVisibility() == View.VISIBLE) {
                            if (btnGin.getAnimation() != null) {
                                btnGin.clearAnimation();
                            }
                            btnGin.setVisibility(View.INVISIBLE);
                        }
                        UserTurnStartedProcess(msg.obj.toString());
                        break;
                    case ResponseCodes.FindTableAndJoinResp:
                        GetFindTableAndJoinProcess(msg.obj.toString());
                        break;
                    case ResponseCodes.ShowMessageResp:
                        try {
                            // Logger.print("MSG Show Message");
                            if (btnGin.getVisibility() != View.VISIBLE) {
                                if (NotificationText.getVisibility() == View.GONE) {
                                    NotificationText.setVisibility(View.VISIBLE);
                                }
                                if (isknock) {
                                    frmMesage.bottomMargin = getheight(100);
                                } else {
                                    frmMesage.bottomMargin = getheight(0);
                                }
                                NotificationText
                                        .setText("Please discard one card to pile.");
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        break;
                    case ResponseCodes.HideMessageResp:
                        try {
                            // Logger.print("MSG Hide Message");
                            NotificationText.setVisibility(View.GONE);
                            NotificationText.setText("");
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        break;
                    case ResponseCodes.UpdateChipsResp:
                        Logger.print("UPDATE CHIPS:" + c.Chips);
                        break;
                    case ResponseCodes.ginvissible:
                        Gin_process();
                        // KnockBtn.setVisibility(view.GONE);
                        // ginbtn.setVisibility(view.VISIBLE);
                        break;
                    case ResponseCodes.Ginresponce:
                        //GinResponseProcess(msg.obj.toString());
                        break;
                    case ResponseCodes.KnockResp:
                        KnockResponseProcess(msg.obj.toString());
                        break;
                    case ResponseCodes.knockvissible:
                        Knock_process();
                        // KnockBtn.setVisibility(view.VISIBLE);
                        break;
                    case ResponseCodes.skipbtnclick:
                       /* opponent 1st
                    {"en":"UTS","data":{"pt":1,"type":"PST","nt":0,"pc":"k-8"}}
                    {"en":"UTS","data":{"pt":1,"type":"OPST","nt":0,"pc":"f-2"}}*/
                        passTurn();
                        break;
                    case ResponseCodes.GoToFriendsTable:
                        JSONObject obj = (JSONObject) msg.obj;
                        break;
                    case ResponseCodes.SeeMyCardResp:
                        JSONObject MyCards = null;
                        try {
                            MyCards = new JSONObject(msg.obj.toString());
                            SeeMyCardEvent(MyCards);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                Message message = new Message();
                message.what = msg.what;
                message.obj = msg.obj;
                // playingActivity.handler.sendMessage(message);
                return false;
            }
        });
    }

    private void declareDraw() {
        isSortButtonClickable = false;
        isCardTouchable = false;
        isDraw = true;
        GAME_OVER = true;
        c.isKnocked = false;
        if (ivHandCloseDeck.getAnimation() != null) {
            ivHandCloseDeck.clearAnimation();
        }
        if (ivHandOpenCard.getAnimation() != null) {
            ivHandOpenCard.clearAnimation();
        }
        if (ivHandCloseDeck.getVisibility() == View.VISIBLE) {
            ivHandCloseDeck.setVisibility(View.INVISIBLE);
        }
        if (ivHandOpenCard.getVisibility() == View.VISIBLE) {
            ivHandOpenCard.setVisibility(View.INVISIBLE);
        }
        if (RightUserRing.getAnimation() != null) {
            RightUserRing.clearAnimation();
        }
        if (LeftUserRing.getAnimation() != null) {
            LeftUserRing.clearAnimation();
        }
        if (BottomUserRing.getAnimation() != null) {
            BottomUserRing.clearAnimation();
        }
        LeftUserRing.setVisibility(View.INVISIBLE);
        RightUserRing.setVisibility(View.INVISIBLE);
        BottomUserRing.setVisibility(View.INVISIBLE);
        if (btnKnock.getVisibility() == View.VISIBLE) {
            if (btnKnock.getAnimation() != null) {
                btnKnock.clearAnimation();
            }
            btnKnock.setVisibility(View.INVISIBLE);
        }
        if (ivScore_knock.getVisibility() == View.VISIBLE) {
            if (ivScore_knock.getAnimation() != null) {
                ivScore_knock.clearAnimation();
            }
            ivScore_knock.setVisibility(View.INVISIBLE);
        }
        if (btnGin.getVisibility() == View.VISIBLE) {
            if (btnGin.getAnimation() != null) {
                btnGin.clearAnimation();
            }
            btnGin.setVisibility(View.INVISIBLE);
        }
        int seatIndex = -1;
        for (int i = 0; i < DealCardToSeat.length; i++) {
            if (DealCardToSeat[i]) {
                seatIndex = i;
                break;
            }
        }
        if (is3danimation) {
            frmtable.setRotationX(0);
            frmDeckBackGround.setRotationX(0);
        }
        ivGinRummyText.setVisibility(View.INVISIBLE);
        showInfoDialog("Card Finished!", "There is not any card left in the stock\nand the winner was not determined\nfor that round...");
//        Logger.print("Draw from seat INDEX => " + seatIndex);
//        if (GameisOn())
//            AnimateDrawText(seatIndex);
//
//        JSONObject rtsObject = getScoreboardData();
//        Logger.print("Round SCore data " + rtsObject.toString());
//        if (PlayScreen2.handler != null) {
//            Message message = new Message();
//            message.what = ResponseCodes.RoundTimerStartResp;
//            message.obj = rtsObject.toString();
//            PlayScreen2.handler.sendMessage(message);
//        }
    }

    private void showInfoDialog(String DialogTitle, final String Message) {
        // TODO Auto-generated method stub
        LinearLayout.LayoutParams lin;
        if (ConfirmationDialogInfo != null || (ConfirmationDialogInfo != null && ConfirmationDialogInfo.isShowing())) {
            if (ConfirmationDialogInfo.isShowing()) {
                ConfirmationDialogInfo.dismiss();
            }
            ConfirmationDialogInfo = null;
        }
        ConfirmationDialogInfo = new Dialog(
                this,
                R.style.Theme_Transparent);
        ConfirmationDialogInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ConfirmationDialogInfo.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        ConfirmationDialogInfo.setContentView(R.layout.dialog);
        ConfirmationDialogInfo.setCancelable(false);
        TextView title = ConfirmationDialogInfo
                .findViewById(R.id.tvTitle);
        title.setPadding(getwidth(20), getheight(20), getwidth(20),
                getheight(20));
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
        title.setTypeface(c.typeface);
        title.setText(DialogTitle);
        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.line).getLayoutParams();
        lin.height = getheight(4);
        lin.width = getwidth(600);
        TextView message = ConfirmationDialogInfo
                .findViewById(R.id.tvMessage);
        message.setTypeface(c.typeface);
        message.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));
        Button btnOne = ConfirmationDialogInfo.findViewById(R.id.button1);
        btnOne.setTypeface(c.typeface);
        btnOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnOne.setText("Continue");
        Button btnTwo = ConfirmationDialogInfo.findViewById(R.id.button2);
        btnTwo.setVisibility(View.GONE);
        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.button1).getLayoutParams();
        lin.height = getheight(80);
        lin.width = getwidth(180);
        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.tvMessage).getLayoutParams();
        lin.topMargin = getheight(30);
        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.llButtons).getLayoutParams();
        lin.topMargin = getheight(30);
        message.setText(Message);
        btnOne.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method
                Music_Manager.buttonclick();
                ConfirmationDialogInfo.dismiss();
                Message msg = new Message();
                msg.what = ResponseCodes.Start_New_Round;
                if (handler != null) {
                    iv_2X.setVisibility(View.GONE);
                    c.isDouble = false;
                    c.multi = 1;
                    Logger.print(">>>>>> TWO X PROBLM >>>> Start_New_Round MESSAGE SENT ");
                    handler.sendMessage(msg);
                }
            }
        });
        if (!isFinishing())
            ConfirmationDialogInfo.show();
    }

    private void GinResponseProcess(String response, ArrayList<Item_Card> userCards) {
        Logger.print("StackOverflow => GinResponseProcess called ");
        isSortButtonClickable = false;
        isCardTouchable = false;
        int GP = 25;
        GAME_OVER = true;
        boolean isBigGin = false;
        if (RightUserRing.getAnimation() != null) {
            RightUserRing.clearAnimation();
        }
        if (LeftUserRing.getAnimation() != null) {
            LeftUserRing.clearAnimation();
        }
        if (BottomUserRing.getAnimation() != null) {
            BottomUserRing.clearAnimation();
        }
        LeftUserRing.setVisibility(View.INVISIBLE);
        RightUserRing.setVisibility(View.INVISIBLE);
        BottomUserRing.setVisibility(View.INVISIBLE);
        if (btnKnock.getVisibility() == View.VISIBLE) {
            if (btnKnock.getAnimation() != null) {
                btnKnock.clearAnimation();
            }
            btnKnock.setVisibility(View.INVISIBLE);
        }
        if (ivScore_knock.getVisibility() == View.VISIBLE) {
            if (ivScore_knock.getAnimation() != null) {
                ivScore_knock.clearAnimation();
            }
            ivScore_knock.setVisibility(View.INVISIBLE);
        }
        if (btnGin.getVisibility() == View.VISIBLE) {
            if (btnGin.getAnimation() != null) {
                btnGin.clearAnimation();
            }
            btnGin.setVisibility(View.INVISIBLE);
        }
        JSONObject jGin;
        try {
            jGin = new JSONObject(response);
            int seat = jGin.getJSONObject(Parameters.data).getInt(
                    Parameters.SeatIndex);
            c.isKnocked = false;
            Logger.print("StackOverflow => GinResponseProcess called 1");
            String cardToThrow = "";
            for (int i = 0; i < userCards.size(); i++) {
                if (!userCards.get(i).getIsValidGroup()) {
                    cardToThrow = getCardString(userCards
                            .get(i));
                }
            }
            if (seat == BottomSeatIndex) {
                Logger.print("StackOverflow => GinResponseProcess called 2");
                if (cardToThrow != null
                        && !cardToThrow.contentEquals("")) {
                    JSONArray array_card = new JSONArray();
                    array_card.put(cardToThrow);
                    ThrowRemainingCard(array_card);
                } else {
                    isBigGin = true;
                }
            } else {
                Logger.print("cards by ROBOT => " + userCards.toString());
                Logger.print("throw card by ROBOT => " + cardToThrow);
                if (cardToThrow != null
                        && !cardToThrow.contentEquals("")) {
                    Item_Card thrownCard = getItemCard(cardToThrow);
                    openBunchCard.add(thrownCard);
                    removeElement(userCards, thrownCard);
                    AnimateUserThrowCardOnOpenDeck(seat, cardToThrow);
                } else {
                    isBigGin = true;
                }
            }
            if (isBigGin) {
                GP = 31;
            }
            Logger.print("StackOverflow => GinResponseProcess called 3");
            if (GameisOn()) {
                if (isBigGin) {
                    AnimateBigGinText(seat);
                } else {
                    AnimateGinText(seat);
                }
            }
            Logger.print("StackOverflow => GinResponseProcess called 4 seat " + seat);
            if (seat == BottomSeatIndex) {
                //====================== Quest Counter ====================================
                c.lostCount = 0;
                if (c.gameType == 1) {
//                    PreferenceManager.SetQuestStraightCount(PreferenceManager.GetQuestStraightCount() + 1);
                } else if (c.gameType == 3) {
//                    PreferenceManager.SetQuestOklahomaCount(PreferenceManager.GetQuestOklahomaCount() + 1);
                } else if (c.gameType == 2) {
                } else if (c.gameType == 0) {
                    if (isBigGin) {
                        PreferenceManager.SetQuestBigGinCount(PreferenceManager.GetQuestBigGinCount() + 1);
                    } else {
                        PreferenceManager.SetQuestGinCount(PreferenceManager.GetQuestGinCount() + 1);
                    }
                }
                Dashboard.savedGamesUpdate();
                //=========================================================================
                int ginPoint = (RightDeadWood + LeftDeadWood) * c.multi;
                scoreboardData[BottomSeatIndex].getPoints().put(ginPoint + GP);
                JSONObject jObject = new JSONObject();
                jObject.put("GP", GP);
                jObject.put("DP", ginPoint);
                jObject.put("WP", 0);
                scoreboardData[BottomSeatIndex].getSt().put(jObject);
                scoreboardData[BottomSeatIndex].setPs(scoreboardData[BottomSeatIndex].getPs() + (ginPoint + GP));
                scoreboardData[RightSeatIndex].getPoints().put(0);
                jObject = new JSONObject();
                jObject.put("GP", 0);
                jObject.put("DP", 0);
                jObject.put("WP", 0);
                scoreboardData[RightSeatIndex].getSt().put(jObject);
                if (numberOfPlayers > 2) {
                    scoreboardData[LeftSeatIndex].getPoints().put(0);
                    jObject = new JSONObject();
                    jObject.put("GP", 0);
                    jObject.put("DP", 0);
                    jObject.put("WP", 0);
                    scoreboardData[LeftSeatIndex].getSt().put(jObject);
                }
            } else if (seat == RightSeatIndex) {
                c.lostCount++;
                if (c.gameType == 1) {
                } else if (c.gameType == 3) {
                } else if (c.gameType == 2) {
                } else if (c.gameType == 0) {
                }
                int ginPoint = (BottomDeadWood + LeftDeadWood) * c.multi;
                scoreboardData[RightSeatIndex].getPoints().put(ginPoint + GP);
                JSONObject jObject = new JSONObject();
                jObject.put("GP", GP);
                jObject.put("DP", ginPoint);
                jObject.put("WP", 0);
                scoreboardData[RightSeatIndex].getSt().put(jObject);
                scoreboardData[RightSeatIndex].setPs(scoreboardData[RightSeatIndex].getPs() + (ginPoint + GP));
                scoreboardData[BottomSeatIndex].getPoints().put(0);
                jObject = new JSONObject();
                jObject.put("GP", 0);
                jObject.put("DP", 0);
                jObject.put("WP", 0);
                scoreboardData[BottomSeatIndex].getSt().put(jObject);
                if (numberOfPlayers > 2) {
                    scoreboardData[LeftSeatIndex].getPoints().put(0);
                    jObject = new JSONObject();
                    jObject.put("GP", 0);
                    jObject.put("DP", 0);
                    jObject.put("WP", 0);
                    scoreboardData[LeftSeatIndex].getSt().put(jObject);
                }
            } else if (seat == LeftSeatIndex) {
                c.lostCount++;
                if (c.gameType == 1) {
                } else if (c.gameType == 3) {
                } else if (c.gameType == 2) {
                } else if (c.gameType == 0) {
                }
                int ginPoint = (RightDeadWood + BottomDeadWood) * c.multi;
                scoreboardData[LeftSeatIndex].getPoints().put(ginPoint + GP);
                JSONObject jObject = new JSONObject();
                jObject.put("GP", GP);
                jObject.put("DP", ginPoint);
                jObject.put("WP", 0);
                scoreboardData[LeftSeatIndex].getSt().put(jObject);
                scoreboardData[LeftSeatIndex].setPs(scoreboardData[LeftSeatIndex].getPs() + (ginPoint + GP));
                scoreboardData[BottomSeatIndex].getPoints().put(0);
                jObject = new JSONObject();
                jObject.put("GP", 0);
                jObject.put("DP", 0);
                jObject.put("WP", 0);
                scoreboardData[BottomSeatIndex].getSt().put(jObject);
                if (numberOfPlayers > 2) {
                    scoreboardData[RightSeatIndex].getPoints().put(0);
                    jObject = new JSONObject();
                    jObject.put("GP", 0);
                    jObject.put("DP", 0);
                    jObject.put("WP", 0);
                    scoreboardData[RightSeatIndex].getSt().put(jObject);
                }
            }
            tvScoreThree.setText("" + scoreboardData[BottomSeatIndex].getPs());
            tvScoreTwo.setText("" + scoreboardData[RightSeatIndex].getPs());
            if (numberOfPlayers > 2) {
                tvScore.setText("" + scoreboardData[LeftSeatIndex].getPs());
            }
            Logger.print("-----------SCORECARD Bottom--------------- " + scoreboardData[BottomSeatIndex].toString());
            Logger.print("-----------SCORECARD Right--------------- " + scoreboardData[RightSeatIndex].toString());
            Logger.print("-----------SCORECARD Left--------------- " + scoreboardData[LeftSeatIndex].toString());
            int winnerpoints = 0;
            /*for (int i = 0; i < scoreboardData[seat].getPoints().length(); i++) {
                try {
                    winnerpoints += scoreboardData[seat].getPoints().getInt(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }*/
            winnerpoints = scoreboardData[seat].getPs();
            Logger.print("************************************** GIN " + winnerpoints + " " + GAME_POINT);
            Logger.print("StackOverflow => GinResponseProcess called 6 ");
            if (winnerpoints >= GAME_POINT) {
                Logger.print("WINNER DECLARED ::::::::: ");
                JSONObject wdObject = getWinnerData(seat);
                if (PlayScreen2.handler != null) {
                    Message message = new Message();
                    message.what = ResponseCodes.WinnerDeclaredResp;
                    message.obj = wdObject.toString();
                    PlayScreen2.handler.sendMessage(message);
                }
            } else {
                JSONObject rtsObject = getScoreboardData();
                Logger.print("Round SCore data " + rtsObject.toString());
                if (PlayScreen2.handler != null) {
                    Message message = new Message();
                    message.what = ResponseCodes.RoundTimerStartResp;
                    message.obj = rtsObject.toString();
                    PlayScreen2.handler.sendMessage(message);
                }
            }
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    private void KnockResponseProcess(String response) {
        isSortButtonClickable = false;
        isCardTouchable = false;
        GAME_OVER = true;
        //KnockAnimation();
        if (RightUserRing.getAnimation() != null) {
            RightUserRing.clearAnimation();
        }
        if (LeftUserRing.getAnimation() != null) {
            LeftUserRing.clearAnimation();
        }
        if (BottomUserRing.getAnimation() != null) {
            BottomUserRing.clearAnimation();
        }
        LeftUserRing.setVisibility(View.INVISIBLE);
        RightUserRing.setVisibility(View.INVISIBLE);
        BottomUserRing.setVisibility(View.INVISIBLE);
        if (btnKnock.getVisibility() == View.VISIBLE) {
            if (btnKnock.getAnimation() != null) {
                btnKnock.clearAnimation();
            }
            btnKnock.setVisibility(View.INVISIBLE);
        }
        if (ivScore_knock.getVisibility() == View.VISIBLE) {
            if (ivScore_knock.getAnimation() != null) {
                ivScore_knock.clearAnimation();
            }
            ivScore_knock.setVisibility(View.INVISIBLE);
        }
        if (btnGin.getVisibility() == View.VISIBLE) {
            if (btnGin.getAnimation() != null) {
                btnGin.clearAnimation();
            }
            btnGin.setVisibility(View.INVISIBLE);
        }
        try {
            JSONObject jObjKnk = new JSONObject(response);
            final int si = jObjKnk.getJSONObject(Parameters.data)
                    .getInt(Parameters.SeatIndex);
            c.isGined = false;
            c.isKnocked = true;
            c.SeatIndexOfUser = si;
            if (si != BottomSeatIndex) {
                ThrowCardOnDeckProcess(jObjKnk.toString());
            }
            if (GameisOn()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        AnimateKnockText(si);
                    }
                }, 500);
            }
            int winnerSeat = -1;
            int deadwood = 0;
            boolean isKnockPenalty = false;
            HashMap<Integer, Integer> deadwoodMap = new HashMap<>();
            if (numberOfPlayers == 2) {
                deadwoodMap.put(BottomSeatIndex, BottomDeadWood);
                deadwoodMap.put(RightSeatIndex, RightDeadWood);
                deadwoodMap = sortByValues(deadwoodMap);
            } else if (numberOfPlayers == 3) {
                deadwoodMap.put(BottomSeatIndex, BottomDeadWood);
                deadwoodMap.put(RightSeatIndex, RightDeadWood);
                deadwoodMap.put(LeftSeatIndex, LeftDeadWood);
                deadwoodMap = sortByValues(deadwoodMap);
            }
            ArrayList<Integer> keys = new ArrayList<>();
            keys.addAll(deadwoodMap.keySet());
            Logger.print("Key of HASHMAP => " + keys.toString() + " " + si + " " + deadwoodMap.toString());
            if (keys.get(0) == si) {
                int scr0 = deadwoodMap.get(keys.get(0));
                int scr1 = deadwoodMap.get(keys.get(1));
                Logger.print("Score of scr0 and scr1 :: " + scr0 + " " + scr1);
                if (scr0 == scr1) {
                    winnerSeat = keys.get(1);
                } else {
                    winnerSeat = keys.get(0);
                }
            } else {
                Map.Entry<Integer, Integer> entry = deadwoodMap.entrySet().iterator().next();
                winnerSeat = entry.getKey();
            }
            Logger.print("--------------------IsknockPenalty---------------- " + winnerSeat + " " + si + " " + isKnockPenalty);
            if (winnerSeat != si) {
                isKnockPenalty = true;
            }
            Logger.print("--------------------IsknockPenalty---------------- 1 " + isKnockPenalty);
            if ((winnerSeat == si) && (si == BottomSeatIndex)) {
                //====================== Quest Counter ====================================
                c.lostCount = 0;
//                if(c.gameType == 1){
//                    PreferenceManager.SetQuestStraightCount(PreferenceManager.GetQuestStraightCount() + 1);
//                }else if(c.gameType == 3){
//                    PreferenceManager.SetQuestOklahomaCount(PreferenceManager.GetQuestOklahomaCount() + 1);
//                }else
                if (c.gameType == 0) {
                    PreferenceManager.SetQuestKnockCount(PreferenceManager.GetQuestKnockCount() + 1);
                    Logger.print("MMMMMMMMM QUEST Counter>>> Knock increment >>> " + PreferenceManager.GetQuestKnockCount());
                }
                Dashboard.savedGamesUpdate();
                //=========================================================================
            }
            Logger.print("------------------------DeadWood-------------------- " + BottomDeadWood + " " + RightDeadWood + " " + LeftDeadWood);
            if (winnerSeat == BottomSeatIndex) {
                if (c.gameType == 1) {
                } else if (c.gameType == 3) {
                } else if (c.gameType == 2) {
                } else if (c.gameType == 0) {
                }
                deadwood = ((RightDeadWood + LeftDeadWood) - BottomDeadWood) * c.multi;
            } else if (winnerSeat == RightSeatIndex) {
                if (c.gameType == 1) {
                } else if (c.gameType == 3) {
                } else if (c.gameType == 2) {
                } else if (c.gameType == 0) {
                }
                c.lostCount++;
                deadwood = ((BottomDeadWood + LeftDeadWood) - RightDeadWood) * c.multi;
            } else if (winnerSeat == LeftSeatIndex) {
                if (c.gameType == 1) {
                } else if (c.gameType == 3) {
                } else if (c.gameType == 2) {
                } else if (c.gameType == 0) {
                }
                c.lostCount++;
                deadwood = ((RightDeadWood + BottomDeadWood) - LeftDeadWood) * c.multi;
            }
            //if (si == BottomSeatIndex) {
            JSONObject jObject = new JSONObject();
            if (winnerSeat == BottomSeatIndex) {
                if (isKnockPenalty) {
                    scoreboardData[BottomSeatIndex].getPoints().put(25 + deadwood);
                    jObject.put("KP", 25);
                    scoreboardData[BottomSeatIndex].setPs(scoreboardData[BottomSeatIndex].getPs() + (25 + deadwood));
                } else {
                    scoreboardData[BottomSeatIndex].getPoints().put(deadwood);
                    jObject.put("KP", 0);
                    scoreboardData[BottomSeatIndex].setPs(scoreboardData[BottomSeatIndex].getPs() + (deadwood));
                }
                jObject.put("DP", deadwood);
                jObject.put("WP", 0);
            } else {
                scoreboardData[BottomSeatIndex].getPoints().put(0);
                scoreboardData[BottomSeatIndex].setPs(scoreboardData[BottomSeatIndex].getPs());
                jObject.put("KP", 0);
                jObject.put("DP", 0);
                jObject.put("WP", 0);
            }
            scoreboardData[BottomSeatIndex].getSt().put(jObject);
            jObject = new JSONObject();
            if (winnerSeat == RightSeatIndex) {
                if (isKnockPenalty) {
                    scoreboardData[RightSeatIndex].getPoints().put(25 + deadwood);
                    jObject.put("KP", 25);
                    scoreboardData[RightSeatIndex].setPs(scoreboardData[RightSeatIndex].getPs() + (25 + deadwood));
                } else {
                    scoreboardData[RightSeatIndex].getPoints().put(deadwood);
                    jObject.put("KP", 0);
                    scoreboardData[RightSeatIndex].setPs(scoreboardData[RightSeatIndex].getPs() + (deadwood));
                }
                jObject.put("DP", deadwood);
                jObject.put("WP", 0);
            } else {
                scoreboardData[RightSeatIndex].setPs(scoreboardData[RightSeatIndex].getPs());
                scoreboardData[RightSeatIndex].getPoints().put(0);
                jObject.put("KP", 0);
                jObject.put("DP", 0);
                jObject.put("WP", 0);
            }
            scoreboardData[RightSeatIndex].getSt().put(jObject);
            tvScoreThree.setText("" + scoreboardData[BottomSeatIndex].getPs());
            tvScoreTwo.setText("" + scoreboardData[RightSeatIndex].getPs());
            PreferenceManager.setBottomUSerScore(scoreboardData[BottomSeatIndex].getPs());
            PreferenceManager.setrightUSerScore(scoreboardData[RightSeatIndex].getPs());
            if (numberOfPlayers > 2) {
                jObject = new JSONObject();
                if (winnerSeat == LeftSeatIndex) {
                    if (isKnockPenalty) {
                        scoreboardData[LeftSeatIndex].getPoints().put(25 + deadwood);
                        jObject.put("KP", 25);
                        scoreboardData[LeftSeatIndex].setPs(scoreboardData[LeftSeatIndex].getPs() + (25 + deadwood));
                    } else {
                        scoreboardData[LeftSeatIndex].getPoints().put(deadwood);
                        jObject.put("KP", 0);
                        scoreboardData[LeftSeatIndex].setPs(scoreboardData[LeftSeatIndex].getPs() + (deadwood));
                    }
                    jObject.put("DP", deadwood);
                } else {
                    scoreboardData[LeftSeatIndex].getPoints().put(0);
                    scoreboardData[LeftSeatIndex].setPs(scoreboardData[LeftSeatIndex].getPs());
                    jObject.put("KP", 0);
                    jObject.put("DP", 0);
                }
                jObject.put("WP", 0);
                scoreboardData[LeftSeatIndex].getSt().put(jObject);
                tvScore.setText("" + scoreboardData[LeftSeatIndex].getPs());
                PreferenceManager.setleftUSerScore(scoreboardData[LeftSeatIndex].getPs());
            }

            Logger.print("-----------SCORECARD Bottom KNOCK--------------- " + scoreboardData[BottomSeatIndex].toString());
            Logger.print("-----------SCORECARD Right KNOCK--------------- " + scoreboardData[RightSeatIndex].toString());
            Logger.print("-----------SCORECARD Left KNOCK--------------- " + scoreboardData[LeftSeatIndex].toString());
            int winnerpoints = 0;
            /*for (int i = 0; i < scoreboardData[winnerSeat].getPoints().length(); i++) {
                try {
                    winnerpoints = scoreboardData[winnerSeat].getPoints().getInt(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }*/
            winnerpoints = scoreboardData[winnerSeat].getPs();
            Logger.print("************************************** KNOCK " + winnerpoints + " " + GAME_POINT);
            if (winnerpoints >= GAME_POINT) {
                Logger.print("WINNER DECLARED ::::::::: ");
                JSONObject wdObject = getWinnerData(winnerSeat);
                if (PlayScreen2.handler != null) {
                    Message message = new Message();
                    message.what = ResponseCodes.WinnerDeclaredResp;
                    message.obj = wdObject.toString();
                    PlayScreen2.handler.sendMessage(message);
                }
            } else {
                JSONObject rtsObject = getScoreboardData();
                Logger.print("Round SCore data " + rtsObject.toString());
                if (PlayScreen2.handler != null) {
                    Message message = new Message();
                    message.what = ResponseCodes.RoundTimerStartResp;
                    message.obj = rtsObject.toString();
                    PlayScreen2.handler.sendMessage(message);
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private JSONObject getWinnerData(int winner) {
        JSONArray playerInfo = new JSONArray();
        JSONObject player = new JSONObject();
        JSONObject playerRight = new JSONObject();
        JSONObject playerLeft = new JSONObject();
        int bpoints = 0, lpoints = 0, rpoints = 0;
        for (int i = 0; i < scoreboardData[BottomSeatIndex].getPoints().length(); i++) {
            try {
                bpoints = scoreboardData[BottomSeatIndex].getPoints().getInt(i);
                rpoints = scoreboardData[RightSeatIndex].getPoints().getInt(i);
                if (numberOfPlayers > 2) {
                    lpoints = scoreboardData[LeftSeatIndex].getPoints().getInt(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            player.put(Parameters.SeatIndex, BottomSeatIndex);
            player.put(Parameters.points, scoreboardData[BottomSeatIndex].getPoints());
            player.put("st", scoreboardData[BottomSeatIndex].getSt());
            player.put(Parameters.User_Name, players[BottomSeatIndex].getUsername());
            player.put(Parameters.ProfilePicture, players[BottomSeatIndex].getProfilePicture());
            player.put(Parameters.totalsum, scoreboardData[BottomSeatIndex].getPs());
            player.put(Parameters.User_Id, PreferenceManager.get_id());
            JSONObject setSeq = getSetSequence(false, card_BottomUser);
            player.put(Parameters.NewCards, setSeq);
            JSONObject userInfo = new JSONObject();
            userInfo.put(Parameters._id, players[BottomSeatIndex].getUserId());
            userInfo.put(Parameters.User_Name, players[BottomSeatIndex].getUsername());
            userInfo.put(Parameters.ProfilePicture, players[BottomSeatIndex].getProfilePicture());
            userInfo.put(Parameters.SeatIndex, BottomSeatIndex);
            player.put(Parameters.User_Info, userInfo);
            if (winner == BottomSeatIndex) {
                player.put(Parameters.winningChips, (BOOT_VALUE * numberOfPlayers));
                player.put(Parameters.winner, true);
                c.Chips = PreferenceManager.getChips() + (BOOT_VALUE * numberOfPlayers);
                PreferenceManager.setChips(c.Chips);
                sqLiteManager.updateChip(PreferenceManager.get_id(), BOOT_VALUE * numberOfPlayers, true);
                c.lostChip = c.lostChip - (BOOT_VALUE * numberOfPlayers);
                Logger.print(">>>> LOST CHIP 2222 >>> " + c.lostChip);

            } else {
                player.put(Parameters.winningChips, (BOOT_VALUE * -1));
            }
            playerInfo.put(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            playerRight.put(Parameters.SeatIndex, RightSeatIndex);
            playerRight.put(Parameters.points, scoreboardData[RightSeatIndex].getPoints());
            playerRight.put("st", scoreboardData[RightSeatIndex].getSt());
            playerRight.put(Parameters.User_Name, players[RightSeatIndex].getUsername());
            playerRight.put(Parameters.ProfilePicture, players[RightSeatIndex].getProfilePicture());
            playerRight.put(Parameters.totalsum, scoreboardData[RightSeatIndex].getPs());
            playerRight.put(Parameters.User_Id, "rightuser");
            JSONObject setSeq = getSetSequence(false, card_RightUser);
            playerRight.put(Parameters.NewCards, setSeq);
            JSONObject userInfo = new JSONObject();
            userInfo.put(Parameters._id, players[RightSeatIndex].getUserId());
            userInfo.put(Parameters.User_Name, players[RightSeatIndex].getUsername());
            userInfo.put(Parameters.ProfilePicture, players[RightSeatIndex].getProfilePicture());
            userInfo.put(Parameters.SeatIndex, RightSeatIndex);
            playerRight.put(Parameters.User_Info, userInfo);
            if (winner == RightSeatIndex) {
                playerRight.put(Parameters.winningChips, (BOOT_VALUE * numberOfPlayers));
                playerRight.put(Parameters.winner, true);
                sqLiteManager.updateChip(playingUsers.get(0).get(c.ID), BOOT_VALUE * numberOfPlayers, false);
            } else {
                playerRight.put(Parameters.winningChips, (BOOT_VALUE * -1));
            }
            playerInfo.put(playerRight);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (numberOfPlayers > 2) {
            try {
                playerLeft.put(Parameters.SeatIndex, LeftSeatIndex);
                playerLeft.put(Parameters.points, scoreboardData[LeftSeatIndex].getPoints());
                playerLeft.put("st", scoreboardData[LeftSeatIndex].getSt());
                playerLeft.put(Parameters.User_Name, players[LeftSeatIndex].getUsername());
                playerLeft.put(Parameters.ProfilePicture, players[LeftSeatIndex].getProfilePicture());
                playerLeft.put(Parameters.totalsum, scoreboardData[LeftSeatIndex].getPs());
                playerLeft.put(Parameters.User_Id, "leftuser");
                JSONObject setSeq = getSetSequence(false, card_LeftUser);
                playerLeft.put(Parameters.NewCards, setSeq);
                JSONObject userInfo = new JSONObject();
                userInfo.put(Parameters._id, players[LeftSeatIndex].getUserId());
                userInfo.put(Parameters.User_Name, players[LeftSeatIndex].getUsername());
                userInfo.put(Parameters.ProfilePicture, players[LeftSeatIndex].getProfilePicture());
                userInfo.put(Parameters.SeatIndex, LeftSeatIndex);
                playerLeft.put(Parameters.User_Info, userInfo);
                if (winner == LeftSeatIndex) {
                    playerLeft.put(Parameters.winningChips, (BOOT_VALUE * numberOfPlayers));
                    playerLeft.put(Parameters.winner, true);
                    sqLiteManager.updateChip(playingUsers.get(1).get(c.ID), BOOT_VALUE * numberOfPlayers, false);
                } else {
                    playerLeft.put(Parameters.winningChips, (BOOT_VALUE * -1));
                }
                playerInfo.put(playerLeft);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        JSONObject dataInfo = new JSONObject();
        JSONObject dataObj = new JSONObject();
        try {
            dataInfo.put(Parameters.PlayersInfo, playerInfo);
            dataInfo.put(Parameters.Round, ROUND_NUMBER);
            dataObj.put(Parameters.data, dataInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataObj;
    }

    private JSONObject getScoreboardData() {
        JSONArray playerInfo = new JSONArray();
        JSONObject player = new JSONObject();
        JSONObject playerRight = new JSONObject();
        JSONObject playerLeft = new JSONObject();
       /* int bpoints = 0, lpoints = 0, rpoints = 0;
        for (int i = 0; i < scoreboardData[BottomSeatIndex].getPoints().length(); i++) {
            try {
                bpoints = scoreboardData[BottomSeatIndex].getPoints().getInt(i);
                rpoints = scoreboardData[RightSeatIndex].getPoints().getInt(i);
                if (numberOfPlayers > 2) {
                    lpoints = scoreboardData[LeftSeatIndex].getPoints().getInt(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/
        try {
            player.put(Parameters.SeatIndex, BottomSeatIndex);
            player.put(Parameters.points, scoreboardData[BottomSeatIndex].getPoints());
            player.put("st", scoreboardData[BottomSeatIndex].getSt());
            player.put(Parameters.User_Name, players[BottomSeatIndex].getUsername());
            player.put(Parameters.ProfilePicture, players[BottomSeatIndex].getProfilePicture());
            player.put(Parameters.totalsum, scoreboardData[BottomSeatIndex].getPs());
            player.put(Parameters.User_Id, PreferenceManager.get_id());
            JSONObject setSeq = getSetSequence(false, card_BottomUser);
            player.put(Parameters.NewCards, setSeq);
            playerInfo.put(player);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            playerRight.put(Parameters.SeatIndex, RightSeatIndex);
            playerRight.put(Parameters.points, scoreboardData[RightSeatIndex].getPoints());
            playerRight.put("st", scoreboardData[RightSeatIndex].getSt());
            playerRight.put(Parameters.User_Name, players[RightSeatIndex].getUsername());
            playerRight.put(Parameters.ProfilePicture, players[RightSeatIndex].getProfilePicture());
            playerRight.put(Parameters.totalsum, scoreboardData[RightSeatIndex].getPs());
            playerRight.put(Parameters.User_Id, "rightuser");
            JSONObject setSeq = getSetSequence(false, card_RightUser);
            playerRight.put(Parameters.NewCards, setSeq);
            playerInfo.put(playerRight);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (numberOfPlayers > 2) {
            try {
                playerLeft.put(Parameters.SeatIndex, LeftSeatIndex);
                playerLeft.put(Parameters.points, scoreboardData[LeftSeatIndex].getPoints());
                playerLeft.put("st", scoreboardData[LeftSeatIndex].getSt());
                playerLeft.put(Parameters.User_Name, players[LeftSeatIndex].getUsername());
                playerLeft.put(Parameters.ProfilePicture, players[LeftSeatIndex].getProfilePicture());
                playerLeft.put(Parameters.totalsum, scoreboardData[LeftSeatIndex].getPs());
                playerLeft.put(Parameters.User_Id, "leftuser");
                JSONObject setSeq = getSetSequence(false, card_LeftUser);
                playerLeft.put(Parameters.NewCards, setSeq);
                playerInfo.put(playerLeft);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        JSONObject dataInfo = new JSONObject();
        JSONObject dataObj = new JSONObject();
        try {
            dataInfo.put(Parameters.PlayersInfo, playerInfo);
            dataInfo.put(Parameters.Round, ROUND_NUMBER);
            dataObj.put(Parameters.data, dataInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataObj;
    }

    private void SeeMyCardEvent(JSONObject MyCards) {
        Logger.print("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL>>>>>>>> SMC");
        try {
            JSONArray cards = MyCards
                    .getJSONObject(Parameters.data).getJSONArray(
                            Parameters.Cards);
            Logger.print("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL => " + " Card Bottom User => " + card_BottomUser.size() + " Server Side => " + cards.length());
            Logger.print("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL => " + " Card Serevr => " + cards.toString());
            Logger.print("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL => " + " Card User => " + card_BottomUser.toString());
            HelpText.setVisibility(View.GONE);
            if (card_BottomUser.size() > 0) {
                String cardTemp;
                ArrayList<Item_Card> tempArray = new ArrayList<>();
                // ArrayList<Item_Card> tempArray2 = new ArrayList<>();
                for (int temp = 0; temp < cards.length(); temp++) {
                    cardTemp = cards.getString(temp);
                    Item_Card card = getItemCard(cardTemp);
                    tempArray.add(card);
                }
                Logger.print("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL => " + " Temp Array User => " + tempArray.size());
                for (int j = card_BottomUser.size() - 1; j >= 0; j--) {
                    if (!tempArray.contains(card_BottomUser.get(j))) {
                        card_BottomUser.remove(j);
                    }
                }
                if (card_BottomUser.size() < cardsize || card_BottomUser.size() > cardsize + 1 || card_BottomUser.size() < 7) {
                    card_BottomUser = new ArrayList<>(Sort(tempArray, BottomSeatIndex));
                }
                Logger.print("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL => " + " Card Bottom User => " + card_BottomUser.size() + " CardSize => " + cardsize);
            } else {
                if (card_BottomUser.size() > 0)
                    card_BottomUser.clear();
                String Card;
                for (int i = 0; i < cards.length(); i++) {
                    Card = cards.getString(i);
                    Item_Card card = getItemCard(Card);
                    card_BottomUser.add(card);
                }
                card_BottomUser = new ArrayList<>(Sort(card_BottomUser, BottomSeatIndex));
            }
            if (isSeeMyCardCalledFromGti) {
                isSeeMyCardCalledFromGti = false;
                cardsize = card_BottomUser.size();
                if (cardsize == 8)
                    cardsize = 7;
                if (cardsize == 11)
                    cardsize = 10;
            }
            reDrawUserCards("");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void resetAfterWinning() {
        // TODO Auto-generated method stub
        /*HelpText.setVisibility(View.GONE);*/
        btnFaceUp.setVisibility(View.GONE);
        btnDrawNew.setVisibility(View.GONE);
        btnDiscard.setVisibility(View.GONE);
        ivCloseCard.setClickable(false);
        ivCloseDeck.setClickable(false);
        Logger.print("resetAfterWinning Called");
        tvScore.setText("0");
        tvScoreThree.setText("0");
        tvScoreTwo.setText("0");
        if (ivCloseCard.getAnimation() != null) {
            ivCloseCard.clearAnimation();
        }
        ivCloseCard.setVisibility(View.INVISIBLE);
        for (ImageView ivBoot : ivBootAnim) {
            if (ivBoot.getAnimation() != null) {
                ivBoot.clearAnimation();
            }
            ivBoot.setVisibility(View.INVISIBLE);
        }
        tvTotalBetValue.setText("");
        frmMesage.bottomMargin = getheight(0);
        ivUserOneCard.setVisibility(View.INVISIBLE);
        ivUserTwoCard.setVisibility(View.INVISIBLE);
        /*if (c.myScoreList.size() > 0) {
            c.myScoreList.clear();
        }*/
        if (ivHandOpenCard.getAnimation() != null)
            ivHandOpenCard.clearAnimation();
        if (ivHandCloseDeck.getAnimation() != null)
            ivHandCloseDeck.clearAnimation();
        if (UserTimer != null)
            UserTimer.cancel();
        ivHandCloseDeck.setVisibility(View.INVISIBLE);
        ivHandOpenCard.setVisibility(View.INVISIBLE);
        if (btnSkip.getVisibility() == View.VISIBLE) {
            if (btnSkip.getAnimation() != null) {
                btnSkip.clearAnimation();
            }
            btnSkip.setVisibility(View.GONE);

        }
        if (btnGin.getVisibility() == View.VISIBLE) {
            if (btnGin.getAnimation() != null) {
                btnGin.clearAnimation();
            }
            btnGin.setVisibility(View.GONE);
        }
        if (btnKnock.getVisibility() == View.VISIBLE) {
            if (btnKnock.getAnimation() != null) {
                btnKnock.clearAnimation();
            }
            btnKnock.setVisibility(View.GONE);
        }
        if (ivScore_knock.getVisibility() == View.VISIBLE) {
            if (ivScore_knock.getAnimation() != null) {
                ivScore_knock.clearAnimation();
            }
            ivScore_knock.setVisibility(View.INVISIBLE);
        }
        if (LeftUserRing.getAnimation() != null) {
            LeftUserRing.clearAnimation();
        }
        LeftUserRing.setVisibility(View.INVISIBLE);
        if (RightUserRing.getAnimation() != null) {
            RightUserRing.clearAnimation();
        }
        RightUserRing.setVisibility(View.INVISIBLE);
        if (BottomUserRing.getAnimation() != null) {
            BottomUserRing.clearAnimation();
        }
        BottomUserRing.setVisibility(View.INVISIBLE);
        clearSomeData();
        if (ivScore.getVisibility() == View.VISIBLE) {
            ivScore.setVisibility(View.INVISIBLE);
        }
        ivCloseDeck.setVisibility(View.VISIBLE);
        tvCardCounter.setVisibility(View.VISIBLE);
        Logger.print("CARD COUNTER >>> 15");
        tvCardCounter.setText("52");
        // }
        // } catch (JSONException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
    }

    private void AnimateGinText(final int si) {
        // TODO Auto-generated method stub
        if (si == BottomSeatIndex) {
            ivGinUserThree.setVisibility(View.VISIBLE);
        } else if (si == LeftSeatIndex) {
            ivGinUserOne.setVisibility(View.VISIBLE);
        } else if (si == RightSeatIndex) {
            ivGinUserTwo.setVisibility(View.VISIBLE);
        }
        int[] userOneSkipXY = new int[2];
        int[] userTwoSkipXY = new int[2];
        int[] userThreeSkipXY = new int[2];
        //int[] textViewCenterXY = new int[2];
        ivGinUserOne.getLocationOnScreen(userOneSkipXY);
        ivGinUserTwo.getLocationOnScreen(userTwoSkipXY);
        ivGinUserThree.getLocationOnScreen(userThreeSkipXY);
        // NotificationText.getLocationOnScreen(textViewCenterXY);
        int lx = userOneSkipXY[0];
        int ly = userOneSkipXY[1];
        int rx = userTwoSkipXY[0];
        int ry = userTwoSkipXY[1];
        int bx = userThreeSkipXY[0];
        int by = userThreeSkipXY[1];
        // int cenX = textViewCenterXY[0];
        // int cenY = textViewCenterXY[1];
        int cenX = Screen_Width / 2;
        int cenY = Screen_Hight / 2;
        AnimationSet animSet = new AnimationSet(true);
        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 10.0f, 1.0f, 10.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnim.setDuration(1000);
        scaleAnim.setFillAfter(false);
        TranslateAnimation tAnimationLeft = new TranslateAnimation(0,
                cenX - lx, 0, cenY - ly);
        tAnimationLeft.setDuration(1000);
        tAnimationLeft.setFillAfter(false);
        TranslateAnimation tAnimationBottom = new TranslateAnimation(0, cenX
                - bx, 0, cenY - by);
        tAnimationBottom.setDuration(1000);
        tAnimationBottom.setFillAfter(false);
        TranslateAnimation tAnimationRight = new TranslateAnimation(0, cenX
                - rx, 0, cenY - ry);
        tAnimationRight.setDuration(1000);
        tAnimationRight.setFillAfter(false);
        // Logger.print("COOOOOOOOOOOOOOOOORRRRRRRRRRSSSSSS => " + cenX + " "
        // + cenY + " " + bx + " " + by);
        if (si == BottomSeatIndex) {
            // Logger.print("SKIIIIIIIPPPPPPPPPP => 4");
            animSet.addAnimation(scaleAnim);
            animSet.addAnimation(tAnimationBottom);
            ivGinUserThree.startAnimation(animSet);
        } else if (si == LeftSeatIndex) {
            // Logger.print("SKIIIIIIIPPPPPPPPPP => 5");
            animSet.addAnimation(scaleAnim);
            animSet.addAnimation(tAnimationLeft);
            ivGinUserOne.startAnimation(animSet);
        } else if (si == RightSeatIndex) {
            // Logger.print("SKIIIIIIIPPPPPPPPPP => 6");
            animSet.addAnimation(scaleAnim);
            animSet.addAnimation(tAnimationRight);
            ivGinUserTwo.startAnimation(animSet);
        }
        animSet.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                Music_Manager.play_Gin();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                if (si == BottomSeatIndex) {
                    ivGinUserThree.setVisibility(View.INVISIBLE);
                } else if (si == LeftSeatIndex) {
                    ivGinUserOne.setVisibility(View.INVISIBLE);
                } else if (si == RightSeatIndex) {
                    ivGinUserTwo.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void AnimateBigGinText(final int si) {
        // TODO Auto-generated method stub
        if (si == BottomSeatIndex) {
            ivGinUserThree2.setVisibility(View.VISIBLE);
        } else if (si == LeftSeatIndex) {
            ivGinUserOne2.setVisibility(View.VISIBLE);
        } else if (si == RightSeatIndex) {
            ivGinUserTwo2.setVisibility(View.VISIBLE);
        }
        int[] userOneSkipXY = new int[2];
        int[] userTwoSkipXY = new int[2];
        int[] userThreeSkipXY = new int[2];
        //int[] textViewCenterXY = new int[2];
        ivGinUserOne2.getLocationOnScreen(userOneSkipXY);
        ivGinUserTwo2.getLocationOnScreen(userTwoSkipXY);
        ivGinUserThree2.getLocationOnScreen(userThreeSkipXY);
        // NotificationText.getLocationOnScreen(textViewCenterXY);
        int lx = userOneSkipXY[0];
        int ly = userOneSkipXY[1];
        int rx = userTwoSkipXY[0];
        int ry = userTwoSkipXY[1];
        int bx = userThreeSkipXY[0];
        int by = userThreeSkipXY[1];
        // int cenX = textViewCenterXY[0];
        // int cenY = textViewCenterXY[1];
        int cenX = Screen_Width / 2;
        int cenY = Screen_Hight / 2;
        AnimationSet animSet = new AnimationSet(true);
        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 10.0f, 1.0f, 10.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnim.setDuration(1000);
        scaleAnim.setFillAfter(false);
        TranslateAnimation tAnimationLeft = new TranslateAnimation(0,
                cenX - lx, 0, cenY - ly);
        tAnimationLeft.setDuration(1000);
        tAnimationLeft.setFillAfter(false);
        TranslateAnimation tAnimationBottom = new TranslateAnimation(0, cenX
                - bx, 0, cenY - by);
        tAnimationBottom.setDuration(1000);
        tAnimationBottom.setFillAfter(false);
        TranslateAnimation tAnimationRight = new TranslateAnimation(0, cenX
                - rx, 0, cenY - ry);
        tAnimationRight.setDuration(1000);
        tAnimationRight.setFillAfter(false);
        // Logger.print("COOOOOOOOOOOOOOOOORRRRRRRRRRSSSSSS => " + cenX + " "
        // + cenY + " " + bx + " " + by);
        if (si == BottomSeatIndex) {
            // Logger.print("SKIIIIIIIPPPPPPPPPP => 4");
            animSet.addAnimation(scaleAnim);
            animSet.addAnimation(tAnimationBottom);
            ivGinUserThree2.startAnimation(animSet);
        } else if (si == LeftSeatIndex) {
            // Logger.print("SKIIIIIIIPPPPPPPPPP => 5");
            animSet.addAnimation(scaleAnim);
            animSet.addAnimation(tAnimationLeft);
            ivGinUserOne2.startAnimation(animSet);
        } else if (si == RightSeatIndex) {
            // Logger.print("SKIIIIIIIPPPPPPPPPP => 6");
            animSet.addAnimation(scaleAnim);
            animSet.addAnimation(tAnimationRight);
            ivGinUserTwo2.startAnimation(animSet);
        }
        animSet.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                Music_Manager.play_Gin();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                if (si == BottomSeatIndex) {
                    ivGinUserThree2.setVisibility(View.INVISIBLE);
                } else if (si == LeftSeatIndex) {
                    ivGinUserOne2.setVisibility(View.INVISIBLE);
                } else if (si == RightSeatIndex) {
                    ivGinUserTwo2.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void AnimateKnockText(final int si) {
        // TODO Auto-generated method stub
        if (si == BottomSeatIndex) {
            ivKnockUserThree.setVisibility(View.VISIBLE);
        } else if (si == LeftSeatIndex) {
            ivKnockUserOne.setVisibility(View.VISIBLE);
        } else if (si == RightSeatIndex) {
            ivKnockUserTwo.setVisibility(View.VISIBLE);
        }
        int[] userOneSkipXY = new int[2];
        int[] userTwoSkipXY = new int[2];
        int[] userThreeSkipXY = new int[2];
        // int[] textViewCenterXY = new int[2];
        ivKnockUserOne.getLocationOnScreen(userOneSkipXY);
        ivKnockUserTwo.getLocationOnScreen(userTwoSkipXY);
        ivKnockUserThree.getLocationOnScreen(userThreeSkipXY);
        // NotificationText.getLocationOnScreen(textViewCenterXY);
        int lx = userOneSkipXY[0];
        int ly = userOneSkipXY[1];
        int rx = userTwoSkipXY[0];
        int ry = userTwoSkipXY[1];
        int bx = userThreeSkipXY[0];
        int by = userThreeSkipXY[1];
        // int cenX = textViewCenterXY[0];
        // int cenY = textViewCenterXY[1];
        int cenX = Screen_Width / 2;
        int cenY = Screen_Hight / 2;
        AnimationSet animSet = new AnimationSet(true);
        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 14.0f, 1.0f, 14.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnim.setDuration(1500);
        scaleAnim.setFillAfter(false);
        TranslateAnimation tAnimationLeft = new TranslateAnimation(0,
                cenX - lx, 0, cenY - ly);
        tAnimationLeft.setDuration(1500);
        tAnimationLeft.setFillAfter(false);
        TranslateAnimation tAnimationBottom = new TranslateAnimation(0, cenX
                - bx, 0, cenY - by);
        tAnimationBottom.setDuration(1500);
        tAnimationBottom.setFillAfter(false);
        TranslateAnimation tAnimationRight = new TranslateAnimation(0, cenX
                - rx, 0, cenY - ry);
        tAnimationRight.setDuration(1500);
        tAnimationRight.setFillAfter(false);
        // Logger.print("COOOOOOOOOOOOOOOOORRRRRRRRRRSSSSSS => " + cenX + " "
        // + cenY + " " + bx + " " + by);
        if (si == BottomSeatIndex) {
            // Logger.print("SKIIIIIIIPPPPPPPPPP => 4");
            animSet.addAnimation(scaleAnim);
            animSet.addAnimation(tAnimationBottom);
            ivKnockUserThree.startAnimation(animSet);
        } else if (si == LeftSeatIndex) {
            // Logger.print("SKIIIIIIIPPPPPPPPPP => 5");
            animSet.addAnimation(scaleAnim);
            animSet.addAnimation(tAnimationLeft);
            ivKnockUserOne.startAnimation(animSet);
        } else if (si == RightSeatIndex) {
            // Logger.print("SKIIIIIIIPPPPPPPPPP => 6");
            animSet.addAnimation(scaleAnim);
            animSet.addAnimation(tAnimationRight);
            ivKnockUserTwo.startAnimation(animSet);
        }
        animSet.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                Music_Manager.play_Knock();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                if (si == BottomSeatIndex) {
                    ivKnockUserThree.setVisibility(View.INVISIBLE);
                    // Logger.print("Animend =>  ivKnockUserThree");
                } else if (si == LeftSeatIndex) {
                    ivKnockUserOne.setVisibility(View.INVISIBLE);
                    // Logger.print("Animend => ivKnockUserOne");
                } else if (si == RightSeatIndex) {
                    ivKnockUserTwo.setVisibility(View.INVISIBLE);
                    // Logger.print("Animend => ivKnockUserTwo");
                }
            }
        });
    }

    private void AnimateDrawText(final int si) {
        // TODO Auto-generated method stub
        if (si == BottomSeatIndex) {
            ivDrawUserThree.setVisibility(View.VISIBLE);
        } else if (si == LeftSeatIndex) {
            ivDrawUserOne.setVisibility(View.VISIBLE);
        } else if (si == RightSeatIndex) {
            ivDrawUserTwo.setVisibility(View.VISIBLE);
        }
        int[] userOneSkipXY = new int[2];
        int[] userTwoSkipXY = new int[2];
        int[] userThreeSkipXY = new int[2];
        // int[] textViewCenterXY = new int[2];
        ivDrawUserOne.getLocationOnScreen(userOneSkipXY);
        ivDrawUserTwo.getLocationOnScreen(userTwoSkipXY);
        ivDrawUserThree.getLocationOnScreen(userThreeSkipXY);
        // NotificationText.getLocationOnScreen(textViewCenterXY);
        int lx = userOneSkipXY[0];
        int ly = userOneSkipXY[1];
        int rx = userTwoSkipXY[0];
        int ry = userTwoSkipXY[1];
        int bx = userThreeSkipXY[0];
        int by = userThreeSkipXY[1];
        // int cenX = textViewCenterXY[0];
        // int cenY = textViewCenterXY[1];
        int cenX = Screen_Width / 2;
        int cenY = Screen_Hight / 2;
        AnimationSet animSet = new AnimationSet(true);
        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 14.0f, 1.0f, 14.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnim.setDuration(1000);
        scaleAnim.setFillAfter(false);
        TranslateAnimation tAnimationLeft = new TranslateAnimation(0,
                cenX - lx, 0, cenY - ly);
        tAnimationLeft.setDuration(1000);
        tAnimationLeft.setFillAfter(false);
        TranslateAnimation tAnimationBottom = new TranslateAnimation(0, cenX
                - bx, 0, cenY - by);
        tAnimationBottom.setDuration(1000);
        tAnimationBottom.setFillAfter(false);
        TranslateAnimation tAnimationRight = new TranslateAnimation(0, cenX
                - rx, 0, cenY - ry);
        tAnimationRight.setDuration(1000);
        tAnimationRight.setFillAfter(false);
        // Logger.print("COOOOOOOOOOOOOOOOORRRRRRRRRRSSSSSS => " + cenX + " "
        // + cenY + " " + bx + " " + by);
        if (si == BottomSeatIndex) {
            // Logger.print("SKIIIIIIIPPPPPPPPPP => 4");
            animSet.addAnimation(scaleAnim);
            animSet.addAnimation(tAnimationBottom);
            ivDrawUserThree.startAnimation(animSet);
        } else if (si == LeftSeatIndex) {
            // Logger.print("SKIIIIIIIPPPPPPPPPP => 5");
            animSet.addAnimation(scaleAnim);
            animSet.addAnimation(tAnimationLeft);
            ivDrawUserOne.startAnimation(animSet);
        } else if (si == RightSeatIndex) {
            // Logger.print("SKIIIIIIIPPPPPPPPPP => 6");
            animSet.addAnimation(scaleAnim);
            animSet.addAnimation(tAnimationRight);
            ivDrawUserTwo.startAnimation(animSet);
        }
        animSet.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                if (si == BottomSeatIndex) {
                    ivDrawUserThree.setVisibility(View.INVISIBLE);
                } else if (si == LeftSeatIndex) {
                    ivDrawUserOne.setVisibility(View.INVISIBLE);
                } else if (si == RightSeatIndex) {
                    ivDrawUserTwo.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void ResetForNewRound() {
        // TODO Auto-generated method stub
        isCardPicked = false;
        isUserPicked = false;
        // System.gc();
        Logger.print("Resetting Game =>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        if (ivCloseCard.getAnimation() != null) {
            ivCloseCard.clearAnimation();
        }
        ivCloseCard.setVisibility(View.INVISIBLE);
        if (ivScore.getVisibility() == View.VISIBLE) {
            ivScore.setVisibility(View.INVISIBLE);
        }
        isCardThrown = false;
        isUserTurn = false;
        cardsize = 0;
        // isWinnerDeclared = false;
        // for initial animation
        isAllowtoCardClick = false;
        isCardTouchable = false;
        // deadbox = false;
        ivUserOneCard.setVisibility(View.INVISIBLE);
        ivUserTwoCard.setVisibility(View.INVISIBLE);
        for (int i = 0; i < UserTurnStarted.length; i++) {
            UserTurnStarted[i] = false;
            // DealCardToSeat[i] = false;
            UserWinner[i] = false;
        }
        frmMesage.bottomMargin = getheight(0);
        btnFaceUp.setVisibility(View.GONE);
        btnDrawNew.setVisibility(View.GONE);
        btnDiscard.setVisibility(View.GONE);
        isgin = false;
        isknock = false;
        // leftIndex = 0;
        // rightIndex = 0;
        // selfIndex = 0;
        if (ivCloseCard.getAnimation() != null) {
            ivCloseCard.clearAnimation();
        }
        ivCloseCard.setVisibility(View.INVISIBLE);
        if (ivCloseDeck.getVisibility() != View.VISIBLE) {
            ivCloseDeck.setVisibility(View.VISIBLE);
            tvCardCounter.setVisibility(View.VISIBLE);
        }
        if (btnSkip.getVisibility() == View.VISIBLE) {
            if (btnSkip.getAnimation() != null) {
                btnSkip.clearAnimation();
            }
            btnSkip.setVisibility(View.GONE);
            isFirstTurn = false;
        }
        if (btnGin.getVisibility() == View.VISIBLE) {
            if (btnGin.getAnimation() != null) {
                btnGin.clearAnimation();
            }
            btnGin.setVisibility(View.GONE);
        }
        if (btnKnock.getVisibility() == View.VISIBLE) {
            if (btnKnock.getAnimation() != null) {
                btnKnock.clearAnimation();
            }
            btnKnock.setVisibility(View.GONE);
        }
        if (ivScore_knock.getVisibility() == View.VISIBLE) {
            if (ivScore_knock.getAnimation() != null) {
                ivScore_knock.clearAnimation();
            }
            ivScore_knock.setVisibility(View.INVISIBLE);
        }
        if (ivHandCloseDeck.getAnimation() != null) {
            ivHandCloseDeck.clearAnimation();
        }
        ivHandCloseDeck.setVisibility(View.INVISIBLE);
        if (ivHandOpenCard.getAnimation() != null) {
            ivHandOpenCard.clearAnimation();
        }
        ivHandOpenCard.setVisibility(View.INVISIBLE);
        LeftUserRing.setVisibility(View.INVISIBLE);
        RightUserRing.setVisibility(View.INVISIBLE);
        BottomUserRing.setVisibility(View.INVISIBLE);
        ivCardForDistribution.setVisibility(View.INVISIBLE);
        if (LeftUserRing.getAnimation() != null) {
            LeftUserRing.clearAnimation();
        }
        if (RightUserRing.getAnimation() != null) {
            RightUserRing.clearAnimation();
        }
        if (BottomUserRing.getAnimation() != null) {
            BottomUserRing.clearAnimation();
        }
    }

    private void clearSomeData() {
        // TODO Auto-generated method stub
        Logger.print("Cleared......");
        DealingAnimationCompleted = false;
        //NotificationText.setVisibility(View.INVISIBLE);
        PileCardCounter = 52;
        ivCloseDeck.setVisibility(View.VISIBLE);
        tvCardCounter.setVisibility(View.VISIBLE);
        Logger.print("CARD COUNTER >>> 16");
        tvCardCounter.setText("52");
        ivCardOnDeck.setVisibility(View.INVISIBLE);
        if (card_BottomUser.size() > 0)
            card_BottomUser.clear();
        if (card_LeftUser.size() > 0)
            card_LeftUser.clear();
        if (card_RightUser.size() > 0)
            card_RightUser.clear();
        if (openBunchCard.size() > 0)
            openBunchCard.clear();
        if (closeBunchCard.size() > 0)
            closeBunchCard.clear();
        for (ImageView ivMyCard : ivMyCards) {
            ivMyCard.setVisibility(View.INVISIBLE);
        }
        groupBgLinear.removeAllViews();
        if (frmRightUserOpenCard.getChildCount() > 0)
            frmRightUserOpenCard.removeAllViews();
        if (frmLeftUserOpenCard.getChildCount() > 0)
            frmLeftUserOpenCard.removeAllViews();
        if (ivWinninngCircleOne.getAnimation() != null) {
            ivWinninngCircleOne.clearAnimation();
        }
        if (ivWinninngCircleTwo.getAnimation() != null) {
            ivWinninngCircleTwo.clearAnimation();
        }
        if (ivWinninngCircleThree.getAnimation() != null) {
            ivWinninngCircleThree.clearAnimation();
        }
        ivWinninngCircleOne.setVisibility(View.INVISIBLE);
        ivWinninngCircleTwo.setVisibility(View.INVISIBLE);
        ivWinninngCircleThree.setVisibility(View.INVISIBLE);
        if (ivWinninngRoundOne.getAnimation() != null) {
            ivWinninngRoundOne.clearAnimation();
        }
        if (ivWinninngRoundTwo.getAnimation() != null) {
            ivWinninngRoundTwo.clearAnimation();
        }
        if (ivWinninngRoundThree.getAnimation() != null) {
            ivWinninngRoundThree.clearAnimation();
        }
        ivWinninngRoundOne.setVisibility(View.INVISIBLE);
        ivWinninngRoundTwo.setVisibility(View.INVISIBLE);
        ivWinninngRoundThree.setVisibility(View.INVISIBLE);
        tvdeadwoodScore.setText("0");
        tvdeadwoodScore2.setText("/" + required_knock);
        // tvUserScore.setVisibility(View.INVISIBLE);
    }

    private void WinnerDeclaredProcess(String data, String event) {
        // TODO Auto-generated method stub
        // Logger.print("Round Finished >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        if (ivKnockUserThree.getVisibility() == View.VISIBLE) {
            ivKnockUserThree.setVisibility(View.INVISIBLE);
        } else if (ivKnockUserOne.getVisibility() == View.VISIBLE) {
            ivKnockUserOne.setVisibility(View.INVISIBLE);
        } else if (ivKnockUserTwo.getVisibility() == View.VISIBLE) {
            ivKnockUserTwo.setVisibility(View.INVISIBLE);
        }
        if (ivScore.getVisibility() == View.VISIBLE) {
            ivScore.setVisibility(View.INVISIBLE);
        }
        UserWinner = new boolean[3];
        BottomDeadWood = 0;
        JSONObject jObject;
        try {
            jObject = new JSONObject(data);
            JSONObject data11 = jObject.getJSONObject(Parameters.data);
            JSONArray array = data11.getJSONArray(Parameters.PlayersInfo);
            if (array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    if (array.getJSONObject(i).has("w") && event.equals("B")) {
                        UserWinner[array.getJSONObject(i).getInt(
                                Parameters.SeatIndex)] = true;
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private void DeadwoodCount() {
        // TODO Auto-generated method stub
        if (isUserTurn && !isCardThrown && hasTakenCard) {
            if (BottomDeadWood == 0) {
                isgin = true;
                isknock = false;
                if (btnKnock.getAnimation() != null) {
                    btnKnock.clearAnimation();
                }
                btnKnock.setVisibility(View.GONE);
                if (ivScore_knock.getVisibility() == View.VISIBLE) {
                    if (ivScore_knock.getAnimation() != null) {
                        ivScore_knock.clearAnimation();
                    }
                    ivScore_knock.setVisibility(View.INVISIBLE);
                }
                if (hasTakenCard) {
                    btnGin.clearAnimation();
                    int c = 0;

                    for (int i = 0; i < card_BottomUser.size(); i++) {
                        if (!card_BottomUser.get(i).getCardInPair()) {
                            c++;
                        }
                    }

                    if (c == 0) {
                        btnGin.setBackgroundResource(R.drawable.bigginbtn);
                    } else {
                        btnGin.setBackgroundResource(R.drawable.ginbtn);
                    }

                    btnGin.setVisibility(View.VISIBLE);
                }
                if (btnGin.getAnimation() == null) {
                    btnGin.startAnimation(heartBeatAnimation);
                }
            } else if ((c.gameType != 1) && BottomDeadWood <= required_knock && User_turn_cnt > 0 && hasTakenCard) {
                // Logger.print("DeadWoodCount open condtion");
                isknock = true;
                isgin = false;
                if (btnGin.getAnimation() != null) {
                    btnGin.clearAnimation();
                }
                btnGin.setVisibility(View.GONE);
                btnKnock.setVisibility(View.VISIBLE);
                if (btnKnock.getAnimation() == null) {
                    btnKnock.startAnimation(heartBeatAnimation);
                }
//                btnKnock.setText("Knock for " + BottomDeadWood);
                if (isTutorial) {
                    btnKnock.setClickable(false);
                }
                ivScore_knock.setVisibility(View.INVISIBLE);
            } else {
                if (btnKnock.getAnimation() != null) {
                    btnKnock.clearAnimation();
                }
                btnKnock.setVisibility(View.GONE);
                if (ivScore_knock.getVisibility() == View.VISIBLE) {
                    if (ivScore_knock.getAnimation() != null) {
                        ivScore_knock.clearAnimation();
                    }
                    ivScore_knock.setVisibility(View.INVISIBLE);
                }
                if (btnGin.getAnimation() != null) {
                    btnGin.clearAnimation();
                }
                btnGin.setVisibility(View.GONE);
                isknock = false;
                isgin = false;
            }
            // Logger.print("DDDDDDD"+isgin+ "::::"+isknock);
        }
    }

    private void collectBootValueAnimation(boolean[] dealCardToSeat) {
        // TODO Auto-generated method stub
        c.gameCount++;
        /*NotificationText.setText("");
        NotificationText.setVisibility(View.INVISIBLE);*/
        Logger.print(">>>>>>>>>>>>>>>Boot Value Animation >>> Chips " + c.Chips);
        c.Chips = PreferenceManager.getChips();
        Logger.print(">>>>>>>>>>>>>>>Boot Value Animation 1111111111111");
        int cenY = Screen_Hight / 2;
        int cenX = Screen_Width / 2;
        int[] userOneBootXY, userTwoBootXY, userThreeBootXY;
        if (GameisOn()) {
            Music_Manager.play_Chaal();
            TranslateAnimation bottomBoot = null, leftAnim = null, rightAnim = null;
            try {
                if (dealCardToSeat[BottomSeatIndex]) {
                    c.Chips = c.Chips - BOOT_VALUE;
                    c.lostChip = c.lostChip + BOOT_VALUE;
                    Logger.print(">>>> LOST CHIP 3333 >>> " + c.lostChip);
                    PreferenceManager.setChips(c.Chips);
                    sqLiteManager.updateChip(PreferenceManager.get_id(), BOOT_VALUE, false);
                    ivUserThreeBootChip.setVisibility(View.VISIBLE);
                    userThreeBootXY = new int[2];
                    ivUserThreeBootChip.getLocationInWindow(userThreeBootXY);
                    int x = userThreeBootXY[0];
                    int y = userThreeBootXY[1];
                    bottomBoot = new TranslateAnimation(0, cenX - x, 0, cenY - y);
                    bottomBoot.setDuration(500);
                    bottomBoot.setFillAfter(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (dealCardToSeat[LeftSeatIndex]) {
                    sqLiteManager.updateChip(playingUsers.get(1).get(c.ID), BOOT_VALUE, false);
                    ivUserOneBootChip.setVisibility(View.VISIBLE);
                    userOneBootXY = new int[2];
                    ivUserOneBootChip.getLocationInWindow(userOneBootXY);
                    int x = userOneBootXY[0];
                    int y = userOneBootXY[1];
                    leftAnim = new TranslateAnimation(0, cenX - x, 0, cenY - y);
                    leftAnim.setDuration(500);
                    leftAnim.setFillAfter(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (dealCardToSeat[RightSeatIndex]) {
                    sqLiteManager.updateChip(playingUsers.get(0).get(c.ID), BOOT_VALUE, false);
                    ivUserTwoBootChip.setVisibility(View.VISIBLE);
                    userTwoBootXY = new int[2];
                    ivUserTwoBootChip.getLocationInWindow(userTwoBootXY);
                    int x = userTwoBootXY[0];
                    int y = userTwoBootXY[1];
                    rightAnim = new TranslateAnimation(0, cenX - x, 0, cenY - y);
                    rightAnim.setDuration(500);
                    rightAnim.setFillAfter(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (leftAnim != null)
                ivUserOneBootChip.startAnimation(leftAnim);
            if (rightAnim != null)
                ivUserTwoBootChip.startAnimation(rightAnim);
            if (bottomBoot != null)
                ivUserThreeBootChip.startAnimation(bottomBoot);
            int[] locXY = new int[2];
            ivBootAnim[0].getLocationOnScreen(locXY);
            final int x1 = locXY[0];
            final int y1 = locXY[1];
            int[] bootAmtLocXY = new int[2];
            tvTotalBetValue.getLocationOnScreen(bootAmtLocXY);
            final int x2 = bootAmtLocXY[0];
            final int y2 = bootAmtLocXY[1];
            final TranslateAnimation[] tAnimToValue = new TranslateAnimation[7];
            int startOffset = 0;
            for (int i = 0; i < tAnimToValue.length; i++) {
                tAnimToValue[i] = new TranslateAnimation(0, x2 - x1, 0, y2 - y1);
                tAnimToValue[i].setDuration(500);
                tAnimToValue[i].setStartOffset(startOffset);
                startOffset += 100;
            }
            //  if(GameisOn()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ivUserOneBootChip.setVisibility(View.INVISIBLE);
                    ivUserTwoBootChip.setVisibility(View.INVISIBLE);
                    ivUserThreeBootChip.setVisibility(View.INVISIBLE);
                    AnimBootValue(tAnimToValue);
                }
            }, 300);
        } else {
            c.Chips = c.Chips - BOOT_VALUE;
            c.lostChip = c.lostChip + c.lostChip;
            Logger.print(">>>> LOST CHIP 4444 >>> " + c.lostChip);
            PreferenceManager.setChips(c.Chips);
            sqLiteManager.updateChip(PreferenceManager.get_id(), BOOT_VALUE, false);
            tvTotalBetValue.setVisibility(View.VISIBLE);
            tvTotalBetValue.setText("" + c.getNumberFormatedValue(BOOT_VALUE * numberOfPlayers));
        }
    }

    private void AnimBootValue(TranslateAnimation[] tAnimToValue) {
        // TODO Auto-generated method stub
        for (int i = 0; i < tAnimToValue.length; i++) {
            final int k = i;
            ivBootAnim[i].startAnimation(tAnimToValue[i]);
            tAnimToValue[i].setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // TODO Auto-generated method stub
                    ivBootAnim[k].setVisibility(View.INVISIBLE);
                    if (k == 0) {
                        IncrementCounterChip((BOOT_VALUE * numberOfPlayers));
                    }
                }
            });
        }
        // tvTotalBetValue.setText(betval);
    }

    private void FOR_LEAVE() {
        // TODO Auto-generated method stub
        // AlertDialog ConfirmationDialog;
        timerPause();
        // TODO Auto-generated method stub
        LinearLayout.LayoutParams lin;
        if (ConfirmationDialog != null) {
            if (ConfirmationDialog.isShowing()) {
                ConfirmationDialog.dismiss();
            }
            ConfirmationDialog = null;
        }
        ConfirmationDialog = new Dialog(PlayScreen2.this,
                R.style.Theme_Transparent);
        ConfirmationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ConfirmationDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        ConfirmationDialog.setContentView(R.layout.dialog);
        TextView title = ConfirmationDialog
                .findViewById(R.id.tvTitle);
        title.setPadding(getwidth(20), getheight(20), getwidth(20),
                getheight(20));
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
        title.setTypeface(tf);
        title.setText("Leave Game");
        lin = (LinearLayout.LayoutParams) ConfirmationDialog.findViewById(
                R.id.line).getLayoutParams();
        lin.height = getheight(4);
        lin.width = getwidth(600);
        TextView message = ConfirmationDialog
                .findViewById(R.id.tvMessage);
        message.setTypeface(tf);
        message.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));
        Button btnOne = ConfirmationDialog.findViewById(R.id.button1);
        btnOne.setTypeface(tf);
        btnOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnOne.setText("Leave");
        Button btnTwo = ConfirmationDialog.findViewById(R.id.button2);
        btnTwo.setTypeface(tf);
        btnTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnTwo.setText("Cancel");
        lin = (LinearLayout.LayoutParams) ConfirmationDialog.findViewById(
                R.id.button1).getLayoutParams();
        lin.height = getheight(60);
        lin.width = getwidth(120);
        lin = (LinearLayout.LayoutParams) ConfirmationDialog.findViewById(
                R.id.tvMessage).getLayoutParams();
        lin.topMargin = getheight(30);
        lin = (LinearLayout.LayoutParams) ConfirmationDialog.findViewById(
                R.id.llButtons).getLayoutParams();
        lin.topMargin = getheight(30);
        lin = (LinearLayout.LayoutParams) ConfirmationDialog.findViewById(
                R.id.button2).getLayoutParams();
        lin.height = getheight(60);
        lin.width = getwidth(120);
        message.setText("If you leave this game you will lost your bet chips.");
        btnOne.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Music_Manager.buttonclick();
                PreferenceManager.setIsGameResume(false);
                ConfirmationDialog.dismiss();
                c.exitFromPlayScreen = true;
                c.isPlayingScreenOpen = false;
                BonustimerPause();
                if (Dashboard.handler != null) {
                    Message msg = new Message();
                    msg.what = ResponseCodes.Finish_Playscreen;
                    Dashboard.handler.sendMessage(msg);
                }
                finish();
                overridePendingTransition(R.anim.none, R.anim.to_lefttoright);
            }
        });
        btnTwo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
                // TODO Auto-generated method stub
                timerResume();
                ConfirmationDialog.dismiss();
            }
        });
        if (!isFinishing())
            ConfirmationDialog.show();
    }

    private void PickFromPileProcess(String data) {
        // TODO Auto-generated method stub
        //LoaderFinish(0);
        isCardPicked = true;
        isUserPicked = true;
        JSONObject jObject;
        try {
            jObject = new JSONObject(data);
            int seatIndex = jObject.getJSONObject(Parameters.data).getInt(
                    Parameters.SeatIndex);
            PileCardCounter--;
            Logger.print("CARD COUNTER >>> 17");
            tvCardCounter.setText("" + PileCardCounter);
            if (PileCardCounter == 0) {
                ivCloseDeck.setVisibility(View.INVISIBLE);
                Logger.print("CARD COUNTER >>> 18");
                tvCardCounter.setText("");
                tvCardCounter.setVisibility(View.INVISIBLE);
            }
            String card = jObject.getJSONObject(Parameters.data).getString(
                    Parameters.Card);
            //Logger.print("closeBunchCards PickFromPileProcess B=> " + closeBunchCard.toString());
            closeBunchCard.remove(closeBunchCard.size() - 1);
            //Logger.print("closeBunchCards PickFromPileProcess A=> " + closeBunchCard.toString());
            Item_Card iCard = getItemCard(card);
            if (!isUserTurn(seatIndex)) {
                // PileCardCounter--;
                Logger.print("Pick From Pile");
                if (seatIndex == RightSeatIndex) {
                    card_RightUser.add(iCard);
                    Logger.print("Right User Cards => "
                            + card_RightUser.toString() + " " + card);
                    card_RightUser = new ArrayList<>(Sort(card_RightUser, RightSeatIndex));
                    redrawRobotCard(RightSeatIndex);
                    // sortedArray = Sort(card_RightUser);
                    // Logger.print("QQQQQQQQ => Right => " +
                    // sortedArray.toString());
                    if (GameisOn())
                        animateCloseBunchCardToUSer(RightSeatIndex, card);
                } else if (seatIndex == LeftSeatIndex) {
                    card_LeftUser.add(iCard);
                    card_LeftUser = new ArrayList<>(Sort(card_LeftUser, LeftSeatIndex));
                    // sortedArray = Sort(card_LeftUser);
                    // Logger.print("QQQQQQQQ => Left => " +
                    // sortedArray.toString());
                    redrawRobotCard(LeftSeatIndex);
                    Logger.print("Left User Cards => "
                            + card_LeftUser.toString() + " " + card);
                    if (GameisOn())
                        animateCloseBunchCardToUSer(LeftSeatIndex, card);
                }
                //{"en":"KNK","data":{"si":0,"c":"l-6"}}
                if (seatIndex == RightSeatIndex) {
                    messageHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (RightDeadWood == 0) {
                                JSONObject jObject = new JSONObject();
                                try {
                                    jObject.put(Parameters.SeatIndex, RightSeatIndex);
                                    JSONObject jObject1 = new JSONObject();
                                    jObject1.put(Parameters.data, jObject);
                                    GinResponseProcess(jObject1.toString(), card_RightUser);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if ((c.gameType != 1)
                                    && RightDeadWood <= required_knock &&
                                    ((BottomDeadWood > RightDeadWood && (BottomDeadWood - RightDeadWood <= required_knock || BottomDeadWood - RightDeadWood > 20)) ||
                                            closeBunchCard.size() <= 5)) {
                                Logger.print("DeadWood Counter =======================Right PFP " + RightDeadWood + " " + BottomDeadWood);
                                String cardTOThrow = getHighDeadWoodCard(card_RightUser);
                                JSONObject jObject = new JSONObject();
                                try {
                                    jObject.put(Parameters.SeatIndex, RightSeatIndex);
                                    jObject.put(Parameters.Card, cardTOThrow);
                                    JSONObject jObject1 = new JSONObject();
                                    jObject1.put(Parameters.data, jObject);
                                    KnockResponseProcess(jObject1.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                chooseCardToThrow(card_RightUser);
                            }
                        }
                    }, 3000);
                } else if (seatIndex == LeftSeatIndex) {
                    messageHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (LeftDeadWood == 0) {
                                JSONObject jObject = new JSONObject();
                                try {
                                    jObject.put(Parameters.SeatIndex, LeftSeatIndex);
                                    JSONObject jObject1 = new JSONObject();
                                    jObject1.put(Parameters.data, jObject);
                                    GinResponseProcess(jObject1.toString(), card_LeftUser);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if ((c.gameType != 1) &&
                                    LeftDeadWood <= required_knock &&
                                    ((BottomDeadWood > LeftDeadWood && (BottomDeadWood - LeftDeadWood <= required_knock || BottomDeadWood - LeftDeadWood > 20)) ||
                                            closeBunchCard.size() <= 5)) {
                                Logger.print("DeadWood Counter =======================Left PFP:::: " + LeftDeadWood + " " + BottomDeadWood);
                                String cardTOThrow = getHighDeadWoodCard(card_LeftUser);
                                JSONObject jObject = new JSONObject();
                                try {
                                    jObject.put(Parameters.SeatIndex, LeftSeatIndex);
                                    jObject.put(Parameters.Card, cardTOThrow);
                                    JSONObject jObject1 = new JSONObject();
                                    jObject1.put(Parameters.data, jObject);
                                    KnockResponseProcess(jObject1.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                chooseCardToThrow(card_LeftUser);
                            }
                        }
                    }, 3000);
                }
            } else {
                Logger.print("Receive pick from pile resp");
                hasTakenCard = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setClickable(ResponseCodes.PickFromPileResp);
                    }
                }, 800);
                int cardCount = card_BottomUser.size();
                if (cardCount >= 0) {
                    Item_Card lastCard = card_BottomUser.get(cardCount - 1);
                    if (!lastCard.getIsValidGroup()) {
                        iCard.setGroupNumber(lastCard.getGroupNumber());
                    } else {
                        GroupnumberCounter++;
                        iCard.setGroupNumber(GroupnumberCounter);
                    }
                }
                card_BottomUser.add(iCard);
                //if (GameisOn()) {
                animateCloseBunchCardToUSer(BottomSeatIndex, card);
                //  Logger.print(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Game is ON");
                // } else {
                //    DeadwoodCount();
                // Logger.print(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Game is Not ON");
                // }
                /*
                 * if (NoCardInBunch && bunchManager.closeBunchCard.size() <= 0)
				 * {
				 *
				 * // PileCardCounter--;
				 *
				 * Logger.print("Pick From Pile"); String card =
				 * jObject.getJSONObject(Parameters.data).getString(Parameters.
				 * Card);
				 *
				 * Item_Card object = getItemCard(card);
				 * bunchManager.closeBunchCard.add(object);
				 * AnimateCloseBunchToUser(middle);
				 *
				 * }
				 */
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getHighDeadWoodCard(ArrayList<Item_Card> userCards) {
        Logger.print("user cards before knock =-------- --- --  " + userCards.toString());
        String thrownCard = null;
        int deadwood = 0;
        for (int i = 0; i < userCards.size(); i++) {
            if (!userCards.get(i).getCardInPair() && userCards.get(i).getCardValue() > deadwood) {
                deadwood = userCards.get(i).getCardValue();
                thrownCard = getCardString(userCards.get(i));
                Logger.print("MMMMMMMMMMMM Throw Card Before  =-------- --- --  " + thrownCard);
//                if(!PickCard.equalsIgnoreCase(" ")) {
//                    if (thrownCard.equalsIgnoreCase(PickCard)) {
//
//                        if (userCards.size() >= 2) {
//
//                            if (i == 0) {
//                                Logger.print("MMMMMMMMMMMM Throw Card After 111  =-------- --- --  " + thrownCard.toString());
//                                thrownCard = getCardString(userCards.get(i + 1));
//
//                            } else {
//                                Logger.print("MMMMMMMMMMMM Throw Card After 222  =-------- --- --  " + thrownCard.toString());
//                                thrownCard = getCardString(userCards.get(i - 1));
//
//                            }
//
//                        }
//                    }
//                }
            }
        }
        Logger.print("card to remove => " + thrownCard);
        return thrownCard;
    }

    private void PickFromThrownCardsProcess(String data) {
        // TODO Auto-generated method stub
        isCardPicked = true;
        isUserPicked = true;
        isFirstTurn = false;
        Logger.print("Pic from thrown card => " + data);
        JSONObject jObject;
        try {
            jObject = new JSONObject(data);
            int seatIndex = jObject.getJSONObject(Parameters.data).getInt(
                    Parameters.SeatIndex);
            String card = jObject.getJSONObject(Parameters.data).getString(
                    Parameters.Card);
            Item_Card iCard = getItemCard(card);
            openBunchCard.remove(openBunchCard.size() - 1);
            // Logger.print("AfterRemoving Open cArd " +
            // openBunchCard.toString()
            // + " " + card);
            if (!isUserTurn(seatIndex)) {
                if (seatIndex == RightSeatIndex) {
                    card_RightUser.add(iCard);
                    PickCard = getCardString(iCard);
                    Logger.print("Right User Cards => "
                            + card_RightUser.toString() + " " + card);
                    card_RightUser = new ArrayList<>(Sort(card_RightUser, RightSeatIndex));
                    redrawRobotCard(RightSeatIndex);
                } else if (seatIndex == LeftSeatIndex) {
                    card_LeftUser.add(iCard);
                    PickCard = getCardString(iCard);
                    card_LeftUser = new ArrayList<>(Sort(card_LeftUser, LeftSeatIndex));
                    redrawRobotCard(LeftSeatIndex);
                }
                if (GameisOn()) {
                    AnimateOpenBunchToUser(seatIndex, card);
                }
                if (seatIndex == RightSeatIndex) {
                    messageHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (RightDeadWood == 0) {
                                JSONObject jObject = new JSONObject();
                                try {
                                    jObject.put(Parameters.SeatIndex, RightSeatIndex);
                                    JSONObject jObject1 = new JSONObject();
                                    jObject1.put(Parameters.data, jObject);
                                    GinResponseProcess(jObject1.toString(), card_RightUser);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if ((c.gameType != 1) && RightDeadWood <= required_knock && ((BottomDeadWood > RightDeadWood && (BottomDeadWood - RightDeadWood <= required_knock || BottomDeadWood - RightDeadWood > 20)) || closeBunchCard.size() <= 5)) {
                                String cardTOThrow = getHighDeadWoodCard(card_RightUser);
                                JSONObject jObject = new JSONObject();
                                try {
                                    jObject.put(Parameters.SeatIndex, RightSeatIndex);
                                    jObject.put(Parameters.Card, cardTOThrow);
                                    JSONObject jObject1 = new JSONObject();
                                    jObject1.put(Parameters.data, jObject);
                                    KnockResponseProcess(jObject1.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                chooseCardToThrow(card_RightUser);
                            }
                        }
                    }, 3000);
                } else if (seatIndex == LeftSeatIndex) {
                    messageHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (LeftDeadWood == 0) {
                                JSONObject jObject = new JSONObject();
                                try {
                                    jObject.put(Parameters.SeatIndex, LeftSeatIndex);
                                    JSONObject jObject1 = new JSONObject();
                                    jObject1.put(Parameters.data, jObject);
                                    GinResponseProcess(jObject1.toString(), card_LeftUser);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if ((c.gameType != 1)
                                    && LeftDeadWood <= required_knock &&
                                    ((BottomDeadWood > LeftDeadWood && (BottomDeadWood - LeftDeadWood <= required_knock || BottomDeadWood - LeftDeadWood > 20)) ||
                                            closeBunchCard.size() <= 5)) {
                                String cardTOThrow = getHighDeadWoodCard(card_LeftUser);
                                JSONObject jObject = new JSONObject();
                                try {
                                    jObject.put(Parameters.SeatIndex, LeftSeatIndex);
                                    jObject.put(Parameters.Card, cardTOThrow);
                                    JSONObject jObject1 = new JSONObject();
                                    jObject1.put(Parameters.data, jObject);
                                    KnockResponseProcess(jObject1.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                chooseCardToThrow(card_LeftUser);
                            }
                        }
                    }, 3000);
                }
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setClickable(ResponseCodes.PickFromThrownCardsResp);
                    }
                }, 800);
                hasTakenCard = true;
                Item_Card lastCard = card_BottomUser.get(card_BottomUser.size() - 1);
                if (!lastCard.getIsValidGroup()) {
                    iCard.setGroupNumber(lastCard.getGroupNumber());
                } else {
                    GroupnumberCounter++;
                    iCard.setGroupNumber(GroupnumberCounter);
                }
                card_BottomUser.add(iCard);
                // Logger.print("PFTC Bottom User Cards => "
                // + card_BottomUser.toString() + " " + card);
                // if (GameisOn()) {
                AnimateOpenBunchToUser(BottomSeatIndex, card);
                //} else {
                //   DeadwoodCount();
                // }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chooseCardToThrow(ArrayList<Item_Card> userCards) {
        //{"en":"TCOD","data":{"c":"f-4","si":0}}
        ArrayList<Item_Card> usertemp = new ArrayList<>();
        usertemp.addAll(userCards);
        ArrayList<Item_Card> deadwoodCard = new ArrayList<>();
        ArrayList<Item_Card> deadwoodCard2 = new ArrayList<>();
        ArrayList<Item_Card> deadwoodCard3 = new ArrayList<>();
        ArrayList<Item_Card> paired = new ArrayList<>();
        ArrayList<Item_Card> sequence = new ArrayList<>();
        ArrayList<Item_Card> oneCardDiffrenceSeq = new ArrayList<>();
        String throwCard = null;
        //totalGameCounter = 11;
        Logger.print("------------++++++++++++++++++++++ " + (totalGameCounter % 2 == 0) + " " + (totalGameCounter <= 3) + " " + totalGameCounter);
        if (totalGameCounter <= 3) {
            if (!PickCard.equalsIgnoreCase(" ")) {
                usertemp.remove(getItemCard(PickCard));
            }
            int size = usertemp.size();
            int random = new Random().nextInt(size);
            throwCard = getCardString(usertemp.get(random));
            if (!PickCard.equalsIgnoreCase(" ")) {
                if (usertemp.size() >= 2) {
                    if (throwCard.equalsIgnoreCase(PickCard)) {
                        throwCard = getCardString(usertemp.get(1));
                    }
                }
            }
        } else {
            for (Item_Card card : usertemp) {
                if (!card.getCardInPair()) {
                    deadwoodCard.add(card);
                    deadwoodCard2.add(card);
                    deadwoodCard3.add(card);
                }
            }
            Collections.sort(deadwoodCard);
            Logger.print("_SOrting logic => after sort " + deadwoodCard.toString());
            for (int i = 0; i < deadwoodCard.size() - 1; i++) {
                if ((deadwoodCard.get(i).getCardValue() == (deadwoodCard.get(i + 1).getCardValue() - 1) && deadwoodCard.get(i).getCardColor() == (deadwoodCard.get(i + 1).getCardColor()))) {
                    paired.add(deadwoodCard.get(i));
                    paired.add(deadwoodCard.get(i + 1));
                    i++;
                }
            }
            Logger.print("MMMMMMMMMMMMMMMM>>>>>>>>>>>>paired>>>>>>>>> " + paired.toString());
            for (int i = 0; i < paired.size(); i++) {
                deadwoodCard.remove(paired.get(i));
                deadwoodCard2.remove(paired.get(i));
            }
            Logger.print("_SOrting logic => odd cards " + deadwoodCard.toString());
            Collections.sort(deadwoodCard, new Comparator<Item_Card>() {
                @Override
                public int compare(Item_Card lhs, Item_Card rhs) {
                    return Integer.compare(lhs.getCardValue(), rhs.getCardValue());
                }
            });
            Logger.print("_SOrting logix => pair sort => " + deadwoodCard);
            for (int i = 0; i < deadwoodCard.size() - 1; i++) {
                if ((deadwoodCard.get(i).getCardValue() == (deadwoodCard.get(i + 1).getCardValue()))) {
                    sequence.add(deadwoodCard.get(i));
                    sequence.add(deadwoodCard.get(i + 1));
                    i++;
                }
            }
            Logger.print("MMMMMMMMMMMMMMMM>>>>>>>>>>>>sequence>>>>>>>>> " + sequence.toString());
            for (int i = 0; i < sequence.size(); i++) {
                deadwoodCard.remove(sequence.get(i));
                deadwoodCard2.remove(sequence.get(i));
            }
            //
            ArrayList<Item_Card> oneCardDiffrence = new ArrayList<>();
            for (int i = 0; i < deadwoodCard.size(); i++) {
                for (int j = i; j < sequence.size(); j++) {
                    if (sequence.get(j).getCardColor() == deadwoodCard.get(i).getCardColor()) {
                        if (sequence.get(j).getCardValue() == deadwoodCard.get(i).getCardValue() - 2 || sequence.get(j).getCardValue() == deadwoodCard.get(i).getCardValue() + 2) {
                            oneCardDiffrence.add(deadwoodCard.get(i));
                            break;
                        }
                    }
                }
            }
            Logger.print("_SOrting logic => oneCardDiffrence => " + oneCardDiffrence.toString());
            if (deadwoodCard.size() > oneCardDiffrence.size()) {
                //>>>>>>>>>>> Before <<<<<<<<<<<<<<
//                for (int i = 0; i < oneCardDiffrence.size(); i++) {
//                    deadwoodCard.remove(oneCardDiffrence.get(i));
//                }
                //>>>>>>>>>>> Before <<<<<<<<<<<<<<

                //>>>>>>>>>>> After <<<<<<<<<<<<<<
                deadwoodCard.removeAll(oneCardDiffrence);
                //>>>>>>>>>>> After <<<<<<<<<<<<<<
            }
            if (!PickCard.equalsIgnoreCase(" ")) {
                if (deadwoodCard.size() > 0) {
                    deadwoodCard = new ArrayList<>(removepickCardFromarray(PickCard, deadwoodCard));
                }
                if (deadwoodCard2.size() > 0) {
                    deadwoodCard2 = new ArrayList<>(removepickCardFromarray(PickCard, deadwoodCard));
                }
                if (deadwoodCard3.size() > 0) {
                    deadwoodCard3 = new ArrayList<>(removepickCardFromarray(PickCard, deadwoodCard));
                }
            }
            int deadwood = 0;
            if (deadwoodCard.size() > 0) {
                Logger.print("MMMMMMMMMMMMMMMM>>>>>>>>>>>> throw from deadwoodCard>>>>>>>>> " + deadwoodCard.toString());
                throwCard = getHighDeadWoodCard(deadwoodCard);
//                for (int i = 0; i < deadwoodCard.size(); i++) {
//                    if (deadwoodCard.get(i).getCardValue() > deadwood) {
//                        deadwood = deadwoodCard.get(i).getCardValue();
//                        throwCard = getCardString(deadwoodCard.get(i));
//                    }
//                }
            } else {
                //throwCard = getHighDeadWoodCard(userCards);
                if (deadwoodCard2.size() > 0) {
                    Logger.print("MMMMMMMMMMMMMMMM>>>>>>>>>>>> throw from deadwoodCard2>>>>>>>>> " + deadwoodCard2.toString());
                    throwCard = getHighDeadWoodCard(deadwoodCard2);
                } else if (deadwoodCard3.size() > 0) {
                    Logger.print("MMMMMMMMMMMMMMMM>>>>>>>>>>>> throw from deadwoodCard3>>>>>>>>> " + deadwoodCard3.toString());
                    throwCard = getHighDeadWoodCard(deadwoodCard3);
                } else {
                    Logger.print("MMMMMMMMMMMMMMMM>>>>>>>>>>>> throw from userCards>>>>>>>>> " + usertemp.toString());
                    throwCard = getHighDeadWoodCard(usertemp);
                }
            }
        }
        Logger.print("_SOrting logic => throwCard cards " + throwCard);
        JSONObject jsonObject = new JSONObject();
        try {
            PickCard = " ";
            jsonObject.put(Parameters.Card, throwCard);
            jsonObject.put(Parameters.SeatIndex, CURRENT_USER_TURN);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put(Parameters.data, jsonObject);
            ThrowCardOnDeckProcess(jsonObject1.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Item_Card> removepickCardFromarray(String pickCard, ArrayList<Item_Card> deadwoodCard) {
        ArrayList<Item_Card> returnArray = new ArrayList<>(deadwoodCard);
        Logger.print("MMMMMMMMMMMMMM>>> Before >>> " + returnArray);
        for (int i = 0; i < returnArray.size(); i++) {
            String card = getCardString(deadwoodCard.get(i));
            if (card.equalsIgnoreCase(pickCard)) {
                returnArray.remove(i);
                break;
            }
        }
        Logger.print("MMMMMMMMMMMMMM>>> After Remove >>> " + returnArray);
        return returnArray;
    }

    private void AnimateUserThrowCardOnOpenDeck(int seatIndex, String card) {
        // TODO Auto-generated method stub
        Music_Manager.throwcard();
        iCardThrowOnDeck = -1;
        int index = Arrays.asList(cardString).indexOf(card);
        Logger.print("throw card for animation => " + card + " " + seatIndex + " index => " + index);
        RotateAnimation rotateThrowCardTCOD = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateThrowCardTCOD.setDuration(600);
        rotateThrowCardTCOD.setFillAfter(false);
        ScaleAnimation scaleAnimTCOD = new ScaleAnimation(1.0f, 1.8f, 1.0f, 1.8f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnimTCOD.setDuration(600);
        scaleAnimTCOD.setFillAfter(false);
        closeCardXY = new int[2];
        ivCloseCard.getLocationOnScreen(closeCardXY);
        int xTCOD = closeCardXY[0] + getwidth(30);
        int yTCOD = closeCardXY[1] + getheight(20);
        if (seatIndex == LeftSeatIndex) {
            ivUserOneThrownCard.setVisibility(View.INVISIBLE);
            ivUserOneThrownCard2.setBackgroundResource(CardDefault[index]);
            pickCardAnimLeftUserXY = new int[2];
            ivUserOneThrownCard2.getLocationOnScreen(pickCardAnimLeftUserXY);
            int xLeftTCOD = pickCardAnimLeftUserXY[0];
            int yLeftTCOD = pickCardAnimLeftUserXY[1];
            TranslateAnimation tAnimLeftTCOD = new TranslateAnimation(0, xTCOD - xLeftTCOD, 0,
                    yTCOD - yLeftTCOD);
            tAnimLeftTCOD.setDuration(600);
            tAnimLeftTCOD.setFillAfter(false);
            AnimationSet animSetLeftTCOD = new AnimationSet(true);
            animSetLeftTCOD.addAnimation(scaleAnimTCOD);
            //animSetLeftTCOD.addAnimation(rotateThrowCardTCOD);
            animSetLeftTCOD.addAnimation(tAnimLeftTCOD);
            animSetLeftTCOD.setFillAfter(false);
            animationHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (ivUserOneThrownCard2.getAnimation() != null) {
                        ivUserOneThrownCard2.clearAnimation();
                    }
                    ivUserOneThrownCard2.setVisibility(View.INVISIBLE);
                    try {
                        iCardThrowOnDeck = openBunchCard.size() - 1;
                        if (iCardThrowOnDeck >= 0) {
                            int thrownCard = Arrays.asList(cardString).indexOf(
                                    getCardString(openBunchCard.get(iCardThrowOnDeck)));
                            ivCloseCard.setVisibility(View.VISIBLE);
                            ivCloseCard.setImageResource(CardDefault[thrownCard]);
                            ivCloseCard.setTag(cardString[thrownCard]);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    frmDeckBackGround.invalidate();
                }
            }, 600);
            /*animSetLeftTCOD.getAnimations().get(1).setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    // TODO Auto-generated method stub
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                    // TODO Auto-generated method stub
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    // TODO Auto-generated method stub
                    if (ivUserOneThrownCard.getAnimation() != null) {
                        ivUserOneThrownCard.clearAnimation();
                    }
                    ivUserOneThrownCard.setVisibility(View.INVISIBLE);
                    try {
                        iCardThrowOnDeck = openBunchCard.size() - 1;
                        if (iCardThrowOnDeck >= 0) {
                            int thrownCard = Arrays.asList(cardString).indexOf(
                                    getCardString(openBunchCard.get(iCardThrowOnDeck)));
                            ivCloseCard.setVisibility(View.VISIBLE);
                            ivCloseCard.setImageResource(CardDefault[thrownCard]);
                            ivCloseCard.setTag(cardString[thrownCard]);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    frmDeckBackGround.invalidate();
                }
            });*/
            ivUserOneThrownCard2.startAnimation(animSetLeftTCOD);
        } else if (seatIndex == RightSeatIndex) {
            ivUserTwoThrownCard.setVisibility(View.INVISIBLE);
            ivUserTwoThrownCard2.setBackgroundResource(CardDefault[index]);
            pickCardAnimRightUserXY = new int[2];
            ivUserTwoThrownCard2.getLocationOnScreen(pickCardAnimRightUserXY);
            int xRightTCOD = pickCardAnimRightUserXY[0];
            int yRightTCOD = pickCardAnimRightUserXY[1];

            TranslateAnimation tAnimRightTCOD = new TranslateAnimation(0, xTCOD - xRightTCOD,
                    0, yTCOD - yRightTCOD);
            tAnimRightTCOD.setDuration(600);
            tAnimRightTCOD.setFillAfter(false);

            AnimationSet animSetRightTCOD = new AnimationSet(true);
            animSetRightTCOD.addAnimation(scaleAnimTCOD);
            //animSetRightTCOD.addAnimation(rotateThrowCardTCOD);
            animSetRightTCOD.addAnimation(tAnimRightTCOD);
            animSetRightTCOD.setFillAfter(false);
//            animSetRightTCOD.getAnimations().get(1).setAnimationListener(new AnimationListener() {
//
//                @Override
//                public void onAnimationStart(Animation animation) {
//                    // TODO Auto-generated method stub
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//                    // TODO Auto-generated method stub
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    // TODO Auto-generated method stub
//                    if (ivUserOneThrownCard.getAnimation() != null) {
//                        ivUserOneThrownCard.clearAnimation();
//                    }
//                    ivUserTwoThrownCard.setVisibility(View.GONE);
//
//                    try {
//                        iCardThrowOnDeck = openBunchCard.size() - 1;
//
//                        if (iCardThrowOnDeck >= 0) {
//
//                            int thrownCard = Arrays.asList(cardString).indexOf(
//                                    getCardString(openBunchCard.get(iCardThrowOnDeck)));
//                            ivCloseCard.setVisibility(View.VISIBLE);
//                            ivCloseCard.setImageResource(CardDefault[thrownCard]);
//                            ivCloseCard.setTag(cardString[thrownCard]);
//                        }
//                    } catch (Exception e) {
//                        Logger.print("Exception Thrown....");
//                        e.printStackTrace();
//                    }
//
//                    /*int thrownCard = Arrays.asList(cardString).indexOf(
//                            getCardString(openBunchCard.get(openBunchCard
//                                    .size() - 1)));
//                    ivCloseCard.setBackgroundResource(CardDefault[thrownCard]);
//                    ivCloseCard.setTag(cardString[thrownCard]);*/
//                    frmDeckBackGround.invalidate();
//                }
//
//            });
            animationHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (ivUserTwoThrownCard2.getAnimation() != null) {
                        ivUserTwoThrownCard2.clearAnimation();
                    }
                    ivUserTwoThrownCard2.setVisibility(View.INVISIBLE);
                    try {
                        iCardThrowOnDeck = openBunchCard.size() - 1;
                        if (iCardThrowOnDeck >= 0) {
                            int thrownCard = Arrays.asList(cardString).indexOf(
                                    getCardString(openBunchCard.get(iCardThrowOnDeck)));
                            ivCloseCard.setVisibility(View.VISIBLE);
                            ivCloseCard.setImageResource(CardDefault[thrownCard]);
                            ivCloseCard.setTag(cardString[thrownCard]);
                        }
                    } catch (Exception e) {
                        Logger.print("Exception Thrown....");
                        e.printStackTrace();
                    }
                    /*int thrownCard = Arrays.asList(cardString).indexOf(
                            getCardString(openBunchCard.get(openBunchCard
                                    .size() - 1)));
                    ivCloseCard.setBackgroundResource(CardDefault[thrownCard]);
                    ivCloseCard.setTag(cardString[thrownCard]);*/
                    frmDeckBackGround.invalidate();
                }
            }, 600);
            ivUserTwoThrownCard2.startAnimation(animSetRightTCOD);
        }
    }

    private void AnimateOpenBunchToUser(int seatIndex, final String card) {
        // TODO Auto-generated method stub
        Music_Manager.throwcard();
        int ind = Arrays.asList(cardString).indexOf(card);
        ivPickCard.setVisibility(View.VISIBLE);
        ivPickCard.bringToFront();
        if (back_menu_frame.getVisibility() == View.VISIBLE) {
            back_menu_frame.bringToFront();
        }
        ivPickCard.setBackgroundResource(CardDefault[ind]);
        ivPickCard.getLocationOnScreen(closeCardXY);
        int xPFTC = closeCardXY[0];
        int yPFTC = closeCardXY[1];
        ScaleAnimation scaleAnimPFTC = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnimPFTC.setDuration(600);
        scaleAnimPFTC.setFillAfter(false);
        if (seatIndex == RightSeatIndex) {
            ivUserTwoCard.getLocationOnScreen(pickCardAnimRightUserXY);
            int xRightPFTC = pickCardAnimRightUserXY[0] - getwidth(25);
            int yRightPFTC = pickCardAnimRightUserXY[1] - getheight(38);
            TranslateAnimation tAnimRightPFTC = new TranslateAnimation(0, xRightPFTC - xPFTC,
                    0, yRightPFTC - yPFTC);
            tAnimRightPFTC.setDuration(600);
            tAnimRightPFTC.setFillAfter(false);
            setToRight = new AnimationSet(true);
            setToRight.addAnimation(scaleAnimPFTC);
            setToRight.addAnimation(tAnimRightPFTC);
            if (setToRight != null) {
                frmTemp12.bringToFront();
                ivPickCard.bringToFront();
                ivPickCard.startAnimation(setToRight);
                setToRight.getAnimations().get(1).setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub
                        if (openBunchCard.size() > 0) {
                            Item_Card openCard = openBunchCard
                                    .get(openBunchCard.size() - 1);
                            String openCardStr = getCardString(openCard);
                            int index = Arrays.asList(cardString).indexOf(
                                    openCardStr);
                            if (index != -1) {
                                ivCloseCard
                                        .setImageResource(CardDefault[index]);
                                ivCloseCard.setTag(cardString[index]);
                            }
                        }
                        if (openBunchCard.size() <= 0) {
                            ivCloseCard
                                    .setImageResource(android.R.color.transparent);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // TODO Auto-generated method stub
                        if (ivPickCard.getAnimation() != null) {
                            ivPickCard.clearAnimation();
                        }
                        ivPickCard.setVisibility(View.INVISIBLE);
                    }
                });
            }
        } else if (seatIndex == LeftSeatIndex) {
            ivUserOneCard.getLocationOnScreen(pickCardAnimLeftUserXY);
            int xLeftPFTC = pickCardAnimLeftUserXY[0] - getwidth(25);
            int yLeftPFTC = pickCardAnimLeftUserXY[1] - getheight(38);
            TranslateAnimation tAnimLeftPFTC = new TranslateAnimation(0, xLeftPFTC - xPFTC, 0,
                    yLeftPFTC - yPFTC);
            tAnimLeftPFTC.setDuration(600);
            tAnimLeftPFTC.setFillAfter(false);
            setToLeft = new AnimationSet(true);
            setToLeft.addAnimation(scaleAnimPFTC);
            setToLeft.addAnimation(tAnimLeftPFTC);
            if (setToLeft != null) {
//                frmDeckBackGround.invalidate();
                frmMainLayout.invalidate();
                frmTemp12.bringToFront();
                ivPickCard.bringToFront();
                ivPickCard.startAnimation(setToLeft);
                setToLeft.getAnimations().get(1).setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub
                        if (openBunchCard.size() > 0) {
                            Item_Card openCard = openBunchCard
                                    .get(openBunchCard.size() - 1);
                            String openCardStr = getCardString(openCard);
                            int index = Arrays.asList(cardString).indexOf(
                                    openCardStr);
                            if (index != -1) {
                                ivCloseCard
                                        .setImageResource(CardDefault[index]);
                                ivCloseCard.setTag(cardString[index]);
                            }
                        }
                        if (openBunchCard.size() <= 0) {
                            ivCloseCard
                                    .setImageResource(android.R.color.transparent);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // TODO Auto-generated method stub
                        if (ivPickCard.getAnimation() != null) {
                            ivPickCard.clearAnimation();
                        }
                        ivPickCard.setVisibility(View.INVISIBLE);
                    }
                });
            }
        } else if (seatIndex == BottomSeatIndex) {
            ivPickCard.setVisibility(View.INVISIBLE);
            ivPickCard9.setVisibility(View.VISIBLE);
            ivPickCard9.bringToFront();
            if (back_menu_frame.getVisibility() == View.VISIBLE) {
                back_menu_frame.bringToFront();
            }
            ivPickCard9.setBackgroundResource(CardDefault[ind]);
            ivPickCard9.getLocationOnScreen(closeCardXY);
            // String card = openBunchCard.get(openBunchCard.size() - 1);
            if (card_BottomUser.size() > 0) {
                ivMyCards[card_BottomUser.size() - 1]
                        .getLocationOnScreen(lastCardOfUser);
                // ivBottomUserCard.getLocationOnScreen(lastCardOfUser);
                int x1PFTC = lastCardOfUser[0];
                int y1PFTC = lastCardOfUser[1];
                int deltaX = x1PFTC - xPFTC;
                int deltaY = x1PFTC - xPFTC;
                tAnimBottomPFTC = new TranslateAnimation(0, x1PFTC - xPFTC, 0, y1PFTC - yPFTC);
                tAnimBottomPFTC.setDuration(600);
                tAnimBottomPFTC.setFillAfter(false);
                ScaleAnimation scale = new ScaleAnimation(1, 1.25f, 1, 1.25f, Animation.RELATIVE_TO_SELF, 1.25f,
                        Animation.RELATIVE_TO_SELF, 1.25f);
                scale.setFillAfter(true);
                scale.setDuration(300);
                ScaleAnimation scale2 = new ScaleAnimation(1, .8f, 1f, .8f, Animation.RELATIVE_TO_SELF, .8f,
                        Animation.RELATIVE_TO_SELF, .8f);
                scale2.setFillAfter(true);
                scale2.setStartOffset(300);
                scale2.setDuration(300);
                Rotate3dAnimationX zrotate = new Rotate3dAnimationX(25, 0, (ivPickCard9.getWidth() / 2), (ivPickCard9.getHeight() / 2), 0, false);
                zrotate.setDuration(600);
                zrotate.setInterpolator(new LinearInterpolator());
                AnimationSet bottomset = new AnimationSet(false);
                bottomset.addAnimation(scale);
                bottomset.addAnimation(scale2);
                bottomset.addAnimation(tAnimBottomPFTC);
                bottomset.addAnimation(zrotate);
                frmMainLayout.invalidate();
                ivPickCard9.bringToFront();
                frmDeckBackGround9.bringToFront();
                ivPickCard9.startAnimation(bottomset);
//                final AnimatorSet mAnimatorSet = new AnimatorSet();
//                mAnimatorSet.playTogether(
////                        ObjectAnimator.ofFloat(ivPickCard,"scaleX",1,0.9f,0.9f,1.1f,1.1f,1),
////                        ObjectAnimator.ofFloat(ivPickCard,"scaleY",1,0.9f,0.9f,1.1f,1.1f,1),
//                        ObjectAnimator.ofFloat(ivPickCard, "translationX", deltaX),
//                        ObjectAnimator.ofFloat(ivPickCard, "translationY", deltaY),
//                        ObjectAnimator.ofFloat(ivPickCard,"rotationX",45f,0)
//
//                );
//
////define any animation you want,like above
//
//                mAnimatorSet.setDuration(600); //set duration for animations
//                mAnimatorSet.setInterpolator(new LinearInterpolator());
//                mAnimatorSet.start();
            }
            if (tAnimBottomPFTC != null) {
//                ivPickCard.startAnimation(bottomset);
                animationHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // card_BottomUser.add(pickedCard);
                        // ivMyCards[card_BottomUser.size() -
                        // 1].setBackgroundResource(CardDefault[index]);
                        // ivMyCards[card_BottomUser.size() -
                        // 1].setTag(cardString[index]);
                        // card_BottomUser.add(cardString[index]);
                        if (ivPickCard9.getAnimation() != null) {
                            ivPickCard9.clearAnimation();
                        }
                        ivPickCard9.setVisibility(View.INVISIBLE);
                        ivPickCard9.setVisibility(View.INVISIBLE);
                        hasTakenCard = true;
                        /*card_BottomUser = new ArrayList<Item_Card>(Sort(
                                card_BottomUser, BottomSeatIndex));*/
                        //DeadwoodCount();
                        reDrawUserCards(card);
                    }
                }, 600);
                tAnimBottomPFTC.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub
                        // Logger.print("$$$$$$$$$$$$$$$$$$$1 "
                        // + closeBunchCard.toString());
                        if (openBunchCard.size() > 0) {
                            Item_Card openCard = openBunchCard
                                    .get(openBunchCard.size() - 1);
                            String openCardStr = getCardString(openCard);
                            int index1 = Arrays.asList(cardString).indexOf(
                                    openCardStr);
                            if (index1 != -1) {
                                ivCloseCard
                                        .setImageResource(CardDefault[index1]);
                                ivCloseCard.setTag(openCardStr);
                            }
                        }
                        if (openBunchCard.size() <= 0) {
                            // Logger.print("$$$$$$$$$$$$$$$$$$$");
                            ivCloseCard
                                    .setImageResource(android.R.color.transparent);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // TODO Auto-generated method stub
//                        // card_BottomUser.add(pickedCard);
//
//                        // ivMyCards[card_BottomUser.size() -
//                        // 1].setBackgroundResource(CardDefault[index]);
//                        // ivMyCards[card_BottomUser.size() -
//                        // 1].setTag(cardString[index]);
//                        // card_BottomUser.add(cardString[index]);
//                        if (ivPickCard.getAnimation() != null) {
//                            ivPickCard.clearAnimation();
//                        }
//                        ivPickCard.setVisibility(View.INVISIBLE);
//                        ivPickCard.setVisibility(View.INVISIBLE);
//
//                        hasTakenCard = true;
//                        /*card_BottomUser = new ArrayList<Item_Card>(Sort(
//                                card_BottomUser, BottomSeatIndex));*/
//                        //DeadwoodCount();
//                        reDrawUserCards(card);
                    }
                });
            }
        }
    }

    private void animateCloseBunchCardToUSer(int seatIndex, final String card) {
        // TODO Auto-generated method stub
        Music_Manager.throwcard();
        ivCardOnDeck.setVisibility(View.VISIBLE);
        ivCloseDeck.getLocationOnScreen(closeDeckXY);
        ivCardOnDeck.bringToFront();
        //ivCardOnDeck9.bringToFront();
        if (back_menu_frame.getVisibility() == View.VISIBLE) {
            back_menu_frame.bringToFront();
        }
        int xPFP = closeDeckXY[0];
        int yPFP = closeDeckXY[1];
        ScaleAnimation scaleAnimPFP = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnimPFP.setDuration(600);
        scaleAnimPFP.setFillAfter(false);
        // setToRight.setInterpolator(new DecelerateInterpolator());
        if (seatIndex == RightSeatIndex) {
            ivUserTwoCard.getLocationOnScreen(pickCardAnimRightUserXY);
            int xRightPFP = pickCardAnimRightUserXY[0] - getwidth(25);
            int yRightPFP = pickCardAnimRightUserXY[1] - getheight(38);
            TranslateAnimation tAnimRightPFP = new TranslateAnimation(0, xRightPFP - xPFP,
                    0, yRightPFP - yPFP);
            tAnimRightPFP.setDuration(600);
            tAnimRightPFP.setFillAfter(false);
            setToRight = new AnimationSet(true);
            setToRight.addAnimation(scaleAnimPFP);
            setToRight.addAnimation(tAnimRightPFP);
            ivCardOnDeck.setBackgroundResource(R.drawable.card);
            frmTemp11.bringToFront();
            ivCardOnDeck.bringToFront();
            ivCardOnDeck.startAnimation(setToRight);
            setToRight.getAnimations().get(1)
                    .setAnimationListener(new AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            // TODO Auto-generated method stub
                            Logger.print("End Left.......................1");
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            // TODO Auto-generated method stub
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            // TODO Auto-generated method stub
                            Logger.print("End Left.......................2");
                            if (ivCardOnDeck.getAnimation() != null) {
                                ivCardOnDeck.clearAnimation();
                            }
                            ivCardOnDeck.setVisibility(View.GONE);
                        }
                    });
        } else if (seatIndex == LeftSeatIndex) {
            ivUserOneCard.getLocationOnScreen(pickCardAnimLeftUserXY);
            int xLeftPFP = pickCardAnimLeftUserXY[0] - getwidth(25);
            int yLeftPFP = pickCardAnimLeftUserXY[1] - getheight(38);
            TranslateAnimation tAnimLeftPFP = new TranslateAnimation(0, xLeftPFP - xPFP, 0,
                    yLeftPFP - yPFP);
            tAnimLeftPFP.setDuration(600);
            tAnimLeftPFP.setFillAfter(false);
            setToLeft = new AnimationSet(true);
            setToLeft.addAnimation(scaleAnimPFP);
            setToLeft.addAnimation(tAnimLeftPFP);
            ivCardOnDeck.setBackgroundResource(R.drawable.card);
            frmTemp11.bringToFront();
            ivCardOnDeck.bringToFront();
            ivCardOnDeck.startAnimation(setToLeft);
            setToLeft.getAnimations().get(1)
                    .setAnimationListener(new AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            // TODO Auto-generated method stub
                            Logger.print("End Right.......................1");
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            // TODO Auto-generated method stub
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            // TODO Auto-generated method stub
                            Logger.print("End Right.......................2");
                            if (ivCardOnDeck.getAnimation() != null) {
                                ivCardOnDeck.clearAnimation();
                            }
                            ivCardOnDeck.setVisibility(View.GONE);
                        }
                    });
        } else if (seatIndex == BottomSeatIndex) {
            //ivCardOnDeck9.setVisibility(View.VISIBLE);
            /*final Item_Card pickedCard = closeBunchCard.get(closeBunchCard
                    .size() - 1);
            String pickedCardStr = getCardString(pickedCard);*/
            ivCardOnDeck.setVisibility(View.INVISIBLE);
            final int index = Arrays.asList(cardString).indexOf(card);
            ivCardOnDeck9.setBackgroundResource(CardDefault[index]);
            ivCloseDeck9.getLocationOnScreen(closeDeckXY);
            int xxPFP = closeDeckXY[0];
            int yyPFP = closeDeckXY[1];
            ivMyCards[card_BottomUser.size() - 1]
                    .getLocationOnScreen(lastCardOfUser);
            //ivBottomUserCard.getLocationOnScreen(lastCardOfUser);
            int x1PFP = lastCardOfUser[0];
            int y1PFP = lastCardOfUser[1];
            int deltaX = x1PFP - xxPFP;
            int deltaY = y1PFP - yyPFP;
            TranslateAnimation tAnimationBottomPFP = new TranslateAnimation(0, x1PFP - xxPFP,
                    0, y1PFP - yyPFP);
            tAnimationBottomPFP.setDuration(600);
            // tAnimation.setInterpolator(new DecelerateInterpolator());
            ScaleAnimation scale = new ScaleAnimation(1, 1.25f, 1, 1.25f, Animation.RELATIVE_TO_SELF, 1.25f,
                    Animation.RELATIVE_TO_SELF, 1.25f);
            scale.setFillAfter(true);
            scale.setDuration(300);
            ScaleAnimation scale2 = new ScaleAnimation(1, .8f, 1f, .8f, Animation.RELATIVE_TO_SELF, .8f,
                    Animation.RELATIVE_TO_SELF, .8f);
            scale2.setFillAfter(true);
            scale2.setStartOffset(300);
            scale2.setDuration(300);
            Rotate3dAnimationX zrotate = new Rotate3dAnimationX(25, 0, (ivCardOnDeck9.getWidth() / 2), (ivCardOnDeck9.getHeight() / 2), 0, false);
            zrotate.setDuration(600);
            zrotate.setInterpolator(new LinearInterpolator());
            AnimationSet bottomset = new AnimationSet(false);
            bottomset.addAnimation(scale);
            bottomset.addAnimation(scale2);
            bottomset.addAnimation(tAnimationBottomPFP);
            bottomset.addAnimation(zrotate);
            //ivCardOnDeck.setRotationX(-0);
            frmMainLayout.invalidate();
            ivCardOnDeck9.bringToFront();
            frmDeckBackGround9.bringToFront();
            animationHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        int bottomCardSize = card_BottomUser.size();
                        if (bottomCardSize > 0) {
                            //ivCardOnDeck.setRotationX(0);
                            ivMyCards[bottomCardSize - 2]
                                    .setBackgroundResource(CardDefault[index]);
                            ivMyCards[bottomCardSize - 2]
                                    .setTag(cardString[index]);
                            if (ivCardOnDeck9.getAnimation() != null) {
                                ivCardOnDeck9.clearAnimation();
                            }
                            ivCardOnDeck9.setVisibility(View.GONE);
                            ivCardOnDeck9.invalidate();
                        /*card_BottomUser = new ArrayList<Item_Card>(Sort(
                                card_BottomUser, BottomSeatIndex));*/
                            //DeadwoodCount();
                            reDrawUserCards(card);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        reDrawUserCards(card);
                    }
                }
            }, 600);
            tAnimationBottomPFP.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // TODO Auto-generated method stub
//                    // card_BottomUser.add(pickedCard);
//                    int bottomCardSize = card_BottomUser.size();
//                    if (bottomCardSize > 0) {
//                        ivMyCards[bottomCardSize - 2]
//                                .setBackgroundResource(CardDefault[index]);
//                        ivMyCards[bottomCardSize - 2]
//                                .setTag(cardString[index]);
//
//                        if (ivCardOnDeck.getAnimation() != null) {
//                            ivCardOnDeck.clearAnimation();
//                        }
//                        ivCardOnDeck.setVisibility(View.GONE);
//                        ivCardOnDeck.invalidate();
//
//
//                        /*card_BottomUser = new ArrayList<Item_Card>(Sort(
//                                card_BottomUser, BottomSeatIndex));*/
//
//                        //DeadwoodCount();
//                        reDrawUserCards(card);
//                    }
//                    // EmitPickFromPile();
                }
            });
            ivCardOnDeck9.startAnimation(bottomset);
//            final AnimatorSet mAnimatorSet = new AnimatorSet();
//            mAnimatorSet.playTogether(
////                    ObjectAnimator.ofFloat(ivCardOnDeck,"scaleX",1,0.9f,0.9f,1.1f,1.1f,1),
////                    ObjectAnimator.ofFloat(ivCardOnDeck,"scaleY",1,0.9f,0.9f,1.1f,1.1f,1),
//                    ObjectAnimator.ofFloat(ivPickCard, "translationX", deltaX),
//                    ObjectAnimator.ofFloat(ivPickCard, "translationY", deltaY),
//                    ObjectAnimator.ofFloat(ivPickCard,"rotationX",45f,0)
//            );
//
////define any animation you want,like above
//
//            mAnimatorSet.setDuration(3000); //set duration for animations
//            mAnimatorSet.setInterpolator(new LinearInterpolator());
//            mAnimatorSet.start();
        }
    }

    private int CalculateTotalCount(JSONArray card) throws JSONException {
        int TotalCount = 0;
        for (int i = 0; i < card.length(); i++) {
            int value;
            if (getItemCard(card.getString(i)).getCardValue() >= 10) {
                value = 10;
            } else {
                value = getItemCard(card.getString(i)).getCardValue();
            }
            TotalCount += value;
        }
        return TotalCount;
    }

    private void SortSimpleSequenceWise(ArrayList<Item_Card> UserCard) {
        // TODO Auto-generated method stub
        try {
            Collections.sort(UserCard, new Comparator<Item_Card>() {
                @Override
                public int compare(Item_Card lhs, Item_Card another) {
                    // TODO Auto-generated method stub
                    try {
                        return Integer.compare(lhs.getCardValue(), another.getCardValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return 0;
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SortTripleWise(ArrayList<Item_Card> array) {
        try {
            Collections.sort(array, new Comparator<Item_Card>() {
                @Override
                public int compare(Item_Card lhs, Item_Card rhs) {
                    // TODO Auto-generated method stub
                    //return (lhs.getCardValue() - rhs.getCardValue());
                    try {
                        return Integer.compare(lhs.getCardValue(), rhs.getCardValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return 0;
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Item_Card> Sort(ArrayList<Item_Card> mCardsArray, int seatIndex) {
        ArrayList<Item_Card> FinalSortedArray = new ArrayList<>();
        Logger.print("Cads from SOrt method => " + mCardsArray.toString());
        Hand test = new Hand();
        for (int i = 0; i < mCardsArray.size(); i++) {
            test.add(test.getCard(mCardsArray.get(i)));
        }
        test.autoMatch();
        for (ICardSet set : test.getMatchedSets()) {
            if (set.isGroup()) {
                JSONArray jArray = new JSONArray();
                for (Card c : set) {
                    String card = test.getCardColor(c) + "-"
                            + (c.getRank().ordinal() + 1);
                    jArray.put(card);
                }
                test.MatchedCardsArray.put(jArray);
            }
        }
        JSONArray temp = new JSONArray();
        try {
            for (int i = 0; i < test.MatchedCardsArray.length(); i++) {
                for (int j = 0; j < test.MatchedCardsArray.length() - 1; j++) {
                    if (CalculateTotalCount(test.MatchedCardsArray
                            .getJSONArray(j)) > CalculateTotalCount(test.MatchedCardsArray
                            .getJSONArray(j + 1))) {
                        temp = test.MatchedCardsArray.getJSONArray(j);
                        test.MatchedCardsArray.put(j,
                                test.MatchedCardsArray.get(j + 1));
                        test.MatchedCardsArray.put(j + 1, temp);// array[d+1] =
                        // swap;
                    } else if (CalculateTotalCount(test.MatchedCardsArray
                            .getJSONArray(j)) == CalculateTotalCount(test.MatchedCardsArray
                            .getJSONArray(j + 1))) {
                        if (test.MatchedCardsArray.getJSONArray(j).length() < test.MatchedCardsArray
                                .getJSONArray(j + 1).length()) {
                            temp = test.MatchedCardsArray.getJSONArray(j);
                            test.MatchedCardsArray.put(j,
                                    test.MatchedCardsArray.get(j + 1));
                            test.MatchedCardsArray.put(j + 1, temp);// array[d+1]
                            // = swap;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (ICardSet set : test.getMatchedSets()) {
            if (set.isRun()) {
                JSONArray jArray = new JSONArray();
                for (Card c : set) {
                    String card = test.getCardColor(c) + "-"
                            + (c.getRank().ordinal() + 1);
                    jArray.put(card);
                }
                test.MatchedCardsArray1.put(jArray);
            }
        }
        try {
            for (int i = 0; i < test.MatchedCardsArray1.length(); i++) {
                for (int j = 0; j < test.MatchedCardsArray1.length() - 1; j++) {
                    if (getItemCard(
                            test.MatchedCardsArray1.getJSONArray(j)
                                    .getString(0)).getCardColor() > getItemCard(
                            test.MatchedCardsArray1.getJSONArray(j + 1)
                                    .getString(0)).getCardColor()) {
                        temp = test.MatchedCardsArray1.getJSONArray(j);
                        test.MatchedCardsArray1.put(j,
                                test.MatchedCardsArray1.get(j + 1));
                        test.MatchedCardsArray1.put(j + 1, temp);// array[d+1] =
                        // swap;
                    } else if (getItemCard(
                            test.MatchedCardsArray1.getJSONArray(j)
                                    .getString(0)).getCardColor() == getItemCard(
                            test.MatchedCardsArray1.getJSONArray(j + 1)
                                    .getString(0)).getCardColor()) {
                        if (CalculateTotalCount(test.MatchedCardsArray1
                                .getJSONArray(j)) > CalculateTotalCount(test.MatchedCardsArray1
                                .getJSONArray(j + 1))) /*
                                                         * For descending order
														 * use <
														 */ {
                            temp = test.MatchedCardsArray1.getJSONArray(j);
                            test.MatchedCardsArray1.put(j,
                                    test.MatchedCardsArray1.get(j + 1));
                            test.MatchedCardsArray1.put(j + 1, temp);// array[d+1]
                            // =
                            // swap;
                        } else if (CalculateTotalCount(test.MatchedCardsArray1
                                .getJSONArray(j)) == CalculateTotalCount(test.MatchedCardsArray1
                                .getJSONArray(j + 1))) {
                            if (test.MatchedCardsArray1.getJSONArray(j)
                                    .length() < test.MatchedCardsArray1
                                    .getJSONArray(j + 1).length()) {
                                temp = test.MatchedCardsArray1.getJSONArray(j);
                                test.MatchedCardsArray1.put(j,
                                        test.MatchedCardsArray1.get(j + 1));
                                test.MatchedCardsArray1.put(j + 1, temp);// array[d+1]
                                // =
                                // swap;
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < test.MatchedCardsArray1.length(); i++) {
                test.MatchedCardsArray.put(test.MatchedCardsArray1.get(i));
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        for (Card set : test.getUnmatchedCards()) {
            String card = test.getCardColor(set) + "-"
                    + (set.getRank().ordinal() + 1);
            test.UnMatchedCardsArray.put(card);
        }
        try {
            for (int i = 0; i < test.MatchedCardsArray.length(); i++) {
                ArrayList<Item_Card> arr = new ArrayList<>();
                for (int j = 0; j < test.MatchedCardsArray.getJSONArray(i)
                        .length(); j++) {
                    Item_Card obj = getItemCard(test.MatchedCardsArray
                            .getJSONArray(i).getString(j));
                    obj.setPairNumber(i + 1);
                    obj.setCardInPair(true);
                    obj.setIsValidGroup(true);
                    obj.setGroupNumber(i + 1);
                    arr.add(obj);
                }
                Collections.sort(arr);
                FinalSortedArray.addAll(arr);
            }
            ArrayList<Item_Card> arr2 = new ArrayList<>();
            for (int i = 0; i < test.UnMatchedCardsArray.length(); i++) {
                Item_Card obj = getItemCard(test.UnMatchedCardsArray
                        .getString(i));
                obj.setPairNumber(0);
                obj.setGroupNumber(0);
                obj.setCardInPair(false);
                obj.setIsValidGroup(false);
                arr2.add(obj);
            }
            Collections.sort(arr2);
            if (!isDefaultSequencewise) {
                SortTripleWise(arr2);
            }
            FinalSortedArray.addAll(arr2);
            ArrayList<Item_Card> UnPairedCards = new ArrayList<>();
            for (int j = 0; j < FinalSortedArray.size(); j++) {
                if (!FinalSortedArray.get(j).getCardInPair()) {
                    UnPairedCards.add(FinalSortedArray.get(j).clone());
                }
            }
            if (FinalSortedArray.size() > cardsize) {
                if (UnPairedCards.size() > 0) {
                    SortSimpleSequenceWise(UnPairedCards);
                    UnPairedCards.remove(UnPairedCards.size() - 1);
                }
            }
            if (seatIndex == RightSeatIndex) {
                RightDeadWood = getDeadwoodPoints(UnPairedCards);
                Logger.print("DeadWood Counter => Right => " + RightDeadWood);
            } else if (seatIndex == LeftSeatIndex) {
                LeftDeadWood = getDeadwoodPoints(UnPairedCards);
                Logger.print("DeadWood Counter => Left => " + LeftDeadWood);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FinalSortedArray;
    }

    private int getDeadwoodPoints(ArrayList<Item_Card> cards) {
        int deadwood = 0;
        for (int i = 0; i < cards.size(); i++) {
            int temppoint = cards.get(i).getCardValue();
            if (temppoint >= 10) {
                temppoint = 10;
            }
            deadwood += temppoint;
        }
        return deadwood;
    }

    private void StartDealingProcess() {
        // TODO Auto-generated method stub
        Logger.print("WWWWWWWWWWW::::::::StartDealingProcess()>>>> ");
        User_turn_cnt = 0;
        try {
            HashMap<String, String> myMap = new HashMap<>();
            myMap = sqLiteManager.getUserData(PreferenceManager.get_id());
            totalGameCounter = Integer.parseInt(myMap.get(c.PLAYED));
        } catch (Exception e) {
            e.printStackTrace();
            totalGameCounter = 3;
        }

        PileCards = new ArrayList<>(Arrays.asList(cardString));
        Collections.shuffle(PileCards);
        card_RightUser = new ArrayList<>();
        card_LeftUser = new ArrayList<>();
        card_BottomUser = new ArrayList<>();
        openBunchCard.clear();
        closeBunchCard.clear();
        openBunchCard = new ArrayList<>();
        closeBunchCard = new ArrayList<>();
        if (numberOfPlayers == 3) {
            cardsize = 7;
        } else {
            cardsize = 10;
        }
        if (c.lostCount >= 3 && totalGameCounter > c.easygamecounter && c.tableindex <= 5) {
            Random ram = new Random();
            int isEasy = ram.nextInt(2);
            if (isEasy == 0) {
                totalGameCounter = 2;
            }
        }

        boolean isSequeceGive = false;
        boolean isTwoSequeceGive = false;
        if (totalGameCounter > c.easygamecounter) {
            if (PreferenceManager.getChips() < 5000) {
                Random ram = new Random();
                int isEasy = ram.nextInt(10);
                if (isEasy < 9) {//90%
                    isSequeceGive = true;
                } else {
                    isTwoSequeceGive = true;
                }
            } else if (PreferenceManager.getChips() < 1000000) {
                Random ram = new Random();
                int isEasy = ram.nextInt(10);
                if (isEasy < 7) {//70%
                    Logger.print(" >>> CARDS >>> EASY CARDS GIVEN >>>");
                    isSequeceGive = true;
                }
            } else if (PreferenceManager.getChips() < 1500000) {
                Random ram = new Random();
                int isEasy = ram.nextInt(10);
                if (isEasy < 5) {//50%
                    Logger.print(" >>> CARDS >>> EASY CARDS GIVEN >>>");
                    isSequeceGive = true;
                }
            } else if (PreferenceManager.getChips() < 2000000) {
                Random ram = new Random();
                int isEasy = ram.nextInt(10);
                if (isEasy < 4) {//40%
                    Logger.print(" >>> CARDS >>> EASY CARDS GIVEN >>>");
                    isSequeceGive = true;
                }
            } else if (PreferenceManager.getChips() > 2000000) {
                Random ram = new Random();
                int isEasy = ram.nextInt(10);
                if (isEasy < 3) {//40%
                    Logger.print(" >>> CARDS >>> EASY CARDS GIVEN >>>");
                    isSequeceGive = true;
                }
            }
        }

        if (totalGameCounter <= c.easygamecounter || isSequeceGive) {


            ArrayList<String> sortcrd = new ArrayList<>();
            sortcrd = getSortedCards(PileCards, true);
            Logger.print("WWWWWWWWWWW::::::::Sorted card>>>> " + sortcrd);
            Random refresh = new Random();
            int colorrandom = 0;
            colorrandom = refresh.nextInt(4) * 13;//<<<<<<<<<<<======0,1,2,3
            Logger.print("WWWWWWWWWWW::::::::colorrandom>>>> " + colorrandom);
            int cardrandom = refresh.nextInt(10);
            //        colorrandom=39;
            //         cardrandom=10;
            int index = cardrandom + colorrandom;

            for (int j = index; j < index + 4; j++) {
                Item_Card iCard1 = getItemCard(sortcrd.get(j));
                card_BottomUser.add(iCard1);
            }

            for (int j = 0; j < card_BottomUser.size(); j++) {
                PileCards.remove(getCardString(card_BottomUser.get(j)));
            }
            //  Logger.print("WWWWWWWWWW::::::::::card_BottomUser>>>> "+card_BottomUser);
            Collections.shuffle(PileCards);


            for (int j = card_BottomUser.size(); j < cardsize; j++) {
                Item_Card iCard1 = getItemCard(PileCards.get(j));
                card_BottomUser.add(iCard1);
            }

            for (int j = 0; j < card_BottomUser.size(); j++) {
                PileCards.remove(getCardString(card_BottomUser.get(j)));
            }
            Logger.print("WWWWWWWWWW::::::::::card_BottomUser>>>> " + card_BottomUser);


            Collections.shuffle(PileCards);

            for (int j = 0; j < cardsize; j++) {
                Item_Card iCard1 = getItemCard(PileCards.get(j));
                card_RightUser.add(iCard1);
                //Item_Card iCard3 = getItemCard(PileCards.get(j + (cardsize * 2)));
                // card_BottomUser.add(iCard3);
                if (numberOfPlayers == 3) {
                    Item_Card iCard2 = getItemCard(PileCards.get(j + cardsize));
                    card_LeftUser.add(iCard2);
                }
            }


        } else {

//            card_LeftUser.add(getItemCard("l-6"));
//            card_LeftUser.add(getItemCard("k-6"));
//            card_LeftUser.add(getItemCard("c-6"));
//            card_LeftUser.add(getItemCard("f-6"));
//            card_LeftUser.add(getItemCard("c-7"));
//            card_LeftUser.add(getItemCard("f-7"));
//            card_LeftUser.add(getItemCard("k-7"));
////
//            card_BottomUser.add(getItemCard("k-4"));
//            card_BottomUser.add(getItemCard("c-4"));
//            card_BottomUser.add(getItemCard("f-4"));
//            card_BottomUser.add(getItemCard("l-2"));
//            card_BottomUser.add(getItemCard("l-3"));
//            card_BottomUser.add(getItemCard("l-4"));
//            card_BottomUser.add(getItemCard("l-1"));
//
//                card_LeftUser.add(getItemCard("l-7"));
//                card_LeftUser.add(getItemCard("c-7"));
//                card_LeftUser.add(getItemCard("f-7"));
//                card_LeftUser.add(getItemCard("l-8"));
//                card_LeftUser.add(getItemCard("l-9"));
//                card_LeftUser.add(getItemCard("l-10"));
//                card_LeftUser.add(getItemCard("l-1"));

//            if (c.gameMode == Parameters.Hard_Mode) {
//
//
//                ArrayList<String> sortcrd = new ArrayList<>();
//                sortcrd = getSortedCards(PileCards, true);
//                Logger.print("WWWWWWWWWWW::::::::Sorted card>>>> " + sortcrd);
//                Random refresh = new Random();
//                int colorrandom = 0;
//                colorrandom = refresh.nextInt(4) * 13;//<<<<<<<<<<<======0,1,2,3
//                Logger.print("WWWWWWWWWWW::::::::colorrandom>>>> " + colorrandom);
//                int cardrandom = refresh.nextInt(10);
//                //        colorrandom=39;
//                //         cardrandom=10;
//                int index = cardrandom + colorrandom;
//
//                for (int j = index; j < index + 4; j++) {
//                    Item_Card iCard1 = getItemCard(sortcrd.get(j));
//                    if (numberOfPlayers == 3) {
//                        card_LeftUser.add(iCard1);
//                    }else {
//                        card_RightUser.add(iCard1);
//                    }
//                }
//                for (int j = 0; j < card_RightUser.size(); j++) {
//                    if (numberOfPlayers == 3) {
//                        PileCards.remove(getCardString(card_LeftUser.get(j)));
//                    }else {
//                        PileCards.remove(getCardString(card_RightUser.get(j)));
//                    }
//                }
//                //  Logger.print("WWWWWWWWWW::::::::::card_BottomUser>>>> "+card_BottomUser);
//                Collections.shuffle(PileCards);
//                for (int j = card_RightUser.size(); j < cardsize; j++) {
//                    Item_Card iCard1 = getItemCard(PileCards.get(j));
//                    if (numberOfPlayers == 3) {
//                        card_LeftUser.add(iCard1);
//                    }else {
//                        card_RightUser.add(iCard1);
//                    }
////                        card_RightUser.add(iCard1);
//                }
//                for (int j = 0; j < card_RightUser.size(); j++) {
//                    if (numberOfPlayers == 3) {
//                        PileCards.remove(getCardString(card_LeftUser.get(j)));
//                    }else {
//                        PileCards.remove(getCardString(card_RightUser.get(j)));
//                    }
//                }
//                Logger.print("WWWWWWWWWW::::::::::card_BottomUser>>>> " + card_BottomUser);
//
//
//                Collections.shuffle(PileCards);
//
//                for (int j = 0; j < cardsize; j++) {
//                    Item_Card iCard1 = getItemCard(PileCards.get(j));
//                    card_BottomUser.add(iCard1);
//                    //Item_Card iCard3 = getItemCard(PileCards.get(j + (cardsize * 2)));
//                    // card_BottomUser.add(iCard3);
//                    if (numberOfPlayers == 3) {
//                        Item_Card iCard2 = getItemCard(PileCards.get(j + cardsize));
//                        card_RightUser.add(iCard2);
//                    }
//                }
//
//            } else {
            Collections.shuffle(PileCards);
            Collections.shuffle(PileCards);
            Collections.shuffle(PileCards);
            for (int j = 0; j < cardsize; j++) {
                Item_Card iCard1 = getItemCard(PileCards.get(j));
                card_RightUser.add(iCard1);
                Item_Card iCard3 = getItemCard(PileCards.get(j + (cardsize * 2)));
                card_BottomUser.add(iCard3);
                if (numberOfPlayers == 3) {
                    Item_Card iCard2 = getItemCard(PileCards.get(j + cardsize));
                    card_LeftUser.add(iCard2);
                }
            }
//            }
        }
        Logger.print(">> CARDS >>> pilecards => " + PileCards.toString());
        Logger.print(">> CARDS >>> card_LeftUser => " + card_LeftUser.toString());
        Logger.print(">> CARDS >>> card_BottomUser => " + card_BottomUser.toString());
        Logger.print(">> CARDS >>> card_RightUser => " + card_RightUser.toString());
//            card_BottomUser = new ArrayList<>();
//
//            //      "f-11","k-11","l-11"
//            //     "c-11", "c-12", "c-13"
//
//
//            card_BottomUser.add(getItemCard("f-1"));
//            card_BottomUser.add(getItemCard("c-1"));
//            card_BottomUser.add(getItemCard("l-1"));
//            card_BottomUser.add(getItemCard("c-4"));
//            card_BottomUser.add(getItemCard("c-3"));
//            card_BottomUser.add(getItemCard("c-2"));
//            card_BottomUser.add(getItemCard("l-11"));
//            card_BottomUser.add(getItemCard("l-9"));
//            card_BottomUser.add(getItemCard("l-8"));
//            card_BottomUser.add(getItemCard("l-10"));
//            //card_BottomUser.add(getItemCard("c-5"));
//            //  card_BottomUser.add(getItemCard("c-2"));
//            //  card_BottomUser.add(getItemCard("k-6"));
        Logger.print(">>> CARDS Before >>>> " + card_BottomUser.size() + " >>> " + card_BottomUser);
        card_BottomUser = new ArrayList<>(Sort(card_BottomUser, BottomSeatIndex));
        card_RightUser = new ArrayList<>(Sort(card_RightUser, RightSeatIndex));
        Logger.print(">>> CARDS After >>>> " + card_BottomUser.size() + " >>> " + card_BottomUser);
        redrawRobotCard(RightSeatIndex);
        if (numberOfPlayers == 3) {
            card_LeftUser = new ArrayList<>(Sort(card_LeftUser, LeftSeatIndex));
            redrawRobotCard(LeftSeatIndex);
            for (int j = 0; j < card_LeftUser.size(); j++) {
                PileCards.remove(getCardString(card_LeftUser.get(j)));
            }
        }
        for (int j = 0; j < card_RightUser.size(); j++) {
            PileCards.remove(getCardString(card_RightUser.get(j)));
        }
        for (int j = 0; j < card_BottomUser.size(); j++) {
            PileCards.remove(getCardString(card_BottomUser.get(j)));
        }
        Logger.print("pilecards => " + PileCards.toString());
        int index = Arrays.asList(cardString).indexOf(PileCards.get(0));
        ivCloseCard.setVisibility(View.VISIBLE);
        ivCloseCard.setImageResource(CardDefault[index]);
        openBunchCard.add(getItemCard(PileCards.get(0)));
        PileCards.remove(0);
        c.isDouble = false;
        c.multi = 1;
//            c.isDouble = true;
//            c.multi = 2;
        // is Oklahoma Mode???
        if (c.gameType == 3) {
            required_knock = getKnockValue(openBunchCard.get(0));
            if (openBunchCard.get(0).getCardColor() == 1) {
                c.isDouble = true;
                c.multi = 2;
                iv_2X.setVisibility(View.VISIBLE);
            }
        } else {
            required_knock = 10;
        }
        if (HelpText.getVisibility() == View.VISIBLE
                && DealCardToSeat[BottomSeatIndex]) {
            HelpText.setVisibility(View.GONE);
        }
        closeBunchCard.clear();
        for (int i = PileCards.size() - 1; i >= 0; i--) {
            closeBunchCard.add(getItemCard(PileCards.get(i)));
        }
        // ============Add Comment on this Section ====================
//            for (int i = 4; i >= 0; i--) {
//                closeBunchCard.add(getItemCard(PileCards.get(i)));
//            }
        //=========================================================
        Logger.print("openBunchCards  =>>>>>>>> " + openBunchCard.toString());
        Logger.print("closeBunchCards =>>>>>>>> " + closeBunchCard.toString());
        Logger.print("Knock Value     =>>>>>>>> " + required_knock);
//            updateCardinPrefrecesBottom();
//            updateCardinPrefrecesRight();
//            updateCardinPrefrecesLeft();
//            updateCardinPrefrecesBunchOpen();
//            updateCardinPrefrecesBunchClose();
        final int totalCardToDistribute = cardsize * numberOfPlayers;
        if (GameisOn()/*&& !ispause*/) {
            distributeCardsAnimation(BottomSeatIndex, totalCardToDistribute);
        } else {
            ivCloseDeck.setVisibility(View.VISIBLE);
            Logger.print("CARD COUNTER >>> 18");

            tvCardCounter.setVisibility(View.VISIBLE);
            PileCardCounter = PileCardCounter - totalCardToDistribute - 1;
            DealingAnimationCompleted = true;
            tvCardCounter.setText("" + PileCardCounter);
            //frmtable.setRotationX(45);
        }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private int getKnockValue(Item_Card item_card) {
        int knockvalue = item_card.getCardValue();
        if (knockvalue > 10) {
            return 10;
        } else {
            return knockvalue;
        }
    }

    private void ThrowCardOnDeckProcess(String data) {
        // TODO Auto-generated method stub
        Logger.print("ThrowCardOnDeckProcess => " + data);
        JSONObject jObject;
        try {
            jObject = new JSONObject(data);
            int seatIndex = jObject.getJSONObject(Parameters.data).getInt(
                    Parameters.SeatIndex);
            String cardStr = jObject.getJSONObject(Parameters.data).getString(
                    Parameters.Card);
            Item_Card card = getItemCard(cardStr);
            openBunchCard.add(card);
//            if(GameisOn()){
//
//
//                Music_Manager.throwcard();
//            }
            if (closeBunchCard.size() == 0) {
                GAME_OVER = true;
                messageHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        declareDraw();
                    }
                }, 1500);
            }
            if (!GAME_OVER) {
                messageHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        passTurn();
                    }
                }, 800);
            }
            if (!isUserTurn(seatIndex)) {
                isCardThrown = true;
                if (seatIndex == RightSeatIndex) {
                    removeElement(card_RightUser, card);
                    card_RightUser = new ArrayList<>(Sort(card_RightUser, RightSeatIndex));
                    redrawRobotCard(RightSeatIndex);
                    if (GameisOn()) {
                        AnimateUserThrowCardOnOpenDeck(RightSeatIndex,
                                getCardString(card));
                    }
                } else if (seatIndex == LeftSeatIndex) {
                    removeElement(card_LeftUser, card);
                    card_LeftUser = new ArrayList<>(Sort(card_LeftUser, LeftSeatIndex));
                    redrawRobotCard(LeftSeatIndex);
                    if (GameisOn())
                        AnimateUserThrowCardOnOpenDeck(LeftSeatIndex,
                                getCardString(card));
                }
                if (!GameisOn()) {
                    isCardThrown = false;
                }
            } else {
                isCardThrown = false;
                hasTakenCard = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // public Callback mCallBack =
    private void Gin_process() {
        // TODO Auto-generated method stub
        String strthc = null;
        if (BottomDeadWood <= required_knock) {
            JSONObject jObject = new JSONObject();
            try {
                jObject.put(Parameters.SeatIndex, BottomSeatIndex);
                jObject.put(Parameters.opencard, strthc);
                JSONObject jObject1 = new JSONObject();
                jObject1.put(Parameters.data, jObject);
                GinResponseProcess(jObject1.toString(), card_BottomUser);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void Knock_process() {
        // TODO Auto-generated method stub
        JSONArray array_card = new JSONArray();
        for (int i = 0; i < card_BottomUser.size(); i++) {
            if (!card_BottomUser.get(i).getIsValidGroup()) {
                array_card.put(getCardString((card_BottomUser.get(i))));
            }
        }
        ThrowRemainingCard(array_card);
        JSONObject jobject = new JSONObject();
        JSONObject setSeq = getSetSequence(false, card_BottomUser);
        try {
            jobject.put(Parameters.Cards, setSeq);
            jobject.put("nCd", array_card);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONObject jObject = new JSONObject();
        try {
            jObject.put(Parameters.SeatIndex, BottomSeatIndex);
            JSONObject jObject1 = new JSONObject();
            jObject1.put(Parameters.data, jObject);
            jObject1.put(Parameters.Card, getHighDeadWoodCard(card_BottomUser));
            KnockResponseProcess(jObject1.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> getSortedCards(ArrayList<String> data,
                                             boolean isWithColor) {
        ArrayList<String> returnData = new ArrayList<>();
        if (isWithColor) {
            Collections.sort(data);
            // ///////////////////////////////////// C
            // /////////////////////////////////////////
            ArrayList<Integer> cData = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).startsWith("c")) {
                    String c = data.get(i);
                    c = c.replace("-", "");
                    c = c.replace("c", "");
                    c = c.replace("__Blank", "");
                    cData.add(Integer.valueOf(c));
                }
            }
            Collections.sort(cData);
            // Collections.reverse(cData);
            for (int i = 0; i < cData.size(); i++) {
                String value = String.valueOf(cData.get(i));
//
//                if (value.length() == 3) {
//                    value = value.substring(0, 2) + "-" + value.charAt(2);
//                } else {
//                    value = value.charAt(0) + "-" + value.charAt(1);
//                }
                returnData.add("c-" + value);
            }
            // ///////////////////////////////////// F
            // /////////////////////////////////////////
            cData = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).startsWith("f")) {
                    String c = data.get(i);
                    c = c.replace("-", "");
                    c = c.replace("f", "");
                    c = c.replace("__Blank", "");
                    cData.add(Integer.valueOf(c));
                }
            }
            Collections.sort(cData);
            // Collections.reverse(cData);
            for (int i = 0; i < cData.size(); i++) {
                String value = String.valueOf(cData.get(i));
//
//                if (value.length() == 3) {
//                    value = value.substring(0, 2) + "-" + value.charAt(2);
//                } else {
//                    value = value.charAt(0) + "-" + value.charAt(1);
//                }
                returnData.add("f-" + value);
            }
            // //////////////////////////////////// J
            // //////////////////////////////////////////
//            for (int i = 0; i < data.size(); i++) {
//                if (data.get(i).contains("j")) {
//                    returnData.add(data.get(i));
//                }
//            }
            // ///////////////////////////////////// K
            // /////////////////////////////////////////
            cData = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).startsWith("k")) {
                    String c = data.get(i);
                    c = c.replace("-", "");
                    c = c.replace("k", "");
                    c = c.replace("__Blank", "");
                    cData.add(Integer.valueOf(c));
                }
            }
            Collections.sort(cData);
            // Collections.reverse(cData);
            for (int i = 0; i < cData.size(); i++) {
                String value = String.valueOf(cData.get(i));
//                if (value.length() == 3) {
//                    value = value.substring(0, 2) + "-" + value.charAt(2);
//                } else {
//                    value = value.charAt(0) + "-" + value.charAt(1);
//                }
                returnData.add("k-" + value);
            }
            // ///////////////////////////////////// L
            // /////////////////////////////////////////
            cData = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).startsWith("l")) {
                    String c = data.get(i);
                    c = c.replace("-", "");
                    c = c.replace("l", "");
                    c = c.replace("__Blank", "");
                    cData.add(Integer.valueOf(c));
                }
            }
            Collections.sort(cData);
            for (int i = 0; i < cData.size(); i++) {
                String value = String.valueOf(cData.get(i));
//                if (value.length() == 3) {
//                    value = value.substring(0, 2) + "-" + value.charAt(2);
//                } else {
//                    value = value.charAt(0) + "-" + value.charAt(1);
//                }
                returnData.add("l-" + value);
            }
        } else {
            Collections.sort(data, new NameSorter());
            returnData = data;
        }
        return returnData;
    }

    private JSONObject getSetSequence(boolean isGin, ArrayList<Item_Card> userCards) {
        JSONArray validCards = new JSONArray();
        JSONArray invalidCards = new JSONArray();
        ArrayList<ArrayList<Integer>> ar = new ArrayList<>();
        boolean isInSequence, isInSet;
        for (int i = 0; i < userCards.size(); i++) {
            int num = userCards.get(i).getGroupNumber();
            ArrayList<Integer> array = new ArrayList<>();
            for (int j = i; j < userCards.size(); j++) {
                if (num == userCards.get(j).getGroupNumber()) {
                    array.add(j);
                    i = j;
                    Logger.print("getSeqSetInvalidCards called" + i + " " + j);
                } else {
                    Logger.print("BREAK");
                    break;
                }
            }
            ar.add(array);
        }
        for (int i = 0; i < ar.size(); i++) {
            isInSet = false;
            isInSequence = checkForSequenceNew(ar, userCards, i);
            if (!isInSequence) {
                isInSet = checkForSet(ar, userCards, i);
            }
            JSONArray tempCard = getCards(ar, userCards, i);
            if (isInSequence || isInSet) {
                validCards.put(tempCard);
            } else {
                for (int in = 0; in < tempCard.length(); in++) {
                    try {
                        invalidCards.put(tempCard.get(in));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        JSONObject jObj = new JSONObject();
        try {
            jObj.put("valid", validCards);
            if (!isGin) {
                jObj.put("invalid", invalidCards);
            } else {
                jObj.put("invalid", new JSONArray());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;
    }

    private JSONArray getCards(ArrayList<ArrayList<Integer>> ar, ArrayList<Item_Card> card_BottomUser, int k) {
        JSONArray jSet = new JSONArray();
        for (int i = 0; i < ar.get(k).size(); i++) {
            jSet.put(getCardString(card_BottomUser.get(ar.get(k).get(i))));
        }
        return jSet;
    }

    private void ThrowRemainingCard(JSONArray array_card) {
        // TODO Auto-generated method stub
        try {
            ArrayList<Item_Card> Cards = new ArrayList<>();
            for (int i = 0; i < array_card.length(); i++) {
                Cards.add(getItemCard(array_card.getString(i)));
            }
            SortSimpleSequenceWise(Cards);
            Logger.print("SortSimpleSequenceWise called " + Cards.toString());
            if (PlayScreen2.handler != null) {
                Message msg = new Message();
                msg.what = ResponseCodes.HideMessageResp;
                PlayScreen2.handler.sendMessage(msg);
            }
            if (Cards.size() > 0) {
                final String card = getCardString(Cards.get(Cards.size() - 1));
                int index = 0;
                for (int i = 0; i < card_BottomUser.size(); i++) {
                    if (getCardString(card_BottomUser.get(i)).equalsIgnoreCase(
                            card)) {
                        index = i;
                        break;
                    }
                }
                removeElement(card_BottomUser, getItemCard(card));
                openBunchCard.add(getItemCard(card));
                Logger.print("Card Bottomuser KNOCK=>"
                        + card_BottomUser.toString());
                ivMyCards[index].setRotation(0);
                ivMyCards[index].getLocationOnScreen(selectedCardXY);
                ivCloseCard.getLocationOnScreen(closeCardLocationXY);
                int x1 = selectedCardXY[0];
                int y1 = selectedCardXY[1];
                int x = closeCardLocationXY[0];
                int y = closeCardLocationXY[1];
                animationHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int index1;
                        index1 = Arrays.asList(cardString).indexOf(card);
                        ivCloseCard.setImageResource(CardDefault[index1]);
                        ivCloseCard.setTag(cardString[index1]);
                        Logger.print("ThrowCardOnDeckProcess called XXXX Bottom"
                                + card_BottomUser.toString()
                                + " "
                                + selectedCardToRemove);
                        /*card_BottomUser = new ArrayList<Item_Card>(Sort(
                                card_BottomUser, BottomSeatIndex));*/
                        reDrawUserCards("");
                        setClickable(ResponseCodes.ThrowCardOnDeckResp);
                    }
                }, 500);
                TranslateAnimation tAnimation = new TranslateAnimation(0, x
                        - x1, 0, y - y1);
                tAnimation.setDuration(500);
                tAnimation.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // TODO Auto-generated method stub
//                        int index1;
//
//                        index1 = Arrays.asList(cardString).indexOf(card);
//                        ivCloseCard.setImageResource(CardDefault[index1]);
//                        ivCloseCard.setTag(cardString[index1]);
//
//                        Logger.print("ThrowCardOnDeckProcess called XXXX Bottom"
//                                + card_BottomUser.toString()
//                                + " "
//                                + selectedCardToRemove);
//
//                        /*card_BottomUser = new ArrayList<Item_Card>(Sort(
//                                card_BottomUser, BottomSeatIndex));*/
//                        reDrawUserCards("");
//                        setClickable(ResponseCodes.ThrowCardOnDeckResp);
                    }
                });
                ivMyCards[index].startAnimation(tAnimation);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private Item_Card getItemCard(String string) {
        // TODO Auto-generated method stub
        Item_Card ic = new Item_Card();
        try {
            int CardColor = c.getCardSuit(String.valueOf(string.charAt(0)));
            ic.setCardColor(CardColor);
            int CardValue = Integer.parseInt(string.substring(2, string.length()));
            //CardValue = CardValue;
            ic.setCardValue(CardValue);
            //Logger.print("CArdValue => " + CardValue + " CardColor => " + CardColor);
            // ic.setCardSprite(new
            // Sprite(spriteManager.mCards[CardColor][CardValue]));
            return ic;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ic;
        }
    }

    private String getCardString(Item_Card object) {
        // TODO Auto-generated method stub
        Logger.print("Card for conrt to string => " + object.toString());
        String card = "";
        if (object.getCardColor() == c.Falli) {
            card = "f";
        } else if (object.getCardColor() == c.Chirkut) {
            card = "c";
        } else if (object.getCardColor() == c.Laal) {
            card = "l";
        } else if (object.getCardColor() == c.Kaali) {
            card = "k";
        }
        card = card + "-" + (object.getCardValue());
        return card;
    }

    private void AnimatePushCardToPile(final int seatIndex, final int size) {
        // TODO Auto-generated method stub
        final ImageView[] ivPush = new ImageView[5];
        LayoutParams frmCards;
        int[] userCard = new int[2];
        int[] pileCard = new int[2];
        int x = 0, y = 0, x1, y1;
        if (seatIndex == RightSeatIndex) {
            ivUserTwoCard.getLocationOnScreen(userCard);
            x = userCard[0];
            y = userCard[1];
        } else if (seatIndex == LeftSeatIndex) {
            ivUserOneCard.getLocationOnScreen(userCard);
            x = userCard[0];
            y = userCard[1];
        }
        ivCloseDeck.getLocationOnScreen(pileCard);
        x1 = pileCard[0];
        y1 = pileCard[1];
        Logger.print("Start PUsh ANimation => " + x + " " + y + " " + x1 + " "
                + y1);
        for (int i = 0; i < 5; i++) {
            ivPush[i] = new ImageView(getApplicationContext());
            ivPush[i].setBackgroundResource(R.drawable.card);
            frmCards = new LayoutParams(getwidth(105),
                    getheight(135));
            frmCards.topMargin = y;
            frmCards.leftMargin = x;
            ivPush[i].setLayoutParams(frmCards);
            if (frmMainLayout != null)
                frmMainLayout.addView(ivPush[i]);
        }
        // ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.3f, 1.0f, 1.3f,
        // Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
        // 0.5f);
        // scaleAnim.setDuration(1000);
        // scaleAnim.setFillAfter(false);
        final TranslateAnimation[] tAnimToValue = new TranslateAnimation[5];
        int startOffset = 0;
        for (int i = 0; i < tAnimToValue.length; i++) {
            tAnimToValue[i] = new TranslateAnimation(0, x1 - x, 0, y1 - y);
            tAnimToValue[i].setDuration(500);
            tAnimToValue[i].setStartOffset(startOffset);
            startOffset += 100;
        }
        for (int i = 0; i < tAnimToValue.length; i++) {
            final int k = i;
            ivPush[i].startAnimation(tAnimToValue[i]);
            tAnimToValue[i].setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // TODO Auto-generated method stub
                    ivPush[k].setVisibility(View.GONE);
                    Logger.print("SIZZZZZZZZZZZZZZZZEEEEEEEEEEEEEEEEEE => " + size);
                    if (k == 4) {
                        PileCardCounter = PileCardCounter + size;
                        Logger.print("CARD COUNTER >>> 1");
                        tvCardCounter.setText("" + PileCardCounter);
                    }
                }
            });
        }
        // AnimationSet animArr[] = new AnimationSet[5];
        //
        // for (int j = 0; j < 5; j++) {
        // animArr[j] = new AnimationSet(true);
        // animArr[j].addAnimation(tPushAnim);
        // animArr[j].addAnimation(scaleAnim);
        // animArr[j].setFillAfter(false);
        // animArr[j].setDuration(1000);
        // animArr[j].setStartOffset(j * 500);
        // }
        //
        // for (int k = 0; k < 5; k++) {
        // ivPush[k].startAnimation(animArr[k]);
        // final int l = k;
        // animArr[k].setAnimationListener(new AnimationListener() {
        //
        // @Override
        // public void onAnimationStart(Animation animation) {
        // // TODO Auto-generated method stub
        // Logger.print("Animation Start");
        // }
        //
        // @Override
        // public void onAnimationRepeat(Animation animation) {
        // // TODO Auto-generated method stub
        //
        // }
        //
        // @Override
        // public void onAnimationEnd(Animation animation) {
        // // TODO Auto-generated method stub
        // Logger.print("Animation End");
        //
        // ivPush[l].setBackgroundResource(0);
        // if (ivPush[l].getAnimation() != null) {
        // ivPush[l].clearAnimation();
        // }
        // ivPush[l].setVisibility(View.GONE);
        //
        // }
        // });
        //
        // }
    }

    private void removeUserData(int seatIndex) {
        // TODO Auto-generated method stub
        if (seatIndex == RightSeatIndex) {
            // ivProfileImageTwo.setBorderColor(Color.parseColor("#00000000"));
            Logger.print("Right User Leave");
            ivGiftIconTwo.setImageResource(R.drawable.gift);
            ivGiftIconTwo.setVisibility(View.INVISIBLE);
            tvScoreTwo.setText("0");
            tvScoreTwo.setVisibility(View.INVISIBLE);
            RightUserVipTag.setVisibility(View.INVISIBLE);
            ivUserTwoCard.setVisibility(View.INVISIBLE);
            ivUserNameTwo.setText("Invite");
            // ivUserNameTwo.setVisibility(View.INVISIBLE);
            if (card_RightUser.size() > 0) {
                card_RightUser.clear();
            }
            if (RightUserRing.getAnimation() != null)
                RightUserRing.clearAnimation();
            RightUserRing.setVisibility(View.INVISIBLE);
           /* ivProfileImageTwo.setImageResource(0);
            ivProfileImageTwo.setBackgroundResource(0);
            ivProfileImageTwo.setImageResource(R.drawable.profile_contaibnner);
            ivProfileImageTwo.setBackgroundResource(R.drawable.profile_contaibnner);*/
        } else if (seatIndex == LeftSeatIndex) {
            // ivProfileImage.setBorderColor(Color.parseColor("#00000000"));
            Logger.print("Left User Leave");
            ivGiftIcon.setImageResource(R.drawable.gift);
            ivGiftIcon.setVisibility(View.INVISIBLE);
            tvScore.setText("0");
            tvScore.setVisibility(View.INVISIBLE);
            LeftUserVipTag.setVisibility(View.INVISIBLE);
            ivUserOneCard.setVisibility(View.INVISIBLE);
            ivUserNameOne.setText("Invite");
            // ivUserNameOne.setVisibility(View.INVISIBLE);
            if (card_LeftUser.size() > 0) {
                card_LeftUser.clear();
            }
            if (LeftUserRing.getAnimation() != null)
                LeftUserRing.clearAnimation();
            LeftUserRing.setVisibility(View.INVISIBLE);
            /*ivProfileImage.setImageResource(0);
            ivProfileImage.setBackgroundResource(0);
            ivProfileImage.setImageResource(R.drawable.profile_contaibnner);
            ivProfileImage.setBackgroundResource(R.drawable.profile_contaibnner);*/
        }
    }

    private void WinnerDeclareProcess(JSONArray jsonArray) throws Exception {
        // TODO Auto-generated method stub
        UserWinner = new boolean[3];
        for (int i = 0; i < jsonArray.length(); i++) {
            if (jsonArray.getJSONObject(i).has(Parameters.winner)
                    && jsonArray.getJSONObject(i).getBoolean(Parameters.winner)) {
                UserWinner[jsonArray.getJSONObject(i).getInt(
                        Parameters.SeatIndex)] = true;
                hasTakenCard = false;
            }
        }
        if (UserWinner[BottomSeatIndex]) {
            sqLiteManager.incrementGameWin(PreferenceManager.get_id());
            if (c.gameType == 0) {
                PreferenceManager.SetGinWon(PreferenceManager.GetGinWon() + 1);
                PreferenceManager.setContinuesWin(PreferenceManager.getContinuesWin() + 1);
            } else if (c.gameType == 1) {
                PreferenceManager.SetStraightWon(PreferenceManager.GetStraightWon() + 1);
                PreferenceManager.SetQuestStraightCount(PreferenceManager.GetQuestStraightCount() + 1);
                PreferenceManager.setContinuesWin(PreferenceManager.getContinuesWin() + 1);
            } else if (c.gameType == 2) {
                PreferenceManager.SetHollyWoodWon(PreferenceManager.GetHollyWoodWon() + 1);
                PreferenceManager.setContinuesWin(PreferenceManager.getContinuesWin() + 1);
//                PreferenceManager.SetQuestStraightCount(PreferenceManager.GetQuestStraightCount() + 1);
            } else if (c.gameType == 3) {
                PreferenceManager.SetOklahomaWon(PreferenceManager.GetOklahomaWon() + 1);
                PreferenceManager.SetQuestOklahomaCount(PreferenceManager.GetQuestOklahomaCount() + 1);
                PreferenceManager.setContinuesWin(PreferenceManager.getContinuesWin() + 1);
            }
            sqLiteManager.updateBiggestWin(PreferenceManager.get_id(), BOOT_VALUE * numberOfPlayers);
        } else {
            if (c.gameType == 0) {
                PreferenceManager.setContinuesWin(0);
            } else if (c.gameType == 1) {
                PreferenceManager.setContinuesWin(0);
            } else if (c.gameType == 2) {
                PreferenceManager.setContinuesWin(0);
            } else if (c.gameType == 3) {
                PreferenceManager.setContinuesWin(0);
            }
        }
        Dashboard.savedGamesUpdate();
    }

    private String getProUserName(String name) {
        try {
            String names[] = name.split(" ");
            if (names.length <= 1)
                return name;
            else
                return names[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    private void GetFindTableAndJoinProcess(String data) {
        // TODO Auto-generated method stub
        JSONObject jObject;
        try {
            jObject = new JSONObject(data);
            if (!jObject.getBoolean(Parameters.Flag)) {
                ShowErrorPopup(jObject.getString(Parameters.msg), true,
                        "Message");
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            BonustimerPause();
            finish();
            overridePendingTransition(0, android.R.anim.slide_out_right);
        }
    }

    private void ShowErrorPopup(String Message, final boolean haveToFinish,
                                String dialogTitle) {
        // TODO Auto-generated method stub
        LinearLayout.LayoutParams lin;
        if (ConfirmationDialog != null) {
            if (ConfirmationDialog.isShowing()) {
                ConfirmationDialog.dismiss();
            }
            ConfirmationDialog = null;
        }
        ConfirmationDialog = new Dialog(PlayScreen2.this,
                R.style.Theme_Transparent);
        ConfirmationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ConfirmationDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        ConfirmationDialog.setContentView(R.layout.dialog);
        TextView title = ConfirmationDialog
                .findViewById(R.id.tvTitle);
        title.setPadding(getwidth(20), getheight(20), getwidth(20),
                getheight(20));
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
        title.setTypeface(tf);
        title.setText(dialogTitle);
        lin = (LinearLayout.LayoutParams) ConfirmationDialog.findViewById(
                R.id.line).getLayoutParams();
        lin.height = getheight(4);
        lin.width = getwidth(600);
        TextView message = ConfirmationDialog
                .findViewById(R.id.tvMessage);
        message.setTypeface(tf);
        message.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));
        Button btnOne = ConfirmationDialog.findViewById(R.id.button1);
        btnOne.setTypeface(tf);
        btnOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnOne.setText("Ok");
        Button btnTwo = ConfirmationDialog.findViewById(R.id.button2);
        btnTwo.setVisibility(View.GONE);
        lin = (LinearLayout.LayoutParams) ConfirmationDialog.findViewById(
                R.id.button1).getLayoutParams();
        lin.height = getheight(60);
        lin.width = getwidth(120);
        lin = (LinearLayout.LayoutParams) ConfirmationDialog.findViewById(
                R.id.tvMessage).getLayoutParams();
        lin.topMargin = getheight(30);
        lin = (LinearLayout.LayoutParams) ConfirmationDialog.findViewById(
                R.id.llButtons).getLayoutParams();
        lin.topMargin = getheight(30);
        message.setText(Message);

        btnOne.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ConfirmationDialog.dismiss();
                if (haveToFinish) {
                    BonustimerPause();
                    finish();
                    overridePendingTransition(0, android.R.anim.slide_out_right);
                }
                // }
            }
        });
        if (!isFinishing())
            ConfirmationDialog.show();
    }


    private void UserTurnStartedProcess(String data) {
        // TODO Auto-generated method stub
        isUserTurn = false;
        ivCloseCard.setColorFilter(Color.argb(0, 0, 0, 0));
        tvTotalBetValue.setVisibility(View.VISIBLE);
        tvTotalBetValue.setText("" + c.getNumberFormatedValue(BOOT_VALUE * numberOfPlayers));
        isCardTouchable = true;
        JSONObject UserTurnStartedObject;
        disableAllOtherTurn();
        reDrawUserCards("");
        isSortButtonClickable = true;
        btnKnock.setVisibility(View.GONE);
        if (RightUserRing.getAnimation() != null) {
            RightUserRing.clearAnimation();
        }
        if (LeftUserRing.getAnimation() != null) {
            LeftUserRing.clearAnimation();
        }
        if (BottomUserRing.getAnimation() != null) {
            BottomUserRing.clearAnimation();
        }
        if (UserTimer != null) {
            UserTimer.cancel();
            UserTimer = null;
        }
        LeftUserRing.setVisibility(View.INVISIBLE);
        RightUserRing.setVisibility(View.INVISIBLE);
        BottomUserRing.setVisibility(View.INVISIBLE);
        currentProgress = 1;
        try {
            UserTurnStartedObject = new JSONObject(data);
            totalTime = (float) c.SLOW_TABLE_TIMER * 1000;
            int timerSecond = c.SLOW_TABLE_TIMER;
            if (UserTurnStartedObject.has("timer")) {
                timerSecond = UserTurnStartedObject.getInt("timer");
                //currentProgress = (float) timeofuser * 1000;
                Logger.print("Timerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr => " + currentProgress + " " + c.SLOW_TABLE_TIMER + " " + UserTurnStartedObject.toString());
            }
            final int seatIndex = UserTurnStartedObject
                    .getJSONObject(Parameters.data).getInt(Parameters.NextTurn);
            String pileCard = UserTurnStartedObject.getJSONObject(
                    Parameters.data).getString(Parameters.PileCard);
            dontAllowToClickOnCloseDeck = false;
            if (UserTurnStartedObject.getJSONObject(Parameters.data)
                    .has("type")) {
                String passType = UserTurnStartedObject.getJSONObject(
                        Parameters.data).getString("type");
                if (passType.contentEquals("PST")) {
                    isFirstTurn = true;
                    int prevTurn = UserTurnStartedObject.getJSONObject(
                            Parameters.data).getInt(Parameters.PrevTurn);
                    if (GameisOn())
                        AnimateSkipText(prevTurn);
                }
                Logger.print("Timerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr => 1 " + dontAllowToClickOnCloseDeck + " " + UserTurnStartedObject.toString() + " " + passType);
                if (passType.contentEquals("OPST")) {
                    dontAllowToClickOnCloseDeck = true;
                    Logger.print("Timerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr => 2 " + dontAllowToClickOnCloseDeck + " " + UserTurnStartedObject.toString() + " " + passType);
                    if (UserTurnStartedObject.getJSONObject(
                            Parameters.data).has(Parameters.PrevTurn)) {
                        int prevTurn = UserTurnStartedObject.getJSONObject(
                                Parameters.data).getInt(Parameters.PrevTurn);
                        if (GameisOn())
                            AnimateSkipText(prevTurn);
                    }
                }
            }
            Logger.print("Timerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr =>  3 " + dontAllowToClickOnCloseDeck + " " + UserTurnStartedObject.toString());
            Logger.print("MSG Hide Message");
            NotificationText.setVisibility(View.GONE);
            NotificationText.setText("");
            if (seatIndex == LeftSeatIndex) {
                LeftUserRing.setVisibility(View.VISIBLE);
                LeftUserRing.startAnimation(animUserRing);
                // animateRingScaling(seatIndex, LeftUserRing);
            } else if (seatIndex == RightSeatIndex) {
                RightUserRing.setVisibility(View.VISIBLE);
                RightUserRing.startAnimation(animUserRing);
                // animateRingScaling(seatIndex, RightUserRing);
            } else if (seatIndex == BottomSeatIndex) {
                // musicManager.userTurnCome();
                // IsMyTurnEventComing = true;
                isUserTurn = true;
                cardTouch1 = true;
                BottomUserRing.setVisibility(View.VISIBLE);
                setClickable(ResponseCodes.UserTurnStartedResp);
                ivHandOpenCard.bringToFront();
                ivHandCloseDeck.bringToFront();
                if (!isTutorial) {
                    if (isFirstTurn) {
                        ivHandOpenCard.setVisibility(View.VISIBLE);
                        ivHandOpenCard.startAnimation(handAnimation);
                        if (showControls) {
                            btnFaceUp.setVisibility(View.VISIBLE);
                            btnDrawNew.setVisibility(View.GONE);
                            btnDiscard.setVisibility(View.GONE);
                        }
                    } else {
                        if (dontAllowToClickOnCloseDeck) {
                            ivHandCloseDeck.setVisibility(View.VISIBLE);
                            ivHandCloseDeck.startAnimation(handAnimation);
                            ivCloseCard.setClickable(false);
                            ivCloseCard.setColorFilter(Color.argb(100, 128, 128, 125));
                        } else {
                            ivHandOpenCard.setVisibility(View.VISIBLE);
                            ivHandOpenCard.startAnimation(handAnimation);
                            ivHandCloseDeck.setVisibility(View.VISIBLE);
                            ivHandCloseDeck.startAnimation(handAnimation);
                        }
                        if (showControls) {
                            if (dontAllowToClickOnCloseDeck) {
                                btnFaceUp.setVisibility(View.GONE);
                                btnDrawNew.setVisibility(View.VISIBLE);
                                btnDiscard.setVisibility(View.GONE);
                            } else {
                                btnFaceUp.setVisibility(View.VISIBLE);
                                btnDrawNew.setVisibility(View.VISIBLE);
                                btnDiscard.setVisibility(View.GONE);
                            }
                        }
                    }
                }
                BottomUserRing.startAnimation(animUserRing);
                // animateRingScaling(seatIndex, BottomUserRing);
                // BottomUserStar.startAnimation(StarRotate);
                if (!GameisOn()) {
                    showNotification("Take your turn...");
                }
                // vibrate(500);
            }
            User_turn_cnt++;
            hasTakenCard = false;
            isCardThrown = false;
            if (isUserTurn(seatIndex)) {
                if (!GameisOn()) {
                    showNotification("Its your turn, please take your turn..");
                } else {
                    if (PreferenceManager.getVibrate()) {
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        v.vibrate(500);
                    }
                    Music_Manager.play_UserTurn();
                }
                UserTurnStarted[seatIndex] = true;
                hasTakenCard = false;
                isUserTurn = true;
                if (isFirstTurn) {
                    // user must skip or pick from thrown card
                    // isFirstTurn = false;
                    btnSkip.setVisibility(View.VISIBLE);
//                    btnSkip.setBackgroundResource(R.drawable.skipbuttonframeanim);
                    // Get the background, which has been compiled to an AnimationDrawable object.
//                    AnimationDrawable frameAnimation = (AnimationDrawable) btnSkip.getBackground();
                    // Start the animation (looped playback by default).
//                    frameAnimation.start();
                    ivCloseDeck.setClickable(false);
                    btnSkip.startAnimation(heartBeatAnimation);

                } else {
                    if (btnSkip.getVisibility() == View.VISIBLE) {
                        if (btnSkip.getAnimation() != null) {
                            btnSkip.clearAnimation();
                        }
                        btnSkip.setVisibility(View.GONE);
                    }
                }
                //DeadwoodCount();
            } else {
                btnFaceUp.setVisibility(View.GONE);
                btnDrawNew.setVisibility(View.GONE);
                btnDiscard.setVisibility(View.GONE);
                UserTurnStarted[seatIndex] = true;
                if (btnKnock.getVisibility() == View.VISIBLE) {
                    if (btnKnock.getAnimation() != null) {
                        btnKnock.clearAnimation();
                    }
                    btnKnock.setVisibility(View.INVISIBLE);
                }
                if (ivHandOpenCard.getAnimation() != null)
                    ivHandOpenCard.clearAnimation();
                if (ivHandCloseDeck.getAnimation() != null)
                    ivHandCloseDeck.clearAnimation();
                ivHandCloseDeck.setVisibility(View.INVISIBLE);
                ivHandOpenCard.setVisibility(View.INVISIBLE);
                isFirstTurn = false;
                messageHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (seatIndex == RightSeatIndex) {
                            ChoosePickCard(card_RightUser);
                        } else if (seatIndex == LeftSeatIndex) {
                            ChoosePickCard(card_LeftUser);
                        }
                    }
                }, 3000);
                // ViewHelper.setAlpha(KnockBtn, 0.6f);
                // KnockBtn.setEnabled(false);
            }
            // AnimateTo[RotatedArray[seatIndex]] =
            // UserTurnStarted[RotatedArray[seatIndex]];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ChoosePickCard(ArrayList<Item_Card> userCards) {
        Logger.print("MMMMMMMMMMMMMMMMM>>>>>> ChoosePickCard Method ================================= ");
        Logger.print("MMMMMMMMMMMMMMMMM>>>>>> OpenCard => " + openBunchCard.toString());
        Logger.print("MMMMMMMMMMMMMMMMM>>>>>> CloseCard => " + closeBunchCard.toString());
        Logger.print("MMMMMMMMMMMMMMMMM>>>>>> UserCards => " + userCards.toString());
        if (openBunchCard.size() > 0) {
            Item_Card openCard = openBunchCard.get(openBunchCard.size() - 1);
            ArrayList<Item_Card> tempCards = new ArrayList<>(userCards);
            tempCards.add(openCard);
            tempCards = new ArrayList<>(Sort(tempCards, 10));
            boolean isValidCard = false;
            for (int i = 0; i < tempCards.size(); i++) {
                if (tempCards.get(i).equals(openCard)) {
                    if (tempCards.get(i).getCardInPair()) {
                        isValidCard = true;
                    }
                    break;
                }
            }

//            if (CURRENT_USER_TURN == LeftSeatIndex)
//                isValidCard = false;
//            else
//                isValidCard = false;

//            if (!isValidCard) {
//
//                isValidCard = iscardmaketwocardsquance(tempCards, openCard);
//
//            }
            tempCards.remove(openCard);
            if (isValidCard) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(Parameters.Card, getCardString(openCard));
                    jsonObject.put(Parameters.SeatIndex, CURRENT_USER_TURN);
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put(Parameters.data, jsonObject);
                    PickFromThrownCardsProcess(jsonObject1.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (!isCardPicked && !dontAllowToClickOnCloseDeck) {
                passTurn();
            } else if (closeBunchCard.size() > 0) {
                //{"en":"PFP","data":{"c":"k-3","si":0}}
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(Parameters.Card, getCardString(closeBunchCard.get(closeBunchCard.size() - 1)));
                    jsonObject.put(Parameters.SeatIndex, CURRENT_USER_TURN);
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put(Parameters.data, jsonObject);
                    PickFromPileProcess(jsonObject1.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } /*else {
                throw new IllegalStateException("closeBunch Deck is Empty");
            }*/
        } else if (closeBunchCard.size() > 0) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(Parameters.Card, getCardString(closeBunchCard.get(closeBunchCard.size() - 1)));
                jsonObject.put(Parameters.SeatIndex, CURRENT_USER_TURN);
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put(Parameters.data, jsonObject);
                PickFromPileProcess(jsonObject1.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }/* else {
            throw new IllegalStateException("Both Bunch Are Empty");
        }*/
    }

    private boolean iscardmaketwocardsquance(ArrayList<Item_Card> usercard, Item_Card openCard) {
        boolean isValidCard = false;
        Logger.print("MMMMMMMMMMMMMMMMM>>>>>>iscardmaketwocardsquance ==usercard== >>>>> " + usercard);
        Logger.print("MMMMMMMMMMMMMMMMM>>>>>>iscardmaketwocardsquance ==openCard== >>>>> " + openCard);
        String opencardcolor = getcardcolor(getCardString(openCard));
        int opencardvalue = getCardValue2(getCardString(openCard));
        Logger.print("MMMMMMMMMMMMMMMMM>>>>>>iscardmaketwocardsquance ==opencardcolor== >>>>> " + opencardcolor);
        ArrayList<Item_Card> usercardarray2 = new ArrayList<>();
        for (Item_Card card : usercard) {
            if (!card.getCardInPair()) {
                usercardarray2.add(card);
            }
        }
        Logger.print("MMMMMMMMMMMMMMMMM>>>>>>iscardmaketwocardsquance ==usercardarray2== >>>>> " + usercardarray2);
        ArrayList<Item_Card> samecolorscards = new ArrayList<>();
        ArrayList<Item_Card> samevaluecards = new ArrayList<>();
        for (int i = 0; i < usercardarray2.size(); i++) {
            if (getcardcolor(getCardString(usercardarray2.get(i))).equalsIgnoreCase(opencardcolor)) {
                samecolorscards.add(usercardarray2.get(i));
            }
        }
        Logger.print("MMMMMMMMMMMMMMMMM>>>>>>samecolorscards ==BEFORE== sort>>>>> " + samecolorscards);
        //  ArrayList<Item_Card> squence=new ArrayList<>();
        if (samecolorscards.size() > 1 && usercardarray2.size() > 2) {
            // check is make squance
            //    squence= new ArrayList<>();
            samecolorscards = Sort(samecolorscards, 10);
            Logger.print("MMMMMMMMMMMMMMMMM>>>>>>samecolorscards ==AFTER== sort>>>>> " + samecolorscards);
            for (int i = 0; i < samecolorscards.size() - 1; i++) {
                if (getCardValue2(getCardString(samecolorscards.get(i + 1))) - getCardValue2(getCardString(samecolorscards.get(i))) == Math.abs(1)) {
                    Logger.print("MMMMMMMMMMMMMMMMM>>>>>>samecolorscards ==1111111==>>>>> ");
                    if (getCardString(samecolorscards.get(i + 1)).equals(getCardString(openCard)) || getCardString(samecolorscards.get(i)).equals(getCardString(openCard))) {
                        Logger.print("MMMMMMMMMMMMMMMMM>>>>>>samecolorscards ==2222222==>>>>> ");
                        Item_Card card1 = new Item_Card(), card2 = new Item_Card();
                        card1.setCardValue(0);
                        card2.setCardValue(0);
                        if (getCardValue2(getCardString(samecolorscards.get(i))) != 1) {
                            card1.setCardColor(samecolorscards.get(i).getCardColor());
                            card1.setCardValue(samecolorscards.get(i).getCardValue() - 1);
                            Logger.print("MMMMMMMMMMMMMMMMM>>>>>>samecolorscards ==3333333==>>>>>card1 " + card1);
                        }
                        if (getCardValue2(getCardString(samecolorscards.get(i + 1))) != 13) {
                            card2.setCardColor(samecolorscards.get(i + 1).getCardColor());
                            card2.setCardValue(samecolorscards.get(i + 1).getCardValue() + 1);
                            Logger.print("MMMMMMMMMMMMMMMMM>>>>>>samecolorscards ==44444444==>>>>>card2 " + card2);
                        }
                        if (card1.getCardValue() != 0) {
                            if ((!card1.getCardInPair()) && (closeBunchCard.contains(card1))) {
                                isValidCard = true;
                                Logger.print("MMMMMMMMMMMMMMMMM>>>>>>samecolorscards ==5555555==>>>>>card1 ");
                            }
                        }
                        if (!isValidCard) {
                            if (card2.getCardValue() != 0) {
                                if ((!card2.getCardInPair()) && (closeBunchCard.contains(card2))) {
                                    isValidCard = true;
                                    Logger.print("MMMMMMMMMMMMMMMMM>>>>>>samecolorscards ==6666666==>>>>>card2 ");
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
//        if (!isValidCard) {
//
//            // check is make pair
//            samecolorscards = new ArrayList<>();
//            for (int i = 0; i < usercardarray2.size(); i++) {
//                if (getCardValue2(getCardString(usercardarray2.get(i))) == (opencardvalue)) {
//                    samecolorscards.add(usercardarray2.get(i));
//                }
//            }
//            Logger.print("MMMMMMMMMMMMMMMMM>>>>>>check pair ==samevaluecards== >>>>> " + samecolorscards);
//
//            if (samecolorscards.size() > 1 && usercardarray2.size() > 2) {
//
//
//                ArrayList<String> cardcolors = new ArrayList<>();
//
//                cardcolors = getremainigcolor(samecolorscards);
//                Item_Card[] card = new Item_Card[cardcolors.size()];
//
//                for (int i = 0; i < cardcolors.size(); i++) {
//                    card[i] = new Item_Card();
//                    card[i].setCardValue(opencardvalue);
//                    card[i].setCardColor(cardcolorint(cardcolors.get(i)));
//                    Logger.print("MMMMMMMMMMMMMMMMM>>>>>>samecolorscards222 ==7777777==>>>>>card1 " + card[i]);
//                    if (card[i].getCardValue() != 0) {
//                        if ((!card[i].getCardInPair()) && (closeBunchCard.contains(card[i]))) {
//                            isValidCard = true;
//                            Logger.print("MMMMMMMMMMMMMMMMM>>>>>>samecolorscards222 ==8888888==>>>>>card1 " + card[i]);
//                        }
//
//                    }
//
//                    if (isValidCard) {
//                        break;
//                    }
//
//                }
//            }
//        }
        return isValidCard;
    }

    private int cardcolorint(String CardColor) {
        int colr = 0;
        if (CardColor.equalsIgnoreCase("l")) {
            colr = 0;
        } else if (CardColor.equalsIgnoreCase("k")) {
            colr = 1;
        } else if (CardColor.equalsIgnoreCase("c")) {
            colr = 2;
        } else if (CardColor.equalsIgnoreCase("f")) {
            colr = 3;
        }
        return colr;
    }

    private ArrayList<String> getremainigcolor(ArrayList<Item_Card> samecolorscards) {
        ArrayList<String> colors11 = new ArrayList<>();
        ArrayList<String> colors = new ArrayList<>();
        for (int i = 0; i < samecolorscards.size(); i++) {
            String clr = getcardcolor(getCardString(samecolorscards.get(i)));
            colors11.add(clr);
        }
        for (int i = 0; i < COLOR.length; i++) {
            String clr = COLOR[i];
            if (!colors11.contains(clr)) {
                colors.add(clr);
            }
        }
        return colors;
    }

    private String getcardcolor(String cardString) {
        String color = String.valueOf(cardString.charAt(0));
        return color;
    }

    private void AnimateSkipText(final int prevTurn) {
        // TODO Auto-generated method stub
        Logger.print("SKIIIIIIIPPPPPPPPPP => 3");
        if (ivCloseCard.getVisibility() != View.VISIBLE) {
            ivCloseCard.setVisibility(View.VISIBLE);
        }
        if (RightUserRing.getAnimation() != null) {
            RightUserRing.clearAnimation();
        }
        if (LeftUserRing.getAnimation() != null) {
            LeftUserRing.clearAnimation();
        }
        if (BottomUserRing.getAnimation() != null) {
            BottomUserRing.clearAnimation();
        }
        if (prevTurn == BottomSeatIndex) {
            ivSkipUserThree.setVisibility(View.VISIBLE);
            ivSkipUserThree.bringToFront();
        } else if (prevTurn == LeftSeatIndex) {
            ivSkipUserOne.setVisibility(View.VISIBLE);
            ivSkipUserOne.bringToFront();
        } else if (prevTurn == RightSeatIndex) {
            ivSkipUserTwo.setVisibility(View.VISIBLE);
            ivSkipUserTwo.bringToFront();
        }
        int[] userOneSkipXY = new int[2];
        int[] userTwoSkipXY = new int[2];
        int[] userThreeSkipXY = new int[2];
        ivSkipUserOne.getLocationOnScreen(userOneSkipXY);
        ivSkipUserTwo.getLocationOnScreen(userTwoSkipXY);
        ivSkipUserThree.getLocationOnScreen(userThreeSkipXY);
        // NotificationText.getLocationOnScreen(textViewCenterXY);
        int lx = userOneSkipXY[0];
        int ly = userOneSkipXY[1];
        int rx = userTwoSkipXY[0];
        int ry = userTwoSkipXY[1];
        int bx = userThreeSkipXY[0];
        int by = userThreeSkipXY[1];
        // int cenX = textViewCenterXY[0];
        // int cenY = textViewCenterXY[1];
        int cenX = Screen_Width / 2;
        int cenY = Screen_Hight / 2;
        AnimationSet animSet = new AnimationSet(true);
        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 16.0f, 1.0f, 16.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnim.setDuration(1500);
        scaleAnim.setFillAfter(false);
        TranslateAnimation tAnimationLeft = new TranslateAnimation(0,
                cenX - lx, 0, cenY - ly);
        tAnimationLeft.setDuration(1500);
        tAnimationLeft.setFillAfter(false);
        TranslateAnimation tAnimationBottom = new TranslateAnimation(0, cenX
                - bx, 0, cenY - by);
        tAnimationBottom.setDuration(1500);
        tAnimationBottom.setFillAfter(false);
        TranslateAnimation tAnimationRight = new TranslateAnimation(0, cenX
                - rx, 0, cenY - ry);
        tAnimationRight.setDuration(1500);
        tAnimationRight.setFillAfter(false);
        Logger.print("COOOOOOOOOOOOOOOOORRRRRRRRRRSSSSSS => " + cenX + " "
                + cenY + " " + bx + " " + by);
        if (prevTurn == BottomSeatIndex) {
            Logger.print("SKIIIIIIIPPPPPPPPPP => 4");
            animSet.addAnimation(scaleAnim);
            animSet.addAnimation(tAnimationBottom);
            ivSkipUserThree.startAnimation(animSet);
        } else if (prevTurn == LeftSeatIndex) {
            Logger.print("SKIIIIIIIPPPPPPPPPP => 5");
            animSet.addAnimation(scaleAnim);
            animSet.addAnimation(tAnimationLeft);
            ivSkipUserOne.startAnimation(animSet);
        } else if (prevTurn == RightSeatIndex) {
            Logger.print("SKIIIIIIIPPPPPPPPPP => 6");
            animSet.addAnimation(scaleAnim);
            animSet.addAnimation(tAnimationRight);
            ivSkipUserTwo.startAnimation(animSet);
        }
        animSet.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                //Music_Manager.play_Skip();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if (prevTurn == BottomSeatIndex) {
                            ivSkipUserThree.setVisibility(View.INVISIBLE);
                        } else if (prevTurn == LeftSeatIndex) {
                            ivSkipUserOne.setVisibility(View.INVISIBLE);
                        } else if (prevTurn == RightSeatIndex) {
                            ivSkipUserTwo.setVisibility(View.INVISIBLE);
                        }
                    }
                }, 0);
            }
        });
    }

    private void AnimateTimeOutText(final int prevTurn) {
        // TODO Auto-generated method stub
        Logger.print("TIMEEEEEEEEEE OUTTTTTTTTTTT  3");
        if (prevTurn == BottomSeatIndex) {
            ivTimeOutUserThree.setVisibility(View.VISIBLE);
        } else if (prevTurn == LeftSeatIndex) {
            ivTimeOutUserOne.setVisibility(View.VISIBLE);
        } else if (prevTurn == RightSeatIndex) {
            ivTimeOutUserTwo.setVisibility(View.VISIBLE);
        }
        int[] userOneSkipXY = new int[2];
        int[] userTwoSkipXY = new int[2];
        int[] userThreeSkipXY = new int[2];
        // int[] textViewCenterXY = new int[2];
        ivSkipUserOne.getLocationOnScreen(userOneSkipXY);
        ivSkipUserTwo.getLocationOnScreen(userTwoSkipXY);
        ivSkipUserThree.getLocationOnScreen(userThreeSkipXY);
        // NotificationText.getLocationOnScreen(textViewCenterXY);
        int lx = userOneSkipXY[0];
        int ly = userOneSkipXY[1];
        int rx = userTwoSkipXY[0];
        int ry = userTwoSkipXY[1];
        int bx = userThreeSkipXY[0];
        int by = userThreeSkipXY[1];
        // int cenX = textViewCenterXY[0];
        // int cenY = textViewCenterXY[1];
        int cenX = Screen_Width / 2;
        int cenY = Screen_Hight / 2;
        AnimationSet animSet = new AnimationSet(true);
        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 20.0f, 1.0f, 20.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnim.setDuration(1000);
        scaleAnim.setFillAfter(false);
        TranslateAnimation tAnimationLeft = new TranslateAnimation(0,
                cenX - lx, 0, cenY - ly);
        tAnimationLeft.setDuration(1000);
        tAnimationLeft.setFillAfter(false);
        TranslateAnimation tAnimationBottom = new TranslateAnimation(0, cenX
                - bx, 0, cenY - by);
        tAnimationBottom.setDuration(1000);
        tAnimationBottom.setFillAfter(false);
        TranslateAnimation tAnimationRight = new TranslateAnimation(0, cenX
                - rx, 0, cenY - ry);
        tAnimationRight.setDuration(1000);
        tAnimationRight.setFillAfter(false);
        Logger.print("COOOOOOOOOOOOOOOOORRRRRRRRRRSSSSSS => " + cenX + " "
                + cenY + " " + bx + " " + by);
        if (prevTurn == BottomSeatIndex) {
            Logger.print("SKIIIIIIIPPPPPPPPPP => 4");
            animSet.addAnimation(scaleAnim);
            animSet.addAnimation(tAnimationBottom);
            ivTimeOutUserThree.startAnimation(animSet);
        } else if (prevTurn == LeftSeatIndex) {
            Logger.print("SKIIIIIIIPPPPPPPPPP => 5");
            animSet.addAnimation(scaleAnim);
            animSet.addAnimation(tAnimationLeft);
            ivTimeOutUserOne.startAnimation(animSet);
        } else if (prevTurn == RightSeatIndex) {
            Logger.print("SKIIIIIIIPPPPPPPPPP => 6");
            animSet.addAnimation(scaleAnim);
            animSet.addAnimation(tAnimationRight);
            ivTimeOutUserTwo.startAnimation(animSet);
        }
        animSet.getAnimations().get(1)
                .setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub
                        Music_Manager.play_Timeout();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // TODO Auto-generated method stub
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                if (prevTurn == BottomSeatIndex) {
                                    ivTimeOutUserThree
                                            .setVisibility(View.INVISIBLE);
                                } else if (prevTurn == LeftSeatIndex) {
                                    ivTimeOutUserOne
                                            .setVisibility(View.INVISIBLE);
                                } else if (prevTurn == RightSeatIndex) {
                                    ivTimeOutUserTwo
                                            .setVisibility(View.INVISIBLE);
                                }
                            }
                        }, 0);
                    }
                });
    }

    private void disableAllOtherTurn() {
        // TODO Auto-generated method stub
        for (int i = 0; i < UserTurnStarted.length; i++) {
            UserTurnStarted[i] = false;
            // AnimateTo[i] = false;
        }
    }

    private boolean isUserTurn(int SeatIndex) throws JSONException {
        return SeatIndex == BottomSeatIndex;
    }

    private boolean GameisOn() {
        // TODO Auto-generated method stub
//        try {
        ActivityManager activityManager = (ActivityManager) this
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> services = activityManager
                .getRunningTasks(Integer.MAX_VALUE);
        boolean isActivityFound = false;
        if (services.get(0).topActivity.getPackageName()
                .equalsIgnoreCase(this.getPackageName())) {
            isActivityFound = true;
        }
        if (isActivityFound) {
            return true;
        } else {
            RefreshView = true;
        }
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        }
        return false;
    }

    private void showNotification2(String Message) {
        try {
            // Music_Manager.play_Notification();
            Intent intent = new Intent(this, PlayScreen2.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            intent.putExtra("ReloadData", true);
            PendingIntent pIntent = PendingIntent.getActivity(
                    PlayScreen2.this, 0, intent, 0);
            Notification mNotification = prepareNotification2(this,
                    getResources().getString(R.string.app_name), Message,
                    pIntent);
            mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(0, mNotification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showNotification(String Message) {
        if (PreferenceManager.getNotification()) {
            Music_Manager.play_Notification();
            Intent intent = new Intent(this, PlayScreen2.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            // intent.putExtra("ReloadData", true);
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                    0);
            Notification mNotification = prepareNotification(this,/*getResources().getString(R.string.app_name),*/
                    Message, pIntent);
            mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(0, mNotification);
        }
    }

    private Notification prepareNotification2(Context context, String title,
                                              String msg, PendingIntent intent) {
        long when = System.currentTimeMillis() + 1000;
// Notification notification = new Notification(R.mipmap.ic_launcher, title, when);
        Notification.Builder builder = new Notification.Builder(context);
//		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//			builder.setSmallIcon(R.drawable.sil_icon)
//					.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
//					.setContentTitle(title)
//					.setContentText(msg)
//					.setContentIntent(intent)
//					.setWhen(when);
//		}else{
        builder.setSmallIcon(R.drawable.ic_stat_notify
        )
                .setContentTitle(title)
                .setContentText(msg)
                .setContentIntent(intent)
                .setWhen(when);
        //}
        Notification notification = builder.getNotification();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
// notification.setLatestEventInfo(context, title, msg, intent);
        return notification;
    }

    private Notification prepareNotification(Context context,
                                             String msg, PendingIntent intent) {
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_stat_notify);
            builder = new Notification.Builder(this);
            builder.setSmallIcon(R.drawable.sil_icon)
                    .setLargeIcon(largeIcon)
                    .setContentTitle("Gin Rummy Offline")
                    .setContentText(msg)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(intent);
        } else {
            builder = new Notification.Builder(this);
            builder.setSmallIcon(R.drawable.ic_stat_notify)
                    .setContentTitle("Gin Rummy Offline")
                    .setContentText(msg)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(intent);
        }
        Notification notification = builder.getNotification();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//             notification.setLatestEventInfo(this, msg[1], msg[0], i);
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        return notification;
    }

    private void RoundTimerStartProcess(int timer, boolean type) {
        Disableautosort();
        iv_2X.setVisibility(View.GONE);
//        DisableStandUp_Btn();
        // TODO Auto-generated method stub
        Logger.print("TIMER 3");
        // playingActivity.UserWinner = new boolean[3];
        second = timer;
        Logger.print("MSG Show Message Timer " + second);
        if (second > 0) {
            // start Timer
            Logger.print("MSG Show Message Timer");
            NotificationText.setVisibility(View.VISIBLE);
            try {
                if (type)
                    startTimer("New Game begins in ", second, -1);
                else
                    startTimer("New Round begins in ", second, -1);
            } catch (Exception e) {
                PreferenceManager.setIsGameResume(false);
                c.exitFromPlayScreen = true;
                c.isPlayingScreenOpen = false;
                BonustimerPause();
                finish();
            }
        }
    }

    private void startTimer(final String text, final int remainingSeconds,
                            final int TurnSeatIndex) {
        second = remainingSeconds;
        timercounter = second /* 1000*/;
        msg = text;
        Logger.print("MSG Show Message Timer => 2 " + second);
//        if (timer != null) {
//            timer.cancel();
//        }
//        timer = new Timer();
        try {
            /*timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    PlayScreen2.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Logger.print("TTTTTTTTTTTTTTT Timer =>  " + second);
                            if (second >= 0) {
                                Logger.print("TTTTTTTTTTTTTTT  sssssss ");
                                NotificationText.setVisibility(View.VISIBLE);
                                NotificationText.setText(text + "" + second
                                        + " seconds");
                            } else {
                                Logger.print("TTTTTTTTTTTTTTT  fffffff ");
                                if (ROUND_NUMBER != 0) {
                                    startRound();
                                } else {
                                    startNewGame();
                                }
                                NotificationText.setVisibility(View.GONE);
                                NotificationText.setText("");
                                if (timer != null) {
                                    timer.cancel();
                                }
                            }
                        }
                    });
                    timercounter = timercounter - 100;
                    if (timercounter % 1000 == 0) {
                        second--;
                        // timercounter = 0;
                    }
                }
            }, 0, 100);*/
//            NotificationText.setVisibility(View.VISIBLE);
//            NotificationText.setText(text + "" + second
//                    + " seconds");
//            mHandler = new Handler();
//            mRunnable = new Runnable() {
//
//                @Override
//                public void run() {
//                    second--;
//                    if(second == 0){
//                        if (ROUND_NUMBER != 0) {
//                            startRound();
//                        } else {
//                            startNewGame();
//                        }
//                        NotificationText.setVisibility(View.GONE);
//                        NotificationText.setText("");
//                    }else {
//                        NotificationText.setVisibility(View.VISIBLE);
//                        NotificationText.setText(text + "" + second
//                                + " seconds");
//
//                        mHandler.postDelayed(mRunnable, 1000);
//                    }
//                }
//            };
//            mHandler.postDelayed(mRunnable, 1000);
            timerStart(remainingSeconds * 1000);
        } catch (Exception e) {
            e.printStackTrace();
//            if (timer != null) {
//                timer.cancel();
//            }
        }
    }

    public void timerStart(long timeLengthMilli) {
//        iv_2X.setVisibility(View.VISIBLE);
        isTimerStart = true;
        cdt = new CountDownTimer(timeLengthMilli, 1000) {
            @Override
            public void onTick(long milliTillFinish) {
                milliLeft = milliTillFinish;
                NotificationText.setVisibility(View.VISIBLE);
                NotificationText.setText(msg + "" + milliTillFinish / 1000
                        + " seconds");
//                Log.i("Tick", "Tock");
            }

            @Override
            public void onFinish() {
                isTimerStart = false;
                if (ROUND_NUMBER != 0) {
                    startRound();
                } else {
                    startNewGame();
                }
                NotificationText.setVisibility(View.GONE);
                NotificationText.setText("");
            }
        }.start();
    }

    public void timerPause() {
        if (cdt != null && isTimerStart) {
            cdt.cancel();
        }
    }

    private void timerResume() {
        if (isTimerStart) {
            timerStart(milliLeft);
        }
    }

    @Override
    protected void onPause() {
        try {
            super.onPause();
            //=============== New Bonus Timer ================
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    Logger.print(">>>>>> Bonus >>>> onPause Called >>>>> ");
                    if (!GameisOn()) {
                        Logger.print(">>>>>> Bonus >>>> onPause Called >>>>> BackGraound");
                        isGameinBackGround = true;
                        BonustimerPause();
                    }
                }
            });
            //================================================

            isGameComeFromPauseMode = true;

            isAnimStop = true;
            ispause = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        try {
            //=============== New Bonus Timer ================
            if (isGameinBackGround) {
                isGameinBackGround = false;
                BonustimerResume();
            }
            //================================================
            c.payment_Handler = handler;
            c.con = this;
            c.activity = PlayScreen2.this;
            ispause = false;
            //stopTimerMM();
            c.height = Screen_Hight = PreferenceManager.getheight();
            c.width = Screen_Width = PreferenceManager.getwidth();
            Logger.print("ON Resume::::Called Screen_Hight >>>" + Screen_Hight);
            Logger.print("ON Resume::::Called Screen_Width >>>" + Screen_Width);
            // findViewIds1();
            //setScreen();
            //  c.showAds(getApplicationContext());
            Logger.print("ON Resume::::Called");
//        updateChips();
            //
            Logger.print("ON Resume::::isGameComeFromPauseMode " + isGameComeFromPauseMode + " RefreshView " + RefreshView);
            if (isGameComeFromPauseMode && RefreshView) {
                try {
                    if (notificationManager != null) {
                        notificationManager.cancelAll();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                isGameComeFromPauseMode = false;
                RefreshView = false;
                refreshGame();
            }
            try {
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.cancelAll();
            } catch (Exception e) {
                Logger.print("ON Resume::::Notification");
            }
            super.onResume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshGame() {
        Logger.print("ON Resume::::refreshGame");
        if (card_BottomUser.size() > 0) {
            /*card_BottomUser = new ArrayList<Item_Card>(Sort(
                    card_BottomUser, BottomSeatIndex));*/
            reDrawUserCards("");
        }
        if (senddata.length() > 0) {
            ivScoreboard.setVisibility(View.VISIBLE);
        } else {
            ivScoreboard.setVisibility(View.INVISIBLE);
        }
        Logger.print("CARD COUNTER >>> 2");
        tvCardCounter.setText("" + PileCardCounter);
        if (DealCardToSeat[RightSeatIndex] && DealingAnimationCompleted) {
//            if (ivUserTwoCard.getVisibility() != View.VISIBLE)
            ivUserTwoCard.setVisibility(View.INVISIBLE);
        }
        if (DealCardToSeat[LeftSeatIndex] && DealingAnimationCompleted) {
//            if (ivUserOneCard.getVisibility() != View.INVISIBLE)
            ivUserOneCard.setVisibility(View.INVISIBLE);
        }
        if (openBunchCard.size() > 0) {
            Item_Card openCard = openBunchCard
                    .get(openBunchCard.size() - 1);
            String openCardStr = getCardString(openCard);
            int index1 = Arrays.asList(cardString).indexOf(
                    openCardStr);
            ivCloseCard
                    .setImageResource(CardDefault[index1]);
            ivCloseCard.setTag(openCardStr);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // LeaveTable(Parameters.LeavingTable);
        //stopTimerMM();
        PreferenceManager.isPlayingScreenOpen = false;
        //c.isPlayingScreenOpen=false;
        c.isCloseLoaderOnDashBoardOnResume = true;
        if (messageHandler != null) {
            messageHandler.removeCallbacksAndMessages(null);
            // messageHandler = null;
        }
        // System.out.println("on create called destroy");
        try {
            if (notificationManager != null) {
                notificationManager.cancelAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (ConfirmationDialog != null && ConfirmationDialog.isShowing()) {
                ConfirmationDialog.cancel();
                ConfirmationDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (ConfirmationDialogInfo != null && ConfirmationDialogInfo.isShowing()) {
                ConfirmationDialogInfo.cancel();
                ConfirmationDialogInfo = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.themeId = null;
        if (gameHandler != null) {
            gameHandler.removeCallbacksAndMessages(null);
        }
        if (mCountDownTimer != null)
            mCountDownTimer.cancel();
        if (Bonushcdtimer != null) {
            Bonushcdtimer.cancel();
            Bonushcdtimer = null;
        }
        recursiveLoopChildren(frmMainLayout);
    }

    @Override
    public void onBackPressed() {

    }

    private void recursiveLoopChildren(ViewGroup parent) {
        if (parent == null) return;
        for (int i = parent.getChildCount() - 1; i >= 0; i--) {
            final View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                recursiveLoopChildren((ViewGroup) child);
            } else {
                if (child != null) {
                    try {
                        if (child instanceof ImageView) {
                            try {
                                ((ImageView) child).setImageResource(0);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        child.setBackgroundResource(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private class NameSorter implements Comparator<String> {
        public int compare(String one, String another) {
            return getCardValue(one).compareTo(getCardValue(another));
        }
    }

}
