package gr.sullenart.ads;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Date;

public class AdsManager  {
    private String adUnitId = "ad unit ";

    private AdView adView;

    private int adHeight = 0;

    public int getAdHeight() {
        return adHeight;
    }

    public void addAdsView(Activity activity, RelativeLayout layout,
                           RelativeLayout.LayoutParams params) {
        int screenLayout = activity.getResources().getConfiguration().screenLayout;
        if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= 4) {
            adView = new AdView(activity);
            adView.setAdSize(AdSize.LEADERBOARD);
            adHeight = 90; // 728x90 size for xlarge screens (>= 960dp x 720dp)
        }
        else if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 3) {
            adView = new AdView(activity);
            adView.setAdSize(AdSize.BANNER);
            adHeight = 75; // 468x60 size for large screens (>= 640dp x 480dp)
        }
        else {
            adView = new AdView(activity);
            adView.setAdSize(AdSize.BANNER);
            adHeight = 75; // 320x50 size for normal (>= 470dp x 320dp) and small screens
        }
        adView.setAdUnitId(adUnitId);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.e(AdsManager.class.getName(), "Failed to receive ad (" + errorCode + ")");
            }
            @Override
            public void onAdLoaded() {

            }
        });

        layout.addView(adView, params);
        AdRequest request = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("5EA5B374B0B6F1201A685AACAC300DDD")
                .build();
        adView.loadAd(request);
    }


    public void addAdsView(Activity activity, LinearLayout layout) {
        int screenLayout = activity.getResources().getConfiguration().screenLayout;
        if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= 4) {
            adView = new AdView(activity);
            adView.setAdSize(AdSize.LEADERBOARD);
            adHeight = 90; // 728x90 size for xlarge screens (>= 960dp x 720dp)
        }
        else if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 3) {
            adView = new AdView(activity);
            adView.setAdSize(AdSize.BANNER);
            adHeight = 75; // 468x60 size for large screens (>= 640dp x 480dp)
        }
        else {
            adView = new AdView(activity);
            adView.setAdSize(AdSize.BANNER);
            adHeight = 75; // 320x50 size for normal (>= 470dp x 320dp) and small screens
        }
        adView.setAdUnitId(adUnitId);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.e(AdsManager.class.getName(), "Failed to receive ad (" + errorCode + ")");
            }
            @Override
            public void onAdLoaded() {

            }
        });

        layout.addView(adView);
        AdRequest request = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("5EA5B374B0B6F1201A685AACAC300DDD")
                .build();
        adView.loadAd(request);
    }

    public void removeAdView(RelativeLayout layout) {
        if (adView != null) {
            layout.removeView(adView);
            adHeight = 0;
            adView = null;
        }
    }

    public void showAdd(boolean show) {
        if (show) {
            adView.setVisibility(View.VISIBLE);
        }
        else {
            adView.setVisibility(View.GONE);
        }
    }

    private InterstitialAd interstitial;

    public void showInterstitial(Activity activity, String interstitialAdUnitId) {
        final String interstitialAdTimeKey = "InterstitialAdTime";
        final SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(activity.getBaseContext());
        long adTime = preferences.getLong(interstitialAdTimeKey, 0);
        long now = (new Date()).getTime();
        long dayMs = 24*3600*1000;
        if (now - adTime < dayMs) {
            return;
        }
        interstitial = new InterstitialAd(activity);
        interstitial.setAdUnitId(interstitialAdUnitId);
        // Create ad request.
        AdRequest adRequest = new AdRequest.Builder().build();
        // Begin loading your interstitial.
        interstitial.loadAd(adRequest);
        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                try {
                    if (interstitial.isLoaded()) {
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putLong(interstitialAdTimeKey, (new Date()).getTime());
                        edit.commit();
                        interstitial.show();
                    }
                }
                catch(Exception ex) {
                }
            }
        });
    }

}