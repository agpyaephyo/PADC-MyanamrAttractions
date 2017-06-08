package xyz.aungpyaephyo.padc.myanmarattractions.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by aung on 10/19/16.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected boolean mDetechedFromWindow;

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(this);

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }

        itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                mDetechedFromWindow = false;
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                mDetechedFromWindow = true;
            }
        });
    }

    public abstract void bind(T data);

    public void onEvent(Object obj) {

    }
}
