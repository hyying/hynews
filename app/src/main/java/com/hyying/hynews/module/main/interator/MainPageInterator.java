package com.hyying.hynews.module.main.interator;

import com.hyying.hynews.base.BaseRequestCallBack;
import com.hyying.hynews.bean.Item;
import com.hyying.hynews.http.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class MainPageInterator {
    public Subscription getMainItem(final BaseRequestCallBack<List<Item>> requestCallBack, String page, String size) {
        Observable<List<Item>> observable = RetrofitManager.getInstance().getMainItem(page, size);
        return observable.subscribe(new Subscriber<List<Item>>() {
            @Override
            public void onCompleted() {
                requestCallBack.onComplete();
            }

            @Override
            public void onError(Throwable e) {
                List<Item> itemList = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    Item item = new Item();
                    item.setType("topic");
                    item.setTitle("哈哈很多");
                    item.setImage_url("http://pic1.bantangapp.com/topic/201608/10/web_1470805583789_57548_95085");
                    item.setComment_num("28");
                    itemList.add(item);
                }
                onNext(itemList);
            }

            @Override
            public void onNext(List<Item> items) {
                requestCallBack.onSuccess(items);
            }
        });
    }

}
