package com.xd.lifepuzzle;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;


public class AfterBeatingGameVideoActivity extends AppCompatActivity {

    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if video exists in the database, then playVideo
        setContentView(R.layout.activity_after_beating_game_video);
        playVideo();

        /*

        //else if audio message exists in the database, then display congratulations game completed along with playing audio

        setContentView(R.layout.activity_after_beating_game_congratulations);
        playAudio(null);

        //else, display congratulations game completed only

        setContentView(R.layout.activity_after_beating_game_congratulations);

        */

    }


    /**
     * Description: plays video
     * currently set to play video "throwingGranolaBarVideo" located in the res -> raw folder
     */
    public void playVideo(){

        //retrieve the video and play the video
        VideoView videoView = findViewById(R.id.video_view);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.throwingfoodvideo;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.start();

        // for the play/pause controls
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
    }


    /**
     * Description: Plays the audio file and calls stopPlayer() after it's finished playing
     * stopPlayer() deletes the MediaPlayer object
     * source used: https://www.youtube.com/watch?v=C_Ka7cKwXW0&ab_channel=CodinginFlow
     */
    public void playAudio(View v) {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.audiotest);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }
        player.start();
    }


    /**
     * Description: Releases the MediaPlayer object and sets it as null
     */
    private void stopPlayer(){
        if(player != null){
            player.release();
            player = null;
            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Description: opens Main Menu Activity
     */
    public void launchMainMenuActivity(View v){
        Intent i = new Intent(this, MainMenuActivity.class);
        startActivity(i);
    }



}
