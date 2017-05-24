package com.yibao.biggirl.base;


import android.support.v4.app.Fragment;


/**
 * 作者：Stran on 2017/3/29 15:24
 * 描述：${TODO}
 * 邮箱：strangermy@outlook.com
 */
public abstract class BaseFragment
        extends Fragment
{
/*
    public LoadingPager mLoadingPager;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {

        if (mLoadingPager == null) {
            startLoad();
            mLoadingPager = new LoadingPager(MyApplication.getIntstance()) {
                @Override
                public LoadedResult initData() {
                    return BaseFragment.this.initData();
                }

                @Override
                public View initSuccessView() {
                    return BaseFragment.this.initSuccessView();
                }
            };
        }

        return mLoadingPager;
    }


    protected abstract LoadingPager.LoadedResult initData();

    protected abstract View initSuccessView();

    protected abstract void startLoad();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    *//**
     * 根据请求回来的数据,返回具体的LoadedResult类型值
     *
     * @param resResult
     * @return
     *//*
    public LoadingPager.LoadedResult checkResResult(Object resResult) {
        if (resResult == null) {
            return LoadingPager.LoadedResult.EMPTY;
        }
        //list
        if (resResult instanceof List) {
            if (((List) resResult).size() == 0) {
                return LoadingPager.LoadedResult.EMPTY;
            }
        }
        //map
        if (resResult instanceof Map) {
            if (((Map) resResult).size() == 0) {
                return LoadingPager.LoadedResult.EMPTY;
            }
        }
        return LoadingPager.LoadedResult.SUCCESS;
    }*/

}
