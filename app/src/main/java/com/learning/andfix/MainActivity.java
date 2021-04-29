package com.learning.andfix;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private TextView mTv;
    private File mFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        mTv = findViewById(R.id.sample_text);
        mTv.setText(stringFromJNI());

        findViewById(R.id.btn_calculate).setOnClickListener(this);
        findViewById(R.id.btn_fix).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_calculate:
                Calculator calculator = new Calculator();
                int res = calculator.div(1, 0);
                mTv.setText(res);
                break;
            case R.id.btn_fix:
                DexManager manager = new DexManager(getApplicationContext());
                Log.e("hshhsh",Environment.getExternalStorageDirectory().toString());
                mFile = new File(Environment.getExternalStorageDirectory(), "fix.dex");
                if(!mFile.exists()){
                    Toast.makeText(getApplicationContext(),"文件不存在",Toast.LENGTH_LONG).show();
                }
                manager.Loadpath(mFile);
                break;
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
