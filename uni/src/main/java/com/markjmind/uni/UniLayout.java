package com.markjmind.uni;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.markjmind.uni.common.Store;
import com.markjmind.uni.progress.ProgressBuilder;
import com.markjmind.uni.progress.UniProgress;
import com.markjmind.uni.thread.CancelAdapter;
import com.markjmind.uni.thread.LoadEvent;
import com.markjmind.uni.thread.aop.CancelAop;
import com.markjmind.uni.thread.aop.UniAop;

/**
 * <br>捲土重來<br>
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
  * @since 2016-01-28
 */
public class UniLayout extends FrameLayout implements UniInterface{

    private View view;
    private UniTask uniTask;
    private ViewGroup frameLayout;
    public Store<?> param;
    public ProgressBuilder progress = new ProgressBuilder();
    private UniAop aop = new UniAop();

    public UniLayout(Context context) {
        super(context);
        init(context);
    }

    public UniLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public UniLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        frameLayout = new FrameLayout(context);
        super.addView(frameLayout);
        this.progress.setParents(this);
        param = new Store<>();
    }

//    void initTask(UniTask task, Store<?> par, ProgressBuilder pro){
//        this.uniTask = task;
//        this.param = par;
//        this.progress = pro;
//        pro.setParents(this);
//        addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
//            @Override
//            public void onViewAttachedToWindow(View v) {
//                uniTask.getCancelObservable().setAttached(true);
//            }
//
//            @Override
//            public void onViewDetachedFromWindow(View v) {
//                param.clear();
//                progress.param.clear();
//                uniTask.getCancelObservable().setAttached(false);
//                uniTask.getCancelObservable().cancelAll();
//            }
//        });
//    }

    void initTask(UniTask task){
        this.uniTask = task;
        addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                uniTask.getCancelObservable().setAttached(true);
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                param.clear();
                progress.param.clear();
                uniTask.getCancelObservable().setAttached(false);
                uniTask.getCancelObservable().cancelAll();
            }
        });
    }

    void syncAttribute(Store<?> par, ProgressBuilder pro){
        this.param = par;
        this.progress = pro;
    }

    public void setAsync(boolean isAsync){
        this.uniTask.setAsync(isAsync);
    }

    public boolean isAsync(){
        return this.uniTask.isAsync();
    }

    UniTask getUniTask(){
        return uniTask;
    }

    public void setLayout(View view) {
        this.removeAllViews();
        this.view = view;
        super.addView(this.view);
        this.setLayoutParams(this.view.getLayoutParams());
    }

//    public void bind(UniTask uniTask){
//        uniTask.mapper.setInjectParents(UniTask.class);
//        uniTask.setEnableMapping(true);
//        uniTask.syncUniLayout(this, uniTask.param, uniTask.progress, uniTask, uniTask.getUniInterface(), null);
//    }

    /*************************************************** execute 관련 *********************************************/
    public void setCancelAop(CancelAop cancelAop){
        aop.setCancelAop(cancelAop);
    }

    public UniAop getAop(){
        return aop;
    }

    public TaskController getTask(){
        if(uniTask==null){
            uniTask = new UniTask(true);
            uniTask.mapper.setInjectParents(UniLayout.class);
            uniTask.setUniInterface(this);
            uniTask.reverseBind(this, this, param, progress);
        }
        return uniTask.getTask();
    }

    /*************************************************** UniTask Interface 관련 *********************************************/
    @Override
    public void onBind() {

    }

    @Override
    public void onPre() {

    }

    @Override
    public void onLoad(LoadEvent event, CancelAdapter cancelAdapter) throws Exception {

    }

    @Override
    public void onUpdate(Object value, CancelAdapter cancelAdapter) {

    }

    @Override
    public void onPost() {

    }

    @Override
    public void onPostFail(String message, Object arg) {

    }

    @Override
    public void onException(Exception e) {

    }

    @Override
    public void onCancelled(boolean attached) {

    }

    public UniProgress getProgeress(){
        return progress.get();
    }

    public void setProgress(int mode, UniProgress pro){
        progress.set(mode, pro);
    }
}
