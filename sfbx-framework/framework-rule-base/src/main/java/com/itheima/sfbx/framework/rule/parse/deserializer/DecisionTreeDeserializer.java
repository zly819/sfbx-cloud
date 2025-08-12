/*******************************************************************************
 * Copyright 2017 Bstek
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.itheima.sfbx.framework.rule.parse.deserializer;

import com.itheima.sfbx.framework.rule.model.decisiontree.DecisionTree;
import com.itheima.sfbx.framework.rule.parse.decisiontree.DecisionTreeParser;
import org.dom4j.Element;

/**
 * @author Jacky.gao
 * @since 2016年2月29日
 */
public class DecisionTreeDeserializer implements Deserializer<DecisionTree> {
	public static final String BEAN_ID="urule.decisionTreeDeserializer";
	private DecisionTreeParser decisionTreeParser;
	@Override
	public DecisionTree deserialize(Element root) {
		return decisionTreeParser.parse(root);
	}
	public void setDecisionTreeParser(DecisionTreeParser decisionTreeParser) {
		this.decisionTreeParser = decisionTreeParser;
	}
	@Override
	public boolean support(Element root) {
		return decisionTreeParser.support(root.getName());
	}
}
