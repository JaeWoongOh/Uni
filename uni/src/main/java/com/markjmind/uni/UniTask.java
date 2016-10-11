package com.markjmind.uni;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.markjmind.uni.common.Store;
import com.markjmind.uni.mapper.UniMapper;
import com.markjmind.uni.mapper.annotiation.LayoutInjector;
import com.markjmind.uni.mapper.annotiation.adapter.GetViewAdapter;
import com.markjmind.uni.mapper.annotiation.adapter.OnClickAdapter;
import com.markjmind.uni.mapper.annotiation.adapter.ParamAdapter;
import com.markjmind.uni.mapper.annotiation.adapter.ProgressAdapter;
import com.markjmind.uni.progress.ProgressBuilder;
import com.markjmind.uni.thread.CancelAdapter;
import com.markjmind.uni.thread.CancelObservable;
import com.markjmind.uni.thread.LoadEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-04
 */
public class UniTask implements UniInterface {
    private UniLayout uniLayout;
    public UniMapper mapper;
    public Store<?> param;
    public ProgressBuilder progress;


    private Context context;
    private boolean isAsync;

    private TaskController taskController;
    private CancelObservable cancelObservable;
    private boolean enableMapping; //매필을 할수 있는지 여부(UniAsyncTask는 매핑을 안함)

    public UniTask() {
        this(false);
    }

    /**
     * Bind를 거치지 않고 내부적으로 UniTask를 생성해서 할 경우
     * enbaleMapping true로 설정
     * @param enableMapping
     */
    UniTask(boolean enableMapping) {
        uniLayout = null;
        setEnableMapping(enableMapping);
        mapper = new UniMapper();
        isAsync = true;
        param = new Store<>();
        progress = new ProgressBuilder();
        cancelObservable = new CancelObservable();
        taskController = new TaskController(this);
    }

    private void beforeBind(){
        mapper.addSubscriptionOnInit(new ParamAdapter(param));
        if(enableMapping) {
            mapper.addSubscriptionOnInit(new ProgressAdapter(progress));
        }
        mapper.injectSubscriptionOnInit();
    }

    private void afterBind(LayoutInflater inflater, ViewGroup container){
        if (enableMapping) {
            mapper.addSubscriptionOnStart(new LayoutInjector(inflater, uniLayout, container));
            mapper.addSubscriptionOnStart(new GetViewAdapter());
            mapper.addSubscriptionOnStart(new OnClickAdapter());
            mapper.injectSubscriptionOnStart();
        }
    }

    private void binding(LayoutInflater inflater, ViewGroup container){
        beforeBind();
        taskController.getUniInterface().onBind();
        afterBind(inflater, container);
    }

    /**
     * Execute는 여러번 일어날수있다는것에 주의
     */
    void beforeExecute() {

    }

    void beforeOnPre(){

    }
    void afterOnPre(){

    }
    void beforeOnLoad(){

    }
    void afterOnLoad(){

    }
    void beforeOnPost(){

    }
    void afterOnPost(){

    }
    void beforeOnCancel(){

    }
    void afterOnCancel(){

    }
    void beforeOnException(){

    }
    void afterOnException(){

    }

    Object mappingObj;
    LayoutInflater inflater; ViewGroup container;


    void bind(Object mappingObj, UniLayout uniLayout, LayoutInflater inflater, ViewGroup container){
        this.uniLayout = uniLayout;
        this.context = uniLayout.getContext();
        this.mapper.reset(this.uniLayout, mappingObj);
        this.uniLayout.initTask(this);
        if (inflater == null) {
            inflater = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        }
        if(container==null){
            container = uniLayout;
        }
        this.progress.setParents(uniLayout);

        binding(inflater, container);
    }

    public void bindLayout(UniLayout uniLayout) {
        setEnableMapping(true); //바인드가 되면 매핑을 할수있다.
        uniLayout.param = this.param;
        uniLayout.progress = this.progress;
        mapper.setInjectParents(UniLayout.class);
        this.bind(this, uniLayout, null, null);
    }

    public void bindFragment(UniFragment uniFragment){
        setEnableMapping(true); //바인드가 되면 매핑을 할수있다.
        uniFragment.param = this.param;
        uniFragment.progress = this.progress;
        mapper.setInjectParents(UniFragment.class);
        uniFragment.setUniTask(this);
    }

    public void bindDialog(UniDialog uniDialog){
        setEnableMapping(true); //바인드가 되면 매핑을 할수있다.
        uniDialog.param = this.param;
        uniDialog.progress = this.progress;
        mapper.setInjectParents(UniDialog.class);
        uniDialog.setUniTask(this);
    }

    /**
     * UniLayout에다 task를 입히는 방법
     * @param uniLayout
     */
    public void bind(UniLayout uniLayout) {
        setEnableMapping(true); //바인드가 되면 매핑을 할수있다.
        mapper.setInjectParents(UniTask.class);

        this.bind(this, uniLayout, null, null);
    }

    void reverseBind(Object mappingObj, UniLayout uniLayout, Store<?> param, ProgressBuilder progress){
        this.param = param;
        this.progress = progress;
        this.progress.setParents(uniLayout);
        bind(mappingObj, uniLayout, null, null);
    }

    CancelObservable getCancelObservable() {
        return cancelObservable;
    }

    void setEnableMapping(boolean enable) {
        this.enableMapping = enable;
    }

    /***************************************************************************************************************
     * Uni 외부지원 함수 관련
     **************************************************************************************************************/

    public View findViewById(int id) {
        return uniLayout.findViewById(id);
    }

    public View findViewWithTag(Object tag) {
        return uniLayout.findViewWithTag(tag);
    }

    public UniLayout getUniLayout() {
        return uniLayout;
    }

    public void setAsync(boolean isAsync) {
        this.isAsync = isAsync;
    }

    public boolean isAsync() {
        return this.isAsync;
    }


    /***************************************************************************************************************
     * Context 함수 관련
     **************************************************************************************************************/
    public Context getContext() {
        return context;
    }

    public Resources getResource() {
        return context.getResources();
    }

    public Context getApplicationContext() {
        return context.getApplicationContext();
    }

    public PackageManager getPackageManager() {
        return context.getPackageManager();
    }

    /***************************************************************************************************************
     * execute 관련
     **************************************************************************************************************/
    public TaskController getTask() {
        if (enableMapping) {
            taskController.init(this, cancelObservable);
        } else {
            taskController.init(null, cancelObservable);
        }
        return taskController;
    }

    public void setUniInterface(UniInterface uniInterface) {
        taskController.setUniInterface(uniInterface);
    }

    public UniInterface getUniInterface() {
        return taskController.getUniInterface();
    }

    /***************************************************************************************************************
     * UniTask Interface 관련
     **************************************************************************************************************/
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


}

