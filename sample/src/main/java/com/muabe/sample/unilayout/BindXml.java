package com.muabe.sample.unilayout;

import android.widget.TextView;

import com.markjmind.uni.UniTask;
import com.markjmind.uni.mapper.annotiation.GetView;
import com.markjmind.uni.mapper.annotiation.Progress;
import com.markjmind.uni.progress.UniProgress;
import com.markjmind.uni.thread.CancelAdapter;
import com.markjmind.uni.thread.LoadEvent;
import com.muabe.sample.R;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-10-20
 */

@Progress(mode= UniProgress.VIEW, res= R.layout.default_progress)
public class BindXml extends UniTask {

    @GetView
    TextView txt_xml_bind;

    @Override
    public void onPre() {
        txt_xml_bind.setText("BindXml onPre");
    }

    @Override
    public void onLoad(LoadEvent event, CancelAdapter cancelAdapter) throws Exception {
        Thread.sleep(2000);
    }

    @Override
    public void onPost() {
        txt_xml_bind.setText("BindXml onPost");
    }
}
