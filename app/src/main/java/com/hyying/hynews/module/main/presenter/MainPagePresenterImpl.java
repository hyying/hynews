package com.hyying.hynews.module.main.presenter;

import com.hyying.hynews.base.BaseRequestCallBack;
import com.hyying.hynews.bean.Item;
import com.hyying.hynews.http.NetworkConfig;
import com.hyying.hynews.module.main.interator.MainPageInterator;
import com.hyying.hynews.view.IMainPageView;

import java.util.List;

public class MainPagePresenterImpl implements IMainPagePresenter, BaseRequestCallBack<List<Item>> {
    private IMainPageView mMainPageView;
    private MainPageInterator mPageInterator;

    public MainPagePresenterImpl(IMainPageView mainPageView) {
        mMainPageView = mainPageView;
        mPageInterator = new MainPageInterator();
    }

    @Override
    public void getMainItems() {
        mPageInterator.getMainItem(this, "1", NetworkConfig.DEFAULT_PAGE_SIZE);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onSuccess(List<Item> items) {
        mMainPageView.updateMainPage(items);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onBefore() {

    }
}
