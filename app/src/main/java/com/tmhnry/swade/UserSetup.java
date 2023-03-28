package com.tmhnry.swade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.tmhnry.swade.databinding.ActivityUserSetupBinding;
import com.tmhnry.swade.fragment.UserSetupMainFragment;

public class UserSetup extends AppCompatActivity  {
    ActivityUserSetupBinding binding;
    String fragmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserSetupBinding
                .inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadFragment(UserSetupMainFragment.FRAGMENT_ID);
    }

    public void loadFragment(String fragmentId){
        this.fragmentId = fragmentId;
        Fragment fragment = null;
        switch (fragmentId){
            default:
                fragment = UserSetupMainFragment.Builder();
        }
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
                .replace(binding.fragment.getId(), fragment)
                .commit();
    }
}