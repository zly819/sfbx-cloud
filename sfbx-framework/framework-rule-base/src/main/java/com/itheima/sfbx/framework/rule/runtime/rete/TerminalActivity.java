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
package com.itheima.sfbx.framework.rule.runtime.rete;

import com.itheima.sfbx.framework.rule.debug.MsgType;
import com.itheima.sfbx.framework.rule.model.rule.Rule;
import com.itheima.sfbx.framework.rule.runtime.KnowledgeSession;
import com.itheima.sfbx.framework.rule.runtime.agenda.ActivationImpl;
import com.itheima.sfbx.framework.rule.runtime.event.impl.ActivationCreatedEventImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Jacky.gao
 * @since 2015年1月8日
 */
public class TerminalActivity extends AbstractActivity {
	private Rule rule;
	public TerminalActivity(Rule rule) {
		this.rule = rule;
	}
	public Collection<FactTracker> enter(EvaluationContext context, Object obj,FactTracker tracker,Map<String,Object> variableMap) {
		List<FactTracker> result=new ArrayList<FactTracker>();
		ActivationImpl ac=new ActivationImpl(rule,variableMap);
		tracker.setActivation(ac);
		result.add(tracker);
		KnowledgeSession session = (KnowledgeSession)context.getWorkingMemory();
		session.fireEvent(new ActivationCreatedEventImpl(ac,session));
		if(rule.getDebug()!=null){
			String msg="√√√规则【"+rule.getName()+"】成功匹配";
			context.debugMsg(msg, MsgType.RuleMatch, rule.getDebug());
		}
		return result;
	}
	@Override
	public boolean orNodeIsPassed() {
		return false;
	}
	@Override
	public void reset() {
	}
}
