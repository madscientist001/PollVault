package grimreaper.pollvault;

import java.util.ArrayList;
import java.util.List;

class Post {

    private String postOPUsername;
    private String postOPName;

    public Post(String username, String name){
        postOPUsername = username;
        postOPName = name;
    }

    public String getPostOPUsername() {
        return postOPUsername;
    }

    public void setPostOPUsername(String postOPUsername) {
        this.postOPUsername = postOPUsername;
    }

    public String getPostOPName() {
        return postOPName;
    }

    public void setPostOPName(String postOPName) {
        this.postOPName = postOPName;
    }

    private static int lastPostId = 0;

    public static List<Post> createPostList(int numPosts, int offset) {
        List<Post> posts = new ArrayList<Post>();

        for (int i = 1; i <= numPosts; i++) {
            lastPostId++;
            posts.add(new Post("User " + lastPostId , "Person " + lastPostId));
        }

        return posts;
    }
}
