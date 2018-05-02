//package com.djl.forgetpwd;
//
//import android.content.Context;
//import android.support.test.InstrumentationRegistry;
//import android.support.test.runner.AndroidJUnit4;
//import android.util.Log;
//
//import com.djl.forgetpwd.bean.NameValuePairBean;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//
///**
// * Instrumentation test, which will execute on an Android device.
// *
// * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
// */
//@RunWith(AndroidJUnit4.class)
//public class ExampleInstrumentedTest {
//    @Test
//    public void useAppContext() throws Exception {
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getTargetContext();
//
//        assertEquals("com.djl.forgetpwd", appContext.getPackageName());
//    }
//
//    @Test
//    public void nameValuePairTest() {
//        NameValuePairBean base = new NameValuePairBean();
//        NameValuePairBean[] pairBeen = {
//                new NameValuePairBean().setName("sub1Name").setValue("sub1Value"),
//                new NameValuePairBean().setName("sub2Name").setValue("sub2Value"),
//                new NameValuePairBean().setName("sub3Name").setValue("sub3Value").setSubs(new NameValuePairBean[]{
//                        new NameValuePairBean().setName("subsub1Name").setValue("subsub1Value"),
//                        new NameValuePairBean().setName("subsub2Name").setValue("subsub2Value"),
//                }),
//        };
//        base.setName("baseName").setValue("baseValue").setSubs(pairBeen);
////        System.out.println(base);
//        Log.i("abccccccc", "nameValuePairTest: " + base.toString());
//    }
//
//
//
//    private List<NameValuePairBean> getBeen(ArrayList<NameValuePairBean> nameValuePairBeen, NameValuePairBean data) {
//        NameValuePairBean[] been;
//        been = data.getSubs();
//        data.setSubs(null);
//        nameValuePairBeen.add(data);
//        if (been != null && been.length > 0) {
//            for (NameValuePairBean nameValuePairBean : been) {
//                nameValuePairBean.setProgress(data.getProgress() / 2);
//                getBeen(nameValuePairBeen, nameValuePairBean);
//            }
//        }
//        return nameValuePairBeen;
//    }
//
//    private NameValuePairBean getData() {
//        NameValuePairBean base = new NameValuePairBean();
//        NameValuePairBean[] pairBeen = {
//                new NameValuePairBean().setName("sub1Name").setValue("sub1Value"),
//                new NameValuePairBean().setName("sub2Name").setValue("sub2Value"),
//                new NameValuePairBean().setName("sub3Name").setValue("sub3Value").setSubs(new NameValuePairBean[]{
//                        new NameValuePairBean().setName("subsub1Name").setValue("subsub1Value"),
//                        new NameValuePairBean().setName("subsub2Name").setValue("subsub2Value"),
//                }),
//        };
//        base.setName("baseName").setValue("baseValue").setSubs(pairBeen);
//        return base;
//    }
//}
