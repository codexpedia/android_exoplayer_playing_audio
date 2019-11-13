package com.example.exoplayerplayingaudio

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Player.EventListener {

    lateinit var player: SimpleExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val renderersFactory = DefaultRenderersFactory(this)
        val trackSelectionFactory = AdaptiveTrackSelection.Factory()
        val trackSelectSelector = DefaultTrackSelector(trackSelectionFactory)
        val loadControl = DefaultLoadControl()

        player = ExoPlayerFactory.newSimpleInstance(this, renderersFactory, trackSelectSelector, loadControl)
        player.addListener(this)

        val dataSourceFactory = DefaultDataSourceFactory(this, getString(R.string.app_name))
        val extractorsFactory = DefaultExtractorsFactory()

        val mediaSource = ProgressiveMediaSource
            .Factory(dataSourceFactory, extractorsFactory)
            .createMediaSource(Uri.parse("https://file-examples.com/wp-content/uploads/2017/11/file_example_MP3_5MG.mp3"))

        player.prepare(mediaSource)

        video_view.player = player

    }

    override fun onResume() {
        super.onResume()
        player.playWhenReady = true
    }

    override fun onPause() {
        super.onPause()
        player.playWhenReady = false
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

    //region Player.EventListener
    override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
    }

    override fun onLoadingChanged(isLoading: Boolean) {
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
    }

    override fun onPlayerError(error: ExoPlaybackException?) {
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
    }
    //endregion
}
