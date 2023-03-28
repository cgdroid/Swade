package com.tmhnry.swade.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tmhnry.swade.R;
import com.tmhnry.swade.databinding.FragmentEntityProfileBinding;
import com.tmhnry.swade.model.Entity;
import com.tmhnry.swade.singleton.User;

import java.util.stream.Collectors;

public class EntityProfileFragment extends Fragment {

    FragmentEntityProfileBinding binding;
    Entity entity;

    public EntityProfileFragment() {
    }

    public static EntityProfileFragment Builder(String key) {
        EntityProfileFragment fragment = new EntityProfileFragment();
        Bundle args = new Bundle();
        args.putString("key", key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String key = getArguments().getString("key");
        this.entity = Entity.getModels().values().stream()
                .filter(entity -> entity.key.equals(key))
                .collect(Collectors.toList()).get(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEntityProfileBinding.inflate(inflater, container, false);
        binding.setEntity(entity);
        if(User.getAccountType(getContext()).equals(Entity.EMPLOYEE)){
            binding.wrapperSalary.setVisibility(View.GONE);
        }
        return binding.getRoot();
    }
}