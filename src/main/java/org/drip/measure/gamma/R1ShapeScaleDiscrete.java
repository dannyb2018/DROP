
package org.drip.measure.gamma;

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
 * <i>R1ShapeScaleDiscrete</i> generates Discrete Variables that are Derivatives of the R<sup>1</sup> Gamma
 * 	Distribution. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Devroye, L. (1986): <i>Non-Uniform Random Variate Generation</i> <b>Springer-Verlag</b> New York
 * 		</li>
 * 		<li>
 * 			Gamma Distribution (2019): Gamma Distribution
 * 				https://en.wikipedia.org/wiki/Chi-squared_distribution
 * 		</li>
 * 		<li>
 * 			Louzada, F., P. L. Ramos, and E. Ramos (2019): A Note on Bias of Closed-Form Estimators for the
 * 				Gamma Distribution Derived From Likelihood Equations <i>The American Statistician</i> <b>73
 * 				(2)</b> 195-199
 * 		</li>
 * 		<li>
 * 			Minka, T. (2002): Estimating a Gamma distribution https://tminka.github.io/papers/minka-gamma.pdf
 * 		</li>
 * 		<li>
 * 			Ye, Z. S., and N. Chen (2017): Closed-Form Estimators for the Gamma Distribution Derived from
 * 				Likelihood Equations <i>The American Statistician</i> <b>71 (2)</b> 177-181
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/gamma/README.md">R<sup>1</sup> Gamma Distribution Implementation/Properties</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1ShapeScaleDiscrete
	extends org.drip.measure.gamma.R1ShapeScaleDistribution
{

	/**
	 * Generate Random Discrete from Inverse CDF
	 */

	public static final int DISCRETE_RANDOM_FROM_INVERSE_CDF = 1;

	/**
	 * Generate Random Discrete from Ahrens-Dieter (1982) Scheme
	 */

	public static final int DISCRETE_RANDOM_FROM_AHRENS_DIETER = 2;

	/**
	 * Generate Random Discrete from Marsaglia (1977) Scheme
	 */

	public static final int DISCRETE_RANDOM_FROM_MARSAGLIA = 3;

	private int _randomGenerationScheme = -1;

	/**
	 * R1ShapeScaleDiscrete Constructor
	 * 
	 * @param shapeParameter Shape Parameter
	 * @param scaleParameter Scale Parameter
	 * @param gammaEstimator Gamma Estimator
	 * @param digammaEstimator Digamma Estimator
	 * @param lowerIncompleteGammaEstimator Lower Incomplete Gamma Estimator
	 * @param randomGenerationScheme Scheme for the Random Gamma NUmber Generator
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R1ShapeScaleDiscrete (
		final double shapeParameter,
		final double scaleParameter,
		final org.drip.function.definition.R1ToR1 gammaEstimator,
		final org.drip.function.definition.R1ToR1 digammaEstimator,
		final org.drip.function.definition.R2ToR1 lowerIncompleteGammaEstimator,
		final int randomGenerationScheme)
		throws java.lang.Exception
	{
		super (
			new org.drip.measure.gamma.ShapeScaleParameters (
				shapeParameter,
				scaleParameter
			),
			gammaEstimator,
			digammaEstimator,
			lowerIncompleteGammaEstimator
		);

		_randomGenerationScheme = randomGenerationScheme;
	}

	/**
	 * Retrieve the Discrete Random Number Generator Scheme
	 * 
	 * @return The Discrete Random Number Generator Scheme
	 */

	public int randomGenerationScheme()
	{
		return _randomGenerationScheme;
	}

	@Override public double random()
		throws java.lang.Exception
	{
		if (DISCRETE_RANDOM_FROM_INVERSE_CDF == _randomGenerationScheme)
		{
			return super.random();
		}

		if (DISCRETE_RANDOM_FROM_AHRENS_DIETER == _randomGenerationScheme)
		{
			return randomAhrensDieter1982();
		}

		if (DISCRETE_RANDOM_FROM_MARSAGLIA == _randomGenerationScheme)
		{
			return randomMarsaglia1977();
		}

		throw new java.lang.Exception (
			"R1ShapeScaleDiscrete::random => Invalid Scheme"
		);
	}

	/**
	 * Generate Generalized Gamma Distributed Random Number
	 * 
	 * @param q The Power Parameter
	 * 
	 * @return The Generalized Gamma Distributed Random Number
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double randomGeneralizedGamma (
		final double q)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
				q
			) || 0. >= q
		)
		{
			throw new java.lang.Exception (
				"R1ShapeScaleDiscrete::randomGeneralizedGamma => Invalid Inputs"
			);
		}

		return java.lang.Math.pow (
			random(),
			q
		);
	}

	/**
	 * Generate Inverse Gamma Distributed Random Number
	 * 
	 * @return The Generalized Gamma Distributed Random Number
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double randomInverseGamma()
		throws java.lang.Exception
	{
		return 1. / random();
	}
}
