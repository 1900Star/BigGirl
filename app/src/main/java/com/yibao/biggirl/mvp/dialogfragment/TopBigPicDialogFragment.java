package com.yibao.biggirl.mvp.dialogfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseDialogFragment;
import com.yibao.biggirl.model.girl.DownGrilProgressData;
import com.yibao.biggirl.util.AnimationUtil;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.ImageUitl;
import com.yibao.biggirl.util.NetworkUtil;
import com.yibao.biggirl.util.SnakbarUtil;
import com.yibao.biggirl.view.ProgressBtn;
import com.yibao.biggirl.view.ZoomImageView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/30 13:27
 *
 * @author Stran
 */
public class TopBigPicDialogFragment
        extends BaseDialogFragment {

    private View mView;
    private ProgressBtn mPb;

    @Override
    public View getViews() {
        mView = LinearLayout.inflate(getActivity(), R.layout.dialog_big_girl, null);
        initView();
        getProgress();
        return mView;
    }

    public static TopBigPicDialogFragment newInstance(String url) {
        TopBigPicDialogFragment fragment = new TopBigPicDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initView() {
        LinearLayout topPicContent = mView.findViewById(R.id.top_big_pic_content);
        TextView save = mView.findViewById(R.id.tv_save);
        mPb = mView.findViewById(R.id.pb_toppic);
        mPb.setMax(MAX_DOWN_PREGRESS);
        ZoomImageView view = ImageUitl.creatZoomView(getActivity());
        String url = getArguments().getString("url");
        ImageUitl.loadPic(getActivity(), url, view);
        view.setOnClickListener(view1 -> TopBigPicDialogFragment.this.dismiss());
        save.setOnClickListener(view12 -> savePic(url));
        AnimationUtil.applyBobbleAnim(topPicContent);
        topPicContent.addView(view);


    }

    private void savePic(String url) {
        // 网络检查
        boolean isConnected = NetworkUtil.isNetworkConnected(getActivity());
        if (isConnected) {
            ImageUitl.savePic(url, Constants.FIRST_DWON)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(integer -> {
                        if (integer == Constants.EXISTS) {
                            SnakbarUtil.picAlreadyExists(mView);
                        }
                    });
        } else {
            SnakbarUtil.netErrors(mView);
        }
    }


    //Rxbus接收下载进度 ，设置progress进度

    public void getProgress() {
        mDisposable.add(mApplication.bus()
                .toObserverable(DownGrilProgressData.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> TopBigPicDialogFragment.this.setProgress(data.getProgress(),
                        data.getType())));
    }

    private void setProgress(int progress, int type) {
        mPb.setProgress(progress);
        if (type == Constants.FIRST_DWON && progress == MAX_DOWN_PREGRESS) {
            //将下载的图片插入到系统相册
            ImageUitl.insertImageToPhotos().subscribe(aBoolean -> {
                if (aBoolean) {
                    SnakbarUtil.showSuccessView(mView);
                } else {
                    SnakbarUtil.showDownPicFail(mView);
                }
            });
        }
    }
}
