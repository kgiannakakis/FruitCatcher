package gr.sullenart.ads;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class AdsManager implements AdListener {
    private String publisherId = "Your publisher id here";

    private AdView adView;

    private int adHeight = 0;

    public int getAdHeight() {
        return adHeight;
    }

    public void addAdsView(Activity activity, RelativeLayout layout,
                           RelativeLayout.LayoutParams params) {
        int screenLayout = activity.getResources().getConfiguration().screenLayout;
        if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= 4) {
            adView = new AdView(activity, AdSize.IAB_LEADERBOARD, publisherId);
            adHeight = 90; // 728x90 size for xlarge screens (>= 960dp x 720dp)
        }
        else if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 3) {
            adView = new AdView(activity, AdSize.IAB_BANNER , publisherId);
            adHeight = 75; // 468x60 size for large screens (>= 640dp x 480dp)
        }
        else {
            adView = new AdView(activity, AdSize.BANNER, publisherId);
            adHeight = 75; // 320x50 size for normal (>= 470dp x 320dp) and small screens
        }
        layout.addView(adView, params);
        AdRequest request = new AdRequest();
        request.addTestDevice(AdRequest.TEST_EMULATOR);
        request.addTestDevice("6E28D68C6DDF0423C43C013CCD986D62");
        adView.loadAd(request);
    }


    public void addAdsView(Activity activity, LinearLayout layout) {
        int screenLayout = activity.getResources().getConfiguration().screenLayout;
        if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= 4) {
            adView = new AdView(activity, AdSize.IAB_LEADERBOARD, publisherId);
            adHeight = 90; // 728x90 size for xlarge screens (>= 960dp x 720dp)
        }
        else if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 3) {
            adView = new AdView(activity, AdSize.IAB_BANNER , publisherId);
            adHeight = 75; // 468x60 size for large screens (>= 640dp x 480dp)
        }
        else {
            adView = new AdView(activity, AdSize.BANNER, publisherId);
            adHeight = 75; // 320x50 size for normal (>= 470dp x 320dp) and small screens
        }
        layout.addView(adView);
        AdRequest request = new AdRequest();
        request.addTestDevice(AdRequest.TEST_EMULATOR);
        request.addTestDevice("5EA5B374B0B6F1201A685AACAC300DDD");
        request.addTestDevice("6E28D68C6DDF0423C43C013CCD986D62");
        adView.loadAd(request);
    }

    public void removeAdView(RelativeLayout layout) {
        if (adView != null) {
            layout.removeView(adView);
            adHeight = 0;
            adView = null;
        }
    }

    @Override
    public void onDismissScreen(Ad arg0) {


    }

    @Override
    public void onFailedToReceiveAd(Ad ad, ErrorCode errorCode) {
        Log.e(AdsManager.class.getName(),
                "Failed to receive ad (" + errorCode + ")");
    }

    @Override
    public void onLeaveApplication(Ad arg0) {


    }

    @Override
    public void onPresentScreen(Ad arg0) {


    }

    @Override
    public void onReceiveAd(Ad ad) {
        int h = adView.getHeight();
        if (h > 0) {

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

}