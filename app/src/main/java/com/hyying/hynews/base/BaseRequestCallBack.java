package com.hyying.hynews.base;

public interface BaseRequestCallBack<T> {
    void onError(Throwable e);

    void onSuccess(T t);

    void onComplete();

    void onBefore();
}
