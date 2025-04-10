/*
 * Copyright (C) 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.brunofelix.fitactions.tasks

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.google.shortcuts.builders.CapabilityBuilder
import androidx.core.graphics.drawable.IconCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import dev.brunofelix.fitactions.BuildConfig
import dev.brunofelix.fitactions.R
import java.util.Arrays

/**
 * Main activity for the todoapp. Holds the Navigation Host Fragment and the Drawer, Toolbar, etc.
 */
class TasksActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tasks_act)
        setupNavigationDrawer()
        setSupportActionBar(findViewById(R.id.toolbar))

        val navController: NavController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration =
            AppBarConfiguration.Builder(R.id.tasks_fragment_dest, R.id.statistics_fragment_dest)
                .setOpenableLayout(drawerLayout)
                .build()
        setupActionBarWithNavController(navController, appBarConfiguration)
        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)
    }

    private fun createShortcut() {
        val shortcuts = ShortcutManagerCompat.getDynamicShortcuts(this)
        val isShortcutExists = shortcuts.any { it.id == "get_torus_score_shortcut" }
        if (!isShortcutExists) {
            val intent = Intent(this, TasksActivity::class.java).apply {
                action = Intent.ACTION_VIEW
                putExtra("q", "")
            }
            val shortcut = ShortcutInfoCompat.Builder(this, "get_torus_score")
                .setShortLabel("Get torus score")
                .setLongLabel("Show my torus score")
                .setIcon(IconCompat.createWithResource(this, R.drawable.ic_list))
                .setIntent(intent)
                .addCapabilityBinding(
                    "actions.intent.GET_THING",
                    "thing.name",
                    listOf("q")
                )
                .build()
            ShortcutManagerCompat.pushDynamicShortcut(this, shortcut)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration) ||
            super.onSupportNavigateUp()
    }

    private fun setupNavigationDrawer() {
        drawerLayout = (findViewById<DrawerLayout>(R.id.drawer_layout))
            .apply {
                setStatusBarBackground(R.color.colorPrimaryDark)
            }
        val currentVersion = "v${BuildConfig.VERSION_NAME} build ${BuildConfig.VERSION_CODE}"
        val nav = drawerLayout.findViewById<NavigationView>(R.id.nav_view)
        nav.getHeaderView(0).findViewById<TextView>(R.id.nav_build_number).text = currentVersion
    }
}

// Keys for navigation
const val ADD_EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 1
const val DELETE_RESULT_OK = Activity.RESULT_FIRST_USER + 2
const val EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 3
