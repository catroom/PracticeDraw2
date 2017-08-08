package com.hencoder.hencoderpracticedraw2.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice12PathEffectView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path path = new Path();

    public Practice12PathEffectView(Context context) {
        super(context);
    }

    public Practice12PathEffectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice12PathEffectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint.setStyle(Paint.Style.STROKE);

        path.moveTo(50, 100);
        path.rLineTo(50, 100);
        path.rLineTo(80, -150);
        path.rLineTo(100, 100);
        path.rLineTo(70, -120);
        path.rLineTo(150, 80);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 使用 Paint.setPathEffect() 来设置不同的 PathEffect
        PathEffect pathEffect = new CornerPathEffect(20);
        paint.setPathEffect(pathEffect);
        // 第一处：CornerPathEffect
        canvas.drawPath(path, paint);
        canvas.save();
        canvas.translate(500, 0);

        // 第二处：DiscretePathEffect segmentLength 是用来拼接的每个线段的长度， deviation 是偏离量
        DiscretePathEffect discretePathEffect=new DiscretePathEffect(5,20);
        paint.setPathEffect(discretePathEffect);
        canvas.drawPath(path, paint);
        canvas.restore();
        canvas.save();
        canvas.translate(0, 200);

       //第一个参数 intervals 是一个数组，它指定了虚线的格式：
        // 数组中元素必须为偶数（最少是 2 个），按照「画线长度、空白长度、
        // 画线长度、空白长度」……的顺序排列，例如上面代码中的 20, 5, 10, 5
        // 就表示虚线是按照「画 20 像素、空 5 像素、画 10 像素、空 5 像素」的模式来绘制；
        // 第二个参数 phase 是虚线的偏移量
        paint.setPathEffect(new DashPathEffect(new float[]{20, 10, 5, 10}, 0));
        // 第三处：DashPathEffect
        canvas.drawPath(path, paint);
        canvas.restore();
        canvas.save();
        canvas.translate(500, 200);

//        shape 参数是用来绘制的 Path ； advance 是两个相邻的 shape 段之间的间隔，
//        不过注意，这个间隔是两个 shape 段的起点的间隔，而不是前一个的终点和后一个的起点的距离；
//        phase 和  DashPathEffect 中一样，是虚线的偏移；
//        最后一个参数 style，是用来指定拐弯改变的时候 shape 的转换方式。
        // 第四处：PathDashPathEffect

        Path dashPath = new Path();
        dashPath.addRect(0,0,10,10, Path.Direction.CCW);
//        dashPath.lineTo(40, 0);
        dashPath.close();
        PathDashPathEffect pathDashPathEffect = new PathDashPathEffect(dashPath, 50, 0, PathDashPathEffect.Style.MORPH);

//        Path dashPash=new Path();
//        dashPash.addCircle(10,10,5, Path.Direction.CW);
        paint.setPathEffect(pathDashPathEffect);
        canvas.drawPath(path, paint);
        canvas.restore();
        canvas.save();
        canvas.translate(0, 400);


        PathEffect dashEffect = new DashPathEffect(new float[]{20, 10}, 0);
        PathEffect discreteEffect = new DiscretePathEffect(20, 5);
        SumPathEffect pathEffect0 = new SumPathEffect(dashEffect, discreteEffect);
        paint.setPathEffect(pathEffect0);
        // 第五处：SumPathEffect
        canvas.drawPath(path, paint);
        canvas.restore();
        canvas.save();
        canvas.translate(500, 400);

        // 第六处：ComposePathEffect
        PathEffect dashEffect1 = new DashPathEffect(new float[]{20, 10}, 0);
        PathEffect discreteEffect2 = new DiscretePathEffect(20, 5);
        ComposePathEffect pathEffect3 = new ComposePathEffect(dashEffect1, discreteEffect2);
        paint.setPathEffect(pathEffect3);
        canvas.drawPath(path, paint);
        canvas.restore();
    }
}
