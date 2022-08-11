package org.tensorflow.lite.examples.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import org.tensorflow.lite.examples.R;

public class Fragment3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course, container, false);

        final ImageView course = view.findViewById(R.id.courseImage);
        course.setImageResource(R.drawable.course);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}