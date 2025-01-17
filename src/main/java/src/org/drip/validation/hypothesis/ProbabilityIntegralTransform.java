
package org.drip.validation.hypothesis;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 * <i>ProbabilityIntegralTransform</i> holds the PIT Distribution CDF of the Test-Statistic Response over the
 * Outcome Instances.
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Bhattacharya, B., and D. Habtzghi (2002): Median of the p-value under the Alternate Hypothesis
 *  			American Statistician 56 (3) 202-206
 *  	</li>
 *  	<li>
 *  		Head, M. L., L. Holman, R, Lanfear, A. T. Kahn, and M. D. Jennions (2015): The Extent and
 *  			Consequences of p-Hacking in Science PLoS Biology 13 (3) e1002106
 *  	</li>
 *  	<li>
 *  		Wasserstein, R. L., and N. A. Lazar (2016): The ASA�s Statement on p-values: Context, Process,
 *  			and Purpose American Statistician 70 (2) 129-133
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018): Probability Integral Transform
 *  			https://en.wikipedia.org/wiki/Probability_integral_transform
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): p-value https://en.wikipedia.org/wiki/P-value
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ModelValidationAnalyticsLibrary.md">Model Validation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/README.md">Risk Factor and Hypothesis Validation, Evidence Processing, and Model Testing</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/hypothesis/README.md">Statistical Hypothesis Validation Test Suite</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ProbabilityIntegralTransform
{
	private java.util.Map<java.lang.Double, java.lang.Double> _pValueTestStatisticMap = null;
	private java.util.Map<java.lang.Double, java.lang.Double> _testStatisticPValueMap = null;

	/**
	 * ProbabilityIntegralTransform Constructor
	 * 
	 * @param testStatisticPValueMap Test Statistic - p Value Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ProbabilityIntegralTransform (
		final java.util.Map<java.lang.Double, java.lang.Double> testStatisticPValueMap)
		throws java.lang.Exception
	{
		if (null == (_testStatisticPValueMap = testStatisticPValueMap) ||
			0 == _testStatisticPValueMap.size())
		{
			throw new java.lang.Exception ("ProbabilityIntegralTransform Constructor => Invalid Inputs");
		}

		_pValueTestStatisticMap = new java.util.TreeMap<java.lang.Double, java.lang.Double>();

		for (java.util.Map.Entry<java.lang.Double, java.lang.Double> testStatisticPValueMapEntry :
			_testStatisticPValueMap.entrySet())
		{
			_pValueTestStatisticMap.put (
				testStatisticPValueMapEntry.getValue(),
				testStatisticPValueMapEntry.getKey()
			);
		}
	}

	/**
	 * Retrieve the p Value - Test Statistic Map
	 * 
	 * @return The p Value - Test Statistic Map
	 */

	public java.util.Map<java.lang.Double, java.lang.Double> pValueTestStatisticMap()
	{
		return _pValueTestStatisticMap;
	}

	/**
	 * Retrieve the Test Statistic - p Value Map
	 * 
	 * @return The Test Statistic - p Value Map
	 */

	public java.util.Map<java.lang.Double, java.lang.Double> testStatisticPValueMap()
	{
		return _testStatisticPValueMap;
	}

	/**
	 * Compute the p-Value corresponding to the Test Statistic Instance
	 * 
	 * @param testStatistic The Test Statistic Instance
	 * 
	 * @return The p-Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double pValue (
		final double testStatistic)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (testStatistic))
		{
			throw new java.lang.Exception ("ProbabilityIntegralTransform::pValue => Invalid Inputs");
		}

		java.util.Set<java.lang.Double> testStatisticKeySet = _testStatisticPValueMap.keySet();

		double testStatisticKeyCurrent = java.lang.Double.NaN;
		double testStatisticKeyPrevious = java.lang.Double.NaN;

		for (double testStatisticKey : testStatisticKeySet)
		{
			if (testStatistic == testStatisticKey)
			{
				return _testStatisticPValueMap.get (testStatistic);
			}

			if (testStatistic < testStatisticKey)
			{
				if (!org.drip.numerical.common.NumberUtil.IsValid (testStatisticKeyPrevious))
				{
					return 0.;
				}

				testStatisticKeyCurrent = testStatisticKey;
				break;
			}

			testStatisticKeyPrevious = testStatisticKey;
		}

		return !org.drip.numerical.common.NumberUtil.IsValid (testStatisticKeyCurrent) ||
			testStatistic >= testStatisticKeyCurrent ? 1. :
			((testStatistic - testStatisticKeyPrevious) * _testStatisticPValueMap.get
				(testStatisticKeyCurrent) +
			(testStatisticKeyCurrent - testStatistic) * _testStatisticPValueMap.get
				(testStatisticKeyPrevious)) /
			(testStatisticKeyCurrent - testStatisticKeyPrevious);
	}

	/**
	 * Compute the Test Statistic Instance corresponding to the p-Value
	 * 
	 * @param pValue The p-Value
	 * 
	 * @return The Response Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double testStatistic (
		final double pValue)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (pValue))
		{
			throw new java.lang.Exception ("ProbabilityIntegralTransform::testStatistic => Invalid Inputs");
		}

		java.util.Set<java.lang.Double> pValueKeySet = _pValueTestStatisticMap.keySet();

		double pValueKeyCurrent = java.lang.Double.NaN;
		double pValueKeyPrevious = java.lang.Double.NaN;

		for (double pValueKey : pValueKeySet)
		{
			if (pValue == pValueKey)
			{
				return _pValueTestStatisticMap.get (pValue);
			}

			if (pValue < pValueKey)
			{
				if (!org.drip.numerical.common.NumberUtil.IsValid (pValueKeyPrevious))
				{
					return _pValueTestStatisticMap.get (pValueKey);
				}

				pValueKeyCurrent = pValueKey;
				break;
			}

			pValueKeyPrevious = pValueKey;
		}

		return pValue >= pValueKeyCurrent ? _pValueTestStatisticMap.get (pValueKeyCurrent) :
			((pValue - pValueKeyPrevious) * _pValueTestStatisticMap.get (pValueKeyCurrent) +
			(pValueKeyCurrent - pValue) * _pValueTestStatisticMap.get (pValueKeyPrevious)) /
			(pValueKeyCurrent - pValueKeyPrevious);
	}
}
