package org.tensorflow.lite.examples.Main;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.tensorflow.lite.examples.R;
import org.tensorflow.lite.examples.detection.CertificationFragment;
import org.tensorflow.lite.examples.detection.Data;
import org.tensorflow.lite.examples.detection.DetailActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fragment3 extends Fragment {

    static List<String> listTitle = Arrays.asList("정문", "안내문", "북문", "테크노문", "석탑", "백호관", "당근", "test", "test");

    private GridView gridView = null;
    private GridViewAdapter adapter = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course, container, false);

        gridView = view.findViewById(R.id.gridview);
        adapter = new GridViewAdapter();
        gridView.setAdapter(adapter);

        getData();

        final ImageView course = view.findViewById(R.id.courseImage);
        course.setImageResource(R.drawable.course);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void getData() {
        for (int i = 0; i < listTitle.size(); i++) {
            adapter.addItem(listTitle.get(i));
        }
    }

    class GridViewAdapter extends BaseAdapter{

        ArrayList<String> items = new ArrayList<String>();

        public int getCount() {
            return items.size();
        }

        public void addItem(String item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            final Context context = viewGroup.getContext();
            final String item = items.get(position);

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.gridview_list_item, viewGroup, false);

                TextView gv_text = (TextView) convertView.findViewById(R.id.gv_text);

                gv_text.setText(item);

            }
            else {
                View view = new View(context);
                view = (View) convertView;
            }

            //각 아이템 선택 event
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("targetI", position);

                    context.startActivity(intent);
                }
            });

            return convertView;  //뷰 객체 반환
        }
    }

}