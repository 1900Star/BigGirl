package com.yibao.biggirl.mvp.music;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yibao.biggirl.R;
import com.yibao.biggirl.factory.RecyclerViewFactory;
import com.yibao.biggirl.model.music.MusicInfo;
import com.yibao.biggirl.util.LogUtil;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/8/22 14:11
 */
public class MusicBottomSheetDialog
        implements View.OnClickListener
{
    private LinearLayout mBottomListContent;
    private TextView     mBottomListColection;
    private TextView     mBottomListClear;
    private TextView     mBottomListTitleSize;


    public static MusicBottomSheetDialog newInstance() {
        return new MusicBottomSheetDialog();
    }

    public BottomSheetDialog getBottomDialog(Context context, List<MusicInfo> list) {
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context)
                                  .inflate(R.layout.bottom_sheet_list_dialog, null);
        initView(view);
        initListener();
        RecyclerView recyclerView = RecyclerViewFactory.creatRecyclerView(1,
                                                                          new BottomSheetAdapter(
                                                                                  list));

        mBottomListTitleSize.setText("播放列表(" + list.size() + ")");
        mBottomListContent.addView(recyclerView);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    private void initListener() {
        mBottomListColection.setOnClickListener(this);
        mBottomListClear.setOnClickListener(this);

    }


    private void initView(View view) {
        mBottomListContent = (LinearLayout) view.findViewById(R.id.bottom_list_content);
        mBottomListColection = (TextView) view.findViewById(R.id.bottom_list_collection);
        mBottomListClear = (TextView) view.findViewById(R.id.bottom_list_clear);
        mBottomListTitleSize = (TextView) view.findViewById(R.id.bottom_list_title_size);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_list_collection:
                LogUtil.d("********    Collecotion");
                break;
            case R.id.bottom_list_clear:
                LogUtil.d("********    Clear");
                break;
            default:
                break;
        }
    }
}


