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
package com.itheima.sfbx.framework.rule.runtime;

import com.itheima.sfbx.framework.rule.model.flow.FlowDefinition;
import com.itheima.sfbx.framework.rule.model.rete.Rete;
import com.itheima.sfbx.framework.rule.model.rule.Rule;
import com.itheima.sfbx.framework.rule.runtime.rete.ReteInstance;

import java.util.List;
import java.util.Map;

/**
 * @author Jacky.gao
 * @since 2015年1月20日
 */
public interface KnowledgePackage {
	Rete getRete();
	Map<String,String> getVariableCateogoryMap();
	Map<String, FlowDefinition> getFlowMap();
	Map<String, String> getParameters();
	ReteInstance newReteInstance();
	long getTimestamp();
	void resetTimestamp();
	List<Rule> getNoLhsRules();
	List<Rule> getWithElseRules();
	String getId();
}
