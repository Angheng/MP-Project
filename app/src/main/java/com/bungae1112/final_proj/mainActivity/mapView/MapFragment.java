/*
Author: 20175165 서민주
Last Modification date: 19.11.26
Function: MainActivity
 */

package com.bungae1112.final_proj.mainActivity.mapView;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bungae1112.final_proj.R;
import com.bungae1112.final_proj.mainActivity.listView.ItemData;
import com.bungae1112.final_proj.mainActivity.listView.ListFragment;
import com.bungae1112.final_proj.tools.GetJson;
import com.bungae1112.final_proj.tools.JsonDataSet;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MapFragment extends Fragment
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener, GoogleMap.OnInfoWindowClickListener { // adb emu geo fix 126.999686 37.607830

    private FragmentActivity mContext;

    private static final String TAG = MapFragment.class.getSimpleName();
    private View marker_root_view;
    private TextView tv_marker;
    private GoogleMap mMap;
    private MapView mapView = null;
    private Marker selectedMarker = null;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationRequest locationRequest;
    private Location mCurrentLocation;

    private final LatLng mDefaultLocation = new LatLng(37.607830, 126.999686);
    private static final int DEFAULT_ZOOM = 18;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int UPDATE_INTERVAL_MS = 1000 * 60 * 5;  // 5분 단위 시간 갱신
    private static final int FASTEST_UPDATE_INTERVAL_MS = 1000 * 30; // 30초 단위로 화면 갱신

    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    private Retrofit retrofit;
    private GetJson getJson;
    private JsonDataSet jsonDataSet = null;
    private ArrayList<ItemData> itemList;


    public MapFragment() {
    }

    @Override
    public void onAttach(Context context) { // Fragment 가 Activity에 attach 될 때 호출
        Activity activity;

        if (context instanceof Activity) {
            activity = (Activity) context;
            mContext = (FragmentActivity) activity;
            super.onAttach(context);
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 초기화 해야 하는 리소스들을 여기서 초기화

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Layout 을 inflate 하는 곳
        if (savedInstanceState != null) {
            mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            CameraPosition mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        View layout = inflater.inflate(R.layout.map_fragment, container, false);
        mapView = (MapView) layout.findViewById(R.id.googleMap);
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }

        retrofit = RetrofitClient.getClient1("http://54.180.153.64:3000");
        getJson = retrofit.create(GetJson.class);

        mapView.getMapAsync(this);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // Fragement에서의 OnCreateView를 마치고, Activity에서 onCreate()가 호출되고 나서 호출되는 메소드
        // Activity와 Fragment의 뷰가 모두 생성된 상태로, View를 변경하는 작업이 가능한 단계
        super.onActivityCreated(savedInstanceState);

        // 액티비티가 처음 생성될 때 실행되는 함수
        MapsInitializer.initialize(mContext);

        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) // 정확도를 최우선적으로 고려
                .setInterval(UPDATE_INTERVAL_MS) // 위치가 Update 되는 주기
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS); // 위치 획득후 업데이트되는 주기

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();

        builder.addLocationRequest(locationRequest);

        // FusedLocationProviderClient 객체 생성
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setDefaultLocation(); // GPS를 찾지 못하는 장소에 있을 경우 지도의 초기 위치가 필요함.

        getLocationPermission();

        updateLocationUI();

        getDeviceLocation();

        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

        setCustomMarkerView();
        getSampleMarkerItems();

    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                Toast.makeText(mContext, "위치정보 가져올 수 없습니다. 위치 퍼미션과 GPS 활성 여부 확인하세요", Toast.LENGTH_SHORT).show();

                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mCurrentLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void setDefaultLocation() {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM);
        mMap.moveCamera(cameraUpdate);
    }

    private String getCurrentAddress(LatLng latlng) {
        // 위치 정보와 지역으로부터 주소 문자열을 구한다.
        List<Address> addressList = null;
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

        // 지오코더를 이용하여 주소 리스트를 구한다.
        try {
            addressList = geocoder.getFromLocation(latlng.latitude, latlng.longitude, 1);
        } catch (IOException e) {
            Toast.makeText(mContext, "위치로부터 주소를 인식할 수 없습니다. 네트워크가 연결되어 있는지 확인해 주세요.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return "주소 인식 불가";
        }

        if (addressList.size() < 1) { // 주소 리스트가 비어있는지 비어 있으면
            return "해당 위치에 주소 없음";
        }

        // 주소를 담는 문자열을 생성하고 리턴
        Address address = addressList.get(0);
        StringBuilder addressStringBuilder = new StringBuilder();
        for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
            addressStringBuilder.append(address.getAddressLine(i));
            if (i < address.getMaxAddressLineIndex())
                addressStringBuilder.append("\n");
        }

        return addressStringBuilder.toString();
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                Location location = locationList.get(locationList.size() - 1);

//                LatLng currentPosition
//                        = new LatLng(location.getLatitude(), location.getLongitude());
//
//                String markerTitle = getCurrentAddress(currentPosition);
//                String markerSnippet = "위도:" + String.valueOf(location.getLatitude())
//                        + " 경도:" + String.valueOf(location.getLongitude());
//
//                Log.d(TAG, "Time :" + CurrentTime() + " onLocationResult : " + markerSnippet);
//
//                //현재 위치에 마커 생성하고 이동
//                setCurrentLocation(location, markerTitle, markerSnippet);
                mCurrentLocation = location;
            }
        }

    };

//    private String CurrentTime() {
//        Date today = new Date();
//        SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
//        SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a");
//        return time.format(today);
//    }

//    private void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {
//
//        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//
//
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
//        mMap.moveCamera(cameraUpdate);
//    }

    private void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(mContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(mContext,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
                break;
            default:

        }
        updateLocationUI();
    }


    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();

        Log.d(TAG, "onStart ");
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();

        if (mFusedLocationProviderClient != null) {
            Log.d(TAG, "onStop : removeLocationUpdates");

            mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

        if (mLocationPermissionGranted) {
            Log.d(TAG, "onResume : requestLocationUpdates");

            mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
            if (mMap != null)
                mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mFusedLocationProviderClient != null) {
            Log.d(TAG, "onDestroyView : removeLocationUpdates");
            mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


    private void setCustomMarkerView() {
        marker_root_view = LayoutInflater.from(mContext).inflate(R.layout.marker_layout, null);
        tv_marker = (TextView) marker_root_view.findViewById(R.id.tv_marker);
    }

    private void getSampleMarkerItems() { // 126.999686 37.607830
        final ArrayList<MarkerItem> storeList = new ArrayList<MarkerItem>();

        ListFragment listFragment = new ListFragment("total");
        getJson.getData("").enqueue(new Callback<JsonDataSet>()
        {
            @Override
            public void onResponse(Call<JsonDataSet> call, Response<JsonDataSet> response)
            {
                if ( response.isSuccessful() )
                {
                    jsonDataSet = response.body();
                    Log.d("DataSet", jsonDataSet.getResult());

                    if ( jsonDataSet != null && jsonDataSet.getResult().equals("success") )
                    {
                        List<ItemData> itemData = jsonDataSet.getRawJsonArr();
                        for(ItemData item : itemData) {
                            storeList.add(
                                    new MarkerItem(Double.valueOf(item.getLat()),
                                            Double.valueOf(item.getLng()), item.getName()));
                        }

                        Log.d("InitMarkers", "Success");

                    }
                    else {
                        Log.d("InitMarkers", "Error");
                    }
                    for (MarkerItem markerItem : storeList) {
                        addMarker(markerItem, false);
                        Log.d(TAG, "addMarker " + markerItem.getTitle());
                    }
                }
                else {
                    Log.d("data.response", "response Error");
                }
            }

            @Override
            public void onFailure(Call<JsonDataSet> call, Throwable t) {
                Log.d("data", "Connect Failed\t" + t.getMessage());
            }
        });
    }

    private Marker addMarker(MarkerItem markerItem, boolean isSelectedMarker) {
        LatLng position = new LatLng(markerItem.getLat(), markerItem.getLng());
        String title = markerItem.getTitle();

        tv_marker.setText(title);

        if (isSelectedMarker) {
            tv_marker.setBackgroundResource(R.drawable.ic_marker_green);
            tv_marker.setTextColor(Color.WHITE);
        } else {
            tv_marker.setBackgroundResource(R.drawable.ic_marker_greenwhite);
            tv_marker.setTextColor(Color.BLACK);
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(title)
                .position(position)
                .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(mContext, marker_root_view)));

        InfoWindowCustom infoWindow = new InfoWindowCustom(mContext);
        mMap.setInfoWindowAdapter(infoWindow);

        return mMap.addMarker(markerOptions);

    }

    private Marker addMarker(Marker marker, boolean isSelectedMarker) {
        double lat = marker.getPosition().latitude;
        double lng = marker.getPosition().longitude;
        String title = marker.getTitle();

        MarkerItem temp = new MarkerItem(lat, lng, title);
        return addMarker(temp, isSelectedMarker);
    }

    // View를 Bitmap으로 변환
    private Bitmap createDrawableFromView(Context context, View view) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        CameraUpdate center = CameraUpdateFactory.newLatLng(marker.getPosition());
        mMap.animateCamera(center);

        changeSelectedMarker(marker);


        System.out.println(marker.isInfoWindowShown());

        return true;
    }

    private void changeSelectedMarker(Marker marker) {
        // 선택했던 마커 되돌리기
        if (selectedMarker != null) {
            addMarker(selectedMarker, false);
            selectedMarker.remove();
        }

        // 선택한 마커 표시
        if (marker != null) {
            selectedMarker = addMarker(marker, true);
            selectedMarker.showInfoWindow();
            marker.remove();
        }

    }

    @Override
    public void onMapClick(LatLng latLng) {
        changeSelectedMarker(null);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(getContext(), com.bungae1112.final_proj.itemView.itemView.class);

        ItemData targ_data = itemList.get(1);
        System.out.println(itemList);

        intent.putExtra( "name", targ_data.getName() );
        intent.putExtra( "address", targ_data.getAddr() );
        intent.putExtra( "telnum", targ_data.getTel() );
        intent.putExtra( "menu", targ_data.getMenu() );
        intent.putExtra( "remain", targ_data.getRemain() );
        intent.putExtra( "seat", targ_data.getSeat() );

        startActivity(intent);
    }

    public static class RetrofitClient {
        private static Retrofit retrofit = null;
        public static Retrofit getClient1(String baseUrl) {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }
}