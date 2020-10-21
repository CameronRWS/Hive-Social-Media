package com.example.hivefrontend;

import com.example.hivefrontend.PostDetails.PostDetailsActivity;
import com.example.hivefrontend.Profile.ProfileActivity;
import com.example.hivefrontend.ui.home.HomeFragment;
import com.example.hivefrontend.PostDetails.Logic.PostDetailsLogic;
import com.example.hivefrontend.PostDetails.Network.ServerRequest;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


public class PostDetailsTest {

    @Test
    public void testCommentSuccess(){
        PostDetailsActivity post = mock(PostDetailsActivity.class);
        ServerRequest server = mock(ServerRequest.class);
        PostDetailsLogic logic = new PostDetailsLogic(post,server);

        when(post.getPostId()).thenReturn(3);

        logic.onCommentPostSuccess();

        verify(server,times(1)).requestPostJson(3);
        verify(post,times(1)).handleCommentSuccess();

    }
}
