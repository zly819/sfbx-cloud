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

import com.itheima.sfbx.framework.rule.model.scorecard.ScorecardDefinition;
import com.itheima.sfbx.framework.rule.parse.scorecard.ScorecardParser;
import org.dom4j.Element;

/**
 * @author Jacky.gao
 * @since 2016年9月22日
 */
public class ScorecardDeserializer implements Deserializer<ScorecardDefinition> {
	public static final String BEAN_ID="urule.scorecardDeserializer";
	private ScorecardParser scorecardParser;
	@Override
	public ScorecardDefinition deserialize(Element root) {
		ScorecardDefinition card=scorecardParser.parse(root);
		return card;
	}
	
	public void setScorecardParser(ScorecardParser scorecardParser) {
		this.scorecardParser = scorecardParser;
	}
	@Override
	public boolean support(Element root) {
		return scorecardParser.support(root.getName());
	}
}
