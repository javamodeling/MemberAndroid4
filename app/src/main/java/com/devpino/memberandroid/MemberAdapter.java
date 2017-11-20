package com.devpino.memberandroid;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DHKOH on 2017-11-20.
 */

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    private List<Member> members = null;

    static class MemberViewHolder extends RecyclerView.ViewHolder {

        public ImageView photo;
        public TextView name;
        public TextView email;
        public LinearLayout rowLinearLayout;

        MemberViewHolder(View view) {
            super(view);
            photo = (ImageView)view.findViewById(R.id.imageViewPhoto);
            name = (TextView)view.findViewById(R.id.textViewName);
            email = (TextView)view.findViewById(R.id.textViewEmail);
            rowLinearLayout = (LinearLayout)view;
        }
    }

    public MemberAdapter(List<Member> members) {
        this.members = members;
    }

    public void add(int position, Member member) {
        members.add(position, member);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        members.remove(position);
        notifyItemRemoved(position);
    }


    // Create new views (invoked by the layout manager)
    @Override
    public MemberAdapter.MemberViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.row2_layout, parent, false);

        MemberViewHolder memberViewHolder= new MemberViewHolder(v);

        return memberViewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MemberViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Member member = members.get(position);
        holder.name.setText(member.getMemberName());
        holder.email.setText(member.getEmail());

        if(member.getPhotoUrl() != null) {

            holder.photo.setImageURI(Uri.parse(member.getPhotoUrl()));
        }

        holder.rowLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Member member = members.get(position);

                long no = member.getNo();

                Intent intent = new Intent(view.getContext(), MemberViewActivity.class);
                intent.putExtra("no", no);

                view.getContext().startActivity(intent);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return members.size();
    }

}
