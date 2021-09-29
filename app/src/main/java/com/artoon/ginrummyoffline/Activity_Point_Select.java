package com.artoon.ginrummyoffline;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import adapters.Adapter_Point;
import utils.C;
import utils.PreferenceManager;
import utils.ResponseCodes;

/**
 * Created by milin.patel on 7/4/2017.
 */

public class Activity_Point_Select extends Activity implements AdapterView.OnItemClickListener {

    C c = C.getInstance();

    //========= tittle and Close button
    TextView tv_tittle;
    Button btn_close;
    GridView grid_point;
    int[] Point = {10, 50, 100, 150, 200, 250};
    Adapter_Point adapter_point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_point_select);

        FindViewById();
        LayOutScreen();

    }

    private void FindViewById() {
        //========= tittle and Close button
        tv_tittle = findViewById(R.id.tv_tittle);
        tv_tittle.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(35));
        tv_tittle.setTypeface(c.typeface, Typeface.BOLD);

        btn_close = findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
                finish();
                overridePendingTransition(R.anim.none, R.anim.to_lefttoright);
            }
        });

        grid_point = findViewById(R.id.grid_point);
        grid_point.setOnItemClickListener(this);
        grid_point.setHorizontalSpacing(c.getwidth1(15));
        grid_point.setVerticalSpacing(c.getheight1(40));

        adapter_point = new Adapter_Point(Point, this);
        grid_point.setAdapter(adapter_point);
    }

    private void LayOutScreen() {

        FrameLayout.LayoutParams frm;
        int w, h;

        //========= tittle and Close button

        frm = (FrameLayout.LayoutParams) tv_tittle.getLayoutParams();
        frm.height = c.getheight1(70);
        frm.width = c.getwidth1(900);
        frm.topMargin = c.getheight1(10);

        frm = (FrameLayout.LayoutParams) btn_close.getLayoutParams();
        frm.width = c.getwidth1(80);
        frm.height = c.getheight1(70);
        frm.topMargin = c.getheight1(10);
        frm.rightMargin = c.getwidth1(10);


        frm = (FrameLayout.LayoutParams) findViewById(R.id.frm_grid).getLayoutParams();
        frm.width = c.getwidth1(900);
        frm.topMargin = c.getheight1(20);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        c.initialRoundPoint = Point[position];

        PreferenceManager.setPlayerPoints(Point[position], c.gameType, PreferenceManager.getNumberOfPlayers());


        Music_Manager.buttonclick();
        finish();
        sendMassegeToDashBoard();

    }

    private void sendMassegeToDashBoard() {

        if (Dashboard.handler != null) {
            Message msg = new Message();
            msg.what = ResponseCodes.Start_Playing_Mode;
            Dashboard.handler.sendMessage(msg);
        }

        if (Select_Mode.handler != null) {
            Message msg = new Message();
            msg.what = ResponseCodes.FINISH;
            Select_Mode.handler.sendMessage(msg);
        }

    }
}
