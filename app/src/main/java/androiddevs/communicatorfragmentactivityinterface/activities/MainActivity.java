package androiddevs.communicatorfragmentactivityinterface.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androiddevs.communicatorfragmentactivityinterface.R;
import androiddevs.communicatorfragmentactivityinterface.fragments.MyFragment;
import androiddevs.communicatorfragmentactivityinterface.interfaces.ActivityCommunicator;
import androiddevs.communicatorfragmentactivityinterface.interfaces.FragmentCommunicator;

public class MainActivity extends AppCompatActivity implements ActivityCommunicator {

    private static final String TAG = MainActivity.class.getSimpleName();
    //interface through which communication is made to fragment
    public FragmentCommunicator fragmentCommunicator;
    private TextView getData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(MyFragment.newInstance());
        getData = findViewById(R.id.tvGetData);
        findViewById(R.id.btnSetData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentCommunicator != null)
                    fragmentCommunicator.passDataToFragment("Hi from Activity");
            }
        });
    }


    private void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        Log.e(TAG, "replaceFragment: " + backStateName);
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
        if (!fragmentPopped) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.container, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    @Override
    public void passDataToActivity(String someValue) {
        getData.setText(someValue);
        Toast.makeText(this, someValue, Toast.LENGTH_SHORT).show();
    }
}