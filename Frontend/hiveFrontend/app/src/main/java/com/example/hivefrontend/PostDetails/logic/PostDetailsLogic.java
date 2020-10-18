package com.example.hivefrontend.PostDetails.Logic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hivefrontend.PostDetails.Network.ServerRequest;
import com.example.hivefrontend.PostDetails.PostDetailsActivity;
import com.example.hivefrontend.R;

public class PostDetailsLogic {

    PostDetailsActivity post;
    public PostDetailsLogic(PostDetailsActivity p){
        post = p;
    }

    public void getPostInfoJson(int postId){
        ServerRequest server = new ServerRequest(post);
        server.requestPostJson(postId);
    }


    public void promptDialog() {
        LayoutInflater li = LayoutInflater.from(post);
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                post);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                if(userInput.getText().toString().length()==0){
                                    Toast.makeText(post.getApplicationContext(),"Cannot submit an empty comment!", Toast.LENGTH_LONG);
                                }
                                else {
                                    ServerRequest server = new ServerRequest(post);
                                    server.postComment(userInput.getText().toString());
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void checkLikesAndPost(){
        ServerRequest server = new ServerRequest(post);
        server.checkLikesAndPost();
    }
}
