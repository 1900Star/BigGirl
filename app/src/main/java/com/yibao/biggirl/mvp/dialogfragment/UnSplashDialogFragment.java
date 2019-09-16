package com.yibao.biggirl.mvp.dialogfragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseDialogFra;
import com.yibao.biggirl.mvp.gank.girls.GirlsAdapter;
import com.yibao.biggirl.util.DialogUtil;
import com.yibao.biggirl.util.UrlUtil;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/30 13:27
 */
public class UnSplashDialogFragment
        extends BaseDialogFra<String> {

    private View mView;
    private FloatingActionButton mFab;
    private LinearLayout mContentView;
    private SwipeRefreshLayout mSwipeRefresh;
    private List<String> mStringList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStringList = UrlUtil.getUnsplashUrl(mList);

    }

    @Override
    public Dialog getFagDialog() {
        mView = LinearLayout.inflate(getActivity(), R.layout.girls_frag, null);
        initView();
        initData();
        return DialogUtil.getDialogFag(getActivity(), mView);
    }


    private void initData() {
        GirlsAdapter adapter = new GirlsAdapter(getActivity(), mStringList);
        RecyclerView recyclerView = getRecyclerView(mFab, 2, adapter);

        mContentView.addView(recyclerView);
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    protected void refreshData() {
                mSwipeRefresh.setRefreshing(false);
    }

    @Override
    protected void loadMoreData() {

    }

    private void initView() {
        mSwipeRefresh = mView.findViewById(R.id.swipe_refresh);
        mFab = mView.findViewById(R.id.fab_fag);
        mContentView = mView.findViewById(R.id.fag_content);
        mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setRefreshing(true);

    }

    public static UnSplashDialogFragment newInstance() {

        return new UnSplashDialogFragment();
    }

}
