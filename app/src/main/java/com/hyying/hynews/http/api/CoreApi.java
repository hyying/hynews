package com.hyying.hynews.http.api;

import com.hyying.hynews.bean.Item;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

public interface CoreApi {
    @GET("mainpage")
    Observable<List<Item>> getMainItem(@Header("Cache-Control") String cacheControl,
                                       @Query("page") String page, @Query("size") String size);
}
