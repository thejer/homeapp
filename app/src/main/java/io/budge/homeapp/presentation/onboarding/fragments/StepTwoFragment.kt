package io.budge.homeapp.presentation.onboarding.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import io.budge.homeapp.databinding.FragmentStepTwoBinding
import io.budge.homeapp.core.Logger
import io.budge.homeapp.presentation.onboarding.viewmodel.OnboardingViewModel
import io.budge.homeapp.presentation.onboarding.ui.OnboardingActivity

class StepTwoFragment : Fragment() {
    private var _binding: FragmentStepTwoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OnboardingViewModel by viewModels({ requireActivity() })

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
        viewModel.resetLaunchedFromStepTwo()
        binding.buttonContinue.setOnClickListener {
            Logger.v(TAG, "Continue button clicked")
            launchHomeSettings()
        }
    }

    override fun onResume() {
        super.onResume()
        Logger.v(TAG, "onResume")
        viewModel.resetLaunchedFromStepTwo()
        if (viewModel.isAppDefaultLauncher()) {
            Logger.v(TAG, "App is default launcher, go to step 3")
            (activity as? OnboardingActivity)?.goToNextStep()
        } else {
            Logger.v(TAG, "App is NOT default launcher")
        }
    }

    private fun launchHomeSettings() {
        viewModel.setLaunchedFromStepTwo()
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