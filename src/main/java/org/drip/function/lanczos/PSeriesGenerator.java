
package org.drip.function.lanczos;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>PSeriesGenerator</i> generates the Terms of the Lanczos P Series. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Godfrey, P. (2001): Lanczos Implementation of the Gamma Function
 * 				http://www.numericana.com/answer/info/godfrey.htm
 * 		</li>
 * 		<li>
 * 			Press, W. H., S. A. Teukolsky, W. T. Vetterling, and B. P. Flannery (2007): <i>Numerical Recipes:
 * 				The Art of Scientific Computing 3rd Edition</i> <b>Cambridge University Press</b> New York
 * 		</li>
 * 		<li>
 * 			Pugh, G. R. (2004): <i>An Analysis of the Lanczos Gamma Approximation</i> Ph. D. <b>University of
 * 				British Columbia</b>
 * 		</li>
 * 		<li>
 * 			Toth V. T. (2016): Programmable Calculators � The Gamma Function
 * 				http://www.rskey.org/CMS/index.php/the-library/11
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Lanczos Approximation https://en.wikipedia.org/wiki/Lanczos_approximation
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/lanczos/README.md">Lanczos Scheme for Gamma Estimate</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PSeriesGenerator extends org.drip.numerical.estimation.R0ToR1Series
{
	private double[][] _chebyshevCoefficientMatrix = null;

	/**
	 * Construct a Standard Instance of the Lanczos P Series Generator
	 * 
	 * @param g Lanczos g Control
	 * @param termCount Lanczos Series Term Count
	 * 
	 * @return Standard Instance of the Lanczos P Series Generator
	 */

	public static final PSeriesGenerator Standard (
		final int g,
		final int termCount)
	{
		double[][] chebyshevCoefficientMatrix = org.drip.function.lanczos.ChebyshevCoefficientMatrix.Rollout
			(2 * termCount);

		if (null == chebyshevCoefficientMatrix)
		{
			return null;
		}

		java.util.TreeMap<java.lang.Integer, java.lang.Double> chebyshevCoefficientWeightMap = new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		double sqrt2OverPI = java.lang.Math.sqrt (2.) / java.lang.Math.PI;

		for (int termIndex = 0; termIndex <= termCount; ++termIndex)
		{
			chebyshevCoefficientWeightMap.put (
				termIndex,
				sqrt2OverPI * chebyshevCoefficientMatrix[2 * termCount][2 * termIndex]
			);
		}

		try
		{
			return new PSeriesGenerator (
				new org.drip.function.lanczos.PSeriesTerm (g),
				chebyshevCoefficientWeightMap,
				chebyshevCoefficientMatrix
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * PSeriesGenerator Constructor
	 * 
	 * @param pSeriesTerm Lanczos P Series Term
	 * @param chebyshevCoefficientWeightMap Chebyshev Coefficient Term Weight Map
	 * @param chebyshevCoefficientMatrix Chebyshev Coefficient Matrix
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PSeriesGenerator (
		final org.drip.function.lanczos.PSeriesTerm pSeriesTerm,
		final java.util.TreeMap<java.lang.Integer, java.lang.Double> chebyshevCoefficientWeightMap,
		final double[][] chebyshevCoefficientMatrix)
		throws java.lang.Exception
	{
		super (
			pSeriesTerm,
			false,
			chebyshevCoefficientWeightMap,
			true
		);

		_chebyshevCoefficientMatrix = chebyshevCoefficientMatrix;
	}

	/**
	 * Retrieve the Chebyshev Coefficient Matrix
	 * 
	 * @return The Chebyshev Coefficient Matrix
	 */

	public double[][] _chebyshevCoefficientMatrix()
	{
		return _chebyshevCoefficientMatrix;
	}

	/**
	 * Retrieve the Series Term Count
	 * 
	 * @return The Series Term Count
	 */

	public int termCount()
	{
		return _chebyshevCoefficientMatrix.length;
	}
}
