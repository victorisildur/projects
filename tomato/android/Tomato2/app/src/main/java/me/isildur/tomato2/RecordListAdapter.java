package me.isildur.tomato2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.isildur.tomato2.util.TimeUtil;

/**
 * Created by isi on 16/1/24.
 */
public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.ViewHolder> {
    private List<TomatoRecord> mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public ViewHolder(View v) {
            super(v);
            mView = v;
        }
    }

    public RecordListAdapter(List<TomatoRecord> dataSet) {
        mDataSet = dataSet;
        if(null == dataSet)
            mDataSet = new ArrayList<TomatoRecord>();
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
        Calendar stime = mDataSet.get(position).getmStartTime();
        Calendar etime = mDataSet.get(position).getmEndTime();
        textView1.setText(TimeUtil.cldrToTimestr(stime) + " ~ " + TimeUtil.cldrToTimestr(etime));
        TextView textView2 = (TextView) holder.mView.findViewById(R.id.list_entry_text2);
        textView2.setText(mDataSet.get(position).getmActivity());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
