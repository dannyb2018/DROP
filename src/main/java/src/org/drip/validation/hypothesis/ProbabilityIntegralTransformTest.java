
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
 * <i>ProbabilityIntegralTransformTest</i> implements Comparison Tests post a PIT Transform on the Hypothesis
 * and/or Test Sample.
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
 *  		Wetzels, R., D. Matzke, M. D. Lee, J. N. Rouder, G, J, Iverson, and E. J. Wagenmakers (2011):
 *  		Statistical Evidence in Experimental Psychology: An Empirical Comparison using 855 t-Tests
 *  		Perspectives in Psychological Science 6 (3) 291-298
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

public class ProbabilityIntegralTransformTest
{
	private org.drip.validation.hypothesis.ProbabilityIntegralTransform _probabilityIntegralTransform = null;

	/**
	 * ProbabilityIntegralTransformTest Constructor
	 * 
	 * @param probabilityIntegralTransform The Probability Integral Transform Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ProbabilityIntegralTransformTest (
		final org.drip.validation.hypothesis.ProbabilityIntegralTransform probabilityIntegralTransform)
		throws java.lang.Exception
	{
		if (null == (_probabilityIntegralTransform = probabilityIntegralTransform))
		{
			throw new java.lang.Exception ("ProbabilityIntegralTransformTest Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the ProbabilityIntegralTransform Instance
	 * 
	 * @return The ProbabilityIntegralTransform Instance
	 */

	public org.drip.validation.hypothesis.ProbabilityIntegralTransform probabilityIntegralTransform()
	{
		return _probabilityIntegralTransform;
	}

	/**
	 * Run the Significance Test for the Realized Test Statistic
	 * 
	 * @param testStatistic The Realized Test Statistic
	 * @param pTestSetting The P-Test Setting
	 * 
	 * @return The Significance Test Result for the Realized Test Statistic
	 */

	public org.drip.validation.hypothesis.SignificanceTestOutcome significanceTest (
		final double testStatistic,
		final org.drip.validation.hypothesis.SignificanceTestSetting pTestSetting)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (testStatistic) || null == pTestSetting)
		{
			return null;
		}

		double pValue = java.lang.Double.NaN;

		try
		{
			pValue = _probabilityIntegralTransform.pValue (testStatistic);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		int tailCheck = pTestSetting.tailCheck();

		double threshold = pTestSetting.threshold();

		if (org.drip.validation.hypothesis.SignificanceTestSetting.LEFT_TAIL_CHECK == tailCheck)
		{
			try
			{
				return new SignificanceTestOutcome (
					testStatistic,
					1. - pValue,
					pValue,
					pValue > threshold
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		if (org.drip.validation.hypothesis.SignificanceTestSetting.RIGHT_TAIL_CHECK == tailCheck)
		{
			try
			{
				return new SignificanceTestOutcome (
					testStatistic,
					1. - pValue,
					pValue,
					1. - pValue > threshold
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		try
		{
			return new SignificanceTestOutcome (
				testStatistic,
				1. - pValue,
				pValue,
				2. * java.lang.Math.min (
					pValue,
					1. - pValue
				) > threshold
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Run a Distance Gap Test between the Hypothesis and the Sample
	 * 
	 * @param samplePIT The Sample Probability Integral Transform
	 * @param gapTestSetting The Distance Gap Test Setting
	 * 
	 * @return The Distance Gap Test Outcome
	 */

	public org.drip.validation.distance.GapTestOutcome distanceTest (
		final org.drip.validation.hypothesis.ProbabilityIntegralTransform samplePIT,
		final org.drip.validation.distance.GapTestSetting gapTestSetting)
	{
		if (null == samplePIT || null == gapTestSetting)
		{
			return null;
		}

		double distance = 0.;
		double hypothesisPValueLeft = 0.;

		org.drip.validation.distance.GapLossFunction gapLossFunction = gapTestSetting.lossFunction();

		org.drip.validation.distance.GapLossWeightFunction gapLossWeightFunction =
			gapTestSetting.lossWeightFunction();

		org.drip.validation.evidence.TestStatisticAccumulator weightedGapLossAccumulator = new
			org.drip.validation.evidence.TestStatisticAccumulator();

		org.drip.validation.evidence.TestStatisticAccumulator unweightedGapLossAccumulator = new
			org.drip.validation.evidence.TestStatisticAccumulator();

		for (java.util.Map.Entry<java.lang.Double, java.lang.Double> testStatisticPValueEntry :
			samplePIT.testStatisticPValueMap().entrySet())
		{
			try
			{
				double hypothesisPValueRight = _probabilityIntegralTransform.pValue
					(testStatisticPValueEntry.getKey());

				double gapLoss = gapLossFunction.loss (testStatisticPValueEntry.getValue() -
					hypothesisPValueRight);

				double weightedGapLoss = gapLoss * gapLossWeightFunction.weight (hypothesisPValueRight);

				distance = distance + weightedGapLoss * (hypothesisPValueRight - hypothesisPValueLeft);

				if (!unweightedGapLossAccumulator.addTestStatistic (gapLoss) ||
					!weightedGapLossAccumulator.addTestStatistic (weightedGapLoss))
				{
					return null;
				}

				hypothesisPValueLeft = hypothesisPValueRight;
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		try
		{
			return new org.drip.validation.distance.GapTestOutcome (
				unweightedGapLossAccumulator.probabilityIntegralTransform(),
				weightedGapLossAccumulator.probabilityIntegralTransform(),
				distance
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Run a Histogram Test Corresponding to the Test Statistic and its p-Value
	 * 
	 * @param histogramTestSetting The Histogram Test Setting
	 * 
	 * @return The Histogram Test Corresponding to the Test Statistic and its p-Value
	 */

	public org.drip.validation.hypothesis.HistogramTestOutcome histogramTest (
		final org.drip.validation.hypothesis.HistogramTestSetting histogramTestSetting)
	{
		if (null == histogramTestSetting)
		{
			return null;
		}

		org.drip.validation.quantile.PlottingPositionGenerator plottingPositionGenerator =
			histogramTestSetting.plottingPositionGenerator();

		org.drip.validation.quantile.PlottingPosition[] plottingPositionArray =
			plottingPositionGenerator.generate();

		int qqVertexCount = plottingPositionArray.length + 2;
		double[] testStatisticArray = new double[qqVertexCount];
		double[] pValueCumulativeArray = new double[qqVertexCount];
		double[] pValueIncrementalArray = new double[qqVertexCount];

		try
		{
			pValueIncrementalArray[0] = 0.;

			testStatisticArray[0] = _probabilityIntegralTransform.testStatistic
				(pValueCumulativeArray[0] = 0.);

			testStatisticArray[qqVertexCount - 1] = _probabilityIntegralTransform.testStatistic
				(pValueCumulativeArray[qqVertexCount - 1] = 1.);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (int qqVertexIndex = 1; qqVertexIndex < qqVertexCount - 1; ++qqVertexIndex)
		{
			try
			{
				testStatisticArray[qqVertexIndex] = _probabilityIntegralTransform.testStatistic
					(pValueCumulativeArray[qqVertexIndex] =
						plottingPositionArray[qqVertexIndex - 1].quantile());

				pValueIncrementalArray[qqVertexIndex] = pValueCumulativeArray[qqVertexIndex] -
					pValueCumulativeArray[qqVertexIndex - 1];
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		pValueIncrementalArray[qqVertexCount - 1] = pValueCumulativeArray[qqVertexCount - 1] -
			pValueCumulativeArray[qqVertexCount - 2];

		try
		{
			return new org.drip.validation.hypothesis.HistogramTestOutcome (
				testStatisticArray,
				pValueCumulativeArray,
				pValueIncrementalArray,
				_probabilityIntegralTransform.testStatistic (histogramTestSetting.pValueThreshold())
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Run the Quantile-Quantile Test
	 * 
	 * @param samplePIT The Sample Probability Integral Transform
	 * @param plottingPositionGenerator The Quantile-Quantile Test Plotting Position Generator
	 * 
	 * @return The Quantile-Quantile Test Outcome
	 */

	public org.drip.validation.quantile.QQTestOutcome qqTest (
		final org.drip.validation.hypothesis.ProbabilityIntegralTransform samplePIT,
		final org.drip.validation.quantile.PlottingPositionGenerator plottingPositionGenerator)
	{
		if (null == samplePIT || null == plottingPositionGenerator)
		{
			return null;
		}

		org.drip.validation.quantile.PlottingPosition[] plottingPositionArray =
			plottingPositionGenerator.generate();

		if (null == plottingPositionArray)
		{
			return null;
		}

		int orderStatisticCount = plottingPositionArray.length;
		org.drip.validation.quantile.QQVertex[] qqVertexArray = new
			org.drip.validation.quantile.QQVertex[orderStatisticCount];

		for (int orderStatisticIndex = 0; orderStatisticIndex < orderStatisticCount; ++orderStatisticIndex)
		{
			try
			{
				double pValue = plottingPositionArray[orderStatisticIndex].quantile();
		
				qqVertexArray[orderStatisticIndex] = new org.drip.validation.quantile.QQVertex (
					plottingPositionArray[orderStatisticIndex],
					samplePIT.testStatistic (pValue),
					_probabilityIntegralTransform.testStatistic (pValue)
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		try
		{
			return new org.drip.validation.quantile.QQTestOutcome (qqVertexArray);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
