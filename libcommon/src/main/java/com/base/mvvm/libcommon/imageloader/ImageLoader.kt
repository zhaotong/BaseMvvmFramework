package com.base.mvvm.libcommon.imageloader

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.IntDef
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.ImageViewTarget
import java.io.File
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by tone on 2020/3/13
 */
object ImageLoader {

    fun with(context: Activity): Builder {
        return Builder(context)
    }

    fun with(context: FragmentActivity): Builder {
        return Builder(context)
    }

    fun with(context: Fragment): Builder {
        return Builder(context)
    }

    fun with(context: View): Builder {
        return Builder(context)
    }

    fun with(context: Context): Builder {
        return Builder(context)
    }


    class Builder(private val context: Any) {

        companion object {
            const val TYPE_DRAWABLE = 0
            const val TYPE_GIF = 1
            const val TYPE_BITMAP = 2
            const val TYPE_BLUR = 3
            const val TYPE_FILE = 4


            const val DISK_CACHE_NONE = 1
            const val DISK_CACHE_ALL = 1 shl 1
            const val DISK_CACHE_RESOURCE = 1 shl 2

            @IntDef(value = [DISK_CACHE_NONE, DISK_CACHE_ALL, DISK_CACHE_RESOURCE])
            @Retention(RetentionPolicy.SOURCE)
            annotation class DiskCache
        }

        private var options = RequestOptions()

        private var resLoadType = TYPE_DRAWABLE

        private var res: Any? = null

        private val transformations = mutableListOf<Transformation<Bitmap>>()

        open fun error(res: Any) = apply {
            if (res is Int)
                options = options.error(res)
            else if (res is Drawable)
                options = options.error(res)
        }

        open fun placeholder(res: Any) = apply {
            if (res is Int)
                options = options.placeholder(res)
            else if (res is Drawable)
                options = options.placeholder(res)
        }


        open fun round(round: Int = 0, fitXY: Boolean = false) = apply {
            if (round > 0) {
                if (fitXY) {
                    transformations.add(RoundedCorners(round))
                } else {
                    transformations.add(CenterCrop())
                    transformations.add(RoundedCorners(round))
                }
                options = options.transform(*transformations.toTypedArray())
            }
        }

        open fun circle() = apply {
            transformations.add(CircleCrop())
            options = options.transform(*transformations.toTypedArray())
        }

        open fun diskCache(@DiskCache diskCache: Int) = apply {
            when (diskCache) {
                DISK_CACHE_NONE -> options.diskCacheStrategy(DiskCacheStrategy.NONE)
                DISK_CACHE_ALL -> options.diskCacheStrategy(DiskCacheStrategy.ALL)
                DISK_CACHE_RESOURCE -> options.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            }
        }

        open fun size(width: Int, height: Int) {
            options = options.override(width, height)
        }

        open fun asBitmap() = apply {
            resLoadType = TYPE_BITMAP
        }

        open fun asFile() = apply {
            resLoadType = TYPE_FILE
        }

        open fun asGif() = apply {
            resLoadType = TYPE_GIF
        }

        open fun asBlur() = apply {
            resLoadType = TYPE_BLUR

            transformations.add(BlurTransformation())
            options = options.transform(*transformations.toTypedArray())
        }

        open fun asDrawable() = apply {
            resLoadType = TYPE_DRAWABLE
        }

        open fun load(res: Any) = apply {
            this.res = res
        }

        open fun getBitmapResource(): Bitmap {
            val requestManager = getRequestManager()
            if (res == null) {
                throw NullPointerException(
                        "null of res... url"
                )
            }
            return requestManager.asBitmap().load(res).apply(options).submit().get()
        }

        open fun getDrawableResource(): Drawable {
            val requestManager = getRequestManager()
            if (res == null) {
                throw NullPointerException(
                        "null of res"
                )
            }
            return requestManager.asDrawable().load(res).apply(options).submit().get()
        }

        open fun getFileResource(): File {
            val requestManager = getRequestManager()
            if (res == null) {
                throw NullPointerException(
                        "null of res"
                )
            }
            return requestManager.asFile().load(res).apply(options).submit().get()
        }

        private fun getRequestManager(): RequestManager =
                when (context) {
                    is Activity -> Glide.with(context)
                    is FragmentActivity -> Glide.with(context)
                    is Fragment -> Glide.with(context)
                    is View -> Glide.with(context)
                    else -> Glide.with(context as Context)
                }


        open fun into(imageView: ImageView) {
            val requestManager = getRequestManager()

            if (res == null) {
                throw NullPointerException(
                        "null of res"
                )
            }

            when (resLoadType) {
                TYPE_BITMAP -> {
                    requestManager.asBitmap().load(res).apply(options)
                            .into(BitmapImageViewTarget(imageView))
                }
                TYPE_BLUR -> requestManager.asBitmap().load(res).apply(options).into(
                        BitmapImageViewTarget(imageView)
                )
                TYPE_GIF -> requestManager.asGif().load(res).apply(options).into(
                        GifDrawableImageViewTarget(imageView)
                )
                TYPE_DRAWABLE -> requestManager.asDrawable().load(res).apply(options).into(
                        DrawableImageViewTarget(imageView)
                )
                else -> requestManager.asDrawable().load(res).apply(options).into(
                        DrawableImageViewTarget(imageView)
                )
            }
        }

    }

    class DrawableImageViewTarget(view: ImageView) :
            ImageViewTarget<Drawable>(view) {
        override fun setResource(resource: Drawable?) {
            if (resource != null) {
                if (resource is GifDrawable) {
                    resource.setLoopCount(GifDrawable.LOOP_FOREVER)
                    view.setImageDrawable(resource)
                } else {
                    view.setImageDrawable(resource)
                }
            }
        }
    }

    class GifDrawableImageViewTarget(view: ImageView) :
            ImageViewTarget<GifDrawable>(view) {
        override fun setResource(resource: GifDrawable?) {
            if (resource != null) {
                if (resource is GifDrawable) {
                    resource.setLoopCount(GifDrawable.LOOP_INTRINSIC)
                    view.setImageDrawable(resource)
                } else {
                    view.setImageDrawable(resource)
                }
            }
        }
    }
}