package com.cwj.imagesynthesis

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.youth.banner.adapter.BannerAdapter


/**
 *  author : ChenWenJie
 *  email  : 1181620038@qq.com
 *  date   : 2020/9/25
 *  desc   : 图片适配器
 */
class ImageAdapter(datas: MutableList<ImageBean>?) :
    BannerAdapter<ImageBean, ImageAdapter.BannerViewHolder>(datas) {
    class BannerViewHolder(var imageView: ImageView) : ViewHolder(imageView)
    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent!!.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        imageView.scaleType=ImageView.ScaleType.FIT_CENTER
        return BannerViewHolder(imageView)
    }

    override fun onBindView(holder: BannerViewHolder?, data: ImageBean?, position: Int, size: Int) {
        holder!!.imageView.setImageBitmap(data!!.image);
    }

}

 