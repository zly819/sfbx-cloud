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
package com.itheima.sfbx.framework.rule.model.rete.builder;

import com.itheima.sfbx.framework.rule.RuleException;
import com.itheima.sfbx.framework.rule.model.rete.*;
import com.itheima.sfbx.framework.rule.model.rule.lhs.And;
import com.itheima.sfbx.framework.rule.model.rule.lhs.BaseCriterion;
import com.itheima.sfbx.framework.rule.model.rule.lhs.Criterion;

import java.util.List;

/**
 * @author Jacky.gao
 * @since 2016年9月9日
 */
public class AndBuilder extends JunctionBuilder{
	@Override
	public BaseReteNode buildCriterion(BaseCriterion c, BuildContext context) {
		And and=(And)c;
		AndNode andNode=null;
		ConditionNode currentCriteriaNode=null;
		List<Criterion> criterions=and.getCriterions();
		if(criterions==null || criterions.size()==0){
			throw new RuleException("Condition join node[and] need one child at least.");
		}
		for(Criterion criterion:criterions){
			ReteNode node=buildCriterion(criterion, context, currentCriteriaNode);
			if(node==null){
				continue;
			}
			if(node instanceof CriteriaNode){
				if(currentCriteriaNode!=null){
					List<ReteNode> childrenNodes=currentCriteriaNode.getChildrenNodes();
					if(!childrenNodes.contains(node)){
						if(andNode==null){
							andNode=new AndNode(context.nextId());
						}
						currentCriteriaNode.addLine(andNode);
					}
				}
				currentCriteriaNode=(ConditionNode)node;
			}else if(node instanceof JunctionNode){
				if(andNode==null){
					andNode=new AndNode(context.nextId());
				}
				((JunctionNode) node).addLine(andNode);
			}else if(node instanceof NamedCriteriaNode){
				if(currentCriteriaNode!=null){
					List<ReteNode> childrenNodes=currentCriteriaNode.getChildrenNodes();
					if(!childrenNodes.contains(node)){
						if(andNode==null){
							andNode=new AndNode(context.nextId());
						}
						currentCriteriaNode.addLine(andNode);
					}
				}
				currentCriteriaNode=(ConditionNode)node;
			}
		}
		if(criterions.size()==1 && currentCriteriaNode!=null){
			return (BaseReteNode)currentCriteriaNode;
		}
		if(andNode==null){
			return (BaseReteNode)currentCriteriaNode;
		}
		if(andNode!=null && currentCriteriaNode!=null){
			currentCriteriaNode.addLine(andNode);
		}
		return andNode;
	}
	
	@Override
	public boolean support(Criterion criterion) {
		return criterion instanceof And;
	}
}
