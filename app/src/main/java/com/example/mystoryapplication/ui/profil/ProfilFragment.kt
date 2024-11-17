package com.example.mystoryapplication.ui.profil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mystoryapplication.databinding.FragmentProfilBinding
import com.example.mystoryapplication.pref.UserPreferences
import com.example.mystoryapplication.ui.welcome.WelcomeActivity

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        val root: View = binding.root

        userPreferences = UserPreferences(requireContext())

        val name = userPreferences.getSession().name
        binding.profileName.text = name

        binding.logoutButton.setOnClickListener {
            userPreferences.logout()

            val intent = Intent(requireContext(), WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            activity?.finish()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}