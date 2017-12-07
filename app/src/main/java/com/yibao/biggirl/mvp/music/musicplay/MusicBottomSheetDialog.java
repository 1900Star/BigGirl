package com.yibao.biggirl.mvp.music.musicplay;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.R;
import com.yibao.biggirl.factory.RecyclerFactory;
import com.yibao.biggirl.model.music.BottomSheetStatus;
import com.yibao.biggirl.model.music.MusicInfo;
import com.yibao.biggirl.service.AudioPlayService;
import com.yibao.biggirl.util.RxBus;
import com.yibao.biggirl.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/8/22 14:11
 */
public class MusicBottomSheetDialog
        implements View.OnClickListener
{
    private LinearLayout              mBottomListContent;
    private TextView                  mBottomListColection;
    private TextView                  mBottomListClear;
    private TextView                  mBottomListTitleSize;
    private Context                   mContext;
    private List<MusicInfo>           mList;
    private RecyclerView              mRecyclerView;
    private CompositeDisposable       mDisposable;
    private RxBus                     mBus;
    private BottomSheetBehavior<View> mBehavior;

    public static MusicBottomSheetDialog newInstance() {
        return new MusicBottomSheetDialog();
    }

    void getBottomDialog(Context context, List<MusicInfo> list) {
        this.mContext = context;
        this.mList = list;
        mDisposable = new CompositeDisposable();
        mBus = MyApplication.getIntstance()
                            .bus();
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context)
                                  .inflate(R.layout.bottom_sheet_list_dialog, null);
        initView(view);
        initListener();
        rxData();
        BottomSheetAdapter adapter = new BottomSheetAdapter(mList);
        mRecyclerView = RecyclerFactory.creatRecyclerView(1, adapter);
        String title = StringUtil.getBottomSheetTitile(mList.size());
        mBottomListTitleSize.setText(title);
        mBottomListContent.addView(mRecyclerView);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            dialog.getWindow()
                  .addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        dialog.setCanceledOnTouchOutside(true);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        dialog.show();
    }

    private void rxData() {
        mDisposable.add(mBus.toObserverable(BottomSheetStatus.class)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(bean -> MusicBottomSheetDialog.this.playMusic(bean.getType())));
    }

    private void initListener() {
        mBottomListColection.setOnClickListener(this);
        mBottomListClear.setOnClickListener(this);
        mBottomListTitleSize.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_sheet_bar_play:
                Random random = new Random();
                int position = random.nextInt(mList.size());
                playMusic(position);
                break;
            case R.id.bottom_list_title_size:
                backTop();
                break;
            case R.id.bottom_sheet_bar_clear:
                clearFavoriteMusic();

                break;
            default:
                break;
        }
    }

    private void clearFavoriteMusic() {
        MyApplication.getIntstance()
                     .getDaoSession()
                     .getMusicInfoDao()
                     .deleteAll();
        mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    private void backTop() {
        BottomSheetAdapter  adapter            = (BottomSheetAdapter) mRecyclerView.getAdapter();
        int                 positionForSection = adapter.getPositionForSection(0);
        LinearLayoutManager manager            = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        manager.scrollToPositionWithOffset(positionForSection, 0);
    }

    private void playMusic(int position) {
        Intent intent = new Intent();
        intent.setClass(mContext, AudioPlayService.class);
        intent.putParcelableArrayListExtra("musicItem", (ArrayList<? extends Parcelable>) mList);
        intent.putExtra("position", position);
        AudioServiceConnection connection = new AudioServiceConnection();
        mContext.bindService(intent, connection, Service.BIND_AUTO_CREATE);
        mContext.startService(intent);
    }

    private void initView(View view) {
        mBottomListContent = view.findViewById(R.id.bottom_list_content);
        mBottomListColection = view.findViewById(R.id.bottom_sheet_bar_play);
        mBottomListClear = view.findViewById(R.id.bottom_sheet_bar_clear);
        mBottomListTitleSize = view.findViewById(R.id.bottom_list_title_size);
    }

}


