/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.markjmind.uni.progress;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-02-24
 */
class ProgressView implements ProgressBuilder.ProgressInterface {
    private ViewGroup progressLayout;
    private boolean isShowing;
    private ViewGroup parents;

    public ProgressView(ViewGroup layout, ViewGroup parents){
        reset(layout, parents);
    }

    void reset(ViewGroup layout, ViewGroup parents){
        this.progressLayout = layout;
        this.parents = parents;
    }

    @Override
    public boolean isShowing() {
        return isShowing;
    }

    @Override
    public synchronized void show(View view) {
        if(!isShowing) {
            isShowing = true;
            if(view!=null) {
                progressLayout.addView(view);
            }
            AlphaAnimation alphaAnimation = new AlphaAnimation(0f,1f);
            alphaAnimation.setDuration(300);
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    progressLayout.clearAnimation();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            progressLayout.setAnimation(alphaAnimation);
            parents.addView(progressLayout);
        }
    }

    @Override
    public synchronized void dismiss() {
        if(isShowing) {
            if(progressLayout !=null) {
                AlphaAnimation alphaAnimation = new AlphaAnimation(1f,0f);
                alphaAnimation.setDuration(300);
                alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        progressLayout.clearAnimation();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                if(progressLayout.getChildCount() > 0){
                    progressLayout.getChildAt(0).clearAnimation();
                    progressLayout.getChildAt(0).setAnimation(alphaAnimation);
                }
                progressLayout.setAnimation(alphaAnimation);
                progressLayout.removeAllViews();
                parents.removeView(progressLayout);
            }
            isShowing = false;
        }
    }

    @Override
    public int getMode() {
        return UniProgress.VIEW;
    }
}
