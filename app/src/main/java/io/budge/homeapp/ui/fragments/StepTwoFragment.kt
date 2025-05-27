package io.budge.homeapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.budge.homeapp.databinding.FragmentStepTwoBinding
import io.budge.homeapp.ui.activities.OnboardingActivity
import io.budge.homeapp.util.LauncherUtils
import io.budge.homeapp.util.Logger
import io.budge.homeapp.util.Prefs

class StepTwoFragment : Fragment() {
    private var _binding: FragmentStepTwoBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        Logger.v(TAG, "onCreateView")
        _binding = FragmentStepTwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Logger.v(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        binding.buttonContinue.setOnClickListener {
            Logger.v(TAG, "Continue button clicked")
            launchHomeSettings()
        }
    }

    override fun onResume() {
        super.onResume()
        Logger.v(TAG, "onResume")
        if (LauncherUtils.isAppDefaultLauncher(requireContext())) {
            Logger.v(TAG, "App is default launcher, go to step 3")
            (activity as? OnboardingActivity)?.goToNextStep()
        } else {
            Logger.v(TAG, "App is NOT default launcher")
        }
    }

    private fun launchHomeSettings() {
        Prefs.setLaunchedFromStepTwo(requireContext(), true)
        val intent = Intent(Settings.ACTION_HOME_SETTINGS)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "StepTwoFragment"
    }
}