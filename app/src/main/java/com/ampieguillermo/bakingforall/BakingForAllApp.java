package com.ampieguillermo.bakingforall;

import android.app.Application;
import android.os.StrictMode;
import com.squareup.leakcanary.LeakCanary;

public class BakingForAllApp extends Application {

  /**
   * Called when the application is starting, before any activity, service,
   * or receiver objects (excluding content providers) have been created.
   * Implementations should be as quick as possible (for example using
   * lazy initialization of state) since the time spent in this function
   * directly impacts the performance of starting the first activity,
   * service, or receiver in a process.
   * If you override this method, be sure to call super.onCreate().
   */
  @Override
  public void onCreate() {
    super.onCreate();
    enableStrictMode();
    enableLeakCanary();
  }

  private void enableStrictMode() {
    if (BuildConfig.DEBUG) {
      // Detect for blocking the UI thread
      StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy
          .Builder()
          .detectAll()
          .penaltyLog()
          .build());

      // Detect for memory leaks
      StrictMode.setVmPolicy(new StrictMode.VmPolicy
          .Builder()
          .detectAll()
          .penaltyLog()
          .build());
    }
  }

  private void enableLeakCanary() {
    if (LeakCanary.isInAnalyzerProcess(this)) {
      // This process is dedicated to LeakCanary for heap analysis.
      // You should not init your app in this process.
      return;
    }
    LeakCanary.install(this);
  }
}
