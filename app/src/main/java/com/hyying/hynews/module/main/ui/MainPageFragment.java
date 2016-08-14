package com.hyying.hynews.module.main.ui;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyying.hynews.R;
import com.hyying.hynews.bean.Item;
import com.hyying.hynews.module.main.presenter.IMainPagePresenter;
import com.hyying.hynews.module.main.presenter.MainPagePresenterImpl;
import com.hyying.hynews.view.IMainPageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainPageFragment extends Fragment implements IMainPageView {
    @BindView(R.id.testmain_recyclerview)
    public RecyclerView mRecyclerView;
    private List<Item> mItemList = new ArrayList<>();
    private MainPageAdapter mMainPageAdapter;
    private IMainPagePresenter mMainPagePresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.base_recyclerview, container, false);
        ButterKnife.bind(this, rootView);
        initRecyclerView();
        initData();
        return rootView;
    }

    private void initData() {
        mMainPagePresenter = new MainPagePresenterImpl(this);
        mMainPagePresenter.getMainItems();
    }

    @TargetApi(16)
    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setRecycleChildrenOnDetach(false);
        mRecyclerView.setLayoutManager(layoutManager);
        mMainPageAdapter = new MainPageAdapter(getActivity(), mItemList);
        mRecyclerView.setAdapter(mMainPageAdapter);
    }

    @Override
    public void updateMainPage(List<Item> items) {
        mItemList.addAll(items);
        mMainPageAdapter.notifyDataSetChanged();
    }
}
