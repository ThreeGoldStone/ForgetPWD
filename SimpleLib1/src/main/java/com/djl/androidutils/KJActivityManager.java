/*
 * Copyright (c) 2014, KJFrameForAndroid 张涛 (kymjs123@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.djl.androidutils;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出<br>
 *
 * <b>创建时间</b> 2014-2-28
 *
 * @author kymjs(kymjs123@gmail.com)
 * @version 1.1
 */
final public class KJActivityManager {
	private static Stack<Activity> activityStack;

	private KJActivityManager() {
	}

	private static class ManagerHolder {
		private static final KJActivityManager instance = new KJActivityManager();
	}

	public static KJActivityManager create() {
		return ManagerHolder.instance;
	}

	/**
	 * 获取当前Activity栈中元素个数
	 */
	public int getCount() {
		return activityStack.size();
	}

	/**
	 * 添加Activity到栈
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		DJLUtils.log("添加Activity到栈    " + activity.getClass().getName());
		activityStack.add(activity);
	}

	/**
	 * 获取当前Activity（栈顶Activity）
	 */
	public Activity topActivity() {
		if (activityStack == null) {
			throw new NullPointerException(
					"Activity stack is Null,your Activity must extend BaseActivity");
		}
		if (activityStack.isEmpty()) {
			return null;
		}
		Activity activity = activityStack.lastElement();
		return (Activity) activity;
	}

	/**
	 * 获取当前Activity（栈顶Activity） 没有找到则返回null
	 */
	@SuppressWarnings("unchecked")
	public <T extends Activity> T findActivity(Class<T> cls) {
		Activity activity = null;
		for (Activity aty : activityStack) {
			if (aty.getClass().equals(cls)) {
				activity = aty;
				break;
			}
		}
		return (T) activity;
	}

	/**
	 * 结束当前Activity（栈顶Activity）
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity((Activity) activity);
	}

	/**
	 * 结束指定的Activity(重载)
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			DJLUtils.log("结束指定的Activity(重载)Activity    " + activity.getClass().getName());
			activityStack.remove(activity);
			activity.finish();
		}
	}

	/**
	 * 结束指定的Activity(重载)
	 */
	public void finishActivity(Class<?> cls) {
		Activity a = null;
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				DJLUtils.log("结束指定的Activity(重载)class    " + activity.getClass().getName());
				a = activity;
			}
		}
		finishActivity((Activity) a);
	}

	/**
	 * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
	 *
	 * @param cls
	 */
	public void finishOthersActivity(Class<?> cls) {
		Stack<Activity> temp = new Stack<>();
		for (Activity activity : activityStack) {
			if (!(activity.getClass().equals(cls))) {
				temp.add(activity);
			}
		}
		for (Activity activity : temp) {
			finishActivity(activity);
		}
		temp = null;
	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				DJLUtils.log("结束all的Activity   " + activityStack.get(i).getClass().getName());
				((Activity) activityStack.get(i)).finish();
			}
		}
		activityStack.clear();
	}

	/**
	 * 应用程序退出
	 *
	 */
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			DJLUtils.log("Runtime.getRuntime().exit(0)");
			Runtime.getRuntime().exit(0);
		} catch (Exception e) {
			DJLUtils.log("Runtime.getRuntime().exit(-1)");
			Runtime.getRuntime().exit(-1);
		}
	}
}