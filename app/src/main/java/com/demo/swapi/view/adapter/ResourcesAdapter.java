package com.demo.swapi.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.demo.swapi.R;
import com.demo.swapi.interfaces.IMasterFragmentInteractionListener;
import com.demo.swapi.model.Result;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResourcesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * ViewTypes serve as a mapping point to which layout should be inflated
     */
    private static final int VIEW_TYPE_LIST = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    @NonNull
    private List<Result> mResultList;

    private IMasterFragmentInteractionListener mIMasterFragmentInteractionListener;

    public ResourcesAdapter(@NonNull List<Result> resultList, @NonNull IMasterFragmentInteractionListener iMasterFragmentInteractionListener) {
        this.mResultList = resultList;
        this.mIMasterFragmentInteractionListener = iMasterFragmentInteractionListener;
    }

    @Override
    public int getItemViewType(int position) {
        return mResultList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_LIST;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_bar, parent, false);
            return new ProgressBarViewHolder(view);
        }
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resource, parent, false);
        return new ResourceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_LOADING) {
            return; // no-op
        }
        ResourceViewHolder resourceViewHolder = (ResourceViewHolder) holder;
        Result result = mResultList.get(position);
        if(result == null){
            return;
        }
        resourceViewHolder.tvGender.setText(result.getGender());
        resourceViewHolder.tvBirthYear.setText(result.getBirthYear());
        resourceViewHolder.tvName.setText(result.getName());
    }

    @Override
    public int getItemCount() {
        return this.mResultList.size();
    }


    public void add(@Nullable Result result) {
        add(-1, result);
    }

    /**
     * Add progress view at end of the list
     * @return true if view added in to the list.
     */
    public boolean addLoadingView() {
        if (getItemViewType(mResultList.size() - 1) != VIEW_TYPE_LOADING) {
            add(null);
            return true;
        }
        return false;
    }

    /**
     * Add item at position.
     * @param position position
     * @param result instance of {@link Result}
     */
    public void add(int position, @Nullable Result result) {
        if (position > 0) {
            mResultList.add(position, result);
            notifyItemInserted(position);
        } else {
            mResultList.add(result);
            notifyItemInserted(mResultList.size() - 1);
        }
    }

    /**
     * Add item at position in list.
     * @param resultList new list of {@link Result}
     */
    public void addItems(@NonNull List<Result> resultList) {
        mResultList.addAll(resultList);
        notifyItemRangeInserted(getItemCount(), mResultList.size() - 1);
    }

    /**
     * Remove item from list position.
     */
    private void remove(int position) {
        if (mResultList.size() < position) {
            return;
        }
        mResultList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * Remove all views from adapter
     */
    public void removeAll() {
        mResultList.clear();
        notifyDataSetChanged();
    }

    /**
     * Remove progress view from footer of list and return it's status
     * @return is view removed or not.
     */
    public boolean removeLoadingView() {
        if (mResultList.size() > 1) {
            int loadingViewPosition = mResultList.size() - 1;
            if (getItemViewType(loadingViewPosition) == VIEW_TYPE_LOADING) {
                remove(loadingViewPosition);
                return true;
            }
        }
        return false;
    }

    // Progressbar view holder which will display on load more
    public class ProgressBarViewHolder extends RecyclerView.ViewHolder {

        final ProgressBar progressBar;

        ProgressBarViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.progress_bar);
        }
    }

    // View holder which will display list content
    class ResourceViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_resource_tv_birth_year)
        TextView tvBirthYear;
        @BindView(R.id.item_resource_tv_gender)
        TextView tvGender;
        @BindView(R.id.item_resource_tv_name)
        TextView tvName;

        ResourceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.item_resources_rl_main)
        void onViewItemClick(View view){
            if(mIMasterFragmentInteractionListener == null){
                return;
            }
            mIMasterFragmentInteractionListener.onResourceSelected(getAdapterPosition(), view, mResultList.get(getAdapterPosition()));
        }
    }
}
