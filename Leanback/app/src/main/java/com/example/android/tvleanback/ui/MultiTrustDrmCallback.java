package com.example.android.tvleanback.ui;

import android.media.MediaDrm;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import androidx.annotation.Nullable;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.drm.MediaDrmCallback;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Thrown when a request results in a response code not in the 2xx range.
 */
final class MultiTrustDrmException extends HttpDataSource.HttpDataSourceException {

  /**
   * The response code that was outside of the 2xx range.
   */
  public final int code;

  /** The error embedded in HTTP body (if any). */
  @Nullable
  public final String error;


  public MultiTrustDrmException(int responseCode, @Nullable String body, DataSpec dataSpec) {
    super("Response code: " + responseCode, dataSpec, TYPE_OPEN);
    this.code = responseCode;
    this.error = body;
  }

}


public class MultiTrustDrmCallback implements MediaDrmCallback {

  private static final String TAG = "MultiTrustDrmCallback";
  private MultiTrustHttpDataSource multiTrustHttp;


  public MultiTrustDrmCallback(MultiTrustHttpDataSource http)
  {
    multiTrustHttp = http;
  }

  @Override
  public byte[] executeProvisionRequest(UUID uuid, ExoMediaDrm.ProvisionRequest request) throws Exception {

    //Posts device provisioning request to Google Widevine Cloud service based on non-public specification
    //Uses HTTP POST with empty body and specifically formatted URI query string


    /*Code that is specific to Widevine PROVISIONING REQUESTS ONLY */
    String requestUrl = request.getDefaultUrl();

    requestUrl += "&signedRequest=" + new String(request.getData());

    Log.i(TAG, "Executing Provisioning Request :" + requestUrl);

    /*End of code that is specific to Widevine PROVISIONING REQUESTS ONLY */

    try{
      Pair<Integer, byte[]> networkResponse;
      networkResponse = multiTrustHttp.postRequest(requestUrl);


      if (networkResponse.first < 200 || networkResponse.first > 300) {
        //Underlying ExoPlayer network exceptions require a 'DataSpec' object containing basic information about the connection end-point
        DataSpec dataSpec = new DataSpec(Uri.parse(requestUrl));
        throw new MultiTrustDrmException(networkResponse.first, new String(networkResponse.second), dataSpec);
      }
      else {
        Log.i(TAG, "Provisioning Request Success");
        return networkResponse.second;
      }

    } catch (IOException e) {
      Log.e(TAG, "Provisioning Failed" + e.toString());
      throw new Exception("Provisioning Failed");
    }
  }

  //don't need anything to do with the eKR function from HTTPMediaDrmCallback
  //handle the response or handle the exception thrown.
  @Override
  public byte[] executeKeyRequest(UUID uuid, ExoMediaDrm.KeyRequest request) throws Exception {
    //TODO - replace return empty with working implementation
    try {
      Pair<Integer, byte[]> networkResponse;
      //byte array

      networkResponse = multiTrustHttp.postRequest(request.getData());

      if (networkResponse.first != 200) {
        DataSpec dataSpec = new DataSpec(Uri.parse(multiTrustHttp.getProxyUrl()));
//        Log.d(TAG, "THIS IS THE RESPONSE ------" + networkResponse.first + " 2nd param " + new String(networkResponse.second) + " DATASPEC " + dataSpec);
        //Run through and debug what is happening. Need to see what exactly is happening with this
        throw new MultiTrustDrmException(networkResponse.first, new String(networkResponse.second), dataSpec);
      } else {
        Log.d(TAG, "Key Request Success");
        return networkResponse.second;
      }
    }catch(IOException e){
      Log.e (TAG, "Key Request Failed: " + e.getMessage());
      throw e;
    }
  }
}