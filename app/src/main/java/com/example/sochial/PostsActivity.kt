package com.example.sochial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sochial.models.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_posts.*

private const val TAG="PostsActivity"
class PostsActivity : AppCompatActivity() {
    private lateinit var firestoreDB: FirebaseFirestore
    private lateinit var posts:MutableList<Post>
    private lateinit var adapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)
        //    To build a layout to display one post-Done
        //    Create data source-done
        posts= mutableListOf()
        //    Create adapter
        adapter= PostsAdapter(this,posts)
        //    Bind layout manager and adapter to the recycler view
        rvPosts.adapter=adapter
        rvPosts.layoutManager=LinearLayoutManager(this)




        firestoreDB= FirebaseFirestore.getInstance()
        val postsReference=firestoreDB.collection("posts").limit(20).orderBy("creation_time", Query.Direction.DESCENDING)
        postsReference.addSnapshotListener{ snapshot,exception->
            if(exception!=null || snapshot==null) {
                Log.e(TAG, "Error in posts query", exception)
                return@addSnapshotListener
            }


            val postList= snapshot.toObjects(Post::class.java)
            posts.clear()
            posts.addAll(postList)
            adapter.notifyDataSetChanged()

            for (post in postList){
                Log.i(TAG, "Post ${post}:")
            }



        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_posts,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.menu_profile){
            val intent=Intent(this,ProfileActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}