package com.hyying.hynews.http;


import android.util.Log;

import com.hyying.hynews.app.AppProfile;
import com.hyying.hynews.bean.Item;
import com.hyying.hynews.http.api.CoreApi;
import com.hyying.hynews.utils.LogUtils;
import com.hyying.hynews.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManager {

    private static final String TAG = "RetrofitManager";

    private static final long CONNECT_TIMEOUT = 30;
    private static RetrofitManager mRetrofitManager = null;

    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 30;
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    private static final String CACHE_CONTROL_NETWORK = "max-age=0";

    private static OkHttpClient mOkHttpClient;
    private static Retrofit retrofit;
    private CoreApi mCoreApi;


    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (mOkHttpClient == null) {
                    Cache cache = new Cache(new File(AppProfile.sContext.getCacheDir(), "HttpCache"),
                            1024 * 1024 * 100);

                    mOkHttpClient = new OkHttpClient.Builder().cache(cache)
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(mLoggingInterceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS).build();
                }
            }
        }
        return mOkHttpClient;
    }

    public static RetrofitManager getInstance() {
        if (mRetrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (mRetrofitManager == null) {
                    mRetrofitManager = new RetrofitManager();
                }
            }
        }
        return mRetrofitManager;
    }

    private RetrofitManager() {
        retrofit = new Retrofit.Builder().baseUrl(NetworkConfig.baseUrl)
                .client(getOkHttpClient()).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
    }

    private String getCacheControl() {
        return NetworkUtils.isConnected(AppProfile.sContext) ? CACHE_CONTROL_NETWORK : CACHE_CONTROL_CACHE;
    }

    private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtils.isConnected(AppProfile.sContext)) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                LogUtils.e(TAG, "no network");
            }
            Response originalResponse = chain.proceed(request);

            if (NetworkUtils.isConnected(AppProfile.sContext)) {
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .header("Content-Type", "application/json")
                        .removeHeader("Pragma").build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached," + CACHE_STALE_SEC)
                        .removeHeader("Pragma").build();
            }
        }
    };

    private Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            final Request request = chain.request();
            LogUtils.d("-----------------------开始打印请求数据-----------------------");
            if (request != null) {
                LogUtils.d(request.toString());
                Headers headers = request.headers();
                if (headers != null) {
                    LogUtils.d("headers : " + headers.toString());
                }
                RequestBody body = request.body();
                if (body != null) {
                    Buffer buffer = new Buffer();
                    body.writeTo(buffer);
                    String req = buffer.readByteString().utf8();
                    LogUtils.d("body : " + req);
                }
            }
            LogUtils.d("-----------------------结束打印请求数据-----------------------");

            final Response response = chain.proceed(request);
            final ResponseBody responseBody = response.body();
            final long contentLength = responseBody.contentLength();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(charset);
                } catch (UnsupportedCharsetException e) {
                    LogUtils.e(TAG, "Couldn't decode the response body; charset is likely malformed.");
                    return response;
                }
            }
            if (contentLength != 0) {
                LogUtils.d("-----------------------开始打印响应数据-----------------------");
                LogUtils.d(buffer.clone().readString(charset));
                LogUtils.d("-----------------------结束打印响应数据-----------------------");
            }

            return response;
        }
    };

    public CoreApi getCoreApi() {
        if (null == mCoreApi)
            mCoreApi = retrofit.create(CoreApi.class);
        return mCoreApi;
    }

    public Observable<List<Item>> getMainItem(String page, String size) {
        return getCoreApi().getMainItem(getCacheControl(), page, size).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
    }
}
