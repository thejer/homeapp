package io.budge.homeapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.budge.homeapp.databinding.FragmentStepOneBinding
import io.budge.homeapp.ui.OnboardingViewModel
import io.budge.homeapp.ui.activities.OnboardingActivity
import io.budge.homeapp.util.Logger

class StepOneFragment : Fragment() {
    private var _binding: FragmentStepOneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        Logger.v(TAG, "onCreateView")
        _binding = FragmentStepOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Logger.v(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity())[OnboardingViewModel::class.java]
        viewModel.currentStep = 1
        binding.buttonContinue.setOnClickListener {
            Logger.v(TAG, "Continue button clicked")
            (activity as? OnboardingActivity)?.goToNextStep()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "StepOneFragment"
    }
}