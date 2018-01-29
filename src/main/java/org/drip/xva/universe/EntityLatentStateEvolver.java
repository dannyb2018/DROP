
package org.drip.xva.universe;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   	you may not use this file except in compliance with the License.
 *   
 *  You may obtain a copy of the License at
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  	distributed under the License is distributed on an "AS IS" BASIS,
 *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  See the License for the specific language governing permissions and
 *  	limitations under the License.
 */

/**
 * EntityLatentStateEvolver contains the Bank and the Counter Party Credit and Funding Latent State Evolvers.
 *  The References are:<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2013): Funding Strategies, Funding Costs <i>Risk</i> <b>24 (12)</b>
 *  	82-87.<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19.<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75.<br><br>
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  	86-90.<br><br>
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  	<i>Risk</i> <b>21 (2)</b> 97-102.<br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EntityLatentStateEvolver
{
	private org.drip.measure.process.DiffusionEvolver _bankHazardRateEvolver = null;
	private org.drip.measure.process.DiffusionEvolver _counterPartyHazardRateEvolver = null;
	private org.drip.measure.process.DiffusionEvolver _bankSeniorRecoveryRateEvolver = null;
	private org.drip.measure.process.DiffusionEvolver _counterPartyRecoveryRateEvolver = null;
	private org.drip.measure.process.DiffusionEvolver _bankSubordinateRecoveryRateEvolver = null;

	/**
	 * EntityLatentStateEvolver Constructor
	 * 
	 * @param bankHazardRateEvolver The Bank Hazard Rate Diffusive Evolver
	 * @param bankSeniorRecoveryRateEvolver The Bank Senior Recovery Rate Diffusive Evolver
	 * @param bankSubordinateRecoveryRateEvolver The Bank Subordinate Rate Diffusive Evolver
	 * @param counterPartyHazardRateEvolver The Counter Party Hazard Rate Diffusive Evolver
	 * @param counterPartyRecoveryRateEvolver The Counter Party Recovery Rate Diffusive Evolver
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EntityLatentStateEvolver (
		final org.drip.measure.process.DiffusionEvolver bankHazardRateEvolver,
		final org.drip.measure.process.DiffusionEvolver bankSeniorRecoveryRateEvolver,
		final org.drip.measure.process.DiffusionEvolver bankSubordinateRecoveryRateEvolver,
		final org.drip.measure.process.DiffusionEvolver counterPartyHazardRateEvolver,
		final org.drip.measure.process.DiffusionEvolver counterPartyRecoveryRateEvolver)
		throws java.lang.Exception
	{
		if (null == (_bankHazardRateEvolver = bankHazardRateEvolver) ||
			null == (_bankSeniorRecoveryRateEvolver = bankSeniorRecoveryRateEvolver) ||
			null == (_counterPartyHazardRateEvolver = counterPartyHazardRateEvolver) ||
			null == (_counterPartyRecoveryRateEvolver = counterPartyRecoveryRateEvolver))
		{
			throw new java.lang.Exception ("EntityLatentStateEvolver Constructor => Invalid Inputs");
		}

		_bankSubordinateRecoveryRateEvolver = bankSubordinateRecoveryRateEvolver;
	}

	/**
	 * Retrieve the Bank Hazard Rate Evolver
	 * 
	 * @return The Bank Hazard Rate Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver bankHazardRateEvolver()
	{
		return _bankHazardRateEvolver;
	}

	/**
	 * Retrieve the Bank Senior Recovery Rate Evolver
	 * 
	 * @return The Bank Senior Recovery Rate Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver bankSeniorRecoveryRateEvolver()
	{
		return _bankSeniorRecoveryRateEvolver;
	}

	/**
	 * Retrieve the Bank Subordinate Recovery Rate Evolver
	 * 
	 * @return The Bank Subordinate Recovery Rate Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver bankSubordinateRecoveryRateEvolver()
	{
		return _bankSubordinateRecoveryRateEvolver;
	}

	/**
	 * Retrieve the Counter Party Hazard Rate Evolver
	 * 
	 * @return The Counter Party Hazard Rate Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver counterPartyHazardRateEvolver()
	{
		return _counterPartyHazardRateEvolver;
	}

	/**
	 * Retrieve the Counter Party Recovery Rate Evolver
	 * 
	 * @return The Counter Party Recovery Rate Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver counterPartyRecoveryRateEvolver()
	{
		return _counterPartyRecoveryRateEvolver;
	}

}
