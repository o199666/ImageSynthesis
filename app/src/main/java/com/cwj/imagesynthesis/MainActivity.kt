package com.cwj.imagesynthesis

import android.R.attr.banner
import android.R.attr.bitmap
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.listener.OnPageChangeListener
import com.yun.utils.image.ImageUtils
import com.yun.utils.image.QRCodeUtil
import com.yun.utils.image.SizeUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    var imgs = ArrayList<ImageBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createImage();
        imgs.add(ImageBean(123456, bitmap_1!!))
        imgs.add(ImageBean(123456, bitmap_2!!))
        imgs.add(ImageBean(123456, bitmap_3!!))
        initBanner()

    }


    fun initBanner() {
        //默认第一个等
        mBitmap = bitmap_1
        banners.addBannerLifecycleObserver(this)//添加生命周期观察者
            .setAdapter(ImageAdapter(imgs))
            .setIndicator(CircleIndicator(this))
//            .setBannerGalleryEffect(50,10) //画廊效果
            .setBannerGalleryMZ(50) //魅族效果
            .isAutoLoop(false)//是否允许自动播放
            .addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                //选中时，等于临时的bitmap
                override fun onPageSelected(position: Int) {
                    Toast.makeText(
                        this@MainActivity, "position:${bitmap_1.toString()
                        }", Toast.LENGTH_SHORT
                    ).show()

                    mBitmap = when (position) {
                        0 -> bitmap_1
                        1 -> bitmap_2
                        2 -> bitmap_3
                        else ->
                            bitmap_1
                    }
                }

            })
        save_btn.setOnClickListener {
            var file = ImageUtils.saveImageToFile(
                this@MainActivity,
                mBitmap!!,
                "invite_face.jpg"
            )!!
            Toast.makeText(this@MainActivity, "file:${file.path}", Toast.LENGTH_SHORT).show()


        }
    }

    private var mBitmap: Bitmap? = null
    private var bitmap_1: Bitmap? = null
    private var bitmap_2: Bitmap? = null
    private var bitmap_3: Bitmap? = null
    private var mUrl: String = "https://www.baidu.com/" //二维码对应的网址。

    /**
     * 创建图片
     */
    fun createImage() {
        //二维码相关设置
        //第一种二维码
        val logoBm = QRCodeUtil.createQRCodeBitmap(
            mUrl,
            SizeUtils.dip2px(this, 100f).toInt(),
            SizeUtils.dip2px(this, 100f).toInt()
        )
        //第一张图片 加上二维码
        val bm1 = (resources.getDrawable(R.mipmap.p1) as BitmapDrawable).bitmap
        bitmap_1 =
                //
            mergeBitmap(bm1, logoBm, SizeUtils.dip2px(this, 20f), SizeUtils.dip2px(this, 175f), 0)
        //第二张图片 加上二维码

        val bm2 = (resources.getDrawable(R.mipmap.p2) as BitmapDrawable).bitmap
        bitmap_2 =
            mergeBitmap(bm2, logoBm, SizeUtils.dip2px(this, 20f), SizeUtils.dip2px(this, 175f), 0)
        //第三张图片 加上二维码
        val bm3 = (resources.getDrawable(R.mipmap.p3) as BitmapDrawable).bitmap
        bitmap_3 =
            mergeBitmap(bm3, logoBm, SizeUtils.dip2px(this, 20f), SizeUtils.dip2px(this, 175f), 0)


    }

    //合成图片
    fun mergeBitmap(
        firstBitmap: Bitmap?,
        secondBitmap: Bitmap?,
        width: Float,
        height: Float,
        type: Int

    ): Bitmap? {
        if (firstBitmap == null || secondBitmap == null) return null
        val bitmap = Bitmap.createBitmap(
            firstBitmap.width, firstBitmap.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        canvas.drawBitmap(firstBitmap, 0f, 0f, null)
        //NULL

        when (type) {
            0, 1 -> {
                // 画笔。
                val paint = Paint()
                paint.color = Color.WHITE
                paint.style = Paint.Style.FILL
                paint.strokeWidth = 12f
                paint.textSize = SizeUtils.dip2px(this, 20f)
                val msg = "邀请码ID:123456"
                //绘制到图片上。在图片上位置
                canvas.drawText(
                    msg,
                    SizeUtils.dip2px(this, 20f),
                    SizeUtils.dip2px(this, 165f),
                    paint
                )
            }
            2 -> {
                val paint = Paint()
                paint.color = Color.WHITE
                paint.style = Paint.Style.FILL
                paint.strokeWidth = 12f
                paint.textSize = SizeUtils.dip2px(this, 20f)
                val msg = "邀请码ID:123456"
                canvas.drawText(
                    msg,
                    SizeUtils.dip2px(this, 30f),
                    SizeUtils.dip2px(this, 20f),
                    paint
                )
            }

        }


        canvas.save()
        canvas.drawBitmap(secondBitmap, width, height, null)
        canvas.restore()
        return bitmap
    }

}


