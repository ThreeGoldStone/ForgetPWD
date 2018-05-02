package com.djl.forgetpwd;

import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;

import com.djl.androidutils.DJLUtils;
import com.djl.forgetpwd.simple_successor.MyActivity;
import com.djl.forgetpwd.view.PWDView;

public class DetailActivity extends MyActivity {


    private PWDView pwd;

    @Override
    public void initData() {
        pwd = (PWDView) vFinder.getView(R.id.PWDView);
//        vFinder.getEditText(R.id.etContent).setText(content);
        pwd.loadImage(Environment.getExternalStorageDirectory().getAbsolutePath() + "/a.png");
    }


    @Override
    public int contentLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
                pwd.setContent(vFinder.getEditText(R.id.etContent).getText().toString(), "hahaha");
                break;
            case R.id.btShow:
                String content = null;
                try {
                    content = pwd.getContent("hahaha");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DJLUtils.log(content);
                break;
            case R.id.btShowPic:
                Bitmap bitmapFromView = PWDView.getBitmapFromView(pwd);
                vFinder.getImageView(R.id.iv).setImageBitmap(bitmapFromView);
                pwd.saveImage(Environment.getExternalStorageDirectory().getAbsolutePath() + "/a.png");
                break;
        }
    }

    //    String content = "1";
    String content2 = "啊";

    String content = "doctor, actor, lawyer or a singer\n" +
            "医生演员律师或歌唱家\n" +
            "why not president, be a dreamer\n" +
            "为什么不是总统？做一个有梦想的人\n" +
            "you can be just the one you wanna be\n" +
            "你可以成为任何一个你想成为的人\n" +
            "police man, fire fighter or a post man\n" +
            "警察消防员或者邮递员\n" +
            "why not something like your old man\n" +
            "为什么不是像你老伙伴的人\n" +
            "you can be just the one you wanna be\n" +
            "你可以成为任何一个你想成为的人\n" +
            "doctor, actor, lawyer or a singer\n" +
            "医生演员律师或者歌唱家\n" +
            "why not president, be a dreamer\n" +
            "为什么不是总统？做一个有梦想的人\n" +
            "you can be just the one you wanna be\n" +
            "你可以成为任何一个你想成为的人\n" +
            "i know that we all got one thing\n" +
            "我知道我们都得到一样东西\n" +
            "that we all share together\n" +
            "那就是我们都在分享的\n" +
            "we got that one nice dream\n" +
            "我们都拥有一个美好的梦想\n" +
            "we live for\n" +
            "我们为之生存\n" +
            "you never know what life could bring\n" +
            "你不会知道生活会给你带来什么\n" +
            "cause nothing last for ever\n" +
            "因为没有什么能永恒\n" +
            "just hold on to the team\n" +
            "只是坚持住\n" +
            "you play for\n" +
            "为了你所努力的团队\n" +
            "i know you could reach the top\n" +
            "我知道你会达到顶峰\n" +
            "make sure that you won't stop\n" +
            "你一定不要停下来\n" +
            "be the one that you wanna be\n" +
            "做那个你一直都想成为的人\n" +
            "now sing this with me\n" +
            "现在和我一起歌唱\n" +
            "doctor, actor, lawyer or a singer\n" +
            "医生演员律师或歌唱家\n" +
            "why not president, be a dreamer\n" +
            "为什么不是总统？做一个有梦想的人\n" +
            "you can be just the one you wanna be\n" +
            "你可以成为任何一个你想成为的人\n" +
            "police man, fire fighter or a post man\n" +
            "警察消防员或者邮递员\n" +
            "why not something like your old man\n" +
            "为什么不是像你老伙伴的人呢？\n" +
            "you can be just the one you wanna be\n" +
            "你可以成为任何一个你想成为的人\n" +
            "we may have different ways to think\n" +
            "我们也许会从不同角度考虑问题\n" +
            "but it doesn't really matter\n" +
            "但这没关系\n" +
            "we all caught up in the steam\n" +
            "我们都赶上了\n" +
            "of this life\n" +
            "这次人生之旅\n" +
            "focus on every little thing\n" +
            "执著于每一件琐事\n" +
            "that's what does really matter\n" +
            "这才是问题所在\n" +
            "luxury cars and bling\n" +
            "金钱（奢华的车）和物质\n" +
            "thats not real life\n" +
            "那都不是真正的生活\n" +
            "i know you could reach the top\n" +
            "我知道你会达到顶峰\n" +
            "make sure that you won't stop\n" +
            "确定你不会停下来\n" +
            "be the one that you wanna be\n" +
            "做那个你一直都想成为的人\n" +
            "now sing this with me\n" +
            "现在和我一起歌唱\n" +
            "doctor, actor, lawyer or a singer\n" +
            "医生演员律师或歌唱家\n" +
            "why not president, be a dreamer\n" +
            "为什么不是总统？做一个有梦想的人\n" +
            "you can be just the one you wanna be\n" +
            "你可以成为任何一个你想成为的人\n" +
            "police man, fire fighter or a post man\n" +
            "警察消防员或者邮递员\n" +
            "why not something like your old man\n" +
            "为什么不是像你老伙伴的人呢\n" +
            "you can be just the one you wanna be\n" +
            "你可以成为任何一个你想成为的人\n" +
            "last year i used to dream about this day\n" +
            "去年我还在梦想着有这么一天\n" +
            "now i'm here i'm singing for you\n" +
            "现在这一切都实现我将为你而唱\n" +
            "i hope i could inspire you\n" +
            "我希望我能鼓舞你的斗气\n" +
            "cause i've got all the love, coz i've got all love for you\n" +
            "因为我得到了所有的爱我得到了 你赐予我的所有的爱\n" +
            "doctor, actor, lawyer or a singer\n" +
            "医生演员律师或歌唱家\n" +
            "why not president, be a dreamer\n" +
            "为什么不是总统？做一个有梦想的人\n" +
            "you can be just the one you wanna be\n" +
            "你可以成为任何一个你想成为的人\n" +
            "police man, fire fighter or a post man\n" +
            "警察消防员或者邮递员\n" +
            "why not something like your old man\n" +
            "为什么不是像你老伙伴的人呢\n" +
            "you can be just the one you wanna be\n" +
            "你可以成为任何一个你想成为的人\n" +
            "Doctor, actor, lawyer or a singer\n" +
            "医生演员律师或歌唱家\n" +
            "why not president, be a dreamer\n" +
            "为什么不是总统？做一个有梦想的人\n" +
            "you can be just the one you wanna be\n" +
            "你可以成为任何一个你想成为的人\n" +
            "police man, fire fighter or a post man\n" +
            "警察消防员或者b邮递员\n" +
            "why not something like your old man\n" +
            "为什么不是像你老伙伴的人呢\n" +
            "you can be just the one you wanna be\n" +
            "你可以成为任何一个你想成为的人\n" +
            "Doctor, actor, lawyer or a singer\n" +
            "医生演员律师或歌唱家\n" +
            "why not president, be a dreamer\n" +
            "为什么不是总统？做一个有梦想的人\n" +
            "you can be just the one you wanna be\n" +
            "你可以成为任何一个你想成为的人\n" +
            "police man, fire fighter or a post man\n" +
            "警察消防员或者a邮递员\n" +
            "why not something like your old man\n" +
            "为什么不是像你老伙伴的人呢\n" +
            "you can be just the one you wanna be\n" +
            "你可以成为任何一个你想成为的人\n";
}
