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
package com.itheima.sfbx.framework.rule.runtime.agenda;

import com.itheima.sfbx.framework.rule.action.ActionValue;
import com.itheima.sfbx.framework.rule.model.rule.RuleInfo;
import com.itheima.sfbx.framework.rule.runtime.KnowledgeSessionImpl;
import com.itheima.sfbx.framework.rule.runtime.WorkingMemory;
import com.itheima.sfbx.framework.rule.runtime.response.ExecutionResponseImpl;
import com.itheima.sfbx.framework.rule.runtime.response.RuleExecutionResponse;
import com.itheima.sfbx.framework.rule.runtime.rete.*;
import com.itheima.sfbx.framework.rule.runtime.rete.EvaluationContext;
import com.itheima.sfbx.framework.rule.runtime.rete.FactTracker;
import com.itheima.sfbx.framework.rule.runtime.rete.ReteInstance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * @author Jacky.gao
 * @since 2015年1月2日
 */
public class Agenda {
	private Context context;
	private List<RuleBox> ruleBoxes=new ArrayList<RuleBox>();
	private List<RuleInfo> matchedRules=new ArrayList<RuleInfo>();
	public Agenda(WorkingMemory workingMemory,Context context){
		this.context=context;
		ruleBoxes.add(new AgendaGroupRuleBox(context,matchedRules));
		ruleBoxes.add(new ActivationGroupRuleBox(context,matchedRules));
		ruleBoxes.add(new ActivationRuleBox(context,matchedRules));
	}
	public RuleExecutionResponse execute(AgendaFilter filter,int max){
		ExecutionResponseImpl response=new ExecutionResponseImpl();
		List<ActionValue> actionValues=new ArrayList<ActionValue>();
		response.setActionValues(actionValues);
		List<RuleInfo> firedRules=new ArrayList<RuleInfo>();
		RuleBox ruleBox=nextRuleBox();
		while(ruleBox!=null){
			List<RuleInfo> ruleInfoResult=ruleBox.execute(filter, max-firedRules.size(),actionValues);
			if(ruleInfoResult!=null && ruleInfoResult.size()>0){
				firedRules.addAll(ruleInfoResult);
			}
			if(firedRules.size()>=max){
				break;
			}
			ruleBox=nextRuleBox();
		}
		
		KnowledgeSessionImpl session=(KnowledgeSessionImpl)context.getWorkingMemory();
		List<ReteInstance> reteInstanceList=session.getReteInstanceList();
		for(ReteInstance reteInstance:reteInstanceList){
			reteInstance.reset();			
		}
		session.getAllFacts().clear();
		response.setFiredRules(firedRules);
		response.addMatchedRules(matchedRules);
		return response;
	}
	
	private RuleBox nextRuleBox(){
		for(RuleBox ruleBox:ruleBoxes){
			RuleBox next=ruleBox.next();
			if(next!=null){
				return next;
			}
		}
		return null;
	}
	
	
	public void addTrackers(Collection<FactTracker> list){
		for(FactTracker tracker:list){
			Activation activation=tracker.getActivation();
			for(RuleBox ruleBox:ruleBoxes){
				boolean add=ruleBox.add(activation);
				if(add){
					break;
				}
			}
		}
	}
	
	public void retract(Object obj){
		for(RuleBox ruleBox:ruleBoxes){
			ruleBox.retract(obj);
		}
	}
	
	public void reevaluate(Object obj,EvaluationContext context){
		for(RuleBox ruleBox:ruleBoxes){
			ruleBox.reevaluate(obj, context);
		}
	}
	
	public List<RuleBox> getRuleBoxes() {
		return ruleBoxes;
	}
	
	public void clean(){
		for(RuleBox ruleBox:ruleBoxes){
			ruleBox.clean();
		}
	}
}
