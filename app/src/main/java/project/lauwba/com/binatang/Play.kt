package project.lauwba.com.binatang

import android.app.Activity
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView

@Suppress("DEPRECATION")
class Play : AppCompatActivity() {

    private lateinit var pager: ViewPager
    private var nama = arrayOf("Singa", "Monyet", "Gajah")
    private var gambar = intArrayOf(R.drawable.singa, R.drawable.monyet, R.drawable.gajah)
    private var suara = intArrayOf(R.raw.singa, R.raw.monyet, R.raw.gajah)
    private lateinit var adapter: PagerAdapter
    private var mp: MediaPlayer? = null

    var onPage: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {
            if (mp != null) {
                mp?.reset()
                mp?.release()
            }
            mp = MediaPlayer.create(this@Play, suara[position])
            mp?.start()
        }

        override fun onPageScrollStateChanged(state: Int) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_play)

        pager = findViewById(R.id.viewpager)

        adapter = GambarAdapter(this, gambar, nama)
        pager.adapter = adapter
        pager.setOnPageChangeListener(onPage)

    }

    private inner class GambarAdapter(play: Play, internal var gambar: IntArray, internal var nama: Array<String>) : PagerAdapter() {

        var inflater: LayoutInflater? = null
        internal var activity: Activity = play

        override fun getCount(): Int {
            return gambar.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object` as ScrollView
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            inflater = activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val tampil = inflater?.inflate(R.layout.item_pager, container, false)

            val img = tampil?.findViewById(R.id.imgbinatang) as ImageView
            val text = tampil.findViewById(R.id.textbinatang) as TextView

            img.setImageResource(gambar[position])
            text.text = nama[position]

            (container as ViewPager).addView(tampil)

            return tampil
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            (container as ViewPager).removeView(`object` as ScrollView)
        }
    }
}
