package xyz.aungpyaephyo.padc.myanmarattractions.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.aungpyaephyo.padc.myanmarattractions.R;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.NotificationUtils;

/**
 * Created by aung on 7/22/16.
 */
public class NotificationFragment extends BaseFragment {

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @OnClick(R.id.btn_notify)
    public void onTapNotifiy(Button btnNotify) {
        NotificationUtils.showNotification();
    }

    @OnClick(R.id.btn_notify_bigtext)
    public void onTapNotifyBigText(Button btnNotifyBigText) {
        NotificationUtils.showNotificationWithBigTextStyle();
    }

    @OnClick(R.id.btn_notify_bigpic)
    public void onTapNotifyBigPicture(Button btnNotifyBigPicture) {
        NotificationUtils.showNotificationWithBigPictureStyle();
    }

    @OnClick(R.id.btn_notify_with_action)
    public void onTapNotifyWithAction(Button btnNotifyWithAction) {
        NotificationUtils.showNotificationWithAction();
    }
}
