package com.markjmind.uni.thread.aop;

/**
 * Created by Muabe on 2018-05-11.
 */

public abstract class AopOnPostAdapter implements AopListener{
    @Override
    public void beforeOnPre() {}

    @Override
    public void afterOnPre() {}

    @Override
    public void beforeOnLoad() {}

    @Override
    public void afterOnLoad() {}

    @Override
    public void beforeOnCancel() {}

    @Override
    public void afterOnCancel() {}

    @Override
    public void beforeOnException(Exception e) {}

    @Override
    public void afterOnException(Exception e) {}
}
