package moviedleapp.main.helpers

import android.graphics.drawable.Drawable
import java.io.InputStream
import java.net.URL

fun getDrawableByUrl(url: String): Drawable = Drawable.createFromStream(URL(url).content as InputStream, "srcName")
