package ua.kpi.comsys.iv7312;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {

    private static final String TAG = "RecyclerViewAdapter";

    static protected List<State> mStates = new ArrayList<>();
    static protected List<State> mStatesCopy;
    private int layout;
    private Context mContext;
    private LayoutInflater inflater;
    static boolean openSearch = true;

    public RecyclerViewAdapter(Context context, int resource, List<State> states ) {
        mStates = states;
        mStatesCopy = new ArrayList<>(mStates);
        mContext = context;
        inflater = LayoutInflater.from(context);
        layout = resource;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.imagePoster.setImageResource(mStates.get(position).getPoster());
        holder.textTitle.setText(mStates.get(position).getTitle());
        holder.textYear.setText(mStates.get(position).getYear());
        holder.textType.setText(mStates.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return mStates.size();
    }


    @Override
    public Filter getFilter() {
        return statesFilter;
    }

    private Filter statesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<State> filterList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                if (openSearch){
                    RecyclerViewAdapter.mStatesCopy = new ArrayList<>(mStates);
                    openSearch = false;
                }
                filterList.addAll(mStatesCopy);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (State state: mStatesCopy){
                    if (state.getTitle().toLowerCase().contains(filterPattern)){
                        filterList.add(state);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mStates.clear();
            mStates.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imagePoster;
        TextView textTitle;
        TextView textYear;
        TextView textType;

        LinearLayout parentLayouot;

        public ViewHolder(View itemView) {
            super(itemView);

            imagePoster = itemView.findViewById(R.id.poster_list_item);
            textTitle = itemView.findViewById(R.id.title_list_item);
            textYear = itemView.findViewById(R.id.year_list_item);
            textType = itemView.findViewById(R.id.type_list_item);
            parentLayouot = itemView.findViewById(R.id.linearLayout_parent);

            parentLayouot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity mainActivity = new MainActivity();
                    Intent intent = new Intent(v.getContext(), InfoActivity.class);

                    if(mStates.get(getAdapterPosition()).getIsInfo()){
                        mainActivity.setIndex(mStates.get(getAdapterPosition()).getIndex());
                        v.getContext().startActivity(intent);
                    }

                }
            });
        }
    }
}
