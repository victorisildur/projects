package me.isildur.tomato2.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.isildur.tomato2.R;
import me.isildur.tomato2.data_structure.ActivityRankEntry;
import me.isildur.tomato2.ui_controller.PieChartController;

/**
 * Created by isi on 16/1/28.
 */
public class StatisticListAdapter extends RecyclerView.Adapter<StatisticListAdapter.ViewHolder> {
    private List<ActivityRankEntry> mDataSet;
    private PieChartController mPieChartController;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public ViewHolder(View v) {
            super(v);
            mView = v;
        }
    }

    public StatisticListAdapter(List<ActivityRankEntry> dataSet, PieChartController pieChartController) {
        mDataSet = dataSet;
        if(null == dataSet)
            mDataSet = new ArrayList<ActivityRankEntry>();
        mPieChartController = pieChartController;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //why use parent as context
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_entry, parent, false);
        final ViewHolder vh = new ViewHolder(v);
        vh.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPieChartController.enlarge(vh.getPosition());
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView textView1 = (TextView) holder.mView.findViewById(R.id.list_entry_text1);
        textView1.setText(mDataSet.get(position).getActivity());
        TextView textView2 = (TextView) holder.mView.findViewById(R.id.list_entry_text2);
        textView2.setText("共" + mDataSet.get(position).getTimeList().size() + "个钟！");
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setDataSet(List<ActivityRankEntry> mDataSet) {
        this.mDataSet = mDataSet;
    }
}
