package com.qcj.common.util.localImageHelper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.ImageColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * author：qiuchunjia time：下午1:43:02
 * 
 * 类描述：这个类是实现获取 手机当中每一个文件夹的照片，
 * 
 * 并分类展示出来
 *
 */

public class LocalImage {
	public final static String IMG_JPG = "image/jpg";
	public final static String IMG_JPEG = "image/jpeg";
	public final static String IMG_PNG = "image/png";
	public final static String IMG_GIF = "image/gif";

	/**
	 * 获取每一个文件夹的照片对应的需要的信息，对应的信息可以再photodirinfo里面看
	 * 
	 * @param context
	 * @return ArrayList
	 */
	public static synchronized ArrayList<PhotoDirInfo> getImageDir(
			Context context) {
		ArrayList<PhotoDirInfo> list = null;
		PhotoDirInfo dirInfo = null;
		ContentResolver mResolver = context.getContentResolver();
		String[] IMAGE_PROJECTION = new String[] { ImageColumns.BUCKET_ID,
				ImageColumns.BUCKET_DISPLAY_NAME, ImageColumns.DATA,
				"COUNT(*) AS " + ImageColumns.DATA };

		String selection = " 1=1 AND " + ImageColumns.MIME_TYPE
				+ " IN (?,?,?)) GROUP BY (" + ImageColumns.BUCKET_ID
				+ ") ORDER BY (" + ImageColumns.BUCKET_DISPLAY_NAME;

		String[] selectionArgs = new String[] { IMG_JPG, IMG_JPEG, IMG_PNG };
		Cursor cursor = mResolver.query(Images.Media.EXTERNAL_CONTENT_URI,
				IMAGE_PROJECTION, selection, selectionArgs, null);
		if (null != cursor) {
			if (cursor.getCount() > 0) {
				list = new ArrayList<PhotoDirInfo>();
				while (cursor.moveToNext()) {
					dirInfo = new PhotoDirInfo();
					dirInfo.setDirId(cursor.getString(0));
					dirInfo.setDirName(cursor.getString(1));
					dirInfo.setFirstPicPath(cursor.getString(2));
					dirInfo.setPicCount(cursor.getInt(3));
					dirInfo.setUserOtherPicSoft(false);
					list.add(dirInfo);
				}
			}
			cursor.close();
		}
		return list;
	}

	/**
	 * 根据代表某文件的id，来获取这个文件里面的所有的照片路径（即每一张照片的路径）
	 * 
	 * @param context
	 * @param bucketID
	 *            代表某文件的id 这id是随机的，
	 * @return
	 */
	public static List<String> getPhotoFileNameById(Context context,
			String bucketID) {
		ArrayList<String> tmpList = new ArrayList<String>();
		ContentResolver mResolver = context.getContentResolver();
		String[] IMAGE_PROJECTION = new String[] { ImageColumns.DATA };

		String selection = ImageColumns.BUCKET_ID + "= ?  AND "
				+ ImageColumns.MIME_TYPE + " IN (?,?,?)";

		String[] selectionArgs = new String[] { bucketID, IMG_JPG, IMG_JPEG,
				IMG_PNG };

		Cursor cursor = mResolver.query(Images.Media.EXTERNAL_CONTENT_URI,
				IMAGE_PROJECTION, selection, selectionArgs, null);
		if (null != cursor) {
			if (cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					tmpList.add(cursor.getString(0));
				}
			}
			cursor.close();
		}
		return tmpList;
	}
}
