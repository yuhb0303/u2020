package com.jakewharton.u2020.data;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.Uri;
import com.jakewharton.u2020.data.api.ApiModule;
import com.squareup.okhttp.HttpResponseCache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import java.io.File;
import java.io.IOException;
import javax.inject.Singleton;
import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;

@Module(
    includes = ApiModule.class,
    complete = false,
    library = true
)
public class DataModule {
  private static final int CACHE_SIZE = 50 * 1024 * 1024; // 50MB

  @Provides @Singleton SharedPreferences provideSharedPreferences(Application app) {
    return app.getSharedPreferences("u2020", MODE_PRIVATE);
  }

  @Provides @Singleton OkHttpClient provideOkHttpClient(Application app) {
    return createOkHttpClient(app);
  }

  @Provides @Singleton Picasso providePicasso(Application app, OkHttpClient client) {
    return new Picasso.Builder(app)
        .downloader(new OkHttpDownloader(client))
        .listener(new Picasso.Listener() {
          @Override public void onImageLoadFailed(Picasso picasso, Uri uri, Exception e) {
            Timber.e(e, "Failed to load image: %s", uri);
          }
        })
        .build();
  }

  static OkHttpClient createOkHttpClient(Application app) {
    OkHttpClient client = new OkHttpClient();

    // Install an HTTP cache in the application cache directory.
    try {
      File cacheDir = new File(app.getCacheDir(), "http");
      HttpResponseCache cache = new HttpResponseCache(cacheDir, CACHE_SIZE);
      client.setResponseCache(cache);
    } catch (IOException e) {
      Timber.e(e, "Unable to install disk cache.");
    }

    return client;
  }
}
