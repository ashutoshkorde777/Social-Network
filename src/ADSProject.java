
import java.util.*;


class User{
    public String name;
    ArrayList<Post> posts;

    User(String name){
        this.name = name;
        this.posts = new ArrayList<>();
    }




    public void addPost(String content) {
        Post post = new Post(content);
        posts.add(post);
    }
}

class Post{
    String content;
    ArrayList<Like> likes;
    ArrayList<Comment> comments;

    Post(String content){
        this.content = content;
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public void addComment(User commenter, String content) {
        Comment comment = new Comment(commenter, content);
        comments.add(comment);
    }

    public void addLike(User liker) {
        Like like = new Like(liker);
        likes.add(like);
    }

}

class Like{
    User likedBy;
    Like(User likedBy){
        this.likedBy = likedBy;
    }
}
class Comment{
    String content;
    User commentBy;
    Comment(User commentBy, String content){
        this.commentBy = commentBy;
        this.content = content;
    }
}


public class ADSProject {
    public static HashMap<User, LinkedList<User>> socialNetwork = new HashMap<>();



    public static void addConnection(User user1, User user2) {
        socialNetwork.putIfAbsent(user1, new LinkedList<>());
        socialNetwork.putIfAbsent(user2, new LinkedList<>());

        socialNetwork.get(user1).add(user2);
        socialNetwork.get(user2).add(user1);
    }

    public static String printSocialNetwork() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<User, LinkedList<User>> entry : socialNetwork.entrySet()) {
            User user = entry.getKey();
            LinkedList<User> friends = entry.getValue();

            sb.append(user.name).append(" is connected to: ");
            for (User friend : friends) {
                sb.append(friend.name).append(", ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public static String displayFriends(User user) {
        StringBuilder sb = new StringBuilder();
        LinkedList<User> friends = socialNetwork.getOrDefault(user, new LinkedList<>());
        if (friends.isEmpty()) {
            sb.append(user.name).append(" has no friends.");
        } else {
            sb.append(user.name).append(" is friends with: ");
            for (User friend : friends) {
                sb.append(friend.name).append(", ");
            }
            sb.setLength(sb.length() - 2); // Remove the last ", "
        }

        return sb.toString();
    }


    public static String findFriendsOfFriends(User user) {
        StringBuilder sb = new StringBuilder();

        LinkedList<User> friends = socialNetwork.getOrDefault(user, new LinkedList<>());
        Set<User> visited = new HashSet<>();
        Set<User> friendsOfUser = new HashSet<>(friends);

        Queue<User> queue = new LinkedList<>();
        queue.add(user);

        sb.append("Friends of friends of  ").append(user.name).append("\n");

        while (!queue.isEmpty()) {
            User currUser = queue.remove();
            LinkedList<User> currFriends = socialNetwork.getOrDefault(currUser, new LinkedList<>());
            if (!visited.contains(currUser)) {
                if (currUser != user && !friendsOfUser.contains(currUser)) {
                    sb.append(currUser.name).append("\n");
                }
                visited.add(currUser);

                for (User f : currFriends) {
                    queue.add(f);
                }
            }
        }

        return sb.toString();
    }


    public static void makeFriends(User user1, User user2) {
        if (!socialNetwork.containsKey(user1) || !socialNetwork.containsKey(user2)) {
            System.out.println("One or both of the users do not exist.");
            return;
        }

        if (socialNetwork.get(user1).contains(user2)) {
            System.out.println(user1.name + " and " + user2.name + " are already friends.");
            return;
        }

        socialNetwork.get(user1).add(user2);
        socialNetwork.get(user2).add(user1);
        System.out.println(user1.name + " and " + user2.name + " are now friends.");
    }

    public static ArrayList<String[]> seeFriendsPosts(User user) {
        ArrayList<String[]> postsList = new ArrayList<>();

        LinkedList<User> friends = socialNetwork.getOrDefault(user, new LinkedList<>());

        if (friends.isEmpty()) {
            postsList.add(new String[]{ user.name + " has no friends." });
        } else {
            for (User friend : friends) {
                for (Post post : friend.posts) {
                    String[] postInfo = new String[5];
                    postInfo[0] = "Posted by: " + friend.name + ", Content: " + post.content;

                    // Likes
                    if (post.likes.isEmpty()) {
                        postInfo[1] = "No likes";
                    } else {
                        postInfo[1] = "Likes:";
                        for (Like like : post.likes) {
                            postInfo[1] += "\n- Liked by: " + like.likedBy.name;
                        }
                    }

                    // Comments
                    if (post.comments.isEmpty()) {
                        postInfo[2] = "No comments";
                    } else {
                        postInfo[2] = "Comments:";
                        for (Comment comment : post.comments) {
                            postInfo[2] += "\n- Comment by: " + comment.commentBy.name + ", Content: " + comment.content;
                        }
                    }

                    postInfo[3] = "Total Likes: " + post.likes.size();
                    postInfo[4] = "Total Comments: " + post.comments.size();

                    postsList.add(postInfo);
                }
            }
        }

        return postsList;
    }



    public static ArrayList<String[]> topFivePostsOfFriends(User user) {
        ArrayList<String[]> postsList = new ArrayList<>();

        LinkedList<User> friends = socialNetwork.getOrDefault(user, new LinkedList<>());

        if (friends.isEmpty()) {
            postsList.add(new String[]{ user.name + " has no friends." });
        } else {
            PriorityQueue<PostWithUser> pq = new PriorityQueue<>((a, b) -> b.post.likes.size() - a.post.likes.size());

            for (User friend : friends) {
                for (Post post : friend.posts) {
                    pq.offer(new PostWithUser(post, friend));
                }
            }

            postsList.add(new String[]{ "Top five posts of " + user.name + "'s friends:" });
            int count = 0;
            while (!pq.isEmpty() && count < 5) {
                PostWithUser postWithUser = pq.poll();
                Post post = postWithUser.post;
                User friend = postWithUser.user;
                String[] postInfo = new String[5];
                postInfo[0] = "Posted by: " + friend.name + ", Content: " + post.content;

                // Likes
                if (post.likes.isEmpty()) {
                    postInfo[1] = "No likes";
                } else {
                    postInfo[1] = "Likes:";
                    for (Like like : post.likes) {
                        postInfo[1] += "\n- Liked by: " + like.likedBy.name;
                    }
                }

                // Comments
                if (post.comments.isEmpty()) {
                    postInfo[2] = "No comments";
                } else {
                    postInfo[2] = "Comments:";
                    for (Comment comment : post.comments) {
                        postInfo[2] += "\n- Comment by: " + comment.commentBy.name + ", Content: " + comment.content;
                    }
                }

                postInfo[3] = "Total Likes: " + post.likes.size();
                postInfo[4] = "Total Comments: " + post.comments.size();

                postsList.add(postInfo);
                count++;
            }
        }

        return postsList;
    }




    public static ArrayList<String[]> topFivePostsOfNetwork() {
        ArrayList<String[]> postsList = new ArrayList<>();

        PriorityQueue<PostWithUser> pq = new PriorityQueue<>((a, b) -> b.post.likes.size() - a.post.likes.size());
        Set<User> visited = new HashSet<>();

        for (User user : socialNetwork.keySet()) {
            dfs(user, visited, pq);
        }

        postsList.add(new String[]{ "Top five posts of the entire social network:" });
        int count = 0;
        while (!pq.isEmpty() && count < 5) {
            PostWithUser postWithUser = pq.poll();
            Post post = postWithUser.post;
            User user = postWithUser.user;
            String[] postInfo = new String[5];
            postInfo[0] = "Posted by: " + user.name + ", Content: " + post.content;

            // Likes
            if (post.likes.isEmpty()) {
                postInfo[1] = "No likes";
            } else {
                postInfo[1] = "Likes:";
                for (Like like : post.likes) {
                    postInfo[1] += "\n- Liked by: " + like.likedBy.name;
                }
            }

            // Comments
            if (post.comments.isEmpty()) {
                postInfo[2] = "No comments";
            } else {
                postInfo[2] = "Comments:";
                for (Comment comment : post.comments) {
                    postInfo[2] += "\n- Comment by: " + comment.commentBy.name + ", Content: " + comment.content;
                }
            }

            postInfo[3] = "Total Likes: " + post.likes.size();
            postInfo[4] = "Total Comments: " + post.comments.size();

            postsList.add(postInfo);
            count++;
        }

        return postsList;
    }



    private static void dfs(User user, Set<User> visited, PriorityQueue<PostWithUser> pq) {
        if (visited.contains(user)) {
            return;
        }
        visited.add(user);

        ArrayList<Post> posts = user.posts;
        for (Post post : posts) {
            pq.offer(new PostWithUser(post, user));
        }

        LinkedList<User> friends = socialNetwork.getOrDefault(user, new LinkedList<>());
        for (User friend : friends) {
            dfs(friend, visited, pq);
        }
    }

    static class PostWithUser {
        Post post;
        User user;

        PostWithUser(Post post, User user) {
            this.post = post;
            this.user = user;
        }
    }


    public static String findTopUsers() {
        StringBuilder sb = new StringBuilder();

        PriorityQueue<User> pq = new PriorityQueue<>((a, b) -> socialNetwork.get(b).size() - socialNetwork.get(a).size());
        Set<User> visited = new HashSet<>();

        for (User user : socialNetwork.keySet()) {
            dfs2(user, visited, pq);
        }

        sb.append("Top users based on the number of friends in the total network:\n");
        int count = 0;
        while (!pq.isEmpty() && count < 5) {
            User user = pq.remove();
            sb.append(user.name).append(" has ").append(socialNetwork.get(user).size()).append(" friends.\n");
            count++;
        }

        return sb.toString();
    }


    private static void dfs2(User user, Set<User> visited, PriorityQueue<User> pq) {
        if (visited.contains(user)) {
            return;
        }
        visited.add(user);
        pq.add(user);

        LinkedList<User> friends = socialNetwork.getOrDefault(user, new LinkedList<>());
        for (User friend : friends) {
            dfs2(friend, visited, pq);
        }
    }

    public static ArrayList<String[]> seeUserPosts(User user) {
        ArrayList<String[]> postsList = new ArrayList<>();

        ArrayList<Post> userPosts = user.posts;

        if (userPosts.isEmpty()) {
            String[] noPosts = {"Posts of " + user.name + ":", "No posts found.", "", "", ""};
            postsList.add(noPosts);
        } else {
            for (Post post : userPosts) {
                String[] postInfo = new String[5];
                postInfo[0] = "Content: " + post.content;

                // Likes
                if (post.likes.isEmpty()) {
                    postInfo[1] = "No likes";
                } else {
                    postInfo[1] = "Likes:";
                    for (Like like : post.likes) {
                        postInfo[1] += "\n- Liked by: " + like.likedBy.name;
                    }
                }

                // Comments
                if (post.comments.isEmpty()) {
                    postInfo[2] = "No comments";
                } else {
                    postInfo[2] = "Comments:";
                    for (Comment comment : post.comments) {
                        postInfo[2] += "\n- Comment by: " + comment.commentBy.name + ", Content: " + comment.content;
                    }
                }

                postInfo[3] = "Total Likes: " + post.likes.size();
                postInfo[4] = "Total Comments: " + post.comments.size();

                postsList.add(postInfo);
            }
        }

        return postsList;
    }





    ADSProject(){
        User user1 = new User("Shreyash");
        User user2 = new User("Sudhanshu");
        User user3 = new User("Sharvari");
        User user4 = new User("Ashutosh");
        User user5 = new User("Aditya");
        User user6 = new User("Ayush");
        User user7 = new User("Aniket");
        User user8 = new User("Soham");
        User user9 = new User("Harshad");
        User user10 = new User("Sarthak");

        addConnection(user1, user2);
        addConnection(user1, user3);
        addConnection(user2, user3);
        addConnection(user4, user5);
        addConnection(user3, user4);
        addConnection(user4, user6);
        addConnection(user5, user6);
        addConnection(user6, user7);
        addConnection(user7, user8);
        addConnection(user8, user9);
        addConnection(user9, user10);

        user1.addPost("This is my first post.");
        user1.addPost("Feeling great today!");
        user2.addPost("Hello world!");
        user3.addPost("Happy birthday to me!");

        user1.posts.get(0).addComment(user2, "Nice post!");
        user1.posts.get(1).addComment(user3, "Congratulations!");

        user1.posts.get(0).addLike(user2);
        user1.posts.get(1).addLike(user3);
        user2.posts.get(0).addLike(user1);

        // Adding more posts, likes, and comments

        user2.addPost("Having fun with Spring Framework!");
        user3.addPost("Excited about the new project!");
        user4.addPost("Just finished a coding marathon!");
        user5.addPost("Enjoying a relaxing weekend.");

        user2.posts.get(0).addComment(user1, "Great to hear!");
        user3.posts.get(0).addComment(user2, "Can't wait to see it!");
        user4.posts.get(0).addComment(user3, "Impressive!");

        user2.posts.get(0).addLike(user1);
        user3.posts.get(0).addLike(user1);
        user4.posts.get(0).addLike(user1);
        user5.posts.get(0).addLike(user1);
    }

}














