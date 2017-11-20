package com.devpino.memberandroid;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Member> members = new ArrayList<>();

    private RecyclerView recyclerView;

    private RecyclerView.Adapter memberAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewMembers);

        recyclerView.setHasFixedSize(true);

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

        memberAdapter = new MemberAdapter(members);

        recyclerView.setAdapter(memberAdapter);

    }

}
