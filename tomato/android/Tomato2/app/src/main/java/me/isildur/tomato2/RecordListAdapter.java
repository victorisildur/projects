package me.isildur.tomato2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by isi on 16/1/24.
 */
public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.ViewHolder> {
    private TomatoRecord[] mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public ViewHolder(View v) {
            super(v);
            mView = v;
        }
    }

    public RecordListAdapter(TomatoRecord[] dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //why use parent as context
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_entry, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView textView1 = (TextView) holder.mView.findViewById(R.id.list_entry_text1);
        Calendar time = mDataSet[position].getmTime();
        textView1.setText(time.get(Calendar.HOUR) + ":" + time.get(Calendar.MINUTE));
        TextView textView2 = (TextView) holder.mView.findViewById(R.id.list_entry_text2);
        textView2.setText(mDataSet[position].getmDoContent());
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}
