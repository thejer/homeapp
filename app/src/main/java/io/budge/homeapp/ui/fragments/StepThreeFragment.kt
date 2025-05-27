package io.budge.homeapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.budge.homeapp.databinding.FragmentStepThreeBinding
import io.budge.homeapp.ui.OnboardingViewModel
import io.budge.homeapp.util.Logger

class StepThreeFragment : Fragment() {

    private var _binding: FragmentStepThreeBinding? = null
    private val binding get() = _binding!!

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
        val viewModel = ViewModelProvider(requireActivity())[OnboardingViewModel::class.java]
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