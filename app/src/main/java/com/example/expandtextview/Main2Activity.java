package com.example.expandtextview;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expandtextview.util.CommonUtils;
import com.example.expandtextview.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private RecyclerView mRlvContent;
    private LinearLayout mLlMain2Scro;
    private EditText mEt2Comment;
    private TextView mTv2SendComment;
    private LinearLayout mLl2Comment;
    private int screenHeight;
    private int editTextBodyHeight;
    private int currentKeyboardH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        initContent();
    }

    private void initContent() {

        Main2Adapter main2Adapter = new Main2Adapter(this, getData());
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        mRlvContent.setLayoutManager(manager);
        mRlvContent.setNestedScrollingEnabled(false);
        mRlvContent.setAdapter(main2Adapter);
        main2Adapter.setOnItemClickListener(new Main2Adapter.OnItemClickListener() {
            @Override
            public void onClickItem(View v,Object o) {
                final int itemBottomY = getCoordinateY(v) + v.getHeight();//item 底部y坐标
                mLl2Comment.setVisibility(View.VISIBLE);
                mEt2Comment.requestFocus();
                CommonUtils.showSoftInput(Main2Activity.this, mLl2Comment);
                mEt2Comment.setHint("说点什么");
                mEt2Comment.setText("");


                v.postDelayed(() -> {
                    int y = getCoordinateY(mLl2Comment);
                    //评论时滑动到对应item底部和输入框顶部对齐
                    mRlvContent.smoothScrollBy(0,itemBottomY-y);
                }, 300);
            }
        });
        mTv2SendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEt2Comment.getText().toString())) {
                    Toast.makeText(Main2Activity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    return;
                }
//                setViewTreeObserver();
            }
        });
    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        int a = 200;
        for (int i = 0; i < a; i++) {
            list.add("i==" + i);

        }
        return list;
    }

    private void setViewTreeObserver() {
        final ViewTreeObserver swipeRefreshLayoutVTO = mLlMain2Scro.getViewTreeObserver();
        swipeRefreshLayoutVTO.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                mLlMain2Scro.getWindowVisibleDisplayFrame(r);
                int statusBarH = Utils.getStatusBarHeight();//状态栏高度
                int screenH = mLlMain2Scro.getRootView().getHeight();
                if (r.top != statusBarH) {
                    //在这个demo中r.top代表的是状态栏高度，在沉浸式状态栏时r.top＝0，通过getStatusBarHeight获取状态栏高度
                    r.top = statusBarH;
                }
                int keyboardH = screenH - (r.bottom - r.top);
                Log.d("+=", "screenH＝ " + screenH + " &keyboardH = " + keyboardH + " &r.bottom=" + r.bottom + " &top=" + r.top + " &statusBarH=" + statusBarH);

                if (keyboardH == currentKeyboardH) {//有变化时才处理，否则会陷入死循环
                    return;
                }
                currentKeyboardH = keyboardH;
                screenHeight = screenH;//应用屏幕的高度
                editTextBodyHeight = mLl2Comment.getHeight();
                if (keyboardH < 150) {//说明是隐藏键盘的情况
                    updateEditTextBodyVisible(View.GONE);
                    return;
                }
            }
        });
    }

    private void initView() {
        mRlvContent = (RecyclerView) findViewById(R.id.rlv_content);
        mLlMain2Scro = (LinearLayout) findViewById(R.id.ll_main2_scro);
        mEt2Comment = (EditText) findViewById(R.id.et2_comment);
        mTv2SendComment = (TextView) findViewById(R.id.tv2_send_comment);
        mLl2Comment = (LinearLayout) findViewById(R.id.ll2_comment);
    }

    public void updateEditTextBodyVisible(int visibility) {
        mLl2Comment.setVisibility(visibility);
        if (View.VISIBLE == visibility) {
            mLl2Comment.requestFocus();
            //弹出键盘
            CommonUtils.showSoftInput(mEt2Comment.getContext(), mEt2Comment);

        } else if (View.GONE == visibility) {
            //隐藏键盘
            CommonUtils.hideSoftInput(mEt2Comment.getContext(), mEt2Comment);
        }
    }


    /**
     * 获取控件左上顶点Y坐标
     *
     * @param view
     * @return
     */
    private int getCoordinateY(View view) {
        int[] coordinate = new int[2];
        view.getLocationOnScreen(coordinate);
        return coordinate[1];
    }

}
