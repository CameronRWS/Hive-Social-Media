//
//package hive.hive;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class PostMockedData {
//    //list of Post posts
//    private List<Post> Posts;
//
//    private static PostMockedData instance = null;
//    public static PostMockedData getInstance(){
//         if(instance == null){
//             instance = new PostMockedData();
//         }
//         return instance;
//    }
//
//
//    public PostMockedData(){
//        Posts = new ArrayList<Post>();
//        Posts.add(new Post(1, 1000, 1, "2018-10-15T17:52:00Z", 
//                "check out this funny video lawl xD https://youtube.com"));
//        Posts.add(new Post(2, 2000, 2, "2018-10-15T17:52:00Z", 
//                "text for some content"));
//        Posts.add(new Post(3, 3000, 3, "2018-10-15T17:52:00Z", 
//                "more text content"));
//        Posts.add(new Post(4, 4000, 4, "2018-10-15T17:52:00Z", 
//                "and some more text content"));
//        Posts.add(new Post(5, 5000, 5, "2018-10-15T17:52:00Z", 
//                "last content"));
//    }
//
//    // return all Posts
//    public List<Post> fetchPosts() {
//        return Posts;
//    }
//
//    // return Post by id
//    public Post getPostByPostId(int postId) {
//        for(Post b: Posts) {
//            if(b.getPostId() == postId) {
//                return b;
//            }
//        }
//        return null;
//    }
//
//    // search Post by text
//    public List<Post> searchPosts(String searchTerm) {
//        List<Post> searchedPosts = new ArrayList<Post>();
//        for(Post b: Posts) {
//            if(b.getContent().toLowerCase().contains(searchTerm.toLowerCase())) {
//                searchedPosts.add(b);
//            }
//        }
//
//        return searchedPosts;
//    }
//
//    // create Post
//    public Post createPost(int postId, int hiveId, int userId, String dateCreated, String textContent) {
//        Post newPost = new Post(postId, hiveId, userId, dateCreated, textContent);
//        Posts.add(newPost);
//        return newPost;
//    }
//
//    // update Post
//    public Post updatePost(int postId, int hiveId, int userId, String dateCreated, String textContent) {
//        for(Post b: Posts) {
//            if(b.getPostId() == postId && b.getHiveId() == hiveId && b.getUserId() == userId) {
//                int PostIndex = Posts.indexOf(b);
//                b.setContent(textContent);
//                Posts.set(PostIndex, b);
//                return b;
//            }
//
//        }
//
//        return null;
//    }
//
//    // delete Post by id
//    public boolean delete(int postId){
//        int PostIndex = -1;
//        for(Post b: Posts) {
//            if(b.getPostId() == postId) {
//                PostIndex = Posts.indexOf(b);
//                continue;
//            }
//        }
//        if(PostIndex > -1){
//            Posts.remove(PostIndex);
//        }
//        return true;
//    }
//
//}