package grimreaper.pollvault;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ContentFragment extends Fragment {

    public ContentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_content, container, false);
        RecyclerView rvItems = (RecyclerView) v.findViewById(R.id.recyclerView);
        final List<Post> allPosts = Post.createPostList(10, 0);
        final PostAdapter adapter = new PostAdapter(allPosts);
        rvItems.setAdapter(adapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());
        rvItems.setLayoutManager(linearLayoutManager);
        EndlessScrollListener scrollListener = new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                List<Post> morePosts = Post.createPostList(10, page);
                final int curSize = adapter.getItemCount();
                allPosts.addAll(morePosts);

                view.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyItemRangeInserted(curSize, allPosts.size() - 1);
                    }
                });
            }
        };
        rvItems.addOnScrollListener(scrollListener);
        return v;
    }
}
