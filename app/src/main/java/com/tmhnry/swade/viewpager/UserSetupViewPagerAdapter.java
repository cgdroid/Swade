package com.tmhnry.swade.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.tmhnry.swade.R;

import java.util.List;

public class UserSetupViewPagerAdapter extends PagerAdapter {

    Context context;
    List<Page> pages;

    public UserSetupViewPagerAdapter(Context context, List<Page> pages) {
        this.context = context;
        this.pages = pages;
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.page_user_setup_main, null);
//        TextView pageNumber = layout.findViewById(R.id.txt_page_number);
//        TextView question = layout.findViewById(R.id.txt_question);
        container.addView(layout);
        return layout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public static class Page {
        String question;
        int type;
        List<String> options;

        public Page(String question, int type, List<String> options) {
            this.question = question;
            this.type = type;
            this.options = options;
        }

        public int getType() {
            return type;
        }

        public List<String> getOptions() {
            return options;
        }

        public String getQuestion() {
            return question;
        }
    }
}
