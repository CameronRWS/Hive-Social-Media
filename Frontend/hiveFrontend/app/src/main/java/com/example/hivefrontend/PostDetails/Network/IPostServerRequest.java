package com.example.hivefrontend.PostDetails.Network;

import com.example.hivefrontend.PostDetails.Logic.IPostVolleyListener;
import com.example.hivefrontend.PostDetails.Logic.PostDetailsLogic;

/**
 * Interface implemented by the post details ServerRequest
 */
public interface IPostServerRequest {

    void addVolleyListener(IPostVolleyListener l);

    void requestPostJson(int postId);

    void checkLikesAndPost();
}
