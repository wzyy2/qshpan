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
			put("���»ظ�", 10001);
			put("���·���", 10002);
			put("��������", 10003);

			put("���п���", 199);
			put("LaTeX��������", 308);
			put("�۽�����", 277);
			put("��ͷ�µĳɵ�", 307);
			put("һ������", 309);
			put("��ʦ����", 276);
			put("��ѧ���ȵ�", 275);
			put("�ɵ�UED", 310);
			put("ǰ��֮��", 258);
			put("��������Ѷ", 271);
			put("����ǰ��", 272);
			put("��ѧ֮��", 266);
			put("����FAQ", 66);
			put("Ӳ������", 108);
			put("Unix_Linux", 99);
			put("����Ա", 70);
			put("�������", 121);
			put("��ҵ��ҵ", 174);
			put("�ֻ�֮��", 224);
			put("������ѧ", 219);
			put("��Լ�ؼ�", 225);
			put("ѧϰ����", 20);
			put("�ɵ�켣", 109);
			put("���ר��", 45);
			put("����ٿ�", 31);
			put("�����", 17);
			put("�ɵ��Ｃ", 244);
			put("��Ӱ����", 55);
			put("����ר��", 30);
			put("����ʱ��", 140);
			put("����һЦ", 41);
			put("Ӱ�����", 149);
			put("��Ϸ����", 191);
			put("����ͼ��", 42);
			put("���ֻ���", 212);
			put("��̳����", 204);
			put("���ֿռ�", 74);
			put("��ϵ��Ե", 197);
			put("У԰����", 257);
			put("ʱ��Ҫ��", 44);
			put("����̬", 136);
			put("�Ƽ�����", 211);
			put("���¹���", 115);
			put("�������", 137);
			put("����ר��", 61);
			put("����ר��", 111);
			put("��������", 255);
			put("ˮ��֮��", 25);
			put("��ʷ_�Ļ�_����", 181);
			put("����ī��", 114);
			put("�ҵĴ�ѧ����", 236);
			put("��ҵ����", 237);
			put("ĸУ��չ_�����ײ�", 238);
			put("վ�񹫸�", 2);
			put("վ���ۺ�", 46);
			put("Ӱ����Դ", 233);
			put("������Դ", 152);
			put("������Դ", 128);
			put("�����Դ", 252);
			put("������Դ", 229);
		}
	};
}
