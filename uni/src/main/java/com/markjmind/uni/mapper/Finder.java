/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.markjmind.uni.mapper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;

/**
 * <br>捲土重來<br>
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-02-02
 */
public class Finder {
    private FinderInterFace finderInterFace;
    private Context context;

    private abstract class FinderInterFace{
        private Object obj;

        public FinderInterFace(Object obj){
            this.obj = obj;
        }

        public <T>T getFinder(Class<T> clz){
            return (T)obj;
        }

        public abstract View findViewById(int id);
    }

    public Finder(View finder){
        this.context = finder.getContext();
        final Class<View> clz = (Class<View>)finder.getClass();
        this.finderInterFace = new FinderInterFace(finder){
            @Override
            public View findViewById(int id) {
                return getFinder(clz).findViewById(id);
            }
        };
    }

    public Finder(Activity finder){
        this.context = finder;
        final Class<Activity> clz = (Class<Activity>)finder.getClass();
        this.finderInterFace = new FinderInterFace(finder){
            @Override
            public View findViewById(int id) {
                return getFinder(clz).findViewById(id);
            }
        };
    }

    public Finder(Dialog finder){
        this.context = finder.getContext();
        final Class<Dialog> clz = (Class<Dialog>) finder.getClass();
        this.finderInterFace = new FinderInterFace(finder){
            @Override
            public View findViewById(int id) {
                return getFinder(clz).findViewById(id);
            }
        };
    }

    public View findViewById(int id){
        return finderInterFace.findViewById(id);
    }

}
