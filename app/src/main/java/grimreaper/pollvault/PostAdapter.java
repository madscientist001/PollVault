package grimreaper.pollvault;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by soura on 04-09-2017.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    // Store a member variable for the contacts
    private List<Post> mPosts;

    // Pass in the posts array into the constructor
    public PostAdapter(List<Post> posts) {
        mPosts = posts;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView usernameTextView;
        public TextView nameTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.opUsername);
            nameTextView = itemView.findViewById(R.id.opName);
        }
    }

    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PostAdapter.ViewHolder holder, int position) {
        Post post = mPosts.get(position);

        TextView usernameTextView = holder.usernameTextView;
        usernameTextView.setText(post.getPostOPUsername());

        TextView nameTextView = holder.nameTextView;
        nameTextView.setText(post.getPostOPName());
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }
}
