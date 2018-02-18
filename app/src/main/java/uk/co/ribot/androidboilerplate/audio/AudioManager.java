package uk.co.ribot.androidboilerplate.audio;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by drdgv on 2018-02-18.
 */

public class AudioManager {

    private static final int SAMPLING_RATE_IN_HZ = 44100;

    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;

    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

    private static final int BUFFER_SIZE_FACTOR = 2;

    /**
     * Size of the buffer where the audio data is stored by Android
     */
    private static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLING_RATE_IN_HZ,
            CHANNEL_CONFIG, AUDIO_FORMAT) * BUFFER_SIZE_FACTOR;

    /**
     * Signals whether a recording is in progress (true) or not (false).
     */
    private final AtomicBoolean recordingInProgress = new AtomicBoolean(false);

    private AudioRecord recorder = null;

    private Thread recordingThread = null;

    private Button startButton;

    private Button stopButton;

    private int avgDb;

    private double avgDbOverTime=0;
    private long numOfAvgs=1;
    private Thread avgThread = null;

    public int getAvgDb() {
        return this.avgDb;
    }
    public AudioManager() {
    }

    public double getAvg() {
        double temp = avgDbOverTime;
        avgDbOverTime = 0;
        numOfAvgs = 0;
        Log.i("avg over time = ", ""+temp+" /n AVG CLEARED!");
        return temp;
    }


    public void startRecording() {
        recorder = new AudioRecord(MediaRecorder.AudioSource.DEFAULT, SAMPLING_RATE_IN_HZ,
                CHANNEL_CONFIG, AUDIO_FORMAT, BUFFER_SIZE);

        recorder.startRecording();

        recordingInProgress.set(true);

        recordingThread = new Thread(new RecordingRunnable(), "Recording Thread");
        recordingThread.start();
    }

    private class RecordingRunnable implements Runnable {

        @Override
        public void run() {
            final ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
            while (recordingInProgress.get()) {
                int result = recorder.read(buffer, BUFFER_SIZE);
                if (result < 0) {
                    throw new RuntimeException("Reading of audio buffer failed: " +
                            getBufferReadFailureReason(result));
                }

                recorder.read(buffer, 0, BUFFER_SIZE);
                double loudness = 0;
                int counter = 0;
                while (buffer.hasRemaining()) {
                    buffer.position();
                    double s = buffer.get();
                    //if (Math.abs(s) > loudness)
                    //{
                    loudness += Math.abs(s);
                    counter++;
                    // Log.i (tag, "Loudness = " + s);

                    //}
                }
                avgDb = (int) loudness * 2 / counter;
                Log.i ("Derp", "Loudness = " + avgDb);
                buffer.clear();

                if(avgDbOverTime==0) {
                    avgDbOverTime = avgDb;
                }
                avgDbOverTime=(avgDbOverTime*(numOfAvgs++) + avgDb)/numOfAvgs;
                Log.i("AvgOverTime: ", ""+avgDbOverTime);
            }
        }

        private String getBufferReadFailureReason(int errorCode) {
            switch (errorCode) {
                case AudioRecord.ERROR_INVALID_OPERATION:
                    return "ERROR_INVALID_OPERATION";
                case AudioRecord.ERROR_BAD_VALUE:
                    return "ERROR_BAD_VALUE";
                case AudioRecord.ERROR_DEAD_OBJECT:
                    return "ERROR_DEAD_OBJECT";
                case AudioRecord.ERROR:
                    return "ERROR";
                default:
                    return "Unknown (" + errorCode + ")";
            }
        }
    }
}
