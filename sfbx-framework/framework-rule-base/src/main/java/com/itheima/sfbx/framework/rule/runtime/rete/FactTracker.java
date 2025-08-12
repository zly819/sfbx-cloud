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

import com.itheima.sfbx.framework.rule.model.GeneralEntity;
import com.itheima.sfbx.framework.rule.model.rule.lhs.BaseCriteria;
import com.itheima.sfbx.framework.rule.runtime.agenda.Activation;
import com.itheima.sfbx.framework.rule.runtime.agenda.ActivationImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jacky.gao
 * @since 2015年8月2日
 */
public class FactTracker {
	private Activation activation;
	private Map<Object,List<BaseCriteria>> objectCriteriaMap=new HashMap<Object,List<BaseCriteria>>();
	public Activation getActivation() {
		return activation;
	}
	public void setActivation(Activation activation) {
		ActivationImpl ac=(ActivationImpl)activation;
		ac.setObjectCriteriaMap(objectCriteriaMap);
		this.activation = activation;
	}
	public Map<Object, List<BaseCriteria>> getObjectCriteriaMap() {
		return objectCriteriaMap;
	}
	public void addObjectCriteria(Object obj, BaseCriteria criteria) {
		if(obj instanceof HashMap && !(obj instanceof GeneralEntity)){
			obj=HashMap.class.getName();
		}
		if(objectCriteriaMap.containsKey(obj)){
			List<BaseCriteria> list=objectCriteriaMap.get(obj);
			if(!list.contains(criteria)){
				list.add(criteria);
			}
		}else{
			List<BaseCriteria> list=new ArrayList<BaseCriteria>();
			list.add(criteria);
			objectCriteriaMap.put(obj, list);
		}
	}
	public FactTracker newSubFactTracker(){
		FactTracker tracker=new FactTracker();
		tracker.getObjectCriteriaMap().putAll(objectCriteriaMap);
		return tracker;
	}
}
