package com.utils;

import java.util.HashMap;


import com.Data.ApplicationTrans;
import com.ui.MainActivity;
import com.ui.R;

/**
 * Created by liurongchan on 14-4-25.
 */
public class Utils {

	public static final String FORUM_CATEGORY[] = ApplicationTrans.getContext()
			.getResources().getStringArray(R.array.forumcategory);

	public static final HashMap<String, Integer> FORUM_CATEGORY_ID = new HashMap<String, Integer>() {
		/**
         *
         */
		private static final long serialVersionUID = 1410556396714226136L;

		{
			put("最新回复", 10001);
			put("最新发表", 10002);
			put("今日热门", 10003);

			put("保研考研", 199);
			put("LaTeX技术交流", 308);
			put("聚焦两会", 277);
			put("镜头下的成电", 307);
			put("一周锐评", 309);
			put("名师博文", 276);
			put("大学生热点", 275);
			put("成电UED", 310);
			put("前端之美", 258);
			put("互联网资讯", 271);
			put("数字前端", 272);
			put("数学之美", 266);
			put("电脑FAQ", 66);
			put("硬件数码", 108);
			put("Unix_Linux", 99);
			put("程序员", 70);
			put("电子设计", 121);
			put("就业创业", 174);
			put("手机之家", 224);
			put("出国留学", 219);
			put("相约回家", 225);
			put("学习交流", 20);
			put("成电轨迹", 109);
			put("情感专区", 45);
			put("生活百科", 31);
			put("老乡会", 17);
			put("成电骑迹", 244);
			put("摄影艺术", 55);
			put("旅游专版", 30);
			put("动漫时代", 140);
			put("会心一笑", 41);
			put("影视天地", 149);
			put("游戏世界", 191);
			put("经典图吧", 42);
			put("娱乐花边", 212);
			put("体坛风云", 204);
			put("音乐空间", 74);
			put("情系舞缘", 197);
			put("校园新闻", 257);
			put("时政要闻", 44);
			put("社会百态", 136);
			put("科技教育", 211);
			put("军事国防", 115);
			put("经济相关", 137);
			put("二手专区", 61);
			put("店铺专区", 111);
			put("房屋租赁", 255);
			put("水手之家", 25);
			put("历史_文化_人物", 181);
			put("文人墨客", 114);
			put("我的大学生活", 236);
			put("毕业感言", 237);
			put("母校发展_我来献策", 238);
			put("站务公告", 2);
			put("站务综合", 46);
			put("影视资源", 233);
			put("体育资源", 152);
			put("动漫资源", 128);
			put("软件资源", 252);
			put("音乐资源", 229);
		}
	};
}
