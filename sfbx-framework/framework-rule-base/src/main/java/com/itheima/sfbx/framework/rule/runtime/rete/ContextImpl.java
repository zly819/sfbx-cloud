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

import com.itheima.sfbx.framework.rule.Utils;
import com.itheima.sfbx.framework.rule.debug.MessageItem;
import com.itheima.sfbx.framework.rule.debug.MsgType;
import com.itheima.sfbx.framework.rule.runtime.ElCalculator;
import com.itheima.sfbx.framework.rule.runtime.WorkingMemory;
import com.itheima.sfbx.framework.rule.runtime.assertor.AssertorEvaluator;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jacky.gao
 * @since 2015年1月8日
 */
public class ContextImpl implements Context {
	private ApplicationContext applicationContext;
	private AssertorEvaluator assertorEvaluator;
	private Map<String,String> variableCategoryMap;
	private ValueCompute valueCompute;
	private WorkingMemory workingMemory;
	private List<MessageItem> debugMessageItems;
	private ElCalculator elCalculator=new ElCalculator();
	public ContextImpl(WorkingMemory workingMemory,ApplicationContext applicationContext,Map<String,String> variableCategoryMap,List<MessageItem> debugMessageItems) {
		this.workingMemory=workingMemory;
		this.applicationContext = applicationContext;
		this.assertorEvaluator=(AssertorEvaluator)applicationContext.getBean(AssertorEvaluator.BEAN_ID);
		this.variableCategoryMap=variableCategoryMap;
		this.debugMessageItems=debugMessageItems;
		this.valueCompute=(ValueCompute)applicationContext.getBean(ValueCompute.BEAN_ID);
	}
	@Override
	public WorkingMemory getWorkingMemory() {
		return workingMemory;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	public AssertorEvaluator getAssertorEvaluator() {
		return assertorEvaluator;
	}
	
	@Override
	public Object parseExpression(String expression) {
		return elCalculator.eval(expression);
	}
	
	@Override
	public void debugMsg(String msg, MsgType type, boolean enableDebug) {
		if(!Utils.isDebug() || !enableDebug){
			return;
		}
		if(!Utils.isDebugToFile()){
			System.out.println(msg);
			return;
		}
		MessageItem item=new MessageItem(msg,type);
		debugMessageItems.add(item);		
	}
	
	@Override
	public List<MessageItem> getDebugMessageItems() {
		return debugMessageItems;
	}
	
	public String getVariableCategoryClass(String variableCategory) {
		String clazz=variableCategoryMap.get(variableCategory);
		if(StringUtils.isEmpty(clazz)){
			//throw new RuleException("Variable category ["+variableCategory+"] not exist.");
			clazz=HashMap.class.getName();
		}
		return clazz;
	}
	public ValueCompute getValueCompute() {
		return valueCompute;
	}
}
