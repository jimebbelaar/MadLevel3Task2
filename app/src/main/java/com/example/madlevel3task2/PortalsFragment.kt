package com.example.madlevel3task2

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import android.widget.Button
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_portal.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PortalsFragment : Fragment() {

    private val portals = arrayListOf<Portal>()
    private val portalAdapter = PortalAdapter(portals) { portal: Portal -> onPortalClick(portal) }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_portal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAddPortalResult()
        initViews()
        }
    private fun initViews() {
        // Initialize the recycler view with a linear layout manager, adapter
        rvPortals.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvPortals.adapter = portalAdapter
        rvPortals.addItemDecoration(
            DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL)
        )
        observeAddPortalResult()
    }
    private fun onPortalClick(portal: Portal) {
        CustomTabsIntent.Builder().build().launchUrl(this.context, Uri.parse(portal.portalLink))
    }
    private fun observeAddPortalResult() {
        setFragmentResultListener(REQ_PORTAL_KEY) { key, bundle ->
            bundle.getString(BUNDLE_PORTAL_KEY)?.let {
                val title = bundle.getString("title")
                val url = bundle.getString("url")
                val portal = Portal(title.toString(), url.toString())
                portals.add(portal)
                portalAdapter.notifyDataSetChanged()
            } ?: Log.e("ReminderFragment", "Request triggered, but empty reminder text!")

        }
    }
}


