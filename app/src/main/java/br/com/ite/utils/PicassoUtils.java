package br.com.ite.utils;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by alexandre.paravani on 2016-08-17.
 */
public class PicassoUtils {

    private static boolean singleton = false;

    public PicassoUtils(Context context) {

        if (singleton == false) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Interceptor.Chain chain) throws IOException {
                                    Request request = chain.request().newBuilder()
                                            .addHeader("User-Agent", "firefox").build();
                                    return chain.proceed(request);
                                }
                            }).build();

            Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(client));
            Picasso picasso = builder.build();
            Picasso.setSingletonInstance(picasso);
            singleton = true;
        }
    }
}