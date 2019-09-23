package com.example.musicapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;


public class MediaService extends Service {
    private MediaPlayer mPlayer;
    /* 绑定服务的实现流程：
     * 1.服务 onCreate， onBind， onDestroy 方法
     * 2.onBind 方法需要返回一个 IBinder 对象
     * 3.如果 Activity 绑定，Activity 就可以取到 IBinder 对象，可以直接调用对象的方法
     */
    // 相同应用内部不同组件绑定，可以使用内部类以及Binder对象来返回。
    public class MusicController extends Binder {
        public void play() {
            //开启音乐
            mPlayer.start();
        }
        public void pause() {
            //暂停音乐
            mPlayer.pause();
        }
        public long getMusicDuration() {
            //获取文件的总长度
            return mPlayer.getDuration();
        }
        public long getPosition() {
            //获取当前播放进度
            return mPlayer.getCurrentPosition();
        }
        public void setPosition (int position) {
            //重新设定播放进度
            mPlayer.seekTo(position);
        }
    }

    /**
     * 当绑定服务的时候，自动回调这个方法
     * 返回的对象可以直接操作Service内部的内容
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicController();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = MediaPlayer.create(this, R.raw.chenqin);
    }

    /**
     * 任意一次unbindService()方法，都会触发这个方法
     * 用于释放一些绑定时使用的资源
     * @param intent
     * @return
     */
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
    @Override
    public void onDestroy() {
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        mPlayer.release();
        mPlayer = null;
        super.onDestroy();
    }
}


