package com.cwj.imagesynthesis

import android.graphics.Bitmap

/**
 *  author : ChenWenJie
 *  email  : 1181620038@qq.com
 *  date   : 2020/9/25
 *  desc   :
 */
class ImageBean {
    var id :Int=0
    var image :Bitmap

    constructor(id: Int, image: Bitmap) {
        this.id = id
        this.image = image
    }
}