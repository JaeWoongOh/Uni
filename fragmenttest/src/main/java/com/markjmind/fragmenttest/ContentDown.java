package com.markjmind.fragmenttest;

import android.widget.Button;

import com.markjmind.uni.UniTask;
import com.markjmind.uni.mapper.annotiation.GetView;
import com.markjmind.uni.mapper.annotiation.Layout;
import com.markjmind.uni.mapper.annotiation.Progress;
import com.markjmind.uni.progress.UniProgress;
import com.markjmind.uni.thread.CancelAdapter;
import com.markjmind.uni.thread.LoadEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-03
 */
@Layout(R.layout.content)
@Progress(type = SimpleProgress.class, mode = UniProgress.VIEW)
public class ContentDown extends UniTask{
    @GetView
    Button button3;

    @Override
    public void onLoad(LoadEvent loadEvent, CancelAdapter cancelAdapter) throws Exception {
        Thread.sleep(3000);
    }

    @Override
    public void onPost() {
        button3.setText("끝");
    }
}