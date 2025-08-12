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
package com.itheima.sfbx.framework.rule.builder.table;

import com.itheima.sfbx.framework.rule.model.rule.lhs.*;
import com.itheima.sfbx.framework.rule.model.table.*;

import java.util.List;

/**
 * @author Jacky.gao
 * @since 2015年1月20日
 */
public class CellContentBuilder {
	public Criterion buildCriterion(Cell cell,Column col){
		Joint joint=cell.getJoint();
		if(joint==null){
			return null;
		}
		List<Condition> conditions=joint.getConditions();
		List<Joint> joints=joint.getJoints();
		if((conditions==null || conditions.size()==0) && (joints==null || joints.size()==0)){
			return null;
		}
		Junction topJunction=null;
		if(conditions.size()==1){
			return newCriteria(col, conditions.get(0));
		}else{
			if(joint.getType().equals(JointType.and)){
				topJunction=new And();
			}else{
				topJunction=new Or();
			}
			buildConditionsCriterion(conditions, topJunction, col);
			buildJointsCriterion(joints, col, topJunction);
			return topJunction;
		}
	}
	private void buildJointsCriterion(List<Joint> joints,Column col,Junction parentJunction){
		if(joints==null || joints.size()==0){
			return;
		}
		for(Joint joint:joints){
			Junction junction=joint.getJunction();
			List<Condition> conditions=joint.getConditions();
			buildConditionsCriterion(conditions,junction,col);
			List<Joint> children=joint.getJoints();
			buildJointsCriterion(children,col,junction);
			parentJunction.addCriterion(junction);
		}
	}
	private void buildConditionsCriterion(List<Condition> conditions,Junction junction,Column col){
		if(conditions==null || conditions.size()==0){
			return;
		}
		for(Condition condition:conditions){
			Criteria criteria = newCriteria(col, condition);
			junction.addCriterion(criteria);
		}
	}
	private Criteria newCriteria(Column col, Condition condition) {
		Criteria criteria=new Criteria();
		Left left=new Left();
		VariableLeftPart part=new VariableLeftPart();
		part.setVariableCategory(col.getVariableCategory());
		part.setVariableName(col.getVariableName());
		part.setVariableLabel(col.getVariableLabel());
		part.setDatatype(col.getDatatype());
		left.setLeftPart(part);
		left.setType(LeftType.variable);
		criteria.setLeft(left);
		criteria.setOp(condition.getOp());
		criteria.setValue(condition.getValue());
		return criteria;
	}
}
