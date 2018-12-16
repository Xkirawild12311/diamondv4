package com.proyect.prado.diamondsv2.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton instanciaVolley;
    private RequestQueue request;
    private ImageLoader mImageLoader;
    private static Context mcontexto;

    private VolleySingleton(Context context) {

        mcontexto = context;
        request = getRequestQueue();


        mImageLoader = new ImageLoader(request,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });

    }

    public static synchronized VolleySingleton getInstanciaVolley(Context context) {

        if (instanciaVolley == null) {
            instanciaVolley = new VolleySingleton(context);
        }
        return instanciaVolley;
    }

    public RequestQueue getRequestQueue() {
        if (request == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            request = Volley.newRequestQueue(mcontexto.getApplicationContext());
        }
        return request;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}
