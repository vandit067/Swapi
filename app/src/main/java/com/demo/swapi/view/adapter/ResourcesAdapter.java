package com.demo.swapi.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ResourcesAdapter extends RecyclerView.Adapter<ResourcesAdapter.ResourceViewHolder> {

    @NonNull
    private List<Result> mResultList;

    private IMasterFragmentInteractionListener mIMasterFragmentInteractionListener;

    public ResourcesAdapter(@NonNull List<Result> resultList, @NonNull IMasterFragmentInteractionListener iMasterFragmentInteractionListener) {
        this.mResultList = resultList;
        this.mIMasterFragmentInteractionListener = iMasterFragmentInteractionListener;
    }

    @NonNull
    @Override
    public ResourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resource, parent, false);
        return new ResourceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ResourceViewHolder holder, int position) {
        Result result = mResultList.get(position);
        if(result == null){
            return;
        }
        holder.tvGender.setText(result.getGender());
        holder.tvBirthYear.setText(result.getBirthYear());
        holder.tvName.setText(result.getName());
    }

    @Override
    public int getItemCount() {
        return this.mResultList.size();
    }

    /**
     * Replace list content with new list content
     * @param resultList list of @{@link Result}
     */
    public void swapAdapter(@NonNull List<Result> resultList) {
        this.mResultList = resultList;
        notifyDataSetChanged();
    }

    /**
     * Clear list and notify
     */
    public void clearList(){
        if(this.mResultList.isEmpty()){
            return;
        }
        this.mResultList.clear();
        notifyDataSetChanged();
    }

    @Nullable
    public Result getItemAtPosition(int position){
        if(position < 0 || position > mResultList.size()){
            return null;
        }
        return this.mResultList.get(position);
    }

    class ResourceViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_resource_tv_birth_year)
        TextView tvBirthYear;
        @BindView(R.id.item_resource_tv_gender)
        TextView tvGender;
        @BindView(R.id.item_resource_tv_name)
        TextView tvName;

        public ResourceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.item_resources_rl_main)
        void onViewItemClick(View view){
            if(mIMasterFragmentInteractionListener == null){
                return;
            }
            mIMasterFragmentInteractionListener.onResourceSelected(getAdapterPosition(), mResultList.get(getAdapterPosition()));
        }
    }
}
