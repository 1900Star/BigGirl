package com.yibao.biggirl.view.music;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yibao.biggirl.R;
import com.yibao.biggirl.model.music.MusicLyrBean;
import com.yibao.biggirl.util.LogUtil;
import com.yibao.biggirl.util.StringUtil;

import java.util.ArrayList;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/9/14 01:16
 */
public class LyricsView
        extends TextView
{

    private        Paint                   mPaint;
    private        int                     mViewW;
    private        int                     mViewH;
    private        String                  mCurrentLrc;
    private static ArrayList<MusicLyrBean> mList;
    private static int                     centerLine;
    private        float                   mBigText;
    private        int                     mGreen;
    private        float                   smallText;
    private        int                     mWhite;
    private        int                     lineHeight;
    private        int                     duration;
    private        int                     progress;

    public LyricsView(Context context) {
        super(context);
        initView();
    }


    public LyricsView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        initView();
    }

    public LyricsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mGreen = getResources().getColor(R.color.colorGreen);
        mWhite = getResources().getColor(R.color.colorWhite);
        mBigText = getResources().getDimension(R.dimen.big_text);
        smallText = getResources().getDimension(R.dimen.small_text);
        lineHeight = getResources().getDimensionPixelSize(R.dimen.line_height);
        mPaint.setColor(mGreen);
        mPaint.setTextSize(mBigText);

        mList = new ArrayList<>();

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewW = w;
        mViewH = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mList == null) {
            drawSingLine(canvas);
        } else {

            drawMunitLine(canvas);
        }

    }

    private void drawMunitLine(Canvas canvas) {
        //        中间行y=中间行开始位置-移的距离

        int lineTime;
        //        最后一行居中
        if (centerLine == mList.size() - 1) {
            //            行可用时间 = 总进度 - 行开始时间
            lineTime = duration - mList.get(centerLine)
                                       .getStartTime();
        } else {
            //               其它行居中，
            //              行可用时间 = 下一行开始 时间 - 居中行开始 时间
            lineTime = mList.get(centerLine + 1)
                            .getStartTime() - mList.get(centerLine)
                                                   .getStartTime();
        }
        //          播放时间偏移 = 播放进度 - 居中开始时间
        int offsetTime = progress - mList.get(centerLine)
                                         .getStartTime();
        //           播放时间比 = 播放时间偏移/行可用时间
        float offsetTimePercent = offsetTime / (float) lineTime;

        //          y方向移动的距离 = 行高*播放时间比
        int offsetY = (int) (lineHeight * offsetTimePercent);
        //          中间行歌词
        String centerLrc = mList.get(centerLine)
                                .getContent();
        Rect bounds = new Rect();
        mPaint.getTextBounds(centerLrc, 0, centerLrc.length(), bounds);
        //          中间行 y view 高度一半 + text高度一半
        //          中间行y = 中间行开始 位置 - 移动的距离
        int centerY = mViewH / 2 + bounds.height() / 2 - offsetY;
        for (int i = 0; i < mList.size(); i++) {
            if (i == centerLine) {
                mPaint.setTextSize(mBigText);
                mPaint.setColor(mGreen);
            } else {

                mPaint.setTextSize(smallText);
                mPaint.setColor(mWhite);
            }
            mCurrentLrc = mList.get(i)
                               .getContent();
            float textW = mPaint.measureText(mCurrentLrc, 0, mCurrentLrc.length());

            float x = mViewW / 2 - textW / 2;
            float y = centerY + (i - centerLine) * lineHeight;
            canvas.drawText(mCurrentLrc, 0, mCurrentLrc.length(), x, y, mPaint);

        }

    }


    public void rollText(int progress, int duration) {
        if (mList == null || mList.size() == 0) {
            return;
        }

        this.progress = progress;
        this.duration = duration;
        int startTime = mList.get(mList.size() - 1)
                             .getStartTime();

        if (progress >= startTime) {
            centerLine = mList.size() - 1;
        } else {
            for (int i = 0; i < mList.size() - 1; i++) {
                if (progress >= mList.get(i)
                                     .getStartTime() && progress < mList.get(i + 1)
                                                                        .getStartTime())
                {
                    centerLine = i;
                    break;
                }
            }
        }
        invalidate();
    }

    public void setLrcFile(String path) {
        //    File lrcFile = StringUtil.getLrcFile(path);
        LogUtil.d("Path  " + path);
        mList = StringUtil.getLyrics(StringUtil.getLrcFile(path));
        if (mList.size() == 0 || mList == null) {

            LogUtil.d("检查歌词数据 ：***********" + "HHHHHHHH");
        } else {
            LogUtil.d("检查歌词数据 ：***********" + "Yes Is Ok");

        }
        //默认剧中行=0
        centerLine = 0;
    }


    private void drawSingLine(Canvas canvas) {
        Rect bounds = new Rect();
        mCurrentLrc = "正在加载歌词...";
        mPaint.getTextBounds(mCurrentLrc, 0, mCurrentLrc.length(), bounds);
        float x = mViewW / 2 - bounds.width() / 2;
        float y = mViewH / 2 + bounds.height() / 2;
        canvas.drawText(mCurrentLrc, 0, mCurrentLrc.length(), x, y, mPaint);
    }
}
