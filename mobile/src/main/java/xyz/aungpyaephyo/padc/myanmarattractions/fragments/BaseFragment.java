package xyz.aungpyaephyo.padc.myanmarattractions.fragments;

import android.support.v4.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONObject;

/**
 * Created by aung on 8/11/16.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onStart() {
        super.onStart();
        onSendScreenHit();
    }

    protected abstract void onSendScreenHit();
}
