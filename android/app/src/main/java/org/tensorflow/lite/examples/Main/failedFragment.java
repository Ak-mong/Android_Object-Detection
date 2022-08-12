package org.tensorflow.lite.examples.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.tensorflow.lite.examples.R;

public class failedFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_failed, container, false);

        final ImageView course = view.findViewById(R.id.courseImage);
        course.setImageResource(R.drawable.course);

        final TextView failed_message = view.findViewById(R.id.failed_text);


        return view;
    }

}