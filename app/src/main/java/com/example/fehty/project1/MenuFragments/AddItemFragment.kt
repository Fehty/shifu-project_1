package com.example.fehty.project1.MenuFragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.fehty.project1.Activity.MainActivity
import com.example.fehty.project1.Poco.Poco
import com.example.fehty.project1.R
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_item.*

@SuppressLint("ValidFragment")
class AddItemFragment(private var mainActivity: MainActivity?) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_add_item, container, false)
    }

    private val listFragment = ListFragment()
    private val realm = Realm.getDefaultInstance()
   // private var nextInt = 0
    private val poco = Poco()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonAddItem.setOnClickListener {
            if (addItemText.text.isNotEmpty()) {
                realm.executeTransaction {
//                    val resultOfId = realm.where(Poco::class.java).max("id")
//                    when (resultOfId) {
//                        null -> nextInt = 1
//                        else -> nextInt = resultOfId.toInt() + 1
//                    }
                    poco.itemOfTheList = addItemText.text.toString()
                 //   poco.id = nextInt
                    realm.insertOrUpdate(poco)
                }
//                val bundle = Bundle()
//                bundle.putString("newItem", addItemText.text.toString())
//                listFragment.arguments = bundle
            }
            exitFromAdditionItems()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            exitFromAdditionItems()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun exitFromAdditionItems() {
        activity!!.floatingActionButton.show()
        mainActivity!!.supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        fragmentManager
                ?.beginTransaction()
                ?.addToBackStack(null)
                ?.replace(R.id.container, listFragment)
                ?.commit()
    }

    override fun onPause() {
        super.onPause()
        addItemText.clearFocus()
    }
}
