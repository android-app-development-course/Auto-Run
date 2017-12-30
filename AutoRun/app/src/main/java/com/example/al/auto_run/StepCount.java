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
                notifyListener(this.count);
            }else{
                notifyListener(1);
            }
        }
        else count=1;
    }

    public void initListener(StepValuePassListener listener) {
        this.mStepValuePassListener = listener;
    }

    public void notifyListener(int plussteps) {
        if (this.mStepValuePassListener != null) {
            this.mStepValuePassListener.stepChanged(plussteps, state);
        }
    }


    /*public void setSteps(int initValue) {
        this.mCount_walk = initValue;
        this.mCount_run =initValue;
        this.mCount_ride=initValue;
        this.count=0;
        timeOfLastPeak = 0;
        timeOfThisPeak = 0;
        notifyListener();
    }*/
}
