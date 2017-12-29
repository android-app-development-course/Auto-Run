package com.example.al.auto_run;

import com.example.al.auto_run.custominterface.StepCountListener;
import com.example.al.auto_run.custominterface.StepValuePassListener;

/**
 * Created by finnfu on 16/9/27.
 */

/*
* 根据StepDetector传入的步点"数"步子
* */
public class StepCount implements StepCountListener {

    private int mCount_walk = 0;
    private int mCount_run =0;
    private int mCount_ride=0;
    private int count=0;
    private StepValuePassListener mStepValuePassListener;
    private long timeOfLastPeak = 0;
    private long timeOfThisPeak = 0;
    private int state=0;
    /*
    * 连续走十步才会开始计步
    * 连续走了9步以下,停留超过3秒,则计数清空
    * */
    @Override
    public void countStep(int stateofsport) {
        if(this.state!=stateofsport)count=1;
        this.state = stateofsport;
        this.timeOfLastPeak = this.timeOfThisPeak;
        this.timeOfThisPeak = System.currentTimeMillis();
        if (this.timeOfThisPeak - this.timeOfLastPeak <= 3000L){
            if(this.count<9){
                this.count++;
            }else if(this.count == 9){
                this.count++;
                if(this.state==1)this.mCount_walk += this.count;
                if(this.state==2)this.mCount_run +=this.count;
                if(this.state==3)this.mCount_ride+=this.count;
                notifyListener();
            }else{
                if(this.state==1) this.mCount_walk++;
                if(this.state==2) this.mCount_run++;
                if(this.state==3)this.mCount_ride++;
                notifyListener();
            }
        }
        else count=1;
    }

    public void initListener(StepValuePassListener listener) {
        this.mStepValuePassListener = listener;
    }

    public void notifyListener() {
        if (this.mStepValuePassListener != null) {
            if(this.state==1)
                this.mStepValuePassListener.stepChanged(this.mCount_walk, state);
            if(this.state==2)
                this.mStepValuePassListener.stepChanged(this.mCount_run,state);
            if(this.state==3)
                this.mStepValuePassListener.stepChanged(this.mCount_ride,state);
        }
    }


    public void setSteps(int initValue) {
        this.mCount_walk = initValue;
        this.mCount_run =initValue;
        this.mCount_ride=initValue;
        this.count=0;
        timeOfLastPeak = 0;
        timeOfThisPeak = 0;
        notifyListener();
    }
}
