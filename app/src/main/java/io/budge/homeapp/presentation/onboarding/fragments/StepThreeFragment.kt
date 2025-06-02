package io.budge.homeapp.presentation.onboarding.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import io.budge.homeapp.databinding.FragmentStepThreeBinding
import io.budge.homeapp.core.Logger
import io.budge.homeapp.presentation.onboarding.viewmodel.OnboardingViewModel

class StepThreeFragment : Fragment() {

    private var _binding: FragmentStepThreeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OnboardingViewModel by viewModels({ requireActivity() })
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        Logger.v(TAG, "onCreateView")
        _binding = FragmentStepThreeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Logger.v(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        binding.buttonContinue.setOnClickListener {
            finishOnboarding()
        }
    }

    private fun finishOnboarding() {
        Logger.v(TAG, "Continue clicked, finishing activity")
        viewModel.markComplete()
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "StepThreeFragment"
    }
}