package com.devpino.memberandroid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Member> members = new ArrayList<>();

    private ListView listView;

    private MemberArrayAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listview);

        listView.setOnItemClickListener(onItemClickListenerListView);

        requestPermission();
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadData();

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemAdd) {

            Intent intent = new Intent(this, MemberAddActivity.class);

            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    private void loadData() {

        MemberDbAdapter memberDbAdapter = MemberDbAdapter.getInstance(this);

        memberDbAdapter.open();

        members = memberDbAdapter.searchAllMembers();

        memberDbAdapter.close();

        adapter = new MemberArrayAdapter(this, members);

        listView.setAdapter(adapter);


    }


    private class MemberArrayAdapter extends ArrayAdapter<String> {

        private final Activity context;

        private final List<Member> members;

        class ViewHolder {

            public ImageView photo;
            public TextView name;
            public TextView email;
        }

        public MemberArrayAdapter(Activity context, List<Member> members) {

            super(context, R.layout.row_layout);

            this.context = context;
            this.members = members;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View rowView = convertView;

            // reuse views
            if (rowView == null) {

                LayoutInflater inflater = context.getLayoutInflater();

                rowView = inflater.inflate(R.layout.row2_layout, null);

                // configure view holder
                ViewHolder viewHolder = new ViewHolder();

                viewHolder.photo = (ImageView) rowView.findViewById(R.id.imageViewPhoto);

                viewHolder.name = (TextView) rowView.findViewById(R.id.textViewName);

                viewHolder.email = (TextView) rowView.findViewById(R.id.textViewEmail);

                rowView.setTag(viewHolder);
            }

            // fill data
            ViewHolder holder = (ViewHolder) rowView.getTag();

            Member member = members.get(position);

            File imgFile = new  File(member.getPhotoUrl());

            if(member.getPhotoUrl() != null) {

                holder.photo.setImageURI(Uri.parse(member.getPhotoUrl()));
            }

            holder.name.setText(member.getMemberName());
            holder.email.setText(member.getEmail());

            return rowView;
        }

        @Override
        public int getCount() {

            return members.size();
        }
    }

    AdapterView.OnItemClickListener onItemClickListenerListView = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            Member member = members.get(position);

            long no = member.getNo();

            Intent intent = new Intent(MainActivity.this, MemberViewActivity.class);
            intent.putExtra("no", no);

            startActivity(intent);
        }
    };
}
