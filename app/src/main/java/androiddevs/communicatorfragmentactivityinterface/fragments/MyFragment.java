package androiddevs.communicatorfragmentactivityinterface.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androiddevs.communicatorfragmentactivityinterface.R;
import androiddevs.communicatorfragmentactivityinterface.activities.MainActivity;
import androiddevs.communicatorfragmentactivityinterface.interfaces.ActivityCommunicator;
import androiddevs.communicatorfragmentactivityinterface.interfaces.FragmentCommunicator;

public class MyFragment extends Fragment implements FragmentCommunicator {

    private static final String TAG = MainActivity.class.getSimpleName();
    public Context mContext;
    //interface via which we communicate to hosting Activity
    private ActivityCommunicator activityCommunicator;
    private TextView getData;
    private Button setData;

    //as per Android Fragment documentation an empty constructor
    public static MyFragment newInstance() {
        Bundle args = new Bundle();
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //since Fragment is Activity dependent you need Activity context in various cases
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getActivity();
        activityCommunicator = (ActivityCommunicator) mContext;
        ((MainActivity)mContext).fragmentCommunicator = this;
    }

    //now on your entire fragment use context rather than getActivity()
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    //create your fragment view via layout or other views
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_myfragment, container, false);
        setData = fragmentView.findViewById(R.id.btnSetData);
        setData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommunicator.passDataToActivity("Hi from MyFragment");
            }
        });
        getData = fragmentView.findViewById(R.id.tvGetData);
        setRetainInstance(true);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    //FragmentCommunicator interface implementation
    @Override
    public void passDataToFragment(String someValue) {
        getData.setText(someValue);
        Toast.makeText(mContext, someValue, Toast.LENGTH_SHORT).show();
    }
}