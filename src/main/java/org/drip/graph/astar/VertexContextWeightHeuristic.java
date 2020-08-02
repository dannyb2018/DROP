
package org.drip.graph.astar;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>VertexContextWeightHeuristic</i> computes the Reese (1999) Epsilon-Admissible Weight Heuristic for use
 * 	in the Alpha A<sup>*</sup> Heuristic Function. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Dechter, R., and J. Pearl (1985): Generalized Best-first Search Strategies and the Optimality of
 *  			A<sup>*</sup> <i>Journal of the ACM</i> <b>32 (3)</b> 505-536
 *  	</li>
 *  	<li>
 *  		Hart, P. E., N. J. Nilsson, and B. Raphael (1968): A Formal Basis for the Heuristic Determination
 *  			of the Minimum Cost Paths <i>IEEE Transactions on Systems Sciences and Cybernetics</i> <b>4
 *  			(2)</b> 100-107
 *  	</li>
 *  	<li>
 *  		Kagan, E., and I. Ben-Gal (2014): A Group Testing Algorithm with Online Informational Learning
 *  			<i>IIE Transactions</i> <b>46 (2)</b> 164-184
 *  	</li>
 *  	<li>
 *  		Russell, S. J. and P. Norvig (2018): <i>Artificial Intelligence: A Modern Approach 4<sup>th</sup>
 *  			Edition</i> <b>Pearson</b>
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): A<sup>*</sup> Search Algorithm
 *  			https://en.wikipedia.org/wiki/A*_search_algorithm
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/astar/README.md">A<sup>*</sup> Heuristic Shortest Path Family</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class VertexContextWeightHeuristic
{
	private double _bigLambda = java.lang.Double.NaN;
	private double _smallLambda = java.lang.Double.NaN;
	private org.drip.graph.astar.VertexFunction _gHeuristic = null;

	/**
	 * VertexContextWeightHeuristic Constructor
	 * 
	 * @param gHeuristic The G Heuristic
	 * @param smallLambda The Small Lambda
	 * @param bigLambda The Big Lambda
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public VertexContextWeightHeuristic (
		final org.drip.graph.astar.VertexFunction gHeuristic,
		final double smallLambda,
		final double bigLambda)
		throws java.lang.Exception
	{
		if (null == (_gHeuristic = gHeuristic) ||
			!org.drip.numerical.common.NumberUtil.IsValid (
				_smallLambda = smallLambda
			) || !org.drip.numerical.common.NumberUtil.IsValid (
				_bigLambda = bigLambda
			) || _smallLambda > _bigLambda
		)
		{
			throw new java.lang.Exception (
				"VertexContextWeightHeuristic Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the G Heuristic
	 * 
	 * @return The G Heuristic
	 */

	public org.drip.graph.astar.VertexFunction gHeuristic()
	{
		return _gHeuristic;
	}

	/**
	 * Retrieve the Small Lambda
	 * 
	 * @return The Small Lambda
	 */

	public double smallLambda()
	{
		return _smallLambda;
	}

	/**
	 * Retrieve the Big Lambda
	 * 
	 * @return The Big Lambda
	 */

	public double bigLambda()
	{
		return _bigLambda;
	}

	/**
	 * Compute the Epsilon-Admissible Weight Heuristic for the Specified Vertex Context
	 * 
	 * @param vertexContext The vertex Context
	 * 
	 * @return The Epsilon-Admissible Weight Heuristic for the Specified Vertex Context
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double evaluate (
		final org.drip.graph.astar.VertexContext vertexContext)
		throws java.lang.Exception
	{
		if (null == vertexContext)
		{
			throw new java.lang.Exception (
				"VertexContextWeightHeuristic::evaluate => Invalid Inputs"
			);
		}

		return _gHeuristic.evaluate (
			vertexContext.parent()
		) <= _gHeuristic.evaluate (
			vertexContext.mostRecentlyExpanded()
		) ? _smallLambda : _bigLambda;
	}
}
