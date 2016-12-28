package com.ibc.android.demo.appslist.activity;
 
import java.text.SimpleDateFormat;
import java.util.Date;
import com.ibc.android.demo.appslist.app.AppData;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ApkInfo extends Activity {
 
    TextView appLabel, packageName, version, features;
    TextView permissions, andVersion, installed, lastModify, path;
    PackageInfo packageInfo;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apkinfo);
        View.OnClickListener listener=new View.OnClickListener(){
            public void onClick(View v){
                removeApp(packageInfo.packageName);



            }
        };

        findViewsById();
 
        AppData appData = (AppData) getApplicationContext();
        packageInfo = appData.getPackageInfo();
 
        setValues();

        Button button= (Button)findViewById(R.id.button);

        button.setOnClickListener(listener);
    }
 
    private void findViewsById() {
        appLabel = (TextView) findViewById(R.id.applabel);
        packageName = (TextView) findViewById(R.id.package_name);
        version = (TextView) findViewById(R.id.version_name);
        features = (TextView) findViewById(R.id.req_feature);
        permissions = (TextView) findViewById(R.id.req_permission);
        andVersion = (TextView) findViewById(R.id.andversion);
        path = (TextView) findViewById(R.id.path);
        installed = (TextView) findViewById(R.id.insdate);
        lastModify = (TextView) findViewById(R.id.last_modify);
    }
 
    private void setValues() {
        // APP name
        appLabel.setText(getPackageManager().getApplicationLabel(
                packageInfo.applicationInfo));
 
        // package name
        packageName.setText(packageInfo.packageName);
 
        // version name
        version.setText(packageInfo.versionName);
 
        // target version
        andVersion.setText(Integer
                .toString(packageInfo.applicationInfo.targetSdkVersion));
 
        // path
        path.setText(packageInfo.applicationInfo.sourceDir);
 
        // first installation
        installed.setText(setDateFormat(packageInfo.firstInstallTime));
 
        // last modified
        lastModify.setText(setDateFormat(packageInfo.lastUpdateTime));
 
        // features
        if (packageInfo.reqFeatures != null)
            features.setText(getFeatures(packageInfo.reqFeatures));
        else
            features.setText("-");
 
        // uses-permission
        if (packageInfo.requestedPermissions != null)
            permissions
                    .setText(getPermissions(packageInfo.requestedPermissions));
        else
            permissions.setText("-");
    }
 
    @SuppressLint("SimpleDateFormat")
    private String setDateFormat(long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strDate = formatter.format(date);
        return strDate;
    }
 
    // Convert string array to comma separated string
    private String getPermissions(String[] requestedPermissions) {
        String permission = "";
        for (int i = 0; i < requestedPermissions.length; i++) {
            permission = permission + requestedPermissions[i] + ",\n";
        }
        return permission;
    }
 
    // Convert string array to comma separated string
    private String getFeatures(FeatureInfo[] reqFeatures) {
        String features = "";
        for (int i = 0; i < reqFeatures.length; i++) {
            features = features + reqFeatures[i] + ",\n";
        }
        return features;
    }

    void removeApp(String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE)
                .setData(Uri.parse("package:" + packageName));
        startActivity(intent);
    }


    void installApp(String fileName) {
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setDataAndType(Uri.parse("file://" + fileName), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    public void onClickButton(View v){

        Toast.makeText(this, "복원을 시작합니다!!", Toast.LENGTH_SHORT).show();

    }



}