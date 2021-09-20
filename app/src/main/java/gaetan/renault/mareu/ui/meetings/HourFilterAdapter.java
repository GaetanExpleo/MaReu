package gaetan.renault.mareu.ui.meetings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gaetan.renault.mareu.R;

public class HourFilterAdapter extends RecyclerView.Adapter<HourFilterAdapter.HourFilterViewHolder> {

    private final HourSelectedListener mListener;
    private static final List<Integer> sHourList = new ArrayList<>(Arrays.asList(6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20));

    public HourFilterAdapter(HourSelectedListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public HourFilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HourFilterViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting_hour_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HourFilterAdapter.HourFilterViewHolder holder, int position) {
        int currentHour = sHourList.get(position);
        holder.mTextView.setText(String.format("%sh%s", String.valueOf(currentHour), "00"));
        holder.mTextView.setOnClickListener(v -> {
            mListener.onHourSelected(currentHour);
            v.setSelected(!v.isSelected());
            if (v.isSelected()) {
                holder.mTextView.setTextColor(v.getResources().getColor(R.color.white));
                holder.mView.setVisibility(View.VISIBLE);
            } else {
                holder.mTextView.setTextColor(v.getResources().getColor(R.color.gray));
                holder.mView.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sHourList.size();
    }

    public class HourFilterViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTextView;
        private final View mView;

        public HourFilterViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.meeting_hour_item_textview);
            mView = itemView.findViewById(R.id.meeting_hour_item_view);
        }
    }

    public interface HourSelectedListener {
        void onHourSelected(int hourSelected);
    }
}
