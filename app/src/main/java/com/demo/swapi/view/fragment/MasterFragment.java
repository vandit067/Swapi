package com.demo.swapi.view.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.swapi.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

/**
 * This class is used for perform resource search.
 */
public class MasterFragment extends BaseFragment {

    @BindView(R.id.fragment_master_iv_search)
    ImageView mIvSearchResource;
    @BindView(R.id.fragment_master_til_resource)
    TextInputLayout mTilResource;
    @BindView(R.id.fragment_master_tiet_resource)
    TextInputEditText mTietResource;

    // Create new instance of MasterFragment
    public static MasterFragment newInstance() {
        Bundle args = new Bundle();
        MasterFragment fragment = new MasterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_master, container, false);
        setUnBinder(ButterKnife.bind(this, view));
        return view;
    }

    @OnClick(R.id.fragment_master_iv_search)
    void performResourceSearch(){
        if (TextUtils.isEmpty(this.mTietResource.getText())) {
            this.mTilResource.setError(getString(R.string.message_enter_resource_name));
            return;
        }
        this.performSearch(this.mTietResource.getText().toString());
    }

    @OnTextChanged(value = R.id.fragment_master_tiet_resource, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged(Editable editable){
        if(editable.length() > 0){
            this.mTilResource.setError("");
        }
    }

    @OnEditorAction(R.id.fragment_master_tiet_resource)
    boolean onEditorAction(TextView v, int actionId){
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (TextUtils.isEmpty(v.getText())) {
                this.mTilResource.setError(getString(R.string.message_enter_resource_name));
                return false;
            }
            performSearch(v.getText().toString());
            return true;
        }
        return false;
    }

    /**
     * Check network connection and pass search resource name to  {@link Detailfragment}
     * @param resourceName name of resource
     */
    private void performSearch(@NonNull String resourceName){
        hideKeyboard();
        if(!isNetworkConnected()){
            showError(R.string.message_network_check);
            return;
        }
        replaceFragment(Detailfragment.newInstance(resourceName), getClass().getSimpleName());
    }

    @Override
    protected void setUp(@NonNull View view) {
        // Nothing to do here
    }
}
